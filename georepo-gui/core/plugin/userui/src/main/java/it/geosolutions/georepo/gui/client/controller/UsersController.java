/*
 * $ Header: it.geosolutions.georepo.gui.client.controller.RulesController,v. 0.1 31-gen-2011 13.41.27 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 31-gen-2011 13.41.27 $
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
package it.geosolutions.georepo.gui.client.controller;

import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.service.GsUsersManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.GsUsersManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.tab.GsUsersTabItem;
import it.geosolutions.georepo.gui.client.widget.tab.TabWidget;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

// TODO: Auto-generated Javadoc
/**
 * The Class RulesController.
 */
public class UsersController extends Controller {

    /** The Constant USERS_TAB_ITEM_ID. */
    private static final String USERS_TAB_ITEM_ID = "UsersTabItem";

    /** The gs manager service remote. */
    private GsUsersManagerServiceRemoteAsync gsManagerServiceRemote = GsUsersManagerServiceRemote.Util
            .getInstance();

    /** The profiles manager service remote. */
    private ProfilesManagerServiceRemoteAsync profilesManagerServiceRemote = ProfilesManagerServiceRemote.Util
            .getInstance();

    /** The tab widget. */
    private TabWidget tabWidget;

    /**
     * Instantiates a new uSERS controller.
     */
    public UsersController() {
        registerEventTypes(
                GeoRepoEvents.INIT_MAPS_UI_MODULE,
                GeoRepoEvents.ATTACH_BOTTOM_TAB_WIDGETS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.Controller#initialize()
     */
    @Override
    protected void initialize() {
        initWidget();
    }

    /**
     * Inits the widget.
     */
    private void initWidget() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.Controller#handleEvent(com.extjs.gxt.ui.client
     * .mvc.AppEvent)
     */
    @Override
    public void handleEvent(AppEvent event) {
        if (event.getType() == GeoRepoEvents.ATTACH_BOTTOM_TAB_WIDGETS)
            onAttachTabWidgets(event);

        // forwardToView(aoiView, event);
    }

    /**
     * On attach tab widgets.
     * 
     * @param event
     *            the event
     */
    private void onAttachTabWidgets(AppEvent event) {
        if (tabWidget == null) {
            tabWidget = (TabWidget) event.getData();
            tabWidget.add(new GsUsersTabItem(USERS_TAB_ITEM_ID, gsManagerServiceRemote,
                    profilesManagerServiceRemote));
        }
    }

    /**
     * Forward to tab widget.
     * 
     * @param event
     *            the event
     */
    @SuppressWarnings("unused")
    private void forwardToTabWidget(AppEvent event) {
        // this.tabWidget.fireEvent(event.getType(), event);
    }

}
