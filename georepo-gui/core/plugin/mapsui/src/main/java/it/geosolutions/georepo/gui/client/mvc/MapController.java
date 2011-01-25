/*
 * $ Header: it.geosolutions.georepo.gui.client.mvc.MapController,v. 0.1 25-gen-2011 11.30.32 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.30.32 $
 *
 * ====================================================================
 *
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 * http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
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
package it.geosolutions.georepo.gui.client.mvc;

import it.geosolutions.georepo.gui.client.GeoRepoEvents;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

// TODO: Auto-generated Javadoc
/**
 * The Class MapController.
 */
public class MapController extends Controller {

    /** The map view. */
    private MapView mapView;

    /**
     * Instantiates a new map controller.
     */
    public MapController() {
        registerEventTypes(
                GeoRepoEvents.INIT_MAPS_UI_MODULE,// DGWATCHEvents.INIT_DGWATCH_MAIN_UI,
                GeoRepoEvents.ATTACH_MAP_WIDGET, GeoRepoEvents.UPDATE_MAP_SIZE,
                GeoRepoEvents.ATTACH_TOOLBAR, GeoRepoEvents.ACTIVATE_DRAW_FEATURES,
                GeoRepoEvents.DEACTIVATE_DRAW_FEATURES, GeoRepoEvents.ERASE_AOI_FEATURES,
                GeoRepoEvents.ENABLE_DRAW_BUTTON, GeoRepoEvents.DISABLE_DRAW_BUTTON,
                GeoRepoEvents.DRAW_AOI_ON_MAP, GeoRepoEvents.ZOOM_TO_CENTER,
                GeoRepoEvents.ADMIN_MODE_CHANGE, GeoRepoEvents.LOGIN_SUCCESS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.Controller#initialize()
     */
    @Override
    public void initialize() {
        this.mapView = new MapView(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.Controller#handleEvent(com.extjs.gxt.ui.client
     * .mvc.AppEvent)
     */
    @Override
    public void handleEvent(AppEvent event) {
        forwardToView(mapView, event);

    }

}
