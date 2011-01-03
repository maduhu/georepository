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

package it.geosolutions.georepo.services.exception;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.ws.WebFault;

/**
 * 
 * Bad parameter coming from web service client
 * 
 * @author ETj
 */

@WebFault(name="IllegalParameterFault", faultBean="it.geosolutions.georepo.services.exception.IllegalParameterFault")
public class IllegalParameterFault extends Exception {
	
    public IllegalParameterFault() {
    }

    /**
     * This is needed, or the SOAP client will throw an ex in initialization:
     * java.lang.NoSuchMethodException: it.geosolutions.georepo.services.exception.ResourceNotFoundFault.<init>(java.lang.String)
     */
    public IllegalParameterFault(String msg) {
        super(msg);
    }

    public IllegalParameterFault(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
