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

import it.geosolutions.georepo.core.dao.GSUserDAO;
import it.geosolutions.georepo.core.model.GSUser;
import it.geosolutions.georepo.services.dto.ShortUser;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.trg.search.Search;
import it.geosolutions.georepo.services.exception.BadRequestWebEx;

/**
 * 
 * @author ETj (etj at geo-solutions.it)
 */
public class UserAdminServiceImpl implements UserAdminService {

    private final static Logger LOGGER = Logger.getLogger(UserAdminServiceImpl.class);

    private GSUserDAO userDAO;

    // ==========================================================================
    @Override
    public long insert(GSUser user) {
        userDAO.persist(user);
        return user.getId();
    }

    @Override
    public long update(GSUser user) throws ResourceNotFoundFault {
        GSUser orig = userDAO.find(user.getId());
        if (orig == null) {
            throw new ResourceNotFoundFault("User not found", user.getId());
        }

        userDAO.merge(user);
        return orig.getId();
    }

    @Override
    public GSUser get(long id) throws ResourceNotFoundFault {
        GSUser user = userDAO.find(id);

        if (user == null) {
            throw new ResourceNotFoundFault("User not found", id);
        }

        return user;
    }

    @Override
    public boolean delete(long id) throws ResourceNotFoundFault {
        // data on ancillary tables should be deleted by cascading
        return userDAO.removeById(id);
    }

    @Override
    public List<ShortUser> getAll() {
        List<GSUser> found = userDAO.findAll();
        return convertToShortList(found);
    }

    @Override
    public List<ShortUser> getList(String nameLike, Integer page, Integer entries) {

        if( (page != null && entries == null) || (page ==null && entries != null)) {
            throw new BadRequestWebEx("Page and entries params should be declared together.");
        }

        Search searchCriteria = new Search(GSUser.class);

        if(page != null) {
            searchCriteria.setMaxResults(entries);
            searchCriteria.setPage(page);
        }
        
        searchCriteria.addSortAsc("name");

        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }

        List<GSUser> found = userDAO.search(searchCriteria);
        return convertToShortList(found);
    }

    @Override
    public long getCount(String nameLike) {
        Search searchCriteria = new Search(GSUser.class);

        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }

        return userDAO.count(searchCriteria);
    }

    // ==========================================================================

    private List<ShortUser> convertToShortList(List<GSUser> list) {
        List<ShortUser> swList = new ArrayList<ShortUser>(list.size());
        for (GSUser user : list) {
            swList.add(new ShortUser(user));
        }

        return swList;
    }

    // ==========================================================================

    public void setGsUserDAO(GSUserDAO userDao) {
        this.userDAO = userDao;
    }

}
