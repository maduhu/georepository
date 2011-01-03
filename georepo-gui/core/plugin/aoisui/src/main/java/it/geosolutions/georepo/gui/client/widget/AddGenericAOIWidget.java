/*
 * $Header: it.geosolutions.georepo.gui.client.widget.AddAOIWidget,v. 0.1 06/ago/2010 10.38.23 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 06/ago/2010 10.38.23 $
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


import java.util.Date;

import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.form.DGWATCHFormWidget;
import it.geosolutions.georepo.gui.client.model.AOI;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

/**
 * @author giuseppe
 * 
 */
public abstract class AddGenericAOIWidget extends DGWATCHFormWidget {

    private EventType submitEvent;
    private boolean closeOnSubmit;

	protected TextField<String> aoiTitle;
	protected TextArea wktArea;
	protected Button draw;
	protected AOI aoi = new AOI();

    public AddGenericAOIWidget(EventType submitEvent, boolean closeOnSubmit) {
        this.submitEvent = submitEvent;
        this.closeOnSubmit = closeOnSubmit;
    }

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
	 * @see
	 * it.geosolutions.georepo.gui.client.form.DGWATCHFormWidget#addComponentToForm
	 * ()
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

	public abstract void addOtherComponents();

    /**
     * This method is invoked as a part of the save process after the Submit button has been clicked.
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

	public void resetComponents() {
		this.aoiTitle.reset();
		this.wktArea.reset();
		this.saveStatus.clearStatus("");
		Dispatcher.forwardEvent(DGWATCHEvents.DISABLE_DRAW_BUTTON);
		Dispatcher.forwardEvent(DGWATCHEvents.ERASE_AOI_FEATURES);
	}

	/**
	 * @return the wktArea
	 */
	public TextArea getWktArea() {
		return wktArea;
	}

	public void show(boolean flag) {
		if (flag) {
			resetComponents();
			super.show();
		} else
			super.show();
	}


	/**
	 * @return the aoi
	 */
	public AOI getAoi() {
		return aoi;
	}
}
