/*
 * $Header: it.geosolutions.georepo.gui.client.AOISUI,v. 0.1 08/lug/2010 10.04.22 created by frank $
 * $Revision: 0.1 $
 * $Date: 08/lug/2010 10.04.22 $
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

import it.geosolutions.georepo.gui.client.mvc.AOISController;
import it.geosolutions.georepo.gui.client.mvc.FilterController;
import it.geosolutions.georepo.gui.client.mvc.MapController;
import it.geosolutions.georepo.gui.client.mvc.WatchesController;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.EntryPoint;

/**
 * @author frank
 * 
 */
public class AOISUI implements EntryPoint {

	private Dispatcher dispatcher;

	public void onModuleLoad() {
		dispatcher = Dispatcher.get();

		dispatcher.addController(new MapController());
		dispatcher.addController(new AOISController());
//		dispatcher.addController(new FeatureController());
		dispatcher.addController(new FilterController());
		dispatcher.addController(new WatchesController());

		dispatcher.dispatch(DGWATCHEvents.INIT_AOIS_UI_MODULE);

	}

}