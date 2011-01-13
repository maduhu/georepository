/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.tab.GeoRSSTabItem,v. 0.1 3-gen-2011 16.58.12 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.georepo.gui.client.Resources;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.service.FeatureServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.FeatureManagementWidget;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.TabItem;

// TODO: Auto-generated Javadoc
/**
 * The Class GeoRSSTabItem.
 */
public class GeoRSSTabItem extends TabItem {

    /** The feature management widget. */
    private FeatureManagementWidget featureManagementWidget;

    /**
     * Instantiates a new geo rss tab item.
     */
    public GeoRSSTabItem() {
        super(I18nProvider.getMessages().geoRssLabel());
        setIcon(Resources.ICONS.rss());
    }

    /**
     * Instantiates a new geo rss tab item.
     * 
     * @param service
     *            the service
     */
    public GeoRSSTabItem(FeatureServiceRemoteAsync service) {
        this();
        this.featureManagementWidget = new FeatureManagementWidget(service);
        add(featureManagementWidget);

        setScrollMode(Scroll.NONE);
    }

    /**
     * Gets the feature management widget.
     * 
     * @return the feature management widget
     */
    public FeatureManagementWidget getFeatureManagementWidget() {
        return featureManagementWidget;
    }

}
