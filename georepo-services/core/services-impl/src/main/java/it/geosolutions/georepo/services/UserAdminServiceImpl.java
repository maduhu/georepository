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
package it.geosolutions.georepo.services;

import it.geosolutions.georepo.core.dao.GSUserDAO;
import it.geosolutions.georepo.core.model.GSUser;
import it.geosolutions.georepo.services.dto.ShortUser;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.trg.search.Search;

/**
 * 
 * @author ETj (etj at geo-solutions.it)
 */
public class UserAdminServiceImpl implements UserAdminService {

    private final static Logger LOGGER = Logger.getLogger(UserAdminServiceImpl.class);

    private GSUserDAO userDao;

    // ==========================================================================
    @Override
    public long insertUser(GSUser user) {
        userDao.persist(user);
        return user.getId();
    }

    @Override
    public long updateUser(GSUser user) throws ResourceNotFoundFault {
        GSUser orig = userDao.find(user.getId());
        if (orig == null) {
            throw new ResourceNotFoundFault("User not found", user.getId());
        }

        userDao.merge(user);
        return orig.getId();
    }

    @Override
    public GSUser getUser(long id) throws ResourceNotFoundFault {
        GSUser user = userDao.find(id);

        if (user == null) {
            throw new ResourceNotFoundFault("User not found", id);
        }

        return user;
    }

    @Override
    public boolean deleteUser(long id) throws ResourceNotFoundFault {
        GSUser user = userDao.find(id);

        if (user == null) {
            throw new ResourceNotFoundFault("User not found", id);
        }

        // data on ancillary tables should be deleted by cascading
        return userDao.remove(user);
    }

    @Override
    public List<ShortUser> getAllUsers() {
        List<GSUser> found = userDao.findAll();
        return convertToShortList(found);
    }

    @Override
    public List<ShortUser> getUsers(String nameLike, int page, int entries) {
        Search searchCriteria = new Search(GSUser.class);
        searchCriteria.setMaxResults(entries);
        searchCriteria.setPage(page);
        searchCriteria.addSortAsc("name");

        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }

        List<GSUser> found = userDao.search(searchCriteria);
        return convertToShortList(found);
    }

    @Override
    public long getUsersCount(String nameLike) {
        Search searchCriteria = new Search(GSUser.class);

        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }

        return userDao.count(searchCriteria);
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

    public void setUserDao(GSUserDAO userDao) {
        this.userDao = userDao;
    }

}
