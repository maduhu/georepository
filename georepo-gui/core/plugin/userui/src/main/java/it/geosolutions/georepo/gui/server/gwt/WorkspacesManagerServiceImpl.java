/*
 * $ Header: it.geosolutions.georepo.gui.server.gwt.WorkspacesManagerServiceImpl,v. 0.1 28-gen-2011 18.29.30 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 28-gen-2011 18.29.30 $
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
package it.geosolutions.georepo.gui.server.gwt;

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.data.Layer;
import it.geosolutions.georepo.gui.client.model.data.Workspace;
import it.geosolutions.georepo.gui.client.service.WorkspacesManagerServiceRemote;
import it.geosolutions.georepo.gui.server.service.IWorkspacesManagerService;
import it.geosolutions.georepo.gui.spring.ApplicationContextUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkspacesManagerServiceImpl.
 */
public class WorkspacesManagerServiceImpl extends RemoteServiceServlet implements
        WorkspacesManagerServiceRemote {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 9061178642285826473L;

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** The workspace manager service. */
    private IWorkspacesManagerService workspaceManagerService;

    /**
     * Instantiates a new workspaces manager service impl.
     */
    public WorkspacesManagerServiceImpl() {
        this.workspaceManagerService = (IWorkspacesManagerService) ApplicationContextUtil.getInstance()
                .getBean("workspacesManagerServiceGWT");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.client.service.WorkspacesManagerServiceRemote#getWorkspaces(com.extjs
     * .gxt.ui.client.data.PagingLoadConfig)
     */
    public PagingLoadResult<Workspace> getWorkspaces(PagingLoadConfig config, String URL)
            throws ApplicationException {
        return workspaceManagerService.getWorkspaces(config, URL);
    }

    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.client.service.WorkspacesManagerServiceRemote#getLayers(com.extjs.gxt.ui.client.data.PagingLoadConfig, java.lang.String, java.lang.String)
     */
    public PagingLoadResult<Layer> getLayers(PagingLoadConfig config, String baseURL,
            String workspace) throws ApplicationException {
        return workspaceManagerService.getLayers(config, baseURL, workspace);
    }

}