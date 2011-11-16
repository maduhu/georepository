/*
 * $ Header: it.geosolutions.georepo.gui.server.service.impl.WorkspacesManagerServiceImpl,v. 0.1 28-gen-2011 18.38.29 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 28-gen-2011 18.38.29 $
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
package it.geosolutions.georepo.gui.server.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.extjs.gxt.ui.client.data.PagingLoadResult;

import it.geosolutions.georepo.core.model.LayerDetails;
import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.GSInstance;
import it.geosolutions.georepo.gui.client.model.Rule;
import it.geosolutions.georepo.gui.client.model.data.Layer;
import it.geosolutions.georepo.gui.client.model.data.LayerStyle;
import it.geosolutions.georepo.gui.client.model.data.Workspace;
import it.geosolutions.georepo.gui.client.model.data.rpc.RpcPageLoadResult;
import it.geosolutions.georepo.gui.server.service.IWorkspacesManagerService;
import it.geosolutions.georepo.gui.service.GeoRepoRemoteService;
import it.geosolutions.georepo.services.exception.NotFoundServiceEx;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTLayer;
import it.geosolutions.geoserver.rest.decoder.RESTLayerList;
import it.geosolutions.geoserver.rest.decoder.RESTStyleList;
import it.geosolutions.geoserver.rest.decoder.RESTWorkspaceList;
import it.geosolutions.geoserver.rest.decoder.RESTWorkspaceList.RESTShortWorkspace;
import it.geosolutions.geoserver.rest.decoder.utils.NameLinkElem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


// TODO: Auto-generated Javadoc
/**
 * The Class WorkspacesManagerServiceImpl.
 */
@Component("workspacesManagerServiceGWT")
public class WorkspacesManagerServiceImpl implements IWorkspacesManagerService
{

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** The georepo remote service. */
    @Autowired
    private GeoRepoRemoteService georepoRemoteService;

    /*
     * (non-Javadoc)
     *
     * @see
     * it.geosolutions.georepo.gui.server.service.IWorkspacesManagerService#getWorkspaces(com.extjs
     * .gxt.ui.client.data.PagingLoadConfig, java.lang.String)
     */
    public PagingLoadResult<Workspace> getWorkspaces(int offset, int limit, String remoteURL,
        GSInstance gsInstance) throws ApplicationException
    {

        List<Workspace> workspacesListDTO = new ArrayList<Workspace>();
        workspacesListDTO.add(new Workspace("*"));

        if ((remoteURL != null) && !remoteURL.equals("*") && !remoteURL.contains("?"))
        {
            try
            {
                GeoServerRESTReader gsreader = new GeoServerRESTReader(remoteURL, gsInstance.getUsername(), gsInstance.getPassword());

                RESTWorkspaceList workspaces = gsreader.getWorkspaces();
                if ((workspaces != null) && !workspaces.isEmpty())
                {
                    Iterator<RESTShortWorkspace> wkIT = workspaces.iterator();
                    while (wkIT.hasNext())
                    {
                        RESTShortWorkspace workspace = wkIT.next();

                        workspacesListDTO.add(new Workspace(workspace.getName()));
                    }
                }
            }
            catch (MalformedURLException e)
            {
                logger.error(e.getLocalizedMessage(), e);
                throw new ApplicationException(e.getLocalizedMessage(), e);
            }
        }

        return new RpcPageLoadResult<Workspace>(workspacesListDTO, 0, workspacesListDTO.size());
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * it.geosolutions.georepo.gui.server.service.IWorkspacesManagerService#getLayers(com.extjs.
     * gxt.ui.client.data.PagingLoadConfig, java.lang.String, java.lang.String)
     */
    public PagingLoadResult<Layer> getLayers(int offset, int limit, String baseURL,
        GSInstance gsInstance, String workspace) throws ApplicationException
    {

        List<Layer> layersListDTO = new ArrayList<Layer>();
        layersListDTO.add(new Layer("*"));

        if ((baseURL != null) && !baseURL.equals("*") && !baseURL.contains("?") && (workspace != null) &&
                !workspace.equals("*") && (workspace.length() > 0))
        {
            try
            {
                GeoServerRESTReader gsreader = new GeoServerRESTReader(baseURL, gsInstance.getUsername(), gsInstance.getPassword());

                RESTLayerList layers = gsreader.getLayers();
                if ((layers != null) && !layers.isEmpty())
                {
                    Iterator<NameLinkElem> lrIT = layers.iterator();
                    while (lrIT.hasNext())
                    {
                        NameLinkElem layerLink = lrIT.next();
                        RESTLayer layer = gsreader.getLayer(layerLink.getName());

                        if (checkLayerIsInWorkspace(layer.getResourceUrl(), workspace))
                        {
                            layersListDTO.add(new Layer(layer.getName()));
                        }
                    }
                }
            }
            catch (MalformedURLException e)
            {
                logger.error(e.getLocalizedMessage(), e);
                throw new ApplicationException(e.getLocalizedMessage(), e);
            }
        }

        return new RpcPageLoadResult<Layer>(layersListDTO, 0, layersListDTO.size());
    }

    /**
     * Check layer is in workspace.
     *
     * @param baseURL
     *            the base url
     * @param workspace
     *            the workspace
     * @param layerName
     *            the layer name
     * @return true, if successful
     */
    private boolean checkLayerIsInWorkspace(String resourceHref, String workspace)
    {
        boolean res = false;

        if (resourceHref.contains("workspaces/" + workspace + "/"))
        {
            res = true;
        }

        return res;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * it.geosolutions.georepo.gui.server.service.IWorkspacesManagerService#getStyles(it.geosolutions
     * .georepo.gui.client.model.GSInstance)
     */
    public List<LayerStyle> getStyles(Rule rule) throws ApplicationException
    {

        List<LayerStyle> layerStyles = new ArrayList<LayerStyle>();

        Set<String> allowedStyles = null;
        int size = 0;

        try
        {
            LayerDetails layerDetails = georepoRemoteService.getRuleAdminService().get(rule.getId()).getLayerDetails();

            if (layerDetails != null)
            {
                allowedStyles = layerDetails.getAllowedStyles();

                if (allowedStyles != null)
                {
                    size = allowedStyles.size();
                }
            }

            GeoServerRESTReader gsreader = new GeoServerRESTReader(rule.getInstance().getBaseURL(),
                    rule.getInstance().getUsername(), rule.getInstance().getPassword());

            RESTStyleList styles = gsreader.getStyles();
            List<String> names = styles.getNames();
            Iterator<String> iterator = names.iterator();

            while (iterator.hasNext())
            {
                String name = iterator.next();

                LayerStyle layerStyle = new LayerStyle();

                if (size > 0)
                {
                    Iterator<String> styleIterator = allowedStyles.iterator();
                    while (styleIterator.hasNext())
                    {
                        String allowed = styleIterator.next();

                        if (allowed.equalsIgnoreCase(name))
                        {
                            layerStyle.setEnabled(true);
                        }
                    }
                }
                else
                {
                    layerStyle.setEnabled(false);
                }

                layerStyle.setStyle(name);

                layerStyles.add(layerStyle);
            }

        }
        catch (MalformedURLException e)
        {
            logger.error(e.getLocalizedMessage(), e);
            throw new ApplicationException(e.getLocalizedMessage(), e);
        }
        catch (NotFoundServiceEx e)
        {
            logger.error(e.getLocalizedMessage(), e);
            throw new ApplicationException(e.getLocalizedMessage(), e);
        }

        return layerStyles;
    }

}
