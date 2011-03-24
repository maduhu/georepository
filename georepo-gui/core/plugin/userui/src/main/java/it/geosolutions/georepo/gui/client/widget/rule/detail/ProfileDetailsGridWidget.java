/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.rule.detail.LayerCustomPropsGridWidget,v. 0.1 8-feb-2011 15.06.43 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 8-feb-2011 15.06.43 $
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
package it.geosolutions.georepo.gui.client.widget.rule.detail;

import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.Resources;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.BeanKeyValue;
import it.geosolutions.georepo.gui.client.model.Profile;
import it.geosolutions.georepo.gui.client.model.data.ProfileCustomProps;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.GeoRepoGridWidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.SortDir;
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
import com.extjs.gxt.ui.client.widget.BoxComponent;
import com.extjs.gxt.ui.client.widget.ComponentManager;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * The Class LayerCustomPropsGridWidget.
 */
public class ProfileDetailsGridWidget extends GeoRepoGridWidget<ProfileCustomProps> {

    /** The profile service. */
    private ProfilesManagerServiceRemoteAsync profilesService;

    /** The proxy. */
    private RpcProxy<PagingLoadResult<ProfileCustomProps>> proxy;

    /** The loader. */
    private PagingLoader<PagingLoadResult<ModelData>> loader;

    /** The tool bar. */
    private PagingToolBar toolBar;
    
    private Profile profile;
    
    /**
     * Instantiates a new profile custom props grid widget.
     * @param theRule 
     * 
     * @param rulesService
     *            the rules service
     */
    public ProfileDetailsGridWidget(Profile profile, ProfilesManagerServiceRemoteAsync profilesService) {
        super();
        this.profile = profile;
        this.profilesService = profilesService;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#setGridProperties ()
     */
    @Override
    public void setGridProperties() {
        grid.setLoadMask(true);
        grid.setAutoWidth(true);
    }
    
    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.client.widget.GeoRepoGridWidget#prepareColumnModel()
     */
    @Override
    public ColumnModel prepareColumnModel() {
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
        
        ColumnConfig profilePropKeyColumn = new ColumnConfig();
        profilePropKeyColumn.setId(BeanKeyValue.PROFILE_PROP_KEY.getValue());
        profilePropKeyColumn.setHeader("Key");
        profilePropKeyColumn.setWidth(210);
        profilePropKeyColumn.setRenderer(this.createPropKeyTextBox());
        profilePropKeyColumn.setMenuDisabled(true);
        profilePropKeyColumn.setSortable(false);
        configs.add(profilePropKeyColumn);

        ColumnConfig profilePropValueColumn = new ColumnConfig();
        profilePropValueColumn.setId(BeanKeyValue.PROFILE_PROP_VALUE.getValue());
        profilePropValueColumn.setHeader("Value");
        profilePropValueColumn.setWidth(260);
        profilePropValueColumn.setRenderer(this.createPropValueTextBox());
        profilePropValueColumn.setMenuDisabled(true);
        profilePropValueColumn.setSortable(false);
        configs.add(profilePropValueColumn);
        
        ColumnConfig removeActionColumn = new ColumnConfig();
        removeActionColumn.setId("removeProfileCustomProp");
        removeActionColumn.setWidth(30);
        removeActionColumn.setRenderer(this.createDeleteButton());
        removeActionColumn.setMenuDisabled(true);
        removeActionColumn.setSortable(false);
        configs.add(removeActionColumn);
        
        return new ColumnModel(configs);
    }
    
    /**
     * 
     * @return
     */
    private GridCellRenderer<ProfileCustomProps> createPropKeyTextBox() {

        GridCellRenderer<ProfileCustomProps> textRendered = new GridCellRenderer<ProfileCustomProps>() {

            private boolean init;

            public Object render(final ProfileCustomProps model, String property, ColumnData config,
                    int rowIndex, int colIndex, ListStore<ProfileCustomProps> store, Grid<ProfileCustomProps> grid) {

                if (!init) {
                    init = true;
                    grid.addListener(Events.ColumnResize, new Listener<GridEvent<ProfileCustomProps>>() {

                        public void handleEvent(GridEvent<ProfileCustomProps> be) {
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

                TextField<String> propKey = new TextField<String>();
                propKey.setWidth(200);
                propKey.setAllowBlank(false);
                propKey.setValue(model.getPropKey());
                
                propKey.addListener(Events.Change, new Listener<FieldEvent>() {

                    public void handleEvent(FieldEvent be) {
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "GeoServer Rules: Profile Custom Properties",
                                "Key " + model.getPropKey() + ": Key changed -> "
                                        + be.getField().getValue() });

//                        Map<String, ProfileCustomProps> updateDTO = new HashMap<String, ProfileCustomProps>();
//                        ProfileCustomProps newModel = new ProfileCustomProps();
//                        newModel.setPropKey((String) be.getField().getValue());
//                        newModel.setPropValue(model.getPropValue());
//                        updateDTO.put(model.getPropKey(), newModel);
//                        Dispatcher.forwardEvent(GeoRepoEvents.RULE_PROFILE_CUSTOM_PROP_UPDATE_KEY, updateDTO);
                        
                        model.setPropKey((String) be.getField().getValue());                        
                    }

                });

                return propKey;
            }

        };

        return textRendered;
    }
    
    /**
     * 
     * @return
     */
    private GridCellRenderer<ProfileCustomProps> createPropValueTextBox() {

        GridCellRenderer<ProfileCustomProps> textRendered = new GridCellRenderer<ProfileCustomProps>() {

            private boolean init;

            public Object render(final ProfileCustomProps model, String property, ColumnData config,
                    int rowIndex, int colIndex, ListStore<ProfileCustomProps> store, Grid<ProfileCustomProps> grid) {

                if (!init) {
                    init = true;
                    grid.addListener(Events.ColumnResize, new Listener<GridEvent<ProfileCustomProps>>() {

                        public void handleEvent(GridEvent<ProfileCustomProps> be) {
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

                TextField<String> propValue = new TextField<String>();
                propValue.setWidth(250);
                propValue.setAllowBlank(true);
                propValue.setValue(model.getPropValue());
                
                propValue.addListener(Events.Change, new Listener<FieldEvent>() {

                    public void handleEvent(FieldEvent be) {
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "GeoServer Rules: Profile Custom Properties",
                                "Key " + model.getPropKey() + ": Value changed -> "
                                        + be.getField().getValue() });

//                        Map<String, ProfileCustomProps> updateDTO = new HashMap<String, ProfileCustomProps>();
//                        ProfileCustomProps newModel = new ProfileCustomProps();
//                        newModel.setPropKey(model.getPropKey());
//                        newModel.setPropValue((String) be.getField().getValue());
//                        updateDTO.put(model.getPropKey(), newModel);
//                        Dispatcher.forwardEvent(GeoRepoEvents.RULE_PROFILE_CUSTOM_PROP_UPDATE_VALUE, updateDTO);
                        
                        model.setPropValue((String) be.getField().getValue());
                    }

                });

                return propValue;
            }

        };

        return textRendered;
    }
    
    /**
     * Creates the profile delete button.
     * 
     * @return the grid cell renderer
     */
    private GridCellRenderer<ProfileCustomProps> createDeleteButton() {
        GridCellRenderer<ProfileCustomProps> buttonRendered = new GridCellRenderer<ProfileCustomProps>() {

            private boolean init;

            public Object render(final ProfileCustomProps model, String property, ColumnData config,
                    int rowIndex, int colIndex, ListStore<ProfileCustomProps> store, Grid<ProfileCustomProps> grid) {

                if (!init) {
                    init = true;
                    grid.addListener(Events.ColumnResize, new Listener<GridEvent<ProfileCustomProps>>() {

                        public void handleEvent(GridEvent<ProfileCustomProps> be) {
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

                Button removeButton = new Button();
                removeButton.setBorders(false);
                removeButton.setIcon(Resources.ICONS.delete());
                removeButton.setEnabled(true);

                removeButton.addListener(Events.OnClick, new Listener<ButtonEvent>() {

                    public void handleEvent(ButtonEvent be) {
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "GeoServer Rules: Profile Custom Properties", "Remove Property" });
                        
                        Map<Long, ProfileCustomProps> updateDTO = new HashMap<Long, ProfileCustomProps>();
                        updateDTO.put(profile.getId(), model);
                        Dispatcher.forwardEvent(GeoRepoEvents.RULE_PROFILE_CUSTOM_PROP_DEL, updateDTO);
                    }
                });

                return removeButton;
            }

        };

        return buttonRendered;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#createStore()
     */
    @Override
    public void createStore() {
        this.toolBar = new PagingToolBar(it.geosolutions.georepo.gui.client.Constants.DEFAULT_PAGESIZE);

        // Loader fro rulesService
        this.proxy = new RpcProxy<PagingLoadResult<ProfileCustomProps>>() {

            @Override
            protected void load(Object loadConfig, AsyncCallback<PagingLoadResult<ProfileCustomProps>> callback) {
                profilesService.getProfileCustomProps((PagingLoadConfig) loadConfig, profile, callback);
            }

        };
        loader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);
        loader.setRemoteSort(true);
        store = new ListStore<ProfileCustomProps>(loader);
//        store.sort(BeanKeyValue.PROFILE_PROP_KEY.getValue(), SortDir.ASC);

        Button addRuleCustomPropertyButton = new Button("Add Property");
        addRuleCustomPropertyButton.setIcon(Resources.ICONS.add());

        addRuleCustomPropertyButton.addListener(Events.OnClick, new Listener<ButtonEvent>() {

            public void handleEvent(ButtonEvent be) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                        "GeoServer Rules: Layer Custom Properties", "Add Property" });
                
                Map<Long, ProfileCustomProps> updateDTO = new HashMap<Long, ProfileCustomProps>();
                updateDTO.put(profile.getId(), null);
                Dispatcher.forwardEvent(GeoRepoEvents.RULE_PROFILE_CUSTOM_PROP_ADD, updateDTO);
            }
        });

        Button saveRuleCustomPropertiesButton = new Button("Save");
        saveRuleCustomPropertiesButton.setIcon(Resources.ICONS.save());

        saveRuleCustomPropertiesButton.addListener(Events.OnClick, new Listener<ButtonEvent>() {

            public void handleEvent(ButtonEvent be) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                        "GeoServer Rules: Layer Custom Properties", "Apply Changes" });
                
                Dispatcher.forwardEvent(GeoRepoEvents.RULE_PROFILE_CUSTOM_PROP_APPLY_CHANGES, profile.getId());
            }
        });
        
        Button cancelButton = new Button("Cancel");
        cancelButton.addListener(Events.OnClick, new Listener<ButtonEvent>() {
            public void handleEvent(ButtonEvent be) {
                // /////////////////////////////////////////////////////////
                // Getting the Profile details edit dialogs and hiding this
                // /////////////////////////////////////////////////////////
                ComponentManager.get().get(I18nProvider.getMessages().profileDialogId()).hide();
            }
        }); 
        
        this.toolBar.bind(loader);
        this.toolBar.add(new SeparatorToolItem());
        this.toolBar.add(addRuleCustomPropertyButton);
        this.toolBar.add(new SeparatorToolItem());
        this.toolBar.add(saveRuleCustomPropertiesButton);
        this.toolBar.add(cancelButton);

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
                        message = I18nProvider.getMessages().recordLabel();
                    else
                        message = I18nProvider.getMessages().recordPluralLabel();
                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                            I18nProvider.getMessages().remoteServiceName(),
                            I18nProvider.getMessages().foundLabel() + " " + result.getData().size()
                                    + " " + message });
                } else {
                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                            I18nProvider.getMessages().remoteServiceName(),
                            I18nProvider.getMessages().recordNotFoundMessage() });
                }
            }

        });
    }

}
