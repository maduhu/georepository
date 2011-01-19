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

import it.geosolutions.georepo.core.model.Profile;
import java.util.Map;

import org.junit.Test;

/**
 * 
 * @author ETj (etj at geo-solutions.it)
 */
public class ProfileDAOTest extends BaseDAOTest {

    @Test
    public void testPersist() throws Exception {

        removeAll();

        long id;
        {
            Profile profile = createProfile(getName());
            id = profile.getId();
        }

        // test save & load
        {
            Profile loaded = profileDAO.find(id);
            assertNotNull("Can't retrieve profile", loaded);
        }

        {
            Map<String, String> props = profileDAO.getCustomProps(id);
            assertTrue(props.isEmpty());
            props.put("k1", "v1");
            props.put("k2", "v2");
            profileDAO.setCustomProps(id, props);
        }

        {
            Map<String, String> props = profileDAO.getCustomProps(id);
            assertFalse(props.isEmpty());
            assertEquals(2, props.size());
            assertEquals("v1", props.remove("k1"));
            profileDAO.setCustomProps(id, props);
        }
        {
            Map<String, String> props = profileDAO.getCustomProps(id);
            assertEquals(1, props.size());
            assertEquals("v2", props.remove("k2"));
            profileDAO.setCustomProps(id, props);
        }
        {
            Map<String, String> props = profileDAO.getCustomProps(id);
            assertTrue(props.isEmpty());
        }

        profileDAO.removeById(id);
        assertNull("User not deleted", profileDAO.find(id));
    }

}