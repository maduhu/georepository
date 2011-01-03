/*
 * $Header: it.geosolutions.georepo.gui.client.FilterType,v. 0.1 18/ago/2010 10.07.54 created by frank $
 * $Revision: 0.1 $
 * $Date: 18/ago/2010 10.07.54 $
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
public class FilterType extends BeanModel {

	public enum FilterTypeEnum {
		TYPE("type");

		private String value;

		FilterTypeEnum(String value) {
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
	private static final long serialVersionUID = 4342198507231122012L;

	/**
	 * 
	 */
	public FilterType(String type) {
		setTyper(type);
	}

	public void setTyper(String type) {
		set(FilterTypeEnum.TYPE.getValue(), type);

	}

	public String getType() {
		return get(FilterTypeEnum.TYPE.getValue());
	}

}
