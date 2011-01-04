/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.observer.ObserverFilterWidget,v. 0.1 3-gen-2011 16.58.03 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 16.58.03 $
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
package it.geosolutions.georepo.gui.client.widget.observer;

import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.Observable;
import it.geosolutions.georepo.gui.client.model.AOI;
import it.geosolutions.georepo.gui.client.model.Filter;
import it.geosolutions.georepo.gui.client.model.User;

import com.extjs.gxt.ui.client.mvc.Dispatcher;

// TODO: Auto-generated Javadoc
/**
 * The Class ObserverFilterWidget.
 */
public class ObserverFilterWidget extends Observable {

    /** The aoi selected. */
    private AOI aoiSelected;

    /** The user selected. */
    private User userSelected;

    /** The default filter. */
    private Filter defaultFilter;

    /**
     * Sets the aoi selected.
     * 
     * @param aoiSelected
     *            the new aoi selected
     */
    public void setAoiSelected(AOI aoiSelected) {
        this.aoiSelected = aoiSelected;
        this.notifyBinding();
    }

    /**
     * Sets the user selected.
     * 
     * @param userSelected
     *            the new user selected
     */
    public void setUserSelected(User userSelected) {
        this.userSelected = userSelected;
        this.notifyBinding();
    }

    /**
     * Gets the user selected.
     * 
     * @return the user selected
     */
    public User getUserSelected() {
        return userSelected;
    }

    /**
     * Gets the aoi selected.
     * 
     * @return the aoi selected
     */
    public AOI getAoiSelected() {
        return aoiSelected;
    }

    /**
     * Gets the default filter.
     * 
     * @return the default filter
     */
    public Filter getDefaultFilter() {
        return defaultFilter;
    }

    /**
     * Sets the default filter.
     * 
     * @param defaultFilter
     *            the new default filter
     */
    public void setDefaultFilter(Filter defaultFilter) {
        this.defaultFilter = defaultFilter;
    }

    /**
     * Exist selected user.
     * 
     * @return true, if successful
     */
    public boolean existSelectedUser() {
        return this.userSelected != null;
    }

    /**
     * Exist selected aoi.
     * 
     * @return true, if successful
     */
    public boolean existSelectedAOI() {
        return this.aoiSelected != null;
    }

    /**
     * Notify binding.
     */
    private void notifyBinding() {
        if (existSelectedUser() && existSelectedAOI()) {
            super.setChanged();
            super.notifyObservers();
        } else {
            unbind();
        }
    }

    /**
     * Exist default filter.
     */
    public void existDefaultFilter() {
        if ((this.aoiSelected.isShared()) || (this.userSelected.equals(aoiSelected.getOwner()))) {
            Dispatcher.forwardEvent(DGWATCHEvents.EXIST_DEFAULT_FILTER);
        } else {
            // UNBIND THE MODEL
            unbind();
        }
    }

    /**
     * Unbind.
     */
    public void unbind() {
        Dispatcher.forwardEvent(DGWATCHEvents.UNBIND_FILTER_WIDGET);
    }
}
