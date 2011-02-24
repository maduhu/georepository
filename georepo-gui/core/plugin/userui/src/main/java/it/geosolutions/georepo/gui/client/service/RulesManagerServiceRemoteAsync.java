/*
 * $ Header: it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemoteAsync,v. 0.1 9-feb-2011 13.01.37 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 9-feb-2011 13.01.37 $
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
package it.geosolutions.georepo.gui.client.service;

import it.geosolutions.georepo.gui.client.model.Rule;
import it.geosolutions.georepo.gui.client.model.data.LayerAttribUI;
import it.geosolutions.georepo.gui.client.model.data.LayerCustomProps;
import it.geosolutions.georepo.gui.client.model.data.LayerDetailsInfo;
import it.geosolutions.georepo.gui.client.model.data.LayerStyle;

import java.util.List;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Interface RulesManagerServiceRemoteAsync.
 */
public interface RulesManagerServiceRemoteAsync {

    /**
     * Gets the rules.
     * 
     * @param config
     *            the config
     * @param full
     *            the full
     * @param callback
     *            the callback
     * @return the rules
     */
    public void getRules(PagingLoadConfig config, boolean full,
            AsyncCallback<PagingLoadResult<Rule>> callback);

    /**
     * Save rule
     * 
     * @param rule
     *            the rule
     * @param callback
     *            the callback
     */
    public void saveRule(Rule rules, AsyncCallback<PagingLoadResult<Rule>> callback);
    
    /**
     * Save all rules.
     * 
     * @param rules
     *            the rules
     * @param callback
     *            the callback
     */
    public void saveAllRules(List<Rule> rules, AsyncCallback<PagingLoadResult<Rule>> callback);

    /**
     * Gets the layer custom props.
     * 
     * @param config
     *            the config
     * @param rule
     *            the rule
     * @param callback
     *            the callback
     * @return the layer custom props
     */
    public void getLayerCustomProps(PagingLoadConfig config, Rule rule,
            AsyncCallback<PagingLoadResult<LayerCustomProps>> callback);

    /**
     * Sets the details props.
     * 
     * @param ruleId
     *            the rule id
     * @param customProps
     *            the custom props
     * @param callback
     *            the callback
     */
    public void setDetailsProps(Long ruleId, List<LayerCustomProps> customProps, AsyncCallback<PagingLoadResult<LayerCustomProps>> callback);

    /**
     * Gets the layer attributes.
     * 
     * @param rule
     *            the rule
     * @param callback
     *            the callback
     */
    public void getLayerAttributes(Rule rule, AsyncCallback<List<LayerAttribUI>> callback);
    
    /**
     * @param ruleId
     * @param layerAttributes
     * @param callback
     */
    public void setLayerAttributes(Long ruleId, List<LayerAttribUI> layerAttributes, AsyncCallback<List<LayerAttribUI>> callback);
    
    /**
     * @param layerDetailsForm
     * @param callback
     */
    public void saveLayerDetailsInfo(LayerDetailsInfo layerDetailsForm, List<LayerStyle> layerStyles, AsyncCallback<LayerDetailsInfo> callback);
    
    /**
     * @param rule
     * @param callback
     */
    public void getLayerDetailsInfo(Rule rule, AsyncCallback<LayerDetailsInfo> callback);
    
}
