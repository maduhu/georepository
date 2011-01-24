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
package it.geosolutions.georepo.services.webgis;

import it.geosolutions.georepo.services.exception.IllegalParameterFault;
import it.geosolutions.georepo.services.webgis.model.TOC;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * 
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */

@Path("/toc/")
@Produces("application/xml")
public interface WebGisTOCService {

    // ==========================================================================

    @GET
    @Path("/profile/{profile}/toc")
    @XmlElementWrapper(name="pippo")
    TOC getTOC(
            @PathParam("profile") String profile
            ) throws IllegalParameterFault;


    @GET
    @Path("/layer/{layerId}/property/{propertyName}")
    @Produces("text/plain")
    String getProperty(
            @PathParam("layerId")long layerId,
            @PathParam("propertyName")String propertyName
            );

    @PUT
    @Path("/layer/{layerId}/property/{propertyName}/{propertyValue}")
    @Produces("text/plain")
    String setProperty(
            @PathParam("layerId")long layerId,
            @PathParam("propertyName")String propertyName,
            @PathParam("propertyValue")String propertyValue
            );

}
