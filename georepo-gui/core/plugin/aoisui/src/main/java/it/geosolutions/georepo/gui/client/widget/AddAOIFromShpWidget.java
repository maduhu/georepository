/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.AddAOIFromShpWidget,v. 0.1 3-gen-2011 16.52.55 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.Dispatcher;

// TODO: Auto-generated Javadoc
/**
 * The Class AddAOIFromShpWidget.
 */
public class AddAOIFromShpWidget extends AddGenericAOIWidget {

    /**
     * Instantiates a new adds the aoi from shp widget.
     * 
     * @param submitEvent
     *            the submit event
     * @param closeOnSubmit
     *            the close on submit
     */
    public AddAOIFromShpWidget(EventType submitEvent, boolean closeOnSubmit) {
        super(submitEvent, closeOnSubmit);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.AddGenericAOIWidget# addOtherComponents()
     */
    @Override
    public void addOtherComponents() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.form.DGWATCHFormWidget#initSize()
     */
    @Override
    public void initSize() {
        setHeading("Add AOI from SHAPE");
        setSize(420, 250);

    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.form.DGWATCHFormWidget#initSizeFormPanel ()
     */
    @Override
    public void initSizeFormPanel() {
        formPanel.setHeaderVisible(false);
        formPanel.setSize(450, 200);

    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.form.DGWATCHFormWidget#reset()
     */
    @Override
    public void reset() {
        this.resetComponents();
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.AddGenericAOIWidget#resetComponents()
     */
    @Override
    public void resetComponents() {
        this.aoiTitle.reset();
        this.saveStatus.clearStatus("");
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.AddGenericAOIWidget#injectEvent()
     */
    @Override
    public void injectEvent() {
        Dispatcher.forwardEvent(getSubmitEvent(), this.aoi);
    }

}
