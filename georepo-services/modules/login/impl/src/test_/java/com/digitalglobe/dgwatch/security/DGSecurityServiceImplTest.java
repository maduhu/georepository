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

package it.geosolutions.georepo.security;

import it.geosolutions.georepo.internal.DGInternalService;
import it.geosolutions.georepo.security.response.GrantedAuthorities;
import it.geosolutions.georepo.security.response.RegisteredUser;
import java.util.List;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class DGSecurityServiceImplTest extends TestCase {

    protected final static Logger LOGGER = Logger.getLogger(DGSecurityServiceImplTest.class);

    @Autowired
    protected DGSecurityService securityService;
    protected DGInternalService internalService;
    protected static ClassPathXmlApplicationContext ctx = null;

    public DGSecurityServiceImplTest() {
        synchronized(DGSecurityServiceImplTest.class) {
            if(ctx == null) {
                String[] paths = {"classpath*:applicationContext.xml"};
                ctx = new ClassPathXmlApplicationContext(paths);

                securityService = (DGSecurityService)ctx.getBean("securityService");
                internalService = (DGInternalService)ctx.getBean("internalService");
            }
        }
    }

    @Override
    protected void setUp() throws Exception {
        LOGGER.info("---------- Running " + getClass().getSimpleName() + "::" + getName() );
        super.setUp();
    }


    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }


    public void _testLoginIT() {
        assertNotNull(securityService);
        assertNotNull(internalService);

        GrantedAuthorities ga = securityService.login("dibu_admin", "dibu_admin");
        assertNotNull(ga);

        List<RegisteredUser> users = internalService.getUsers(ga.getToken());
        System.out.println("GOT " + users.size() + " users ");
        for (RegisteredUser user : users) {
            System.out.println("USER " + user + " ORG " + user.getOrganizationName());
        }

    }

    public void testLogout() {
    }

    public void testGetUsers() {
    }


    //==========================================================================

    public void setSecurityService(DGSecurityService securityService) {
        this.securityService = securityService;
    }

}