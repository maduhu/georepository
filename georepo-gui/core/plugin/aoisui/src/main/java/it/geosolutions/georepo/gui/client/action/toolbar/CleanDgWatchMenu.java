/*
 * $ Header: it.geosolutions.georepo.gui.client.action.toolbar.CleanDgWatchMenu,v. 0.1 3-gen-2011 16.52.16 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 16.52.16 $
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

// TODO: Auto-generated Javadoc
/**
 * The Class CleanDgWatchMenu.
 */
public class CleanDgWatchMenu extends ToolbarMapAction {

    /**
     * Instantiates a new clean dg watch menu.
     */
    public CleanDgWatchMenu() {
        super(I18nProvider.getMessages().cleanDGWatchMenuToolTip(), Category.DGWATCH_CLEAN);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.extjs.gxt.ui.client.event.Listener#handleEvent(com.extjs.gxt.ui.client.event.BaseEvent)
     */
    public void handleEvent(BaseEvent baseEvent) {
        Dispatcher.forwardEvent(DGWATCHEvents.UNBIND_SELECTED_MEMBER);
        Dispatcher.forwardEvent(DGWATCHEvents.UNBINDING_WATCH_WIDGET);
        Dispatcher.forwardEvent(DGWATCHEvents.UNBIND_FILTER_WIDGET);
        Dispatcher.forwardEvent(DGWATCHEvents.AOI_MANAGEMENT_UNBIND);
        Dispatcher.forwardEvent(DGWATCHEvents.RESET_AOI_GRID);
        Dispatcher.forwardEvent(DGWATCHEvents.ERASE_AOI_FEATURES);
        Dispatcher.forwardEvent(DGWATCHEvents.RESET_RSS_GRID);
        Dispatcher.forwardEvent(DGWATCHEvents.RESET_WATCH_GRID);

        Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE, new String[] {
                I18nProvider.getMessages().cleanDGWatchDialogTitle(),
                I18nProvider.getMessages().cleanDGWatchDialogMessage() });
    }
}
