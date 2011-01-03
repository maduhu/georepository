/*
 * $Header: it.geosolutions.georepo.gui.client.service.AOIServiceRemote,v. 0.1 20/lug/2010 10.22.05 created by frank $
 * $Revision: 0.1 $
 * $Date: 20/lug/2010 10.22.05 $
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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * @author frank
 * 
 */
public interface AOIServiceRemote extends RemoteService {

	public static class Util {
		private static AOIServiceRemoteAsync instance;

		public static AOIServiceRemoteAsync getInstance() {
			if (instance == null) {
				instance = (AOIServiceRemoteAsync) GWT
						.create(AOIServiceRemote.class);
				ServiceDefTarget target = (ServiceDefTarget) instance;
				target.setServiceEntryPoint(GWT.getModuleBaseURL()
						+ "AOIServiceRemote");
			}
			return instance;
		}
	}

	/**
	 * 
	 * @param config
	 * @return
	 */
	public PagingLoadResult<AOI> loadAOI(PagingLoadConfig config,
			String searchText) throws ApplicationException;

//	/**
//	 * @param config
//	 * @param userId
//	 * @return
//	 */
//	public PagingLoadResult<AOI> getUserAOIs(PagingLoadConfig config, long userId);
	
//	/**
//	 * 
//	 * @param userId
//	 * @param aoiId
//	 */
//	public void unshareAOI(long userId, long aoiId) throws ApplicationException;
	
//	/**
//	 * 
//	 * @param aoiID
//	 * @throws ApplicationException
//	 */
//	public void shareAOI(long aoiID) throws ApplicationException;

	/**
	 * 
	 * @param aoi
	 * @throws ApplicationException
	 */
	public AOI saveAOI(AOI aoi) throws ApplicationException;

	/**
	 * @param aoi
	 * @throws ApplicationException
	 */
	public AOI getAOIDetail(AOI aoi) throws ApplicationException;

	/**
	 * @param aoiId
	 * @throws ApplicationException
	 */
	public void deleteAOI(long aoiId) throws ApplicationException;

	/**
	 * @param aoi
	 * @param callback
	 * @return AOI
	 */
	public AOI updateAOI(AOI aoi) throws ApplicationException;
	
	/**
	 * @param aoiId
	 * @return
	 * @throws ApplicationException
	 */
	public AOI getWatchAOIDetails(long aoiId) throws ApplicationException;

}
