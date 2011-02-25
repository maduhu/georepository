/*
 * $ Header: it.geosolutions.georepo.gui.client.service.InstancesManagerServiceRemoteAsync,v. 0.1 28-gen-2011 11.22.19 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 28-gen-2011 11.22.19 $
 *
 * ====================================================================
 *
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 * http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
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

import it.geosolutions.georepo.gui.client.model.GSInstance;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Interface InstancesManagerServiceRemoteAsync.
 */
public interface InstancesManagerServiceRemoteAsync {

    /**
     * Gets the instances.
     * 
     * @param config
     *            the config
     * @param callback
     *            the callback
     * @return the instances
     */
    public  void  getInstances(PagingLoadConfig config, boolean full, AsyncCallback<PagingLoadResult<GSInstance>> callback);

    /**
     * Get the instance
     * 
     * @param config
     * @param name
     * @return
     */
    public void getInstance(PagingLoadConfig config, long id, AsyncCallback<PagingLoadResult<GSInstance>> asyncCallback);
    
    /**
     * Save instance.
     * 
     * @param instance
     *            the instance
     * @param asyncCallback
     *            the async callback
     */
    public void saveInstance(GSInstance instance, AsyncCallback<PagingLoadResult<GSInstance>> asyncCallback);

    /**
     * Delete instance.
     * 
     * @param instance
     *            the instance
     * @param asyncCallback
     *            the async callback
     */
    public void deleteInstance(GSInstance instance, AsyncCallback<PagingLoadResult<GSInstance>> asyncCallback);

}
