/*
 * $Header: it.geosolutions.georepo.gui.client.widget.AddAOIWidget,v. 0.1 17/ago/2010 11.59.33 created by frank $
 * $Revision: 0.1 $
 * $Date: 17/ago/2010 11.59.33 $
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
import it.geosolutions.georepo.gui.client.Resources;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.GeoConstraint;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextArea;

/**
 * @author frank
 *
 */
public class AddGeoConstraintWidget extends AddGenericAOIWidget {

    public AddGeoConstraintWidget(EventType submitEvent, boolean closeOnSubmit) {
        super(submitEvent, closeOnSubmit);
    }

	/*
	 * (non-Javadoc)
	 *
	 * @see it.geosolutions.georepo.gui.client.form.IForm#execute()
	 */
	@Override
    public void execute() {
		this.aoi.setWkt(this.wktArea.getValue());
		super.execute();
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see it.geosolutions.georepo.gui.client.widget.AddGenericAOIWidget#
	 * addOtherComponents()
	 */
	@Override
	public void addOtherComponents() {
		wktArea = new TextArea();
		wktArea.setFieldLabel(I18nProvider.getMessages().wktAbbreviation());
		wktArea.setAllowBlank(false);
		fieldSet.add(wktArea);

		draw = new Button(I18nProvider.getMessages().drawAoiButton(), new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				hide();
				Dispatcher.forwardEvent(DGWATCHEvents.ENABLE_DRAW_BUTTON, AddGeoConstraintWidget.this);
			}
		});

		draw.setIcon(Resources.ICONS.drawFeature());

		this.formPanel.addButton(draw);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see it.geosolutions.georepo.gui.client.form.DGWATCHFormWidget#initSize()
	 */
	@Override
	public void initSize() {
		setHeading(I18nProvider.getMessages().addAoiDialogTitle());
		setSize(420, 300);

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
		formPanel.setSize(450, 350);
	}


	@Override
	public void injectEvent() {
        GeoConstraint gc = new GeoConstraint();
        gc.setName(this.aoi.getTitle());
        gc.setWkt(this.aoi.getWkt());
        Dispatcher.forwardEvent(getSubmitEvent(), gc);
    }

}