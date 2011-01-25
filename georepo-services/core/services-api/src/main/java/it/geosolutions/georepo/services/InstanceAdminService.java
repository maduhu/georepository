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
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.codehaus.jra.Delete;
import org.codehaus.jra.Get;
import org.codehaus.jra.HttpResource;
import org.codehaus.jra.Post;
import org.codehaus.jra.Put;

/**
 * Operations on {@link GSInstance GSInstance}s.
 * 
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
@WebService(name = "InstanceAdminService", targetNamespace = "http://geosolutions.it/georepo")
public interface InstanceAdminService {

    // ==========================================================================
    // Basic operations

    @Post
    @HttpResource(location = "/instances")
    long insert(@WebParam(name = "instance") GSInstance instance);

    @Put
    @HttpResource(location = "/instances")
    long update(@WebParam(name = "instance") GSInstance instance) throws ResourceNotFoundFault;

    @Delete
    @HttpResource(location = "/instances/{id}")
    boolean delete(@WebParam(name = "id") long id) throws ResourceNotFoundFault;

    @Get
    @HttpResource(location = "/instances/{id}")
    GSInstance get(@WebParam(name = "id") long id) throws ResourceNotFoundFault;

    @Get
    @HttpResource(location = "/instances")
    List<GSInstance> getAll();

    @Get
    @HttpResource(location = "/instances/{nameLike}/{page}/{entries}")
    List<GSInstance> getList(@WebParam(name = "nameLike") String nameLike,
            @WebParam(name = "page") int page, @WebParam(name = "entries") int entries);

    @Get
    @HttpResource(location = "/instancescount/{nameLike}")
    long getCount(@WebParam(name = "nameLike") String nameLike);

}
