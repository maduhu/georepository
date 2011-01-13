/*
 * $ Header: it.geosolutions.georepo.gui.client.service.WatchServiceRemoteAsync,v. 0.1 3-gen-2011 16.52.44 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.georepo.gui.client.model.ClientShortWatch;
import it.geosolutions.georepo.gui.client.model.ExecutedClientShortWatch;
import it.geosolutions.georepo.gui.client.model.Watch;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Interface WatchServiceRemoteAsync.
 */
public interface WatchServiceRemoteAsync {

    /**
     * Gets the member watches.
     * 
     * @param config
     *            the config
     * @param searchText
     *            the search text
     * @param callback
     *            the callback
     * @return the member watches
     */
    public void getMemberWatches(PagingLoadConfig config, String searchText,
            AsyncCallback<PagingLoadResult<ClientShortWatch>> callback);

    /**
     * Save watch.
     * 
     * @param watch
     *            the watch
     * @param callback
     *            the callback
     */
    public void saveWatch(Watch watch, AsyncCallback<Watch> callback);

    /**
     * Update watch.
     * 
     * @param watch
     *            the watch
     * @param callback
     *            the callback
     */
    public void updateWatch(Watch watch, AsyncCallback<Watch> callback);

    /**
     * Gets the watch details.
     * 
     * @param id
     *            the id
     * @param callback
     *            the callback
     * @return the watch details
     */
    public void getWatchDetails(long id, AsyncCallback<Watch> callback);

    /**
     * Gets the executed watches.
     * 
     * @param config
     *            the config
     * @param callback
     *            the callback
     * @return the executed watches
     */
    public void getExecutedWatches(PagingLoadConfig config,
            AsyncCallback<PagingLoadResult<ExecutedClientShortWatch>> callback);

    /**
     * Delete watch.
     * 
     * @param id
     *            the id
     * @param callback
     *            the callback
     */
    public void deleteWatch(long id, AsyncCallback<Object> callback);

}
