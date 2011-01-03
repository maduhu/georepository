/*
 * $Header: it.geosolutions.georepo.gui.client.widget.UpdateUserWidget,v. 0.1 02/ago/2010 14.44.57 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 02/ago/2010 14.44.57 $
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


import it.geosolutions.georepo.gui.client.form.DGWATCHFormWidget;
import it.geosolutions.georepo.gui.client.widget.binding.DGWATCHUserFormBinding;
import com.extjs.gxt.ui.client.data.BaseModel;

/**
 * @author giuseppe
 * 
 */
public abstract class DGWATCHUpdateWidget<T extends BaseModel> extends
		DGWATCHFormWidget {

	protected T object;
	protected DGWATCHUserFormBinding formBinding;

	public DGWATCHUpdateWidget() {
		super();
		this.formBinding = new DGWATCHUserFormBinding(formPanel, true);
	}

	/**
	 * Bind the generic Object in the Form
	 * 
	 * @param model
	 */
	public void bind(T model) {
		this.object = model;
		this.formBinding.bind(this.object);
	}

	@Override
    public void reset() {
		this.saveStatus.clearStatus("");
	}

}
