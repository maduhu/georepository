/*
 * $Header: it.geosolutions.georepo.gui.client.widget.AddAOIFromShpWidget,v. 0.1 17/ago/2010 15.24.16 created by frank $
 * $Revision: 0.1 $
 * $Date: 17/ago/2010 15.24.16 $
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
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.Dispatcher;

/**
 * @author frank
 * 
 */
public class AddAOIFromShpWidget extends AddGenericAOIWidget {

    public AddAOIFromShpWidget(EventType submitEvent, boolean closeOnSubmit) {
        super(submitEvent, closeOnSubmit);
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.geosolutions.georepo.gui.client.widget.AddGenericAOIWidget#
	 * addOtherComponents()
	 */
	@Override
	public void addOtherComponents() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.geosolutions.georepo.gui.client.form.DGWATCHFormWidget#initSize()
	 */
	@Override
	public void initSize() {
		setHeading("Add AOI from SHAPE");
		setSize(420, 250);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.geosolutions.georepo.gui.client.form.DGWATCHFormWidget#initSizeFormPanel
	 * ()
	 */
	@Override
	public void initSizeFormPanel() {
		formPanel.setHeaderVisible(false);
		formPanel.setSize(450, 200);

	}

	@Override
    public void reset() {
		this.resetComponents();
	}

	@Override
    public void resetComponents() {
		this.aoiTitle.reset();
		this.saveStatus.clearStatus("");
	}

	@Override
	public void injectEvent() {
		Dispatcher.forwardEvent(getSubmitEvent(), this.aoi);
	}

}
