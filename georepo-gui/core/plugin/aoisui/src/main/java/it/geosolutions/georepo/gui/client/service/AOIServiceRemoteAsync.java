/*
 * $Header: it.geosolutions.georepo.gui.client.service.AOIServiceRemoteAsync,v. 0.1 20/lug/2010 10.22.30 created by frank $
 * $Revision: 0.1 $
 * $Date: 20/lug/2010 10.22.30 $
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

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.AOI;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author frank
 * 
 */
/**
 * @author Tobia di Pisa
 *
 */
public interface AOIServiceRemoteAsync {

	/**
	 * 
	 * @param config
	 * @param callback
	 */
	public void loadAOI(PagingLoadConfig config, String searchText,
			AsyncCallback<PagingLoadResult<AOI>> callback);

//	/**
//	 * @param config
//	 * @param userId
//	 * @param callback
//	 */
//	public void getUserAOIs(PagingLoadConfig config, long userId,
//			AsyncCallback<PagingLoadResult<AOI>> callback);

//	/**
//	 * 
//	 * @param userId
//	 * @param aoiId
//	 * @param callback
//	 */
//	public void unshareAOI(long userId, long aoiId, AsyncCallback<Object> callback);
//
//	/**
//	 * 
//	 * @param aoiID
//	 * @param callback
//	 */
//	public void shareAOI(long aoiID, AsyncCallback<Object> callback);

	/**
	 * 
	 * @param aoi
	 * @param callback
	 */
	public void saveAOI(AOI aoi, AsyncCallback<AOI> callback);

	/**
	 * @param user
	 * @param callback
	 */
	public void getAOIDetail(AOI aoi, AsyncCallback<AOI> callback);

	/**
	 * @param aoiId
	 * @param callback
	 */
	public void deleteAOI(long aoiId, AsyncCallback<Object> callback);

	/**
	 * @param aoi
	 * @return AOI
	 * @throws ApplicationException
	 */
	public void updateAOI(AOI aoi, AsyncCallback<AOI> callback);
	
	/**
	 * @param aoiId
	 * @param callback
	 */
	public void getWatchAOIDetails(long aoiId, AsyncCallback<AOI> callback);
}
