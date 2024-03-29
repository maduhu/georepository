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

package it.geosolutions.georepo.core.dao;

import it.geosolutions.georepo.core.model.GRUser;

import org.junit.Test;

/**
 * 
 * @author ETj (etj at geo-solutions.it)
 */
public class GRUserDAOTest extends BaseDAOTest {

    @Test
    public void testPersistUser() throws Exception {

        removeAllGRUsers();

        long id;
        {
            GRUser user = new GRUser();
            user.setName(getName());
            grUserDAO.persist(user);
            id = user.getId();
        }

        // test save & load
        {
            GRUser loaded = grUserDAO.find(id);
            assertNotNull("Can't retrieve user", loaded);
        }

        grUserDAO.removeById(id);
        assertNull("User not deleted", grUserDAO.find(id));
    }

}