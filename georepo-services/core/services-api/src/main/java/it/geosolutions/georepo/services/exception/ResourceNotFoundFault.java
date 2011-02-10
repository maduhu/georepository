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

import javax.xml.ws.WebFault;

/**
 * 
 * ResourceNotFoundFault using when if an exception occurs retrieving the data from the database
 * 
 * @author Tobia Di Pisa (tobia.dipisa@geo-solutions.it)
 * 
 * @TODO: the fault is not deserialized properly on client side even if the msg is ok: Payload:
 *        <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
 *        <soap:Body><soap:Fault> <faultcode>soap:Server</faultcode> <faultstring>Fault
 *        occured</faultstring> <detail> <ns2:ResourceNotFoundFault
 *        xmlns:ns2="http://services.dgwatch.digitalglobe.com/"> <id>-10</id> <message>User not
 *        found!</message> </ns2:ResourceNotFoundFault> </detail></soap:Fault> </soap:Body>
 *        </soap:Envelope>
 */

@WebFault(name = "ResourceNotFoundFault", faultBean = "it.geosolutions.georepo.services.exception.ResourceNotFoundFault")
public class ResourceNotFoundFault extends Exception {

    private static final long serialVersionUID = 4100712158220027390L;

    private long id = -1;

    public ResourceNotFoundFault() {
    }

    /**
     * This is needed, or the SOAP client will throw an ex in initialization:
     * java.lang.NoSuchMethodException:
     * it.geosolutions.georepo.services.exception.ResourceNotFoundFault.<init>(java.lang.String)
     */
    public ResourceNotFoundFault(String msg) {
        super(msg);
    }

    public ResourceNotFoundFault(String msg, long id) {
        super(msg + " ("+id+")");
        this.id = id;
    }

    public ResourceNotFoundFault(String message, Throwable cause) {
        super(message, cause);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
