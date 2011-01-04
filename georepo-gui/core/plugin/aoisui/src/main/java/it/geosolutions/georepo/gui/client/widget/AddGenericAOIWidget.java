/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.AddGenericAOIWidget,v. 0.1 3-gen-2011 16.52.56 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.form.DGWATCHFormWidget;
import it.geosolutions.georepo.gui.client.model.AOI;

import java.util.Date;

import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class AddGenericAOIWidget.
 */
public abstract class AddGenericAOIWidget extends DGWATCHFormWidget {

    /** The submit event. */
    private EventType submitEvent;

    /** The close on submit. */
    private boolean closeOnSubmit;

    /** The aoi title. */
    protected TextField<String> aoiTitle;

    /** The wkt area. */
    protected TextArea wktArea;

    /** The draw. */
    protected Button draw;

    /** The aoi. */
    protected AOI aoi = new AOI();

    /**
     * Instantiates a new adds the generic aoi widget.
     * 
     * @param submitEvent
     *            the submit event
     * @param closeOnSubmit
     *            the close on submit
     */
    public AddGenericAOIWidget(EventType submitEvent, boolean closeOnSubmit) {
        this.submitEvent = submitEvent;
        this.closeOnSubmit = closeOnSubmit;
    }

    /**
     * Gets the submit event.
     * 
     * @return the submit event
     */
    protected EventType getSubmitEvent() {
        return this.submitEvent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.form.IForm#execute()
     */
    public void execute() {
        this.saveStatus.setBusy("Operation in progress");
        this.aoi.setTitle(aoiTitle.getValue());
        this.aoi.setDateCreation(new Date());
        this.aoi.setShared(true);

        if (this.closeOnSubmit) {
            this.saveStatus.clearState();
            this.hide();
        }

        this.injectEvent();
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.form.DGWATCHFormWidget#addComponentToForm ()
     */
    @Override
    public void addComponentToForm() {
        fieldSet = new FieldSet();
        fieldSet.setHeading("AOI Information");
        FormLayout layout = new FormLayout();
        layout.setLabelWidth(80);
        fieldSet.setLayout(layout);

        aoiTitle = new TextField<String>();
        aoiTitle.setAllowBlank(false);
        aoiTitle.setFieldLabel("Title");
        fieldSet.add(aoiTitle);

        this.formPanel.add(fieldSet);

        addOtherComponents();
    }

    /**
     * Adds the other components.
     */
    public abstract void addOtherComponents();

    /**
     * Inject event.
     */
    public abstract void injectEvent();

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.form.DGWATCHFormWidget#cancel()
     */
    @SuppressWarnings("deprecation")
    @Override
    public void cancel() {
        resetComponents();
        super.close();

    }

    /**
     * Reset components.
     */
    public void resetComponents() {
        this.aoiTitle.reset();
        this.wktArea.reset();
        this.saveStatus.clearStatus("");
        Dispatcher.forwardEvent(DGWATCHEvents.DISABLE_DRAW_BUTTON);
        Dispatcher.forwardEvent(DGWATCHEvents.ERASE_AOI_FEATURES);
    }

    /**
     * Gets the wkt area.
     * 
     * @return the wkt area
     */
    public TextArea getWktArea() {
        return wktArea;
    }

    /**
     * Show.
     * 
     * @param flag
     *            the flag
     */
    public void show(boolean flag) {
        if (flag) {
            resetComponents();
            super.show();
        } else
            super.show();
    }

    /**
     * Gets the aoi.
     * 
     * @return the aoi
     */
    public AOI getAoi() {
        return aoi;
    }
}
