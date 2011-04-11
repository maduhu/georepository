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
import it.geosolutions.georepo.services.dto.RuleFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.geoserver.catalog.Catalog;
import org.geoserver.catalog.CoverageInfo;
import org.geoserver.catalog.FeatureTypeInfo;
import org.geoserver.catalog.LayerInfo;
import org.geoserver.catalog.ResourceInfo;
import org.geoserver.catalog.StoreInfo;
import org.geoserver.catalog.StyleInfo;
import org.geoserver.catalog.WMSLayerInfo;
import org.geoserver.catalog.WorkspaceInfo;
import org.geoserver.ows.Dispatcher;
import org.geoserver.ows.DispatcherCallback;
import org.geoserver.ows.Request;
import org.geoserver.ows.Response;
import org.geoserver.ows.util.KvpUtils;
import org.geoserver.platform.Operation;
import org.geoserver.platform.Service;
import org.geoserver.platform.ServiceException;
import org.geoserver.security.CatalogMode;
import org.geoserver.security.CoverageAccessLimits;
import org.geoserver.security.DataAccessLimits;
import org.geoserver.security.ResourceAccessManager;
import org.geoserver.security.VectorAccessLimits;
import org.geoserver.security.WMSAccessLimits;
import org.geoserver.security.WorkspaceAccessLimits;
import org.geoserver.wms.GetFeatureInfo;
import org.geoserver.wms.GetFeatureInfoRequest;
import org.geoserver.wms.GetLegendGraphicRequest;
import org.geoserver.wms.GetMapRequest;
import org.geoserver.wms.MapLayerInfo;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.filter.text.cql2.CQLException;
import org.geotools.filter.text.ecql.ECQL;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.styling.Style;
import org.geotools.util.Converters;
import org.geotools.util.logging.Logging;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.expression.PropertyName;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.anonymous.AnonymousAuthenticationToken;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPolygon;

/**
 * Makes GeoServer use the GeoRepository to assess data access rules
 * 
 * @author Andrea Aime - GeoSolutions
 */
public class GeorepositoryAccessManager implements ResourceAccessManager, DispatcherCallback {

    static final Logger LOGGER = Logging.getLogger(GeorepositoryAccessManager.class);

    enum PropertyAccessMode {
        READ, WRITE
    };

    /**
     * The role given to the administrators
     */
    static final String ROOT_ROLE = "ROLE_ADMINISTRATOR";
    
    static final FilterFactory2 FF = CommonFactoryFinder.getFilterFactory2(null);

    CatalogMode catalogMode = CatalogMode.HIDE;

    RuleReaderService rules;
    
    Catalog catalog;

    String instanceName;

    public GeorepositoryAccessManager(RuleReaderService rules, Catalog catalog, String instanceName) {
        this.rules = rules;
        this.catalog = catalog;
        this.instanceName = instanceName;

        LOGGER.log(Level.INFO,
                "Initializing the GeoRepository access manager with instance name {0}",
                instanceName);
    }
    
    boolean isAdmin(Authentication user) {
        if (user.getAuthorities() != null) {
            for (GrantedAuthority authority : user.getAuthorities()) {
                final String userRole = authority.getAuthority();
                if (ROOT_ROLE.equals(userRole)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public WorkspaceAccessLimits getAccessLimits(Authentication user, WorkspaceInfo workspace) {
        LOGGER.log(Level.FINE, "Getting access limits for workspace {0}", workspace.getName());

        // extract the user name
        String username = null;
        if (user != null && !(user instanceof AnonymousAuthenticationToken)) {
            // shortcut, if the user is the admin, he can do everything
            if (isAdmin(user)) {
                LOGGER.log(Level.FINE, "Admin level access, returning "
                        + "full rights for workspace {0}" + workspace.getName());
                return buildAccessLimits(workspace, AccessInfo.ALLOW_ALL);
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

        // get the request infos
        RuleFilter ruleFilter = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
        if(username == null) {
            ruleFilter.setUser(RuleFilter.SpecialFilterType.DEFAULT);
        } else {
            ruleFilter.setUser(username);
        }
        ruleFilter.setInstance(instanceName);
        
        if(service != null) {
            if( "*".equals(service))
                ruleFilter.setService(RuleFilter.SpecialFilterType.ANY);
            else
                ruleFilter.setService(service);
        }
        
        if(request != null) {
            if( "*".equals(request))
                ruleFilter.setRequest(RuleFilter.SpecialFilterType.ANY);
            else
                ruleFilter.setRequest(request);
        }
        ruleFilter.setWorkspace(workspace.getName());
        AccessInfo rule = rules.getAccessInfo(ruleFilter);
        
        if (rule == null) {
            rule = AccessInfo.DENY_ALL;
        }
        WorkspaceAccessLimits limits = buildAccessLimits(workspace, rule);
        LOGGER.log(Level.SEVERE, "Returning {0} for workspace {1} and user {2}", 
                new Object[] {limits, workspace.getName(), username});
        return limits;
    }

    private WorkspaceAccessLimits buildAccessLimits(WorkspaceInfo workspace, AccessInfo rule) {
        if (rule == null) {
            return new WorkspaceAccessLimits(catalogMode, true, true);
        } else {
            return new WorkspaceAccessLimits(catalogMode, rule.getGrant() == GrantType.ALLOW, rule
                    .getGrant() == GrantType.ALLOW);
        }
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
            if (isAdmin(user)) {
                LOGGER.log(Level.FINE, "Admin level access, returning "
                        + "full rights for layer {0}" + resource.getPrefixedName());
                return buildAccessLimits(resource, AccessInfo.ALLOW_ALL);
            }

            username = user.getName();
        }

        // get info from the current request
        String service = "*";
        String request = "*";
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
        RuleFilter ruleFilter = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
        if(username == null)
            ruleFilter.setUser(RuleFilter.SpecialFilterType.DEFAULT);
        else
            ruleFilter.setUser(username);
        ruleFilter.setInstance(instanceName);
        if(service != null) {
            if( "*".equals(service))
                ruleFilter.setService(RuleFilter.SpecialFilterType.ANY);
            else
                ruleFilter.setService(service);
        }

        if(request != null) {
            if( "*".equals(request))
                ruleFilter.setRequest(RuleFilter.SpecialFilterType.ANY);
            else
                ruleFilter.setRequest(request);
        }
        ruleFilter.setWorkspace(workspace);
        ruleFilter.setLayer(layer);
        AccessInfo rule = rules.getAccessInfo(ruleFilter);
        
        if (rule == null) {
            rule = AccessInfo.DENY_ALL;
        }
        DataAccessLimits limits = buildAccessLimits(resource, rule);
        LOGGER.log(Level.FINE, "Returning {0} for layer {1} and user {2}", 
                new Object[] {limits, resource.getPrefixedName(), username});
        return limits;
    }

    /**
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
        
        // reproject the area if necessary
        Geometry area = rule.getArea();
        if(area != null && area.getSRID() > 0) {
            try {
                CoordinateReferenceSystem geomCrs = CRS.decode("EPSG:" + area.getSRID());
                CoordinateReferenceSystem resourceCrs = resource.getCRS();
                if(resourceCrs != null && !CRS.equalsIgnoreMetadata(geomCrs, resourceCrs)) {
                    MathTransform mt = CRS.findMathTransform(geomCrs, resourceCrs, true);
                    area = JTS.transform(area, mt);
                    rule.setArea(area);
                }
            } catch(Exception e) {
                throw new RuntimeException("Failed to reproject the restricted area to the layer's native SRS", e);
            }
        }

        if (resource instanceof FeatureTypeInfo) {
            // merge the area among the filters
            if (area != null) {
                Filter areaFilter = FF.intersects(FF.property(""), FF.literal(area));
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

    @Override
    public void finished(Request request) {
        // nothing to do
    }

    @Override
    public Request init(Request request) {
        return request;
    }

    @Override
    public Operation operationDispatched(Request gsRequest, Operation operation) {
        // service and request
        String service = gsRequest.getService();
        String request = gsRequest.getRequest();
        
        // get the user
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (user != null && !(user instanceof AnonymousAuthenticationToken)) {
            // shortcut, if the user is the admin, he can do everything
            if (isAdmin(user)) {
                LOGGER.log(Level.FINE, "Admin level access, no applying default style for this request");
                return operation;
            } else {
                username = user.getName();
            }
        }

        if(request != null && "WMS".equalsIgnoreCase(service) && ("GetMap".equalsIgnoreCase(request) 
                || "GetFeatureInfo".equalsIgnoreCase(request))) {
            // extract the getmap part
            Object ro = operation.getParameters()[0];
            GetMapRequest getMap;
            if(ro instanceof GetMapRequest) {
                getMap = (GetMapRequest) ro;
            } else if(ro instanceof GetFeatureInfoRequest) {
                getMap = ((GetFeatureInfoRequest) ro).getGetMapRequest();
            } else {
                throw new ServiceException("Unrecognized request object: " + ro);
            }
            
            overrideGetMapRequest(gsRequest, service, request, username, getMap);
        } else if(request != null && "WMS".equalsIgnoreCase(service) && "GetLegendGraphic".equalsIgnoreCase(request)) {
            overrideGetLegendGraphicRequest(gsRequest, operation, service, request, username);
            
        }
        
        return operation;
    }

    void overrideGetLegendGraphicRequest(Request gsRequest, Operation operation,
            String service, String request, String username) {
        // get the layer
        String layerName = (String) gsRequest.getKvp().get("LAYER");
        LayerInfo layer = catalog.getLayerByName(layerName);
        ResourceInfo resource = layer.getResource();
        
        // get the rule, it contains default and allowed styles
        RuleFilter ruleFilter = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
        ruleFilter.setUser(username);
        ruleFilter.setInstance(instanceName);
        ruleFilter.setService(service);
        ruleFilter.setRequest(request);
        ruleFilter.setWorkspace(resource.getStore().getWorkspace().getName());
        ruleFilter.setLayer(resource.getName());
        AccessInfo rule = rules.getAccessInfo(ruleFilter);
        
        // get the request object
        GetLegendGraphicRequest getLegend = (GetLegendGraphicRequest) operation.getParameters()[0];
        
        // get the requested style
        String styleName = (String) gsRequest.getKvp().get("STYLE");
        if(styleName == null) {
            if(rule.getDefaultStyle() != null) {
                try {
                    StyleInfo si = catalog.getStyleByName(rule.getDefaultStyle());
                    if(si == null) {
                        throw new ServiceException("Could not find default style suggested " +
                                    "by GeoRepository: " + rule.getDefaultStyle());
                    }
                    getLegend.setStyle(si.getStyle());
                } catch (IOException e) {
                    throw new ServiceException("Unable to load the style suggested by GeoRepository: " 
                            + rule.getDefaultStyle(), e);
                }
            }
        } else {
            checkStyleAllowed(rule, styleName);
        }
    }

    private void overrideGetMapRequest(Request gsRequest, String service, String request,
            String username, GetMapRequest getMap) {
        // basic sanity checks, we don't allow dynamic styling
        if(getMap.getSld() != null || getMap.getSldBody() != null) {
            throw new ServiceException("Dynamic style usage is forbidden");
        }
        
        if(gsRequest.getKvp().get("layers") == null) {
            throw new ServiceException("GetMap POST requests are forbidden");
        }
        
        // parse the styles param like the kvp parser would (since we have no way,
        // to know if a certain style was requested explicitly or defaulted, and
        // we need to tell apart the default case from the explicit request case
        String stylesParam = (String) gsRequest.getRawKvp().get("STYLES");
        List<String> styleNameList = new ArrayList<String>();
        if (stylesParam != null) {
            styleNameList.addAll(KvpUtils.readFlat(stylesParam));
        }
        
        // apply the override/security check for each layer in the request
        List<MapLayerInfo> layers = getMap.getLayers();
        for (int i = 0; i < layers.size(); i++) {
            MapLayerInfo layer = layers.get(i);
            ResourceInfo info = layer.getResource();
            
            if(info == null) {
                throw new ServiceException("Remote layers are not allowed");
            } 
            
            // get the rule, it contains default and allowed styles
            RuleFilter ruleFilter = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
            ruleFilter.setUser(username);
            ruleFilter.setInstance(instanceName);
            ruleFilter.setService(service);
            ruleFilter.setRequest(request);
            ruleFilter.setWorkspace(info.getStore().getWorkspace().getName());
            ruleFilter.setLayer(info.getName());
            AccessInfo rule = rules.getAccessInfo(ruleFilter);
            
            // get the requested style name
            String styleName = styleNameList.size() > 0 ? styleNameList.get(i) : null;
            
            // if default use georepo default
            if(styleName == null && rule.getDefaultStyle() != null) {
                try {
                    StyleInfo si = catalog.getStyleByName(rule.getDefaultStyle());
                    if(si == null) {
                        throw new ServiceException("Could not find default style suggested " +
                        		"by GeoRepository: " + rule.getDefaultStyle());
                    }
                    Style style = si.getStyle();
                    getMap.getStyles().set(i, style);
                } catch (IOException e) {
                    throw new ServiceException("Unable to load the style suggested by GeoRepository: " 
                            + rule.getDefaultStyle(), e);
                }
            } else {
                checkStyleAllowed(rule, styleName);
            }
        }
    }

    private void checkStyleAllowed(AccessInfo rule, String styleName) {
        // otherwise check if the requested style is allowed 
        Set<String> allowedStyles = new HashSet<String>();
        if(rule.getDefaultStyle() != null) {
            allowedStyles.add(rule.getDefaultStyle());
        }
        if(rule.getAllowedStyles() != null) {
            allowedStyles.addAll(rule.getAllowedStyles());
        }
        
        if(allowedStyles.size() > 0 && !allowedStyles.contains(styleName)) {
            throw new ServiceException("The '" + styleName + "' style is not available on this layer");
        }
    }

    @Override
    public Object operationExecuted(Request request, Operation operation, Object result) {
        return result;
    }

    @Override
    public Response responseDispatched(Request request, Operation operation, Object result,
            Response response) {
        return response;
    }

    @Override
    public Service serviceDispatched(Request request, Service service) throws ServiceException {
        return service;
    }

}
