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

import it.geosolutions.georepo.core.model.*;

import org.junit.Test;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class UserDAOTest extends BaseDAOTest {


    @Test
    public void testPersistUser() throws Exception {

        removeAll();
        User user = createUser(getName());
        userDAO.persist(user);

        // test save & load
        User loaded = userDAO.find(user.getId());
        assertNotNull("Can't retrieve user", loaded);

        userDAO.remove(user);
        assertNull("User not deleted", userDAO.find(user.getId()));
    }


}