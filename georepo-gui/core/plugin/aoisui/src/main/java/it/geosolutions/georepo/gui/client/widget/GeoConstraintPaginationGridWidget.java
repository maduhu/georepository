/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.GeoConstraintPaginationGridWidget,v. 0.1 3-gen-2011 16.52.55 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.GeoConstraint;
import it.geosolutions.georepo.gui.client.service.MembersRemoteAsync;

import java.util.ArrayList;
import java.util.List;

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
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class GeoConstraintPaginationGridWidget.
 */
public class GeoConstraintPaginationGridWidget extends DGWATCHGridWidget<GeoConstraint> {

    /** The service. */
    private MembersRemoteAsync service;

    /** The proxy. */
    private RpcProxy<PagingLoadResult<GeoConstraint>> proxy;

    /** The loader. */
    private PagingLoader<PagingLoadResult<ModelData>> loader;

    /** The tool bar. */
    private PagingToolBar toolBar;

    /** The connect id. */
    private String connectId;

    /**
     * Instantiates a new geo constraint pagination grid widget.
     * 
     * @param service
     *            the service
     */
    public GeoConstraintPaginationGridWidget(MembersRemoteAsync service) {
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
        this.grid.setAutoExpandColumn(GeoConstraint.GeoConstraintKeyValue.GEOCONSTRAINT.getValue());

        this.grid.addListener(Events.CellDoubleClick, new Listener<BaseEvent>() {

            public void handleEvent(BaseEvent be) {
                Dispatcher.forwardEvent(DGWATCHEvents.BIND_SELECTED_GEOCONSTRAINT, grid
                        .getSelectionModel().getSelectedItem());
            }
        });

        this.grid.setLoadMask(true);
        this.grid.setAutoWidth(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget# prepareColumnModel()
     */
    @Override
    public ColumnModel prepareColumnModel() {
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

        // the name of the GeoConstraint
        ColumnConfig columnConfig = new ColumnConfig();
        columnConfig.setId(GeoConstraint.GeoConstraintKeyValue.NAME.getValue());
        columnConfig.setHeader(I18nProvider.getMessages().geoConstraintNameColumnLabel());
        columnConfig.setWidth(200);
        configs.add(columnConfig);

        // the WKT for the GeoConstraint
        columnConfig = new ColumnConfig();
        columnConfig.setId(GeoConstraint.GeoConstraintKeyValue.GEOCONSTRAINT.getValue());
        columnConfig.setHeader(I18nProvider.getMessages().geoConstraintWktColumnLabel());
        // columnConfig.setWidth(100);
        TextField lf = new TextField();
        lf.setEnabled(true);
        lf.disableTextSelection(false);
        lf.setReadOnly(true);
        CellEditor cellEditor = new CellEditor(lf);
        columnConfig.setEditor(cellEditor);
        configs.add(columnConfig);

        // the Delete button column
        GridCellRenderer<GeoConstraint> buttonRenderer = new GridCellRenderer<GeoConstraint>() {

            private boolean init;

            public Object render(final GeoConstraint model, String property, ColumnData config,
                    final int rowIndex, final int colIndex, ListStore<GeoConstraint> store,
                    final Grid<GeoConstraint> grid) {
                if (!init) {
                    init = true;
                    grid.addListener(Events.ColumnResize, new Listener<GridEvent<GeoConstraint>>() {

                        public void handleEvent(GridEvent<GeoConstraint> be) {
                            for (int i = 0; i < be.getGrid().getStore().getCount(); i++) {
                                if (be.getGrid().getView().getWidget(i, be.getColIndex()) != null
                                        && be.getGrid().getView().getWidget(i, be.getColIndex()) instanceof BoxComponent) {
                                    ((BoxComponent) be.getGrid().getView().getWidget(i,
                                            be.getColIndex())).setWidth(be.getWidth() - 10);
                                }
                            }
                        }
                    });
                }

                Button b = new Button(I18nProvider.getMessages().geoConstraintUnbindButtonLabel(),
                        new SelectionListener<ButtonEvent>() {
                            @Override
                            public void componentSelected(ButtonEvent ce) {
                                // GEOREPO REFACT
                                // service.removeMemberGeoConstraint(connectId, model, new
                                // AsyncCallback<Void>() {
                                // public void onFailure(Throwable caught) {
                                // Dispatcher.forwardEvent(
                                // DGWATCHEvents.SEND_ERROR_MESSAGE,
                                // new String[] {
                                // I18nProvider.getMessages().memberServiceName(),
                                // "There was an error unbinding a GeoConstraint " + model.getId() +
                                // " from  member "
                                // + connectId});
                                // }
                                //
                                // public void onSuccess(Void x) {
                                // grid.getStore().remove(model);
                                // Dispatcher.forwardEvent(DGWATCHEvents.MEMBER_GEOCONSTRAINT_UNBOUND,
                                // model);
                                // }
                                //
                                // });
                            }
                        });
                b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
                b.setToolTip("Click for more information");

                return b;
            }
        };

        columnConfig = new ColumnConfig();
        // columnConfig.setId("symbol");
        columnConfig.setHeader("");
        columnConfig.setWidth(130);
        columnConfig.setRenderer(buttonRenderer);
        configs.add(columnConfig);

        return new ColumnModel(configs);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#createStore ()
     */
    @Override
    public void createStore() {
        this.toolBar = new PagingToolBar(25);

        this.proxy = new RpcProxy<PagingLoadResult<GeoConstraint>>() {

            @Override
            protected void load(Object loadConfig,
                    AsyncCallback<PagingLoadResult<GeoConstraint>> callback) {
                // GEOREPO REFACT
                // service.getMemberGeoConstraints((PagingLoadConfig) loadConfig, connectId,
                // callback);
            }
        };

        this.loader = new BasePagingLoader<PagingLoadResult<ModelData>>(this.proxy);

        this.loader.setRemoteSort(false);

        this.store = new ListStore<GeoConstraint>(this.loader);

        this.toolBar.bind(this.loader);

        this.toolBar.disable();

        setUpLoadListener();
    }

    /**
     * Gets the loader.
     * 
     * @return the loader
     */
    public PagingLoader<PagingLoadResult<ModelData>> getLoader() {
        return this.loader;
    }

    /**
     * Gets the tool bar.
     * 
     * @return the tool bar
     */
    public PagingToolBar getToolBar() {
        return this.toolBar;
    }

    /**
     * Gets the connect id.
     * 
     * @return the connect id
     */
    public String getConnectId() {
        return this.connectId;
    }

    /**
     * Sets the connect id.
     * 
     * @param connectId
     *            the new connect id
     */
    public void setConnectId(String connectId) {
        this.connectId = connectId;
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
     * Removes the geo constraint.
     * 
     * @param gc
     *            the gc
     */
    public void removeGeoConstraint(GeoConstraint gc) {
        int index = this.store.indexOf(gc);
        if (index != -1)
            this.store.remove(gc);
    }

    /**
     * Update geo constraint title.
     * 
     * @param gc
     *            the gc
     */
    public void updateGeoConstraintTitle(GeoConstraint gc) {
        int index = this.store.indexOf(gc);
        if (index != -1) {
            this.store.remove(gc);
            this.store.insert(gc, index);
        }
    }

    /**
     * Update geo constraint status.
     * 
     * @param gc
     *            the gc
     */
    public void updateGeoConstraintStatus(GeoConstraint gc) {
        int index = this.store.indexOf(gc);
        if (index != -1) {
            this.store.remove(gc);
            this.store.insert(gc, index);
        }
    }

    /**
     * Removes the geo constraint.
     * 
     * @param gcId
     *            the gc id
     */
    public void removeGeoConstraint(Integer gcId) {
        for (GeoConstraint gc : this.store.getModels()) {
            if (gc.getId().equals(gcId)) {
                this.store.remove(gc);
                break;
            }
        }
    }

    /**
     * Sets the up load listener.
     */
    private void setUpLoadListener() {
        this.loader.addLoadListener(new LoadListener() {

            @Override
            public void loaderBeforeLoad(LoadEvent le) {
                if (!toolBar.isEnabled())
                    toolBar.enable();
            }

            @Override
            public void loaderLoad(LoadEvent le) {
                BasePagingLoadResult<?> result = le.getData();
                if (!result.getData().isEmpty()) {
                    // store.update((GeoConstraint) result.getData().get(0));
                    int size = result.getData().size();
                    String message = "";
                    if (size == 1)
                        message = I18nProvider.getMessages().geoConstraintLabel();
                    else
                        message = I18nProvider.getMessages().geoConstraintPluralLabel();
                    Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE, new String[] {
                            I18nProvider.getMessages().memberServiceName(),
                            I18nProvider.getMessages().foundLabel() + " " + result.getData().size()
                                    + " " + message });
                } else {
                    Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE, new String[] {
                            I18nProvider.getMessages().memberServiceName(),
                            I18nProvider.getMessages().geoConstraintsNotFoundMessage() });
                }
            }

        });
    }

    /**
     * Load data.
     */
    public void loadData() {
        this.loader.load(0, 25);
    }
}