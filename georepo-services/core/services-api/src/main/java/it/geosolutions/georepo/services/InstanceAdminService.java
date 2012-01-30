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

import java.util.List;

import it.geosolutions.georepo.core.model.GSInstance;
import it.geosolutions.georepo.services.exception.NotFoundServiceEx;


/**
 * Operations on {@link GSInstance GSInstance}s.
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
public interface InstanceAdminService extends GetProviderService<GSInstance>
{

    // ==========================================================================
    // Basic operations

    long insert(GSInstance instance);

    long update(GSInstance instance) throws NotFoundServiceEx;

    boolean delete(long id) throws NotFoundServiceEx;

    GSInstance get(long id) throws NotFoundServiceEx;

    List<GSInstance> getAll();

    List<GSInstance> getList(String nameLike, Integer page, Integer entries);

    long getCount(String nameLike);

}
