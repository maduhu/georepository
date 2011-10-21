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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.trg.search.Search;
import it.geosolutions.georepo.core.dao.GRUserDAO;
import it.geosolutions.georepo.core.model.GRUser;
import it.geosolutions.georepo.services.exception.BadRequestServiceEx;
import it.geosolutions.georepo.services.exception.NotFoundServiceEx;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class GRUserAdminServiceImpl implements GRUserAdminService {

    private final static Logger LOGGER = Logger.getLogger(GRUserAdminServiceImpl.class);

    private GRUserDAO grUserDAO;

    // ==========================================================================
    @Override
    public long insert(GRUser user) {
        grUserDAO.persist(user);
        return user.getId();
    }

    @Override
    public long update(GRUser user) throws NotFoundServiceEx {
        GRUser orig = grUserDAO.find(user.getId());
        if (orig == null) {
            throw new NotFoundServiceEx("User not found", user.getId());
        }

        grUserDAO.merge(user);
        return orig.getId();
    }

    @Override
    public GRUser get(long id) throws NotFoundServiceEx {
        GRUser user = grUserDAO.find(id);

        if (user == null) {
            throw new NotFoundServiceEx("User not found", id);
        }

        return user;
    }

    @Override
    public boolean delete(long id) throws NotFoundServiceEx {
        // data on ancillary tables should be deleted by cascading
        return grUserDAO.removeById(id);
    }

    @Override
    public List<GRUser> getFullList(String nameLike, Integer page, Integer entries) {

        if( (page != null && entries == null) || (page ==null && entries != null)) {
            throw new BadRequestServiceEx("Page and entries params should be declared together.");
        }

        Search searchCriteria = new Search(GRUser.class);

        if(page != null) {
            searchCriteria.setMaxResults(entries);
            searchCriteria.setPage(page);
        }

        searchCriteria.addSortAsc("name");

        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }

        List<GRUser> found = grUserDAO.search(searchCriteria);
        return found;
    }

    @Override
    public List<ShortUser> getList(String nameLike, Integer page, Integer entries) {
        return convertToShortList(getFullList(nameLike, page, entries));
    }

    @Override
    public long getCount(String nameLike) {
        Search searchCriteria = new Search(GRUser.class);

        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }

        return grUserDAO.count(searchCriteria);
    }

    // ==========================================================================

    private List<ShortUser> convertToShortList(List<GRUser> list) {
        List<ShortUser> swList = new ArrayList<ShortUser>(list.size());
        for (GRUser user : list) {
            swList.add(new ShortUser(user));
        }

        return swList;
    }

    // ==========================================================================

    public void setGrUserDAO(GRUserDAO userDao) {
        this.grUserDAO = userDao;
    }

}
