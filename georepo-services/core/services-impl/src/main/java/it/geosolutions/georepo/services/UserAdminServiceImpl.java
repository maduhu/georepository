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


import it.geosolutions.georepo.core.model.User;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;
import it.geosolutions.georepo.services.dto.ShortUser;

import java.util.ArrayList;
import java.util.List;

import com.trg.search.Search;
import it.geosolutions.georepo.core.dao.UserDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class UserAdminServiceImpl implements UserAdminService {

    private final static Logger LOGGER = Logger.getLogger(UserAdminServiceImpl.class);
    
    private UserDAO userDao;

    //==========================================================================
    @Override
    public long insertUser(User watch) {
        userDao.persist(watch);
        return watch.getId();
    }

    @Override
    public long updateUser(User user) throws ResourceNotFoundFault {
        User orig = userDao.find(user.getId());
        if (orig == null) {
            throw new ResourceNotFoundFault("User not found", user.getId());
        }

        userDao.merge(user);
        return orig.getId();
    }

    @Override
    public User getUser(long id) throws ResourceNotFoundFault {
        User user = userDao.find(id);

        if (user == null) {
            throw new ResourceNotFoundFault("User not found", id);
        }

        return user;
    }

    @Override
    public boolean deleteUser(long id) throws ResourceNotFoundFault {
        User user = userDao.find(id);

        if (user == null) {
            throw new ResourceNotFoundFault("User not found", id);
        }

        // data on ancillary tables should be deleted by cascading
        return userDao.remove(user);
    }

    @Override
    public List<ShortUser> getAllUsers() {
        List<User> found = userDao.findAll();
        return convertToShortList(found);
    }

    @Override
    public List<ShortUser> getUsers(String nameLike, int page, int entries) {
        Search searchCriteria = new Search(User.class);
        searchCriteria.setMaxResults(entries);
        searchCriteria.setPage(page);
        searchCriteria.addSortAsc("name");

        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }

        List<User> foundWatch = userDao.search(searchCriteria);
        return convertToShortList(foundWatch);
    }

    @Override
    public long getUsersCount(String nameLike) {
        Search searchCriteria = new Search(User.class);

        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }

        return userDao.count(searchCriteria);
    }


    //==========================================================================

    private List<ShortUser> convertToShortList(List<User> watchList) {
        List<ShortUser> swList = new ArrayList<ShortUser>(watchList.size());
        for (User watch : watchList) {
            swList.add(new ShortUser(watch));
        }

        return swList;
    }

    //==========================================================================

    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

}
