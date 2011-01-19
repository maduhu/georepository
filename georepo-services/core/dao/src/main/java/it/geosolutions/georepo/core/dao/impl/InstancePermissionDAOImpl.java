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

import it.geosolutions.georepo.core.model.InstancePermission;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.trg.search.ISearch;
import it.geosolutions.georepo.core.dao.InstancePermissionDAO;

/**
 * Public implementation of the GSInstanceDAO interface
 * 
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
@Transactional
public class InstancePermissionDAOImpl extends BaseDAO<InstancePermission, Long>
// extends GenericDAOImpl<InstancePermission, Long>
        implements InstancePermissionDAO {

    final private static Logger LOGGER = Logger.getLogger(InstancePermissionDAOImpl.class);

    @Override
    public void persist(InstancePermission... entities) {
        super.persist(entities);
    }

    @Override
    public List<InstancePermission> findAll() {
        return super.findAll();
    }

    @Override
    public List<InstancePermission> search(ISearch search) {
        return super.search(search);
    }

    @Override
    public InstancePermission merge(InstancePermission entity) {
        return super.merge(entity);
    }

    @Override
    public boolean remove(InstancePermission entity) {
        return super.remove(entity);
    }

    @Override
    public boolean removeById(Long id) {
        return super.removeById(id);
    }

}
