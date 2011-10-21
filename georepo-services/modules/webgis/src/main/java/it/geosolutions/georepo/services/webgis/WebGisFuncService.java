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
package it.geosolutions.georepo.services.webgis;

import it.geosolutions.georepo.services.webgis.model.WebGisProperty;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
@Path("/func/")
public interface WebGisFuncService {

    @GET
    @Path("/properties/{profile}")
    @Produces(MediaType.APPLICATION_XML)
    List<WebGisProperty> getProfileProperties(
                @PathParam("profile") String profile
            ) ;

    @GET
    @Path("/properties/{profile}/{property}")
    @Produces(MediaType.TEXT_PLAIN)
    String getProperty (
                @PathParam("profile") String profile,
                @PathParam("property") String property
            ) ;

}
