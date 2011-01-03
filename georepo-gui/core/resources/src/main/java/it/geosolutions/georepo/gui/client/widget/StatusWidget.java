/*
 * $Header: it.geosolutions.georepo.gui.client.widget.StatusWidget,v. 0.1 16/lug/2010 15.30.22 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 16/lug/2010 15.30.22 $
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

import com.extjs.gxt.ui.client.widget.Status;

/**
 * @author giuseppe
 * 
 */
public class StatusWidget extends Status {

	public StatusWidget() {
		super();
	}

	/**
	 * Enables a busy icon and displays the given text.
	 * 
	 * @param text
	 *            the text to display
	 */
	@Override
    public void setBusy(String text) {
		setIconStyle("x-loading-status");
		setText(text);
	}

}
