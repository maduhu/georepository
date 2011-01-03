/*
 * $Header: it.geosolutions.georepo.gui.client.widget.ObserverFilterWidget,v. 0.1 18/ago/2010 17.20.35 created by frank $
 * $Revision: 0.1 $
 * $Date: 18/ago/2010 17.20.35 $
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

/**
 * @author frank
 * 
 */
public class ObserverFilterWidget extends Observable {

	private AOI aoiSelected;
	private User userSelected;
	private Filter defaultFilter;

	/**
	 * @param aoiSelected
	 *            the aoiSelected to set
	 */
	public void setAoiSelected(AOI aoiSelected) {
		this.aoiSelected = aoiSelected;
		this.notifyBinding();
	}

	/**
	 * @param userSelected
	 *            the userSelected to set
	 */
	public void setUserSelected(User userSelected) {
		this.userSelected = userSelected;
		this.notifyBinding();
	}

	/**
	 * @return the userSelected
	 */
	public User getUserSelected() {
		return userSelected;
	}

	/**
	 * @return the aoiSelected
	 */
	public AOI getAoiSelected() {
		return aoiSelected;
	}

	/**
	 * @return the defaultFilter
	 */
	public Filter getDefaultFilter() {
		return defaultFilter;
	}

	/**
	 * @param defaultFilter
	 *            the defaultFilter to set
	 */
	public void setDefaultFilter(Filter defaultFilter) {
		this.defaultFilter = defaultFilter;
	}

	/**
	 * This method verify if the user exist
	 * 
	 * @return
	 */
	public boolean existSelectedUser() {
		return this.userSelected != null;
	}

	/**
	 * This method verify if the AOI exist
	 * 
	 * @return
	 */
	public boolean existSelectedAOI() {
		return this.aoiSelected != null;
	}

	/**
	 * Notify Binding check if User Selected and AOI Selected exits and notify
	 * changed in the Observers
	 */
	private void notifyBinding() {
		if (existSelectedUser() && existSelectedAOI()) {
			super.setChanged();
			super.notifyObservers();
		} else {
			unbind();
		}
	}

	public void existDefaultFilter() {
		if ((this.aoiSelected.isShared())
				|| (this.userSelected.equals(aoiSelected.getOwner()))) {
			Dispatcher.forwardEvent(DGWATCHEvents.EXIST_DEFAULT_FILTER);
		} else {
			// UNBIND THE MODEL
			unbind();
		}
	}

	public void unbind() {
		Dispatcher.forwardEvent(DGWATCHEvents.UNBIND_FILTER_WIDGET);
	}
}
