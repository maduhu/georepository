/*
 * $ Header: it.geosolutions.georepo.gui.client.mvc.FilterView,v. 0.1 14-gen-2011 19.28.38 created by afabiani <alessio.fabiani at geo-solutions.it> $
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
package it.geosolutions.georepo.gui.client.mvc;

import it.geosolutions.georepo.gui.client.GeoRepoEvents;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.ContentPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class FilterView.
 */
public class FilterView extends View {

    /**
     * Instantiates a new filter view.
     * 
     * @param controller
     *            the controller
     */
//    private AOISFilter filter;

    /**
     * Instantiates a new filter view.
     * 
     * @param controller
     *            the controller
     */
    public FilterView(Controller controller) {
        super(controller);
//        this.filter = new AOISFilter();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.View#handleEvent(com.extjs.gxt.ui.client. mvc.AppEvent)
     */
    @Override
    protected void handleEvent(AppEvent event) {
        if (event.getType() == GeoRepoEvents.ATTACH_AOI_FILTER)
            onAttachFilterWidget(event);

        if (event.getType() == GeoRepoEvents.UNBIND_FILTER_WIDGET)
            onUnbindFilterWidget();

    }

    /**
     * On attach filter widget.
     * 
     * @param event
     *            the event
     */
    private void onAttachFilterWidget(AppEvent event) {
        ContentPanel east = (ContentPanel) event.getData();
//        east.add(this.filter);
        east.layout();
    }

    /**
     * On unbind filter widget.
     */
    private void onUnbindFilterWidget() {
//        FilterBindingWidget filterBindingWidget = this.filter.getFilterBinding();
//        filterBindingWidget.unBindModel();
//
//        filter.setHeading(I18nProvider.getMessages().aoiFilterLabel()
//                + " (Attribute Filter disabled)");

    }

}
