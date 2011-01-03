/*
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
package it.geosolutions.georepo.gui.service;

import it.geosolutions.georepo.api.UserRegistry;
import it.geosolutions.georepo.login.LoginService;
import it.geosolutions.georepo.services.UserAdminService;

/**
 */
public class DGWATCHRemoteService {

    private LoginService loginService;
    private UserAdminService userAdminService;
    private UserRegistry userProvider;

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public UserAdminService getUserAdminService() {
        return userAdminService;
    }

    public void setUserAdminService(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

    public UserRegistry getUserProvider() {
        return userProvider;
    }

    public void setUserProvider(UserRegistry userProvider) {
        this.userProvider = userProvider;
    }

}
