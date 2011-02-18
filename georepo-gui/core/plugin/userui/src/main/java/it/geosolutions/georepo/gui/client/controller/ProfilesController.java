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
import it.geosolutions.georepo.gui.client.model.BeanKeyValue;
import it.geosolutions.georepo.gui.client.model.Profile;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.view.ProfilesView;
import it.geosolutions.georepo.gui.client.widget.ProfileGridWidget;
import it.geosolutions.georepo.gui.client.widget.tab.ProfilesTabItem;
import it.geosolutions.georepo.gui.client.widget.tab.TabWidget;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class RulesController.
 */
public class ProfilesController extends Controller {

    /** The Constant PROFILES_TAB_ITEM_ID. */
    private static final String PROFILES_TAB_ITEM_ID = "ProfilesTabItem";

    /** The profiles manager service remote. */
    private ProfilesManagerServiceRemoteAsync profilesManagerServiceRemote = ProfilesManagerServiceRemote.Util
            .getInstance();

    /** The tab widget. */
    private TabWidget tabWidget;
    
    /** The users view. */
    private ProfilesView profilesView;

    /**
     * Instantiates a new uSERS controller.
     */
    public ProfilesController() {
        registerEventTypes(
                GeoRepoEvents.INIT_MAPS_UI_MODULE,
                
                GeoRepoEvents.UPDATE_PROFILE,
                GeoRepoEvents.DELETE_PROFILE,
                GeoRepoEvents.SAVE_PROFILE,
                
                GeoRepoEvents.CREATE_NEW_PROFILE,
                
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
        this.profilesView = new ProfilesView(this);
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

        if (event.getType() == GeoRepoEvents.UPDATE_PROFILE ||
                event.getType() == GeoRepoEvents.SAVE_PROFILE)
            onSaveProfile(event);

        if (event.getType() == GeoRepoEvents.DELETE_PROFILE)
            onDeleteProfile(event);
        
         forwardToView(profilesView, event);
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
            tabWidget.add(new ProfilesTabItem(PROFILES_TAB_ITEM_ID, profilesManagerServiceRemote));
        }
    }

    /**
     * 
     * @param event
     */
    private void onSaveProfile(AppEvent event) {
        if (tabWidget != null) {

            ProfilesTabItem profilesTabItem = (ProfilesTabItem) tabWidget.getItemByItemId(PROFILES_TAB_ITEM_ID);
            final ProfileGridWidget profilesInfoWidget = profilesTabItem.getProfileManagementWidget().getProfilesInfo();
            final Grid<Profile> grid = profilesInfoWidget.getGrid();

            if (grid != null && grid.getStore() != null && event.getData() != null && event.getData() instanceof Profile) {
                
                Profile profile = event.getData();
                
                profilesManagerServiceRemote.saveProfile(profile, new AsyncCallback<PagingLoadResult<Profile>>() {

                    public void onFailure(Throwable caught) {

                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE,
                                new String[] {
                                        /* I18nProvider.getMessages().ruleServiceName() */ "Profile Service",
                                        /* I18nProvider.getMessages().ruleFetchFailureMessage() */ "Error occurred while saving profile!" });
                    }

                    public void onSuccess(PagingLoadResult<Profile> result) {

                        //grid.getStore().sort(BeanKeyValue.NAME.getValue(), SortDir.ASC);<<-- ric mod 20100215
                        grid.getStore().getLoader().load();
                        grid.repaint();

                        Dispatcher.forwardEvent(
                                GeoRepoEvents.BIND_MEMBER_DISTRIBUTION_NODES, result);
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
                                new String[] {
                                        /* TODO: I18nProvider.getMessages().ruleServiceName()*/ "Profile Service" ,
                                        /* TODO: I18nProvider.getMessages().ruleFetchSuccessMessage() */ "Profile saved successfully!" });
                    }
                });
                
            }
        }
    }

    /**
     * 
     * @param event
     */
    private void onDeleteProfile(AppEvent event) {
        if (tabWidget != null) {

            ProfilesTabItem profilesTabItem = (ProfilesTabItem) tabWidget.getItemByItemId(PROFILES_TAB_ITEM_ID);
            final ProfileGridWidget profilesInfoWidget = profilesTabItem.getProfileManagementWidget().getProfilesInfo();
            final Grid<Profile> grid = profilesInfoWidget.getGrid();

            if (grid != null && grid.getStore() != null && event.getData() != null && event.getData() instanceof Profile) {
                
                Profile profile = event.getData();
                
                profilesManagerServiceRemote.deleteProfile(profile, new AsyncCallback<PagingLoadResult<Profile>>() {

                    public void onFailure(Throwable caught) {

                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE,
                                new String[] {
                                        /* TODO: I18nProvider.getMessages().ruleServiceName() */ "Profile Service",
                                        /* TODO: I18nProvider.getMessages().ruleFetchFailureMessage() */ "Error occurred while removing Profile!" });
                    }

                    public void onSuccess(PagingLoadResult<Profile> result) {

                        //grid.getStore().sort(BeanKeyValue.USER_NAME.getValue(), SortDir.ASC);<<-- ric mod 20100215
                        grid.getStore().getLoader().load();
                        grid.repaint();

                        Dispatcher.forwardEvent(
                                GeoRepoEvents.BIND_MEMBER_DISTRIBUTION_NODES, result);
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
                                new String[] {
                                        /* TODO: I18nProvider.getMessages().ruleServiceName()*/ "Profile Service" ,
                                        /* TODO: I18nProvider.getMessages().ruleFetchSuccessMessage() */ "Profile removed successfully!" });
                    }
                });
                
            }
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
         this.tabWidget.fireEvent(event.getType(), event);
    }

}
