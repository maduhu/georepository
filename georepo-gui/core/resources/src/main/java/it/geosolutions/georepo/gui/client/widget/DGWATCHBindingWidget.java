/*
 * $Header: it.geosolutions.georepo.gui.client.widget.BindingWidget,v. 0.1 19/lug/2010 15.49.48 created by frank $
 * $Revision: 0.1 $
 * $Date: 19/lug/2010 15.49.48 $
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

import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.widget.form.FormPanel;

/**
 * @author frank
 * 
 */

public abstract class DGWATCHBindingWidget<T extends BaseModel> {

	protected FormPanel formPanel;
	protected FormBinding formBinding;
	private T model;

	/**
	 * 
	 * @param model
	 *            T object to bind
	 */
	public void bindModel(T model) {
		this.model = model;
		this.formBinding.bind(model);
	}

	public void unBindModel() {
		this.formBinding.unbind();
	}

	/**
	 * @return the formBinding
	 */
	public FormBinding getFormBinding() {
		return formBinding;
	}

	/**
	 * @return the model
	 */
	public T getModel() {
		return model;
	}

	public abstract FormPanel createFormPanel();

	/**
	 * @return the formPanel
	 */
	public FormPanel getFormPanel() {
		return formPanel;
	}

}