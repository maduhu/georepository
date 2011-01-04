/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.AOIUserPrefWidget,v. 0.1 3-gen-2011 17.06.54 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 17.06.54 $
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
package it.geosolutions.georepo.gui.client.widget;

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.AOI;
import it.geosolutions.georepo.gui.client.model.BeanKeyValue;
import it.geosolutions.georepo.gui.client.model.User;
import it.geosolutions.georepo.gui.client.service.LoginRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.SearchStatus.EnumSearchStatus;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.LoadListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class AOIUserPrefWidget.
 */
public class AOIUserPrefWidget extends DGWATCHGridWidget<User> {

    /** The parent. */
    private AOIUserPrefContainer parent;

    /** The service. */
    private LoginRemoteAsync service;

    /** The proxy. */
    private RpcProxy<PagingLoadResult<User>> proxy;

    /** The loader. */
    private PagingLoader<PagingLoadResult<ModelData>> loader;

    /** The tool bar. */
    private PagingToolBar toolBar;

    /** The aoi. */
    private AOI aoi;

    /**
     * Instantiates a new aOI user pref widget.
     * 
     * @param service
     *            the service
     * @param parent
     *            the parent
     */
    public AOIUserPrefWidget(LoginRemoteAsync service, AOIUserPrefContainer parent) {
        super();
        this.service = service;
        this.parent = parent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#setGridProperties()
     */
    @Override
    public void setGridProperties() {
        // TODO Auto-generated method stub
        grid.setAutoExpandColumn(BeanKeyValue.USER_NAME.getValue());

        grid.setWidth(380);
        grid.setHeight(270);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#prepareColumnModel()
     */
    @Override
    public ColumnModel prepareColumnModel() {
        // TODO Auto-generated method stub
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

        ColumnConfig userNameColumn = new ColumnConfig();
        userNameColumn.setId(BeanKeyValue.USER_NAME.getValue());
        userNameColumn.setHeader("User Name");
        userNameColumn.setWidth(80);
        configs.add(userNameColumn);

        ColumnConfig emailAddress = new ColumnConfig();
        emailAddress.setId(BeanKeyValue.EMAIL.getValue());
        emailAddress.setHeader("Email");
        emailAddress.setWidth(120);
        configs.add(emailAddress);

        return new ColumnModel(configs);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#createStore()
     */
    @Override
    public void createStore() {
        // TODO Auto-generated method stub
        toolBar = new PagingToolBar(25);

        this.proxy = new RpcProxy<PagingLoadResult<User>>() {

            @Override
            protected void load(Object loadConfig, AsyncCallback<PagingLoadResult<User>> callback) {

                // TODO REFACTOR GG
                // service.getRelatedUsers((PagingLoadConfig) loadConfig,
                // aoi.getId(), callback);
            }
        };

        loader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);
        loader.setRemoteSort(false);

        store = new ListStore<User>(loader);

        this.toolBar.bind(loader);

        setUpLoadListener();
    }

    /**
     * Sets the up load listener.
     */
    private void setUpLoadListener() {
        loader.addLoadListener(new LoadListener() {

            @Override
            public void loaderBeforeLoad(LoadEvent le) {
                parent.setBusy("Connection to the Server");
            }

            @Override
            public void loaderLoad(LoadEvent le) {
                parent.setSearchStatus(EnumSearchStatus.STATUS_SEARCH,
                        EnumSearchStatus.STATUS_MESSAGE_SEARCH);
                // toolBar.enable();
            }

            @Override
            public void loaderLoadException(LoadEvent le) {
                clearGridElements();
                try {
                    throw le.exception;
                } catch (ApplicationException e) {
                    parent.setSearchStatus(EnumSearchStatus.STATUS_NO_SEARCH,
                            EnumSearchStatus.STATUS_MESSAGE_NOT_SEARCH);
                } catch (Throwable e) {
                    // TODO Auto-generated catch block
                    parent.setSearchStatus(EnumSearchStatus.STATUS_SEARCH_ERROR,
                            EnumSearchStatus.STATUS_MESSAGE_SEARCH_ERROR);
                }
            }

        });
    }

    /**
     * Clear grid elements.
     */
    public void clearGridElements() {
        this.store.removeAll();
        this.toolBar.clear();
    }

    /**
     * Gets the aoi.
     * 
     * @return the aoi
     */
    public AOI getAoi() {
        return aoi;
    }

    /**
     * Sets the aoi.
     * 
     * @param aoi
     *            the new aoi
     */
    public void setAoi(AOI aoi) {
        this.aoi = aoi;
    }

    /**
     * Gets the loader.
     * 
     * @return the loader
     */
    public PagingLoader<PagingLoadResult<ModelData>> getLoader() {
        return loader;
    }

    /**
     * Gets the tool bar.
     * 
     * @return the tool bar
     */
    public PagingToolBar getToolBar() {
        return toolBar;
    }
}
