/*
 * $ Header: it.geosolutions.georepo.gui.server.service.ILoginService,v. 0.1 25-gen-2011 11.23.49 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.23.49 $
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

import javax.servlet.http.HttpSession;

import it.geosolutions.georepo.gui.client.model.User;


// TODO: Auto-generated Javadoc
/**
 * The Interface ILoginService.
 */
public interface ILoginService
{

    /**
     * Authenticate.
     *
     * @param userName
     *            the profile name
     * @param password
     *            the password
     * @param session
     *            the session
     * @return the profile
     */
    public User authenticate(String userName, String password, HttpSession session);

    // public PagingLoadResult<User> loadUsers(PagingLoadConfig config,
    // String searchText) throws ApplicationException;
    //
    // // public PagingLoadResult<User> loadAllUsers(PagingLoadConfig config)
    // // throws ApplicationException;
    //
    // public User saveUser(User profile) throws ApplicationException;
    //
    // public void deleteUser(Long userId) throws ApplicationException;
    //
    // public User updateUser(User profile);
    //
    // public User getUserDetail(User profile) throws ApplicationException;
    //
    // public List<RegUser> findUserNames(HttpSession session)
    // throws ApplicationException;
    //
    // public PagingLoadResult<User> getRelatedUsers(PagingLoadConfig config,
    // long aoiID) throws ApplicationException;
    //
    // public int getRelatedUsersCount(long aoiId);
}
