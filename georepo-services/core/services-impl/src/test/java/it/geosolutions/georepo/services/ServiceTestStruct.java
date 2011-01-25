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

import it.geosolutions.georepo.core.model.GSUser;
import it.geosolutions.georepo.core.model.Profile;
import it.geosolutions.georepo.services.dto.ShortProfile;
import it.geosolutions.georepo.services.dto.ShortUser;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;
import java.util.List;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class ServiceTestStruct extends TestCase {

    protected final Logger LOGGER = Logger.getLogger(getClass());

    protected static UserAdminService userAdminService;
    protected static ProfileAdminService profileAdminService;
    protected static InstanceAdminService instanceAdminService;
    protected static RuleAdminService ruleAdminService;

    protected static ClassPathXmlApplicationContext ctx = null;

    public ServiceTestStruct() {
//        LOGGER = Logger.getLogger(getClass());

        synchronized(ServiceTestStruct.class) {
            if(ctx == null) {
                String[] paths = {
                        "applicationContext.xml"
//                         ,"applicationContext-test.xml"
                };
                ctx = new ClassPathXmlApplicationContext(paths);

                userAdminService     = (UserAdminService)ctx.getBean("userAdminService");
                profileAdminService  = (ProfileAdminService)ctx.getBean("profileAdminService");
                instanceAdminService = (InstanceAdminService)ctx.getBean("instanceAdminService");
                ruleAdminService     = (RuleAdminService)ctx.getBean("ruleAdminService");
            }
        }
    }

    @Override
    protected void setUp() throws Exception {
        LOGGER.info("################ Running " + getClass().getSimpleName() + "::" + getName() );
        super.setUp();
    }

    public void testCheckServices() {
        assertNotNull(userAdminService);
        assertNotNull(profileAdminService);
        assertNotNull(instanceAdminService);
        assertNotNull(ruleAdminService);
    }

    protected void removeAll() throws ResourceNotFoundFault {
        removeAllUsers();
        removeAllProfiles();
    }

    protected void removeAllUsers() throws ResourceNotFoundFault {
        List<ShortUser> list = userAdminService.getAll();
        for (ShortUser item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = userAdminService.delete(item.getId());
            assertTrue("User not removed", ret);
        }

        assertEquals("Users have not been properly deleted", 0, userAdminService.getCount(null));
    }

    protected void removeAllProfiles() throws ResourceNotFoundFault {
        List<ShortProfile> list = profileAdminService.getAll();
        for (ShortProfile item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = profileAdminService.delete(item.getId());
            assertTrue("Profile not removed", ret);
        }

        assertEquals("Profiles have not been properly deleted", 0, profileAdminService.getCount(null));
    }

    protected GSUser createUser(String base, Profile profile) {

        GSUser user = new GSUser();
        user.setName( base );
        user.setProfile(profile);
        return user;
    }

    protected Profile createProfile(String base) throws ResourceNotFoundFault {

        ShortProfile profile = new ShortProfile();
        profile.setName(base);
        long id = profileAdminService.insert(profile);
        return profileAdminService.get(id);
    }

    protected GSUser createUserAndProfile(String base) throws ResourceNotFoundFault {

        Profile profile = createProfile(base);
        return createUser(base, profile);
    }

}
