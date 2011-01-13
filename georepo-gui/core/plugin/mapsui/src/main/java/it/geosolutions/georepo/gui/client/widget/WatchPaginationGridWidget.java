/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.WatchPaginationGridWidget,v. 0.1 3-gen-2011 16.52.55 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 16.52.55 $
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

import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.Resources;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.ClientShortWatch;
import it.geosolutions.georepo.gui.client.model.ClientShortWatch.ShortWatchKeyValue;
import it.geosolutions.georepo.gui.client.service.WatchServiceRemoteAsync;

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
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.LoadListener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.BoxComponent;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class WatchPaginationGridWidget.
 */
public class WatchPaginationGridWidget extends GeoRepoGridWidget<ClientShortWatch> {

    /** The service. */
    private final WatchServiceRemoteAsync service;

    /** The proxy. */
    private RpcProxy<PagingLoadResult<ClientShortWatch>> proxy;

    /** The loader. */
    private PagingLoader<PagingLoadResult<ModelData>> loader;

    /** The tool bar. */
    private PagingToolBar toolBar;

    /** The member name. */
    private String memberName;

    /**
     * Instantiates a new watch pagination grid widget.
     * 
     * @param service
     *            the service
     */
    public WatchPaginationGridWidget(WatchServiceRemoteAsync service) {
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
        grid.setAutoExpandColumn(ShortWatchKeyValue.TITLE.getValue());

        grid.addListener(Events.CellDoubleClick, new Listener<BaseEvent>() {

            public void handleEvent(BaseEvent be) {
                Dispatcher.forwardEvent(GeoRepoEvents.BINDING_WATCH_WIDGET, grid
                        .getSelectionModel().getSelectedItem());
            }
        });

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

        ColumnConfig title = new ColumnConfig();
        title.setId(ShortWatchKeyValue.TITLE.getValue());
        title.setHeader(I18nProvider.getMessages().watchTitleLabel());
        title.setWidth(100);
        configs.add(title);

        ColumnConfig action = new ColumnConfig();
        action.setId(ShortWatchKeyValue.ACTION.getValue());
        action.setHeader(I18nProvider.getMessages().watchActionLabel());
        action.setWidth(100);
        configs.add(action);

        ColumnConfig aoiTitle = new ColumnConfig();
        aoiTitle.setId(ShortWatchKeyValue.AOITITLE.getValue());
        aoiTitle.setHeader(I18nProvider.getMessages().watchAoiTitleLabel());
        aoiTitle.setWidth(100);
        configs.add(aoiTitle);

        ColumnConfig triggers = new ColumnConfig();
        triggers.setHeader("Triggers");
        triggers.setWidth(100);
        triggers.setRenderer(this.createTriggerButton());
        triggers.setMenuDisabled(true);
        triggers.setSortable(false);
        configs.add(triggers);

        ColumnConfig delete = new ColumnConfig();
        delete.setHeader("Delete Watch");
        delete.setWidth(100);
        delete.setRenderer(this.createDeleteButton());
        delete.setMenuDisabled(true);
        delete.setSortable(false);
        configs.add(delete);

        return new ColumnModel(configs);
    }

    /**
     * Creates the trigger button.
     * 
     * @return the grid cell renderer
     */
    private GridCellRenderer<ClientShortWatch> createTriggerButton() {

        GridCellRenderer<ClientShortWatch> buttonRendered = new GridCellRenderer<ClientShortWatch>() {

            private boolean init;

            public Object render(final ClientShortWatch model, String property, ColumnData config,
                    int rowIndex, int colIndex, ListStore<ClientShortWatch> store,
                    Grid<ClientShortWatch> grid) {

                if (!init) {
                    init = true;
                    grid.addListener(Events.ColumnResize,
                            new Listener<GridEvent<ClientShortWatch>>() {

                                public void handleEvent(GridEvent<ClientShortWatch> be) {
                                    for (int i = 0; i < be.getGrid().getStore().getCount(); i++) {
                                        if (be.getGrid().getView().getWidget(i, be.getColIndex()) != null
                                                && be.getGrid().getView().getWidget(i,
                                                        be.getColIndex()) instanceof BoxComponent) {
                                            ((BoxComponent) be.getGrid().getView().getWidget(i,
                                                    be.getColIndex())).setWidth(be.getWidth() - 10);
                                        }
                                    }
                                }
                            });
                }

                GridWatchButton trigger = new GridWatchButton("Trigger",
                        new SelectionListener<ButtonEvent>() {

                            @Override
                            public void componentSelected(ButtonEvent ce) {
                                Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
                                        new String[] { "Watch Triggering", "Watch Executed" });

                                GridWatchButton triggerButton = (GridWatchButton) ce.getButton();

                                Dispatcher.forwardEvent(GeoRepoEvents.TRIGGER_WATCH, triggerButton
                                        .getClientShortWatch());
                            }

                        });

                trigger.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
                trigger.setToolTip(model.getTitle() + " Triggering");
                trigger.setClientShortWatch(model);
                trigger.setIcon(Resources.ICONS.trigger());

                return trigger;
            }
        };

        return buttonRendered;
    }

    /**
     * Creates the delete button.
     * 
     * @return the grid cell renderer
     */
    private GridCellRenderer<ClientShortWatch> createDeleteButton() {

        GridCellRenderer<ClientShortWatch> buttonRendered = new GridCellRenderer<ClientShortWatch>() {

            private boolean init;

            public Object render(final ClientShortWatch model, String property, ColumnData config,
                    int rowIndex, int colIndex, ListStore<ClientShortWatch> store,
                    Grid<ClientShortWatch> grid) {

                if (!init) {
                    init = true;
                    grid.addListener(Events.ColumnResize,
                            new Listener<GridEvent<ClientShortWatch>>() {

                                public void handleEvent(GridEvent<ClientShortWatch> be) {
                                    for (int i = 0; i < be.getGrid().getStore().getCount(); i++) {
                                        if (be.getGrid().getView().getWidget(i, be.getColIndex()) != null
                                                && be.getGrid().getView().getWidget(i,
                                                        be.getColIndex()) instanceof BoxComponent) {
                                            ((BoxComponent) be.getGrid().getView().getWidget(i,
                                                    be.getColIndex())).setWidth(be.getWidth() - 10);
                                        }
                                    }
                                }
                            });
                }

                GridWatchButton del = new GridWatchButton("Delete",
                        new SelectionListener<ButtonEvent>() {

                            @Override
                            public void componentSelected(ButtonEvent ce) {
                                Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
                                        new String[] { "Deleting Watch", "Watch Deleted" });

                                final GridWatchButton deleteButton = (GridWatchButton) ce
                                        .getButton();

                                MessageBox.confirm("Delete Watch", "Are you sure to delete Watch "
                                        + deleteButton.getClientShortWatch().getTitle() + " ?",
                                        new Listener<MessageBoxEvent>() {

                                            public void handleEvent(MessageBoxEvent be) {
                                                Button btn = be.getButtonClicked();
                                                if (btn.getText().equalsIgnoreCase("YES")) {
                                                    Dispatcher.forwardEvent(
                                                            GeoRepoEvents.DELETE_WATCH,
                                                            deleteButton.getClientShortWatch());
                                                }
                                            }
                                        });

                            }

                        });

                del.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
                del.setToolTip(model.getTitle() + " Delete");
                del.setClientShortWatch(model);
                del.setIcon(Resources.ICONS.delete());

                return del;
            }
        };

        return buttonRendered;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#createStore ()
     */
    @Override
    public void createStore() {
        toolBar = new PagingToolBar(25);

        this.proxy = new RpcProxy<PagingLoadResult<ClientShortWatch>>() {

            @Override
            protected void load(Object loadConfig,
                    AsyncCallback<PagingLoadResult<ClientShortWatch>> callback) {
                service.getMemberWatches((PagingLoadConfig) loadConfig, memberName, callback);
            }
        };

        loader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);

        loader.setRemoteSort(false);

        store = new ListStore<ClientShortWatch>(loader);

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
                if (!toolBar.isEnabled()) {
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
                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                            I18nProvider.getMessages().watchServiceName(),
                            I18nProvider.getMessages().foundLabel() + " " + result.getData().size()
                                    + " " + message });

                    if (!toolBar.isEnabled()) {
                        toolBar.enable();
                        toolBar.refresh();
                    }

                } else {
                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_ALERT_MESSAGE, new String[] {
                            I18nProvider.getMessages().watchServiceName(),
                            I18nProvider.getMessages().watchNotFoundMessage() });
                }
            }

        });
    }

    /**
     * Gets the member name.
     * 
     * @return the member name
     */
    public String getMemberName() {
        return memberName;
    }

    /**
     * Sets the member name.
     * 
     * @param memberName
     *            the new member name
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#getGrid()
     */
    @Override
    public Grid<ClientShortWatch> getGrid() {
        return super.getGrid();
    }
}
