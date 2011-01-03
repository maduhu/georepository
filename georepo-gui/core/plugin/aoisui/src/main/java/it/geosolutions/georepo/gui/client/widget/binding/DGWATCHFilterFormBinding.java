/*
 * $Header: it.geosolutions.georepo.gui.client.widget.binding.DGWATCHFilterFormBinding,v. 0.1 19/ago/2010 18.22.17 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 19/ago/2010 18.22.17 $
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


import it.geosolutions.georepo.gui.client.model.Filter.FilterKeyValue;
import it.geosolutions.georepo.gui.client.widget.binding.filtertype.DataLayerComboFieldBinding;
import it.geosolutions.georepo.gui.client.widget.binding.filtervalue.AcqDateFieldBinding;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;

/**
 * @author giuseppe
 * 
 */
public class DGWATCHFilterFormBinding extends FormBinding {

	public DGWATCHFilterFormBinding(FormPanel panel, boolean autoBind) {
		super(panel, autoBind);
	}

	@Override
    public void autoBind() {
		for (Field<?> f : panel.getFields()) {
			if (!bindings.containsKey(f.getId())) {
				String name = f.getName();
				if (name != null && name.length() > 0) {
					FieldBinding b = null;
					// if (f.getId().equalsIgnoreCase(
					// FilterKeyValue.FILTER_ACQ_DATE.getValue()))
					// b = new AcqComboFieldBinding(f, f.getName());
					if (f.getId().equalsIgnoreCase(
							FilterKeyValue.ACQ_DATE.getValue()))
						b = new AcqDateFieldBinding(f, f.getName());
					else if (f.getId().equalsIgnoreCase(
							FilterKeyValue.FILTER_DATA_LAYER.getValue()))
						b = new DataLayerComboFieldBinding(f, f.getName());
					// else if (f.getId().equalsIgnoreCase(
					// FilterKeyValue.DATA_LAYER.getValue()))
					// b = new DataLayerFieldBinding(f, f.getName());
					// else if (f.getId().equalsIgnoreCase(
					// FilterKeyValue.FILTER_CLOUD_COVER.getValue()))
					// b = new CloudCoverComboFieldBinding(f, f.getName());
					// else if (f.getId().equalsIgnoreCase(
					// FilterKeyValue.CLOUD_COVER.getValue()))
					// b = new CloudCoverFieldBinding(f, f.getName());
					// else if (f.getId().equalsIgnoreCase(
					// FilterKeyValue.FILTER_LEGACY.getValue()))
					// b = new LegacyIdComboFieldBinding(f, f.getName());
					// else if (f.getId().equalsIgnoreCase(
					// FilterKeyValue.LEGACY_ID.getValue()))
					// b = new LegacyIdFieldBinding(f, f.getName());
					// else if (f.getId()
					// .equalsIgnoreCase(
					// FilterKeyValue.RETRIEVE_TYPE_INCREMENTAL
					// .getValue()))
					// b = new IncrementalRadioFieldBinding(f, f.getName());
					// else if (f.getId().equalsIgnoreCase(
					// FilterKeyValue.RETRIEVE_TYPE.getValue()))
					// b = new CumulativeRadioFieldBinding(f, f.getName());
					if (b != null)
						bindings.put(f.getId(), b);
				}
			}
		}
	}

	/**
	 * reset All Values
	 */
	public void resetAllValues() {
		for (FieldBinding b : getBindings()) {
			((IFilterFieldBinding) b).resetValue();
		}
	}
}
