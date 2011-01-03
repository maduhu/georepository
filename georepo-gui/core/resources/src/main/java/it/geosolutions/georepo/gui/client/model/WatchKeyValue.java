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

/**
 * @author Tobia di Pisa
 *
 */
public enum WatchKeyValue {

	WATCH_TITLE("watchTitle"), WATCH_BEGIN_DATE("watchBeginDate"), WATCH_EXPIRATION("watchExpirationDate"), 
	WATCH_ID("watchId"), WATCH_AOI_ID("watchAOIId"), WATCH_MEMBER("watchMember"), WATCH_FILTER("watchFilter"), 
	WATCH_ACTION_ID("watchActionId"), WATCH_NOTIFICATION("watchNotification"), WATCH_RUNTIME("watchRunetime");

	private String value;

	/**
	 * @param value
	 */
	WatchKeyValue(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
