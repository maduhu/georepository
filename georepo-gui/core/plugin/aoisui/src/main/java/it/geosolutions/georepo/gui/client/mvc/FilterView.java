/*
 * $Header: it.geosolutions.georepo.gui.client.mvc.FilterView,v. 0.1 18/ago/2010 09.28.34 created by frank $
 * $Revision: 0.1 $
 * $Date: 18/ago/2010 09.28.34 $
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
package it.geosolutions.georepo.gui.client.mvc;

import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.widget.AOISFilter;
import it.geosolutions.georepo.gui.client.widget.FilterBindingWidget;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.ContentPanel;

/**
 * @author frank
 * 
 */
public class FilterView extends View {

	private AOISFilter filter;

	public FilterView(Controller controller) {
		super(controller);
		this.filter = new AOISFilter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.extjs.gxt.ui.client.mvc.View#handleEvent(com.extjs.gxt.ui.client.
	 * mvc.AppEvent)
	 */
	@Override
	protected void handleEvent(AppEvent event) {
		if (event.getType() == DGWATCHEvents.ATTACH_AOI_FILTER)
			onAttachFilterWidget(event);

		if (event.getType() == DGWATCHEvents.UNBIND_FILTER_WIDGET)
			onUnbindFilterWidget();

	}

	/**
	 * Attach Filter Widget on East Panel
	 * 
	 * @param event
	 */
	private void onAttachFilterWidget(AppEvent event) {
		ContentPanel east = (ContentPanel) event.getData();
		east.add(this.filter);
		east.layout();
	}

	/**
	 * Unbind Filter Widget and Disables All Buttons
	 */
	private void onUnbindFilterWidget() {
		FilterBindingWidget filterBindingWidget = this.filter.getFilterBinding();
		filterBindingWidget.unBindModel();
		
		filter.setHeading(I18nProvider.getMessages().aoiFilterLabel() +
                " (Attribute Filter disabled)");

	}

	/**
	 * @return the filter
	 */
	public AOISFilter getFilter() {
		return filter;
	}

}
