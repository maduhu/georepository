/*
 * $Header: it.geosolutions.georepo.gui.client.action.application.QuartzMonitoringAction,v. 0.1 30/set/2010 16.07.58 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 30/set/2010 16.07.58 $
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
package it.geosolutions.georepo.gui.client.action.application;

import it.geosolutions.georepo.gui.client.Category;
import it.geosolutions.georepo.gui.client.action.ToolbarApplicationAction;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import com.extjs.gxt.ui.client.event.BaseEvent;

/**
 * @author giuseppe
 * 
 */
public class QuartzMonitoringAction extends ToolbarApplicationAction {

	public QuartzMonitoringAction() {
		super(I18nProvider.getMessages().quartzTriggerMenu(), Category.QUARTZ);
		// TODO Auto-generated constructor stub
	}

    public void handleEvent(BaseEvent baseEvent) {
    }
}
