/*
 * $Header: it.geosolutions.georepo.gui.client.configuration.ConfigurationMainUI,v. 0.1 08/lug/2010 12.53.57 created by frank $
 * $Revision: 0.1 $
 * $Date: 08/lug/2010 12.53.57 $
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

/**
 * @author frank
 * 
 */
public enum ConfigurationMainUI {

    CENTER("CENTER_PANEL"),
    EAST("EAST_PANEL"),
    SOUTH("SOUTH_PANEL"),
    VIEWPORT("VIEWPORT");
    
	private String value;

	ConfigurationMainUI(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	

}
