/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.dialog.RuleDetailsEditDialog,v. 0.1 7-feb-2011 14.07.30 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 7-feb-2011 14.07.30 $
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
package it.geosolutions.georepo.gui.client.widget.dialog;

import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.model.Rule;
import it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.SaveStaus;
import it.geosolutions.georepo.gui.client.widget.SaveStaus.EnumSaveStatus;
import it.geosolutions.georepo.gui.client.widget.rule.detail.LayerAttributesTabItem;
import it.geosolutions.georepo.gui.client.widget.rule.detail.LayerCustomPropsTabItem;
import it.geosolutions.georepo.gui.client.widget.rule.detail.RuleDetailsTabItem;
import it.geosolutions.georepo.gui.client.widget.tab.TabWidget;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.google.gwt.user.client.ui.FormPanel;

/**
 * The Class RuleDetailsEditDialog.
 */
public class RuleDetailsEditDialog extends Dialog {

    public static final String RULE_DETAILS_DIALOG_ID = "ruleDetailsDialog";

    public static final String RULE_LAYER_ATTRIBUTES_DIALOG_ID = "ruleLayerAttributesDialog";

    public static final String RULE_LAYER_CUSTOM_PROPS_DIALOG_ID = "ruleLayerCustomPropsDialog";

    private FormPanel formPanel;

    private com.extjs.gxt.ui.client.widget.form.FormPanel previewFP;

    private SaveStaus saveStatus;

    private Button done;

    protected String wkt;
    
    private Rule model;

    /**
     * 
     */
    private RulesManagerServiceRemoteAsync rulesManagerServiceRemote;

    private TabWidget tabWidget;

    /**
     * @param rulesManagerServiceRemote 
     * 
     */
    public RuleDetailsEditDialog(RulesManagerServiceRemoteAsync rulesManagerServiceRemote) {
        this.rulesManagerServiceRemote = rulesManagerServiceRemote;
        setTabWidget(new TabWidget());
        
        setHeading("Rule Details");
        setResizable(false);
        setButtons("");
        setClosable(true);
        setModal(true);
        setWidth(680);
        setHeight(400);
        
        add(this.getTabWidget());

        // this.addListener(Events.Hide, new Listener<WindowEvent>() {
        //                
        // public void handleEvent(WindowEvent be) {
        // reset();
        // }
        // });
        //        
        // this.addListener(Events.Show, new Listener<WindowEvent>() {
        //                
        // public void handleEvent(WindowEvent be) {
        // mapPreviewWidget.getMapWidget().getMap().zoomToMaxExtent();
        // // mapPreviewWidget.getMapWidget().getMap().zoomTo(1);
        // mapPreviewWidget.getMapWidget().getMap().updateSize();
        // }
        // });
        //        
        // this.createUpload();
        // this.createMapPreview();

        // add(this.previewFP);
    }

    @Override
    protected void createButtons() {
        super.createButtons();

        this.saveStatus = new SaveStaus();
        this.saveStatus.setAutoWidth(true);

        getButtonBar().add(saveStatus);

        getButtonBar().add(new FillToolItem());

        this.done = new Button("Done", new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                hide();
                Dispatcher.forwardEvent(GeoRepoEvents.INJECT_WKT, wkt);
            }
        });

        this.done.disable();
        addButton(done);
    }

    @Override
    public void show() {
        super.show();
        
        if (getModel() != null) {
            
            // TODO: Temporary. To be removed as soon as the rule editor will be completed!
            if (model.getLayer() == null || model.getLayer().equalsIgnoreCase("*")) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_ALERT_MESSAGE, 
                        new String[] {
                            "Rule Properties Editor",
                            "Rule details editor actually enabled only for Rules which specifies a Layer on the filter."
                        }
                );
            } else {
                setHeading("Editing Details for Rule #" + model.getPriority() );
                this.tabWidget.add(new RuleDetailsTabItem(RULE_DETAILS_DIALOG_ID, model, rulesManagerServiceRemote));
                
                if (model.getLayer() != null && !model.getLayer().equalsIgnoreCase("*")) {
                    TabItem layerAttributesItem = new LayerAttributesTabItem(RULE_LAYER_ATTRIBUTES_DIALOG_ID, model, rulesManagerServiceRemote);
                    TabItem layersCustomPropsItem = new LayerCustomPropsTabItem(RULE_LAYER_CUSTOM_PROPS_DIALOG_ID, model, rulesManagerServiceRemote);

                    this.tabWidget.add(layerAttributesItem);
                    this.tabWidget.add(layersCustomPropsItem);
                    
                    this.tabWidget.setSelection(layersCustomPropsItem);
                }
            }
        }
    }

    /**
     * 
     */
    public void reset() {
        this.done.disable();
        this.tabWidget.removeAll();
        this.saveStatus.clearStatus("");
    }

    /**
     * Set the correct Status Iconn Style
     */
    public void setSaveStatus(EnumSaveStatus status, EnumSaveStatus message) {
        this.saveStatus.setIconStyle(status.getValue());
        this.saveStatus.setText(message.getValue());
    }

    /**
     * @param model the model to set
     */
    public void setModel(Rule model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public Rule getModel() {
        return model;
    }

    /**
     * @param tabWidget the tabWidget to set
     */
    public void setTabWidget(TabWidget tabWidget) {
        this.tabWidget = tabWidget;
    }

    /**
     * @return the tabWidget
     */
    public TabWidget getTabWidget() {
        return tabWidget;
    }
}
