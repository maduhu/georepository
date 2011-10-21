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

import it.geosolutions.georepo.core.model.GRUser;
import it.geosolutions.georepo.core.model.GSInstance;
import it.geosolutions.georepo.core.model.GSUser;
import it.geosolutions.georepo.core.model.Profile;
import it.geosolutions.georepo.services.dto.ShortProfile;
import it.geosolutions.georepo.services.dto.ShortRule;
import it.geosolutions.georepo.services.dto.ShortUser;
import it.geosolutions.georepo.services.exception.NotFoundServiceEx;
import java.util.List;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class ServiceTestBase extends TestCase {

    protected final Logger LOGGER = Logger.getLogger(getClass());

    protected static UserAdminService userAdminService;
    protected static GRUserAdminService grUserAdminService;
    protected static ProfileAdminService profileAdminService;
    protected static InstanceAdminService instanceAdminService;
    protected static RuleAdminService ruleAdminService;
    protected static RuleReaderService ruleReaderService;

    protected static ClassPathXmlApplicationContext ctx = null;

    public ServiceTestBase() {
//        LOGGER = Logger.getLogger(getClass());

        synchronized(ServiceTestBase.class) {
            if(ctx == null) {
                String[] paths = {
                        "classpath*:applicationContext.xml"
//                         ,"applicationContext-test.xml"
                };
                ctx = new ClassPathXmlApplicationContext(paths);

                userAdminService     = (UserAdminService)ctx.getBean("userAdminService");
                grUserAdminService   = (GRUserAdminService)ctx.getBean("grUserAdminService");
                profileAdminService  = (ProfileAdminService)ctx.getBean("profileAdminService");
                instanceAdminService = (InstanceAdminService)ctx.getBean("instanceAdminService");
                ruleAdminService     = (RuleAdminService)ctx.getBean("ruleAdminService");
                ruleReaderService    = (RuleReaderService)ctx.getBean("ruleReaderService");
            }
        }
    }

    @Override
    protected void setUp() throws Exception {
        LOGGER.info("################ Running " + getClass().getSimpleName() + "::" + getName() );
        super.setUp();
        removeAll();
    }

    public void testCheckServices() {
        assertNotNull(userAdminService);
        assertNotNull(grUserAdminService);
        assertNotNull(profileAdminService);
        assertNotNull(instanceAdminService);
        assertNotNull(ruleAdminService);
    }

    protected void removeAll() throws NotFoundServiceEx {
        LOGGER.info("***** removeAll()");
        removeAllRules();
        removeAllUsers();
        removeAllGRUsers();
        removeAllProfiles();
        removeAllInstances();
    }

    protected void removeAllRules() throws NotFoundServiceEx {
        List<ShortRule> list = ruleAdminService.getAll();
        for (ShortRule item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = ruleAdminService.delete(item.getId());
            assertTrue("Rule not removed", ret);
        }

        assertEquals("Rules have not been properly deleted", 0, ruleAdminService.getCountAll());
    }

    protected void removeAllUsers() throws NotFoundServiceEx {
        List<ShortUser> list = userAdminService.getList(null,null,null);
        for (ShortUser item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = userAdminService.delete(item.getId());
            assertTrue("User not removed", ret);
        }

        assertEquals("Users have not been properly deleted", 0, userAdminService.getCount(null));
    }

    protected void removeAllGRUsers() throws NotFoundServiceEx {
        List<ShortUser> list = grUserAdminService.getList(null,null,null);
        for (ShortUser item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = grUserAdminService.delete(item.getId());
            assertTrue("User not removed", ret);
        }

        assertEquals("GRUsers have not been properly deleted", 0, grUserAdminService.getCount(null));
    }

    protected void removeAllProfiles() throws NotFoundServiceEx {
        List<ShortProfile> list = profileAdminService.getList(null,null,null);
        for (ShortProfile item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = profileAdminService.delete(item.getId());
            assertTrue("Profile not removed", ret);
        }

        assertEquals("Profiles have not been properly deleted", 0, profileAdminService.getCount(null));
    }

    protected void removeAllInstances() throws NotFoundServiceEx {
        List<GSInstance> list = instanceAdminService.getAll();
        for (GSInstance item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = instanceAdminService.delete(item.getId());
            assertTrue("GSInstance not removed", ret);
        }

        assertEquals("Instances have not been properly deleted", 0, instanceAdminService.getCount(null));
    }

    protected GSUser createUser(String base, Profile profile) {

        GSUser user = new GSUser();
        user.setName( base );
        user.setProfile(profile);
        userAdminService.insert(user);
        return user;
    }

    protected GRUser createUser(String base) {

        GRUser user = new GRUser();
        user.setName( base );
        grUserAdminService.insert(user);
        return user;
    }

    protected Profile createProfile(String base) {

        ShortProfile profile = new ShortProfile();
        profile.setName(base);
        long id = profileAdminService.insert(profile);
        try {
            return profileAdminService.get(id);
        } catch (NotFoundServiceEx ex) {
            throw new RuntimeException("Should never happen ("+id+")", ex);
        }
    }

    protected GSUser createUserAndProfile(String base) {

        Profile profile = createProfile(base);
        return createUser(base, profile);
    }

}
