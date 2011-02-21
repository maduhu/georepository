/*
 * $ Header: it.geosolutions.georepo.gui.client.controller.UsersController,v. 0.1 10-feb-2011 11.34.29 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 10-feb-2011 11.34.29 $
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
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.BeanKeyValue;
import it.geosolutions.georepo.gui.client.model.GSUser;
import it.geosolutions.georepo.gui.client.service.GsUsersManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.GsUsersManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.view.UsersView;
import it.geosolutions.georepo.gui.client.widget.UserGridWidget;
import it.geosolutions.georepo.gui.client.widget.tab.GsUsersTabItem;
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
 * The Class UsersController.
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

    /** The users view. */
    private UsersView usersView;
    
    /**
     * Instantiates a new users controller.
     */
    public UsersController() {
        registerEventTypes(
                GeoRepoEvents.INIT_MAPS_UI_MODULE,
                
                GeoRepoEvents.CREATE_NEW_USER,
                GeoRepoEvents.SAVE_USER,
                GeoRepoEvents.UPDATE_USER,
                GeoRepoEvents.DELETE_USER,
                
                GeoRepoEvents.ATTACH_BOTTOM_TAB_WIDGETS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.Controller#initialize()
     */
    @Override
    protected void initialize() {
        this.usersView = new UsersView(this);
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

        if (event.getType() == GeoRepoEvents.UPDATE_USER ||
                event.getType() == GeoRepoEvents.SAVE_USER)
            onSaveUser(event);

        if (event.getType() == GeoRepoEvents.DELETE_USER)
            onDeleteUser(event);
        
         forwardToView(usersView, event);
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
     * On update profile.
     * 
     * @param event
     *            the event
     */
    private void onSaveUser(AppEvent event) {
        if (tabWidget != null) {

            GsUsersTabItem usersTabItem = (GsUsersTabItem) tabWidget.getItemByItemId(USERS_TAB_ITEM_ID);
            final UserGridWidget usersInfoWidget = usersTabItem.getUserManagementWidget().getUsersInfo();
            final Grid<GSUser> grid = usersInfoWidget.getGrid();

            if (grid != null && grid.getStore() != null && event.getData() != null && event.getData() instanceof GSUser) {
                
                GSUser user = event.getData();
                
                gsManagerServiceRemote.saveGsUser(user, new AsyncCallback<PagingLoadResult<GSUser>>() {

                    public void onFailure(Throwable caught) {

                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE,
                                new String[] {
                                        I18nProvider.getMessages().ruleServiceName(),
                                        I18nProvider.getMessages()
                                                .ruleFetchFailureMessage() });
                    }

                    public void onSuccess(PagingLoadResult<GSUser> result) {

                        //grid.getStore().sort(BeanKeyValue.USER_NAME.getValue(),SortDir.ASC);<<-- ric mod 20100215
                        grid.getStore().getLoader().load();
                        grid.repaint();

                        Dispatcher.forwardEvent(
                                GeoRepoEvents.BIND_MEMBER_DISTRIBUTION_NODES, result);
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
                                new String[] {
                                        /* TODO: I18nProvider.getMessages().ruleServiceName()*/ "User Service" ,
                                        /* TODO: I18nProvider.getMessages().ruleFetchSuccessMessage() */ "User saved successfully!" });
                    }
                });
                
            }
        }
    }

    /**
     * On delete profile.
     * 
     * @param event
     *            the event
     */
    private void onDeleteUser(AppEvent event) {
        if (tabWidget != null) {

            GsUsersTabItem usersTabItem = (GsUsersTabItem) tabWidget.getItemByItemId(USERS_TAB_ITEM_ID);
            final UserGridWidget usersInfoWidget = usersTabItem.getUserManagementWidget().getUsersInfo();
            final Grid<GSUser> grid = usersInfoWidget.getGrid();

            if (grid != null && grid.getStore() != null && event.getData() != null && event.getData() instanceof GSUser) {
                
                GSUser user = event.getData();
                
                gsManagerServiceRemote.deleteGsUser(user, new AsyncCallback<PagingLoadResult<GSUser>>() {

                    public void onFailure(Throwable caught) {

                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE,
                                new String[] {
                                        I18nProvider.getMessages().ruleServiceName(),
                                        I18nProvider.getMessages()
                                                .ruleFetchFailureMessage() });
                    }

                    public void onSuccess(PagingLoadResult<GSUser> result) {

                        //grid.getStore().sort(BeanKeyValue.USER_NAME.getValue(),SortDir.ASC);//<<-- ric mod 20100215
                        grid.getStore().getLoader().load();
                        grid.repaint();

                        Dispatcher.forwardEvent(
                                GeoRepoEvents.BIND_MEMBER_DISTRIBUTION_NODES, result);
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
                                new String[] {
                                        /* TODO: I18nProvider.getMessages().ruleServiceName()*/ "User Service" ,
                                        /* TODO: I18nProvider.getMessages().ruleFetchSuccessMessage() */ "User removed successfully!" });
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
