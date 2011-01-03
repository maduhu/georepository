/*
 * $Header: it.geosolutions.georepo.gui.client.widget.MapPreviewWidget,v. 0.1 31/ago/2010 09.10.34 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 31/ago/2010 09.10.34 $
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
package it.geosolutions.georepo.gui.client.widget.map;

import org.gwtopenmaps.openlayers.client.Bounds;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapUnits;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.OpenLayers;
import org.gwtopenmaps.openlayers.client.Projection;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Geometry;
import org.gwtopenmaps.openlayers.client.geometry.MultiPolygon;
import org.gwtopenmaps.openlayers.client.layer.Layer;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.OSMOptions;
import org.gwtopenmaps.openlayers.client.layer.TransitionEffect;
import org.gwtopenmaps.openlayers.client.layer.Vector;
import org.gwtopenmaps.openlayers.client.layer.VectorOptions;
import org.gwtopenmaps.openlayers.client.layer.WMS;
import org.gwtopenmaps.openlayers.client.layer.WMSOptions;
import org.gwtopenmaps.openlayers.client.layer.WMSParams;

/**
 * @author giuseppe
 * 
 */
public class MapPreviewWidget {

	private MapWidget mapWidget;
	private MapOptions defaultMapOptions;
	private Map map;
	private Layer layer;
	private Layer osm;

	private Vector vector;

	public MapPreviewWidget() {
		this.createMapOption(true);
	}

	private void createMapOption(boolean isGoogle) {
		// TODO Auto-generated method stub

		OpenLayers.setProxyHost("gwtOpenLayersProxy?targetURL=");

		this.defaultMapOptions = new MapOptions();
		this.defaultMapOptions.setNumZoomLevels(18);
		if (isGoogle) {
			this.defaultMapOptions.setProjection("EPSG:900913");
			this.defaultMapOptions.setDisplayProjection(new Projection(
					"EPSG:4326"));
			this.defaultMapOptions.setUnits(MapUnits.METERS);
			this.defaultMapOptions.setMaxExtent(new Bounds(-20037508,
					-20037508, 20037508, 20037508.34));
			this.defaultMapOptions.setMaxResolution(new Double(156543.0339)
					.floatValue());
		} else {
			this.defaultMapOptions.setProjection("EPSG:4326");
		}

		initMapWidget(this.defaultMapOptions, isGoogle);
	}

	private void initMapWidget(MapOptions defaultMapOptions, boolean isGoogle) {
		mapWidget = new MapWidget("100%", "100%", defaultMapOptions);
		this.map = mapWidget.getMap();
		// this.map.addControl(new LayerSwitcher());
		if (isGoogle) {
			this.createOSM();
			// this.createBaseGoogleLayer();
		} else {
			WMSParams wmsParams = new WMSParams();
			wmsParams.setFormat("image/png");
			wmsParams.setLayers("basic");
			wmsParams.setStyles("");

			WMSOptions wmsLayerParams = new WMSOptions();
			wmsLayerParams.setTransitionEffect(TransitionEffect.RESIZE);

			layer = new WMS("Basic WMS", "http://labs.metacarta.com/wms/vmap0",
					wmsParams, wmsLayerParams);
			this.map.addLayer(layer);
		}

		this.initVectorLayer();
	}

	private void createOSM() {
		this.osm = OSM.THIS("OpenStreetMap Preview", OpenLayers.getProxyHost()
				+ "http://tile.openstreetmap.org/${z}/${x}/${y}.png",
				new OSMOptions());
		;// OSM.Mapnik("OpenStreetMap Preview");
		this.map.addLayer(osm);
	}

	private void initVectorLayer() {
		VectorOptions vectorOption = new VectorOptions();
		vectorOption.setStyle(this.createStyle());
		vectorOption.setDisplayInLayerSwitcher(false);
		this.vector = new Vector("DGWATCH PREVIEW Vector Layer", vectorOption);
		this.map.addLayer(vector);
	}

	// private void createBaseGoogleLayer() {
	// GoogleOptions option = new GoogleOptions();
	// option.setType(GMapType.G_NORMAL_MAP);
	// option.setSphericalMercator(true);
	//
	// layer = new Google("Google Normal Preview", option);
	// this.map.addLayer(layer);
	// }

	private Style createStyle() {
		Style style = new Style();
		style.setStrokeColor("#000000");
		style.setStrokeWidth(1);
		style.setFillColor("#FF0000");
		style.setFillOpacity(0.5);
		style.setPointRadius(5);
		style.setStrokeOpacity(1.0);
		return style;
	}

	public void drawAoiOnMap(String wkt) {
		this.eraseFeatures();
		MultiPolygon geom = MultiPolygon.narrowToMultiPolygon(Geometry.fromWKT(
				wkt).getJSObject());
		geom.transform(new Projection("EPSG:4326"), new Projection(
				"EPSG:900913"));
		VectorFeature vectorFeature = new VectorFeature(geom);
		this.vector.addFeature(vectorFeature);
		this.map.zoomToExtent(geom.getBounds());
	}

	/**
	 * Erase all Features added to Vector Layer
	 */
	public void eraseFeatures() {
		this.vector.destroyFeatures();
		this.vector.redraw();
		this.map.updateSize();
	}

	/**
	 * Rebuild Vector Layer
	 */
	public void rebuildVectorLayer() {
		this.map.removeLayer(this.vector);
		this.initVectorLayer();
	}

	/**
	 * @return the mapWidget
	 */
	public MapWidget getMapWidget() {
		return mapWidget;
	}

	/**
	 * @return the vector
	 */
	public Vector getVector() {
		return vector;
	}
}
