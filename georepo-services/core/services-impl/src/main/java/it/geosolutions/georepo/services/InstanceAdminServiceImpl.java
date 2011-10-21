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

import it.geosolutions.georepo.core.model.GSInstance;

import java.util.List;

import org.apache.log4j.Logger;

import com.trg.search.Search;
import it.geosolutions.georepo.core.dao.GSInstanceDAO;
import it.geosolutions.georepo.services.exception.BadRequestServiceEx;
import it.geosolutions.georepo.services.exception.NotFoundServiceEx;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class InstanceAdminServiceImpl implements InstanceAdminService {

    private final static Logger LOGGER = Logger.getLogger(InstanceAdminServiceImpl.class);

    private GSInstanceDAO instanceDAO;

    // ==========================================================================
    @Override
    public long insert(GSInstance instance) {
        instanceDAO.persist(instance);
        return instance.getId();
    }

    @Override
    public long update(GSInstance instance) throws NotFoundServiceEx {
        GSInstance orig = instanceDAO.find(instance.getId());
        if (orig == null) {
            throw new NotFoundServiceEx("GSInstance not found", instance.getId());
        }

        instanceDAO.merge(instance);
        return orig.getId();
    }

    @Override
    public GSInstance get(long id) throws NotFoundServiceEx {
        GSInstance instance = instanceDAO.find(id);

        if (instance == null) {
            throw new NotFoundServiceEx("GSInstance not found", id);
        }

//        return new ShortInstance(instance);
        return instance;
    }

    @Override
    public boolean delete(long id) throws NotFoundServiceEx {
        GSInstance instance = instanceDAO.find(id);

        if (instance == null) {
            throw new NotFoundServiceEx("GSInstance not found", id);
        }

        // data on ancillary tables should be deleted by cascading
        return instanceDAO.remove(instance);
    }

    @Override
    public List<GSInstance> getAll() {
        List<GSInstance> found = instanceDAO.findAll();
        return found;
//        return convertToShortList(found);
    }

    @Override
    public List<GSInstance> getList(String nameLike, Integer page, Integer entries) {

        if( (page != null && entries == null) || (page ==null && entries != null)) {
            throw new BadRequestServiceEx("Page and entries params should be declared together.");
        }

        Search searchCriteria = new Search(GSInstance.class);

        if(page != null) {
            searchCriteria.setMaxResults(entries);
            searchCriteria.setPage(page);
        }
        searchCriteria.addSortAsc("name");

        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }

        List<GSInstance> found = instanceDAO.search(searchCriteria);
        return found;
//        return convertToShortList(found);
    }

    @Override
    public long getCount(String nameLike) {
        Search searchCriteria = new Search(GSInstance.class);

        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }

        return instanceDAO.count(searchCriteria);
    }

    // ==========================================================================

//    @Override
//    public Map<String, String> getCustomProps(Long id) {
//        return instanceDAO.getCustomProps(id);
//    }
//
//    @Override
//    public void setCustomProps(Long id, Map<String, String> props) {
//        instanceDAO.setCustomProps(id, props);
//    }

    // ==========================================================================

//    private List<ShortInstance> convertToShortList(List<GSInstance> list) {
//        List<ShortInstance> swList = new ArrayList<ShortInstance>(list.size());
//        for (GSInstance instance : list) {
//            swList.add(new ShortInstance(instance));
//        }
//
//        return swList;
//    }

    public void setInstanceDAO(GSInstanceDAO instanceDAO) {
        this.instanceDAO = instanceDAO;
    }

    // ==========================================================================

}
