/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.AOISFilter,v. 0.1 3-gen-2011 16.52.55 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 16.52.55 $
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

import it.geosolutions.georepo.gui.client.i18n.I18nProvider;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.ContentPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class AOISFilter.
 */
public class AOISFilter extends ContentPanel {

    /** The filter binding. */
    private FilterBindingWidget filterBinding;

    // private FilterInfo filterInfo;

    /**
     * Instantiates a new aOIS filter.
     */
    public AOISFilter() {
        setId("aoisFilter");
        setHeading(I18nProvider.getMessages().aoiFilterLabel());
        setScrollMode(Scroll.AUTO);

        this.filterBinding = new FilterBindingWidget();
        // this.filterInfo = new FilterInfo();

        // add(filterInfo.getFormPanel());
        add(filterBinding.getFormPanel());

    }

    /**
     * Gets the filter binding.
     * 
     * @return the filter binding
     */
    public FilterBindingWidget getFilterBinding() {
        return filterBinding;
    }

    // /**
    // * @return the filterInfo
    // */
    // public FilterInfo getFilterInfo() {
    // return filterInfo;
    // }
}
