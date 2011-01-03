/*
 * $Header: it.geosolutions.georepo.gui.AOIServiceTest,v. 0.1 27/lug/2010 10.38.59 created by frank $
 * $Revision: 0.1 $
 * $Date: 27/lug/2010 10.38.59 $
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
package it.geosolutions.georepo.gui;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author frank
 * 
 */
public class AOIServiceTest extends TestCase
// AbstractDependencyInjectionSpringContextTests {
{
	// private final Logger logger = Logger.getLogger(this.getClass());
	//
	// @Autowired
	// private DGWATCHRemoteService dgwatchRemoteService;
	//
	// public void testService() {
	// assertNotNull(dgwatchRemoteService);
	//
	// UserList aoiList = dgwatchRemoteService.getClient().getUsers();
	//
	// if (aoiList.getList() != null)
	// logger.info("******************* TOTAL Users  ***********************"
	// + aoiList.getList().size());
	// }
	//
	// public void testUserSearch() {
	//
	// String username1 = "%user%";
	//
	// SearchRequest srq = new SearchRequest(username1);
	//
	// long userCount = dgwatchRemoteService.getClient().getUsersCount(srq);
	//
	// logger.info("USER COUNT FOR %USE% ****************************** "
	// + userCount);
	//
	// PaginatedSearchRequest psr = new PaginatedSearchRequest(username1, 25,
	// 0);
	//
	// UserList ul = dgwatchRemoteService.getClient().searchUsers(psr);
	//
	// if (ul.getList() != null)
	// logger.info("FOUND ELEMENTS FOR PAGINATION ************** "
	// + ul.getList().size());
	//
	// }
	//
	// public void testAOISearch() {
	// AOIList aois = dgwatchRemoteService.getClient().getAois();
	//
	// if (aois.getList() != null)
	// logger.info("NUMBER OF AOI :***************************** "
	// + aois.getList().size());
	// }

	// public void testInternalService() {
	// List<RegisteredUser> usersRegistered = dgwatchRemoteService
	// .getInternalService().getUsers();
	//
	// for (RegisteredUser regUser : usersRegistered) {
	// logger.info("USER_NAME: " + regUser.getUserName()
	// + "  CONNECT_ID: " + regUser.getConnectId());
	// }
	// }

	// protected String[] getConfigLocations() {
	// return new String[] { "classpath:applicationContext.xml" };
	// }

	public AOIServiceTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(AOIServiceTest.class);
	}

	public void testApp() {
		assertTrue(true);
	}

}