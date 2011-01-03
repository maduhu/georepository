/*
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
package it.geosolutions.georepo.gui.client.model;

import com.extjs.gxt.ui.client.data.BeanModel;

/**
 * @author Tobia di Pisa
 *
 */
public class ClientShortWatch extends BeanModel {

	public enum ShortWatchKeyValue {
		ID("shortId"),
        TITLE("shortTitle"),
        ACTION("shortAction"),
		AOITITLE("shortAoiTitle");

		private String value;

		ShortWatchKeyValue(String value) {
			this.value = value;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6410938170911933374L;
	
	private long id;
	private String title;
	private String action;
	private String aoiTitle;
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
		set(ShortWatchKeyValue.ID.getValue(), this.id);
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
		set(ShortWatchKeyValue.TITLE.getValue(), this.title);
	}
	
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
		set(ShortWatchKeyValue.ACTION.getValue(), this.action);
	}
	
	/**
	 * @return the aoiTitle
	 */
	public String getAoiTitle() {
		return aoiTitle;
	}
	
	/**
	 * @param aoiTitle the aoiTitle to set
	 */
	public void setAoiTitle(String aoiTitle) {
		this.aoiTitle = aoiTitle;
		set(ShortWatchKeyValue.AOITITLE.getValue(), this.aoiTitle);
	}	
	
}
