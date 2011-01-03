/*
 * $Header: it.geosolutions.georepo.gui.client.configuration.GenericClientTool,v. 0.1 27/lug/2010 11.05.37 created by frank $
 * $Revision: 0.1 $
 * $Date: 27/lug/2010 11.05.37 $
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
package it.geosolutions.georepo.gui.client.configuration;

import java.io.Serializable;

/**
 * @author frank
 * 
 */
public class GenericClientTool implements Comparable<GenericClientTool>,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4077220993928371589L;

	private String id;
	private int order;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GenericClientTool [ ID = " + id + ", ORDER = " + order + "]";
	}

	public int compareTo(GenericClientTool o) {
		return getOrder() - o.getOrder();
	}
}
