/*
 * $Header: it.geosolutions.georepo.gui.client.service.LoginRemoteAsync,v. 0.1 08/lug/2010 11.01.17 created by frank $
 * $Revision: 0.1 $
 * $Date: 08/lug/2010 11.01.17 $
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

import java.util.List;

import it.geosolutions.georepo.gui.client.model.RegUser;
import it.geosolutions.georepo.gui.client.model.User;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author frank
 * 
 */
public interface LoginRemoteAsync {

	/**
	 * 
	 * @param userName
	 * @param password
	 * @param calback
	 */
	public void authenticate(String userName, String password,
			AsyncCallback<User> calback);

//	/**
//	 *
//	 * @param config
//	 * @param callback
//	 */
//	public void loadUsers(PagingLoadConfig config, String searchText,
//			AsyncCallback<PagingLoadResult<User>> callback);
//
//	/**
//	 *
//	 * @param config
//	 * @param callback
//	 */
//	// public void loadAllUsers(PagingLoadConfig config,
//	// AsyncCallback<PagingLoadResult<User>> callback);
//
//	/**
//	 *
//	 * @param user
//	 * @param callback
//	 */
//	public void saveUser(User user, AsyncCallback<User> callback);
//
//	/**
//	 *
//	 * @param userId
//	 * @param callback
//	 */
//	public void deleteUser(Long userId, AsyncCallback<?> callback);
//
//	/**
//	 *
//	 * @param user
//	 * @param callback
//	 */
//	public void updateUser(User user, AsyncCallback<User> callback);
//
//	/**
//	 *
//	 * @param userId
//	 * @param callback
//	 */
//	public void getUserDetail(User user, AsyncCallback<User> callback);
//
//	/**
//	 *
//	 * @param callback
//	 */
//	public void findUserNames(AsyncCallback<List<RegUser>> callback);
//
	/**
	 * 
	 * @param callback
	 */
	public void logout(AsyncCallback<?> callback);

//	/**
//	 *
//	 * @param config
//	 * @param searchText
//	 * @param callback
//	 */
//	public void getRelatedUsers(PagingLoadConfig config, long aoiID,
//			AsyncCallback<PagingLoadResult<User>> callback);
//
//	/**
//	 *
//	 * @param aoiId
//	 * @param callback
//	 * @return
//	 */
//	public void getRelatedUsersCount(long aoiId, AsyncCallback<Integer> callback);

}
