/*
 * $Header: it.geosolutions.georepo.gui.client.widget.FilterBindingWidget,v. 0.1 18/ago/2010 09.50.16 created by frank $
 * $Revision: 0.1 $
 * $Date: 18/ago/2010 09.50.16 $
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
 * <http:www.geo-solutions.it/>.
 *
 */
package it.geosolutions.georepo.gui.client.widget;

import java.util.Date;

import it.geosolutions.georepo.gui.client.DGWATCHData;
import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.FilterType;
import it.geosolutions.georepo.gui.client.FilterType.FilterTypeEnum;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.Filter;
import it.geosolutions.georepo.gui.client.model.Filter.FilterKeyValue;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.datepicker.client.TimePicker;

/**
 * @author frank
 * 
 */
public class FilterBindingWidget {

	private DateField acqDate;
	private TextField<String> dataLayer;
	private TextField<String> legacyId;
	private TextField<String> sourceId;
	
	private NumberField cloudCover;

	private ComboBox<FilterType> acqDateCombo;
	private ComboBox<FilterType> dataLayerCombo;
	private ComboBox<FilterType> cloudCoverCombo;
	private ComboBox<FilterType> legacyIdCombo;
	private ComboBox<FilterType> sourceIdCombo;

	private ListStore<FilterType> storeAcqDate;
	private ListStore<FilterType> storeDataLayer;
	private ListStore<FilterType> storeCloudCover;
	private ListStore<FilterType> storeLegacyId;
	private ListStore<FilterType> storeSourceId;

	private FormData formData;
	private FormPanel formPanel;
	private Filter filter;

	private FieldSet fieldSet;
	private Button clean;

	private Button clearAcqDate;
	private Button clearDataLayer;
	private Button clearCloudCover;
	private Button clearLegacyId;
	private Button clearSourceId;

	private TimePicker timePicker;
	private CheckBox checkTime;
	private CheckBox noFilter;
	

	/**
	 * 
	 */
	public FilterBindingWidget() {
		this.init();
	}

	private void init() {
		formPanel = createFormPanel();
		this.createButtons();
		// formBinding = new DGWATCHFilterFormBinding(formPanel, true);
		formData = new FormData("-20");
		/***********************************************/
		/*************** VERY IMPORTANT ****************/
		disableWidget();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.geosolutions.georepo.gui.client.widget.DGWATCHBindingWidget#
	 * createFormPanel()
	 */
	public FormPanel createFormPanel() {
		FormPanel fp = new FormPanel();
		fp.setFrame(true);
		fp.setHeaderVisible(false);
		fp.setAutoHeight(true);
		
		FlexTable checkTable = new FlexTable();

		checkTable.setCellSpacing(8);
		checkTable.setCellPadding(12);

		checkTable.setHTML(1, 0,
				"<div style='font-size: 12px; width: 90px'>Attribute Filter :</span>");
		
		noFilter = new CheckBox();
		noFilter.addValueChangeHandler(new ValueChangeHandler<Boolean>(){

			public void onValueChange(ValueChangeEvent<Boolean> event) {
				
				boolean state = event.getValue();

				if(state){
					enableWidget();
				}else{
					disableWidget();
				}
				
			}			
		});

		checkTable.setWidget(1, 1, noFilter);
		fp.add(checkTable);

		fieldSet = new FieldSet();
		fieldSet.setHeading(I18nProvider.getMessages().aoiFilterFieldSetLabel());
		fieldSet.setCheckboxToggle(false);
		fieldSet.setCollapsible(false);

		FormLayout layout = new FormLayout();
		fieldSet.setLayout(layout);

		initWidget();

		fp.add(fieldSet);

		return fp;
	}

	private void createButtons() {
		formPanel.setButtonAlign(HorizontalAlignment.CENTER);

		clean = new Button("Clean",
				new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				Dispatcher.forwardEvent(DGWATCHEvents.UNBIND_FILTER_WIDGET);
			}
		});

		clean.setWidth(110);
		clean.setIconStyle("x-dgwatch-filter");

		formPanel.addButton(clean);
		
	}

	@SuppressWarnings("deprecation")
	private void initWidget() {
		
		this.storeAcqDate = new ListStore<FilterType>();
		this.storeAcqDate.add(DGWATCHData.getValFilterOp());

		FlexTable tableAcqDate = new FlexTable();

		tableAcqDate.setCellSpacing(8);
		tableAcqDate.setCellPadding(12);

		tableAcqDate.setHTML(1, 0,
				"<div style='font-size: 12px; width: 90px'>Acq Date :</span>");

		acqDateCombo = new ComboBox<FilterType>();
		acqDateCombo.setId(FilterKeyValue.FILTER_ACQ_DATE.getValue());
		acqDateCombo.setName(FilterKeyValue.FILTER_ACQ_DATE.getValue());
		acqDateCombo.setEmptyText("Select Filter ...");
		acqDateCombo.setDisplayField(FilterTypeEnum.TYPE.getValue());
		acqDateCombo.setEditable(true);
		acqDateCombo.setStore(storeAcqDate);
		acqDateCombo.setTypeAhead(true);
		acqDateCombo.setTriggerAction(TriggerAction.ALL);
		acqDateCombo.setWidth(200);

		clearAcqDate = new Button("", new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				acqDateCombo.reset();
				acqDate.reset();
			}
		});

		clearAcqDate.setToolTip("Clear Acq Date Filter&Value");
		clearAcqDate.setWidth(20);

		clearAcqDate.setIconStyle("x-dgwatch-clear");

		tableAcqDate.setWidget(1, 1, this.acqDateCombo);

		tableAcqDate.setWidget(1, 2, clearAcqDate);

		fieldSet.add(tableAcqDate);

		acqDate = new DateField();
		acqDate.getPropertyEditor().setFormat(
				DateTimeFormat.getLongDateFormat());
		acqDate.setId(FilterKeyValue.ACQ_DATE.getValue());
		acqDate.setName(FilterKeyValue.ACQ_DATE.getValue());
		acqDate.setLabelSeparator("");
		acqDate.setWidth(100);
		acqDate.setEditable(true);

		fieldSet.add(acqDate, formData);

		FlexTable tableTimerPicker = new FlexTable();

		tableTimerPicker.setCellSpacing(8);
		tableTimerPicker.setCellPadding(12);

		tableTimerPicker.setHTML(1, 0,
				"<div style='font-size: 12px; width: 90px'></span>");

		this.timePicker = new TimePicker(new Date(), null,
				DateTimeFormat.getFormat("HH"), DateTimeFormat.getFormat("mm"),
				DateTimeFormat.getFormat("ss"));

		tableTimerPicker.setWidget(1, 1, this.timePicker);

		checkTime = new CheckBox();
		this.timePicker.setEnabled(checkTime.isChecked());
		checkTime.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				timePicker.setEnabled(checkTime.isChecked());
			}
		});

		tableTimerPicker.setWidget(2, 1, checkTime);

		fieldSet.add(tableTimerPicker);

		this.storeDataLayer = new ListStore<FilterType>();
		this.storeDataLayer.add(DGWATCHData.getTextFilterOp());

		FlexTable tableDataLayer = new FlexTable();

		tableDataLayer.setCellSpacing(8);
		tableDataLayer.setCellPadding(12);

		tableDataLayer
				.setHTML(1, 0,
						"<div style='font-size: 12px; width: 90px'>Data Layer :</span>");

		dataLayerCombo = new ComboBox<FilterType>();
		dataLayerCombo.setId(FilterKeyValue.FILTER_DATA_LAYER.getValue());
		dataLayerCombo.setName(FilterKeyValue.FILTER_DATA_LAYER.getValue());
		dataLayerCombo.setEmptyText("Select Filter ...");
		dataLayerCombo.setDisplayField(FilterTypeEnum.TYPE.getValue());
		dataLayerCombo.setEditable(true);
		dataLayerCombo.setStore(storeDataLayer);
		dataLayerCombo.setTypeAhead(true);
		dataLayerCombo.setTriggerAction(TriggerAction.ALL);
		dataLayerCombo.setWidth(200);

		clearDataLayer = new Button("", new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				dataLayerCombo.reset();
				dataLayer.reset();
			}
		});

		clearDataLayer.setToolTip("Clear Data Layer Filter&Value");
		clearDataLayer.setWidth(20);

		clearDataLayer.setIconStyle("x-dgwatch-clear");

		tableDataLayer.setWidget(1, 1, this.dataLayerCombo);

		tableDataLayer.setWidget(1, 2, clearDataLayer);

		fieldSet.add(tableDataLayer);

		dataLayer = new TextField<String>();
		dataLayer.setId(FilterKeyValue.DATA_LAYER.getValue());
		dataLayer.setName(FilterKeyValue.DATA_LAYER.getValue());
		dataLayer.setLabelSeparator("");
		dataLayer.setWidth(100);

		fieldSet.add(dataLayer, formData);

		this.storeCloudCover = new ListStore<FilterType>();
		this.storeCloudCover.add(DGWATCHData.getValFilterOp());

		FlexTable tableCloudCover = new FlexTable();

		tableCloudCover.setCellSpacing(8);
		tableCloudCover.setCellPadding(12);

		tableCloudCover
				.setHTML(1, 0,
						"<div style='font-size: 12px; width: 90px'>Cloud Cover :</span>");

		cloudCoverCombo = new ComboBox<FilterType>();
		cloudCoverCombo.setId(FilterKeyValue.FILTER_CLOUD_COVER.getValue());
		cloudCoverCombo.setName(FilterKeyValue.FILTER_CLOUD_COVER.getValue());
		cloudCoverCombo.setEmptyText("Select Filter ...");
		cloudCoverCombo.setDisplayField(FilterTypeEnum.TYPE.getValue());
		cloudCoverCombo.setEditable(true);
		cloudCoverCombo.setStore(storeCloudCover);
		cloudCoverCombo.setTypeAhead(true);
		cloudCoverCombo.setTriggerAction(TriggerAction.ALL);
		cloudCoverCombo.setWidth(200);

		clearCloudCover = new Button("", new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				cloudCoverCombo.reset();
				cloudCover.reset();
			}
		});

		clearCloudCover.setToolTip("Clear Cloud Cover Filter&Value");
		clearCloudCover.setWidth(20);

		clearCloudCover.setIconStyle("x-dgwatch-clear");

		tableCloudCover.setWidget(1, 1, this.cloudCoverCombo);

		tableCloudCover.setWidget(1, 2, clearCloudCover);

		fieldSet.add(tableCloudCover);

		cloudCover = new NumberField();
		cloudCover.setId(FilterKeyValue.CLOUD_COVER.getValue());
		cloudCover.setName(FilterKeyValue.CLOUD_COVER.getValue());
		cloudCover.setLabelSeparator("");
		cloudCover.setWidth(100);

		fieldSet.add(cloudCover, formData);

		this.storeLegacyId = new ListStore<FilterType>();
		this.storeLegacyId.add(DGWATCHData.getTextFilterOp());

		FlexTable tableLegacyId = new FlexTable();

		tableLegacyId.setCellSpacing(8);
		tableLegacyId.setCellPadding(12);

		tableLegacyId.setHTML(1, 0,
				"<div style='font-size: 12px; width: 90px'>Legacy Id :</span>");

		legacyIdCombo = new ComboBox<FilterType>();
		legacyIdCombo.setId(FilterKeyValue.FILTER_LEGACY.getValue());
		legacyIdCombo.setName(FilterKeyValue.FILTER_LEGACY.getValue());
		legacyIdCombo.setEmptyText("Select Filter ...");
		legacyIdCombo.setDisplayField(FilterTypeEnum.TYPE.getValue());
		legacyIdCombo.setEditable(true);
		legacyIdCombo.setStore(storeLegacyId);
		legacyIdCombo.setTypeAhead(true);
		legacyIdCombo.setTriggerAction(TriggerAction.ALL);
		legacyIdCombo.setWidth(200);

		clearLegacyId = new Button("", new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				legacyIdCombo.reset();
				legacyId.reset();
			}
		});

		clearLegacyId.setToolTip("Clear Legacy Id Filter&Value");
		clearLegacyId.setWidth(20);

		clearLegacyId.setIconStyle("x-dgwatch-clear");

		tableLegacyId.setWidget(1, 1, this.legacyIdCombo);

		tableLegacyId.setWidget(1, 2, clearLegacyId);

		fieldSet.add(tableLegacyId);

		legacyId = new TextField<String>();
		legacyId.setId(FilterKeyValue.LEGACY_ID.getValue());
		legacyId.setName(FilterKeyValue.LEGACY_ID.getValue());
		legacyId.setLabelSeparator("");

		fieldSet.add(legacyId, formData);
		
		this.storeSourceId = new ListStore<FilterType>();
		this.storeSourceId.add(DGWATCHData.getTextFilterOp());
		
		FlexTable tableSourceId = new FlexTable();

		tableSourceId.setCellSpacing(8);
		tableSourceId.setCellPadding(12);

		tableSourceId.setHTML(1, 0,
				"<div style='font-size: 12px; width: 90px'>Source Id :</span>");

		sourceIdCombo = new ComboBox<FilterType>();
		sourceIdCombo.setId(FilterKeyValue.FILTER_SOURCE.getValue());
		sourceIdCombo.setName(FilterKeyValue.FILTER_SOURCE.getValue());
		sourceIdCombo.setEmptyText("Select Filter ...");
		sourceIdCombo.setDisplayField(FilterTypeEnum.TYPE.getValue());
		sourceIdCombo.setEditable(true);
		sourceIdCombo.setStore(storeSourceId);
		sourceIdCombo.setTypeAhead(true);
		sourceIdCombo.setTriggerAction(TriggerAction.ALL);
		sourceIdCombo.setWidth(200);

		clearSourceId = new Button("", new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				sourceIdCombo.reset();
				sourceId.reset();
			}
		});

		clearSourceId.setToolTip("Clear Source Id Filter&Value");
		clearSourceId.setWidth(20);

		clearSourceId.setIconStyle("x-dgwatch-clear");

		tableSourceId.setWidget(1, 1, this.sourceIdCombo);

		tableSourceId.setWidget(1, 2, clearSourceId);

		fieldSet.add(tableSourceId);
		
		sourceId = new TextField<String>();
		sourceId.setId(FilterKeyValue.SOURCE_ID.getValue());
		sourceId.setName(FilterKeyValue.SOURCE_ID.getValue());
		sourceId.setLabelSeparator("");

		fieldSet.add(sourceId, formData);

	}

	public boolean checkValidation() {
		return (((this.acqDate.getValue() != null) && (this.acqDateCombo
				.getValue() != null))
				|| ((this.dataLayer.getValue() != null) && (this.dataLayerCombo
						.getValue() != null))
				|| ((this.cloudCover != null) && (this.cloudCoverCombo
						.getValue() != null)) || ((this.legacyId.getValue() != null) && (this.legacyIdCombo
				.getValue() != null)) || ((this.sourceId.getValue() != null) && (this.sourceIdCombo
						.getValue() != null)));
	}
	
	/**
	 * @return boolean
	 */
	public boolean noFilterAttibute(){
		return this.noFilter.getValue();
	}

	/**
	 * Enable All Field in the Form
	 */
	public void enableWidget() {
		this.acqDateCombo.enable();
		this.acqDate.enable();
		this.dataLayerCombo.enable();
		this.dataLayer.enable();
		this.cloudCoverCombo.enable();
		this.cloudCover.enable();
		this.legacyIdCombo.enable();
		this.legacyId.enable();
		this.clearAcqDate.enable();
		this.clearDataLayer.enable();
		this.clearCloudCover.enable();
		this.clearLegacyId.enable();
		this.checkTime.setEnabled(true);
		
		this.sourceId.enable();
		this.sourceIdCombo.enable();
		this.clearSourceId.enable();
	}

	/**
	 * Disable All Field in the Form
	 */
	@SuppressWarnings("deprecation")
	public void disableWidget() {
		this.acqDateCombo.disable();
		this.acqDate.disable();
		this.dataLayerCombo.disable();
		this.dataLayer.disable();
		this.cloudCoverCombo.disable();
		this.cloudCover.disable();
		this.legacyIdCombo.disable();
		this.legacyId.disable();
		this.clearAcqDate.disable();
		this.clearDataLayer.disable();
		this.clearCloudCover.disable();
		this.clearLegacyId.disable();
		if (this.checkTime.isChecked()) {
			this.checkTime.setChecked(false);
			timePicker.setEnabled(checkTime.isChecked());
		}
		this.checkTime.setEnabled(false);
		
		this.sourceId.disable();
		this.sourceIdCombo.disable();
		this.clearSourceId.disable();
	}

	public void bindModel(Filter filter) {
		this.filter = filter;

		boolean filterCheck = false;
		
		if(this.filter != null){
			
			if (filter.getFilterAcqDate() != null){
				this.acqDateCombo.setValue(new FilterType(filter.getFilterAcqDate()));
				filterCheck = true;
			}
			
			if (filter.getAcqDate() != null) {
				this.acqDate.setValue(filter.getAcqDate());
				this.timePicker.setDateTime(filter.getAcqDate());
				filterCheck = true;
			}

			if (filter.getFilterDataLayer() != null){
				this.dataLayerCombo.setValue(new FilterType(filter.getFilterDataLayer()));
				filterCheck = true;
			}

			if (filter.getDataLayer() != null){
				this.dataLayer.setValue(filter.getDataLayer());
				filterCheck = true;
			}

			if (filter.getFilterCloudCover() != null){
				this.cloudCoverCombo.setValue(new FilterType(filter.getFilterCloudCover()));
				filterCheck = true;
			}

			if(filter.getCloudCover() != null){
				this.cloudCover.setValue(filter.getCloudCover());
				filterCheck = true;
			}		

			if (filter.getFilterLegacy() != null){
				this.legacyIdCombo.setValue(new FilterType(filter.getFilterLegacy()));
				filterCheck = true;
			}

			if (filter.getLegacyId() != null){
				this.legacyId.setValue(filter.getLegacyId());
				filterCheck = true;
			}		
			
			if (filter.getFilterSource() != null){
				this.sourceIdCombo.setValue(new FilterType(filter.getFilterSource()));
				filterCheck = true;
			}
			
			if (filter.getSourceId() != null){
				this.sourceId.setValue(filter.getSourceId());
				filterCheck = true;
			}	
		}
		
		this.noFilter.setValue(filterCheck, true);

	}

	public void unBindModel() {
		if (this.filter != null) {
			this.injectFilterNullValues();
			this.filter = null;
		}

		this.noFilter.setValue(false, true);		

	}

	private void injectFilterNullValues() {
		this.filter.setAcqDate(null);
		this.acqDateCombo.reset();
		this.acqDate.setValue(null);
		this.filter.setFilterAcqDate(null);
		this.dataLayerCombo.reset();
		this.dataLayer.setValue(null);
		this.filter.setDataLayer(null);
		this.filter.setFilterDataLayer(null);
		this.filter.setCloudCover(null);
		this.cloudCover.setValue(null);
		this.cloudCoverCombo.reset();
		this.filter.setFilterCloudCover(null);
		this.filter.setLegacyId(null);
		this.legacyId.reset();
		this.legacyIdCombo.reset();
		this.filter.setFilterLegacy(null);
		
		this.sourceId.reset();
		this.sourceIdCombo.reset();
		this.filter.setFilterSource(null);

	}

	@SuppressWarnings("deprecation")
	public Filter injectFilterWidgetValues() {
		this.filter = new Filter();
		
		if (this.acqDate.getValue() != null) {
			Date date = this.acqDate.getValue();
			if (this.checkTime.isChecked()) {
				date.setHours(this.timePicker.getDateTime().getHours());
				date.setMinutes(this.timePicker.getDateTime().getMinutes());
				date.setSeconds(this.timePicker.getDateTime().getSeconds());
			}

			this.filter.setAcqDate(date);
		}

		if (this.acqDateCombo.getValue() != null)
			this.filter
					.setFilterAcqDate(this.acqDateCombo.getValue().getType());

		this.filter.setDataLayer(this.dataLayer.getValue());

		if (this.dataLayerCombo.getValue() != null)
			this.filter.setFilterDataLayer(this.dataLayerCombo.getValue()
					.getType());

		if (this.cloudCover.getValue() != null)
			this.filter.setCloudCover(this.cloudCover.getValue().floatValue());

		if (this.cloudCoverCombo.getValue() != null)
			this.filter.setFilterCloudCover(this.cloudCoverCombo.getValue()
					.getType());

		this.filter.setLegacyId(this.legacyId.getValue());

		if (this.legacyIdCombo.getValue() != null)
			this.filter
					.setFilterLegacy(this.legacyIdCombo.getValue().getType());
		
		this.filter.setSourceId(this.sourceId.getValue());

		if (this.sourceIdCombo.getValue() != null)
			this.filter
					.setFilterSource(this.sourceIdCombo.getValue().getType());
		
		return this.filter;

	}

	public Filter getModel() {
		return this.filter;
	}

	public FormPanel getFormPanel() {
		return this.formPanel;
	}
	
}
