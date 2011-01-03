/*
 * $Header: it.geosolutions.georepo.gui.client.widget.SearchStatus,v. 0.1 30/lug/2010 18.51.17 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 30/lug/2010 18.51.17 $
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
public class SearchStatus extends StatusWidget {

	public enum EnumSearchStatus {
		STATUS_SEARCH("x-status-ok"), STATUS_NO_SEARCH("x-status-not-ok"), STATUS_SEARCH_ERROR(
				"x-status-error"), STATUS_MESSAGE_SEARCH("Search OK"), STATUS_MESSAGE_NOT_SEARCH(
				"No Results Found"), STATUS_MESSAGE_SEARCH_ERROR(
				"Search Service Error"), STATUS_MESSAGE_USER_DETAIL_ERROR(
				"User Detail Error"), STATUS_MESSAGE_USER_DETAIL(
				"User Detail Ok"), STATUS_MESSAGE_AOI_DETAIL_ERROR(
				"AOI Deatil Error"), STATUS_MESSAGE_AOI_DETAIL("AOI Deatil Ok"), STATUS_MESSAGE_AOI_UNSHARE_ERROR(
				"Unshare AOI Error"), STATUS_MESSAGE_AOI_UNSHARE(
				"Unshare AOI Ok"),STATUS_MESSAGE_MEMBER_DETAIL(
				"Member Detail Ok");

		private String value;

		EnumSearchStatus(String value) {
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
