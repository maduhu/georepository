/*
 * $Header: it.geosolutions.georepo.gui.server.utility.DGWATCHGeometryUtility,v. 0.1 12/ago/2010 09.42.49 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 12/ago/2010 09.42.49 $
 *
 * ====================================================================
 *
 * Copyright (C) 2010 GeoSolutions S.A.S.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. 
 *
 * ====================================================================
 *
 * This software consists of voluntary contributions made by developers
 * of GeoSolutions.  For more information on GeoSolutions, please see
 * <http://www.geo-solutions.it/>.
 *
 */
package it.geosolutions.georepo.gui.server.utility;

import it.geosolutions.georepo.core.model.adapter.MultiPolygonAdapter;
import it.geosolutions.georepo.core.model.adapter.PolygonAdapter;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.springframework.stereotype.Component;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/**
 * @author giuseppe
 * 
 */
@Component("geometryUtility")
public class DGWATCHGeometryUtility {

	private GeometryFactory factory = new GeometryFactory();
	private PolygonAdapter polAdapter = new PolygonAdapter();
	private MultiPolygonAdapter multiPolAdapter = new MultiPolygonAdapter();

	public Polygon createPolygon(String wkt) throws ParseException {
		Polygon poly = polAdapter.unmarshal(wkt);
		if (poly.getSRID() == 0)
			poly.setSRID(4326);
		
		return poly;
	}

	public MultiPolygon createMultiPolygon(Polygon[] pol) {
		MultiPolygon multiPoly = this.factory.createMultiPolygon(pol);
		if (multiPoly.getSRID() == 0)
			multiPoly.setSRID(4326);
		return multiPoly;
	}
	
	public MultiPolygon createMultiPolygon(String wkt) throws ParseException{
		MultiPolygon multiPoly = this.multiPolAdapter.unmarshal(wkt);
		if (multiPoly.getSRID() == 0)
			multiPoly.setSRID(4326);
		return multiPoly;
	}
	
	public String createWKTFromMultiPolygon(MultiPolygon multiPoly) throws ParseException{
		if (multiPoly.getSRID() == 0)
			multiPoly.setSRID(4326);
		
		return this.multiPolAdapter.marshal(multiPoly);
	}
	
	/**
	 * Project geometries from src to trg CRS.
	 * 
	 * @param originalGeoms
	 * @param srcCRSCode
	 * @param trgCRSCode
	 * @return 
	 * @throws FactoryException 
	 * @throws NoSuchAuthorityCodeException 
	 * @throws TransformException 
	 * @throws MismatchedDimensionException 
	 */
	public static Geometry projectGeometry(Geometry originalGeom,
			String srcCRSCode, String trgCRSCode) throws NoSuchAuthorityCodeException, FactoryException, MismatchedDimensionException, TransformException {

		Geometry projectedGeometry = null;
		final MathTransform transform = CRS.findMathTransform(CRS.decode(srcCRSCode, true), CRS.decode(trgCRSCode, true), true);

		// converting geometries into a linear system
		try {
			projectedGeometry = JTS.transform(originalGeom, transform);
		} catch (Exception e) {
			GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
			WKTReader reader = new WKTReader(geometryFactory);

			try {
				Polygon worldCutPolygon = (Polygon) reader.read("POLYGON((-180 -89.9, -180 89.9, 180 89.9, 180 -89.9, -180 -89.9))");

				projectedGeometry = JTS.transform(originalGeom.intersection(worldCutPolygon), transform);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		
		return projectedGeometry;
	}
}
