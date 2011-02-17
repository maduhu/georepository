/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.tab.InstancesTabItem,v. 0.1 25-gen-2011 11.23.48 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.23.48 $
 *
 * ====================================================================
 *
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 * http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
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
import it.geosolutions.georepo.gui.client.service.InstancesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.InstanceManagementWidget;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.TabItem;

// TODO: Auto-generated Javadoc
/**
 * The Class InstancesTabItem.
 */
public class InstancesTabItem extends TabItem {

    /** The instance management widget. */
    private InstanceManagementWidget instanceManagementWidget;

    /**
     * Instantiates a new instances tab item.
     */
    public InstancesTabItem(String tabItemId) {
        // TODO: add I18n message
        //super(I18nProvider.getMessages().instances());
        super("Instances");
        setId(tabItemId);
        setIcon(Resources.ICONS.pageEdit());
    }

    /**
     * Instantiates a new instances tab item.
     * @param instancesTabItemId 
     * 
     * @param instancesManagerServiceRemote
     *            the instances manager service remote
     */
    public InstancesTabItem(String tabItemId, InstancesManagerServiceRemoteAsync instancesManagerServiceRemote) {
        this(tabItemId);
        setInstanceManagementWidget(new InstanceManagementWidget(instancesManagerServiceRemote));
        add(getInstanceManagementWidget());

        setScrollMode(Scroll.NONE);
        //setHeight("100%");
        getInstanceManagementWidget().getInstancesInfo().getLoader().load(0, 25);

    }

    /**
     * Sets the feature management widget.
     * 
     * @param instanceManagementWidget
     *            the new feature management widget
     */
    public void setInstanceManagementWidget(InstanceManagementWidget instanceManagementWidget) {
        this.instanceManagementWidget = instanceManagementWidget;
    }

    /**
     * Gets the feature management widget.
     * 
     * @return the feature management widget
     */
    public InstanceManagementWidget getInstanceManagementWidget() {
        return instanceManagementWidget;
    }

}
