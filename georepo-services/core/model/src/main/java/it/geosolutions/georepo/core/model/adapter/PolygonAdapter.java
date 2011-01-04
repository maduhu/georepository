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

package it.geosolutions.georepo.core.model.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;

/**
 * 
 * This class is an XML adapter that aim marshal and un-marshal supports to Polygon data fields
 * 
 * @author Tobia Di Pisa (tobia.dipisa@geo-solutions.it)
 * @author Alessio Fabiani (alessio.fabiani@geosolutions.it)
 * 
 */
public class PolygonAdapter extends XmlAdapter<String, Polygon> {

    /**
     * 
     * This method provide unmarshalling by string value
     * 
     * @param val
     *            the WKT representation of the polygon geometry
     * 
     */
    @Override
    public Polygon unmarshal(String val) throws ParseException {
        WKTReader wktReader = new WKTReader();

        Geometry the_geom = wktReader.read(val);
        if (the_geom instanceof Polygon) {
            if (the_geom.getSRID() == 0)
                the_geom.setSRID(4326);

            return (Polygon) the_geom;
        }

        throw new ParseException("WKB val is not a Polygon.");
    }

    /**
     * 
     * This method provide marshalling by Polygon value
     * 
     * @param the
     *            WKT representation of the polygon geometry
     * 
     */
    @Override
    public String marshal(Polygon the_geom) throws ParseException {
        if (the_geom != null) {
            WKTWriter wktWriter = new WKTWriter();
            if (the_geom.getSRID() == 0)
                the_geom.setSRID(4326);

            return wktWriter.write(the_geom);
        } else {
            throw new ParseException("Polygon obj is null.");
        }
    }
}
