/*
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
import it.geosolutions.georepo.gui.client.model.ExecutedClientShortWatch;
import it.geosolutions.georepo.gui.client.model.ExecutedClientShortWatch.ExecutedShortWatchKeyValue;
import it.geosolutions.georepo.gui.client.service.WatchServiceRemoteAsync;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.LoadListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Tobia di Pisa
 *
 */
public class ExecutedWatchesPaginationGridWidget  extends DGWATCHGridWidget<ExecutedClientShortWatch> {

	private WatchServiceRemoteAsync service;
	private RpcProxy<PagingLoadResult<ExecutedClientShortWatch>> proxy;
	private PagingLoader<PagingLoadResult<ModelData>> loader;
	private PagingToolBar toolBar;

	/**
	 * 
	 */
	public ExecutedWatchesPaginationGridWidget(WatchServiceRemoteAsync service) {
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
		grid.setAutoExpandColumn(ExecutedShortWatchKeyValue.LOG_MSG.getValue());

		grid.addListener(Events.CellDoubleClick, new Listener<BaseEvent>() {

			public void handleEvent(BaseEvent be) {
				
			}
		});

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

		ColumnConfig logDate = new ColumnConfig();
		logDate.setId(ExecutedShortWatchKeyValue.LOG_DATE.getValue());
		logDate.setHeader("Log Date");
		logDate.setWidth(100);
		configs.add(logDate);
		
		ColumnConfig id = new ColumnConfig();
		id.setId(ExecutedShortWatchKeyValue.ID.getValue());
		id.setHeader("Watch Id");
		id.setWidth(100);
		configs.add(id);

		ColumnConfig title = new ColumnConfig();
		title.setId(ExecutedShortWatchKeyValue.TITLE.getValue());
		title.setHeader("Watch Title");
		title.setWidth(100);
		configs.add(title);

		ColumnConfig logType = new ColumnConfig();
		logType.setId(ExecutedShortWatchKeyValue.LOG_TYPE.getValue());
		logType.setHeader("Log Type");
		logType.setWidth(100);
		configs.add(logType);
		
		ColumnConfig logMsg = new ColumnConfig();
		logMsg.setId(ExecutedShortWatchKeyValue.LOG_MSG.getValue());
		logMsg.setHeader("Log Message");
		logMsg.setWidth(100);
		configs.add(logMsg);
		
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

		this.proxy = new RpcProxy<PagingLoadResult<ExecutedClientShortWatch>>() {

			@Override
			protected void load(Object loadConfig,
					AsyncCallback<PagingLoadResult<ExecutedClientShortWatch>> callback) {
				service.getExecutedWatches((PagingLoadConfig)loadConfig, callback);
			}
		};

		loader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);

		loader.setRemoteSort(false);

		store = new ListStore<ExecutedClientShortWatch>(loader);

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
				if (!toolBar.isEnabled()){
					toolBar.enable();
				}					
			}

			@Override
			public void loaderLoad(LoadEvent le) {
				BasePagingLoadResult<?> result = le.getData();
				if (result.getData().size() > 0) {
					int size = result.getData().size();
					String message = "";
					if (size == 1)
						message = I18nProvider.getMessages().watchAbbreviatedLabel();
					else
						message = I18nProvider.getMessages().watchAbbreviatedPluralLabel();
					Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
							new String[] {
									I18nProvider.getMessages().watchServiceName(),
									I18nProvider.getMessages().foundLabel() + " " + result.getData().size() + " "
											+ message });
					
					if (!toolBar.isEnabled()){
						toolBar.enable();
					}	
					
				} else {
					Dispatcher.forwardEvent(DGWATCHEvents.SEND_ALERT_MESSAGE,
							new String[] { I18nProvider.getMessages().watchServiceName(),
                                    I18nProvider.getMessages().watchNotFoundMessage() });
				}
			}

		});
	}
	
}
