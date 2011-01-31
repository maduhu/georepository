/*
 *  Copyright (C) 2007 - 2010 GeoSolutions S.A.S.
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

package it.geosolutions.georepo.servicetest;

import it.geosolutions.georepo.core.model.GSInstance;
import it.geosolutions.georepo.core.model.GSUser;
import it.geosolutions.georepo.core.model.LayerAttribute;
import it.geosolutions.georepo.core.model.LayerDetails;
import it.geosolutions.georepo.core.model.Profile;
import it.geosolutions.georepo.core.model.Rule;
import it.geosolutions.georepo.core.model.enums.AccessType;
import it.geosolutions.georepo.core.model.enums.GrantType;
import it.geosolutions.georepo.services.InstanceAdminService;
import it.geosolutions.georepo.services.ProfileAdminService;
import it.geosolutions.georepo.services.RuleAdminService;
import it.geosolutions.georepo.services.UserAdminService;
import it.geosolutions.georepo.services.dto.ShortProfile;
import it.geosolutions.georepo.services.dto.ShortRule;
import it.geosolutions.georepo.services.dto.ShortUser;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class MainTest implements InitializingBean {

    private final static Logger LOGGER = Logger.getLogger(MainTest.class);

    private UserAdminService userAdminService;
    private ProfileAdminService profileAdminService;
    private InstanceAdminService instanceAdminService;
    private RuleAdminService ruleAdminService;

    protected final static String MULTIPOLYGONWKT = "MULTIPOLYGON(((48.6894038 62.33877482, 48.7014874 62.33877482, 48.7014874 62.33968662, 48.6894038 62.33968662, 48.6894038 62.33877482)))";

    public void afterPropertiesSet() throws Exception {
        LOGGER.info("===== RESETTING DB DATA =====");
        removeAll();
        
        LOGGER.info("===== Creating Profiles =====");
        ShortProfile shortProfile = new ShortProfile();
        shortProfile.setName("profile1");
        long pid1 = profileAdminService.insert(shortProfile);
        Profile p1 = profileAdminService.get(pid1);

        ShortProfile shortProfile2 = new ShortProfile();
        shortProfile2.setName("profile2");
        long pid2 = profileAdminService.insert(shortProfile2);
        Profile p2 = profileAdminService.get(pid2);


        LOGGER.info("===== Creating Users =====");
        GSUser u1 = createUser("user1");
        userAdminService.insert(u1);

        GSUser u2 = createUser("user2");
        userAdminService.insert(u2);

        LOGGER.info("===== Creating Rules =====");

        LayerDetails ld1 = new LayerDetails();
        ld1.getAllowedStyles().add("style1");
        ld1.getAllowedStyles().add("style2");
        ld1.getAttributes().add(new LayerAttribute("attr1", AccessType.NONE));
        ld1.getAttributes().add(new LayerAttribute("attr2", AccessType.READONLY));
        ld1.getAttributes().add(new LayerAttribute("attr3", AccessType.READWRITE));

        int pri = -1;
        Rule rules[] = new Rule[100];



        pri++; rules[pri] = new Rule(pri, null, null, null,   "WCS", null, null, null, GrantType.ALLOW);
        pri++; rules[pri] = new Rule(pri, null, null, null,   "s1", "r2", "w2", "l2", GrantType.ALLOW);
        pri++; rules[pri] = new Rule(pri, null, null, null,   "s3", "r3", "w3", "l3", GrantType.ALLOW);
        pri++; rules[pri] = new Rule(pri, null, null, null,    null, null, null, null, GrantType.DENY);

        for (Rule rule : rules) {
            if(rule != null)
                ruleAdminService.insert(rule);
        }


        try {
            LOGGER.info("===== User List =====");

            List<ShortUser> users = userAdminService.getAll();
            for (ShortUser loop : users) {
                System.out.println("User -> " + loop);
            }



        } finally {
        }
    }

    //==========================================================================

    protected GSUser createUser(String baseName) {
        GSUser user = new GSUser();
        user.setName(baseName);
        return user;
    }

    //==========================================================================

    protected void removeAll() throws ResourceNotFoundFault {
        LOGGER.info("***** removeAll()");
        removeAllRules();
        removeAllUsers();
        removeAllProfiles();
        removeAllInstances();
    }

    protected void removeAllRules() throws ResourceNotFoundFault {
        List<ShortRule> list = ruleAdminService.getAll();
        for (ShortRule item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = ruleAdminService.delete(item.getId());
            if(!ret)
                throw new IllegalStateException("Rule not removed");
        }

        if( ruleAdminService.getCountAll() != 0)
                throw new IllegalStateException("Rules have not been properly deleted");
    }

    protected void removeAllUsers() throws ResourceNotFoundFault {
        List<ShortUser> list = userAdminService.getAll();
        for (ShortUser item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = userAdminService.delete(item.getId());
            if(!ret)
                throw new IllegalStateException("User not removed");
        }

        if( userAdminService.getCount(null) != 0)
                throw new IllegalStateException("Users have not been properly deleted");
    }

    protected void removeAllProfiles() throws ResourceNotFoundFault {
        List<ShortProfile> list = profileAdminService.getAll();
        for (ShortProfile item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = profileAdminService.delete(item.getId());
            if(!ret)
                throw new IllegalStateException("Profile not removed");
        }

        if( profileAdminService.getCount(null) != 0)
                throw new IllegalStateException("Profiles have not been properly deleted");
    }

    protected void removeAllInstances() throws ResourceNotFoundFault {
        List<GSInstance> list = instanceAdminService.getAll();
        for (GSInstance item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = instanceAdminService.delete(item.getId());
            if(!ret)
                throw new IllegalStateException("GSInstance not removed");

        }

        if( instanceAdminService.getCount(null) != 0)
                throw new IllegalStateException("Instances have not been properly deleted");
    }

    //==========================================================================
    
    public void setInstanceAdminService(InstanceAdminService instanceAdminService) {
        this.instanceAdminService = instanceAdminService;
    }

    public void setProfileAdminService(ProfileAdminService profileAdminService) {
        this.profileAdminService = profileAdminService;
    }

    public void setRuleAdminService(RuleAdminService ruleAdminService) {
        this.ruleAdminService = ruleAdminService;
    }

    public void setUserAdminService(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

}
