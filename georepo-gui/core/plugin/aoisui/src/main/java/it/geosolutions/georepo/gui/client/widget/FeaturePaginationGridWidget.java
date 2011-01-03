/*
 * $Header: it.geosolutions.georepo.gui.client.widget.tab.GeoRSSTabItem,v. 0.1 09/lug/2010 10.23.26 created by frank $
 * $Revision: 0.1 $
 * $Date: 09/lug/2010 10.23.26 $
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

import java.util.ArrayList;
import java.util.List;

import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.Feature;
import it.geosolutions.georepo.gui.client.model.Feature.FeatureKeyValue;
import it.geosolutions.georepo.gui.client.service.FeatureServiceRemoteAsync;
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

public class FeaturePaginationGridWidget extends DGWATCHGridWidget<Feature> {

	private FeatureServiceRemoteAsync service;
	private RpcProxy<PagingLoadResult<Feature>> proxy;
	private PagingLoader<PagingLoadResult<ModelData>> loader;
	private PagingToolBar toolBar;

	private long userId;

	/**
	 * 
	 */
	public FeaturePaginationGridWidget(FeatureServiceRemoteAsync service) {
		super();
		this.service = service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#
	 * setGridProperties ()
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
	 * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#
	 * prepareColumnModel()
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
	 * @see
	 * it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#createStore
	 * ()
	 */
	@Override
	public void createStore() {
		toolBar = new PagingToolBar(25);

		this.proxy = new RpcProxy<PagingLoadResult<Feature>>() {

			@Override
			protected void load(Object loadConfig,
					AsyncCallback<PagingLoadResult<Feature>> callback) {
				service.getUserFeatures((PagingLoadConfig) loadConfig, userId,
						callback);
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
	 * @return the loader
	 */
	public PagingLoader<PagingLoadResult<ModelData>> getLoader() {
		return loader;
	}

	/**
	 * @return the toolBar
	 */
	public PagingToolBar getToolBar() {
		return toolBar;
	}

	/**
	 * @return long
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * 
	 */
	public void clearGridElements() {
		this.store.removeAll();
		this.toolBar.clear();
		this.toolBar.disable();
	}

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
					Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
							new String[] {
									I18nProvider.getMessages().featureServiceName(),
									I18nProvider.getMessages().foundLabel() + " " + result.getData().size() + " "
											+ message });
				} else {
					Dispatcher.forwardEvent(DGWATCHEvents.SEND_ALERT_MESSAGE,
							new String[] { I18nProvider.getMessages().featureServiceName(),
									I18nProvider.getMessages().featureNotFoundMessage() });
				}
			}

		});
	}
}
