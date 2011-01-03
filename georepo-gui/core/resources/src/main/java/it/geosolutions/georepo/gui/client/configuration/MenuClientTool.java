/*
 * $Header: it.geosolutions.georepo.gui.client.configuration.MenuClientTool,v. 0.1 30/set/2010 15.47.58 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 30/set/2010 15.47.58 $
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

import java.util.Collections;
import java.util.List;

/**
 * @author giuseppe
 * 
 */
public class MenuClientTool extends GenericClientTool {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8189124026216386133L;

	private boolean enabled;
	private List<ActionClientTool> actionTools;

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

	/**
	 * @return the actionTools
	 */
	public List<ActionClientTool> getActionTools() {
		return actionTools;
	}

	/**
	 * @param actionTools
	 *            the actionTools to set
	 */
	public void setActionTools(List<ActionClientTool> actionTools) {
		Collections.sort(actionTools);
		this.actionTools = actionTools;
	}

}
