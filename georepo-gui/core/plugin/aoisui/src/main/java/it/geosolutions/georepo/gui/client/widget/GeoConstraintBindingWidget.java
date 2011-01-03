/*
 * $Header: it.geosolutions.georepo.gui.client.widget.AOIBindingWidget,v. 0.1 19/lug/2010 15.52.13 created by frank $
 * $Revision: 0.1 $
 * $Date: 19/lug/2010 15.52.13 $
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
import it.geosolutions.georepo.gui.client.model.AOI;
import it.geosolutions.georepo.gui.client.model.AOI.AOIKeyValue;
import it.geosolutions.georepo.gui.client.model.GeoConstraint;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

/**
 * @author gmurray
 *
 */
public class GeoConstraintBindingWidget extends DGWATCHBindingWidget<GeoConstraint> {

	private TextField<String> title;
	private TextField<String> wkt;

	private Button addGeoConstraint;
//	private Button updateGeoConstraint;
	private Button deleteGeoConstraint;
	private Button searchGeoConstraint;
//	private Button aoiClean;

	private FormData formData;

	/**
	 *
	 */
	public GeoConstraintBindingWidget() {
		this.init();
	}

	private void init() {
		formPanel = createFormPanel();
		formBinding = new FormBinding(formPanel, true);
		formData = new FormData("-20");

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * it.geosolutions.georepo.gui.client.widget.BindingWidget#createFormPanel
	 * ()
	 */
	@Override
	public FormPanel createFormPanel() {
		FormPanel fp = new FormPanel();
		fp.setFrame(true);
		fp.setHeaderVisible(false);
		fp.setAutoHeight(true);

		FieldSet fieldSet = new FieldSet();
		fieldSet.setHeading(I18nProvider.getMessages().aoiHeading());
		fieldSet.setCheckboxToggle(false);
		fieldSet.setCollapsible(false);

		FormLayout layout = new FormLayout();
		fieldSet.setLayout(layout);

		this.title = new TextField<String>();
		this.title.setId(GeoConstraint.GeoConstraintKeyValue.NAME.getValue());
		this.title.setName(GeoConstraint.GeoConstraintKeyValue.NAME.getValue());
		this.title.setFieldLabel(I18nProvider.getMessages().aoiTitleLabel());
		this.title.setWidth(100);
		fieldSet.add(this.title, this.formData);

		// area = new NumberField();
		this.wkt = new TextField<String>();
		this.wkt.setId(GeoConstraint.GeoConstraintKeyValue.GEOCONSTRAINT.getValue());
		this.wkt.setName(GeoConstraint.GeoConstraintKeyValue.GEOCONSTRAINT.getValue());
		this.wkt.setFieldLabel(I18nProvider.getMessages().geoConstraintWktLabel());
		this.wkt.setWidth(100);
		this.wkt.setEnabled(false);
		fieldSet.add(this.wkt, this.formData);

		fp.add(fieldSet);

		FlexTable table = new FlexTable();
//		table.getElement().getStyle().setProperty("marginLeft", "60px");

		table.setCellSpacing(8);
		table.setCellPadding(4);

		this.addGeoConstraint = new Button(I18nProvider.getMessages().aoiNew(), new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
//				Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
//						new String[] { "Add AOI", "New GeoConstraint button pressed." });
				Dispatcher.forwardEvent(DGWATCHEvents.SHOW_ADD_GEOCONSTRAINT);
			}
		});

		this.addGeoConstraint.setWidth(100);

		this.addGeoConstraint.setIcon(Resources.ICONS.addAOI());

		table.getCellFormatter().setHorizontalAlignment(1, 1,
				HasHorizontalAlignment.ALIGN_CENTER);

		table.setWidget(1, 1, this.addGeoConstraint);

//		updateAOI = new Button(I18nProvider.getMessages().aoiUpdate(), new SelectionListener<ButtonEvent>() {
//
//			@Override
//			public void componentSelected(ButtonEvent ce) {
//
//				AOI aoi = getModel();
//
//				boolean dirty = false;
//				if (title.isDirty()) {
//					aoi.setTitle(title.getValue());
//					dirty = true;
//				}
//
//				if (expiration.isDirty()) {
//					aoi.setExpiration(expiration.getValue());
//					dirty = true;
//				}
//
//				if (dirty) {
//					Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
//							new String[] { "Update AOI",
//									"Update AOI button pressed." });
//					Dispatcher.forwardEvent(DGWATCHEvents.UPDATE_AOI, aoi);
//				}
//			}
//		});
//
//		updateAOI.disable();
//
//		updateAOI.setWidth(100);
//		updateAOI.setIcon(Resources.ICONS.editAOI());
//
//		table.getCellFormatter().setHorizontalAlignment(1, 2,
//				HasHorizontalAlignment.ALIGN_CENTER);
//
//		table.setWidget(1, 2, this.updateAOI);

        // delete button
		this.deleteGeoConstraint = new Button(I18nProvider.getMessages().geoConstraintDeleteButtonLabel(), new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				MessageBox.confirm("Delete GeoConstraint", "Are you sure to delete GeoConstraint "
						+ getModel().getName() + " ?",
						new Listener<MessageBoxEvent>() {

							public void handleEvent(MessageBoxEvent be) {
								Button btn = be.getButtonClicked();
								if (btn.getText().equalsIgnoreCase("YES"))
									Dispatcher.forwardEvent(DGWATCHEvents.DELETE_GEOCONSTRAINT, getModel());
							}
						});
			}
		});

		this.deleteGeoConstraint.disable();

		this.deleteGeoConstraint.setWidth(130);
		this.deleteGeoConstraint.setIcon(Resources.ICONS.deleteAOI());

		table.getCellFormatter().setHorizontalAlignment(1, 3,
				HasHorizontalAlignment.ALIGN_CENTER);

		table.setWidget(1, 3, this.deleteGeoConstraint);

        // search button
		this.searchGeoConstraint = new Button(I18nProvider.getMessages().aoiSearch(), new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				Dispatcher.forwardEvent(DGWATCHEvents.SEARCH_GEOCONSTRAINT);
			}
		});

		this.searchGeoConstraint.setWidth(100);

		this.searchGeoConstraint.setIcon(Resources.ICONS.search());

		table.getCellFormatter().setHorizontalAlignment(2, 1,
				HasHorizontalAlignment.ALIGN_CENTER);

		table.setWidget(2, 1, this.searchGeoConstraint);

//		aoiWatches = new Button(I18nProvider.getMessages().aoiWatches(), new SelectionListener<ButtonEvent>() {
//
//			@Override
//			public void componentSelected(ButtonEvent ce) {
//				Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
//						new String[] { "Search AOI Watches",
//								"Search AOI Watches button pressed." });
//			}
//		});
//
//		aoiWatches.setIcon(Resources.ICONS.searchWatches());
//		aoiWatches.setWidth(100);
//		aoiWatches.disable();
//
//		table.getCellFormatter().setHorizontalAlignment(2, 2,
//				HasHorizontalAlignment.ALIGN_CENTER);
//
//		table.setWidget(2, 2, this.aoiWatches);
//
//		aoiClean = new Button(I18nProvider.getMessages().aoiClean(), new SelectionListener<ButtonEvent>() {
//
//			@Override
//			public void componentSelected(ButtonEvent ce) {
//				Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
//						new String[] { "Clean AOI",
//								"Clean AOI button pressed." });
//				Dispatcher.forwardEvent(DGWATCHEvents.AOI_MANAGEMENT_UNBIND);
//			}
//		});
//
//		aoiClean.setIcon(Resources.ICONS.cleanDgWatchMenu());
//		aoiClean.setWidth(100);
//		aoiClean.disable();
//
//		table.getCellFormatter().setHorizontalAlignment(2, 3,
//				HasHorizontalAlignment.ALIGN_CENTER);
//
//		table.setWidget(2, 3, this.aoiClean);

		fp.add(table/*, new VBoxLayoutData(5, 0, 0, 90)*/);

		return fp;
	}

	/**
	 * Enable Both Delete AOI and Update AOI Buttons
	 */
	public void enableButtons() {
		this.deleteGeoConstraint.enable();
	}

	/**
	 * Disable Both Delete AOI and Update AOI Buttons
	 */
	public void disableButtons() {
		this.deleteGeoConstraint.disable();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * it.geosolutions.georepo.gui.client.widget.DGWATCHBindingWidget#unBindModel
	 * ()
	 */
	@Override
    public void unBindModel() {
		super.unBindModel();
		disableButtons();
	}

}