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

import it.geosolutions.georepo.core.model.GSUser;
import it.geosolutions.georepo.services.InstanceAdminService;
import it.geosolutions.georepo.services.ProfileAdminService;
import it.geosolutions.georepo.services.RuleAdminService;
import it.geosolutions.georepo.services.UserAdminService;
import it.geosolutions.georepo.services.dto.ShortUser;
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
        LOGGER.info("===== Starting tests =====");

        LOGGER.info("===== Removing old users =====");

        for (ShortUser shortUser : userAdminService.getAll()) {
            System.out.println("Removing user -> " + shortUser);
            userAdminService.delete(shortUser.getId());
        }
        
//        LOGGER.info("===== Creating Users =====");
//        Profile profile = new Profile();
//        profile.setName("profile1");
//
//
//        GSUser u1 = createUser("user1");
//        userAdmin.insert(u1);
//
//        GSUser u2 = createUser("user2");
//        userAdmin.insert(u2);

        try {
            LOGGER.info("===== User List =====");

            List<ShortUser> users = userAdminService.getAll();
            for (ShortUser loop : users) {
                System.out.println("User -> " + loop);
            }

        } finally {
//            watchAdmin.deleteWatch(nw.getId());
//            watchAdmin.deleteWatch(dw.getId());
//            aoiAdmin.deleteAoi(aoiId);
        }
    }

    //==========================================================================

    protected GSUser createUser(String baseName) {
        GSUser user = new GSUser();
        user.setName(baseName);
        return user;
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
