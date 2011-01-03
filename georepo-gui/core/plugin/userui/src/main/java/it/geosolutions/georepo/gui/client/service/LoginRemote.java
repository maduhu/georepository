/*
 * $Header: it.geosolutions.georepo.gui.client.service.LoginRemote,v. 0.1 08/lug/2010 11.00.39 created by frank $
 * $Revision: 0.1 $
 * $Date: 08/lug/2010 11.00.39 $
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
package it.geosolutions.georepo.gui.client.service;


import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * @author frank
 * 
 */
public interface LoginRemote extends RemoteService {

	public static class Util {
		private static LoginRemoteAsync instance;

		public static LoginRemoteAsync getInstance() {
			if (instance == null) {
				instance = (LoginRemoteAsync) GWT.create(LoginRemote.class);
				ServiceDefTarget target = (ServiceDefTarget) instance;
				target.setServiceEntryPoint(GWT.getModuleBaseURL()
						+ "LoginRemote");
			}
			return instance;
		}
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 * @return User
	 */
	public User authenticate(String userName, String password)
			throws ApplicationException;

//	/**
//	 *
//	 * @param config
//	 * @return PagingLoadResult<User
//	 * @throws ApplicationException
//	 *
//	 *             Serach Users filtering by Username
//	 *
//	 */
//	public PagingLoadResult<User> loadUsers(PagingLoadConfig config,
//			String serchText) throws ApplicationException;
//
//	/**
//	 *
//	 * @param config
//	 * @return PagingLoadResult<User>
//	 * @throws ApplicationException
//	 */
//	// public PagingLoadResult<User> loadAllUsers(PagingLoadConfig config)
//	// throws ApplicationException;
//
//	/**
//	 *
//	 * @param user
//	 * @return
//	 * @throws ApplicationException
//	 */
//	public User saveUser(User user) throws ApplicationException;
//
//	/**
//	 *
//	 * @param userId
//	 * @throws ApplicationException
//	 */
//	public void deleteUser(Long userId) throws ApplicationException;
//
//	/**
//	 *
//	 * @param user
//	 * @return
//	 */
//	public User updateUser(User user);
//
//	/**
//	 *
//	 * @param userId
//	 * @return
//	 * @throws ApplicationException
//	 */
//	public User getUserDetail(User user) throws ApplicationException;
//
//	/**
//	 * @return a List<String> that represented the couple Key - UserName
//	 */
//	public List<RegUser> findUserNames() throws ApplicationException;

	/**
	 * Logout
	 */
	public void logout();

//	/**
//	 *
//	 * @param config
//	 * @param aoiId
//	 * @return PagingLoadResult<User>
//	 *
//	 * @throws ApplicationException
//	 *
//	 *             Search Users that have UserPref on the AOI
//	 */
//	public PagingLoadResult<User> getRelatedUsers(PagingLoadConfig config,
//			long aoiID) throws ApplicationException;
//
//	/**
//	 *
//	 * @param aoiId
//	 * @return
//	 */
//	public int getRelatedUsersCount(long aoiId);

}
