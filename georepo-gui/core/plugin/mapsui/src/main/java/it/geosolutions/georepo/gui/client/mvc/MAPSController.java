/*
 * $ Header: it.geosolutions.georepo.gui.client.mvc.AOISController,v. 0.1 3-gen-2011 16.52.35 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.service.FeatureServiceRemote;
import it.geosolutions.georepo.gui.client.service.FeatureServiceRemoteAsync;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

// TODO: Auto-generated Javadoc
/**
 * The Class AOISController.
 */
public class MAPSController extends Controller {

    /** The feature remote. */
    private FeatureServiceRemoteAsync featureRemote = FeatureServiceRemote.Util.getInstance();

//    /** The tab widget. */
//    private TabWidget tabWidget;

//    /** The aoi search widget. */
//    private GeoRepoSearchWidget<AOI> aoiSearchWidget;
//
//    /** The geo constraint search widget. */
//    private GeoRepoSearchWidget<GeoConstraint> geoConstraintSearchWidget;

    /**
     * Instantiates a new aOIS controller.
     */
    public MAPSController() {
        registerEventTypes(
                GeoRepoEvents.INIT_MAPS_UI_MODULE,// DGWATCHEvents.INIT_DGWATCH_WIDGET,
                GeoRepoEvents.SHOW_UPLOAD_WIDGET, GeoRepoEvents.ATTACH_AOI_WIDGET,
                GeoRepoEvents.ATTACH_BOTTOM_TAB_WIDGETS, GeoRepoEvents.AOI_MANAGEMENT_BIND,
                GeoRepoEvents.AOI_MANAGEMENT_UNBIND, GeoRepoEvents.SHOW_ADD_AOI,
                GeoRepoEvents.SHOW_ADD_GEOCONSTRAINT, GeoRepoEvents.INJECT_WKT,
                GeoRepoEvents.SAVE_AOI, GeoRepoEvents.SEARCH_AOI, GeoRepoEvents.BIND_SELECTED_AOI,
                GeoRepoEvents.DELETE_AOI, GeoRepoEvents.UPDATE_AOI,
                GeoRepoEvents.INJECT_WKT_FROM_SHP, GeoRepoEvents.SAVE_AOI_FROM_SHP,
                GeoRepoEvents.CHECK_AOI_OWNER, GeoRepoEvents.SEARCH_USER_GEORSS,
                GeoRepoEvents.RESET_AOI_GRID, GeoRepoEvents.RESET_RSS_GRID,
                GeoRepoEvents.CHECK_AOI_STATUS, GeoRepoEvents.ADMIN_MODE_CHANGE,
                GeoRepoEvents.ATTACH_GEOCONSTRAINT_AOI_WIDGET,
                GeoRepoEvents.BIND_SELECTED_GEOCONSTRAINT, GeoRepoEvents.DELETE_CONTENT,
                GeoRepoEvents.BIND_SELECTED_MEMBER, GeoRepoEvents.SAVE_GEOCONSTRAINT,
                GeoRepoEvents.ENABLE_DRAW_BUTTON, GeoRepoEvents.SEARCH_GEOCONSTRAINT,
                GeoRepoEvents.DELETE_GEOCONSTRAINT, GeoRepoEvents.SEARCH_MEMBER_WATCHES,
                GeoRepoEvents.LOAD_WATCH_AOI, GeoRepoEvents.RELOAD_GEOCONSTRAINTS,
                GeoRepoEvents.GEOCONSTRAINT_DELETED, GeoRepoEvents.RESET_WATCH_GRID,
                GeoRepoEvents.REFRESH_WATCH_GRID, GeoRepoEvents.MEMBER_GEOCONSTRAINT_UNBOUND);
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
//        if (event.getType() == GeoRepoEvents.ATTACH_BOTTOM_TAB_WIDGETS)
//            onAttachTabWidgets(event);

//        if (event.getType() == GeoRepoEvents.SEARCH_USER_GEORSS)
//            onSearchUserFeature(event);

//        if (event.getType() == GeoRepoEvents.RESET_RSS_GRID)
//            onResetRSSGrid();



//        forwardToView(aoiView, event);
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
