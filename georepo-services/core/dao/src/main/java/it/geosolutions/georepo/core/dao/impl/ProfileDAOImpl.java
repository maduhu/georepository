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
package it.geosolutions.georepo.core.dao.impl;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.trg.search.ISearch;
import it.geosolutions.georepo.core.dao.ProfileDAO;
import it.geosolutions.georepo.core.model.Profile;

/**
 * Public implementation of the ProfileDAO interface
 * 
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
@Transactional
public class ProfileDAOImpl extends BaseDAO<Profile, Long>
// extends GenericDAOImpl<User, Long>
        implements ProfileDAO {

    final private static Logger LOGGER = Logger.getLogger(ProfileDAOImpl.class);

    @Override
    public void persist(Profile... entities) {
        super.persist(entities);
    }

    @Override
    public List<Profile> findAll() {
        return super.findAll();
    }

    @Override
    public List<Profile> search(ISearch search) {
        return super.search(search);
    }

    @Override
    public Profile merge(Profile entity) {
        return super.merge(entity);
    }

    @Override
    public boolean remove(Profile entity) {
        return super.remove(entity);
    }

    @Override
    public boolean removeById(Long id) {
        return super.removeById(id);
    }

}
