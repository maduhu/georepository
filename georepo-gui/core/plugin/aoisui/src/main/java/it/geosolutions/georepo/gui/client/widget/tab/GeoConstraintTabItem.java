/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.tab.GeoConstraintTabItem,v. 0.1 3-gen-2011 16.58.12 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.GeoConstraint;
import it.geosolutions.georepo.gui.client.model.Member;
import it.geosolutions.georepo.gui.client.service.MembersRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.GeoConstraintManagementWidget;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.widget.TabItem;

// TODO: Auto-generated Javadoc
/**
 * The Class GeoConstraintTabItem.
 */
public class GeoConstraintTabItem extends TabItem implements Listener {

    /** The gc management widget. */
    private GeoConstraintManagementWidget gcManagementWidget;

    /**
     * Instantiates a new geo constraint tab item.
     * 
     * @param service
     *            the service
     */
    public GeoConstraintTabItem(MembersRemoteAsync service) {
        super(I18nProvider.getMessages().geoConstraintTabLabel());
        this.gcManagementWidget = new GeoConstraintManagementWidget(service);
        add(gcManagementWidget);

        addListener(DGWATCHEvents.BIND_SELECTED_MEMBER, this);
        addListener(DGWATCHEvents.RELOAD_GEOCONSTRAINTS, this);

        setScrollMode(Scroll.NONE);
    }

    /**
     * Gets the geo constraint management widget.
     * 
     * @return the geo constraint management widget
     */
    public GeoConstraintManagementWidget getGeoConstraintManagementWidget() {
        return gcManagementWidget;
    }

    /**
     * Removes the geo constraint.
     * 
     * @param gc
     *            the gc
     */
    public void removeGeoConstraint(GeoConstraint gc) {
        this.gcManagementWidget.removeGeoConstraint(gc);
    }

    /**
     * Removes the geo constraint.
     * 
     * @param gcId
     *            the gc id
     */
    public void removeGeoConstraint(Integer gcId) {
        this.gcManagementWidget.removeGeoConstraint(gcId);
    }

    /**
     * Update geo constraint title.
     * 
     * @param gc
     *            the gc
     */
    public void updateGeoConstraintTitle(GeoConstraint gc) {
        this.gcManagementWidget.updateGeoConstraintTitle(gc);
    }

    /**
     * Update geo constraint status.
     * 
     * @param gc
     *            the gc
     */
    public void updateGeoConstraintStatus(GeoConstraint gc) {
        this.gcManagementWidget.updateGeoConstraintStatus(gc);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.extjs.gxt.ui.client.event.Listener#handleEvent(com.extjs.gxt.ui.client.event.BaseEvent)
     */
    public void handleEvent(BaseEvent e) {
        if (e.getType() == DGWATCHEvents.BIND_SELECTED_MEMBER) {
            onBindMember((AppEvent) e);
        }
        if (e.getType() == DGWATCHEvents.RELOAD_GEOCONSTRAINTS) {
            onReloadGeoConstraints((AppEvent) e);
        }
    }

    /**
     * On bind member.
     * 
     * @param event
     *            the event
     */
    private void onBindMember(AppEvent event) {
        gcManagementWidget.loadGeoConstraints((Member) event.getData());
    }

    /**
     * On reload geo constraints.
     * 
     * @param event
     *            the event
     */
    private void onReloadGeoConstraints(AppEvent event) {
        gcManagementWidget.loadGeoConstraints((Member) event.getData());
    }

    /**
     * Contains.
     * 
     * @param gc
     *            the gc
     * @return true, if successful
     */
    public boolean contains(GeoConstraint gc) {
        return this.gcManagementWidget.contains(gc);
    }

}