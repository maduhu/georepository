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

import it.geosolutions.georepo.core.model.ServiceFilter;
import it.geosolutions.georepo.core.model.util.FilterValueEncoder;

import org.junit.Test;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class FilterDAOTest extends BaseDAOTest {


    @Test
    public void testPersistFilter() throws Exception {

        removeAll();

        ServiceFilter filter = new ServiceFilter();
        filter.setService("TEST");
        filter.setProperty("testProp");

        FilterValueEncoder fve = new FilterValueEncoder();
        fve.add("testString");
        fve.add(42);

        filter.setValue(fve.encodeValue());

        filterDAO.persist(filter);

        // test save & load
        ServiceFilter loaded = filterDAO.find(filter.getId());
        assertNotNull("Can't retrieve filter", loaded);

        filterDAO.remove(loaded);
        assertNull("Filter not deleted", filterDAO.find(loaded.getId()));
    }


}