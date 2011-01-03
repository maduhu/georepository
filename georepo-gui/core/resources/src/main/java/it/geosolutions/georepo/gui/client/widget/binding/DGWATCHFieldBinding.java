/*
 * $Header: it.geosolutions.georepo.gui.client.widget.binding.DGWATCHFieldBinding,v. 0.1 20/lug/2010 17.57.04 created by frank $
 * $Revision: 0.1 $
 * $Date: 20/lug/2010 17.57.04 $
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

import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.widget.form.Field;

/**
 * @author frank
 * 
 */
public class DGWATCHFieldBinding extends FieldBinding {

	private Object oldValue;

	@SuppressWarnings("rawtypes")
	public DGWATCHFieldBinding(Field field, String property) {
		super(field, property);
	}

	@Override
    @SuppressWarnings("unchecked")
	public void updateField(boolean updateOriginalValue) {
		Object val = onConvertModelValue(model.get(property));

		if (oldValue == null)
			oldValue = val;

		field.setValue(val);
		if (updateOriginalValue) {
			field.setOriginalValue(val);
		}
	}

	@SuppressWarnings("unchecked")
	public void resetValue() {
		oldValue = onConvertModelValue(oldValue);

		field.setValue(oldValue);

		model.set(property, oldValue.toString());
	}

}
