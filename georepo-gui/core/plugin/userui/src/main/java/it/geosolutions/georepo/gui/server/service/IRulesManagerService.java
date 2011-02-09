/*
 * $ Header: it.geosolutions.georepo.gui.server.service.IRulesManagerService,v. 0.1 9-feb-2011 13.02.31 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 9-feb-2011 13.02.31 $
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
package it.geosolutions.georepo.gui.server.service;

import java.util.List;

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.Rule;
import it.geosolutions.georepo.gui.client.model.data.LayerCustomProps;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

// TODO: Auto-generated Javadoc
/**
 * The Interface IRulesManagerService.
 */
public interface IRulesManagerService {

    /**
     * Gets the rules.
     * 
     * @param config
     *            the config
     * @param full
     *            the full
     * @return the rules
     * @throws ApplicationException
     *             the application exception
     */
    public PagingLoadResult<Rule> getRules(PagingLoadConfig config, boolean full) throws ApplicationException;
    
    /**
     * Save all rules.
     * 
     * @param rules
     *            the rules
     * @throws ApplicationException
     *             the application exception
     */
    public void saveAllRules(List<Rule> rules) throws ApplicationException;

    /**
     * Gets the layer custom props.
     * 
     * @param config
     *            the config
     * @param rule
     *            the rule
     * @return the layer custom props
     */
    public PagingLoadResult<LayerCustomProps> getLayerCustomProps(PagingLoadConfig config, Rule rule);

    /**
     * Sets the details props.
     * 
     * @param ruleId
     *            the rule id
     * @param customProps
     *            the custom props
     */
    public void setDetailsProps(Long ruleId, List<LayerCustomProps> customProps);
}
