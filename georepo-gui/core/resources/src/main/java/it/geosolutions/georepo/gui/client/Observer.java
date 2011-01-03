/*
 * $Header: it.geosolutions.georepo.gui.client.Observer,v. 0.1 18/ago/2010 18.38.37 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 18/ago/2010 18.38.37 $
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
package it.geosolutions.georepo.gui.client;


/**
 * @author giuseppe
 * 
 */
public interface Observer {
	/**
	 * This method is called whenever the observed object is changed. An
	 * application calls an <tt>Observable</tt> object's
	 * <code>notifyObservers</code> method to have all the object's observers
	 * notified of the change.
	 * 
	 * @param o
	 *            the observable object.
	 * @param arg
	 *            an argument passed to the <code>notifyObservers</code> method.
	 */
	void update(Observable o, Object arg);
}
