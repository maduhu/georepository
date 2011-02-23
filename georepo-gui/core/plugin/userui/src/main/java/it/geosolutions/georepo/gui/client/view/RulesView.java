/*
 * $ Header: it.geosolutions.georepo.gui.client.view.RulesView,v. 0.1 9-feb-2011 11.42.29 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 9-feb-2011 11.42.29 $
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
package it.geosolutions.georepo.gui.client.view;

import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.BeanKeyValue;
import it.geosolutions.georepo.gui.client.model.Rule;
import it.geosolutions.georepo.gui.client.model.data.LayerCustomProps;
import it.geosolutions.georepo.gui.client.service.GsUsersManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.GsUsersManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.InstancesManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.InstancesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.WorkspacesManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.WorkspacesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.AddGsUserWidget;
import it.geosolutions.georepo.gui.client.widget.EditRuleWidget;
import it.geosolutions.georepo.gui.client.widget.dialog.RuleDetailsEditDialog;
import it.geosolutions.georepo.gui.client.widget.rule.detail.LayerCustomPropsGridWidget;
import it.geosolutions.georepo.gui.client.widget.rule.detail.LayerCustomPropsTabItem;

import java.util.Map;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class RulesView.
 */
public class RulesView extends View {

    /** The rules manager service remote. */
    private RulesManagerServiceRemoteAsync rulesManagerServiceRemote = RulesManagerServiceRemote.Util
            .getInstance();

    /** The rules manager service remote. */
    private GsUsersManagerServiceRemoteAsync usersManagerServiceRemote = GsUsersManagerServiceRemote.Util
            .getInstance();
    
    /** The rules manager service remote. */
    private InstancesManagerServiceRemoteAsync instancesManagerServiceRemote = InstancesManagerServiceRemote.Util
            .getInstance();

    /** The rules manager service remote. */
    private WorkspacesManagerServiceRemoteAsync workspacesManagerServiceRemote = WorkspacesManagerServiceRemote.Util
            .getInstance();
    
    /** The rules manager service remote. */
    private ProfilesManagerServiceRemoteAsync profilesManagerServiceRemote = ProfilesManagerServiceRemote.Util
            .getInstance();
    
    /** The rule editor dialog. */
    private RuleDetailsEditDialog ruleEditorDialog;

    private EditRuleWidget ruleRowEditor;

    /**
     * Instantiates a new rules view.
     * 
     * @param controller
     *            the controller
     */
    public RulesView(Controller controller) {
        super(controller);

        this.ruleEditorDialog = new RuleDetailsEditDialog(rulesManagerServiceRemote);

        this.ruleRowEditor = new EditRuleWidget(GeoRepoEvents.SAVE_USER, true, rulesManagerServiceRemote, null, null, null, null);
        this.ruleRowEditor.setGsUserService(usersManagerServiceRemote);
        this.ruleRowEditor.setRuleService(rulesManagerServiceRemote);
        this.ruleRowEditor.setInstanceService(instancesManagerServiceRemote);
        this.ruleRowEditor.setWorkspaceService(workspacesManagerServiceRemote);
        this.ruleRowEditor.setProfileService(profilesManagerServiceRemote);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.View#handleEvent(com.extjs.gxt.ui.client.mvc.AppEvent)
     */
    @Override
    protected void handleEvent(AppEvent event) {
        if (event.getType() == GeoRepoEvents.EDIT_RULE_DETAILS)
            onEditRuleDetails(event);
        if (event.getType() == GeoRepoEvents.EDIT_RULE)
            onEditRowRuleDetails(event);
        if (event.getType() == GeoRepoEvents.RULE_CUSTOM_PROP_ADD)
            onRuleCustomPropAdd(event);

        if (event.getType() == GeoRepoEvents.RULE_CUSTOM_PROP_DEL)
            onRuleCustomPropRemove(event);

        if (event.getType() == GeoRepoEvents.RULE_CUSTOM_PROP_UPDATE_KEY)
            onRuleCustomPropUpdateKey(event);

        if (event.getType() == GeoRepoEvents.RULE_CUSTOM_PROP_UPDATE_VALUE)
            onRuleCustomPropUpdateValue(event);

        if (event.getType() == GeoRepoEvents.RULE_CUSTOM_PROP_APPLY_CHANGES)
            onRuleCustomPropSave(event);
        if (event.getType() == GeoRepoEvents.UPDATE_SOUTH_SIZE) {
            //logger
        }
    }

    /**
     * On edit rule details.
     * 
     * @param event
     *            the event
     */
    private void onEditRuleDetails(AppEvent event) {
        if (event.getData() != null && event.getData() instanceof Rule) {
            this.ruleEditorDialog.reset();
            this.ruleEditorDialog.setModel((Rule) event.getData());
            this.ruleEditorDialog.show();
        } else {
            // TODO: i18n!!
            Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE, new String[] {
                    "Rules Editor", "Could not found any associated rule!" });
        }
    }

    /**
     * On edit rule details.
     * 
     * @param event
     *            the event
     */
    private void onEditRowRuleDetails(AppEvent event) {
        if (event.getData() != null && event.getData() instanceof Rule) {
            this.ruleRowEditor.reset();
            this.ruleRowEditor.createStore();
            this.ruleRowEditor.store.add((Rule)event.getData());
            this.ruleRowEditor.initGrid();
            this.ruleRowEditor.setModel((Rule) event.getData());
            this.ruleRowEditor.initializeFormPanel();
            this.ruleRowEditor.add(this.ruleRowEditor.formPanel);
            this.ruleRowEditor.show();
        } else {
            // TODO: i18n!!
            Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE, new String[] {
                    "Rules Editor", "Could not found any associated rule!" });
        }
    }

    
    /**
     * On rule custom prop add.
     * 
     * @param event
     *            the event
     */
    private void onRuleCustomPropAdd(AppEvent event) {
        if (event.getData() != null) {
            LayerCustomPropsTabItem layersCustomPropsItem = (LayerCustomPropsTabItem) this.ruleEditorDialog
                    .getTabWidget().getItemByItemId(
                            RuleDetailsEditDialog.RULE_LAYER_CUSTOM_PROPS_DIALOG_ID);
            final LayerCustomPropsGridWidget layerCustomPropsInfo = layersCustomPropsItem
                    .getLayerCustomPropsWidget().getLayerCustomPropsInfo();
            LayerCustomProps model = new LayerCustomProps();
            model.setPropKey("_new");
            layerCustomPropsInfo.getStore().add(model);

            layerCustomPropsInfo.getGrid().repaint();
        } else {
            // TODO: i18n!!
            Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE, new String[] {
                    "Rules Details Editor", "Could not found any associated rule!" });
        }
    }

    /**
     * On rule custom prop remove.
     * 
     * @param event
     *            the event
     */
    private void onRuleCustomPropRemove(AppEvent event) {
        if (event.getData() != null) {
            Map<Long, LayerCustomProps> updateDTO = event.getData();

            LayerCustomPropsTabItem layersCustomPropsItem = (LayerCustomPropsTabItem) this.ruleEditorDialog
                    .getTabWidget().getItemByItemId(
                            RuleDetailsEditDialog.RULE_LAYER_CUSTOM_PROPS_DIALOG_ID);
            final LayerCustomPropsGridWidget layerCustomPropsInfo = layersCustomPropsItem
                    .getLayerCustomPropsWidget().getLayerCustomPropsInfo();

            for (Long ruleId : updateDTO.keySet()) {
                LayerCustomProps dto = updateDTO.get(ruleId);

                for (LayerCustomProps prop : layerCustomPropsInfo.getStore().getModels()) {
                    if (prop.getPropKey().equals(dto.getPropKey()))
                        layerCustomPropsInfo.getStore().remove(prop);
                }
            }

            layerCustomPropsInfo.getGrid().repaint();

        } else {
            // TODO: i18n!!
            Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE, new String[] {
                    "Rules Details Editor", "Could not found any associated rule!" });
        }
    }

    /**
     * On rule custom prop update key.
     * 
     * @param event
     *            the event
     */
    private void onRuleCustomPropUpdateKey(AppEvent event) {
        if (event.getData() != null) {
            LayerCustomPropsTabItem layersCustomPropsItem = (LayerCustomPropsTabItem) this.ruleEditorDialog
                    .getTabWidget().getItemByItemId(
                            RuleDetailsEditDialog.RULE_LAYER_CUSTOM_PROPS_DIALOG_ID);
            final LayerCustomPropsGridWidget layerCustomPropsInfo = layersCustomPropsItem
                    .getLayerCustomPropsWidget().getLayerCustomPropsInfo();

            Map<String, LayerCustomProps> updateDTO = event.getData();

            for (String key : updateDTO.keySet()) {
                for (LayerCustomProps prop : layerCustomPropsInfo.getStore().getModels()) {
                    if (prop.getPropKey().equals(key)) {
                        layerCustomPropsInfo.getStore().remove(prop);
                        LayerCustomProps newModel = updateDTO.get(key);
                        layerCustomPropsInfo.getStore().add(newModel);
                    }
                }
            }

            layerCustomPropsInfo.getGrid().repaint();
        } else {
            // TODO: i18n!!
            Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE, new String[] {
                    "Rules Details Editor", "Could not found any associated rule!" });
        }
    }

    /**
     * On rule custom prop update value.
     * 
     * @param event
     *            the event
     */
    private void onRuleCustomPropUpdateValue(AppEvent event) {
        if (event.getData() != null) {
            LayerCustomPropsTabItem layersCustomPropsItem = (LayerCustomPropsTabItem) this.ruleEditorDialog
                    .getTabWidget().getItemByItemId(
                            RuleDetailsEditDialog.RULE_LAYER_CUSTOM_PROPS_DIALOG_ID);
            final LayerCustomPropsGridWidget layerCustomPropsInfo = layersCustomPropsItem
                    .getLayerCustomPropsWidget().getLayerCustomPropsInfo();

            Map<String, LayerCustomProps> updateDTO = event.getData();

            for (String key : updateDTO.keySet()) {
                for (LayerCustomProps prop : layerCustomPropsInfo.getStore().getModels()) {
                    if (prop.getPropKey().equals(key)) {
                        layerCustomPropsInfo.getStore().remove(prop);
                        LayerCustomProps newModel = updateDTO.get(key);
                        layerCustomPropsInfo.getStore().add(newModel);
                    }
                }
            }

            layerCustomPropsInfo.getGrid().repaint();
        } else {
            // TODO: i18n!!
            Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE, new String[] {
                    "Rules Details Editor", "Could not found any associated rule!" });
        }
    }

    /**
     * 
     * @param event
     */
    private void onRuleCustomPropSave(AppEvent event) {
        Long ruleId = event.getData();

        LayerCustomPropsTabItem layersCustomPropsItem = (LayerCustomPropsTabItem) this.ruleEditorDialog
                .getTabWidget().getItemByItemId(
                        RuleDetailsEditDialog.RULE_LAYER_CUSTOM_PROPS_DIALOG_ID);
        final LayerCustomPropsGridWidget layerCustomPropsInfo = layersCustomPropsItem
                .getLayerCustomPropsWidget().getLayerCustomPropsInfo();

        rulesManagerServiceRemote.setDetailsProps(ruleId, layerCustomPropsInfo.getStore()
                .getModels(), new AsyncCallback<PagingLoadResult<LayerCustomProps>>() {

            public void onFailure(Throwable caught) {

                Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE, new String[] {
                        I18nProvider.getMessages().ruleServiceName(),
                        "Error occurred while saving Rule Custom Properties!" });
            }

            public void onSuccess(PagingLoadResult<LayerCustomProps> result) {

                //grid.getStore().sort(BeanKeyValue.PRIORITY.getValue(), SortDir.ASC);
            	layerCustomPropsInfo.getStore().getLoader().setSortDir(SortDir.ASC);
            	layerCustomPropsInfo.getStore().getLoader().setSortField(BeanKeyValue.PRIORITY.getValue());
            	layerCustomPropsInfo.getStore().getLoader().load();


                Dispatcher.forwardEvent(GeoRepoEvents.BIND_MEMBER_DISTRIBUTION_NODES, result);
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                        I18nProvider.getMessages().ruleServiceName(),
                        I18nProvider.getMessages().ruleFetchSuccessMessage() });
            }
        });
    }
}
