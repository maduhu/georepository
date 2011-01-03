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
package it.geosolutions.georepo.gui.client.widget.tab;

import it.geosolutions.georepo.gui.client.service.WatchServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.ExecudetWatchesGridManagementWidget;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.TabItem;

/**
 * @author Tobia di Pisa
 *
 */
public class ExecutedWatchesTabItem extends TabItem {
	private ExecudetWatchesGridManagementWidget execudetWatchesGridManagementWidget;

	/**
	 * 
	 * @param service
	 */
	public ExecutedWatchesTabItem(WatchServiceRemoteAsync service) {
		super("Executed Watches");
		this.execudetWatchesGridManagementWidget = new ExecudetWatchesGridManagementWidget(service);
		add(execudetWatchesGridManagementWidget);

		setScrollMode(Scroll.NONE);
		
		this.addListener(Events.Select, new Listener<BaseEvent>(){

			public void handleEvent(BaseEvent be) {
				execudetWatchesGridManagementWidget.getExecutedWatchesPaginationGridWidget().getLoader().load(0, 25);				
			}
			
		});
	}

	/**
	 * @return the aoiManagementWidget
	 */
	public ExecudetWatchesGridManagementWidget getExecudetWatchesGridManagementWidget() {
		return execudetWatchesGridManagementWidget;
	}

}
