/*
 * $Header: it.geosolutions.georepo.gui.server.gwt.LoginRemoteImpl,v. 0.1 08/lug/2010 11.22.43 created by frank $
 * $Revision: 0.1 $
 * $Date: 08/lug/2010 11.22.43 $
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
package it.geosolutions.georepo.gui.server.gwt;


import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.geosolutions.georepo.gui.client.model.User;
import it.geosolutions.georepo.gui.client.service.LoginRemote;
import it.geosolutions.georepo.gui.server.service.ILoginService;
import it.geosolutions.georepo.gui.spring.ApplicationContextUtil;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author frank
 * 
 */
public class LoginRemoteImpl
        extends RemoteServiceServlet
        implements LoginRemote {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6763250533126295210L;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * loginService
	 */
	private ILoginService loginService;

	public LoginRemoteImpl() {
		this.loginService = (ILoginService) ApplicationContextUtil
				.getInstance().getBean("loginService");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.geosolutions.georepo.gui.client.service.LoginRemote#authenticate(java
	 * .lang.String, java.lang.String)
	 */
	public User authenticate(String userName, String password) {
		return loginService.authenticate(userName, password,
				getThreadLocalRequest().getSession());
	}

//	/**
//	 *
//	 */
//	public PagingLoadResult<User> loadUsers(PagingLoadConfig config,
//			String searchText) {
//		return loginService.loadUsers(config, searchText);
//	}
//
//	/**
//	 *
//	 */
//	public User saveUser(User user) throws ApplicationException {
//		return loginService.saveUser(user);
//	}
//
//	/**
//	 *
//	 */
//	// public PagingLoadResult<User> loadAllUsers(PagingLoadConfig config)
//	// throws ApplicationException {
//	// return loginService.loadAllUsers(config);
//	// }
//
//	public void deleteUser(Long userId) throws ApplicationException {
//		this.loginService.deleteUser(userId);
//	}
//
//	public User updateUser(User user) {
//		return loginService.updateUser(user);
//	}
//
//	public User getUserDetail(User user) throws ApplicationException {
//		return loginService.getUserDetail(user);
//	}
//
//	public List<RegUser> findUserNames() throws ApplicationException {
//		return loginService.findUserNames(getThreadLocalRequest().getSession());
//	}

	public void logout() {
		HttpSession session = getThreadLocalRequest().getSession();
		if (session != null) {
			session.invalidate();
			logger.info("LOGOUT ------------------------------ INVALIDATING SESSION");
		}

	}

//	public PagingLoadResult<User> getRelatedUsers(PagingLoadConfig config,
//			long aoiID) throws ApplicationException {
//		return loginService.getRelatedUsers(config, aoiID);
//	}
//
//	public int getRelatedUsersCount(long aoiId) {
//		return loginService.getRelatedUsersCount(aoiId);
//	}
}
