/*
 * $Header: it.geosolutions.georepo.gui.client.configuration.DGWATCHGlobalConfiguration,v. 0.1 09/lug/2010 11.22.40 created by frank $
 * $Revision: 0.1 $
 * $Date: 09/lug/2010 11.22.40 $
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.geosolutions.georepo.gui.client.configuration.IDGWATCHConfiguration;
import it.geosolutions.georepo.gui.client.configuration.IToolbarItemManager;
import it.geosolutions.georepo.gui.client.configuration.IUserBeanManager;

/**
 * @author frank
 *
 */
@Component("dgwatchGlobalConfiguration")
public class DGWATCHGlobalConfiguration implements IDGWATCHConfiguration {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3377836318526396981L;
	
	@Autowired
	private IUserBeanManager userBeanManager;
	
	@Autowired
	private IToolbarItemManager toolbarItemManager;

	public IUserBeanManager getUserBeanManager() {
		// TODO Auto-generated method stub
		return userBeanManager;
	}

	public IToolbarItemManager getToolbarItemManager() {
		return toolbarItemManager;
	}

}
