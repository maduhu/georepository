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
public class WatchMail extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5868042822929449840L;
	
	/**
	 * @param mail
	 */
	public WatchMail(String mail) {
		set("mail", mail);
	}
	
	/**
	 * @return the mails
	 */
	public String getMail() {
		return (String) get("mail");
	}
	
}
