/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.tab.WatchTabItem,v. 0.1 3-gen-2011 16.58.12 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 16.58.12 $
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
import it.geosolutions.georepo.gui.client.service.WatchServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.WatchGridManagementWidget;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.TabItem;

// TODO: Auto-generated Javadoc
/**
 * The Class WatchTabItem.
 */
public class WatchTabItem extends TabItem {

    /** The watch grid management widget. */
    private WatchGridManagementWidget watchGridManagementWidget;

    /**
     * Instantiates a new watch tab item.
     * 
     * @param service
     *            the service
     */
    public WatchTabItem(WatchServiceRemoteAsync service) {
        super(I18nProvider.getMessages().availableWatchLabel());
        this.watchGridManagementWidget = new WatchGridManagementWidget(service);
        add(watchGridManagementWidget);

        setScrollMode(Scroll.NONE);
    }

    /**
     * Gets the watch grid management widget.
     * 
     * @return the watch grid management widget
     */
    public WatchGridManagementWidget getWatchGridManagementWidget() {
        return watchGridManagementWidget;
    }

}