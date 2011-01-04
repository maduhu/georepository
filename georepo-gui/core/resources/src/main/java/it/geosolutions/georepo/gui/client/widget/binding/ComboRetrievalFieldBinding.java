/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.binding.ComboRetrievalFieldBinding,v. 0.1 3-gen-2011 17.06.10 created by afabiani <alessio.fabiani at geo-solutions.it> $
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
package it.geosolutions.georepo.gui.client.widget.binding;

import it.geosolutions.georepo.gui.client.RetrievalType;
import it.geosolutions.georepo.gui.client.model.Watch;

import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;

// TODO: Auto-generated Javadoc
/**
 * The Class ComboRetrievalFieldBinding.
 */
public class ComboRetrievalFieldBinding extends FieldBinding {

    /** The old value. */
    private Object oldValue;

    /**
     * Instantiates a new combo retrieval field binding.
     * 
     * @param field
     *            the field
     * @param property
     *            the property
     */
    @SuppressWarnings("rawtypes")
    public ComboRetrievalFieldBinding(Field field, String property) {
        super(field, property);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.binding.FieldBinding#updateField(boolean)
     */
    @Override
    @SuppressWarnings("unchecked")
    public void updateField(boolean updateOriginalValue) {
        Object val = onConvertModelValue(model.get(property));

        if (oldValue == null)
            oldValue = val;

        ((ComboBox<RetrievalType>) field).setValue(new RetrievalType(val.toString()));

        if (updateOriginalValue) {
            ((ComboBox<RetrievalType>) field).setOriginalValue(new RetrievalType(val.toString()));
        }
    }

    /*
     * (non-Javadoc)
     * 
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
            ((Watch) model).setRetrievalType(((RetrievalType) val).getType());
        }
    }

    /**
     * Reset value.
     */
    @SuppressWarnings("unchecked")
    public void resetValue() {
        oldValue = onConvertFieldValue(field.getValue());

        ((ComboBox<RetrievalType>) field).setValue(new RetrievalType(oldValue.toString()));

        ((Watch) model).setRetrievalType(((RetrievalType) oldValue).getType());
    }
}
