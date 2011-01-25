/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.GeoRepoUpdateWidget,v. 0.1 25-gen-2011 11.24.45 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.24.45 $
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

import it.geosolutions.georepo.gui.client.form.GeoRepoFormWidget;
import it.geosolutions.georepo.gui.client.widget.binding.GeoRepoUserFormBinding;

import com.extjs.gxt.ui.client.data.BaseModel;

// TODO: Auto-generated Javadoc
/**
 * The Class GeoRepoUpdateWidget.
 * 
 * @param <T>
 *            the generic type
 */
public abstract class GeoRepoUpdateWidget<T extends BaseModel> extends GeoRepoFormWidget {

    /** The object. */
    protected T object;

    /** The form binding. */
    protected GeoRepoUserFormBinding formBinding;

    /**
     * Instantiates a new geo repo update widget.
     */
    public GeoRepoUpdateWidget() {
        super();
        this.formBinding = new GeoRepoUserFormBinding(formPanel, true);
    }

    /**
     * Bind.
     * 
     * @param model
     *            the model
     */
    public void bind(T model) {
        this.object = model;
        this.formBinding.bind(this.object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.form.DGWATCHFormWidget#reset()
     */
    @Override
    public void reset() {
        this.saveStatus.clearStatus("");
    }

}
