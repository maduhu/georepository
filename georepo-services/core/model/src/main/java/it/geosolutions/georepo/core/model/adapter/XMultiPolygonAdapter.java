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
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;

/**
 * 
 * eXplictiMultiPoligonAdapter
 * 
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
public class XMultiPolygonAdapter extends XmlAdapter<String, MultiPolygon> {

    /**
     * 
     * This method provide unmarshalling by string value
     * 
     * @param val
     *            the WKT representation of the geometry
     * 
     */
    @Override
    public MultiPolygon unmarshal(String val) throws ParseException {
        WKTReader wktReader = new WKTReader();

        Geometry the_geom = wktReader.read(val);
        if (the_geom.getSRID() == 0)
            the_geom.setSRID(4326);

        try {
            return (MultiPolygon) the_geom;
        } catch (ClassCastException e) {
            throw new ParseException("WKT val is a " + the_geom.getClass().getName());
        }
    }

    /**
     * 
     * This method provide marshalling by value
     * 
     * @param the
     *            WKT representation of the geometry
     * 
     */
    @Override
    public String marshal(MultiPolygon the_geom) throws ParseException {
        if (the_geom != null) {
            WKTWriter wktWriter = new WKTWriter();
            if (the_geom.getSRID() == 0)
                the_geom.setSRID(4326);

            return wktWriter.write(the_geom);
        } else {
            throw new ParseException("Geometry obj is null.");
        }
    }
}
