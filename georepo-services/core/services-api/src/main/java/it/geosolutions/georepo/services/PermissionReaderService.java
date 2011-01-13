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

import it.geosolutions.georepo.core.model.LayerPermission;
import it.geosolutions.georepo.core.model.RequestPermission;
import it.geosolutions.georepo.core.model.WorkspacePermission;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;


import javax.jws.WebParam;
import javax.jws.WebService;

import org.codehaus.jra.Get;
import org.codehaus.jra.HttpResource;

/**
 * 
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
@WebService(name = "PermissionReaderService", targetNamespace = "http://geosolutions.it/georepo")
public interface PermissionReaderService {

    // ==========================================================================
    // Basic operations

    @Get
    @HttpResource(location = "/permission/layer/user/{user}/service/{service}/workspace/{workspace}/layer/{layer}")
    LayerPermission getLayerPermission(
                @WebParam(name = "user") String user,
                @WebParam(name = "service") String service,
                @WebParam(name = "workspace") String workspace,
                @WebParam(name = "layer") String layer
            ) throws ResourceNotFoundFault;
    @Get
    @HttpResource(location = "/permission/workspace/user/{user}/service/{service}/workspace/{workspace}")
    WorkspacePermission getWorkspacePermission(
                @WebParam(name = "user") String user,
                @WebParam(name = "service") String service,
                @WebParam(name = "workspace") String workspace
            ) throws ResourceNotFoundFault;

    @Get
    @HttpResource(location = "/permission/request/user/{user}/service/{service}/request/{request}")
    RequestPermission getRequestPermission(
                @WebParam(name = "user") String user,
                @WebParam(name = "service") String service,
                @WebParam(name = "request") String request
            ) throws ResourceNotFoundFault;

}
