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

package it.geosolutions.georepo.services;

import it.geosolutions.georepo.core.model.GSInstance;
import it.geosolutions.georepo.services.dto.ShortRule;
import it.geosolutions.georepo.services.exception.BadRequestWebEx;
import it.geosolutions.georepo.services.exception.NotFoundWebEx;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;
import it.geosolutions.georepo.services.webgis.model.TOC;
import it.geosolutions.georepo.services.webgis.WebGisTOCService;
import it.geosolutions.georepo.services.webgis.model.TOCConfig;
import it.geosolutions.georepo.services.webgis.model.TOCGroup;
import it.geosolutions.georepo.services.webgis.model.TOCLayer;
import it.geosolutions.georepo.services.webgis.model.WebGisProfile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlElementWrapper;
import org.apache.log4j.Logger;

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

        try {
            WebGisProfile wgp = WebGisProfile.valueOf(profile);
        } catch (IllegalArgumentException e) {
            throw new NotFoundWebEx("Profile not found: " + profile);
        }

        // groupTitle, Group
        Map<String, TOCGroup> groups = new HashMap<String, TOCGroup>();
        Map<Long, GSInstance> instances = new HashMap<Long, GSInstance>();

        List<ShortRule> rules = ruleAdminService.getList("*", profile, "*", "*", "*", "*", "*", null, null);
        for (ShortRule shortRule : rules) {
            if(shortRule.getLayer() != null) {
                LOGGER.info("retrieving props for Rule " + shortRule);
                Map<String, String> props = ruleAdminService.getDetailsProps(shortRule.getId());

                String groupTitle = props.get(TOCLayer.TOCProps.groupName.name());
                if(groupTitle==null)
                    groupTitle = "UNGROUPED";
                GSInstance gs = fetchInstance(instances, shortRule.getInstanceId());
                TOCGroup group = fetchOrCreateGroup(groups, groupTitle, gs);
                TOCLayer tocl = createLayer(gs, shortRule, props);
                group.getLayerList().add(tocl);
            }
        }

        TOC toc = new TOC();
        for (TOCGroup group : groups.values()) {
            toc.getGroupList().add(group);
        }
        return new TOCConfig(toc);
    }

    private GSInstance fetchInstance(Map<Long, GSInstance> instances, Long instanceId) {
        try {
            if (instances.containsKey(instanceId)) {
                return instances.get(instanceId);
            }
            GSInstance instance = instanceAdminService.get(instanceId);
            instances.put(instanceId, instance);
            return instance;
        } catch (ResourceNotFoundFault ex) {
            LOGGER.error(ex.getMessage(), ex);
            return null;
        }
    }

    private static TOCGroup fetchOrCreateGroup(Map<String, TOCGroup> groups, String groupTitle, GSInstance instance) {
        
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


    TOCLayer createLayer(GSInstance instance, ShortRule rule, Map<String,String> props) {
        TOCLayer l = new TOCLayer();
        l.setName(rule.getLayer());
        l.setGeorepoId(rule.getId());
        for (Map.Entry<String, String> entry : props.entrySet()) {
            l.getProperties().put(entry.getKey(), entry.getValue());
        }

        // next info shall be read from the server's getCapa
        l.setTitle("TITLE of " + rule.getLayer());
        l.setAbs("ABS of " + rule.getLayer());
        l.setMinX(-180);
        l.setMaxX(180);
        l.setMinY(-90);
        l.setMaxY(90);
        l.setSrs("EPSG:4326");

        return l;
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
