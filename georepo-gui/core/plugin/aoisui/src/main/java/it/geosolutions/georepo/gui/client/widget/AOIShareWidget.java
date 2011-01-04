/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.AOIShareWidget,v. 0.1 3-gen-2011 16.52.56 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.Resources;
import it.geosolutions.georepo.gui.client.model.AOI;
import it.geosolutions.georepo.gui.client.model.AOI.AOIKeyValue;

import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayoutData;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

// TODO: Auto-generated Javadoc
/**
 * The Class AOIShareWidget.
 */
public class AOIShareWidget extends DGWATCHBindingWidget<AOI> {

    /** The form data. */
    private FormData formData;

    /** The shared. */
    private CheckBox shared;

    /** The owner. */
    private LabelField owner;

    /** The share aoi. */
    private Button shareAOI;

    /** The unshare aoi. */
    private Button unshareAOI;

    /**
     * Instantiates a new aOI share widget.
     */
    public AOIShareWidget() {
        this.init();
    }

    /**
     * Inits the.
     */
    private void init() {
        formData = new FormData("-20");
        formPanel = createFormPanel();
        formBinding = new FormBinding(formPanel, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHBindingWidget#createFormPanel()
     */
    @Override
    public FormPanel createFormPanel() {
        FormPanel fp = new FormPanel();
        fp.setFrame(true);
        fp.setHeaderVisible(false);
        fp.setAutoHeight(true);
        fp.setWidth(428);

        FieldSet fieldSet = new FieldSet();
        fieldSet.setHeading("Share");
        fieldSet.setCheckboxToggle(false);
        fieldSet.setCollapsible(false);

        FormLayout layout = new FormLayout();
        fieldSet.setLayout(layout);

        shared = new CheckBox();
        shared.setId(AOIKeyValue.SHARED.getValue());
        shared.setName(AOIKeyValue.SHARED.getValue());
        shared.setFieldLabel("Shared");
        shared.setWidth(150);
        shared.setEnabled(false);

        fieldSet.add(shared, formData);

        owner = new LabelField();
        owner.setId(AOIKeyValue.OWNER.getValue());
        owner.setName(AOIKeyValue.OWNER.getValue());
        owner.setWidth(200);
        owner.setFieldLabel("Owner");

        fieldSet.add(owner, formData);

        fp.add(fieldSet);

        FlexTable table = new FlexTable();

        table.setCellSpacing(8);
        table.setCellPadding(4);

        shareAOI = new Button("Share", new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE, new String[] {
                        "Share AOI", "Share AOI Button pressed." });
                Dispatcher.forwardEvent(DGWATCHEvents.SHARE_AOI);
            }
        });

        shareAOI.setWidth(90);
        shareAOI.setIcon(Resources.ICONS.share());

        table.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_CENTER);

        table.setWidget(1, 1, this.shareAOI);

        unshareAOI = new Button("Unshare", new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE, new String[] {
                        "Unshare AOI", "Unshare AOI button pressed." });
                Dispatcher.forwardEvent(DGWATCHEvents.SHOW_CHOOSER_USER_WIDGET);

            }
        });

        unshareAOI.setWidth(100);
        unshareAOI.setIconStyle("x-dgwatch-unshare");

        table.getCellFormatter().setHorizontalAlignment(1, 2, HasHorizontalAlignment.ALIGN_CENTER);

        table.setWidget(1, 2, this.unshareAOI);

        shareAOI.disable();
        unshareAOI.disable();

        fp.add(table, new VBoxLayoutData(5, 0, 0, 90));

        return fp;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHBindingWidget#getFormPanel()
     */
    @Override
    public FormPanel getFormPanel() {
        return formPanel;
    }

    /**
     * Gets the share aoi.
     * 
     * @return the share aoi
     */
    public Button getShareAOI() {
        return shareAOI;
    }

    /**
     * Gets the unshare aoi.
     * 
     * @return the unshare aoi
     */
    public Button getUnshareAOI() {
        return unshareAOI;
    }

    /**
     * Disable buttons.
     */
    public void disableButtons() {
        this.shareAOI.disable();
        this.unshareAOI.disable();
    }

}
