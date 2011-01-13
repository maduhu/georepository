/*
 * $ Header: it.geosolutions.georepo.gui.service.DGWATCHRemoteService,v. 0.1 3-gen-2011 17.06.11 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 17.06.11 $
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
package it.geosolutions.georepo.gui.service;

import it.geosolutions.georepo.api.UserRegistry;
import it.geosolutions.georepo.login.LoginService;
import it.geosolutions.georepo.services.UserAdminService;

// TODO: Auto-generated Javadoc
/**
 * The Class GeoRepoRemoteService.
 */
public class GeoRepoRemoteService {

    /** The login service. */
    private LoginService loginService;

    /** The user admin service. */
    private UserAdminService userAdminService;

    /** The user provider. */
    private UserRegistry userProvider;

    /**
     * Gets the login service.
     * 
     * @return the login service
     */
    public LoginService getLoginService() {
        return loginService;
    }

    /**
     * Sets the login service.
     * 
     * @param loginService
     *            the new login service
     */
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Gets the user admin service.
     * 
     * @return the user admin service
     */
    public UserAdminService getUserAdminService() {
        return userAdminService;
    }

    /**
     * Sets the user admin service.
     * 
     * @param userAdminService
     *            the new user admin service
     */
    public void setUserAdminService(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

    /**
     * Gets the user provider.
     * 
     * @return the user provider
     */
    public UserRegistry getUserProvider() {
        return userProvider;
    }

    /**
     * Sets the user provider.
     * 
     * @param userProvider
     *            the new user provider
     */
    public void setUserProvider(UserRegistry userProvider) {
        this.userProvider = userProvider;
    }

}
