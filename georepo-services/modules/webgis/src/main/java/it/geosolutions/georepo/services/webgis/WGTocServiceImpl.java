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

package it.geosolutions.georepo.services.webgis;

import it.geosolutions.georepo.core.model.GSInstance;
import it.geosolutions.georepo.core.model.LayerAttribute;
import it.geosolutions.georepo.core.model.LayerDetails;
import it.geosolutions.georepo.core.model.enums.AccessType;
import it.geosolutions.georepo.core.model.enums.GrantType;
import it.geosolutions.georepo.services.InstanceAdminService;
import it.geosolutions.georepo.services.RuleAdminService;
import it.geosolutions.georepo.services.dto.RuleFilter;
import it.geosolutions.georepo.services.dto.ShortRule;
import it.geosolutions.georepo.services.exception.BadRequestWebEx;
import it.geosolutions.georepo.services.exception.NotFoundWebEx;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;
import it.geosolutions.georepo.services.webgis.model.TOCAttrib;
import it.geosolutions.georepo.services.webgis.model.TOCConfig;
import it.geosolutions.georepo.services.webgis.model.TOCGroup;
import it.geosolutions.georepo.services.webgis.model.TOCLayer;
import it.geosolutions.georepo.services.webgis.utils.DenominatorStyleVisitor;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTLayer;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlElementWrapper;
import org.apache.log4j.Logger;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.styling.SLDParser;
import org.geotools.styling.StyledLayerDescriptor;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class WGTocServiceImpl implements WebGisTOCService {
    private final static Logger LOGGER = Logger.getLogger(WGTocServiceImpl.class);

    private RuleAdminService ruleAdminService;
    private InstanceAdminService instanceAdminService;

    public WGTocServiceImpl() {
        FakeServices fakeServices = new FakeServices();
        ruleAdminService = fakeServices.getRuleAdminService();
        instanceAdminService = fakeServices.getInstanceAdminService();
    }

    @Override
    @XmlElementWrapper(name="pippo")
    public TOCConfig getTOC(String profile) {

        if(profile == null)
            throw new BadRequestWebEx("Missing Profile");

        // groupTitle, Group
        Map<String, TOCGroup> tocGroups = new HashMap<String, TOCGroup>();
        Map<String, TOCGroup> bgGroups = new HashMap<String, TOCGroup>();
        Map<Long, GSInstance> instances = new HashMap<Long, GSInstance>();
        Map<Long, GeoServerRESTReader> readers = new HashMap<Long, GeoServerRESTReader>();

        RuleFilter ruleFilter = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
        ruleFilter.setProfile(profile);
        
        List<ShortRule> rules = ruleAdminService.getList(ruleFilter, null, null);
        for (ShortRule shortRule : rules) {
            if(shortRule.getLayer() != null && shortRule.getAccess()==GrantType.ALLOW) {
                LOGGER.info("retrieving props for Rule " + shortRule);
                Map<String, String> props = ruleAdminService.getDetailsProps(shortRule.getId());

                GSInstance gs = fetchInstance(instances, readers, shortRule.getInstanceId());
                GeoServerRESTReader reader = readers.get(shortRule.getInstanceId());

                TOCGroup bg = fetchOrCreateGroup(bgGroups, props.get(TOCLayer.TOCProps.bgGroup.name()), gs);
                String groupTitle = props.get(TOCLayer.TOCProps.groupName.name());

                if( groupTitle==null && bg==null) // not in bg, and group not set: maybe been forgotten?
                    groupTitle = "UNGROUPED";

                TOCGroup group = fetchOrCreateGroup(tocGroups, groupTitle, gs);
                TOCLayer tocl = createLayer(gs, reader, shortRule, props);
                if(tocl == null) // error condition, already logged
                    continue;

                if(group != null)
                    group.getLayerList().add(tocl);
                if(bg != null)
                    bg.getLayerList().add(tocl);
            }
        }

        TOCConfig tocCfg = new TOCConfig();
        for (TOCGroup group : tocGroups.values()) {
            tocCfg.getTOC().add(group);
        }
        for (TOCGroup bggroup : bgGroups.values()) {
            tocCfg.getBackground().add(bggroup);
        }

        return tocCfg;
    }

    private GSInstance fetchInstance(Map<Long, GSInstance> instances, 
                    Map<Long, GeoServerRESTReader> readers,
                    Long instanceId) {
        try {
            if (instances.containsKey(instanceId)) {
                return instances.get(instanceId);
            }

            GSInstance instance = instanceAdminService.get(instanceId);
            instances.put(instanceId, instance);

            try {
                GeoServerRESTReader gsreader = new GeoServerRESTReader(
                        instance.getBaseURL(),
                        instance.getUsername(),
                        instance.getPassword());

                readers.put(instanceId, gsreader);
            } catch (MalformedURLException ex) {
                LOGGER.error("Can't init a reader for " + instance, ex);
            }

            return instance;
        } catch (ResourceNotFoundFault ex) {
            LOGGER.error(ex.getMessage(), ex);
            return null;
        }
    }

    private static TOCGroup fetchOrCreateGroup(Map<String, TOCGroup> groups, String groupTitle, GSInstance instance) {

        if(groupTitle == null)
            return null;

        String groupKey = groupTitle+" @ "+instance.getName();

        TOCGroup group = groups.get(groupKey);
        if(group == null) {
            group = new TOCGroup();
            group.setTitle(groupTitle);
            group.setUrl(instance.getBaseURL());
            groups.put(groupKey, group);
        }

        return group;
    }


    TOCLayer createLayer(GSInstance instance, GeoServerRESTReader gsreader, ShortRule rule, Map<String, String> props) {
        TOCLayer tocLayer = new TOCLayer();
        tocLayer.setName(rule.getLayer());
        tocLayer.setGeorepoId(rule.getId());

        boolean dataIsSet = false;
        String defaultStyle = null;

        if (gsreader != null) {

            try {
                RESTLayer rl = gsreader.getLayer(rule.getLayer());
                tocLayer.setTitle(rl.getTitle());
                tocLayer.setAbs(rl.getAbstract());
                tocLayer.setMinX(rl.getMinX());
                tocLayer.setMaxX(rl.getMaxX());
                tocLayer.setMinY(rl.getMinY());
                tocLayer.setMaxY(rl.getMaxY());
                tocLayer.setSrs(rl.getCRS());
                dataIsSet = true;

                // we'll use this one if we can't find any in LayerDetails
                defaultStyle = rl.getDefaultStyle();

            } catch (Exception ex) {
                LOGGER.error("Error reading data from GeoServer " + instance + " for layer " + rule.getLayer() + ": " + ex.getMessage());
            }

            try {
                LayerDetails details;
                try {
                    details = ruleAdminService.getDetails(rule.getId());
                } catch (ResourceNotFoundFault ex) {
                    LOGGER.error("Details not found for " + rule + ". Skipping layer");
                    return null;
                }

                //=== extract min and max denominator
                String forcedStyle = details.getDefaultStyle();
                String sldBody = gsreader.getSLD(forcedStyle!=null? forcedStyle : defaultStyle);
                DenominatorStyleVisitor dsv = new DenominatorStyleVisitor();

                SLDParser sldparser = new SLDParser(CommonFactoryFinder.getStyleFactory(null), new StringReader(sldBody));
                StyledLayerDescriptor sld = sldparser.parseSLD();
                sld.accept(dsv);
                props.put(TOCLayer.TOCProps.minScale.name(), ""+dsv.getMin());
                props.put(TOCLayer.TOCProps.maxScale.name(), ""+dsv.getMax());

                //=== add attribs
                for (LayerAttribute attr : details.getAttributes()) {
                    if(attr.getAccess() != AccessType.NONE) {
                        TOCAttrib tocAttr = new TOCAttrib();
                        tocAttr.setName(attr.getName());
                        tocAttr.setDatatype(attr.getDatatype());
                        tocAttr.setWritable(attr.getAccess() == AccessType.READWRITE);

                        tocLayer.getAttributes().add(tocAttr);
                    }
                }

            } catch (Exception ex) {
                LOGGER.error("Error reading data from GeoServer " + instance + " for layer " + rule.getLayer() + ": " + ex.getMessage());
            }

        }

        if (!dataIsSet) {
            tocLayer.setTitle("TITLE of " + rule.getLayer());
            tocLayer.setAbs("ABSTRACT of " + rule.getLayer());
            tocLayer.setMinX(-180);
            tocLayer.setMaxX(180);
            tocLayer.setMinY(-90);
            tocLayer.setMaxY(90);
            tocLayer.setSrs("EPSG:4326");
        }

        for (Map.Entry<String, String> entry : props.entrySet()) {
            tocLayer.getProperties().put(entry.getKey(), entry.getValue());
        }

        return tocLayer;
    }

    //==========================================================================

    @Override
    public String setProperty(long layerId, String propertyName, String propertyValue) throws NotFoundWebEx {

        Map<String, String> props = ruleAdminService.getDetailsProps(layerId); // throws notfoundex

        if(props.containsKey(propertyName)) {
            LOGGER.info("Setting property " +propertyName + " to " + propertyValue + " for layer " + layerId + " in profile ???" );
            String oldValue = props.put(propertyName, propertyValue);
            ruleAdminService.setDetailsProps(layerId, props);
            return oldValue;
        } else {
            LOGGER.info("Will not set property " +propertyName + " to " + propertyValue + " for layer " + layerId + ": property not found");
            throw new NotFoundWebEx("Property "+propertyName+" not found");
        }
    }

    @Override
    public String getProperty(long layerId, String propertyName) throws NotFoundWebEx {
        Map<String, String> props = ruleAdminService.getDetailsProps(layerId); // throws notfoundex

        LOGGER.info("Getting property " +propertyName );
        return props.get(propertyName);
    }

    //==========================================================================

    public void setRuleAdminService(RuleAdminService ruleAdminService) {
        this.ruleAdminService = ruleAdminService;
    }

    public void setInstanceAdminService(InstanceAdminService instanceAdminService) {
        this.instanceAdminService = instanceAdminService;
    }


}
