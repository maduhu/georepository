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

import it.geosolutions.georepo.core.model.LayerDetails;
import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.GSInstance;
import it.geosolutions.georepo.gui.client.model.Rule;
import it.geosolutions.georepo.gui.client.model.data.Layer;
import it.geosolutions.georepo.gui.client.model.data.LayerStyle;
import it.geosolutions.georepo.gui.client.model.data.Workspace;
import it.geosolutions.georepo.gui.server.service.IInstancesManagerService;
import it.geosolutions.georepo.gui.server.service.IWorkspacesManagerService;
import it.geosolutions.georepo.gui.service.GeoRepoRemoteService;
import it.geosolutions.georepo.gui.spring.ApplicationContextUtil;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTStyleList;
import it.geosolutions.geoserver.rest.decoder.RESTWorkspaceList;
import it.geosolutions.geoserver.rest.decoder.RESTWorkspaceList.RESTShortWorkspace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkspacesManagerServiceImpl.
 */
@Component("workspacesManagerServiceGWT")
public class WorkspacesManagerServiceImpl implements IWorkspacesManagerService {

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
    public PagingLoadResult<Workspace> getWorkspaces(PagingLoadConfig config, String remoteURL,
            GSInstance gsInstance) throws ApplicationException {

        List<Workspace> workspacesListDTO = new ArrayList<Workspace>();
        workspacesListDTO.add(new Workspace("*"));

        if (remoteURL != null && !remoteURL.equals("*") && !remoteURL.contains("?")) {
            try {
                GeoServerRESTReader gsreader = new GeoServerRESTReader(remoteURL, gsInstance
                        .getUsername(), gsInstance.getPassword());

                RESTWorkspaceList workspaces = gsreader.getWorkspaces();
                if (workspaces != null && !workspaces.isEmpty()) {
                    Iterator<RESTShortWorkspace> wkIT = workspaces.iterator();
                    while (wkIT.hasNext()) {
                        RESTShortWorkspace workspace = wkIT.next();

                        workspacesListDTO.add(new Workspace(workspace.getName()));
                    }
                }
            } catch (MalformedURLException e) {
                logger.error(e.getLocalizedMessage(), e);
                throw new ApplicationException(e.getLocalizedMessage(), e);
            }
        }

        return new BasePagingLoadResult<Workspace>(workspacesListDTO, 0, workspacesListDTO.size());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.server.service.IWorkspacesManagerService#getLayers(com.extjs.
     * gxt.ui.client.data.PagingLoadConfig, java.lang.String, java.lang.String)
     */
    public PagingLoadResult<Layer> getLayers(PagingLoadConfig config, String baseURL,
            String workspace) throws ApplicationException {

        List<Layer> layersListDTO = new ArrayList<Layer>();
        layersListDTO.add(new Layer("*"));

        if (baseURL != null && !baseURL.equals("*") && !baseURL.contains("?") && workspace != null
                && !workspace.equals("*") && workspace.length() > 0) {
            String remoteURL = baseURL + (!baseURL.endsWith("/") ? "/" : "") + "rest/layers.json";
            String jsonTxt = getRemoteJsonString(remoteURL, null);

            if (jsonTxt != null && jsonTxt.length() > 0) {
                JSONObject json = (JSONObject) JSONSerializer.toJSON(jsonTxt);
                JSONObject layersRoot = json.getJSONObject("layers");

                if (layersRoot != null) {
                    JSONArray layers = layersRoot.getJSONArray("layer");

                    if (layers != null && layers.isArray() && !layers.isEmpty()) {
                        for (int i = 0; i < layers.size(); i++) {
                            JSONObject layer = layers.getJSONObject(i);

                            String layerName = layer.getString("name");

                            if (checkLayerIsInWorkspace(baseURL, workspace, layerName)) {
                                layersListDTO.add(new Layer(layerName));
                            }
                        }
                    }
                }
            }
        }

        return new BasePagingLoadResult<Layer>(layersListDTO, 0, layersListDTO.size());
    }

    /**
     * Gets the remote json string.
     * 
     * @param remoteURL
     *            the remote url
     * @param gsInstance
     * @return the remote json string
     */
    private String getRemoteJsonString(String remoteURL, GSInstance gsInstance) {

        BufferedReader in = null;
        IInstancesManagerService instancesManagerService2 = (IInstancesManagerService) ApplicationContextUtil
                .getInstance().getBean("instancesManagerServiceGWT");
        try {
            if (gsInstance != null
                    && !checkLogin(gsInstance.getBaseURL(), gsInstance.getUsername(), gsInstance
                            .getPassword())) {// remoteURL
                return null;
            }
        } catch (Exception e) {
            logger.warn("no right for instance name " + gsInstance.getName());
            return null;
        }
        try {

            URL url = new URL(remoteURL);
            in = new BufferedReader(new InputStreamReader(url.openStream()));

            String jsonText = "";
            String line = null;
            while ((line = in.readLine()) != null) {
                jsonText += line;
            }

            return jsonText;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e);
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
        }
    }

    /**
     * 
     * @param remoteURL
     * @param username
     * @param password
     * @return
     */
    public boolean checkLogin(String remoteURL, String username, String password) {
        URL url;
        try {
            url = new URL(remoteURL);
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            logger.error(e1.getMessage(), e1);
            return false;
        }
        try {
            // String userPassword = "pippo" + ":" + "pippo";
            String userPassword = username + ":" + password;
            String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
            URLConnection uc = url.openConnection();
            uc.setRequestProperty("Authorization", "Basic " + encoding);
            InputStream content = (InputStream) uc.getInputStream();
            BufferedReader in0 = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = in0.readLine()) != null) {
                // pw.println (line);
                logger.debug(line);
            }
        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
            return false;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
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
    private boolean checkLayerIsInWorkspace(String baseURL, String workspace, String layerName) {
        boolean res = false;

        try {
            String remoteURL = baseURL + (!baseURL.endsWith("/") ? "/" : "") + "rest/layers/"
                    + layerName + ".json";
            String jsonTxt = getRemoteJsonString(remoteURL, null);

            if (jsonTxt != null && jsonTxt.length() > 0) {
                JSONObject json = (JSONObject) JSONSerializer.toJSON(jsonTxt);
                JSONObject layersRoot = json.getJSONObject("layer");

                if (layersRoot != null) {
                    JSONObject resource = layersRoot.getJSONObject("resource");

                    if (resource != null) {
                        String href = resource.getString("href");

                        if (href.contains("workspaces/" + workspace + "/")) {
                            res = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            res = false;
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
    public List<LayerStyle> getStyles(Rule rule) throws ApplicationException {

        List<LayerStyle> layerStyles = new ArrayList<LayerStyle>();

        Set<String> allowedStyles = null;
        int size = 0;

        try {
            LayerDetails layerDetails = georepoRemoteService.getRuleAdminService()
                    .get(rule.getId()).getLayerDetails();

            if (layerDetails != null) {
                allowedStyles = layerDetails.getAllowedStyles();

                if (allowedStyles != null) {
                    size = allowedStyles.size();
                }
            }

            GeoServerRESTReader gsreader = new GeoServerRESTReader(rule.getInstance().getBaseURL(),
                    rule.getInstance().getUsername(), rule.getInstance().getPassword());

            RESTStyleList styles = gsreader.getStyles();
            List<String> names = styles.getNames();
            Iterator<String> iterator = names.iterator();

            while (iterator.hasNext()) {
                String name = iterator.next();

                LayerStyle layerStyle = new LayerStyle();

                if (size > 0) {
                    Iterator<String> styleIterator = allowedStyles.iterator();
                    while (styleIterator.hasNext()) {
                        String allowed = styleIterator.next();

                        if (allowed.equalsIgnoreCase(name)) {
                            layerStyle.setEnabled(true);
                        }
                    }
                } else
                    layerStyle.setEnabled(false);

                layerStyle.setStyle(name);

                layerStyles.add(layerStyle);
            }

        } catch (MalformedURLException e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new ApplicationException(e.getLocalizedMessage(), e);
        } catch (ResourceNotFoundFault e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new ApplicationException(e.getLocalizedMessage(), e);
        }

        return layerStyles;
    }

}
