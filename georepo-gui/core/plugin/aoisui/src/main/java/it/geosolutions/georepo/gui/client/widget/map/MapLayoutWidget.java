/*
 * $Header: it.geosolutions.georepo.gui.client.widget.MapWidget,v. 0.1 08/lug/2010 12.47.35 created by frank $
 * $Revision: 0.1 $
 * $Date: 08/lug/2010 12.47.35 $
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

import java.util.Collections;
import java.util.List;

import org.gwtopenmaps.openlayers.client.Bounds;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapUnits;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.OpenLayers;
import org.gwtopenmaps.openlayers.client.Projection;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.control.DrawFeature;
import org.gwtopenmaps.openlayers.client.control.DrawFeature.FeatureAddedListener;
import org.gwtopenmaps.openlayers.client.control.DrawFeatureOptions;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Geometry;
import org.gwtopenmaps.openlayers.client.geometry.MultiPolygon;
import org.gwtopenmaps.openlayers.client.handler.PolygonHandler;
import org.gwtopenmaps.openlayers.client.layer.Layer;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.OSMOptions;
import org.gwtopenmaps.openlayers.client.layer.TransitionEffect;
import org.gwtopenmaps.openlayers.client.layer.Vector;
import org.gwtopenmaps.openlayers.client.layer.VectorOptions;
import org.gwtopenmaps.openlayers.client.layer.WMS;
import org.gwtopenmaps.openlayers.client.layer.WMSOptions;
import org.gwtopenmaps.openlayers.client.layer.WMSParams;

import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.configuration.GenericClientTool;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

/**
 * @author frank
 * 
 */
public class MapLayoutWidget extends LayoutContainer {

	private MapWidget mapWidget;
	private MapOptions defaultMapOptions;
	private Map map;
	private Layer layer;
	private Layer osm;
	private ContentPanel center;

	private List<GenericClientTool> tools;

	private DrawFeature drawPolygon;
	private Vector vector;

	/**
	 * 
	 */
	public MapLayoutWidget() {
		super();
		this.createMapOption(true);
	}

	private void createMapOption(boolean isGoogle) {

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

		this.initVectorLayer(isGoogle);
	}

	private void createOSM() {
		OSMOptions osmOption = new OSMOptions();
		// osmOption.setDisplayOutsideMaxExtent(true);
		// osmOption.setWrapDateLine(true);

		this.osm = OSM
				.THIS("OpenStreetMap", OpenLayers.getProxyHost()
						+ "http://tile.openstreetmap.org/${z}/${x}/${y}.png",
						osmOption);// OSM.Mapnik("OpenStreetMap", osmOption);
		this.map.addLayer(osm);
	}

	private void initVectorLayer(boolean isGoogle) {
		VectorOptions vectorOption = new VectorOptions();
		vectorOption.setStyle(this.createStyle());
		vectorOption.setDisplayInLayerSwitcher(false);
		this.vector = new Vector("DGWATCH Vector Layer", vectorOption);
		this.map.addLayer(vector);

		this.initDrawFeatures(isGoogle);
	}

	private void initDrawFeatures(final boolean isGoogle) {
		FeatureAddedListener listener = new FeatureAddedListener() {
			public void onFeatureAdded(VectorFeature vf) {
				org.gwtopenmaps.openlayers.client.geometry.Polygon aoi = org.gwtopenmaps.openlayers.client.geometry.Polygon
						.narrowToPolygon(vf.getGeometry().getJSObject());

				if (isGoogle)
					aoi.transform(new Projection("EPSG:900913"),
							new Projection("EPSG:4326"));

				Dispatcher.forwardEvent(DGWATCHEvents.INJECT_WKT,
						aoi.toString());

				Dispatcher.forwardEvent(DGWATCHEvents.DISABLE_DRAW_BUTTON);
			}
		};

		DrawFeatureOptions drawPolygonFeatureOptions = new DrawFeatureOptions();
		drawPolygonFeatureOptions.onFeatureAdded(listener);

		this.drawPolygon = new DrawFeature(vector, new PolygonHandler(),
				drawPolygonFeatureOptions);

		this.map.addControl(this.drawPolygon);
	}

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

	public void onAddToCenterPanel(ContentPanel center) {
		this.center = center;
		center.add(mapWidget);
		center.layout();

		this.map.zoomToMaxExtent();
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
	 * @return the map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * @return the mapWidget
	 */
	public MapWidget getMapWidget() {
		return mapWidget;
	}

	public void updateMapSize() {
		this.map.updateSize();
		this.center.layout();
	}

	// private void createBaseGoogleLayer() {
	// GoogleOptions option = new GoogleOptions();
	// option.setType(GMapType.G_NORMAL_MAP);
	// option.setSphericalMercator(true);
	//
	// layer = new Google("Google Normal", option);
	// this.map.addLayer(layer);
	// }

	/**
	 * Erase all Features added to Vector Layer
	 */
	public void eraseFeatures() {
		this.vector.destroyFeatures();
	}

	/**
	 * @return the tools
	 */
	public List<GenericClientTool> getTools() {
		return tools;
	}

	/**
	 * @param tools
	 *            the tools to set
	 */
	public void setTools(List<GenericClientTool> tools) {
		Collections.sort(tools);
		this.tools = tools;
	}

	/**
	 * activate draw feature control on the map
	 */
	public void activateDrawFeature() {
		this.drawPolygon.activate();
	}

	/**
	 * deactivate draw feature control on the map
	 */
	public void deactivateDrawFeature() {
		this.drawPolygon.deactivate();
	}

}
