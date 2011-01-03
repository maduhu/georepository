/*
 * $Header: it.geosolutions.georepo.gui.client.DGWATCHUtils,v. 0.1 09/lug/2010 12.20.07 created by frank $
 * $Revision: 0.1 $
 * $Date: 09/lug/2010 12.20.07 $
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
package it.geosolutions.georepo.gui.client;

import it.geosolutions.georepo.gui.client.configuration.IDGWATCHConfiguration;

/**
 * @author frank
 * 
 *         Singleton to manage all Application References to various Spring
 *         Objects
 * 
 */
public class DGWATCHUtils {
	
	private static DGWATCHUtils INSTANCE;

	private IDGWATCHConfiguration globalConfiguration;

	public static DGWATCHUtils getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DGWATCHUtils();
		}
		return INSTANCE;
	}

	/**
	 * @return the globalConfiguration
	 */
	public IDGWATCHConfiguration getGlobalConfiguration() {
		return globalConfiguration;
	}

	/**
	 * @param globalConfiguration the globalConfiguration to set
	 */
	public void setGlobalConfiguration(IDGWATCHConfiguration globalConfiguration) {
		this.globalConfiguration = globalConfiguration;
	}

}
