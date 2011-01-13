/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.WatchesManagementWidget,v. 0.1 3-gen-2011 16.52.56 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 16.52.56 $
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

import java.util.List;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.ContentPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class WatchesManagementWidget.
 */
public class WatchesManagementWidget extends ContentPanel {

    /** The watches info. */
    private WatchesInfoBindingWidget watchesInfo;

    /**
     * Instantiates a new watches management widget.
     */
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
     * Gets the watches info.
     * 
     * @return the watches info
     */
    public WatchesInfoBindingWidget getWatchesInfo() {
        return watchesInfo;
    }

    /**
     * Inject security.
     * 
     * @param auths
     *            the auths
     */
    public void injectSecurity(List<Authorization> auths) {
        this.watchesInfo.injectSecurity(auths);
    }

}
