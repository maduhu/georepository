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

import it.geosolutions.georepo.gui.client.ContentType.ContentTypeEnum;
import it.geosolutions.georepo.gui.client.DistribContentType.DistribContentTypeEnum;
import it.geosolutions.georepo.gui.client.DistribUpdateInterval.DistribUpdateIntervalEnum;
import it.geosolutions.georepo.gui.client.RetrievalType.RetrievalTypeEnum;
import it.geosolutions.georepo.gui.client.SendType.SendTypeEnum;
import it.geosolutions.georepo.gui.client.UpdateInterval.UpdateIntervalEnum;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;

/**
 * @author Tobia di Pisa
 *
 */
public class DGWATCHWatchFormBinding  extends FormBinding {
	
	/**
	 * @param panel
	 * @param autoBind
	 */
	public DGWATCHWatchFormBinding(FormPanel panel, boolean autoBind) {
		super(panel, autoBind);
	}

	/* (non-Javadoc)
	 * @see com.extjs.gxt.ui.client.binding.FormBinding#autoBind()
	 */
	@Override
    @SuppressWarnings("rawtypes")
	public void autoBind() {
		for (Field f : panel.getFields()) {
			if (!bindings.containsKey(f.getId())) {
				String name = f.getName();
				if (name != null) {
					FieldBinding b;
					if (f.getId().equalsIgnoreCase(
							SendTypeEnum.TYPE.getValue()))
						b = new ComboTypeFieldBinding(f, f.getName());
					else if (f.getId().equalsIgnoreCase(
							UpdateIntervalEnum.TIME.getValue()))
						b = new ComboTimeFieldBinding(f, f.getName());
					else if (f.getId().equalsIgnoreCase(
							RetrievalTypeEnum.TYPE.getValue()))
						b = new ComboRetrievalFieldBinding(f, f.getName());
					else if (f.getId().equalsIgnoreCase(
							ContentTypeEnum.TYPE.getValue()))
						b = new ComboContentFieldBinding(f, f.getName());					
					else if (f.getId().equalsIgnoreCase(
							DistribContentTypeEnum.TYPE.getValue()))
						b = new ComboDistribContentFieldBinding(f, f.getName());					
					else if (f.getId().equalsIgnoreCase(
							DistribUpdateIntervalEnum.TIME.getValue()))
						b = new ComboDistribTimeFieldBinding(f, f.getName());					
					else
						b = new DGWATCHFieldBinding(f, f.getName());
					bindings.put(f.getId(), b);
				}
			}
		}
	}
	
}
