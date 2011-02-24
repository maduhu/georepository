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
import it.geosolutions.georepo.gui.client.model.Rule;
import it.geosolutions.georepo.gui.client.model.data.LayerCustomProps;
import it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemoteAsync;
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

// TODO: Auto-generated Javadoc
/**
 * The Class LayerCustomPropsGridWidget.
 */
public class LayerCustomPropsGridWidget extends GeoRepoGridWidget<LayerCustomProps> {

    /** The rules service. */
    private RulesManagerServiceRemoteAsync rulesService;

    /** The proxy. */
    private RpcProxy<PagingLoadResult<LayerCustomProps>> proxy;

    /** The loader. */
    private PagingLoader<PagingLoadResult<ModelData>> loader;

    /** The tool bar. */
    private PagingToolBar toolBar;

    
    private Rule theRule;
    
    /**
     * Instantiates a new layer custom props grid widget.
     * @param theRule 
     * 
     * @param rulesService
     *            the rules service
     */
    public LayerCustomPropsGridWidget(Rule model, RulesManagerServiceRemoteAsync rulesService) {
        super();
        this.theRule = model;
        this.rulesService = rulesService;
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
    
    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.client.widget.GeoRepoGridWidget#prepareColumnModel()
     */
    @Override
    public ColumnModel prepareColumnModel() {
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
        
        ColumnConfig layerPropKeyColumn = new ColumnConfig();
        layerPropKeyColumn.setId(BeanKeyValue.PROP_KEY.getValue());
        layerPropKeyColumn.setHeader("Key");
        layerPropKeyColumn.setWidth(210);
        layerPropKeyColumn.setRenderer(this.createPropKeyTextBox());
        layerPropKeyColumn.setMenuDisabled(true);
        layerPropKeyColumn.setSortable(false);
        configs.add(layerPropKeyColumn);

        ColumnConfig layerPropValueColumn = new ColumnConfig();
        layerPropValueColumn.setId(BeanKeyValue.PROP_VALUE.getValue());
        layerPropValueColumn.setHeader("Value");
        layerPropValueColumn.setWidth(260);
        layerPropValueColumn.setRenderer(this.createPropValueTextBox());
        layerPropValueColumn.setMenuDisabled(true);
        layerPropValueColumn.setSortable(false);
        configs.add(layerPropValueColumn);
        
        ColumnConfig removeActionColumn = new ColumnConfig();
        removeActionColumn.setId("removeLayerCustomProp");
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
    private GridCellRenderer<LayerCustomProps> createPropKeyTextBox() {

        GridCellRenderer<LayerCustomProps> textRendered = new GridCellRenderer<LayerCustomProps>() {

            private boolean init;

            public Object render(final LayerCustomProps model, String property, ColumnData config,
                    int rowIndex, int colIndex, ListStore<LayerCustomProps> store, Grid<LayerCustomProps> grid) {

                if (!init) {
                    init = true;
                    grid.addListener(Events.ColumnResize, new Listener<GridEvent<LayerCustomProps>>() {

                        public void handleEvent(GridEvent<LayerCustomProps> be) {
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
                TextField<String> propKey = new TextField<String>();
                propKey.setWidth(200);
                propKey.setAllowBlank(false);
                propKey.setValue(model.getPropKey());
                
                propKey.addListener(Events.Change, new Listener<FieldEvent>() {

                    public void handleEvent(FieldEvent be) {
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "GeoServer Rules: Layer Custom Properties",
                                "Key " + model.getPropKey() + ": Key changed -> "
                                        + be.getField().getValue() });

                        Map<String, LayerCustomProps> updateDTO = new HashMap<String, LayerCustomProps>();
                        LayerCustomProps newModel = new LayerCustomProps();
                        newModel.setPropKey((String) be.getField().getValue());
                        newModel.setPropValue(model.getPropValue());
                        updateDTO.put(model.getPropKey(), newModel);
                        Dispatcher.forwardEvent(GeoRepoEvents.RULE_CUSTOM_PROP_UPDATE_KEY, updateDTO);
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
    private GridCellRenderer<LayerCustomProps> createPropValueTextBox() {

        GridCellRenderer<LayerCustomProps> textRendered = new GridCellRenderer<LayerCustomProps>() {

            private boolean init;

            public Object render(final LayerCustomProps model, String property, ColumnData config,
                    int rowIndex, int colIndex, ListStore<LayerCustomProps> store, Grid<LayerCustomProps> grid) {

                if (!init) {
                    init = true;
                    grid.addListener(Events.ColumnResize, new Listener<GridEvent<LayerCustomProps>>() {

                        public void handleEvent(GridEvent<LayerCustomProps> be) {
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
                TextField<String> propValue = new TextField<String>();
                propValue.setWidth(250);
                propValue.setAllowBlank(true);
                propValue.setValue(model.getPropValue());
                
                propValue.addListener(Events.Change, new Listener<FieldEvent>() {

                    public void handleEvent(FieldEvent be) {
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "GeoServer Rules: Layer Custom Properties",
                                "Key " + model.getPropKey() + ": Value changed -> "
                                        + be.getField().getValue() });

                        Map<String, LayerCustomProps> updateDTO = new HashMap<String, LayerCustomProps>();
                        LayerCustomProps newModel = new LayerCustomProps();
                        newModel.setPropKey(model.getPropKey());
                        newModel.setPropValue((String) be.getField().getValue());
                        updateDTO.put(model.getPropKey(), newModel);
                        Dispatcher.forwardEvent(GeoRepoEvents.RULE_CUSTOM_PROP_UPDATE_VALUE, updateDTO);
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
    private GridCellRenderer<LayerCustomProps> createDeleteButton() {
        GridCellRenderer<LayerCustomProps> buttonRendered = new GridCellRenderer<LayerCustomProps>() {

            private boolean init;

            public Object render(final LayerCustomProps model, String property, ColumnData config,
                    int rowIndex, int colIndex, ListStore<LayerCustomProps> store, Grid<LayerCustomProps> grid) {

                if (!init) {
                    init = true;
                    grid.addListener(Events.ColumnResize, new Listener<GridEvent<LayerCustomProps>>() {

                        public void handleEvent(GridEvent<LayerCustomProps> be) {
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
                Button removeButton = new Button();
                removeButton.setBorders(false);
                removeButton.setIcon(Resources.ICONS.delete());
                // TODO: add correct tooltip text here!
                // removeUserButton.setToolTip("...");
                removeButton.setEnabled(true);

                removeButton.addListener(Events.OnClick, new Listener<ButtonEvent>() {

                    public void handleEvent(ButtonEvent be) {
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "GeoServer Rules: Layer Custom Properties", "Remove Property" });
                        
                        Map<Long, LayerCustomProps> updateDTO = new HashMap<Long, LayerCustomProps>();
                        updateDTO.put(theRule.getId(), model);
                        Dispatcher.forwardEvent(GeoRepoEvents.RULE_CUSTOM_PROP_DEL, updateDTO);
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
        this.proxy = new RpcProxy<PagingLoadResult<LayerCustomProps>>() {

            @Override
            protected void load(Object loadConfig, AsyncCallback<PagingLoadResult<LayerCustomProps>> callback) {
                rulesService.getLayerCustomProps((PagingLoadConfig) loadConfig, theRule, callback);
            }

        };
        loader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);
        loader.setRemoteSort(false);
        store = new ListStore<LayerCustomProps>(loader);
        store.sort(BeanKeyValue.PROP_KEY.getValue(), SortDir.ASC);

        // Apply Changes button
        // TODO: generalize this!
        Button addRuleCustomPropertyButton = new Button("Add Property");
        addRuleCustomPropertyButton.setIcon(Resources.ICONS.add());

        addRuleCustomPropertyButton.addListener(Events.OnClick, new Listener<ButtonEvent>() {

            public void handleEvent(ButtonEvent be) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                        "GeoServer Rules: Layer Custom Properties", "Add Property" });
                
                Map<Long, LayerCustomProps> updateDTO = new HashMap<Long, LayerCustomProps>();
                updateDTO.put(theRule.getId(), null);
                Dispatcher.forwardEvent(GeoRepoEvents.RULE_CUSTOM_PROP_ADD, updateDTO);
            }
        });

        Button saveRuleCustomPropertiesButton = new Button("Apply Changes");
        saveRuleCustomPropertiesButton.setIcon(Resources.ICONS.save());

        saveRuleCustomPropertiesButton.addListener(Events.OnClick, new Listener<ButtonEvent>() {

            public void handleEvent(ButtonEvent be) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                        "GeoServer Rules: Layer Custom Properties", "Apply Changes" });
                
                Dispatcher.forwardEvent(GeoRepoEvents.RULE_CUSTOM_PROP_APPLY_CHANGES, theRule.getId());
            }
        });
        
        this.toolBar.bind(loader);
        this.toolBar.add(new SeparatorToolItem());
        this.toolBar.add(addRuleCustomPropertyButton);
        this.toolBar.add(saveRuleCustomPropertiesButton);
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
                        message = I18nProvider.getMessages().recordLabel();
                    else
                        message = I18nProvider.getMessages().recordPluralLabel();
                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                            I18nProvider.getMessages().remoteServiceName(),
                            I18nProvider.getMessages().foundLabel() + " " + result.getData().size()
                                    + " " + message });
                } else {
                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_ALERT_MESSAGE, new String[] {
                            I18nProvider.getMessages().remoteServiceName(),
                            I18nProvider.getMessages().recordNotFoundMessage() });
                }
            }

        });
    }

}
