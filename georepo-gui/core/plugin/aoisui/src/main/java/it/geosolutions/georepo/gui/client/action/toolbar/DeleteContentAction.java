/*
 * $Header: it.geosolutions.georepo.gui.client.action.toolbar.ZoomOut,v. 0.1 27/lug/2010 11.23.49 created by frank $
 * $Revision: 0.1 $
 * $Date: 27/lug/2010 11.23.49 $
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
 * This action triggers a deletion of content from a particular remote node.
 *
 * @author gmurray
 *
 */
public class DeleteContentAction extends ToolbarMapAction
{
	public DeleteContentAction() {
		super(I18nProvider.getMessages().deleteContentToolTip(), Category.DELETE_CONTENT);
	}

    public void handleEvent(BaseEvent baseEvent) {
        Dispatcher.forwardEvent(DGWATCHEvents.DELETE_CONTENT);
    }
}