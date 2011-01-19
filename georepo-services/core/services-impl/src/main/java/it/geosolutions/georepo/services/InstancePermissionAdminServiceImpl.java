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

import it.geosolutions.georepo.core.model.InstancePermission;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;

import java.util.List;

import org.apache.log4j.Logger;

import com.trg.search.Search;
import it.geosolutions.georepo.core.dao.InstancePermissionDAO;

/**
 * 
 * @author ETj (etj at geo-solutions.it)
 */
public class InstancePermissionAdminServiceImpl implements InstancePermissionAdminService {

    private final static Logger LOGGER = Logger.getLogger(InstancePermissionAdminServiceImpl.class);

    private InstancePermissionDAO instancePermissionDAO;

    // ==========================================================================
    @Override
    public long insert(InstancePermission ip) {
        instancePermissionDAO.persist(ip);
        return ip.getId();
    }

    @Override
    public long update(InstancePermission ip) throws ResourceNotFoundFault {
        InstancePermission orig = instancePermissionDAO.find(ip.getId());
        if (orig == null) {
            throw new ResourceNotFoundFault("InstancePermission not found", ip.getId());
        }

        instancePermissionDAO.merge(ip);
        return orig.getId();
    }

    @Override
    public InstancePermission get(long id) throws ResourceNotFoundFault {
        InstancePermission ip = instancePermissionDAO.find(id);

        if (ip == null) {
            throw new ResourceNotFoundFault("InstancePermission not found", id);
        }

//        return new ShortInstance(ip);
        return ip;
    }

    @Override
    public boolean delete(long id) throws ResourceNotFoundFault {
        InstancePermission ip = instancePermissionDAO.find(id);

        if (ip == null) {
            throw new ResourceNotFoundFault("InstancePermission not found", id);
        }

        // data on ancillary tables should be deleted by cascading
        return instancePermissionDAO.remove(ip);
    }

    @Override
    public List<InstancePermission> getAll() {
        List<InstancePermission> found = instancePermissionDAO.findAll();
        return found;
//        return convertToShortList(found);
    }

    @Override
    public List<InstancePermission> getList(String nameLike, int page, int entries) {
        Search searchCriteria = new Search(InstancePermission.class);
        searchCriteria.setMaxResults(entries);
        searchCriteria.setPage(page);
        searchCriteria.addSortAsc("name");

        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }

        List<InstancePermission> found = instancePermissionDAO.search(searchCriteria);
        return found;
//        return convertToShortList(found);
    }

    @Override
    public long getCount(String nameLike) {
        Search searchCriteria = new Search(InstancePermission.class);

        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }

        return instancePermissionDAO.count(searchCriteria);
    }

    // ==========================================================================
    
    @Override
    public List<InstancePermission> getListByProfile(Long profileId, String nameLike, int page, int entries) {
        Search searchCriteria = new Search(InstancePermission.class);
        searchCriteria.setMaxResults(entries);
        searchCriteria.setPage(page);
        searchCriteria.addSortAsc("name");
        searchCriteria.addFilterEqual("profile.id", profileId);

        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }

        List<InstancePermission> found = instancePermissionDAO.search(searchCriteria);
        return found;
//        return convertToShortList(found);
    }

    @Override
    public long getCountByProfile(Long profileId, String nameLike) {
        Search searchCriteria = new Search(InstancePermission.class);
        searchCriteria.addFilterEqual("profile.id", profileId);

        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }

        return instancePermissionDAO.count(searchCriteria);
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

//    private List<ShortInstance> convertToShortList(List<InstancePermission> list) {
//        List<ShortInstance> swList = new ArrayList<ShortInstance>(list.size());
//        for (InstancePermission ip : list) {
//            swList.add(new ShortInstance(ip));
//        }
//
//        return swList;
//    }


    // ==========================================================================

    public void setInstancePermissionDAO(InstancePermissionDAO instancePermissionDAO) {
        this.instancePermissionDAO = instancePermissionDAO;
    }

}
