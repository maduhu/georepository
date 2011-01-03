/*
 * $Header: it.geosolutions.georepo.gui.client.configuration.UserBeanManager,v. 0.1 09/lug/2010 11.44.44 created by frank $
 * $Revision: 0.1 $
 * $Date: 09/lug/2010 11.44.44 $
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
package it.geosolutions.georepo.gui.client.configuration;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import it.geosolutions.georepo.gui.client.configuration.IUserBeanManager;
import it.geosolutions.georepo.gui.client.model.User;

/**
 * @author frank
 * 
 */
@Component("userBeanManager")
public class UserBeanManager implements IUserBeanManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 575619421702010379L;

	private List<User> users = new ArrayList<User>();

	/**
	 * @param users
	 *            the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.geosolutions.georepo.gui.client.configuration.IUserBeanManager#getUsers
	 * ()
	 */
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return users;
	}

	@PostConstruct
	public void configureUsers() {
		for (int i = 0; i < 200; i++) {
			User user = new User();
			user.setPath("dgwatch/resources/images/userChoose.jpg");
			user.setName("TEST" + i);
			user.setUserName("user" + i);
			user.setPassword("password" + i);
			user.setEmailAddress("user" + i + "@test.it");
			user.setEmailEnable(true);
			user.setRssEnable(true);
			this.users.add(user);
		}		
	}

}
