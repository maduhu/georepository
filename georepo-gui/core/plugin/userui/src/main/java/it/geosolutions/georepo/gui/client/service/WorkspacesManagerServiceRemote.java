/*
 * $ Header: it.geosolutions.georepo.gui.client.service.WorkspacesManagerServiceRemote,v. 0.1 28-gen-2011 18.28.27 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 28-gen-2011 18.28.27 $
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
import it.geosolutions.georepo.gui.client.model.data.Layer;
import it.geosolutions.georepo.gui.client.model.data.Workspace;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

// TODO: Auto-generated Javadoc
/**
 * The Interface WorkspacesManagerServiceRemote.
 */
public interface WorkspacesManagerServiceRemote extends RemoteService {

    /**
     * The Class Util.
     */
    public static class Util {

        /** The instance. */
        private static WorkspacesManagerServiceRemoteAsync instance;

        /**
         * Gets the instance.
         * 
         * @return the instance
         */
        public static WorkspacesManagerServiceRemoteAsync getInstance() {
            if (instance == null) {
                instance = (WorkspacesManagerServiceRemoteAsync) GWT.create(WorkspacesManagerServiceRemote.class);
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint(GWT.getModuleBaseURL() + "WorkspacesManagerServiceRemote");
            }
            return instance;
        }
    }

    /**
     * Gets the workspaces.
     * 
     * @param config
     *            the config
     * @param baseURL
     *            the base url
     * @param gsInstance
     *            the instance: this is passed for for workspace's authorization
     *            
     * @return the workspaces
     * @throws ApplicationException
     *             the application exception
     */
    public PagingLoadResult<Workspace> getWorkspaces(PagingLoadConfig config, String baseURL, GSInstance gsInstance) throws ApplicationException;

    
    /**
     * Gets the layers.
     * 
     * @param loadConfig
     *            the load config
     * @param baseURL
     *            the base url
     * @param workspace
     *            the workspace
     * @return the layers
     * @throws ApplicationException
     *             the application exception
     */
    public PagingLoadResult<Layer> getLayers(PagingLoadConfig loadConfig, String baseURL, String workspace) throws ApplicationException;
}