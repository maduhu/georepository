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
package it.geosolutions.georepo.services.rest;



import it.geosolutions.georepo.services.rest.exception.BadRequestRestEx;
import it.geosolutions.georepo.services.rest.exception.InternalErrorRestEx;
import it.geosolutions.georepo.services.rest.exception.NotFoundRestEx;
import it.geosolutions.georepo.services.rest.model.config.RESTConfigurationRemapping;
import it.geosolutions.georepo.services.rest.model.config.RESTFullConfiguration;
import it.geosolutions.georepo.services.rest.model.config.RESTFullProfileList;
import it.geosolutions.georepo.services.rest.model.config.RESTFullUserList;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

/**
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */

@Path("/")
public interface RESTConfigService {

    /**
     * @return a sample profile list
     * */
    @GET
    @Path("/full")
    @Produces(MediaType.APPLICATION_XML)
    RESTFullConfiguration getConfiguration();

    @PUT
    @Path("/full")
    @Produces(MediaType.APPLICATION_XML)
    RESTConfigurationRemapping setConfiguration(@Multipart("configuration")RESTFullConfiguration config)
            throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx;

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_XML)
    RESTFullUserList getUsers()
            throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx;

    @GET
    @Path("/profiles")
    @Produces(MediaType.APPLICATION_XML)
    RESTFullProfileList getProfiles()
            throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx;

}
