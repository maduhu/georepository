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

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.user.client.rpc.AsyncCallback;

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.model.User;
import it.geosolutions.georepo.gui.client.service.LoginRemote;
import it.geosolutions.georepo.gui.client.service.LoginRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.GeoRepoChooserWidget;
import it.geosolutions.georepo.gui.client.widget.GeoRepoSearchWidget;
import it.geosolutions.georepo.gui.client.widget.GeoRepoUpdateWidget;
import it.geosolutions.georepo.gui.client.widget.LoginStatus.EnumLoginStatus;
import it.geosolutions.georepo.gui.client.widget.LoginWidget;
import it.geosolutions.georepo.gui.client.widget.SearchPagUserWidget;
import it.geosolutions.georepo.gui.client.widget.SearchStatus.EnumSearchStatus;
import it.geosolutions.georepo.gui.client.widget.UpdateUserWidget;
import it.geosolutions.georepo.gui.client.widget.UserManagementWidget;


// TODO: Auto-generated Javadoc
/**
 * The Class LoginController.
 */
public class LoginController extends Controller
{

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
    public LoginController()
    {
        registerEventTypes(GeoRepoEvents.INIT_USER_UI_MODULE, GeoRepoEvents.INIT_GEOREPO_WIDGET,
            GeoRepoEvents.LOGIN, GeoRepoEvents.ATTACH_USER_WIDGET,
            GeoRepoEvents.SHOW_CHOOSER_USER_WIDGET, GeoRepoEvents.SHOW_ADD_USER_WIDGET,
            GeoRepoEvents.SAVE_USER, GeoRepoEvents.SHOW_SEARCH_USER_WIDGET,
            GeoRepoEvents.BIND_SELECTED_USER, GeoRepoEvents.DELETE_USER,
            GeoRepoEvents.SHOW_UPDATE_USER_WIDGET, GeoRepoEvents.UPDATE_USER,
            GeoRepoEvents.NOTIFY_UNSHARE_ERROR, GeoRepoEvents.NOTIFY_UNSHARE_SUCCESS,
            GeoRepoEvents.UNBIND_USER_WIDGET, GeoRepoEvents.LOGOUT,
            GeoRepoEvents.CHECK_RELATED_USERS_COUNT);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.extjs.gxt.ui.client.mvc.Controller#initialize()
     */
    @Override
    protected void initialize()
    {
        this.loginWidget = new LoginWidget();

        this.loginWidget.addListener(Events.Hide, new Listener<WindowEvent>()
            {

                public void handleEvent(WindowEvent be)
                {
                    Dispatcher.forwardEvent(GeoRepoEvents.INIT_GEOREPO_MAIN_UI);
                    loginWidget.reset();
                }
            });

        initWidget();
    }

    /**
     * Inits the widget.
     */
    private void initWidget()
    {
        this.searchWidget = new SearchPagUserWidget(this.loginRemote);
        this.updateUser = new UpdateUserWidget();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.extjs.gxt.ui.client.mvc.Controller#handleEvent(com.extjs.gxt.ui.client.mvc.AppEvent)
     */
    @Override
    public void handleEvent(AppEvent event)
    {
        if (event.getType() == GeoRepoEvents.INIT_GEOREPO_WIDGET)
        {
            onShowLoginWidget();
        }

        if (event.getType() == GeoRepoEvents.LOGIN)
        {
            onLogin(event);
        }

        // if (event.getType() == GeoRepoEvents.ATTACH_USER_WIDGET)
        // onAttachUserWidget(event);

        if (event.getType() == GeoRepoEvents.SHOW_CHOOSER_USER_WIDGET)
        {
            onShowChooseUserWidget();
        }

        if (event.getType() == GeoRepoEvents.SHOW_SEARCH_USER_WIDGET)
        {
            onShowSearchUSerWidget();
        }

        if (event.getType() == GeoRepoEvents.SHOW_UPDATE_USER_WIDGET)
        {
            onShowUpdateUserWidget(event);
        }

        if (event.getType() == GeoRepoEvents.NOTIFY_UNSHARE_ERROR)
        {
            onNotifyUnshareError();
        }

        if (event.getType() == GeoRepoEvents.NOTIFY_UNSHARE_SUCCESS)
        {
            onNotifyUnshareSuccess();
        }

        if (event.getType() == GeoRepoEvents.LOGOUT)
        {
            onLogout();
        }

    }

    /**
     * On logout.
     */
    private void onLogout()
    {
        this.loginRemote.logout(new AsyncCallback<Object>()
            {

                public void onFailure(Throwable caught)
                {
                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE,
                        new String[] { "Logout Service", "There was an error in logout" });
                }

                public void onSuccess(Object result)
                {
                    Dispatcher.forwardEvent(GeoRepoEvents.SESSION_EXPIRED);
                }
            });

    }

    /**
     * On notify unshare success.
     */
    private void onNotifyUnshareSuccess()
    {
        this.chooseUserWidget.setSearchStatus(
            EnumSearchStatus.STATUS_SEARCH,
            EnumSearchStatus.STATUS_MESSAGE_AOI_UNSHARE);
        this.chooseUserWidget.getChooser().hide();
    }

    /**
     * On notify unshare error.
     */
    private void onNotifyUnshareError()
    {
        this.chooseUserWidget.setSearchStatus(
            EnumSearchStatus.STATUS_SEARCH_ERROR,
            EnumSearchStatus.STATUS_MESSAGE_AOI_UNSHARE_ERROR);
    }

    /**
     * On show update profile widget.
     *
     * @param event
     *            the event
     */
    private void onShowUpdateUserWidget(AppEvent event)
    {
        this.updateUser.bind((User) event.getData());
        this.updateUser.show();
    }

    /**
     * On show search u ser widget.
     */
    private void onShowSearchUSerWidget()
    {
        this.searchWidget.show();
    }

    /**
     * On show choose profile widget.
     */
    private void onShowChooseUserWidget()
    {
        this.chooseUserWidget.getChooser().show();
    }

    /**
     * On login.
     *
     * @param event
     *            the event
     */
    private void onLogin(AppEvent event)
    {
        String[] values = (String[]) event.getData();
        this.loginRemote.authenticate(values[0], values[1], new AsyncCallback<User>()
            {

                public void onSuccess(User user)
                {
                    if (user != null)
                    {
                        loginWidget.setStatusLoginFinder(EnumLoginStatus.STATUS_LOGIN,
                            EnumLoginStatus.STATUS_MESSAGE_LOGIN);
                        loginWidget.hide();
                    }
                    else
                    {
                        loginWidget.setStatusLoginFinder(EnumLoginStatus.STATUS_NO_LOGIN,
                            EnumLoginStatus.STATUS_MESSAGE_NOT_LOGIN);
                    }
                }

                public void onFailure(Throwable caught)
                {
                    boolean handled = false;
                    if (caught instanceof ApplicationException)
                    {

                        /*ApplicationException appException = (ApplicationException) caught;
                        if (appException.getDetailedMessage().contains(
                                    "it.geosolutions.georepo.security.exception.AuthException"))
                        {*/
                        // Authentication failed
                        handled = true;
                        loginWidget.resetPassword();
                        loginWidget.setStatusLoginFinder(EnumLoginStatus.STATUS_LOGIN_ERROR,
                            EnumLoginStatus.STATUS_MESSAGE_NOT_LOGIN);
                        /*}*/
                    }

                    if (!handled)
                    {
                        loginWidget.setStatusLoginFinder(EnumLoginStatus.STATUS_LOGIN_ERROR,
                            EnumLoginStatus.STATUS_MESSAGE_LOGIN_ERROR);
                    }
                }
            });
    }

    /**
     * On show login widget.
     */
    private void onShowLoginWidget()
    {
        this.loginRemote.isAuthenticated(new AsyncCallback<Boolean>()
            {

                public void onFailure(Throwable caught)
                {
                    loginWidget.show();
                }

                public void onSuccess(Boolean result)
                {
                    if (!result)
                    {
                        loginWidget.show();
                    }
                    else
                    {
                        loginWidget.hide();
                        Dispatcher.forwardEvent(GeoRepoEvents.INIT_GEOREPO_MAIN_UI);
                    }
                }

            });
    }

}
