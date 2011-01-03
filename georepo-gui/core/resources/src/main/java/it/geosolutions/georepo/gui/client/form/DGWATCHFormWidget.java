/*
 * $Header: it.geosolutions.georepo.gui.client.form.DGWATCHFormWidget,v. 0.1 28/lug/2010 14.27.51 created by frank $
 * $Revision: 0.1 $
 * $Date: 28/lug/2010 14.27.51 $
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
package it.geosolutions.georepo.gui.client.form;


import it.geosolutions.georepo.gui.client.widget.SaveStaus;
import it.geosolutions.georepo.gui.client.widget.SaveStaus.EnumSaveStatus;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.event.WindowListener;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;

/**
 * @author frank
 * 
 */
public abstract class DGWATCHFormWidget extends Window implements IForm {

	protected FormPanel formPanel = new FormPanel();
	protected Button submit;
	protected Button cancel;
	protected FieldSet fieldSet;

	protected SaveStaus saveStatus;

	/**
	 * 
	 */
	public DGWATCHFormWidget() {
		this.initializeWindow();
		this.initializeFormPanel();
		this.add(this.formPanel);
	}

	private void initializeFormPanel() {
		this.formPanel.setFrame(true);
		this.formPanel.setLayout(new FlowLayout());

		initSizeFormPanel();
		addComponentToForm();
		addButtons();
	}

	private void initializeWindow() {
		initSize();
		setResizable(false);

		addWindowListener(new WindowListener() {

			@Override
            public void windowHide(WindowEvent we) {
				reset();
			}

		});

		setLayout(new FitLayout());
		setModal(true);
		setPlain(true);
	}

	private void addButtons() {
		formPanel.setButtonAlign(HorizontalAlignment.LEFT);

		this.saveStatus = new SaveStaus();
		saveStatus.setAutoWidth(true);

		formPanel.getButtonBar().add(saveStatus);

		formPanel.getButtonBar().add(new FillToolItem());

		this.submit = new Button("SUBMIT");

		this.submit.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
            public void componentSelected(ButtonEvent ce) {
				if (formPanel.isValid())
					execute();
			}

		});

		submit.setIconStyle("x-dgwatch-submit");
		
		formPanel.addButton(submit);

		this.cancel = new Button("CANCEL");

		this.cancel.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
            public void componentSelected(ButtonEvent ce) {
				cancel();
			}
		});
		
		this.cancel.setIconStyle("x-dgwatch-cancel");

		formPanel.addButton(cancel);
	}

	/**
	 * Set the correct Status Iconn Style
	 */
	public void setSaveStatus(EnumSaveStatus status, EnumSaveStatus message) {
		this.saveStatus.setIconStyle(status.getValue());
		this.saveStatus.setText(message.getValue());
	}
	
	public void reset(){
		
	}

	public abstract void addComponentToForm();

	public abstract void initSize();

	public abstract void initSizeFormPanel();

	public abstract void cancel();
}
