/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.observer.ObserverFilterWidget,v. 0.1 14-gen-2011 19.28.37 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 14-gen-2011 19.28.37 $
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
package it.geosolutions.georepo.gui.client.widget.observer;

import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.Observable;
import it.geosolutions.georepo.gui.client.model.Filter;
import it.geosolutions.georepo.gui.client.model.User;

import com.extjs.gxt.ui.client.mvc.Dispatcher;

// TODO: Auto-generated Javadoc
/**
 * The Class ObserverFilterWidget.
 */
public class ObserverFilterWidget extends Observable {

    /** The user selected. */
    private User userSelected;

    /** The default filter. */
    private Filter defaultFilter;

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
     * Notify binding.
     */
    private void notifyBinding() {
        if (existSelectedUser()) {
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
//        if ((this.aoiSelected.isShared()) || (this.userSelected.equals(aoiSelected.getOwner()))) {
//            Dispatcher.forwardEvent(GeoRepoEvents.EXIST_DEFAULT_FILTER);
//        } else {
//            // UNBIND THE MODEL
//            unbind();
//        }
    }

    /**
     * Unbind.
     */
    public void unbind() {
        Dispatcher.forwardEvent(GeoRepoEvents.UNBIND_FILTER_WIDGET);
    }
}
