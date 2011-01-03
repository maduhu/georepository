/*
 * $Header: it.geosolutions.georepo.gui.client.widget.AOISWidget,v. 0.1 08/lug/2010 15.59.48 created by frank $
 * $Revision: 0.1 $
 * $Date: 08/lug/2010 15.59.48 $
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

import it.geosolutions.georepo.gui.client.model.AOI;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

/**
 * @author frank, Tobia Di Pisa
 * 
 */
public class AOISWidget extends ContentPanel {

	private DGWATCHBindingWidget<AOI> aoiBinding;

    public AOISWidget(String paneHeading) {
    	setId("aoisWidget");
        setHeading(paneHeading);
        setLayout(new FitLayout());

		setLayoutOnChange(true);

        this.aoiBinding = new AOIBindingWidget();

        add(this.aoiBinding.getFormPanel());
        
        setScrollMode(Scroll.AUTOY);
    }

	/**
	 * @return the aoiBinding
	 */
	public DGWATCHBindingWidget<AOI> getAoiBinding() {
		return aoiBinding;
	}

}
