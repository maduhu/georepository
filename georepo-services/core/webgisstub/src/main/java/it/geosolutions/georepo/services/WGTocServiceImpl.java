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
import it.geosolutions.georepo.core.model.InstancePermission;
import it.geosolutions.georepo.core.model.LayerPermission;
import it.geosolutions.georepo.services.exception.BadRequestWebEx;
import it.geosolutions.georepo.services.exception.NotFoundWebEx;
import it.geosolutions.georepo.services.webgis.model.TOC;
import it.geosolutions.georepo.services.webgis.WebGisTOCService;
import it.geosolutions.georepo.services.webgis.model.TOCLayer;
import it.geosolutions.georepo.services.webgis.model.WebGisProfile;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlElementWrapper;
import org.apache.log4j.Logger;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class WGTocServiceImpl implements WebGisTOCService {
    private final static Logger LOGGER = Logger.getLogger(WGTocServiceImpl.class);
    private FakeDataStorage fakeDAO = new FakeDataStorage();

    @Override
    @XmlElementWrapper(name="pippo")
    public TOC getTOC(String profile) {

        if(profile == null)
            throw new BadRequestWebEx("Missing Profile");

        try {
            WebGisProfile wgp = WebGisProfile.valueOf(profile);
        } catch (IllegalArgumentException e) {
            throw new NotFoundWebEx("Profile not found: " + profile);
        }

        // groupTitle, Group
        Map<String, TOC.Group> groups = new HashMap<String, TOC.Group>();

        for (InstancePermission instancePermission : fakeDAO.getInstancePermissions(profile)) {
            GSInstance instance = instancePermission.getInstance();
            for (LayerPermission layerPermission : fakeDAO.getLayerPermissions(instancePermission)) {
                String groupTitle = layerPermission.getCustomProps().get(TOCLayer.TOCProps.groupName.name());
                if(groupTitle==null)
                    groupTitle = "UNGROUPED";
                TOC.Group group = fetchOrCreateGroup(groups, groupTitle, instance.getBaseURL());
                TOCLayer tocl = createLayer(layerPermission, instance);
                group.getLayerList().add(tocl);
            }

        }

        TOC toc = new TOC();
        for (TOC.Group group : groups.values()) {
            toc.getGroupList().add(group);
        }
        return toc;
    }

    private static TOC.Group fetchOrCreateGroup(Map<String, TOC.Group> groups, String groupTitle, String url) {
        String groupKey = groupTitle+" @ "+url;

        TOC.Group group = groups.get(groupKey);
        if(group == null) {
            group = new TOC.Group();
            group.setTitle(groupTitle);
            group.setUrl(url);
            groups.put(groupKey, group);
        }

        return group;
    }

    TOCLayer createLayer(LayerPermission lp, GSInstance instance) {
        TOCLayer l = new TOCLayer();
        l.setName(lp.getLayerName());
        l.setGeorepoLayerId(lp.getId());
        for (Map.Entry<String, String> entry : lp.getCustomProps().entrySet()) {
            l.getProperties().put(entry.getKey(), entry.getValue());
        }

        // next info shall be read from the server's getCapa
        l.setTitle("TITLE of " + lp.getLayerName());
        l.setAbs("ABS of " + lp.getLayerName());
        l.setMinX(-180);
        l.setMaxX(180);
        l.setMinY(-90);
        l.setMaxY(90);
        l.setSrs("EPSG:4326");

        return l;
    }

    @Override
    public String setProperty(long layerId, String propertyName, String propertyValue) {

        LayerPermission lp = fakeDAO.getLayerPermission(layerId);

        if(lp == null)
            throw new NotFoundWebEx("Layer "+layerId+" not found");

        if(lp.getCustomProps().containsKey(propertyName)) {
            LOGGER.info("Setting property " +propertyName + " to " + propertyValue + " for layer " + layerId + " in profile ???" );
            String oldValue = lp.getCustomProps().put(propertyName, propertyValue);
            return oldValue;
        } else {
            LOGGER.info("Will not set property " +propertyName + " to " + propertyValue + " for layer " + layerId + ": property not found");
            throw new NotFoundWebEx("Property "+propertyName+" not found");
        }
    }

    @Override
    public String getProperty(long layerId, String propertyName) {
        LayerPermission lp = fakeDAO.getLayerPermission(layerId);

        if(lp == null)
            throw new NotFoundWebEx("Layer "+layerId+" not found");

        LOGGER.info("Getting property " +propertyName );
        return lp.getCustomProps().get(propertyName);
    }
    
}
