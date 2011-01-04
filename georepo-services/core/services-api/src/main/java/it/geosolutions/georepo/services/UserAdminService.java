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
import it.geosolutions.georepo.services.dto.ShortUser;
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
 * Operations on {@link User User}s.
 * 
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
@WebService(name = "UserAdminService", targetNamespace = "http://geosolutions.it/georepo")
public interface UserAdminService {

    // ==========================================================================
    // Basic operations

    @Put
    @HttpResource(location = "/users")
    long insertUser(@WebParam(name = "user") User user);

    @Post
    @HttpResource(location = "/users")
    long updateUser(@WebParam(name = "user") User user) throws ResourceNotFoundFault;

    @Delete
    @HttpResource(location = "/users/{id}")
    boolean deleteUser(@WebParam(name = "id") long id) throws ResourceNotFoundFault;

    @Get
    @HttpResource(location = "/users/{id}")
    User getUser(@WebParam(name = "id") long id) throws ResourceNotFoundFault;

    @Get
    @HttpResource(location = "/users")
    List<ShortUser> getAllUsers();

    @Get
    @HttpResource(location = "/users/{nameLike}/{page}/{entries}")
    List<ShortUser> getUsers(@WebParam(name = "nameLike") String nameLike,
            @WebParam(name = "page") int page, @WebParam(name = "entries") int entries);

    @Get
    @HttpResource(location = "/userscount/{nameLike}")
    long getUsersCount(@WebParam(name = "nameLike") String nameLike);
}
