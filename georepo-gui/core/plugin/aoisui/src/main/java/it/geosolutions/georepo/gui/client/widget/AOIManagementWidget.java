/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.AOIManagementWidget,v. 0.1 3-gen-2011 16.52.55 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 16.52.55 $
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

import it.geosolutions.georepo.gui.client.model.AOI;
import it.geosolutions.georepo.gui.client.service.AOIServiceRemoteAsync;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class AOIManagementWidget.
 */
public class AOIManagementWidget extends ContentPanel {

    /** The aoi pag grid widget. */
    private AOIPaginationGridWidget aoiPagGridWidget;

    /**
     * Instantiates a new aOI management widget.
     * 
     * @param service
     *            the service
     */
    public AOIManagementWidget(AOIServiceRemoteAsync service) {
        setHeaderVisible(false);
        setFrame(true);
        setHeight(170);
        setLayout(new FitLayout());

        this.aoiPagGridWidget = new AOIPaginationGridWidget(service);
        add(this.aoiPagGridWidget.getGrid());

        super.setMonitorWindowResize(true);

        setScrollMode(Scroll.NONE);

        setBottomComponent(this.aoiPagGridWidget.getToolBar());
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
     * Gets the aoi pag grid widget.
     * 
     * @return the aoi pag grid widget
     */
    public AOIPaginationGridWidget getAoiPagGridWidget() {
        return aoiPagGridWidget;
    }

    /**
     * Removes the aoi.
     * 
     * @param aoi
     *            the aoi
     */
    public void removeAOI(AOI aoi) {
        this.aoiPagGridWidget.removeAOI(aoi);
    }

    /**
     * Update aoi title.
     * 
     * @param aoi
     *            the aoi
     */
    public void updateAOITitle(AOI aoi) {
        this.aoiPagGridWidget.updateAOITitle(aoi);
    }

    /**
     * Update aoi status.
     * 
     * @param aoi
     *            the aoi
     */
    public void updateAOIStatus(AOI aoi) {
        this.aoiPagGridWidget.updateAOIStatus(aoi);
    }

}
