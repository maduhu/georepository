/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.SearchAOIWidget,v. 0.1 14-gen-2011 19.28.37 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 14-gen-2011 19.28.37 $
 *
 * ====================================================================
 *
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 * http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
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

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchAOIWidget.
 */
public class SearchAOIWidget {

    /** The form panel. */
    private FormPanel formPanel;

    /** The form data. */
    private FormData formData;

    /**
     * Instantiates a new search aoi widget.
     */
    public SearchAOIWidget() {
        this.init();
    }

    /**
     * Inits the.
     */
    private void init() {
        formData = new FormData("-20");
        formPanel = new FormPanel();
        formPanel.setFrame(true);
        formPanel.setHeaderVisible(false);
        formPanel.setAutoHeight(true);

        FieldSet fieldSet = new FieldSet();
        fieldSet.setHeading("AOI Search");
        fieldSet.setCheckboxToggle(false);
        fieldSet.setCollapsible(false);

        FormLayout layout = new FormLayout();
        fieldSet.setLayout(layout);

        TextField<String> searchField = new TextField<String>();

        searchField.setFieldLabel("Search AOI");
        searchField.setWidth(150);
        fieldSet.add(searchField, formData);

        formPanel.add(fieldSet);

        formPanel.setButtonAlign(HorizontalAlignment.CENTER);
        Button searchButton = new Button("Search");

        Button showFeatures = new Button("Show Features");

        formPanel.addButton(searchButton);

        formPanel.addButton(showFeatures);

    }

    /**
     * Gets the form panel.
     * 
     * @return the form panel
     */
    public FormPanel getFormPanel() {
        return formPanel;
    }

}
