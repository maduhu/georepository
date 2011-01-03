/*
 * $Header: it.geosolutions.georepo.gui.client.widget.tab.GeoRSSTabItem,v. 0.1 09/lug/2010 10.23.26 created by frank $
 * $Revision: 0.1 $
 * $Date: 09/lug/2010 10.23.26 $
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
package it.geosolutions.georepo.gui.client.service;

import it.geosolutions.georepo.gui.client.model.Feature;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FeatureServiceRemoteAsync {

	/**
	 * 
	 * @param config
	 * @param callback
	 */
	public void getUserFeatures(PagingLoadConfig config, long userId, AsyncCallback<PagingLoadResult<Feature>> callback);
	
}
