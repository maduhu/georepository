/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.ButtonBar,v. 0.1 25-gen-2011 11.30.33 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.30.33 $
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

import it.geosolutions.georepo.gui.client.Category;
import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.Resources;
import it.geosolutions.georepo.gui.client.action.ToolbarAction;
import it.geosolutions.georepo.gui.client.action.ToolbarActionRegistry;
import it.geosolutions.georepo.gui.client.action.ToolbarApplicationAction;
import it.geosolutions.georepo.gui.client.action.ToolbarMapAction;
import it.geosolutions.georepo.gui.client.action.menu.MenuAction;
import it.geosolutions.georepo.gui.client.action.menu.MenuActionRegistry;
import it.geosolutions.georepo.gui.client.configuration.ActionClientTool;
import it.geosolutions.georepo.gui.client.configuration.DropdownClientTool;
import it.geosolutions.georepo.gui.client.configuration.DropdownOption;
import it.geosolutions.georepo.gui.client.configuration.GenericClientTool;
import it.geosolutions.georepo.gui.client.configuration.MenuClientTool;
import it.geosolutions.georepo.gui.client.model.User;
import it.geosolutions.georepo.gui.client.widget.map.MapLayoutWidget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToggleButton;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

// TODO: Auto-generated Javadoc
/**
 * The Class ButtonBar.
 */
public class ButtonBar extends LayoutContainer implements Listener<AppEvent> {

    /** The Constant TOOLBAR_SEPARATOR. */
    public static final String TOOLBAR_SEPARATOR = "ToolbarSeparator";

    /** The vp. */
    private VerticalPanel vp;

    /** The tool bar. */
    private ToolBar toolBar;

    /** The map layout widget. */
    private MapLayoutWidget mapLayoutWidget;

    /** The REGISTR y_ buttons. */
    private Map<String, Button> REGISTRY_BUTTONS = new HashMap<String, Button>();

    /** The dropdowns. */
    private Map<DropdownClientTool, ComboBox> dropdowns = new HashMap<DropdownClientTool, ComboBox>();

    /**
     * Instantiates a new button bar.
     * 
     * @param mapLayoutWidget
     *            the map layout widget
     */
    public ButtonBar(MapLayoutWidget mapLayoutWidget) {
        super();
        this.vp = new VerticalPanel();
        this.toolBar = new ToolBar();
        this.toolBar.setHeight(60);
        this.mapLayoutWidget = mapLayoutWidget;

        this.addListener(GeoRepoEvents.LOGIN_SUCCESS, this);

        initialize();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.extjs.gxt.ui.client.event.Listener#handleEvent(com.extjs.gxt.ui.client.event.BaseEvent)
     */
    public void handleEvent(AppEvent e) {

        if (e.getType() == GeoRepoEvents.LOGIN_SUCCESS) {

            // reload the contents of any dropdowns (if necessary) based on the logged-in user
            User user = e.getData();
            for (Map.Entry<DropdownClientTool, ComboBox> entry : this.dropdowns.entrySet()) {
                entry.getKey().injectSecurity(entry.getValue(), user.getGrantedAuthorizations());
            }
        }
    }

    /**
     * Initialize.
     */
    private void initialize() {
        List<GenericClientTool> tools = this.mapLayoutWidget.getTools();
        for (GenericClientTool tool : tools) {
            String id = tool.getId();
            if (id.equals(TOOLBAR_SEPARATOR)) {
                addSeparator();
            } else if (tool instanceof MenuClientTool) {
                addMenuButton((MenuClientTool) tool,
                        (ToolbarApplicationAction) ToolbarActionRegistry.get(id, mapLayoutWidget
                                .getMapWidget()));
            } else if (tool instanceof DropdownClientTool) {
                addDropdown((DropdownClientTool) tool,
                        (ToolbarApplicationAction) ToolbarActionRegistry.get(id, mapLayoutWidget
                                .getMapWidget()));
            } else {
                ToolbarAction action = ToolbarActionRegistry
                        .get(id, mapLayoutWidget.getMapWidget());

                action.setEnabled(((ActionClientTool) tool).isEnabled());
                action.setId(id);

                if (action instanceof ToolbarApplicationAction)
                    addApplicationButton((ToolbarApplicationAction) action);

                if (action instanceof ToolbarMapAction) {
                    if (((ActionClientTool) tool).getType().equals("toggle")) {
                        addMapToogleButton((ToolbarMapAction) action);
                    } else {
                        addMapButton((ToolbarMapAction) action);
                    }
                }
            }
        }
        vp.add(toolBar);
        add(vp);
    }

    /**
     * Adds the menu button.
     * 
     * @param tool
     *            the tool
     * @param action
     *            the action
     */
    public void addMenuButton(MenuClientTool tool, ToolbarApplicationAction action) {
        Button button = new Button();
        button.setId(tool.getId());
        button.setWidth(60);
        button.setText(action.getButtonName());
        setIcon(button, action.getCategory());
        button.setEnabled(tool.isEnabled());

        button.setMenu(createMenu(tool.getActionTools()));

        this.toolBar.add(button);
    }

    /**
     * Creates the menu.
     * 
     * @param actionTools
     *            the action tools
     * @return the menu
     */
    private Menu createMenu(List<ActionClientTool> actionTools) {
        Menu menu = new Menu();
        for (ActionClientTool actionTool : actionTools) {
            MenuAction action = MenuActionRegistry.get(actionTool.getId());
            MenuItem item = new MenuItem(action.getTitle());
            item.addSelectionListener(action);
            setMenuIcon(item, action.getCategory());
            menu.add(item);
        }
        return menu;
    }

    /**
     * Adds the dropdown.
     * 
     * @param tool
     *            the tool
     * @param action
     *            the action
     */
    private void addDropdown(DropdownClientTool tool, ToolbarApplicationAction action) {

        // first, add the label if necessary
        if (tool.getLabel() != null) {
            LabelField labelField = new LabelField();
            labelField.setText(tool.getLabel());

            this.toolBar.add(labelField);
        }

        // now, create the actual dropdown
        ComboBox<BaseModelData> dd = new ComboBox<BaseModelData>();
        dd.setId(tool.getId());
        dd.setWidth(170);
        dd.setEnabled(tool.isEnabled());
        dd.setVisible(true);

        // the following calls essentially turn this Combo into a real dropdown box (as opposed to a
        // combo
        // box, which allows you to drop a list down or add new data by typing).

        // Sencha docs/examples are less than comprehensive, so I'll document this here: the
        // setTriggerAction()
        // call is needed to make sure that each time a "trigger action" is sent (presumably any
        // typing, or
        // clicking the "down arrow" button), that the full contents of the dropdown are returned
        // every time.
        // Otherwise, it would only return entries from the drop down list that matched what was
        // typed in
        // the text edit part of the combo box.
        dd.setTriggerAction(ComboBox.TriggerAction.ALL);
        dd.disableTextSelection(true);
        dd.setTypeAhead(false);
        dd.setAllowBlank(tool.isAllowBlank());
        dd.setEditable(false);

        dd.setDisplayField(DropdownOption.LABEL_KEY);
        dd.setValueField(DropdownOption.VALUE_KEY);
        ListStore<BaseModelData> modelData = createComboBaseModel(tool);
        dd.setStore(modelData);

        // set the configured default if it exists
        if ((tool.getDefaultValue() != null) && (!tool.getDefaultValue().isEmpty())) {
            BaseModelData defaultModel = getDefaultModelValue(modelData, tool.getDefaultValue());
            if (defaultModel != null) {
                dd.setValue(defaultModel);
            }
        }

        dd.addListener(Events.SelectionChange, action);

        this.toolBar.add(dd);

        // keep track of dropdowns in case we need to refill the contents
        this.dropdowns.put(tool, dd);
    }

    /**
     * Gets the default model value.
     * 
     * @param modelData
     *            the model data
     * @param defaultValue
     *            the default value
     * @return the default model value
     */
    private BaseModelData getDefaultModelValue(ListStore<BaseModelData> modelData,
            String defaultValue) {
        List<BaseModelData> models = modelData.getModels();

        for (BaseModelData model : models) {
            if (model.get(DropdownOption.VALUE_KEY).equals(defaultValue)) {
                return model;
            }
        }

        return null;
    }

    /**
     * Creates the combo base model.
     * 
     * @param tool
     *            the tool
     * @return the list store
     */
    private ListStore<BaseModelData> createComboBaseModel(DropdownClientTool tool) {
        List<DropdownOption> options = tool.getDropdownOptions();
        ListStore<BaseModelData> store = new ListStore<BaseModelData>();

        for (DropdownOption option : options) {
            BaseModelData model = new BaseModelData();
            model.set(DropdownOption.VALUE_KEY, option.getValue());

            // use the i18n value if it exists, otherwise, use the value that's
            // already in the DropdownOption
            String i18nDisplay = tool.getDropdownOptionDisplayValue(option.getValue());
            model.set(DropdownOption.LABEL_KEY, (i18nDisplay != null ? i18nDisplay : option
                    .getLabel()));
            store.add(model);
        }

        return store;
    }

    /**
     * Adds the separator.
     */
    public void addSeparator() {
        this.toolBar.add(new SeparatorToolItem());
    }

    /**
     * Adds the map button.
     * 
     * @param action
     *            the action
     */
    public void addMapButton(ToolbarMapAction action) {
        Button button = new Button();
        button.setId(action.getId());
        button.setWidth(24);
        button.setToolTip(action.getTooltip());
        setIcon(button, action.getCategory());
        button.addListener(Events.Select, action);
        button.setEnabled(action.isEnabled());

        // TODO: don't hard-code the "fill space" to be after cleanDGWMenu - it should be more
        // dynamic than this
        if (action.getId().equalsIgnoreCase("cleanDGWMenu")) {
            this.toolBar.add(new FillToolItem());
        }
        this.toolBar.add(button);

        REGISTRY_BUTTONS.put(button.getId(), button);
    }

    /**
     * Adds the application button.
     * 
     * @param action
     *            the action
     */
    public void addApplicationButton(ToolbarApplicationAction action) {
        Button button = new Button();
        button.setId(action.getId());
        button.setWidth(70);
        button.setText(action.getButtonName());
        setIcon(button, action.getCategory());
        button.addListener(Events.Select, action);
        button.setEnabled(action.isEnabled());

        if (action.getId().equalsIgnoreCase("logout")) {
            this.toolBar.add(new FillToolItem());
        }
        this.toolBar.add(button);

        REGISTRY_BUTTONS.put(button.getId(), button);
    }

    /**
     * Adds the map toogle button.
     * 
     * @param action
     *            the action
     */
    public void addMapToogleButton(ToolbarMapAction action) {
        Button button = new ToggleButton();
        button.setId(action.getId());
        button.setWidth(24);
        button.setToolTip(action.getTooltip());
        setIcon(button, action.getCategory());
        button.addListener(Events.Select, action);
        // button.addListener((new ButtonEvent(button)).getType(), action);
        // button.addSelectionListener(action);
        button.setEnabled(action.isEnabled());
        this.toolBar.add(button);
        REGISTRY_BUTTONS.put(button.getId(), button);
    }

    /**
     * Sets the icon.
     * 
     * @param button
     *            the button
     * @param cat
     *            the cat
     */
    private void setIcon(Button button, Category cat) {
        switch (cat) {
        case GEOREPO_INFO:
            button.setIcon(Resources.ICONS.info());
            break;
        case GEOREPO_ZOOM_IN:
            button.setIcon(Resources.ICONS.zoomIn());
            break;
        case GEOREPO_ZOOM_OUT:
            button.setIcon(Resources.ICONS.zoomOut());
            break;
        case GEOREPO_DRAW:
            button.setIcon(Resources.ICONS.drawFeature());
            break;
        case GEOREPO_UPLOAD_SHP:
            button.setIcon(Resources.ICONS.uploadSHP());
            break;
        case LOGOUT:
            button.setIcon(Resources.ICONS.logout());
            break;
        case GEOREPO_CLEAN:
            button.setIcon(Resources.ICONS.cleanGeoRepoMenu());
            break;
        case SAVE:
            button.setIcon(Resources.ICONS.save());
            break;
        case DELETE_CONTENT:
            button.setIcon(Resources.ICONS.delete());
            break;
        }
    }

    /**
     * Sets the menu icon.
     * 
     * @param item
     *            the item
     * @param cat
     *            the cat
     */
    private void setMenuIcon(MenuItem item, Category cat) {
    }

    /**
     * Gets the tool bar.
     * 
     * @return the tool bar
     */
    public ToolBar getToolBar() {
        return toolBar;
    }

    /**
     * Change button state.
     * 
     * @param key
     *            the key
     * @param value
     *            the value
     */
    public void changeButtonState(String key, boolean value) {
        if (REGISTRY_BUTTONS.containsKey(key)) {
            REGISTRY_BUTTONS.get(key).setEnabled(value);
        }
    }

}
