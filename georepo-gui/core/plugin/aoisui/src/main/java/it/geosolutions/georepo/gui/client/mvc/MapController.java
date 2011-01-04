/*
 * $ Header: it.geosolutions.georepo.gui.client.mvc.MapController,v. 0.1 3-gen-2011 16.52.35 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 16.52.35 $
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
package it.geosolutions.georepo.gui.client.mvc;

import it.geosolutions.georepo.gui.client.DGWATCHEvents;

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
                DGWATCHEvents.INIT_AOIS_UI_MODULE,// DGWATCHEvents.INIT_DGWATCH_MAIN_UI,
                DGWATCHEvents.ATTACH_MAP_WIDGET, DGWATCHEvents.UPDATE_MAP_SIZE,
                DGWATCHEvents.ATTACH_TOOLBAR, DGWATCHEvents.ACTIVATE_DRAW_FEATURES,
                DGWATCHEvents.DEACTIVATE_DRAW_FEATURES, DGWATCHEvents.ERASE_AOI_FEATURES,
                DGWATCHEvents.ENABLE_DRAW_BUTTON, DGWATCHEvents.DISABLE_DRAW_BUTTON,
                DGWATCHEvents.DRAW_AOI_ON_MAP, DGWATCHEvents.ZOOM_TO_CENTER,
                DGWATCHEvents.ADMIN_MODE_CHANGE, DGWATCHEvents.LOGIN_SUCCESS);
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
