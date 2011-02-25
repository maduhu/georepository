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

package it.geosolutions.georepo.services.webgis.utils;

import it.geosolutions.georepo.core.dao.GSInstanceDAO;
import it.geosolutions.georepo.core.dao.LayerDetailsDAO;
import it.geosolutions.georepo.core.dao.ProfileDAO;
import it.geosolutions.georepo.core.dao.RuleDAO;
import it.geosolutions.georepo.core.model.GSInstance;
import it.geosolutions.georepo.core.model.LayerAttribute;
import it.geosolutions.georepo.core.model.LayerDetails;
import it.geosolutions.georepo.core.model.Profile;
import it.geosolutions.georepo.core.model.Rule;
import it.geosolutions.georepo.core.model.enums.AccessType;
import it.geosolutions.georepo.core.model.enums.GrantType;
import it.geosolutions.georepo.core.model.enums.LayerType;
import it.geosolutions.georepo.services.webgis.model.TOCLayer;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class WGProfilesInitializer implements InitializingBean {
    private final static Logger LOGGER = Logger.getLogger(WGProfilesInitializer.class);

    private ProfileDAO profileDAO;
    private GSInstanceDAO instanceDAO;
    private RuleDAO ruleDAO;
    private LayerDetailsDAO detailsDAO;

    @Override
    public void afterPropertiesSet() throws Exception {
        int cnt = profileDAO.count(null);
        if(cnt > 0) {
            LOGGER.info("There are " + cnt + " profiles in the DB");
            return;
        }
        LOGGER.info("No profiles found. Creating default profiles...");

        Profile baseP = createBaseProfile();
        {
            Profile profile = new Profile();
            profile.setName("Avanzato");
            profileDAO.persist(profile);
            Map<String, String> props = new HashMap<String, String>();
            props.put("XXXdoubleMap", "true");
            props.put("XXXtripleMap", "false");
            profileDAO.setCustomProps(profile.getId(), props);
        }
        {
            Profile profile = new Profile();
            profile.setName("Analisi");
            profileDAO.persist(profile);
            Map<String, String> props = new HashMap<String, String>();
            props.put("QQQdoubleMap", "true");
            props.put("QQQtripleMap", "false");
            profileDAO.setCustomProps(profile.getId(), props);
        }

        GSInstance instance = new GSInstance();
        instance.setBaseURL("http://demo.geo-solutions.it/geoserver/");
        instance.setName("GS@demo");
        instance.setUsername("admin");
        instance.setPassword("geoserver");

        instanceDAO.persist(instance);


        Rule rule = new Rule(1000, null, baseP, instance, null, null, null, "missions", GrantType.ALLOW);
        ruleDAO.persist(rule);

        LayerDetails ld = new LayerDetails();
        ld.setRule(rule);
        ld.setType(LayerType.VECTOR);
        ld.getCustomProps().put(TOCLayer.TOCProps.groupName.name(), "grp1");
        ld.getCustomProps().put(TOCLayer.TOCProps.singleTile.name(), "true");
        ld.getCustomProps().put("p2", "v2");
        ld.getAttributes().add(new LayerAttribute("a1", "String", AccessType.NONE));
        ld.getAttributes().add(new LayerAttribute("a2", "String", AccessType.READONLY));
        ld.getAttributes().add(new LayerAttribute("a3", "String", AccessType.READWRITE));

        detailsDAO.persist(ld);
    }

    protected Profile createBaseProfile() {
        Profile profile = new Profile();
        profile.setName("Base");
        profileDAO.persist(profile);
        Map<String, String> props = new HashMap<String, String>();
        props.put("doubleMap", "true");
        props.put("tripleMap", "false");
        profileDAO.setCustomProps(profile.getId(), props);
        return profile;
    }

    //==========================================================================

    public void setProfileDAO(ProfileDAO dao) {
        LOGGER.info("Setting DAO " + dao);
        this.profileDAO = dao;
    }

    public void setInstanceDAO(GSInstanceDAO dao) {
        LOGGER.info("Setting DAO " + dao);
        this.instanceDAO = dao;
    }

    public void setRuleDAO(RuleDAO dao) {
        LOGGER.info("Setting DAO " + dao);
        this.ruleDAO = dao;
    }

    public void setLayerdetailsDAO(LayerDetailsDAO dao) {
        LOGGER.info("Setting DAO " + dao);
        this.detailsDAO = dao;
    }

}
