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
package it.geosolutions.georepo.gui.client.widget;

import it.geosolutions.georepo.gui.client.service.WatchServiceRemoteAsync;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

/**
 * @author Tobia di Pisa
 *
 */
public class ExecudetWatchesGridManagementWidget extends ContentPanel {

	private ExecutedWatchesPaginationGridWidget watchesPagGridWidget;


	/**
	 * @param service
	 */
	public ExecudetWatchesGridManagementWidget(WatchServiceRemoteAsync service) {
		setHeaderVisible(false);
		setFrame(true);
		setHeight(170);
		setLayout(new FitLayout());
		
		this.watchesPagGridWidget = new ExecutedWatchesPaginationGridWidget(service);
		add(this.watchesPagGridWidget.getGrid());

		super.setMonitorWindowResize(true);

		setScrollMode(Scroll.NONE);

		setBottomComponent(this.watchesPagGridWidget.getToolBar());
	}

	/* (non-Javadoc)
	 * @see com.extjs.gxt.ui.client.widget.Component#onWindowResize(int, int)
	 */
	@Override
	protected void onWindowResize(int width, int height) {
		super.setWidth(width - 5);
		super.layout();
	}

	/**
	 * @return the watchPagGridWidget
	 */
	public ExecutedWatchesPaginationGridWidget getExecutedWatchesPaginationGridWidget() {
		return watchesPagGridWidget;
	}

}
