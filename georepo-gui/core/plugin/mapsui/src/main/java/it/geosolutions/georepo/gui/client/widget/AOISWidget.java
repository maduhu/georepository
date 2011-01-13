/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.AOISWidget,v. 0.1 3-gen-2011 16.52.57 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 16.52.57 $
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

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class AOISWidget.
 */
public class AOISWidget extends ContentPanel {

    /** The aoi binding. */
    private GeoRepoBindingWidget<AOI> aoiBinding;

    /**
     * Instantiates a new aOIS widget.
     * 
     * @param paneHeading
     *            the pane heading
     */
    public AOISWidget(String paneHeading) {
        setId("aoisWidget");
        setHeading(paneHeading);
        setLayout(new FitLayout());

        setLayoutOnChange(true);

        this.aoiBinding = new AOIBindingWidget();

        add(this.aoiBinding.getFormPanel());

        setScrollMode(Scroll.AUTOY);
    }

    /**
     * Gets the aoi binding.
     * 
     * @return the aoi binding
     */
    public GeoRepoBindingWidget<AOI> getAoiBinding() {
        return aoiBinding;
    }

}
