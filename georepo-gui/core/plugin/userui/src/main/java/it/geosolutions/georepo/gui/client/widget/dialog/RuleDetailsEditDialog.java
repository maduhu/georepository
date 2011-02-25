/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.dialog.RuleDetailsEditDialog,v. 0.1 10-feb-2011 11.50.22 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 10-feb-2011 11.50.22 $
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
import it.geosolutions.georepo.gui.client.service.WorkspacesManagerServiceRemoteAsync;
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

    /** The Constant RULE_DETAILS_DIALOG_ID. */
    public static final String RULE_DETAILS_DIALOG_ID = "ruleDetailsDialog";

    /** The Constant RULE_LAYER_ATTRIBUTES_DIALOG_ID. */
    public static final String RULE_LAYER_ATTRIBUTES_DIALOG_ID = "ruleLayerAttributesDialog";

    /** The Constant RULE_LAYER_CUSTOM_PROPS_DIALOG_ID. */
    public static final String RULE_LAYER_CUSTOM_PROPS_DIALOG_ID = "ruleLayerCustomPropsDialog";

    /** The form panel. */
    private FormPanel formPanel;

    /** The preview fp. */
    private com.extjs.gxt.ui.client.widget.form.FormPanel previewFP;

    /** The save status. */
    private SaveStaus saveStatus;

    /** The done. */
    private Button done;

    /** The wkt. */
    protected String wkt;
    
    /** The model. */
    private Rule model;

    /** The rules manager service remote. */
    private RulesManagerServiceRemoteAsync rulesManagerServiceRemote;
    
    private WorkspacesManagerServiceRemoteAsync workspacesManagerServiceRemote;

    /** The tab widget. */
    private TabWidget tabWidget;

    /**
     * Instantiates a new rule details edit dialog.
     * 
     * @param rulesManagerServiceRemote
     *            the rules manager service remote
     */
    public RuleDetailsEditDialog(RulesManagerServiceRemoteAsync rulesManagerServiceRemote,
    		WorkspacesManagerServiceRemoteAsync workspacesManagerServiceRemote) {
        this.rulesManagerServiceRemote = rulesManagerServiceRemote;
        this.workspacesManagerServiceRemote = workspacesManagerServiceRemote;
        setTabWidget(new TabWidget());
        
        setResizable(false);
        setButtons("");
        setClosable(true);
        setModal(true);
        setWidth(700);
        setHeight(427);
        
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

    /* (non-Javadoc)
     * @see com.extjs.gxt.ui.client.widget.Dialog#createButtons()
     */
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
//                Dispatcher.forwardEvent(GeoRepoEvents.INJECT_WKT, wkt);
            }
        });

//        this.done.disable();
        addButton(done);
    }

    /* (non-Javadoc)
     * @see com.extjs.gxt.ui.client.widget.Window#show()
     */
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
                this.tabWidget.add(new RuleDetailsTabItem(RULE_DETAILS_DIALOG_ID, model, workspacesManagerServiceRemote));
                
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
     * Reset.
     */
    public void reset() {
//        this.done.disable();
        this.tabWidget.removeAll();
        this.saveStatus.clearStatus("");
    }

    /**
     * Sets the save status.
     * 
     * @param status
     *            the status
     * @param message
     *            the message
     */
    public void setSaveStatus(EnumSaveStatus status, EnumSaveStatus message) {
        this.saveStatus.setIconStyle(status.getValue());
        this.saveStatus.setText(message.getValue());
    }

    /**
     * Sets the model.
     * 
     * @param model
     *            the new model
     */
    public void setModel(Rule model) {
        this.model = model;
    }

    /* (non-Javadoc)
     * @see com.extjs.gxt.ui.client.widget.Component#getModel()
     */
    public Rule getModel() {
        return model;
    }

    /**
     * Sets the tab widget.
     * 
     * @param tabWidget
     *            the new tab widget
     */
    public void setTabWidget(TabWidget tabWidget) {
        this.tabWidget = tabWidget;
    }

    /**
     * Gets the tab widget.
     * 
     * @return the tab widget
     */
    public TabWidget getTabWidget() {
        return tabWidget;
    }
}
