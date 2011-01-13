/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.GeoConstraintManagementWidget,v. 0.1 3-gen-2011 16.52.56 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.georepo.gui.client.model.GeoConstraint;
import it.geosolutions.georepo.gui.client.model.Member;
import it.geosolutions.georepo.gui.client.service.MembersRemoteAsync;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class GeoConstraintManagementWidget.
 */
public class GeoConstraintManagementWidget extends ContentPanel {

    /** The geo constraint page grid widget. */
    private GeoConstraintPaginationGridWidget geoConstraintPageGridWidget;

    /**
     * Instantiates a new geo constraint management widget.
     * 
     * @param service
     *            the service
     */
    public GeoConstraintManagementWidget(MembersRemoteAsync service) {
        setId("geoConstraintManagementWidget");
        setHeaderVisible(false);
        setFrame(true);
        setHeight(170);
        setLayout(new FitLayout());

        this.geoConstraintPageGridWidget = new GeoConstraintPaginationGridWidget(service);
        add(this.geoConstraintPageGridWidget.getGrid());

        super.setMonitorWindowResize(true);

        setScrollMode(Scroll.NONE);

        setBottomComponent(this.geoConstraintPageGridWidget.getToolBar());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.widget.Component#onWindowResize(int, int)
     */
    @Override
    protected void onWindowResize(int width, int height) {
        // TODO Auto-generated method stub
        super.setWidth(width - 5);
        super.layout();
    }

    /**
     * Load geo constraints.
     * 
     * @param member
     *            the member
     */
    public void loadGeoConstraints(Member member) {
        geoConstraintPageGridWidget.setConnectId(member.getConnectId());
        geoConstraintPageGridWidget.loadData();
    }

    /**
     * Gets the geo constraint page grid widget.
     * 
     * @return the geo constraint page grid widget
     */
    public GeoConstraintPaginationGridWidget getGeoConstraintPageGridWidget() {
        return geoConstraintPageGridWidget;
    }

    /**
     * Removes the geo constraint.
     * 
     * @param gc
     *            the gc
     */
    public void removeGeoConstraint(GeoConstraint gc) {
        this.geoConstraintPageGridWidget.removeGeoConstraint(gc);
    }

    /**
     * Removes the geo constraint.
     * 
     * @param gcId
     *            the gc id
     */
    public void removeGeoConstraint(Integer gcId) {
        this.geoConstraintPageGridWidget.removeGeoConstraint(gcId);
    }

    /**
     * Update geo constraint title.
     * 
     * @param gc
     *            the gc
     */
    public void updateGeoConstraintTitle(GeoConstraint gc) {
        this.geoConstraintPageGridWidget.updateGeoConstraintTitle(gc);
    }

    /**
     * Update geo constraint status.
     * 
     * @param gc
     *            the gc
     */
    public void updateGeoConstraintStatus(GeoConstraint gc) {
        this.geoConstraintPageGridWidget.updateGeoConstraintStatus(gc);
    }

    /**
     * Contains.
     * 
     * @param gc
     *            the gc
     * @return true, if successful
     */
    public boolean contains(GeoConstraint gc) {
        return this.geoConstraintPageGridWidget.getStore().contains(gc);
    }

}