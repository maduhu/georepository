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
package it.geosolutions.georepo.gui.client;

import com.extjs.gxt.ui.client.data.BeanModel;

/**
 * @author Tobia di Pisa
 *
 */
public class DistribUpdateInterval extends BeanModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2604147167853598222L;

	public enum DistribUpdateIntervalEnum {
		TIME("distribTime");

		private String value;

		DistribUpdateIntervalEnum(String value) {
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
	public DistribUpdateInterval(String time) {
		setTime(time);
	}

	public void setTime(String time) {
		set(DistribUpdateIntervalEnum.TIME.getValue(), time);
	}

	public String getTime() {
		return get(DistribUpdateIntervalEnum.TIME.getValue());
	}
}
