/*
 * $Header: it.geosolutions.georepo.gui.client.widget.binding.DGWATCHFormBinding,v. 0.1 20/lug/2010 17.55.49 created by frank $
 * $Revision: 0.1 $
 * $Date: 20/lug/2010 17.55.49 $
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
package it.geosolutions.georepo.gui.client.widget.binding;


import it.geosolutions.georepo.gui.client.SendType.SendTypeEnum;
import it.geosolutions.georepo.gui.client.UpdateInterval.UpdateIntervalEnum;
import it.geosolutions.georepo.gui.client.model.BeanKeyValue;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;

/**
 * @author frank
 * 
 */
public class DGWATCHUserFormBinding extends FormBinding {

	public DGWATCHUserFormBinding(FormPanel panel, boolean autoBind) {
		super(panel, autoBind);
	}

	@Override
    @SuppressWarnings("rawtypes")
	public void autoBind() {
		for (Field f : panel.getFields()) {
			if (!bindings.containsKey(f.getId())) {
				String name = f.getName();
				if (name != null) {
					FieldBinding b;
					if (f.getId()
							.equalsIgnoreCase(SendTypeEnum.TYPE.getValue()))
						b = new ComboTypeFieldBinding(f, f.getName());
					else if (f.getId().equalsIgnoreCase(
							UpdateIntervalEnum.TIME.getValue()))
						b = new ComboTimeFieldBinding(f, f.getName());
					else if (f.getId().equals(
							BeanKeyValue.REDUCED_CONTENT_UPDATE.getValue()))
						b = new ReducedContentFieldBinding(f, f.getName());
					else
						b = new DGWATCHFieldBinding(f, f.getName());
					bindings.put(f.getId(), b);
				}
			}
		}
	}

}
