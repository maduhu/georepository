/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.AOIBindingWidget,v. 0.1 3-gen-2011 16.52.56 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.Resources;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.AOI;
import it.geosolutions.georepo.gui.client.model.AOI.AOIKeyValue;

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

// TODO: Auto-generated Javadoc
/**
 * The Class AOIBindingWidget.
 */
public class AOIBindingWidget extends GeoRepoBindingWidget<AOI> {

    /** The title. */
    private TextField<String> title;

    /** The date creation. */
    private DateField dateCreation;

    /** The last update. */
    private DateField lastUpdate;

    /** The expiration. */
    private DateField expiration;

    /** The area. */
    private TextField<String> area;

    /** The add aoi. */
    private Button addAOI;

    /** The update aoi. */
    private Button updateAOI;

    /** The delete aoi. */
    private Button deleteAOI;

    /** The search aoi. */
    private Button searchAOI;

    /** The aoi clean. */
    private Button aoiClean;

    /** The form data. */
    private FormData formData;

    /** The selected. */
    private boolean selected;

    /**
     * Instantiates a new aOI binding widget.
     */
    public AOIBindingWidget() {
        this.init();
    }

    /**
     * Inits the.
     */
    private void init() {
        formPanel = createFormPanel();
        formBinding = new FormBinding(formPanel, true);
        formData = new FormData("-20");

    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.BindingWidget#createFormPanel ()
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

        title = new TextField<String>();
        title.setId(AOIKeyValue.TITLE.getValue());
        title.setName(AOIKeyValue.TITLE.getValue());
        title.setFieldLabel(I18nProvider.getMessages().aoiTitleLabel());
        title.setWidth(100);
        fieldSet.add(title, formData);

        dateCreation = new DateField();
        dateCreation.setId(AOIKeyValue.DATE_CREATION.getValue());
        dateCreation.setName(AOIKeyValue.DATE_CREATION.getValue());
        dateCreation.setFieldLabel(I18nProvider.getMessages().aoiDateCreation());
        dateCreation.setWidth(100);
        dateCreation.setHideTrigger(true);
        dateCreation.setEditable(false);
        dateCreation.setEnabled(false);
        fieldSet.add(dateCreation, formData);

        lastUpdate = new DateField();
        lastUpdate.setId(AOIKeyValue.LAST_UPDATE.getValue());
        lastUpdate.setName(AOIKeyValue.LAST_UPDATE.getValue());
        lastUpdate.setFieldLabel(I18nProvider.getMessages().aoiLastUpdate());
        lastUpdate.setWidth(100);
        lastUpdate.setHideTrigger(true);
        lastUpdate.setEditable(false);
        lastUpdate.setEnabled(false);
        fieldSet.add(lastUpdate, formData);

        expiration = new DateField();
        expiration.setId(AOIKeyValue.EXPIRATION.getValue());
        expiration.setName(AOIKeyValue.EXPIRATION.getValue());
        expiration.setFieldLabel(I18nProvider.getMessages().aoiExpiration());
        expiration.setWidth(100);
        fieldSet.add(expiration, formData);

        area = new TextField<String>();
        area.setId(AOIKeyValue.AREA.getValue());
        area.setName(AOIKeyValue.AREA.getValue());
        area.setFieldLabel(I18nProvider.getMessages().aoiArea());
        area.setWidth(100);
        area.setEnabled(false);
        fieldSet.add(area, formData);

        fp.add(fieldSet);

        FlexTable table = new FlexTable();

        table.setCellSpacing(8);
        table.setCellPadding(4);

        addAOI = new Button(I18nProvider.getMessages().aoiNew(),
                new SelectionListener<ButtonEvent>() {

                    @Override
                    public void componentSelected(ButtonEvent ce) {
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "Add AOI", "New AOI button pressed." });
                        Dispatcher.forwardEvent(GeoRepoEvents.SHOW_ADD_AOI);
                    }
                });

        addAOI.setWidth(100);

        addAOI.setIcon(Resources.ICONS.addAOI());

        table.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_CENTER);

        table.setWidget(1, 1, this.addAOI);

        updateAOI = new Button(I18nProvider.getMessages().aoiUpdate(),
                new SelectionListener<ButtonEvent>() {

                    @Override
                    public void componentSelected(ButtonEvent ce) {

                        AOI aoi = getModel();
                        aoi = checkIfUpdate(aoi);

                        if (aoi != null) {
                            Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                    "Update AOI", "Update AOI button pressed." });
                            Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_AOI, aoi);
                        }
                    }
                });

        updateAOI.disable();

        updateAOI.setWidth(100);
        updateAOI.setIcon(Resources.ICONS.editAOI());

        table.getCellFormatter().setHorizontalAlignment(1, 2, HasHorizontalAlignment.ALIGN_CENTER);

        table.setWidget(1, 2, this.updateAOI);

        deleteAOI = new Button(I18nProvider.getMessages().aoiDelete(),
                new SelectionListener<ButtonEvent>() {

                    @Override
                    public void componentSelected(ButtonEvent ce) {
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "Delete AOI", "Delete AOI button pressed." });
                        MessageBox.confirm("Delete AOI", "Are you sure to delete AOI "
                                + getModel().getTitle() + " ?", new Listener<MessageBoxEvent>() {

                            public void handleEvent(MessageBoxEvent be) {
                                Button btn = be.getButtonClicked();
                                if (btn.getText().equalsIgnoreCase("YES")) {
                                    Dispatcher.forwardEvent(GeoRepoEvents.DELETE_AOI, getModel());
                                }
                            }
                        });
                    }
                });

        deleteAOI.disable();

        deleteAOI.setWidth(100);
        deleteAOI.setIcon(Resources.ICONS.deleteAOI());

        table.getCellFormatter().setHorizontalAlignment(1, 3, HasHorizontalAlignment.ALIGN_CENTER);

        table.setWidget(1, 3, this.deleteAOI);

        searchAOI = new Button(I18nProvider.getMessages().aoiSearch(),
                new SelectionListener<ButtonEvent>() {

                    @Override
                    public void componentSelected(ButtonEvent ce) {
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "Search AOI", "Search AOI button pressed." });
                        Dispatcher.forwardEvent(GeoRepoEvents.SEARCH_AOI);
                    }
                });

        searchAOI.setWidth(100);

        searchAOI.setIcon(Resources.ICONS.search());

        table.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_CENTER);

        table.setWidget(2, 1, this.searchAOI);

        aoiClean = new Button(I18nProvider.getMessages().aoiClean(),
                new SelectionListener<ButtonEvent>() {

                    @Override
                    public void componentSelected(ButtonEvent ce) {
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "Clean AOI", "Clean AOI button pressed." });
                        Dispatcher.forwardEvent(GeoRepoEvents.AOI_MANAGEMENT_UNBIND);
                    }
                });

        aoiClean.setIcon(Resources.ICONS.cleanDgWatchMenu());
        aoiClean.setWidth(100);
        aoiClean.disable();

        table.getCellFormatter().setHorizontalAlignment(2, 2, HasHorizontalAlignment.ALIGN_CENTER);

        table.setWidget(2, 2, this.aoiClean);

        fp.add(table);

        return fp;
    }

    /**
     * Enable buttons.
     */
    public void enableButtons() {
        this.deleteAOI.enable();
        this.updateAOI.enable();
        this.aoiClean.enable();
    }

    /**
     * Disable buttons.
     */
    public void disableButtons() {
        this.deleteAOI.disable();
        this.updateAOI.disable();
        this.aoiClean.disable();
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHBindingWidget#unBindModel ()
     */
    @Override
    public void unBindModel() {
        super.unBindModel();
        disableButtons();
    }

    /**
     * Checks if is selected.
     * 
     * @return true, if is selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selected.
     * 
     * @param selected
     *            the new selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Check if update.
     * 
     * @param aoi
     *            the aoi
     * @return the aOI
     */
    public AOI checkIfUpdate(AOI aoi) {
        boolean dirty = false;

        if (title.isDirty()) {
            aoi.setTitle(title.getValue());
            dirty = true;
        }

        if (expiration.isDirty()) {
            aoi.setExpiration(expiration.getValue());
            dirty = true;
        }

        if (dirty)
            return aoi;
        else
            return null;
    }

}
