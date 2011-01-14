/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.FilterInfo,v. 0.1 14-gen-2011 19.28.37 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.User;
import it.geosolutions.georepo.gui.client.model.Filter.FilterKeyValue;

import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.layout.FormData;

// TODO: Auto-generated Javadoc
/**
 * The Class FilterInfo.
 */
public class FilterInfo extends GeoRepoBindingWidget<User> {

    /** The aoi. */
    private LabelField aoi;

    /** The owner. */
    private LabelField owner;

    /**
     * Instantiates a new filter info.
     */
    public FilterInfo() {
        this.init();
    }

    /**
     * Inits the.
     */
    private void init() {
        formPanel = createFormPanel();
        formBinding = new FormBinding(formPanel, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHBindingWidget# createFormPanel()
     */
    @Override
    public FormPanel createFormPanel() {
        FormPanel fp = new FormPanel();
        fp.setFrame(true);
        fp.setHeaderVisible(false);
        fp.setAutoHeight(true);

        FormData formData = new FormData("-20");

        aoi = new LabelField();
        aoi.setId(FilterKeyValue.AOI.getValue());
        aoi.setFieldLabel(I18nProvider.getMessages().aoiAbbreviatedLabel());
        aoi.setWidth(150);
        fp.add(aoi, formData);

        owner = new LabelField();
        owner.setId(FilterKeyValue.OWNER.getValue());
        // owner.setFieldLabel(I18nProvider.getMessages().aoiOwnerLabel());
        owner.setWidth(150);
        fp.add(owner, formData);

        return fp;
    }

}
