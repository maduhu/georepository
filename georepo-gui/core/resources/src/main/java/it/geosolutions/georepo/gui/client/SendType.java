/*
 * $Header: it.geosolutions.georepo.gui.client.SendType,v. 0.1 28/lug/2010 14.38.10 created by frank $
 * $Revision: 0.1 $
 * $Date: 28/lug/2010 14.38.10 $
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

import com.extjs.gxt.ui.client.data.BeanModel;

/**
 * @author frank
 * 
 */
public class SendType extends BeanModel {

	public enum SendTypeEnum {
		TYPE("type");

		private String value;

		SendTypeEnum(String value) {
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
	private static final long serialVersionUID = -6754712996337001119L;

	/**
	 * 
	 */
	public SendType(String type) {
		setType(type);
	}

	public void setType(String type) {
		set(SendTypeEnum.TYPE.getValue(), type);
	}

	public String getType() {
		return get(SendTypeEnum.TYPE.getValue());
	}

}
