/*
 * $ Header: it.geosolutions.georepo.gui.client.controller.LoginController,v. 0.1 3-gen-2011 17.06.53 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 17.06.53 $
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
import com.extjs.gxt.ui.client.widget.ContentPanel;
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

    /** The choose user widget. */
    private GeoRepoChooserWidget<User> chooseUserWidget;

    /** The user management widget. */
    private UserManagementWidget userManagementWidget;

    /** The search widget. */
    private GeoRepoSearchWidget<User> searchWidget;

    /** The update user. */
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

        if (event.getType() == GeoRepoEvents.ATTACH_USER_WIDGET)
            onAttachUserWidget(event);

        if (event.getType() == GeoRepoEvents.SHOW_CHOOSER_USER_WIDGET)
            onShowChooseUserWidget();

        if (event.getType() == GeoRepoEvents.SHOW_ADD_USER_WIDGET)
            onShowAddUserWidget();

        if (event.getType() == GeoRepoEvents.SAVE_USER)
            onSaveUser(event);

        if (event.getType() == GeoRepoEvents.SHOW_SEARCH_USER_WIDGET)
            onShowSearchUSerWidget();

        if (event.getType() == GeoRepoEvents.BIND_SELECTED_USER)
            onBindSelectedUser(event);

        if (event.getType() == GeoRepoEvents.DELETE_USER)
            onDeleteUser(event);

        if (event.getType() == GeoRepoEvents.SHOW_UPDATE_USER_WIDGET)
            onShowUpdateUserWidget(event);

        if (event.getType() == GeoRepoEvents.UPDATE_USER)
            onUpdateUser(event);

        if (event.getType() == GeoRepoEvents.NOTIFY_UNSHARE_ERROR)
            onNotifyUnshareError();

        if (event.getType() == GeoRepoEvents.NOTIFY_UNSHARE_SUCCESS)
            onNotifyUnshareSuccess();

        if (event.getType() == GeoRepoEvents.UNBIND_USER_WIDGET)
            onUnbindUserWidget();

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

    /**
     * On unbind user widget.
     */
    private void onUnbindUserWidget() {
        userManagementWidget.getUserInfo().unBindModel();
    }

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
     * On update user.
     * 
     * @param event
     *            the event
     */
    private void onUpdateUser(AppEvent event) {
        final User user = (User) event.getData();

        // TODO REFACTOR GG
        // this.loginRemote.updateUser(user, new AsyncCallback<User>() {
        //
        // public void onFailure(Throwable caught) {
        // updateUser.setSaveStatus(EnumSaveStatus.STATUS_SAVE_ERROR,
        // EnumSaveStatus.STATUS_MESSAGE_SAVE_ERROR);
        // Dispatcher.forwardEvent(DGWATCHEvents.SEND_ERROR_MESSAGE,
        // new String[] { "Update User",
        // "An error occurred when updating the user " });
        // }
        //
        // @SuppressWarnings("deprecation")
        // public void onSuccess(User result) {
        // updateUser.setSaveStatus(EnumSaveStatus.STATUS_SAVE,
        // EnumSaveStatus.STATUS_MESSAGE_SAVE);
        // bindUser(result);
        // Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
        // new String[] { "Update User",
        // "he user has been successfully updated." });
        // updateUser.close();
        // }
        // });
    }

    /**
     * On show update user widget.
     * 
     * @param event
     *            the event
     */
    private void onShowUpdateUserWidget(AppEvent event) {
        this.updateUser.bind((User) event.getData());
        this.updateUser.show();
    }

    /**
     * On delete user.
     * 
     * @param event
     *            the event
     */
    private void onDeleteUser(AppEvent event) {
        final User user = (User) event.getData();
        // TODO REFACTOR GG
        // this.loginRemote.deleteUser(user.getId(), new AsyncCallback<Object>() {
        //
        // public void onFailure(Throwable caught) {
        // Dispatcher.forwardEvent(DGWATCHEvents.SEND_ERROR_MESSAGE,
        // new String[] { "Delete User",
        // "There was an error when deleting user" });
        // }
        //
        // public void onSuccess(Object result) {
        // Dispatcher.forwardEvent(
        // DGWATCHEvents.SEND_INFO_MESSAGE,
        // new String[] {
        // "Delete User",
        // "The user with User Name: "
        // + user.getUserName()
        // + " was successfully deleted." });
        // userManagementWidget.getUserInfo().unBindModel();
        // Dispatcher.forwardEvent(DGWATCHEvents.RESET_AOI_GRID);
        // Dispatcher.forwardEvent(DGWATCHEvents.RESET_RSS_GRID);
        // Dispatcher.forwardEvent(DGWATCHEvents.USER_SELECTED, null);
        // Dispatcher.forwardEvent(DGWATCHEvents.CHECK_AOI_OWNER, user);
        // }
        // });
    }

    /**
     * On bind selected user.
     * 
     * @param event
     *            the event
     */
    private void onBindSelectedUser(AppEvent event) {
        final User user = (User) event.getData();
        // TODO REFACTOR GG
        // this.loginRemote.getUserDetail(user, new AsyncCallback<User>() {
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
     * On save user.
     * 
     * @param event
     *            the event
     */
    private void onSaveUser(AppEvent event) {
        // TODO REFACTOR GG
        // this.loginRemote.saveUser((User) event.getData(),
        // new AsyncCallback<User>() {
        //
        // public void onFailure(Throwable caught) {
        // addUserWidget.setSaveStatus(
        // EnumSaveStatus.STATUS_SAVE_ERROR,
        // EnumSaveStatus.STATUS_MESSAGE_SAVE_ERROR);
        // Dispatcher
        // .forwardEvent(
        // DGWATCHEvents.SEND_ERROR_MESSAGE,
        // new String[] { "Save User",
        // "An error occurred while saving the user." });
        // }
        //
        // @SuppressWarnings("deprecation")
        // public void onSuccess(User result) {
        // addUserWidget.setSaveStatus(EnumSaveStatus.STATUS_SAVE,
        // EnumSaveStatus.STATUS_MESSAGE_SAVE);
        // bindUser(result);
        // Dispatcher.forwardEvent(
        // DGWATCHEvents.SEND_INFO_MESSAGE, new String[] {
        // "Save User",
        // "the user has been successfully saved with ID : "
        // + result.getId() });
        // addUserWidget.close();
        // }
        //
        // });

    }

    /**
     * On show add user widget.
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
     * On show choose user widget.
     */
    private void onShowChooseUserWidget() {
        this.chooseUserWidget.getChooser().show();
    }

    /**
     * On attach user widget.
     * 
     * @param event
     *            the event
     */
    private void onAttachUserWidget(AppEvent event) {
        ContentPanel east = (ContentPanel) event.getData();
        this.userManagementWidget = new UserManagementWidget();
        east.add(userManagementWidget);
        east.layout();
    }

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
                    Dispatcher.forwardEvent(GeoRepoEvents.ZOOM_TO_CENTER);
                    Dispatcher.forwardEvent(GeoRepoEvents.LOGIN_SUCCESS, user);
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

    // private void bindUser(User user) {
    // this.userManagementWidget.getUserInfo().unBindModel();
    // this.userManagementWidget.getUserInfo().bindModel(user);
    // this.userManagementWidget.enableButtons();
    //
    // Dispatcher.forwardEvent(DGWATCHEvents.USER_SELECTED, user);
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
