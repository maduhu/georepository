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

import it.geosolutions.georepo.core.model.GSUser;

import java.util.List;

import com.trg.search.ISearch;

/**
 * Public interface to define operations on GSUsers
 * 
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */

public interface GSUserDAO extends RestrictedGenericDAO<GSUser>/* extends GenericDAO<GSUser, Long> */{

//    public List<GSUser> findAll();
//
//    public GSUser find(Long id);
//
//    public void persist(GSUser... user);
//
//    public GSUser merge(GSUser user);
//
//    public boolean remove(GSUser user);
//    public boolean removeById(Long id);
//
//    public List<GSUser> search(ISearch search);
//
//    public int count(ISearch search);
}