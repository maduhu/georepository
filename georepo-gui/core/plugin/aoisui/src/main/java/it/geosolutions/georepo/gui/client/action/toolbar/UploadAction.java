/*
 * $Header: it.geosolutions.georepo.gui.client.action.toolbar.UploadAction,v. 0.1 12/ago/2010 10.18.43 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 12/ago/2010 10.18.43 $
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
package it.geosolutions.georepo.gui.client.action.toolbar;

import it.geosolutions.georepo.gui.client.Category;
import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.action.ToolbarMapAction;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;


/**
 * @author giuseppe
 * 
 */
public class UploadAction extends ToolbarMapAction {

	public UploadAction() {
		super(I18nProvider.getMessages().uploadShapeFileToolTip(), Category.DGWATCH_UPLOAD_SHP);
	}

    public void handleEvent(BaseEvent baseEvent) {
        Dispatcher.forwardEvent(DGWATCHEvents.SHOW_UPLOAD_WIDGET);
    }
}
