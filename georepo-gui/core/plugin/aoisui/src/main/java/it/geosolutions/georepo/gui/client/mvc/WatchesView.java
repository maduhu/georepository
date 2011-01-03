/*
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
import it.geosolutions.georepo.gui.client.model.Authorization;
import it.geosolutions.georepo.gui.client.model.User;
import it.geosolutions.georepo.gui.client.widget.WatchesManagementWidget;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.ContentPanel;

import java.util.List;

/**
 * @author Tobia di Pisa
 *
 */
public class WatchesView extends View {

	private WatchesManagementWidget wmWidget;

	public WatchesView(Controller controller) {
		super(controller);
		this.wmWidget = new WatchesManagementWidget();
	}

	@Override
	protected void handleEvent(AppEvent event) {
        if (event.getType() == DGWATCHEvents.ATTACH_WATCHES_WIDGET)
            onAttachWatchesWidget(event);
        if (event.getType() == DGWATCHEvents.LOGIN_SUCCESS)
            onLoginSuccess(event);	
	}

	/**
	 * @param event
	 */
	private void onAttachWatchesWidget(AppEvent event) {
        ContentPanel east = (ContentPanel) event.getData();
        east.add(this.wmWidget);
        east.layout();
	}
	
	/**
	 * @return the wmWidget
	 */
	public WatchesManagementWidget getWmWidget() {
		return wmWidget;
	}

    private void onLoginSuccess(AppEvent event) {
        this.wmWidget.injectSecurity(((User) event.getData()).getGrantedAuthorizations());    
    }
	
}
