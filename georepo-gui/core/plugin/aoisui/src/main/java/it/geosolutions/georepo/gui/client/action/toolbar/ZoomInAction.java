/*
 * $Header: it.geosolutions.georepo.gui.client.action.toolbar.ZoomInAction,v. 0.1 27/lug/2010 11.21.33 created by frank $
 * $Revision: 0.1 $
 * $Date: 27/lug/2010 11.21.33 $
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
package it.geosolutions.georepo.gui.client.action.toolbar;


import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import com.extjs.gxt.ui.client.event.BaseEvent;
import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.MapWidget;

import it.geosolutions.georepo.gui.client.Category;
import it.geosolutions.georepo.gui.client.action.ToolbarMapAction;

/**
 * @author frank
 * 
 */
public class ZoomInAction extends ToolbarMapAction {

	private MapWidget mapWidget;

	private int zoomFactor = 1;

	public ZoomInAction(MapWidget mapWidget) {
		super(I18nProvider.getMessages().zoomInToolTip(), Category.DGWATCH_ZOOM_IN);

		this.mapWidget = mapWidget;
	}

    public void handleEvent(BaseEvent baseEvent) {
        LonLat center = this.mapWidget.getMap().getCenter();
        int oldZoom = this.mapWidget.getMap().getZoom();
        this.mapWidget.getMap().setCenter(center, oldZoom + this.zoomFactor);
    }
}
