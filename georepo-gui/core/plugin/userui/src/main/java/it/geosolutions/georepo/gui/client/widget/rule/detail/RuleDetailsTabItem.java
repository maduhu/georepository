/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.rule.detail.RuleDetailsTabItem,v. 0.1 8-feb-2011 15.06.43 created by afabiani <alessio.fabiani at geo-solutions.it> $
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
import it.geosolutions.georepo.gui.client.service.WorkspacesManagerServiceRemoteAsync;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.TabItem;

/**
 * The Class RuleDetailsTabItem.
 * 
 * @author Tobia di Pisa.
 */
public class RuleDetailsTabItem extends TabItem {

    /** The rule details widget. */
    private RuleDetailsWidget ruleDetailsWidget;
    private Rule theRule;
    
    /**
     * Instantiates a new rule details tab item.
     * 
     * @param tabItemId
     *            the tab item id
     */
    private RuleDetailsTabItem(String tabItemId) {
        // TODO: add I18n message
        // super(I18nProvider.getMessages().profiles());
        super("Layer Details");
        setId(tabItemId);
        setIcon(Resources.ICONS.addAOI());
    }

    /**
     * Instantiates a new rule details tab item.
     * 
     * @param tabItemId
     *            the tab item id
     * @param model 
     * @param rulesService
     *            the rules service
     */
    public RuleDetailsTabItem(String tabItemId, Rule model, WorkspacesManagerServiceRemoteAsync workspacesService) {
        this(tabItemId);
        this.theRule = model;

        setRuleDetailsWidget(new RuleDetailsWidget(this.theRule, workspacesService));
        add(getRuleDetailsWidget());

        setScrollMode(Scroll.NONE);
        
		this.addListener(Events.Select, new Listener<BaseEvent>(){

			public void handleEvent(BaseEvent be) {	
				if(ruleDetailsWidget.getRuleDetailsInfo().getModel() == null){
					Dispatcher.forwardEvent(GeoRepoEvents.LOAD_LAYER_DETAILS, theRule);
				}	
				
				if(ruleDetailsWidget.getRuleDetailsGrid().getStore().getCount() < 1){
					ruleDetailsWidget.getRuleDetailsGrid().getLoader().load();
				}	
			}
			
		});
    }

    /**
     * Sets the rule details widget.
     * 
     * @param ruleDetailsWidget
     *            the new rule details widget
     */
    public void setRuleDetailsWidget(RuleDetailsWidget ruleDetailsWidget) {
        this.ruleDetailsWidget = ruleDetailsWidget;
    }

    /**
     * Gets the rule details widget.
     * 
     * @return the rule details widget
     */
    public RuleDetailsWidget getRuleDetailsWidget() {
        return ruleDetailsWidget;
    }

}
