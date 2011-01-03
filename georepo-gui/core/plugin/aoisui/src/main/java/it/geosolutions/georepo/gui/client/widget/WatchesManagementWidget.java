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

import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.Authorization;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.ContentPanel;

import java.util.List;

/**
 * @author Tobia di Pisa
 *
 */
public class WatchesManagementWidget extends ContentPanel {

	private WatchesInfoBindingWidget watchesInfo;
	
	public WatchesManagementWidget() {
		setId("watchesManagementWidget");
		setHeading(I18nProvider.getMessages().watchesManagementLabel());

		setScrollMode(Scroll.AUTO);
		
		// /////////////////////
		// Others component
		// /////////////////////
		
		watchesInfo = new WatchesInfoBindingWidget();
		add(watchesInfo.getFormPanel());

	}

	/**
	 * @return the userInfo
	 */
	public WatchesInfoBindingWidget getWatchesInfo() {
		return watchesInfo;
	}

    public void injectSecurity(List<Authorization> auths) {
        this.watchesInfo.injectSecurity(auths);
    }
	
}
