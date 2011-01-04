/*
 * $ Header: it.geosolutions.georepo.gui.client.mvc.WatchesView,v. 0.1 3-gen-2011 16.52.35 created by afabiani <alessio.fabiani at geo-solutions.it> $
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
import it.geosolutions.georepo.gui.client.model.User;
import it.geosolutions.georepo.gui.client.widget.WatchesManagementWidget;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.ContentPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class WatchesView.
 */
public class WatchesView extends View {

    /** The wm widget. */
    private WatchesManagementWidget wmWidget;

    /**
     * Instantiates a new watches view.
     * 
     * @param controller
     *            the controller
     */
    public WatchesView(Controller controller) {
        super(controller);
        this.wmWidget = new WatchesManagementWidget();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.View#handleEvent(com.extjs.gxt.ui.client.mvc.AppEvent)
     */
    @Override
    protected void handleEvent(AppEvent event) {
        if (event.getType() == DGWATCHEvents.ATTACH_WATCHES_WIDGET)
            onAttachWatchesWidget(event);
        if (event.getType() == DGWATCHEvents.LOGIN_SUCCESS)
            onLoginSuccess(event);
    }

    /**
     * On attach watches widget.
     * 
     * @param event
     *            the event
     */
    private void onAttachWatchesWidget(AppEvent event) {
        ContentPanel east = (ContentPanel) event.getData();
        east.add(this.wmWidget);
        east.layout();
    }

    /**
     * Gets the wm widget.
     * 
     * @return the wm widget
     */
    public WatchesManagementWidget getWmWidget() {
        return wmWidget;
    }

    /**
     * On login success.
     * 
     * @param event
     *            the event
     */
    private void onLoginSuccess(AppEvent event) {
        this.wmWidget.injectSecurity(((User) event.getData()).getGrantedAuthorizations());
    }

}
