/*
 * $Header: it.geosolutions.georepo.gui.client.widget.binding.ComboTypeFieldBinding,v. 0.1 03/ago/2010 10.13.18 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 03/ago/2010 10.13.18 $
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


import it.geosolutions.georepo.gui.client.SendType;
import it.geosolutions.georepo.gui.client.model.Watch;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;

/**
 * @author giuseppe
 * 
 */
public class ComboTypeFieldBinding extends FieldBinding {

	private Object oldValue;

	@SuppressWarnings("rawtypes")
	public ComboTypeFieldBinding(Field field, String property) {
		super(field, property);
	}

	@Override
    @SuppressWarnings("unchecked")
	public void updateField(boolean updateOriginalValue) {
		Object val = onConvertModelValue(model.get(property));

		if (oldValue == null)
			oldValue = val;

		((ComboBox<SendType>) field).setValue(new SendType(val.toString()));

		if (updateOriginalValue) {
			((ComboBox<SendType>) field).setOriginalValue(new SendType(val
					.toString()));
		}
	}

	@Override
    @SuppressWarnings("unchecked")
	public void updateModel() {
		Object val = onConvertFieldValue(field.getValue());
		if (store != null) {
			Record r = store.getRecord(model);
			if (r != null) {
				r.setValid(property, field.isValid());
				r.set(property, val);
			}
		} else {
			// model.set(property, ((SendType) val).getType());
			((Watch) model).setSendType(((SendType) val).getType());
		}
	}

	@SuppressWarnings("unchecked")
	public void resetValue() {
		oldValue = onConvertFieldValue(field.getValue());

		((ComboBox<SendType>) field)
				.setValue(new SendType(oldValue.toString()));

		// model.set(property, oldValue.toString());
		((Watch) model).setSendType(((SendType) oldValue).getType());
	}
}
