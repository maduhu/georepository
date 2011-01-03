/*
 * $Header: it.geosolutions.georepo.gui.client.widget.LoginStatus,v. 0.1 16/lug/2010 15.38.37 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 16/lug/2010 15.38.37 $
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

import it.geosolutions.georepo.gui.client.widget.StatusWidget;

/**
 * @author giuseppe
 * 
 */
public class LoginStatus extends StatusWidget {

	public enum EnumLoginStatus {
		STATUS_LOGIN("x-status-ok"), STATUS_NO_LOGIN("x-status-not-ok"), STATUS_LOGIN_ERROR(
				"x-status-error"), STATUS_MESSAGE_LOGIN("Login OK"), STATUS_MESSAGE_NOT_LOGIN(
				"Login Failed"), STATUS_MESSAGE_LOGIN_ERROR(
				"Login Service Error");

		private String value;

		EnumLoginStatus(String value) {
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
