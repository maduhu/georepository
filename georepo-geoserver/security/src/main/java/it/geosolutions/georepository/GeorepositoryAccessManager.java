/*
 *  Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.georepository;

import it.geosolutions.georepo.core.model.LayerAttribute;
import it.geosolutions.georepo.core.model.enums.AccessType;
import it.geosolutions.georepo.core.model.enums.GrantType;
import it.geosolutions.georepo.services.RuleReaderService;
import it.geosolutions.georepo.services.dto.AccessInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.geoserver.catalog.CoverageInfo;
import org.geoserver.catalog.FeatureTypeInfo;
import org.geoserver.catalog.LayerInfo;
import org.geoserver.catalog.ResourceInfo;
import org.geoserver.catalog.StoreInfo;
import org.geoserver.catalog.WMSLayerInfo;
import org.geoserver.catalog.WorkspaceInfo;
import org.geoserver.ows.Dispatcher;
import org.geoserver.ows.Request;
import org.geoserver.security.CatalogMode;
import org.geoserver.security.CoverageAccessLimits;
import org.geoserver.security.DataAccessLimits;
import org.geoserver.security.ResourceAccessManager;
import org.geoserver.security.VectorAccessLimits;
import org.geoserver.security.WMSAccessLimits;
import org.geoserver.security.WorkspaceAccessLimits;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.filter.text.cql2.CQLException;
import org.geotools.filter.text.ecql.ECQL;
import org.geotools.util.Converters;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.expression.PropertyName;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.providers.anonymous.AnonymousAuthenticationToken;

import com.vividsolutions.jts.geom.MultiPolygon;

/**
 * Makes GeoServer use the GeoRepository to assess data access rules
 * 
 * @author Andrea Aime - GeoSolutions
 */
public class GeorepositoryAccessManager implements ResourceAccessManager {

    enum PropertyAccessMode {
        READ, WRITE
    };

    /**
     * The role given to the administrators
     */
    static final String ROOT_ROLE = "ROLE_ADMINISTRATOR";

    static final FilterFactory2 FF = CommonFactoryFinder.getFilterFactory2(null);

    CatalogMode catalogMode = CatalogMode.CHALLENGE;

    RuleReaderService rules;

    String instanceName;

    public GeorepositoryAccessManager(RuleReaderService rules, String instanceName) {
        this.rules = rules;
        this.instanceName = instanceName;
    }

    @Override
    public WorkspaceAccessLimits getAccessLimits(Authentication user, WorkspaceInfo workspace) {
        return new WorkspaceAccessLimits(CatalogMode.CHALLENGE, true, true);
    }

    @Override
    public DataAccessLimits getAccessLimits(Authentication user, LayerInfo layer) {
        return getAccessLimits(user, layer.getResource());
    }

    @Override
    public DataAccessLimits getAccessLimits(Authentication user, ResourceInfo resource) {
        // extract the user name
        String username = null;
        if (user != null && !(user instanceof AnonymousAuthenticationToken)) {
            // shortcut, if the user is the admin, he can do everything
            for (GrantedAuthority authority : user.getAuthorities()) {
                final String userRole = authority.getAuthority();
                if (ROOT_ROLE.equals(userRole)) {
                    return buildAccessLimits(resource, AccessInfo.ALLOW_ALL);
                }
            }

            username = user.getName();
        }

        // get info from the current request
        String service = null;
        String request = null;
        Request owsRequest = Dispatcher.REQUEST.get();
        if (owsRequest != null) {
            service = owsRequest.getService();
            request = owsRequest.getRequest();
        }

        // get the resource info
        String layer = resource.getName();
        StoreInfo store = resource.getStore();
        WorkspaceInfo ws = store.getWorkspace();
        String workspace = ws.getName();

        // get the request infos
        AccessInfo rule = rules.getAccessInfo(username, null, instanceName, service, request,
                workspace, layer);
        if (rule == null) {
            rule = AccessInfo.DENY_ALL;
        }
        return buildAccessLimits(resource, rule);
    }

    /**
     * 
     * @param resource
     * @param rule
     * @return
     */
    DataAccessLimits buildAccessLimits(ResourceInfo resource, AccessInfo rule) {
        // basic filter
        Filter readFilter = rule.getGrant() == GrantType.ALLOW ? Filter.INCLUDE : Filter.EXCLUDE;
        Filter writeFilter = rule.getGrant() == GrantType.ALLOW ? Filter.INCLUDE : Filter.EXCLUDE;
        try {
            if (rule.getCqlFilterRead() != null) {
                readFilter = ECQL.toFilter(rule.getCqlFilterRead());
            }
            if (rule.getCqlFilterWrite() != null) {
                writeFilter = ECQL.toFilter(rule.getCqlFilterWrite());
            }
        } catch (CQLException e) {
            throw new IllegalArgumentException("Invalid cql filter found: " + e.getMessage(), e);
        }

        // get the attributes
        List<PropertyName> readAttributes = toPropertyNames(rule.getAttributes(),
                PropertyAccessMode.READ);
        List<PropertyName> writeAttributes = toPropertyNames(rule.getAttributes(),
                PropertyAccessMode.WRITE);

        if (resource instanceof FeatureTypeInfo) {
            // merge the area among the filters
            if (rule.getArea() != null) {
                Filter areaFilter = FF.intersects(FF.property(""), FF.literal(rule.getArea()));
                readFilter = mergeFilter(readFilter, areaFilter);
                writeFilter = mergeFilter(writeFilter, areaFilter);
            }

            return new VectorAccessLimits(catalogMode, readAttributes, readFilter, writeAttributes,
                    writeFilter);
        } else if (resource instanceof CoverageInfo) {
            MultiPolygon rasterFilter = buildRasterFilter(rule);
            return new CoverageAccessLimits(catalogMode, readFilter, rasterFilter, null);
        } else if (resource instanceof WMSLayerInfo) {
            MultiPolygon rasterFilter = buildRasterFilter(rule);
            return new WMSAccessLimits(catalogMode, readFilter, rasterFilter, true);
        } else {
            throw new IllegalArgumentException("Don't know how to handle resource " + resource);
        }
    }

    private MultiPolygon buildRasterFilter(AccessInfo rule) {
        MultiPolygon rasterFilter = null;
        if (rule.getArea() != null) {
            rasterFilter = Converters.convert(rule.getArea(), MultiPolygon.class);
            if (rasterFilter == null) {
                throw new RuntimeException("Error applying security rules, cannot convert "
                        + "the GeoRepository area restriction " + rule.getArea()
                        + " to a multi-polygon");
            }
        }
        return rasterFilter;
    }

    /**
     * Merges the two filters into one by AND
     * 
     * @param filter
     * @param areaFilter
     * @return
     */
    private Filter mergeFilter(Filter filter, Filter areaFilter) {
        if (filter == null || filter == Filter.INCLUDE) {
            return areaFilter;
        } else if (filter == Filter.EXCLUDE) {
            return filter;
        } else {
            return FF.and(filter, areaFilter);
        }
    }

    /**
     * Builds the equivalent {@link PropertyName} list for the specified access mode
     * 
     * @param attributes
     * @param mode
     * @return
     */
    private List<PropertyName> toPropertyNames(Set<LayerAttribute> attributes,
            PropertyAccessMode mode) {
        // handle simple case
        if (attributes == null || attributes.size() == 0) {
            return null;
        }

        // filter and translate
        List<PropertyName> result = new ArrayList<PropertyName>();
        for (LayerAttribute attribute : attributes) {
            if (attribute.getAccess() == AccessType.READWRITE
                    || (mode == PropertyAccessMode.READ && attribute.getAccess() == AccessType.READONLY)) {
                PropertyName property = FF.property(attribute.getName());
                result.add(property);
            }
        }

        return result;
    }

}