/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.FilterBindingWidget,v. 0.1 3-gen-2011 16.52.56 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.georepo.gui.client.GeoRepoWidgetsData;
import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.FilterType;
import it.geosolutions.georepo.gui.client.FilterType.FilterTypeEnum;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.Filter;
import it.geosolutions.georepo.gui.client.model.Filter.FilterKeyValue;

import java.util.Date;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
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

// TODO: Auto-generated Javadoc
/**
 * The Class FilterBindingWidget.
 */
public class FilterBindingWidget {

    /** The acq date. */
    private DateField acqDate;

    /** The data layer. */
    private TextField<String> dataLayer;

    /** The legacy id. */
    private TextField<String> legacyId;

    /** The source id. */
    private TextField<String> sourceId;

    /** The cloud cover. */
    private NumberField cloudCover;

    /** The acq date combo. */
    private ComboBox<FilterType> acqDateCombo;

    /** The data layer combo. */
    private ComboBox<FilterType> dataLayerCombo;

    /** The cloud cover combo. */
    private ComboBox<FilterType> cloudCoverCombo;

    /** The legacy id combo. */
    private ComboBox<FilterType> legacyIdCombo;

    /** The source id combo. */
    private ComboBox<FilterType> sourceIdCombo;

    /** The store acq date. */
    private ListStore<FilterType> storeAcqDate;

    /** The store data layer. */
    private ListStore<FilterType> storeDataLayer;

    /** The store cloud cover. */
    private ListStore<FilterType> storeCloudCover;

    /** The store legacy id. */
    private ListStore<FilterType> storeLegacyId;

    /** The store source id. */
    private ListStore<FilterType> storeSourceId;

    /** The form data. */
    private FormData formData;

    /** The form panel. */
    private FormPanel formPanel;

    /** The filter. */
    private Filter filter;

    /** The field set. */
    private FieldSet fieldSet;

    /** The clean. */
    private Button clean;

    /** The clear acq date. */
    private Button clearAcqDate;

    /** The clear data layer. */
    private Button clearDataLayer;

    /** The clear cloud cover. */
    private Button clearCloudCover;

    /** The clear legacy id. */
    private Button clearLegacyId;

    /** The clear source id. */
    private Button clearSourceId;

    /** The time picker. */
    private TimePicker timePicker;

    /** The check time. */
    private CheckBox checkTime;

    /** The no filter. */
    private CheckBox noFilter;

    /**
     * Instantiates a new filter binding widget.
     */
    public FilterBindingWidget() {
        this.init();
    }

    /**
     * Inits the.
     */
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
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHBindingWidget# createFormPanel()
     */
    /**
     * Creates the form panel.
     * 
     * @return the form panel
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
        noFilter.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

            public void onValueChange(ValueChangeEvent<Boolean> event) {

                boolean state = event.getValue();

                if (state) {
                    enableWidget();
                } else {
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

    /**
     * Creates the buttons.
     */
    private void createButtons() {
        formPanel.setButtonAlign(HorizontalAlignment.CENTER);

        clean = new Button("Clean", new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                Dispatcher.forwardEvent(GeoRepoEvents.UNBIND_FILTER_WIDGET);
            }
        });

        clean.setWidth(110);
        clean.setIconStyle("x-georepo-filter");

        formPanel.addButton(clean);

    }

    /**
     * Inits the widget.
     */
    @SuppressWarnings("deprecation")
    private void initWidget() {

        this.storeAcqDate = new ListStore<FilterType>();
        this.storeAcqDate.add(GeoRepoWidgetsData.getValFilterOp());

        FlexTable tableAcqDate = new FlexTable();

        tableAcqDate.setCellSpacing(8);
        tableAcqDate.setCellPadding(12);

        tableAcqDate.setHTML(1, 0, "<div style='font-size: 12px; width: 90px'>Acq Date :</span>");

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

        clearAcqDate.setIconStyle("x-georepo-clear");

        tableAcqDate.setWidget(1, 1, this.acqDateCombo);

        tableAcqDate.setWidget(1, 2, clearAcqDate);

        fieldSet.add(tableAcqDate);

        acqDate = new DateField();
        acqDate.getPropertyEditor().setFormat(DateTimeFormat.getLongDateFormat());
        acqDate.setId(FilterKeyValue.ACQ_DATE.getValue());
        acqDate.setName(FilterKeyValue.ACQ_DATE.getValue());
        acqDate.setLabelSeparator("");
        acqDate.setWidth(100);
        acqDate.setEditable(true);

        fieldSet.add(acqDate, formData);

        FlexTable tableTimerPicker = new FlexTable();

        tableTimerPicker.setCellSpacing(8);
        tableTimerPicker.setCellPadding(12);

        tableTimerPicker.setHTML(1, 0, "<div style='font-size: 12px; width: 90px'></span>");

        this.timePicker = new TimePicker(new Date(), null, DateTimeFormat.getFormat("HH"),
                DateTimeFormat.getFormat("mm"), DateTimeFormat.getFormat("ss"));

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
        this.storeDataLayer.add(GeoRepoWidgetsData.getTextFilterOp());

        FlexTable tableDataLayer = new FlexTable();

        tableDataLayer.setCellSpacing(8);
        tableDataLayer.setCellPadding(12);

        tableDataLayer.setHTML(1, 0,
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

        clearDataLayer.setIconStyle("x-georepo-clear");

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
        this.storeCloudCover.add(GeoRepoWidgetsData.getValFilterOp());

        FlexTable tableCloudCover = new FlexTable();

        tableCloudCover.setCellSpacing(8);
        tableCloudCover.setCellPadding(12);

        tableCloudCover.setHTML(1, 0,
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

        clearCloudCover.setIconStyle("x-georepo-clear");

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
        this.storeLegacyId.add(GeoRepoWidgetsData.getTextFilterOp());

        FlexTable tableLegacyId = new FlexTable();

        tableLegacyId.setCellSpacing(8);
        tableLegacyId.setCellPadding(12);

        tableLegacyId.setHTML(1, 0, "<div style='font-size: 12px; width: 90px'>Legacy Id :</span>");

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

        clearLegacyId.setIconStyle("x-georepo-clear");

        tableLegacyId.setWidget(1, 1, this.legacyIdCombo);

        tableLegacyId.setWidget(1, 2, clearLegacyId);

        fieldSet.add(tableLegacyId);

        legacyId = new TextField<String>();
        legacyId.setId(FilterKeyValue.LEGACY_ID.getValue());
        legacyId.setName(FilterKeyValue.LEGACY_ID.getValue());
        legacyId.setLabelSeparator("");

        fieldSet.add(legacyId, formData);

        this.storeSourceId = new ListStore<FilterType>();
        this.storeSourceId.add(GeoRepoWidgetsData.getTextFilterOp());

        FlexTable tableSourceId = new FlexTable();

        tableSourceId.setCellSpacing(8);
        tableSourceId.setCellPadding(12);

        tableSourceId.setHTML(1, 0, "<div style='font-size: 12px; width: 90px'>Source Id :</span>");

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

        clearSourceId.setIconStyle("x-georepo-clear");

        tableSourceId.setWidget(1, 1, this.sourceIdCombo);

        tableSourceId.setWidget(1, 2, clearSourceId);

        fieldSet.add(tableSourceId);

        sourceId = new TextField<String>();
        sourceId.setId(FilterKeyValue.SOURCE_ID.getValue());
        sourceId.setName(FilterKeyValue.SOURCE_ID.getValue());
        sourceId.setLabelSeparator("");

        fieldSet.add(sourceId, formData);

    }

    /**
     * Check validation.
     * 
     * @return true, if successful
     */
    public boolean checkValidation() {
        return (((this.acqDate.getValue() != null) && (this.acqDateCombo.getValue() != null))
                || ((this.dataLayer.getValue() != null) && (this.dataLayerCombo.getValue() != null))
                || ((this.cloudCover != null) && (this.cloudCoverCombo.getValue() != null))
                || ((this.legacyId.getValue() != null) && (this.legacyIdCombo.getValue() != null)) || ((this.sourceId
                .getValue() != null) && (this.sourceIdCombo.getValue() != null)));
    }

    /**
     * No filter attibute.
     * 
     * @return true, if successful
     */
    public boolean noFilterAttibute() {
        return this.noFilter.getValue();
    }

    /**
     * Enable widget.
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
     * Disable widget.
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

    /**
     * Bind model.
     * 
     * @param filter
     *            the filter
     */
    public void bindModel(Filter filter) {
        this.filter = filter;

        boolean filterCheck = false;

        if (this.filter != null) {

            if (filter.getFilterAcqDate() != null) {
                this.acqDateCombo.setValue(new FilterType(filter.getFilterAcqDate()));
                filterCheck = true;
            }

            if (filter.getAcqDate() != null) {
                this.acqDate.setValue(filter.getAcqDate());
                this.timePicker.setDateTime(filter.getAcqDate());
                filterCheck = true;
            }

            if (filter.getFilterDataLayer() != null) {
                this.dataLayerCombo.setValue(new FilterType(filter.getFilterDataLayer()));
                filterCheck = true;
            }

            if (filter.getDataLayer() != null) {
                this.dataLayer.setValue(filter.getDataLayer());
                filterCheck = true;
            }

            if (filter.getFilterCloudCover() != null) {
                this.cloudCoverCombo.setValue(new FilterType(filter.getFilterCloudCover()));
                filterCheck = true;
            }

            if (filter.getCloudCover() != null) {
                this.cloudCover.setValue(filter.getCloudCover());
                filterCheck = true;
            }

            if (filter.getFilterLegacy() != null) {
                this.legacyIdCombo.setValue(new FilterType(filter.getFilterLegacy()));
                filterCheck = true;
            }

            if (filter.getLegacyId() != null) {
                this.legacyId.setValue(filter.getLegacyId());
                filterCheck = true;
            }

            if (filter.getFilterSource() != null) {
                this.sourceIdCombo.setValue(new FilterType(filter.getFilterSource()));
                filterCheck = true;
            }

            if (filter.getSourceId() != null) {
                this.sourceId.setValue(filter.getSourceId());
                filterCheck = true;
            }
        }

        this.noFilter.setValue(filterCheck, true);

    }

    /**
     * Un bind model.
     */
    public void unBindModel() {
        if (this.filter != null) {
            this.injectFilterNullValues();
            this.filter = null;
        }

        this.noFilter.setValue(false, true);

    }

    /**
     * Inject filter null values.
     */
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

    /**
     * Inject filter widget values.
     * 
     * @return the filter
     */
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
            this.filter.setFilterAcqDate(this.acqDateCombo.getValue().getType());

        this.filter.setDataLayer(this.dataLayer.getValue());

        if (this.dataLayerCombo.getValue() != null)
            this.filter.setFilterDataLayer(this.dataLayerCombo.getValue().getType());

        if (this.cloudCover.getValue() != null)
            this.filter.setCloudCover(this.cloudCover.getValue().floatValue());

        if (this.cloudCoverCombo.getValue() != null)
            this.filter.setFilterCloudCover(this.cloudCoverCombo.getValue().getType());

        this.filter.setLegacyId(this.legacyId.getValue());

        if (this.legacyIdCombo.getValue() != null)
            this.filter.setFilterLegacy(this.legacyIdCombo.getValue().getType());

        this.filter.setSourceId(this.sourceId.getValue());

        if (this.sourceIdCombo.getValue() != null)
            this.filter.setFilterSource(this.sourceIdCombo.getValue().getType());

        return this.filter;

    }

    /**
     * Gets the model.
     * 
     * @return the model
     */
    public Filter getModel() {
        return this.filter;
    }

    /**
     * Gets the form panel.
     * 
     * @return the form panel
     */
    public FormPanel getFormPanel() {
        return this.formPanel;
    }

}
