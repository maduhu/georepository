/*
 * $ Header: it.geosolutions.georepo.gui.server.service.IGsUsersManagerService,v. 0.1 10-feb-2011 11.09.23 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 10-feb-2011 11.09.23 $
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
package it.geosolutions.georepo.gui.server.service;

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.GSUser;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

// TODO: Auto-generated Javadoc
/**
 * The Interface IGsUsersManagerService.
 */
public interface IGsUsersManagerService {

    /**
     * Gets the gs users.
     * 
     * @param config
     *            the config
     * @param full
     *            the full
     * @return the gs users
     * @throws ApplicationException
     *             the application exception
     */
    public PagingLoadResult<GSUser> getGsUsers(PagingLoadConfig config, boolean full)
            throws ApplicationException;

    /**
     * Save user.
     * 
     * @param user
     *            the user
     * @throws ApplicationException
     *             the application exception
     */
    public void saveUser(GSUser user) throws ApplicationException;

    /**
     * Delete user.
     * 
     * @param user
     *            the user
     */
    public void deleteUser(GSUser user);
}
