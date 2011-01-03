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

import it.geosolutions.georepo.core.model.ServiceFilter;
import it.geosolutions.georepo.core.model.User;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;
import it.geosolutions.georepo.services.dto.ShortUser;

import java.util.List;
import javax.jws.WebParam;
import javax.jws.WebService;
import org.codehaus.jra.Delete;
import org.codehaus.jra.Get;
import org.codehaus.jra.HttpResource;
import org.codehaus.jra.Post;
import org.codehaus.jra.Put;

/**
 * Operations on {@link User User}s.
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
@WebService(name = "FilterAdminService",
            targetNamespace = "http://geosolutions.it/georepo")
public interface FilterAdminService {

    //==========================================================================
    // Basic operations

    @Put
    @HttpResource(location = "/filters")
    long insertFilter(@WebParam(name="user")ServiceFilter filter);

    @Post
    @HttpResource(location = "/filters")
    long updateFilter(@WebParam(name="filter")ServiceFilter filter) throws ResourceNotFoundFault;

    @Delete
    @HttpResource(location = "/filters/{id}")
    boolean deleteFilter(@WebParam(name="id")long id) throws ResourceNotFoundFault;

    @Get
    @HttpResource(location = "/filters/{id}")
    ServiceFilter geFilter(@WebParam(name="id")long id) throws ResourceNotFoundFault;

    @Get
    @HttpResource(location = "/filters")
    List<ServiceFilter> getAllFilters();

    @Get
    @HttpResource(location = "/searchfilters/group/{group}")
    List<ServiceFilter> getFiltersByGroup(@WebParam(name="group")String group);

    @Get
    @HttpResource(location = "/searchfilters/service/{service}")
    List<ServiceFilter> getFiltersByService(@WebParam(name="service")String service);

    @Get
    @HttpResource(location = "/searchfilters/service/{service}/prop/{propname}/user/{user}")
    List<ServiceFilter> getFiltersByServicePropUser(
            @WebParam(name="service")String service,
            @WebParam(name="propname")String propname,
            @WebParam(name="user")String user            
            );

}
