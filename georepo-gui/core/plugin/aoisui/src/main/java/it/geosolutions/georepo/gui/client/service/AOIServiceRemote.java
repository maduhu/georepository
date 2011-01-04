/*
 * $ Header: it.geosolutions.georepo.gui.client.service.AOIServiceRemote,v. 0.1 3-gen-2011 16.52.44 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 16.52.44 $
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

// TODO: Auto-generated Javadoc
/**
 * The Interface AOIServiceRemote.
 */
public interface AOIServiceRemote extends RemoteService {

    /**
     * The Class Util.
     */
    public static class Util {

        /** The instance. */
        private static AOIServiceRemoteAsync instance;

        /**
         * Gets the single instance of Util.
         * 
         * @return single instance of Util
         */
        public static AOIServiceRemoteAsync getInstance() {
            if (instance == null) {
                instance = (AOIServiceRemoteAsync) GWT.create(AOIServiceRemote.class);
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint(GWT.getModuleBaseURL() + "AOIServiceRemote");
            }
            return instance;
        }
    }

    /**
     * Load aoi.
     * 
     * @param config
     *            the config
     * @param searchText
     *            the search text
     * @return the paging load result
     * @throws ApplicationException
     *             the application exception
     */
    public PagingLoadResult<AOI> loadAOI(PagingLoadConfig config, String searchText)
            throws ApplicationException;

    // /**
    // * @param config
    // * @param userId
    // * @return
    // */
    // public PagingLoadResult<AOI> getUserAOIs(PagingLoadConfig config, long userId);

    // /**
    // *
    // * @param userId
    // * @param aoiId
    // */
    // public void unshareAOI(long userId, long aoiId) throws ApplicationException;

    // /**
    // *
    // * @param aoiID
    // * @throws ApplicationException
    // */
    // public void shareAOI(long aoiID) throws ApplicationException;

    /**
     * Save aoi.
     * 
     * @param aoi
     *            the aoi
     * @return the aOI
     * @throws ApplicationException
     *             the application exception
     */
    public AOI saveAOI(AOI aoi) throws ApplicationException;

    /**
     * Gets the aOI detail.
     * 
     * @param aoi
     *            the aoi
     * @return the aOI detail
     * @throws ApplicationException
     *             the application exception
     */
    public AOI getAOIDetail(AOI aoi) throws ApplicationException;

    /**
     * Delete aoi.
     * 
     * @param aoiId
     *            the aoi id
     * @throws ApplicationException
     *             the application exception
     */
    public void deleteAOI(long aoiId) throws ApplicationException;

    /**
     * Update aoi.
     * 
     * @param aoi
     *            the aoi
     * @return the aOI
     * @throws ApplicationException
     *             the application exception
     */
    public AOI updateAOI(AOI aoi) throws ApplicationException;

    /**
     * Gets the watch aoi details.
     * 
     * @param aoiId
     *            the aoi id
     * @return the watch aoi details
     * @throws ApplicationException
     *             the application exception
     */
    public AOI getWatchAOIDetails(long aoiId) throws ApplicationException;

}
