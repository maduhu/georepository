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


import com.vividsolutions.jts.geom.MultiPolygon;

/**
 * 
 * This class is an XML adapter that aim marshal and un-marshal supports to MultiPolygon data fields
 * 
 * @author etj (etj at geo-solutions.it)
 * 
 */
public class MultiPolygonAdapter extends GeometryAdapter<MultiPolygon> {}