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

package it.geosolutions.georepo.core.dao;

import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import it.geosolutions.georepo.core.model.User;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public abstract class BaseDAOTest extends TestCase {
    protected final Logger LOGGER;

    protected static UserDAO userDAO;
    protected static ServiceFilterDAO filterDAO;

    protected static ClassPathXmlApplicationContext ctx = null;

    public BaseDAOTest() {
        LOGGER = Logger.getLogger(getClass());

        synchronized(BaseDAOTest.class) {
            if(ctx == null) {
                String[] paths = {
                        "applicationContext.xml"
//                         ,"applicationContext-test.xml"
                };
                ctx = new ClassPathXmlApplicationContext(paths);

                userDAO = (UserDAO)ctx.getBean("userDAO");
                filterDAO = (ServiceFilterDAO)ctx.getBean("filterDAO");
            }
        }
    }

    @Override
    protected void setUp() throws Exception {
        LOGGER.info("################ Running " + getClass().getSimpleName() + "::" + getName() );
        super.setUp();
    }

    @Test
    public void testCheckDAOs() {

        assertNotNull(userDAO);
    }

    protected void removeAll() {
        removeAllUsers(); // reference AOI
    }

    protected void removeAllUsers() {
        List<User> list = userDAO.findAll();
        for (User item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = userDAO.remove(item);
            assertTrue("User not removed", ret);
        }

        assertEquals("Users have not been properly deleted", 0, userDAO.count(null));
    }

    protected User createUser(String base) {
        User user = new User();
        user.setName( base );
        return user;
    }


//    protected final static String MULTIPOLYGONWKT = "MULTIPOLYGON(((48.6894038 62.33877482, 48.7014874 62.33877482, 48.7014874 62.33968662, 48.6894038 62.33968662, 48.6894038 62.33877482)))";
//    protected final static String POLYGONWKT = "POLYGON((48.6894038 62.33877482, 48.7014874 62.33877482, 48.7014874 62.33968662, 48.6894038 62.33968662, 48.6894038 62.33877482))";
//
//    protected MultiPolygon buildMultiPolygon() throws ParseException {
//        WKTReader reader = new WKTReader();
//        MultiPolygon mp = (MultiPolygon) reader.read(MULTIPOLYGONWKT);
//        mp.setSRID(4326);
//
//        return mp;
//    }
//
//    protected Polygon buildPolygon() throws ParseException {
//        WKTReader reader = new WKTReader();
//        Polygon mp = (Polygon) reader.read(POLYGONWKT);
//        mp.setSRID(4326);
//
//        return mp;
//    }


    //==========================================================================

}
