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
import it.geosolutions.georepo.gui.client.widget.LoginStatus.EnumLoginStatus;
import it.geosolutions.georepo.gui.client.widget.LoginWidget;

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

    /**
     * Instantiates a new login controller.
     */
    public LoginController() {
        registerEventTypes(
                
                GeoRepoEvents.INIT_GEOREPO_WIDGET, 
                GeoRepoEvents.LOGIN,
                GeoRepoEvents.LOGOUT);
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
        
        if (event.getType() == GeoRepoEvents.LOGOUT)
            onLogout();

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

}
