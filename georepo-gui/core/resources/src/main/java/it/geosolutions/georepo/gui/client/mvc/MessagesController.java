/*
 * $Header: it.geosolutions.georepo.gui.client.mvc.MessagesController,v. 0.1 25/set/2010 16.13.37 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 25/set/2010 16.13.37 $
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
package it.geosolutions.georepo.gui.client.mvc;

import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

/**
 * @author giuseppe
 * 
 */
public class MessagesController extends Controller {

	private MessagesView messagesView;

	public MessagesController() {
		registerEventTypes(DGWATCHEvents.INIT_RESOURCES_MODULE,
				DGWATCHEvents.SEND_ALERT_MESSAGE,
				DGWATCHEvents.SEND_ERROR_MESSAGE,
				DGWATCHEvents.SEND_INFO_MESSAGE);
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		this.messagesView = new MessagesView(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.extjs.gxt.ui.client.mvc.Controller#handleEvent(com.extjs.gxt.ui.client
	 * .mvc.AppEvent)
	 */
	@Override
	public void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		forwardToView(messagesView, event);
	}
}