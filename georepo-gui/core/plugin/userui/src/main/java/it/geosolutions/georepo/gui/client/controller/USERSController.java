/*
 * $ Header: it.geosolutions.georepo.gui.client.controller.USERSController,v. 0.1 25-gen-2011 12.04.33 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 12.04.33 $
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
import it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.tab.GsUsersTabItem;
import it.geosolutions.georepo.gui.client.widget.tab.ProfilesTabItem;
import it.geosolutions.georepo.gui.client.widget.tab.RulesTabItem;
import it.geosolutions.georepo.gui.client.widget.tab.TabWidget;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

// TODO: Auto-generated Javadoc
/**
 * The Class USERSController.
 */
public class USERSController extends Controller {

    /** The gs manager service remote. */
    private GsUsersManagerServiceRemoteAsync gsManagerServiceRemote = GsUsersManagerServiceRemote.Util.getInstance();
    
    /** The profiles manager service remote. */
    private ProfilesManagerServiceRemoteAsync profilesManagerServiceRemote = ProfilesManagerServiceRemote.Util.getInstance();
    
    /** The rules manager service remote. */
    private RulesManagerServiceRemoteAsync rulesManagerServiceRemote = RulesManagerServiceRemote.Util.getInstance();
    
//    /** The feature remote. */
//    private FeatureServiceRemoteAsync featureRemote = FeatureServiceRemote.Util.getInstance();

//    /** The tab widget. */
//    private TabWidget tabWidget;

//    /** The aoi search widget. */
//    private GeoRepoSearchWidget<AOI> aoiSearchWidget;
//
//    /** The geo constraint search widget. */
//    private GeoRepoSearchWidget<GeoConstraint> geoConstraintSearchWidget;

    /**
 * Instantiates a new uSERS controller.
 */
    public USERSController() {
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
//        this.aoiSearchWidget = new SearchPagAOIWidget(this.aoiRemote);
//        this.geoConstraintSearchWidget = new SearchPagingGeoConstraintWidget(this.membersRemote);
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

//        if (event.getType() == GeoRepoEvents.SEARCH_USER_GEORSS)
//            onSearchUserFeature(event);

//        if (event.getType() == GeoRepoEvents.RESET_RSS_GRID)
//            onResetRSSGrid();



//        forwardToView(aoiView, event);
    }

    /**
     * On attach tab widgets.
     * 
     * @param event
     *            the event
     */
    private void onAttachTabWidgets(AppEvent event) {
        TabWidget tabWidget = (TabWidget)event.getData();
        tabWidget.add(new GsUsersTabItem(gsManagerServiceRemote));
        tabWidget.add(new ProfilesTabItem(profilesManagerServiceRemote));
        tabWidget.add(new RulesTabItem(rulesManagerServiceRemote));
    }

    /**
     * Forward to tab widget.
     * 
     * @param event
     *            the event
     */
    private void forwardToTabWidget(AppEvent event) {
//        this.tabWidget.fireEvent(event.getType(), event);
    }

}
