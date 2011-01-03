/*
 * $Header: it.geosolutions.georepo.gui.client.widget.tab.AOITabItem,v. 0.1 09/lug/2010 10.24.38 created by frank $
 * $Revision: 0.1 $
 * $Date: 09/lug/2010 10.24.38 $
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

import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.AOI;
import it.geosolutions.georepo.gui.client.service.AOIServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.AOIManagementWidget;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.TabItem;

/**
 * @author frank
 * 
 */
public class AOITabItem extends TabItem {

	private AOIManagementWidget aoiManagementWidget;

	/**
	 * 
	 * @param service
	 */
	public AOITabItem(AOIServiceRemoteAsync service) {
		super(I18nProvider.getMessages().aoiLabel());
		this.aoiManagementWidget = new AOIManagementWidget(service);
		add(aoiManagementWidget);

		setScrollMode(Scroll.NONE);
	}

	/**
	 * @return the aoiManagementWidget
	 */
	public AOIManagementWidget getAoiManagementWidget() {
		return aoiManagementWidget;
	}

	public void removeAOI(AOI aoi) {
		this.aoiManagementWidget.removeAOI(aoi);
	}

	public void updateAOITitle(AOI aoi) {
		this.aoiManagementWidget.updateAOITitle(aoi);
	}
	
	public void updateAOIStatus(AOI aoi) {
		this.aoiManagementWidget.updateAOIStatus(aoi);
	}
}
