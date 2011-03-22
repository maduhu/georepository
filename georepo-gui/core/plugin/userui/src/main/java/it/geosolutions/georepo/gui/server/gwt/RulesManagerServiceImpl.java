/*
 * $ Header: it.geosolutions.georepo.gui.server.gwt.RulesManagerServiceImpl,v. 0.1 9-feb-2011 13.02.25 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 9-feb-2011 13.02.25 $
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
package it.geosolutions.georepo.gui.server.gwt;

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.Rule;
import it.geosolutions.georepo.gui.client.model.data.LayerAttribUI;
import it.geosolutions.georepo.gui.client.model.data.LayerCustomProps;
import it.geosolutions.georepo.gui.client.model.data.LayerDetailsInfo;
import it.geosolutions.georepo.gui.client.model.data.LayerLimitsInfo;
import it.geosolutions.georepo.gui.client.model.data.LayerStyle;
import it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemote;
import it.geosolutions.georepo.gui.server.service.IRulesManagerService;
import it.geosolutions.georepo.gui.spring.ApplicationContextUtil;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// TODO: Auto-generated Javadoc
/**
 * The Class RulesManagerServiceImpl.
 */
public class RulesManagerServiceImpl extends RemoteServiceServlet implements
        RulesManagerServiceRemote {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5342510982782032063L;

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** The rules manager service. */
    private IRulesManagerService rulesManagerService;

    /**
     * Instantiates a new rules manager service impl.
     */
    public RulesManagerServiceImpl() {
        this.rulesManagerService = (IRulesManagerService) ApplicationContextUtil.getInstance()
                .getBean("rulesManagerServiceGWT");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemote#getRules(com.extjs
     * .gxt.ui.client.data.PagingLoadConfig)
     */
    public PagingLoadResult<Rule> getRules(PagingLoadConfig config, boolean full)
            throws ApplicationException {
        PagingLoadResult<Rule> ret = rulesManagerService.getRules(config, full);
        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemote#saveAllRules(java.util
     * .List)
     */
    public void saveRule(Rule rule) throws ApplicationException {
        rulesManagerService.saveRule(rule);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemote#saveAllRules(java.util
     * .List)
     */
    public void deleteRule(Rule rule) throws ApplicationException {
        rulesManagerService.deleteRule(rule);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemote#saveAllRules(java.util
     * .List)
     */
    public void saveAllRules(List<Rule> rules) throws ApplicationException {
        rulesManagerService.saveAllRules(rules);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemote#getLayerCustomProps(
     * com.extjs.gxt.ui.client.data.PagingLoadConfig, it.geosolutions.georepo.gui.client.model.Rule)
     */
    public PagingLoadResult<LayerCustomProps> getLayerCustomProps(PagingLoadConfig config, Rule rule)
            throws ApplicationException {
        return rulesManagerService.getLayerCustomProps(config, rule);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemote#setDetailsProps(java
     * .lang.Long, it.geosolutions.georepo.gui.client.model.data.LayerCustomProps)
     */
    public void setDetailsProps(Long ruleId, List<LayerCustomProps> customProps)
            throws ApplicationException {
        rulesManagerService.setDetailsProps(ruleId, customProps);
    }

    public void shift(long priorityStart, long offset) throws ApplicationException {
        rulesManagerService.shift(priorityStart, offset);
    }

    public void swap(long id1, long id2) throws ApplicationException {
        rulesManagerService.swap(id1, id2);
    }

    public void findRule(Rule rule) throws ApplicationException, Exception {
        rulesManagerService.findRule(rule);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemote#setLayerAttributes(java
     * .lang.Long, java.util.List)
     */
    public void setLayerAttributes(Long ruleId, List<LayerAttribUI> layerAttributes)
            throws ApplicationException {
        rulesManagerService.setLayerAttributes(ruleId, layerAttributes);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemote#saveLayerDetails(it.
     * geosolutions.georepo.gui.client.model.data.LayerDetailsForm)
     */
    public LayerDetailsInfo saveLayerDetailsInfo(LayerDetailsInfo layerDetailsForm,
            List<LayerStyle> layerStyles) throws ApplicationException {
        return rulesManagerService.saveLayerDetailsInfo(layerDetailsForm, layerStyles);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemote#getLayerDetailsInfo(
     * it.geosolutions.georepo.gui.client.model.Rule)
     */
    public LayerDetailsInfo getLayerDetailsInfo(Rule rule) throws ApplicationException {
        return rulesManagerService.getLayerDetailsInfo(rule);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemote#getLayerAttributes(com
     * .extjs.gxt.ui.client.data.PagingLoadConfig, it.geosolutions.georepo.gui.client.model.Rule)
     */
    public List<LayerAttribUI> getLayerAttributes(Rule rule) throws ApplicationException {
        return rulesManagerService.getLayerAttributes(rule);
    }    

    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemote#saveLayerLimitsInfo(it.geosolutions.georepo.gui.client.model.data.LayerLimitsInfo)
     */
    public LayerLimitsInfo saveLayerLimitsInfo(LayerLimitsInfo layerLimitsForm) throws ApplicationException{
        return rulesManagerService.saveLayerLimitsInfo(layerLimitsForm);
    }

    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemote#getLayerLimitsInfo(it.geosolutions.georepo.gui.client.model.Rule)
     */
    public LayerLimitsInfo getLayerLimitsInfo(Rule rule) throws ApplicationException{
        return rulesManagerService.getLayerLimitsInfo(rule);
    }
}