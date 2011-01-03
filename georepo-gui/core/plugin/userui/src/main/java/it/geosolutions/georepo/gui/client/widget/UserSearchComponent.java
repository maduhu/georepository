/*
 * $Header: it.geosolutions.georepo.gui.client.widget.UserSearchWidget,v. 0.1 28/lug/2010 14.11.17 created by frank $
 * $Revision: 0.1 $
 * $Date: 28/lug/2010 14.11.17 $
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
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.LayoutData;

/**
 * @author frank
 * 
 */
public class UserSearchComponent {

	private FormPanel formPanel;
	private Button search;

	/**
	 * 
	 */
	public UserSearchComponent() {
		this.createFormPanel();
	}

	private void createFormPanel() {
		formPanel = new FormPanel();
		formPanel.setFrame(true);
		formPanel.setHeaderVisible(false);
		formPanel.setAutoHeight(true);

		FieldSet fieldSet = new FieldSet();
		fieldSet.setHeading("Search Management");
		fieldSet.setCheckboxToggle(false);
		fieldSet.setCollapsible(false);

		FormLayout layout = new FormLayout();
		fieldSet.setLayout(layout);

		search = new Button("Search", new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				Dispatcher.forwardEvent(DGWATCHEvents.SHOW_SEARCH_USER_WIDGET);
			}
		});
		
		ButtonBar bar = new ButtonBar();
		bar.setAlignment(HorizontalAlignment.CENTER);
		
		bar.add(search);
		
		Button p = new Button("get AOIs");

		Button q = new Button("get Features");
		
		bar.add(p);
		bar.add(q);
		
		fieldSet.add(bar);

		formPanel.add(fieldSet);
	}

	/**
	 * @return the formPanel
	 */
	public FormPanel getFormPanel() {
		return formPanel;
	}

}
