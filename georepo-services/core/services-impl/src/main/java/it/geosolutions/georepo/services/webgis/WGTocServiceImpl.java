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

import it.geosolutions.georepo.services.exception.IllegalParameterFault;
import it.geosolutions.georepo.services.webgis.model.TOCConfig;
import org.apache.log4j.Logger;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class WGTocServiceImpl implements WebGisTOCService {
    private final static Logger LOGGER = Logger.getLogger(WGTocServiceImpl.class);

    @Override
    public TOCConfig getTOC(String profile) throws IllegalParameterFault {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getProperty(long layerId, String propertyName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String setProperty(long layerId, String propertyName, String propertyValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
