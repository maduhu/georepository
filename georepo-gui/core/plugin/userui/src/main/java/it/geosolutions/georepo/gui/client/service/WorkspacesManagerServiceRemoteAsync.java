/*
 * $ Header: it.geosolutions.georepo.gui.client.service.WorkspacesManagerServiceRemoteAsync,v. 0.1 28-gen-2011 18.27.42 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 28-gen-2011 18.27.42 $
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

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.GSInstance;
import it.geosolutions.georepo.gui.client.model.Rule;
import it.geosolutions.georepo.gui.client.model.data.Layer;
import it.geosolutions.georepo.gui.client.model.data.LayerStyle;
import it.geosolutions.georepo.gui.client.model.data.Workspace;

import java.util.List;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Interface WorkspacesManagerServiceRemoteAsync.
 */
public interface WorkspacesManagerServiceRemoteAsync {

    /**
     * Gets the workspaces.
     * 
     * @param config
     *            the config
     * @param URL
     *            the URL
     * @param callback
     *            the callback
     * @param gsInstance
     *            the nstance: this is passed for for workspace's authorization
     * @return the workspaces
     */
    public void getWorkspaces(PagingLoadConfig config, String baseURL, GSInstance gsInstance,
            AsyncCallback<PagingLoadResult<Workspace>> callback) throws ApplicationException;

    /**
     * Gets the layers.
     * 
     * @param loadConfig
     *            the load config
     * @param baseURL
     *            the base url
     * @param workspace
     *            the workspace
     * @param callback
     *            the callback
     * @return the layers
     */
    public void getLayers(PagingLoadConfig loadConfig, String baseURL, GSInstance gsInstance,
            String workspace, AsyncCallback<PagingLoadResult<Layer>> callback);

    /**
     * @param gsInstance
     * @param callback
     */
    public void getStyles(Rule rule, AsyncCallback<List<LayerStyle>> callback);

}
