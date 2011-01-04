/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.DGWATCHFormBindingWidget,v. 0.1 3-gen-2011 17.06.10 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 17.06.10 $
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

import it.geosolutions.georepo.gui.client.model.Authorization;
import it.geosolutions.georepo.gui.client.widget.binding.DGWATCHWatchFormBinding;

import java.util.List;

import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.widget.form.FormPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class DGWATCHFormBindingWidget.
 * 
 * @param <T>
 *            the generic type
 */
public abstract class DGWATCHFormBindingWidget<T extends BaseModel> {

    /** The form panel. */
    protected FormPanel formPanel;

    /** The form binding. */
    protected DGWATCHWatchFormBinding formBinding;

    /** The model. */
    private T model;

    /**
     * Bind model.
     * 
     * @param model
     *            the model
     */
    public void bindModel(T model) {
        this.model = model;
        this.formBinding.bind(model);
    }

    /**
     * Un bind model.
     */
    public void unBindModel() {
        this.formBinding.unbind();
    }

    /**
     * Gets the form binding.
     * 
     * @return the form binding
     */
    public FormBinding getFormBinding() {
        return formBinding;
    }

    /**
     * Gets the model.
     * 
     * @return the model
     */
    public T getModel() {
        return model;
    }

    /**
     * Creates the form panel.
     * 
     * @return the form panel
     */
    public abstract FormPanel createFormPanel();

    /**
     * Gets the form panel.
     * 
     * @return the form panel
     */
    public FormPanel getFormPanel() {
        return formPanel;
    }

    /**
     * Inject security.
     * 
     * @param auths
     *            the auths
     */
    public void injectSecurity(List<Authorization> auths) {
        // do nothing for default implementation
    }

}
