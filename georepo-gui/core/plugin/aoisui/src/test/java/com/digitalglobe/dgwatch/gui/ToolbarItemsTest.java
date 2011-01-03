/*
 * $Header: it.geosolutions.georepo.gui.ToolbarItemsTest,v. 0.1 01/ago/2010 10.06.44 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 01/ago/2010 10.06.44 $
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


import java.util.Collections;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import it.geosolutions.georepo.gui.client.configuration.IToolbarItemManager;

/**
 * @author giuseppe
 * 
 */
@SuppressWarnings("deprecation")
public class ToolbarItemsTest extends
		AbstractDependencyInjectionSpringContextTests {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private IToolbarItemManager toolbarItemManager;

	public void test() {
		assertNotNull(toolbarItemManager);
		
		Collections.sort(toolbarItemManager.getClientTools());

		logger.info("################## TOOLBAR ITEMS ####################### "
				+ toolbarItemManager.getClientTools());
	}

	@Override
    protected String[] getConfigLocations() {
		return new String[] { "classpath:applicationContext.xml" };
	}

}
