/*
 * $Header: it.geosolutions.georepo.gui.client.ResourcesGWT,v. 0.1 25/set/2010 16.11.02 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 25/set/2010 16.11.02 $
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

import it.geosolutions.georepo.gui.client.mvc.MessagesController;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.EntryPoint;

/**
 * @author giuseppe
 * 
 */
public class ResourcesEntryPoint implements EntryPoint {

	private Dispatcher dispatcher;

	public void onModuleLoad() {
		// TODO Auto-generated method stub
		dispatcher = Dispatcher.get();

		dispatcher.addController(new MessagesController());

		dispatcher.dispatch(DGWATCHEvents.INIT_RESOURCES_MODULE);
	}
}