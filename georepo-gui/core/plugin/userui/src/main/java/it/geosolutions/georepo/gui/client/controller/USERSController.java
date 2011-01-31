/*
 * $ Header: it.geosolutions.georepo.gui.client.controller.USERSController,v. 0.1 28-gen-2011 11.49.40 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 28-gen-2011 11.49.40 $
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
import it.geosolutions.georepo.gui.client.model.Rule;
import it.geosolutions.georepo.gui.client.service.GsUsersManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.GsUsersManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.InstancesManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.InstancesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.WorkspacesManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.WorkspacesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.RuleGridWidget;
import it.geosolutions.georepo.gui.client.widget.tab.GsUsersTabItem;
import it.geosolutions.georepo.gui.client.widget.tab.ProfilesTabItem;
import it.geosolutions.georepo.gui.client.widget.tab.RulesTabItem;
import it.geosolutions.georepo.gui.client.widget.tab.TabWidget;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.grid.Grid;

// TODO: Auto-generated Javadoc
/**
 * The Class USERSController.
 */
public class USERSController extends Controller {

    /** The Constant USERS_TAB_ITEM_ID. */
    private static final String USERS_TAB_ITEM_ID = "UsersTabItem";

    /** The Constant PROFILES_TAB_ITEM_ID. */
    private static final String PROFILES_TAB_ITEM_ID = "ProfilesTabItem";

    /** The Constant RULES_TAB_ITEM_ID. */
    private static final String RULES_TAB_ITEM_ID = "RulesTabItem";

    /** The gs manager service remote. */
    private GsUsersManagerServiceRemoteAsync gsManagerServiceRemote = GsUsersManagerServiceRemote.Util
            .getInstance();

    /** The profiles manager service remote. */
    private ProfilesManagerServiceRemoteAsync profilesManagerServiceRemote = ProfilesManagerServiceRemote.Util
            .getInstance();

    /** The instances manager service remote. */
    private InstancesManagerServiceRemoteAsync instancesManagerServiceRemote = InstancesManagerServiceRemote.Util
            .getInstance();

    private WorkspacesManagerServiceRemoteAsync workspacesManagerServiceRemote = WorkspacesManagerServiceRemote.Util
            .getInstance();

    /** The rules manager service remote. */
    private RulesManagerServiceRemoteAsync rulesManagerServiceRemote = RulesManagerServiceRemote.Util
            .getInstance();

    /** The tab widget. */
    private TabWidget tabWidget;

    // /** The feature remote. */
    // private FeatureServiceRemoteAsync featureRemote = FeatureServiceRemote.Util.getInstance();

    // /** The tab widget. */
    // private TabWidget tabWidget;

    // /** The aoi search widget. */
    // private GeoRepoSearchWidget<AOI> aoiSearchWidget;
    //
    // /** The geo constraint search widget. */
    // private GeoRepoSearchWidget<GeoConstraint> geoConstraintSearchWidget;

    /**
     * Instantiates a new uSERS controller.
     */
    public USERSController() {
        registerEventTypes(GeoRepoEvents.INIT_MAPS_UI_MODULE,
                GeoRepoEvents.ATTACH_BOTTOM_TAB_WIDGETS, GeoRepoEvents.UPDATE_RULES_GRID_COMBOS);
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
        // this.aoiSearchWidget = new SearchPagAOIWidget(this.aoiRemote);
        // this.geoConstraintSearchWidget = new SearchPagingGeoConstraintWidget(this.membersRemote);
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

        if (event.getType() == GeoRepoEvents.UPDATE_RULES_GRID_COMBOS)
            onUpdateRuleRequestsCombo(event);

        // if (event.getType() == GeoRepoEvents.RESET_RSS_GRID)
        // onResetRSSGrid();

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
            tabWidget.add(new ProfilesTabItem(PROFILES_TAB_ITEM_ID, profilesManagerServiceRemote));
            tabWidget.add(new RulesTabItem(RULES_TAB_ITEM_ID, rulesManagerServiceRemote,
                    gsManagerServiceRemote, profilesManagerServiceRemote,
                    instancesManagerServiceRemote, workspacesManagerServiceRemote));
        }
    }

    /**
     * On update rule requests combo.
     * 
     * @param event
     *            the event
     */
    private void onUpdateRuleRequestsCombo(AppEvent event) {
        if (tabWidget != null) {
            Object tabData = event.getData();

            if (tabData instanceof Rule) {
                Rule model = (Rule) tabData;

                RulesTabItem rulesTabItem = (RulesTabItem) tabWidget
                        .getItemByItemId(RULES_TAB_ITEM_ID);
                final RuleGridWidget rulesInfoWidget = rulesTabItem.getRuleManagementWidget()
                        .getRulesInfo();
                final Grid<Rule> grid = rulesInfoWidget.getGrid();
                grid.getStore().remove(model);
                grid.getStore().add(model);
                grid.repaint();
            }
        }
    }

    /**
     * Forward to tab widget.
     * 
     * @param event
     *            the event
     */
    private void forwardToTabWidget(AppEvent event) {
        // this.tabWidget.fireEvent(event.getType(), event);
    }

}
