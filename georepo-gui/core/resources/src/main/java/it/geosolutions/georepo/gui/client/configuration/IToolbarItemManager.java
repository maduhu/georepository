/*
 * $Header: it.geosolutions.georepo.gui.client.configuration.IToolbarItemManager,v. 0.1 27/lug/2010 12.11.52 created by frank $
 * $Revision: 0.1 $
 * $Date: 27/lug/2010 12.11.52 $
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
import java.util.List;

/**
 * @author frank
 *
 */
public interface IToolbarItemManager extends Serializable {
	
	public List<GenericClientTool> getClientTools();
	
	public void setClientTools(List<GenericClientTool> clientTools);
	

}
