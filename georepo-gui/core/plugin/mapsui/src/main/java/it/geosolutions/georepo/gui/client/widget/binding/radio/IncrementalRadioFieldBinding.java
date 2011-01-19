/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.binding.radio.IncrementalRadioFieldBinding,v. 0.1 14-gen-2011 19.28.38 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 14-gen-2011 19.28.38 $
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
package it.geosolutions.georepo.gui.client.widget.binding.radio;

import it.geosolutions.georepo.gui.client.widget.binding.IFilterFieldBinding;

import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.Radio;

// TODO: Auto-generated Javadoc
/**
 * The Class IncrementalRadioFieldBinding.
 */
public class IncrementalRadioFieldBinding extends FieldBinding implements IFilterFieldBinding {

    /** The old value. */
    private Object oldValue;

    /**
     * Instantiates a new incremental radio field binding.
     * 
     * @param field
     *            the field
     * @param property
     *            the property
     */
    @SuppressWarnings("rawtypes")
    public IncrementalRadioFieldBinding(Field field, String property) {
        super(field, property);
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.binding.FieldBinding#updateField(boolean)
     */
    @Override
    public void updateField(boolean updateOriginalValue) {
        Object val = onConvertModelValue(model.get(property));

        if (oldValue == null)
            oldValue = val;

        if (((String) val).equals("INCREMENTAL"))
            ((Radio) field).setValue(Boolean.TRUE);
        else
            ((Radio) field).setValue(Boolean.FALSE);
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
            // model.set(property, ((SendType) val).getType());
            // ((Filter) model).setRetrieveType(((Boolean) val)
            // .equals(Boolean.TRUE) ? "INCREMENTAL" : null);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.binding.IFilterFieldBinding#resetValue()
     */
    public void resetValue() {
        oldValue = onConvertFieldValue(field.getValue());

        if (((String) oldValue).equals("INCREMENTAL"))
            ((Radio) field).setValue(Boolean.TRUE);
        else
            ((Radio) field).setValue(Boolean.FALSE);

        // model.set(property, oldValue.toString());
        // ((Filter) model).setRetrieveType(((Boolean) oldValue)
        // .equals(Boolean.TRUE) ? "INCREMENTAL" : null);
    }
}
