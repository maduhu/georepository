/*
 * $ Header: it.geosolutions.georepo.gui.client.controller.LoginController,v. 0.1 25-gen-2011 11.23.48 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.23.48 $
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

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.model.User;
import it.geosolutions.georepo.gui.client.service.LoginRemote;
import it.geosolutions.georepo.gui.client.service.LoginRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.ChooseUserPagWidget;
import it.geosolutions.georepo.gui.client.widget.GeoRepoChooserWidget;
import it.geosolutions.georepo.gui.client.widget.GeoRepoSearchWidget;
import it.geosolutions.georepo.gui.client.widget.GeoRepoUpdateWidget;
import it.geosolutions.georepo.gui.client.widget.LoginWidget;
import it.geosolutions.georepo.gui.client.widget.SearchPagUserWidget;
import it.geosolutions.georepo.gui.client.widget.UpdateUserWidget;
import it.geosolutions.georepo.gui.client.widget.UserManagementWidget;
import it.geosolutions.georepo.gui.client.widget.LoginStatus.EnumLoginStatus;
import it.geosolutions.georepo.gui.client.widget.SearchStatus.EnumSearchStatus;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginController.
 */
public class LoginController extends Controller {

    /** The login remote. */
    private LoginRemoteAsync loginRemote = LoginRemote.Util.getInstance();

    /** The login widget. */
    private LoginWidget loginWidget;

    /** The choose profile widget. */
    private GeoRepoChooserWidget<User> chooseUserWidget;

    /** The profile management widget. */
    private UserManagementWidget userManagementWidget;

    /** The search widget. */
    private GeoRepoSearchWidget<User> searchWidget;

    /** The update profile. */
    private GeoRepoUpdateWidget<User> updateUser;

    /**
     * Instantiates a new login controller.
     */
    public LoginController() {
        registerEventTypes(
                GeoRepoEvents.INIT_USER_UI_MODULE,// DGWATCHEvents.INIT,
                GeoRepoEvents.INIT_GEOREPO_WIDGET, GeoRepoEvents.LOGIN,
                GeoRepoEvents.ATTACH_USER_WIDGET, GeoRepoEvents.SHOW_CHOOSER_USER_WIDGET,
                GeoRepoEvents.SHOW_ADD_USER_WIDGET, GeoRepoEvents.SAVE_USER,
                GeoRepoEvents.SHOW_SEARCH_USER_WIDGET, GeoRepoEvents.BIND_SELECTED_USER,
                GeoRepoEvents.DELETE_USER, GeoRepoEvents.SHOW_UPDATE_USER_WIDGET,
                GeoRepoEvents.UPDATE_USER, GeoRepoEvents.NOTIFY_UNSHARE_ERROR,
                GeoRepoEvents.NOTIFY_UNSHARE_SUCCESS, GeoRepoEvents.UNBIND_USER_WIDGET,
                GeoRepoEvents.LOGOUT, GeoRepoEvents.CHECK_RELATED_USERS_COUNT);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.Controller#initialize()
     */
    @Override
    protected void initialize() {
        this.loginWidget = new LoginWidget();

        this.loginWidget.addListener(Events.Hide, new Listener<WindowEvent>() {

            public void handleEvent(WindowEvent be) {
                Dispatcher.forwardEvent(GeoRepoEvents.INIT_GEOREPO_MAIN_UI);
                loginWidget.reset();
            }
        });

        initWidget();
    }

    /**
     * Inits the widget.
     */
    private void initWidget() {
        this.chooseUserWidget = new ChooseUserPagWidget(loginRemote);
        this.searchWidget = new SearchPagUserWidget(this.loginRemote);
        this.updateUser = new UpdateUserWidget();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.Controller#handleEvent(com.extjs.gxt.ui.client.mvc.AppEvent)
     */
    @Override
    public void handleEvent(AppEvent event) {
        if (event.getType() == GeoRepoEvents.INIT_GEOREPO_WIDGET)
            onShowLoginWidget();

        if (event.getType() == GeoRepoEvents.LOGIN)
            onLogin(event);

//        if (event.getType() == GeoRepoEvents.ATTACH_USER_WIDGET)
//            onAttachUserWidget(event);

        if (event.getType() == GeoRepoEvents.SHOW_CHOOSER_USER_WIDGET)
            onShowChooseUserWidget();

        if (event.getType() == GeoRepoEvents.SHOW_ADD_USER_WIDGET)
            onShowAddUserWidget();

        if (event.getType() == GeoRepoEvents.SHOW_SEARCH_USER_WIDGET)
            onShowSearchUSerWidget();

        if (event.getType() == GeoRepoEvents.BIND_SELECTED_USER)
            onBindSelectedUser(event);

        if (event.getType() == GeoRepoEvents.SHOW_UPDATE_USER_WIDGET)
            onShowUpdateUserWidget(event);

        if (event.getType() == GeoRepoEvents.NOTIFY_UNSHARE_ERROR)
            onNotifyUnshareError();

        if (event.getType() == GeoRepoEvents.NOTIFY_UNSHARE_SUCCESS)
            onNotifyUnshareSuccess();

//        if (event.getType() == GeoRepoEvents.UNBIND_USER_WIDGET)
//            onUnbindUserWidget();

        if (event.getType() == GeoRepoEvents.LOGOUT)
            onLogout();

        if (event.getType() == GeoRepoEvents.CHECK_RELATED_USERS_COUNT)
            onCheckRelatedUsersCount(event);
    }

    /**
     * On check related users count.
     * 
     * @param event
     *            the event
     */
    private void onCheckRelatedUsersCount(AppEvent event) {
//        final AOI aoi = (AOI) event.getData();

        // TODO REFACTOR GG
        // this.loginRemote.getRelatedUsersCount(aoi.getId(),
        // new AsyncCallback<Integer>() {
        //
        // public void onFailure(Throwable caught) {
        // Dispatcher
        // .forwardEvent(
        // DGWATCHEvents.SEND_ERROR_MESSAGE,
        // new String[] { "AOI User Preferences",
        // "There was an error loading the data." });
        // }
        //
        // public void onSuccess(Integer result) {
        // int count = result.intValue();
        // if (count > 0) {
        // MessageBox
        // .confirm(
        // "AOI User Preferences",
        // "There are users who have preferences on AOI. Do you want to see them ?",
        // new Listener<MessageBoxEvent>() {
        //
        // public void handleEvent(
        // MessageBoxEvent be) {
        // aoiUserContainer
        // .getAoiUserPref()
        // .setAoi(aoi);
        // aoiUserContainer.show();
        // }
        // });
        // } else
        // Dispatcher.forwardEvent(DGWATCHEvents.DELETE_AOI,
        // aoi);
        // }
        // });
    }

    /**
     * On logout.
     */
    private void onLogout() {
        this.loginRemote.logout(new AsyncCallback<Object>() {

            public void onFailure(Throwable caught) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE, new String[] {
                        "Logout Service", "There was an error in logout" });
            }

            public void onSuccess(Object result) {
                Dispatcher.forwardEvent(GeoRepoEvents.SESSION_EXPIRED);
            }
        });

    }

//    /**
//     * On unbind profile widget.
//     */
//    private void onUnbindUserWidget() {
//        userManagementWidget.getUserInfo().unBindModel();
//    }

    /**
 * On notify unshare success.
 */
    private void onNotifyUnshareSuccess() {
        this.chooseUserWidget.setSearchStatus(EnumSearchStatus.STATUS_SEARCH,
                EnumSearchStatus.STATUS_MESSAGE_AOI_UNSHARE);
        this.chooseUserWidget.getChooser().hide();
    }

    /**
     * On notify unshare error.
     */
    private void onNotifyUnshareError() {
        this.chooseUserWidget.setSearchStatus(EnumSearchStatus.STATUS_SEARCH_ERROR,
                EnumSearchStatus.STATUS_MESSAGE_AOI_UNSHARE_ERROR);
    }

    /**
     * On show update profile widget.
     * 
     * @param event
     *            the event
     */
    private void onShowUpdateUserWidget(AppEvent event) {
        this.updateUser.bind((User) event.getData());
        this.updateUser.show();
    }

    /**
     * On bind selected profile.
     * 
     * @param event
     *            the event
     */
    private void onBindSelectedUser(AppEvent event) {
        final User user = (User) event.getData();
        // TODO REFACTOR GG
        // this.loginRemote.getUserDetail(profile, new AsyncCallback<User>() {
        //
        // public void onFailure(Throwable caught) {
        // searchWidget.setSearchStatus(
        // EnumSearchStatus.STATUS_SEARCH_ERROR,
        // EnumSearchStatus.STATUS_MESSAGE_USER_DETAIL_ERROR);
        // }
        //
        // public void onSuccess(User result) {
        // searchWidget.setSearchStatus(EnumSearchStatus.STATUS_SEARCH,
        // EnumSearchStatus.STATUS_MESSAGE_USER_DETAIL);
        // searchWidget.cancel();
        // bindUser(result);
        // }
        // });

        // bindUser((User) event.getData());
    }

    /**
     * On show search u ser widget.
     */
    private void onShowSearchUSerWidget() {
        this.searchWidget.show();
    }

    /**
     * On show add profile widget.
     */
    private void onShowAddUserWidget() {
        // TODO REFACTOR GG
        // this.loginRemote.findUserNames(new AsyncCallback<List<RegUser>>() {
        //
        // public void onFailure(Throwable caught) {
        // try {
        // throw caught;
        // } catch (ApplicationException e) {
        // MessageBox.alert("Session Expired",
        // "The Session Expired. You must re-login.",
        // new Listener<MessageBoxEvent>() {
        //
        // public void handleEvent(MessageBoxEvent be) {
        // Dispatcher
        // .forwardEvent(DGWATCHEvents.SESSION_EXPIRED);
        // }
        // });
        // } catch (Throwable e) {
        // Dispatcher.forwardEvent(DGWATCHEvents.SEND_ERROR_MESSAGE,
        // new String[] { "Member Service",
        // "There was a problem loading data" });
        // }
        //
        // }
        //
        // public void onSuccess(List<RegUser> result) {
        // addUserWidget.fillStoreRegUser(result);
        // addUserWidget.show();
        // }
        // });

    }

    /**
     * On show choose profile widget.
     */
    private void onShowChooseUserWidget() {
        this.chooseUserWidget.getChooser().show();
    }

//    /**
//     * On attach profile widget.
//     * 
//     * @param event
//     *            the event
//     */
//    private void onAttachUserWidget(AppEvent event) {
//        ContentPanel east = (ContentPanel) event.getData();
//        this.userManagementWidget = new UserManagementWidget();
//        east.add(userManagementWidget);
//        east.layout();
//    }

    /**
 * On login.
 * 
 * @param event
 *            the event
 */
    private void onLogin(AppEvent event) {
        String[] values = (String[]) event.getData();
        this.loginRemote.authenticate(values[0], values[1], new AsyncCallback<User>() {

            public void onSuccess(User user) {
                if (user != null) {
                    loginWidget.setStatusLoginFinder(EnumLoginStatus.STATUS_LOGIN,
                            EnumLoginStatus.STATUS_MESSAGE_LOGIN);
                    loginWidget.hide();
                    //Dispatcher.forwardEvent(GeoRepoEvents.ZOOM_TO_CENTER);<<-- ric mod 20100217
                    //Dispatcher.forwardEvent(GeoRepoEvents.LOGIN_SUCCESS, user);<<-- ric mod 20100217
                } else {
                    loginWidget.setStatusLoginFinder(EnumLoginStatus.STATUS_NO_LOGIN,
                            EnumLoginStatus.STATUS_MESSAGE_NOT_LOGIN);
                }
            }

            public void onFailure(Throwable caught) {
                boolean handled = false;
                if (caught instanceof ApplicationException) {
                    ApplicationException appException = (ApplicationException) caught;
                    if (appException.getDetailedMessage().contains(
                            "it.geosolutions.georepo.security.exception.AuthException")) {
                        // Authentication failed
                        handled = true;
                        loginWidget.resetPassword();
                        loginWidget.setStatusLoginFinder(EnumLoginStatus.STATUS_LOGIN_ERROR,
                                EnumLoginStatus.STATUS_MESSAGE_NOT_LOGIN);
                    }
                }

                if (!handled) {
                    loginWidget.setStatusLoginFinder(EnumLoginStatus.STATUS_LOGIN_ERROR,
                            EnumLoginStatus.STATUS_MESSAGE_LOGIN_ERROR);
                }
            }
        });
    }

    /**
     * On show login widget.
     */
    private void onShowLoginWidget() {
        this.loginWidget.show();

    }

    // private void bindUser(User profile) {
    // this.userManagementWidget.getUserInfo().unBindModel();
    // this.userManagementWidget.getUserInfo().bindModel(profile);
    // this.userManagementWidget.enableButtons();
    //
    // Dispatcher.forwardEvent(DGWATCHEvents.USER_SELECTED, profile);
    //
    // // ///////////////////////////
    // // Reset the grids contents
    // // ///////////////////////////
    //
    // Dispatcher.forwardEvent(DGWATCHEvents.RESET_AOI_GRID);
    // Dispatcher.forwardEvent(DGWATCHEvents.RESET_RSS_GRID);
    // // Dispatcher.forwardEvent(DGWATCHEvents.ERASE_AOI_FEATURES);
    // }

}
