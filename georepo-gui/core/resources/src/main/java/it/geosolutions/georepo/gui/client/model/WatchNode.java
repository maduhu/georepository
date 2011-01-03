/*
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
package it.geosolutions.georepo.gui.client.model;

import com.extjs.gxt.ui.client.data.BaseModel;

/**
 * @author Tobia di Pisa
 *
 */
public class WatchNode extends BaseModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1111659793475229355L;


	/**
	 * @param name
	 * @param id
	 */
	public WatchNode(String name, Long id) {
		set("name", name);
		set("id", id);
	}
	
	/**
	 * @return the mails
	 */
	public String getName() {
		return (String) get("name");
	}
	
	/**
	 * @return the mails
	 */
	public Long getId() {
		return get("id");
	}
	
}
