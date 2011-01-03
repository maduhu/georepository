/*
 * $Header: it.geosolutions.georepo.gui.client.widget.UploadWatchAreaWidget,v. 0.1 19/lug/2010 09.23.25 created by frank $
 * $Revision: 0.1 $
 * $Date: 19/lug/2010 09.23.25 $
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
import it.geosolutions.georepo.gui.client.widget.SaveStaus.EnumSaveStatus;
import it.geosolutions.georepo.gui.client.widget.map.MapPreviewWidget;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author frank
 * 
 */
public class UploadWatchAreaWidget extends Dialog {
	
	private Button done;
	private FileUpload upload;
	
	private SaveStaus saveStatus;
	private String wkt;
	
	private FormPanel formPanel;
	private com.extjs.gxt.ui.client.widget.form.FormPanel previewFP;
	private MapPreviewWidget mapPreviewWidget;
	
	private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL()
	+ "upload";
	
	/**
	 * 
	 */
	public UploadWatchAreaWidget() {
		setHeading("[AOI] - Upload Watch Area");
		setResizable(false);
		setButtons("");
		setClosable(true);
		setModal(true);
		setWidth(400);
		
		this.addListener(Events.Hide, new Listener<WindowEvent>() {
			
			public void handleEvent(WindowEvent be) {
				reset();
			}
		});
		
		this.addListener(Events.Show, new Listener<WindowEvent>() {
			
			public void handleEvent(WindowEvent be) {
				mapPreviewWidget.getMapWidget().getMap().zoomToMaxExtent();
				// mapPreviewWidget.getMapWidget().getMap().zoomTo(1);
				mapPreviewWidget.getMapWidget().getMap().updateSize();
			}
		});
		
		this.createUpload();
		this.createMapPreview();
		
		add(this.previewFP);
	}
	
	private void createMapPreview() {
		this.previewFP = new com.extjs.gxt.ui.client.widget.form.FormPanel();
		this.previewFP.setFrame(true);
		this.previewFP.setLayout(new FlowLayout());
		
		this.previewFP.setHeaderVisible(false);
		
		FieldSet fieldSetUpload = new FieldSet();
		fieldSetUpload.setHeading("Upload");
		fieldSetUpload.setHeight(80);
		fieldSetUpload.setLayout(new CenterLayout());
		
		fieldSetUpload.add(this.formPanel);
		
		this.previewFP.add(fieldSetUpload);
		
		FieldSet fieldSetMapPreview = new FieldSet();
		fieldSetMapPreview.setHeading("Map Preview");
		
		this.mapPreviewWidget = new MapPreviewWidget();
		
		ContentPanel cp = new ContentPanel();
		cp.setHeaderVisible(false);
		cp.setLayout(new FitLayout());
		cp.setHeight(200);
		
		cp.add(this.mapPreviewWidget.getMapWidget());
		
		fieldSetMapPreview.add(cp);
		
		this.previewFP.add(fieldSetMapPreview);
	}
	
	private void createUpload() {
		formPanel = new FormPanel();
		formPanel.setAction(UPLOAD_ACTION_URL);
		
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_POST);
		
		VerticalPanel panel = new VerticalPanel();
		formPanel.setWidget(panel);
		
		upload = new FileUpload();
		upload.setName("uploadFormElement");
		panel.add(upload);
		
		panel.add(new Button("Submit", new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				formPanel.submit();
				if ((upload.getFilename() != null)
					&& (upload.getFilename().endsWith("zip")))
					saveStatus.setBusy("Uploading in progress...");
			}
		}));
		
		// // Add an event handler to the form.
		formPanel.addSubmitHandler(new FormPanel.SubmitHandler() {
			public void onSubmit(SubmitEvent event) {
				// This event is fired just before the form is submitted. We can
				// take this opportunity to perform validation.
				if (!upload.getFilename().endsWith("zip")) {
					Window.alert("You must upload only file .zip");
					event.cancel();
					formPanel.reset();
				}
			}
		});
		
		formPanel
		.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			public void onSubmitComplete(SubmitCompleteEvent event) {
				// When the form submission is successfully completed,
				// this
				// event is fired. Assuming the service returned a
				// response of
				// type
				// text/html, we can get the result text here (see the
				// FormPanel
				// documentation for further explanation).
				formPanel.reset();
				wkt = event.getResults();
				wkt = wkt.replaceAll("<pre>", "");
				wkt = wkt.replaceAll("</pre>", "");
				
				wkt = wkt
				.replaceAll(
							"<pre style=\"word-wrap: break-word; white-space: pre-wrap;\">",
							"");
				
				if ((wkt != null) && !(wkt.length() == 0)) {
					
					if (wkt.contains("Error : ")) {
						MessageBox.alert("Alert", wkt,
										 new Listener<MessageBoxEvent>() {
											 
											 public void handleEvent(
																	 MessageBoxEvent be) {
											 }
										 });
						saveStatus.clearStatus("");
						return;
					}
					
					done.enable();
					mapPreviewWidget.drawAoiOnMap(wkt);
					
					setSaveStatus(EnumSaveStatus.STATUS_SAVE,
								  EnumSaveStatus.STATUS_MESSAGE_SAVE);
				} else
					setSaveStatus(EnumSaveStatus.STATUS_NO_SAVE,
								  EnumSaveStatus.STATUS_MESSAGE_SAVE_ERROR);
			}
		});
		
	}
	
	@Override
    protected void createButtons() {
		super.createButtons();
		
		this.saveStatus = new SaveStaus();
		this.saveStatus.setAutoWidth(true);
		
		getButtonBar().add(saveStatus);
		
		getButtonBar().add(new FillToolItem());
		
		this.done = new Button("Done", new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				hide();
				Dispatcher.forwardEvent(DGWATCHEvents.INJECT_WKT_FROM_SHP, wkt);
			}
		});
		
		this.done.disable();
		addButton(done);
	}
	
	@Override
    public void show() {
		super.show();
	}
	
	public void reset() {
		this.done.disable();
		this.saveStatus.clearStatus("");
		this.mapPreviewWidget.rebuildVectorLayer();
	}
	
	/**
	 * Set the correct Status Iconn Style
	 */
	public void setSaveStatus(EnumSaveStatus status, EnumSaveStatus message) {
		this.saveStatus.setIconStyle(status.getValue());
		this.saveStatus.setText(message.getValue());
	}
	
}
