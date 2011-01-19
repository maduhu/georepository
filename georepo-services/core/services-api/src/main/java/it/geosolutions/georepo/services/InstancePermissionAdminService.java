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

import it.geosolutions.georepo.core.model.InstancePermission;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;

import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.codehaus.jra.Delete;
import org.codehaus.jra.Get;
import org.codehaus.jra.HttpResource;
import org.codehaus.jra.Post;
import org.codehaus.jra.Put;

/**
 * Operations on {@link InstancePermission InstancePermission}s.
 * 
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
@WebService(name = "InstancePermissionAdminService", targetNamespace = "http://geosolutions.it/georepo")
public interface InstancePermissionAdminService {

    // ==========================================================================
    // Basic operations

    @Post
    @HttpResource(location = "/instancePermissions")
    long insert(@WebParam(name = "instancePermission") InstancePermission instancePermission);

    @Put
    @HttpResource(location = "/instancePermissions")
    long update(@WebParam(name = "instancePermission") InstancePermission instancePermission) throws ResourceNotFoundFault;

    @Delete
    @HttpResource(location = "/instancePermissions/{id}")
    boolean delete(@WebParam(name = "id") long id) throws ResourceNotFoundFault;

    @Get
    @HttpResource(location = "/instancePermissions/{id}")
    InstancePermission get(@WebParam(name = "id") long id) throws ResourceNotFoundFault;

    @Get
    @HttpResource(location = "/instancePermissions")
    List<InstancePermission> getAll();

    @Get
    @HttpResource(location = "/instancePermissions/{nameLike}/{page}/{entries}")
    List<InstancePermission> getList(@WebParam(name = "nameLike") String nameLike,
            @WebParam(name = "page") int page, @WebParam(name = "entries") int entries);

    @Get
    @HttpResource(location = "/instancePermissionscount/{nameLike}")
    long getCount(@WebParam(name = "nameLike") String nameLike);

    // ==========================================================================
    List<InstancePermission> getListByProfile(
            @WebParam(name = "profileId") Long profileId,
            @WebParam(name = "nameLike") String nameLike,
            @WebParam(name = "page") int page,
            @WebParam(name = "entries") int entries);

    long getCountByProfile(Long profileId, String nameLike);

//    @Get
//    @HttpResource(location = "/instancePermissions/{id}/props")
//    public Map<String, String> getCustomProps(@WebParam(name = "id") Long id);
//
//    @Put
//    @HttpResource(location = "/instancePermissions/{id}/props")
//    public void setCustomProps(@WebParam(name = "id")Long id, Map<String, String> props);

}
