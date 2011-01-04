/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.AddGeoConstraintFromShpWidget,v. 0.1 3-gen-2011 16.52.56 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.Dispatcher;

// TODO: Auto-generated Javadoc
/**
 * The Class AddGeoConstraintFromShpWidget.
 */
public class AddGeoConstraintFromShpWidget extends AddGenericAOIWidget {

    /**
     * Instantiates a new adds the geo constraint from shp widget.
     * 
     * @param submitEvent
     *            the submit event
     * @param closeOnSubmit
     *            the close on submit
     */
    public AddGeoConstraintFromShpWidget(EventType submitEvent, boolean closeOnSubmit) {
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
        setHeading("Add GeoConstraint from SHAPE");
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
        GeoConstraint gc = new GeoConstraint();
        gc.setName(this.aoi.getTitle());
        gc.setWkt(this.aoi.getWkt());
        Dispatcher.forwardEvent(getSubmitEvent(), gc);
    }

}