/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.rule.detail.RuleDetailsGridWidget,v. 0.1 8-feb-2011 15.06.43 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.georepo.gui.client.model.BeanKeyValue;
import it.geosolutions.georepo.gui.client.model.Rule;
import it.geosolutions.georepo.gui.client.model.data.LayerDetailsInfo;
import it.geosolutions.georepo.gui.client.model.data.LayerStyle;
import it.geosolutions.georepo.gui.client.service.WorkspacesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.GeoRepoFormBindingWidget;

import java.util.List;

import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The Class RuleDetailsGridWidget.
 * 
 * @author Tobia di Pisa.
 */
public class RuleDetailsInfoWidget extends GeoRepoFormBindingWidget<LayerDetailsInfo> {

	private Rule theRule;
    
    /** The workspaces service. */
    private WorkspacesManagerServiceRemoteAsync workspacesService;
    
    private RuleDetailsWidget ruleDetailsWidget;
    
    private ComboBox<LayerStyle> comboStyles;      
    private TextArea cqlFilterRead;
    private TextArea cqlFilterWrite;
    private TextArea allowedArea;

    /**
     * Instantiates a new rule details grid widget.
     * 
     * @param model 
     * @param rulesService
     *            the rules service
     */
    public RuleDetailsInfoWidget(Rule model, WorkspacesManagerServiceRemoteAsync workspacesService,
    		RuleDetailsWidget ruleDetailsWidget) {
    	
        super();
        this.theRule = model;
        this.workspacesService = workspacesService;
        this.ruleDetailsWidget = ruleDetailsWidget;
        formPanel = createFormPanel();
    }

	@Override
	public FormPanel createFormPanel() {
        FormPanel fp = new FormPanel();
        fp.setFrame(true);
        fp.setHeaderVisible(false);
        fp.setHeight(400);
        fp.setWidth(650);
        
        FieldSet fieldSet = new FieldSet();
        fieldSet.setHeading("Layer Details");
        fieldSet.setCheckboxToggle(false);
        fieldSet.setCollapsible(false);

        FormLayout layout = new FormLayout();
        fieldSet.setLayout(layout);

        comboStyles = new ComboBox<LayerStyle>();
        comboStyles.setFieldLabel("Default Style");
        comboStyles.setEmptyText("Select a Style");
        comboStyles.setId(BeanKeyValue.STYLES_COMBO.getValue());
        comboStyles.setName(BeanKeyValue.STYLES_COMBO.getValue());
        comboStyles.setDisplayField(BeanKeyValue.STYLES_COMBO.getValue());
        comboStyles.setEditable(false);
        comboStyles.setAllowBlank(false);
        comboStyles.setForceSelection(true);
        comboStyles.setStore(getAvailableStyles(theRule));
        comboStyles.setTypeAhead(true);
        comboStyles.setTriggerAction(TriggerAction.ALL);
        comboStyles.addListener(Events.Select, new Listener<FieldEvent>() {

        	public void handleEvent(FieldEvent be) {
        		ruleDetailsWidget.enableSaveButton();
        	}
        	
        });        
        
        fieldSet.add(comboStyles);
        
        cqlFilterRead = new TextArea();
        cqlFilterRead.setFieldLabel("CQL Read");
        cqlFilterRead.setWidth(200);
        cqlFilterRead.setPreventScrollbars(true);
        cqlFilterRead.addListener(Events.Change, new Listener<FieldEvent>() {

        	public void handleEvent(FieldEvent be) {
        		ruleDetailsWidget.enableSaveButton();
        	}
        	
        }); 
        
        fieldSet.add(cqlFilterRead);
        
        cqlFilterWrite = new TextArea();
        cqlFilterWrite.setFieldLabel("CQL Write");
        cqlFilterWrite.setWidth(200);
        cqlFilterWrite.setPreventScrollbars(true);
        cqlFilterWrite.addListener(Events.Change, new Listener<FieldEvent>() {

        	public void handleEvent(FieldEvent be) {
        		ruleDetailsWidget.enableSaveButton();
        	}
        	
        }); 
        
        fieldSet.add(cqlFilterWrite);
        
        allowedArea = new TextArea();
        allowedArea.setFieldLabel("Allowed Area");
        allowedArea.setWidth(200);
        allowedArea.setPreventScrollbars(true);
        allowedArea.addListener(Events.Change, new Listener<FieldEvent>() {

        	public void handleEvent(FieldEvent be) {
        		ruleDetailsWidget.enableSaveButton();
        	}
        	
        }); 
        
        fieldSet.add(allowedArea);

        fp.add(fieldSet);
        
		return fp;
	}
	
    /**
     * @return LayerDetailsForm
     */
    public LayerDetailsInfo getModelData() {
    	LayerDetailsInfo layerDetailsForm = new LayerDetailsInfo();

    	layerDetailsForm.setAllowedArea(allowedArea.getValue());
    	layerDetailsForm.setCqlFilterRead(cqlFilterRead.getValue());
    	layerDetailsForm.setCqlFilterWrite(cqlFilterWrite.getValue());
    	layerDetailsForm.setDefaultStyle(comboStyles.getValue().getStyle());
    	layerDetailsForm.setRuleId(theRule.getId());
    	
        return layerDetailsForm;
    }
    
    public void bindModelData(LayerDetailsInfo layerDetailsInfo){
    	this.bindModel(layerDetailsInfo);
    	
    	String defaultStyle = layerDetailsInfo.getDefaultStyle();
    	if(defaultStyle != null){
    		comboStyles.setValue(new LayerStyle(defaultStyle));
    	}
    	    	
    	String cqlRead = layerDetailsInfo.getCqlFilterRead();
    	if(cqlRead != null){
    		cqlFilterRead.setValue(cqlRead);
    	}
    	
    	
    	String cqlWrite = layerDetailsInfo.getCqlFilterWrite();
    	if(cqlWrite != null){
    		cqlFilterWrite.setValue(cqlWrite);
    	}
    	
    	
    	String area = layerDetailsInfo.getAllowedArea();
    	if(area != null){
    		allowedArea.setValue(area);
    	}    	
    }
	
    /**
     * TODO: Call User Service here!!
     * 
     * @param rule 
     * @return ListStore<LayerStyle>
     */
    private ListStore<LayerStyle> getAvailableStyles(final Rule rule) {
        RpcProxy<List<LayerStyle>> workspacesProxy = new RpcProxy<List<LayerStyle>>() {

            @Override
            protected void load(Object loadConfig,
                    AsyncCallback<List<LayerStyle>> callback) {
                    workspacesService.getStyles(rule, callback);
            }

        };
        
        BaseListLoader<ListLoadResult<ModelData>> workspacesLoader = new BaseListLoader<ListLoadResult<ModelData>>(
                workspacesProxy);
        workspacesLoader.setRemoteSort(false);
        ListStore<LayerStyle> geoserverStyles = new ListStore<LayerStyle>(workspacesLoader);

        return geoserverStyles;
    }

	/**
	 * 
	 */
	public void disableCQLFilterButtons() {
		this.cqlFilterRead.disable();
		this.cqlFilterWrite.disable();		
	}

	/**
	 * 
	 */
	public void enableCQLFilterButtons() {
		this.cqlFilterRead.enable();
		this.cqlFilterWrite.enable();	
	}

}
