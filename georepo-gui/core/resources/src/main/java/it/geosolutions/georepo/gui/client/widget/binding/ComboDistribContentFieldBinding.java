/*
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

import it.geosolutions.georepo.gui.client.DistribContentType;
import it.geosolutions.georepo.gui.client.model.Watch;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;

/**
 * @author Tobia di Pisa
 *
 */
public class ComboDistribContentFieldBinding extends FieldBinding {

	private Object oldValue;

	/**
	 * @param field
	 * @param property
	 */
	@SuppressWarnings("rawtypes")
	public ComboDistribContentFieldBinding(Field field, String property) {
		super(field, property);
	}

	/* (non-Javadoc)
	 * @see com.extjs.gxt.ui.client.binding.FieldBinding#updateField(boolean)
	 */
	@Override
    @SuppressWarnings("unchecked")
	public void updateField(boolean updateOriginalValue) {
		Object val = onConvertModelValue(model.get(property));

		if (oldValue == null)
			oldValue = val;

		((ComboBox<DistribContentType>) field).setValue(new DistribContentType(val
				.toString()));

		if (updateOriginalValue) {
			((ComboBox<DistribContentType>) field)
					.setOriginalValue(new DistribContentType(val.toString()));
		}
	}

	/* (non-Javadoc)
	 * @see com.extjs.gxt.ui.client.binding.FieldBinding#updateModel()
	 */
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
			((Watch) model).setDistContentType(((DistribContentType) val).getType());
		}
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void resetValue() {
		oldValue = onConvertFieldValue(field.getValue());

		((ComboBox<DistribContentType>) field).setValue(new DistribContentType(oldValue
				.toString()));

		((Watch) model).setDistContentType(((DistribContentType) oldValue).getType());
	}
}
