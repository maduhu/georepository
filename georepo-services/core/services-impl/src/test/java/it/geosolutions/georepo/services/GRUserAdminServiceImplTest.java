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

import it.geosolutions.georepo.services.dto.ShortUser;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import it.geosolutions.georepo.core.model.GRUser;
import it.geosolutions.georepo.services.exception.NotFoundServiceEx;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class GRUserAdminServiceImplTest extends ServiceTestBase {

    public GRUserAdminServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testInsertDeleteUser() throws NotFoundServiceEx {

        GRUser user = createUser(getName());
        grUserAdminService.get(user.getId()); // will throw if not found
        assertTrue("Could not delete user", grUserAdminService.delete(user.getId()));
    }

    @Test
    public void testUpdateUser() throws Exception {
        GRUser user = createUser("u1");

        final String NEWNAME = "NEWNAME";

        {
            GRUser loaded = grUserAdminService.get(user.getId());
            assertNotNull(loaded);

            assertEquals("u1", loaded.getName());

            loaded.setName(NEWNAME);

            grUserAdminService.update(loaded);
        }
        {
            GRUser loaded = grUserAdminService.get(user.getId());
            assertNotNull(loaded);

            assertEquals(NEWNAME, loaded.getName());
        }
    }

    @Test
    public void testGetAllUsers() {
        assertEquals(0, grUserAdminService.getList(null,null,null).size());

        createUser("u1");
        createUser("u2");
        createUser("u3");

        assertEquals(3, grUserAdminService.getList(null,null,null).size());
    }

    @Test
    public void testGetUsersCount() {
        assertEquals(0, grUserAdminService.getCount(null));

        createUser("u10");
        createUser("u20");
        createUser("u30");
        GRUser u99 = createUser("u99");

        assertEquals(4, grUserAdminService.getCount(null));
        assertEquals(4, grUserAdminService.getCount("u%"));
        assertEquals(3, grUserAdminService.getCount("%0"));

        List<ShortUser> users = grUserAdminService.getList("%9",null,null);
        assertEquals(1, users.size());
        assertEquals("u99", users.get(0).getName());
        assertEquals((Long)u99.getId(), (Long)users.get(0).getId());
    }

}
