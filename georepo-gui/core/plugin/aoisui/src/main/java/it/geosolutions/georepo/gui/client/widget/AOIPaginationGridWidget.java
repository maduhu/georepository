/*
 * $Header: it.geosolutions.georepo.gui.client.widget.AOIPaginationGridWidget,v. 0.1 20/lug/2010 10.56.09 created by frank $
 * $Revision: 0.1 $
 * $Date: 20/lug/2010 10.56.09 $
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
import it.geosolutions.georepo.gui.client.model.AOI;
import it.geosolutions.georepo.gui.client.model.AOI.AOIKeyValue;
import it.geosolutions.georepo.gui.client.service.AOIServiceRemoteAsync;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.LoadListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.BoxComponent;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author frank
 * 
 */
public class AOIPaginationGridWidget extends DGWATCHGridWidget<AOI> {

	private AOIServiceRemoteAsync service;
	private RpcProxy<PagingLoadResult<AOI>> proxy;
	private PagingLoader<PagingLoadResult<ModelData>> loader;
	private PagingToolBar toolBar;

	private CheckColumnConfig checkColumn;

	private long userId;

	/**
	 * 
	 */
	public AOIPaginationGridWidget(AOIServiceRemoteAsync service) {
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
		grid.setAutoExpandColumn(AOIKeyValue.TITLE.getValue());

		grid.addListener(Events.CellDoubleClick, new Listener<BaseEvent>() {

			public void handleEvent(BaseEvent be) {
				Dispatcher.forwardEvent(DGWATCHEvents.BIND_SELECTED_AOI, grid
						.getSelectionModel().getSelectedItem());
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

		ColumnConfig titleColumn = new ColumnConfig();
		titleColumn.setId(AOIKeyValue.TITLE.getValue());
		titleColumn.setHeader(I18nProvider.getMessages().aoiTitleLabel());
		titleColumn.setWidth(100);
		configs.add(titleColumn);

		checkColumn = new CheckColumnConfig(AOIKeyValue.SHARED.getValue(),
                I18nProvider.getMessages().aoiSharedLabel(), 80);

		CellEditor checkBoxEditor = new CellEditor(new CheckBox());
		checkBoxEditor.setEnabled(false);

		this.checkColumn.setEditor(checkBoxEditor);

		this.checkColumn.setSortable(false);
		this.checkColumn.setResizable(false);
		this.checkColumn.setMenuDisabled(true);

		configs.add(this.checkColumn);

		ColumnConfig features = new ColumnConfig();
		features.setHeader(I18nProvider.getMessages().aoiFeatureListLabel());
		features.setWidth(100);
		features.setRenderer(this.createButtonRendered());
		features.setMenuDisabled(true);
		features.setSortable(false);

		configs.add(features);

		return new ColumnModel(configs);
	}

	private GridCellRenderer<AOI> createButtonRendered() {
		// TODO Auto-generated method stub
		GridCellRenderer<AOI> buttonRendered = new GridCellRenderer<AOI>() {

			private boolean init;

			public Object render(final AOI model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<AOI> store, Grid<AOI> grid) {
				// TODO Auto-generated method stub
				if (!init) {
					init = true;
					grid.addListener(Events.ColumnResize,
							new Listener<GridEvent<AOI>>() {

								public void handleEvent(GridEvent<AOI> be) {
									for (int i = 0; i < be.getGrid().getStore()
											.getCount(); i++) {
										if (be.getGrid().getView()
												.getWidget(i, be.getColIndex()) != null
												&& be.getGrid()
														.getView()
														.getWidget(
																i,
																be.getColIndex()) instanceof BoxComponent) {
											((BoxComponent) be
													.getGrid()
													.getView()
													.getWidget(i,
															be.getColIndex()))
													.setWidth(be.getWidth() - 10);
										}
									}
								}
							});
				}

				Button b = new Button("Get Features",
						new SelectionListener<ButtonEvent>() {

							@Override
							public void componentSelected(ButtonEvent ce) {
								Dispatcher.forwardEvent(
										DGWATCHEvents.SEND_ALERT_MESSAGE,
										new String[] { "Get Features",
												"Action must be implemented" });
							}
						});
				b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
				b.setToolTip(model.getTitle() + " Features");

				return b;
			}
		};
		return buttonRendered;
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

		this.proxy = new RpcProxy<PagingLoadResult<AOI>>() {

			@Override
			protected void load(Object loadConfig,
					AsyncCallback<PagingLoadResult<AOI>> callback) {
//				service.getUserAOIs((PagingLoadConfig) loadConfig, userId,
//						callback);
			}
		};

		loader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);

		loader.setRemoteSort(false);

		store = new ListStore<AOI>(loader);

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

	public void removeAOI(AOI aoi) {
		int index = this.store.indexOf(aoi);
		if (index != -1)
			this.store.remove(aoi);
	}

	public void updateAOITitle(AOI aoi) {
		int index = this.store.indexOf(aoi);
		if (index != -1) {
			this.store.remove(aoi);
			this.store.insert(aoi, index);
		}
	}

	public void updateAOIStatus(AOI aoi) {
		int index = this.store.indexOf(aoi);
		if (index != -1) {
			this.store.remove(aoi);
			this.store.insert(aoi, index);
		}
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
						message = I18nProvider.getMessages().aoiAbbreviatedLabel();
					else
						message = I18nProvider.getMessages().aoiAbbreviatedPluralLabel();
					Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
							new String[] {
									I18nProvider.getMessages().aoiServiceName(),
									I18nProvider.getMessages().foundLabel() + " " + result.getData().size() + " "
											+ message });
				} else {
					Dispatcher.forwardEvent(DGWATCHEvents.SEND_ALERT_MESSAGE,
							new String[] { I18nProvider.getMessages().aoiServiceName(),
                                    I18nProvider.getMessages().aoiNotFoundMessage() });
				}
			}

		});
	}
}
