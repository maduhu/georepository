/*
 * $Header: it.geosolutions.georepo.gui.client.configuration.ActionClientTool,v. 0.1 27/lug/2010 11.07.36 created by frank $
 * $Revision: 0.1 $
 * $Date: 27/lug/2010 11.07.36 $
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
package it.geosolutions.georepo.gui.client.configuration;

/**
 * @author frank
 * 
 */
public class ActionClientTool extends GenericClientTool {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7373117917871895237L;

	private String type;
	private boolean enabled;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ActionClientTool [enabled=" + enabled + ", getId()=" + getId()
				+ ", getOrder()=" + getOrder() + ", getType()=" + getType()
				+ "]";
	}

}
