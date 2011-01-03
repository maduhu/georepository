/*
 * $Header: it.geosolutions.georepo.gui.client.UserUI,v. 0.1 08/lug/2010 10.05.23 created by frank $
 * $Revision: 0.1 $
 * $Date: 08/lug/2010 10.05.23 $
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

import it.geosolutions.georepo.gui.client.controller.LoginController;
import it.geosolutions.georepo.gui.client.controller.MemberController;
import it.geosolutions.georepo.gui.client.controller.ServicesController;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.EntryPoint;

/**
 * @author frank
 * 
 */
public class UserUI implements EntryPoint {

	private Dispatcher dispatcher;

	public void onModuleLoad() {
		dispatcher = Dispatcher.get();
		dispatcher.addController(new LoginController());
        dispatcher.addController(new ServicesController());
        dispatcher.addController(new MemberController());

		dispatcher.dispatch(DGWATCHEvents.INIT_USER_UI_MODULE);
	}
}
