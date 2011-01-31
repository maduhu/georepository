/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.ProfileGridWidget,v. 0.1 25-gen-2011 11.23.48 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.23.48 $
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
import it.geosolutions.georepo.gui.client.Resources;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.BeanKeyValue;
import it.geosolutions.georepo.gui.client.model.Profile;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemoteAsync;

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
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.LoadListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.widget.BoxComponent;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class ProfileGridWidget.
 */
public class ProfileGridWidget extends GeoRepoGridWidget<Profile> {

    /** The service. */
    private ProfilesManagerServiceRemoteAsync service;

    /** The proxy. */
    private RpcProxy<PagingLoadResult<Profile>> proxy;

    /** The loader. */
    private PagingLoader<PagingLoadResult<ModelData>> loader;

    /** The tool bar. */
    private PagingToolBar toolBar;

    /**
     * Instantiates a new profile grid widget.
     * 
     * @param service
     *            the service
     */
    public ProfileGridWidget(ProfilesManagerServiceRemoteAsync service) {
        super();
        this.service = service;
    }

    /**
     * Instantiates a new profile grid widget.
     * 
     * @param models
     *            the models
     */
    public ProfileGridWidget(List<Profile> models) {
        super(models);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#setGridProperties ()
     */
    @Override
    public void setGridProperties() {
        // grid.setAutoExpandColumn(BeanKeyValue.NAME.getValue());
        // grid.addPlugin(emailEnable);
        // grid.addPlugin(rssEnable);

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

        ColumnConfig userNameColumn = new ColumnConfig();
        userNameColumn.setId(BeanKeyValue.NAME.getValue());
        userNameColumn.setHeader("User Name");
        userNameColumn.setWidth(100);
        configs.add(userNameColumn);

        ColumnConfig dateCreationColumn = new ColumnConfig();
        dateCreationColumn.setId(BeanKeyValue.DATE_CREATION.getValue());
        dateCreationColumn.setHeader("Date Creation");
        dateCreationColumn.setWidth(180);
        configs.add(dateCreationColumn);

        ColumnConfig profileEnabledColumn = new ColumnConfig();
        profileEnabledColumn.setId(BeanKeyValue.PROFILE_ENABLED.getValue());
        profileEnabledColumn.setHeader("Enabled");
        profileEnabledColumn.setWidth(80);
        profileEnabledColumn.setRenderer(this.createEnableCheckBox());
        profileEnabledColumn.setMenuDisabled(true);
        profileEnabledColumn.setSortable(false);
        configs.add(profileEnabledColumn);

        ColumnConfig removeActionColumn = new ColumnConfig();
        removeActionColumn.setId("removeProfile");
        // removeActionColumn.setHeader("Remove");
        removeActionColumn.setWidth(80);
        removeActionColumn.setRenderer(this.createProfileDeleteButton());
        removeActionColumn.setMenuDisabled(true);
        removeActionColumn.setSortable(false);
        configs.add(removeActionColumn);

        return new ColumnModel(configs);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#createStore()
     */
    @Override
    public void createStore() {
        this.toolBar = new PagingToolBar(25);

        // Loader fro service
        this.proxy = new RpcProxy<PagingLoadResult<Profile>>() {

            @Override
            protected void load(Object loadConfig, AsyncCallback<PagingLoadResult<Profile>> callback) {
                service.getProfiles((PagingLoadConfig) loadConfig, false, callback);
            }

        };
        loader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);
        loader.setRemoteSort(false);
        store = new ListStore<Profile>(loader);
        //store.sort(BeanKeyValue.NAME.getValue(), SortDir.ASC);

        // Search tool
        SearchFilterField<Profile> filter = new SearchFilterField<Profile>() {

            @Override
            protected boolean doSelect(Store<Profile> store, Profile parent, Profile record,
                    String property, String filter) {

                String name = parent.get("name");
                name = name.toLowerCase();
                if (name.indexOf(filter.toLowerCase()) != -1) {
                    return true;
                }
                return false;
            }

        };
        filter.setWidth(130);
        filter.setIcon(Resources.ICONS.search());
        // Bind the filter field to your grid store (grid.getStore())
        filter.bind(store);

        // Add Profile button
        // TODO: generalize this!
        Button addProfileButton = new Button("Add Profile");
        addProfileButton.setIcon(Resources.ICONS.add());
        // TODO: temporally disabled!
        addProfileButton.setEnabled(false);

        addProfileButton.addListener(Events.OnClick, new Listener<ButtonEvent>() {

            public void handleEvent(ButtonEvent be) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                        "GeoServer Profile", "Add Profile" });
            }
        });

        this.toolBar.bind(loader);
        this.toolBar.add(new SeparatorToolItem());
        this.toolBar.add(addProfileButton);
        this.toolBar.add(new SeparatorToolItem());
        this.toolBar.add(filter);
        this.toolBar.add(new SeparatorToolItem());

        // this.toolBar.disable();

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
                if (!toolBar.isEnabled())
                    toolBar.enable();
            }

            @Override
            public void loaderLoad(LoadEvent le) {

                // TODO: change messages here!!

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

    /**
     * Creates the enable check box.
     * 
     * @return the grid cell renderer
     */
    private GridCellRenderer<Profile> createEnableCheckBox() {

        GridCellRenderer<Profile> buttonRendered = new GridCellRenderer<Profile>() {

            private boolean init;

            public Object render(final Profile model, String property, ColumnData config,
                    int rowIndex, int colIndex, ListStore<Profile> store, Grid<Profile> grid) {

                if (!init) {
                    init = true;
                    grid.addListener(Events.ColumnResize, new Listener<GridEvent<Profile>>() {

                        public void handleEvent(GridEvent<Profile> be) {
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

                CheckBox profileEnabledButton = new CheckBox();
                // TODO: add correct tooltip text here!
                //profileEnabledButton.setToolTip("Test");
                // TODO: Read only mode in this version.
                profileEnabledButton.setReadOnly(true);
                
                profileEnabledButton.setValue(model.isEnabled());

                profileEnabledButton.addListener(Events.OnClick, new Listener<FieldEvent>() {

                    public void handleEvent(FieldEvent be) {
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "GeoServer Profile", "Enable check!" });
                    }
                });

                return profileEnabledButton;
            }
        };

        return buttonRendered;
    }

    /**
     * Creates the profile delete button.
     * 
     * @return the grid cell renderer
     */
    private GridCellRenderer<Profile> createProfileDeleteButton() {
        GridCellRenderer<Profile> buttonRendered = new GridCellRenderer<Profile>() {

            private boolean init;

            public Object render(final Profile model, String property, ColumnData config,
                    int rowIndex, int colIndex, ListStore<Profile> store, Grid<Profile> grid) {

                if (!init) {
                    init = true;
                    grid.addListener(Events.ColumnResize, new Listener<GridEvent<Profile>>() {

                        public void handleEvent(GridEvent<Profile> be) {
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

                // TODO: generalize this!
                Button removeProfileButton = new Button("Remove");
                removeProfileButton.setIcon(Resources.ICONS.delete());
                // TODO: temporally disabled!
                removeProfileButton.setEnabled(false);

                removeProfileButton.addListener(Events.OnClick, new Listener<ButtonEvent>() {

                    public void handleEvent(ButtonEvent be) {
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "GeoServer Profile", "Remove Profile: " + model.getName() });
                    }
                });

                return removeProfileButton;
            }

        };

        return buttonRendered;
    }

}