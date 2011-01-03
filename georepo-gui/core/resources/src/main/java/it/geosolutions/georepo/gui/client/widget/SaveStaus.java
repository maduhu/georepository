/*
 * $Header: it.geosolutions.georepo.gui.client.widget.SaveStaus,v. 0.1 02/ago/2010 09.15.23 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 02/ago/2010 09.15.23 $
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
package it.geosolutions.georepo.gui.client.widget;

/**
 * @author giuseppe
 *
 */
public class SaveStaus extends StatusWidget {
	
	public enum EnumSaveStatus {
		STATUS_SAVE("x-status-ok"), STATUS_NO_SAVE("x-status-not-ok"), STATUS_SAVE_ERROR(
				"x-status-error"), STATUS_MESSAGE_SAVE("Operation Ok"), STATUS_MESSAGE_NOT_SAVE(
				"Operation Failed"), STATUS_MESSAGE_SAVE_ERROR(
				"Service Error");

		private String value;

		EnumSaveStatus(String value) {
			this.value = value;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}
	}

}
