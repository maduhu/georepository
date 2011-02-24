/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.rule.detail.RuleDetailsWidget,v. 0.1 8-feb-2011 15.06.43 created by afabiani <alessio.fabiani at geo-solutions.it> $
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
import it.geosolutions.georepo.gui.client.model.Rule;
import it.geosolutions.georepo.gui.client.model.data.LayerDetailsInfo;
import it.geosolutions.georepo.gui.client.service.WorkspacesManagerServiceRemoteAsync;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

/**
 * The Class RuleDetailsWidget.
 * 
 * @author Tobia di Pisa.
 */
public class RuleDetailsWidget extends ContentPanel {
    
	private Rule theRule;
	
    /** The rule details info. */
    private RuleDetailsInfoWidget ruleDetailsInfo;
    
    /** The rule details grid. */
    private RuleDetailsGridWidget ruleDetailsGrid;	
	
    /** The tool bar. */
    private ToolBar toolBar;
    
    /** The save button. */
    private Button saveLayerDetailsButton;

	/**
     * Instantiates a new rule details widget.
     * 
     * @param model 
     * @param rulesService
     *            the rules service
     */
    public RuleDetailsWidget(Rule model, WorkspacesManagerServiceRemoteAsync workspacesService) {
        this.theRule = model;
        
        setHeaderVisible(false);
        setFrame(true);
        setHeight(330);
        setWidth(690);
        setLayout(new FitLayout());

        ruleDetailsInfo = new RuleDetailsInfoWidget(this.theRule, workspacesService, this);
        add(ruleDetailsInfo.getFormPanel());
        
        ruleDetailsGrid = new RuleDetailsGridWidget(this.theRule, workspacesService, this);
        add(ruleDetailsGrid.getGrid());

        super.setMonitorWindowResize(true);

        setScrollMode(Scroll.AUTOY);
        
        this.toolBar = new ToolBar();
        
        this.saveLayerDetailsButton = new Button("Apply Changes");
        saveLayerDetailsButton.setIcon(Resources.ICONS.save());
        saveLayerDetailsButton.disable();

        saveLayerDetailsButton.addListener(Events.OnClick, new Listener<ButtonEvent>() {

        	public void handleEvent(ButtonEvent be) {

        		disableSaveButton();
        		
        		LayerDetailsInfo detailsInfoModel = ruleDetailsInfo.getModelData();        	
        		Dispatcher.forwardEvent(GeoRepoEvents.SAVE_LAYER_DETAILS, detailsInfoModel);

        		Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
        				"GeoServer Rules: Layer Details", "Apply Changes" }); 

        	}
        });
        
        this.toolBar.add(new FillToolItem());
        this.toolBar.add(saveLayerDetailsButton);        
        setBottomComponent(this.toolBar);        
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.widget.Component#onWindowResize(int, int)
     */
    @Override
    protected void onWindowResize(int width, int height) {
//        super.setWidth(width - 5);
        super.layout();
    }
    
    /**
     * Sets the rule details info.
     * 
     * @param layerCustomPropsInfo
     *            the new rule details info
     */
    public void setRuleDetailsInfo(RuleDetailsInfoWidget layerCustomPropsInfo) {
        this.ruleDetailsInfo = layerCustomPropsInfo;
    }

    /**
     * Gets the rule details info.
     * 
     * @return the rule details info
     */
    public RuleDetailsInfoWidget getRuleDetailsInfo() {
        return ruleDetailsInfo;
    }
    
    /**
	 * @return the ruleDetailsGrid
	 */
	public RuleDetailsGridWidget getRuleDetailsGrid() {
		return ruleDetailsGrid;
	}

	/**
	 * @param ruleDetailsGrid the ruleDetailsGrid to set
	 */
	public void setRuleDetailsGrid(RuleDetailsGridWidget ruleDetailsGrid) {
		this.ruleDetailsGrid = ruleDetailsGrid;
	}
	
    /**
	 * 
	 */
	public void disableSaveButton() {
		if(this.saveLayerDetailsButton.isEnabled())
			this.saveLayerDetailsButton.disable();
	}
	
    /**
	 * @return 
	 */
	public void enableSaveButton() {
		if(!this.saveLayerDetailsButton.isEnabled())
			this.saveLayerDetailsButton.enable();
	}
    
}
