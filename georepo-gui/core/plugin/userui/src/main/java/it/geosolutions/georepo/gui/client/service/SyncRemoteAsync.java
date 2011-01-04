/*
 * $ Header: it.geosolutions.georepo.gui.client.service.SyncRemoteAsync,v. 0.1 3-gen-2011 17.06.55 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 17.06.55 $
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

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Interface SyncRemoteAsync.
 */
public interface SyncRemoteAsync {

    /**
     * Run distribution.
     * 
     * @param watchId
     *            the watch id
     * @param delayInSeconds
     *            the delay in seconds
     * @param callback
     *            the callback
     */
    public void runDistribution(long watchId, int delayInSeconds, AsyncCallback<Void> callback);

    /**
     * Delete imagery.
     * 
     * @param featureIds
     *            the feature ids
     * @param nodeIds
     *            the node ids
     * @param callback
     *            the callback
     */
    public void deleteImagery(List<String> featureIds, List<Long> nodeIds,
            AsyncCallback<Void> callback);
}
