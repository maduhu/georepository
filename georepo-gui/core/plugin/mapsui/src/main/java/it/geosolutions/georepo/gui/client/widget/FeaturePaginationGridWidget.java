/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.FeaturePaginationGridWidget,v. 0.1 14-gen-2011 19.28.38 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 14-gen-2011 19.28.38 $
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
package it.geosolutions.georepo.gui.client.widget;

import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.Feature;
import it.geosolutions.georepo.gui.client.model.Feature.FeatureKeyValue;
import it.geosolutions.georepo.gui.client.service.FeatureServiceRemoteAsync;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.LoadListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class FeaturePaginationGridWidget.
 */
public class FeaturePaginationGridWidget extends GeoRepoGridWidget<Feature> {

    /** The service. */
    private FeatureServiceRemoteAsync service;

    /** The proxy. */
    private RpcProxy<PagingLoadResult<Feature>> proxy;

    /** The loader. */
    private PagingLoader<PagingLoadResult<ModelData>> loader;

    /** The tool bar. */
    private PagingToolBar toolBar;

    /** The user id. */
    private long userId;

    /**
     * Instantiates a new feature pagination grid widget.
     * 
     * @param service
     *            the service
     */
    public FeaturePaginationGridWidget(FeatureServiceRemoteAsync service) {
        super();
        this.service = service;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget# setGridProperties ()
     */
    @Override
    public void setGridProperties() {
        // grid.setAutoExpandColumn(FeatureKeyValue.LAST_SENT_BY_MAIL.getValue());

        grid.setLoadMask(true);
        grid.setAutoWidth(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget# prepareColumnModel()
     */
    @Override
    public ColumnModel prepareColumnModel() {
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

        ColumnConfig idColumn = new ColumnConfig();
        idColumn.setId(FeatureKeyValue.LAST_SENT_BY_MAIL.getValue());
        idColumn.setHeader(I18nProvider.getMessages().geoRssEmailSentLabel());
        idColumn.setWidth(200);
        configs.add(idColumn);

        ColumnConfig titleColumn = new ColumnConfig();
        titleColumn.setId(FeatureKeyValue.LAST_SENT_BY_RSS.getValue());
        titleColumn.setHeader(I18nProvider.getMessages().geoRssRssSentLabel());
        titleColumn.setWidth(200);
        configs.add(titleColumn);

        ColumnConfig xmlColumn = new ColumnConfig();
        xmlColumn.setId(FeatureKeyValue.WFS_RESPONSE_BLOB.getValue());
        xmlColumn.setHeader(I18nProvider.getMessages().geoRssWfsResponseBlobLabel());
        xmlColumn.setWidth(200);
        configs.add(xmlColumn);

        return new ColumnModel(configs);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#createStore ()
     */
    @Override
    public void createStore() {
        toolBar = new PagingToolBar(25);

        this.proxy = new RpcProxy<PagingLoadResult<Feature>>() {

            @Override
            protected void load(Object loadConfig, AsyncCallback<PagingLoadResult<Feature>> callback) {
                service.getUserFeatures((PagingLoadConfig) loadConfig, userId, callback);
            }
        };

        loader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);

        loader.setRemoteSort(false);

        store = new ListStore<Feature>(loader);

        this.toolBar.bind(loader);

        toolBar.disable();

        setUpLoadListener();
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

    /**
     * Gets the user id.
     * 
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     * 
     * @param userId
     *            the new user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Clear grid elements.
     */
    public void clearGridElements() {
        this.store.removeAll();
        this.toolBar.clear();
        this.toolBar.disable();
    }

    /**
     * Sets the up load listener.
     */
    private void setUpLoadListener() {
        loader.addLoadListener(new LoadListener() {

            @Override
            public void loaderBeforeLoad(LoadEvent le) {
                if (!toolBar.isEnabled())
                    toolBar.enable();
            }

            @Override
            public void loaderLoad(LoadEvent le) {
                BasePagingLoadResult<?> result = le.getData();
                if (!result.getData().isEmpty()) {
                    int size = result.getData().size();
                    String message = "";
                    if (size == 1)
                        message = I18nProvider.getMessages().featureLabel();
                    else
                        message = I18nProvider.getMessages().featurePluralLabel();
                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                            I18nProvider.getMessages().featureServiceName(),
                            I18nProvider.getMessages().foundLabel() + " " + result.getData().size()
                                    + " " + message });
                } else {
                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_ALERT_MESSAGE, new String[] {
                            I18nProvider.getMessages().featureServiceName(),
                            I18nProvider.getMessages().featureNotFoundMessage() });
                }
            }

        });
    }
}
