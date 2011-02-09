/*
 * $ Header: it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemote,v. 0.1 9-feb-2011 13.01.52 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 9-feb-2011 13.01.52 $
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

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.Rule;
import it.geosolutions.georepo.gui.client.model.data.LayerCustomProps;

import java.util.List;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

// TODO: Auto-generated Javadoc
/**
 * The Interface RulesManagerServiceRemote.
 */
public interface RulesManagerServiceRemote extends RemoteService {

    /**
     * The Class Util.
     */
    public static class Util {

        /** The instance. */
        private static RulesManagerServiceRemoteAsync instance;

        /**
         * Gets the instance.
         * 
         * @return the instance
         */
        public static RulesManagerServiceRemoteAsync getInstance() {
            if (instance == null) {
                instance = (RulesManagerServiceRemoteAsync) GWT.create(RulesManagerServiceRemote.class);
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint(GWT.getModuleBaseURL() + "RulesManagerServiceRemote");
            }
            return instance;
        }
    }

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
     * @throws ApplicationException
     *             the application exception
     */
    public PagingLoadResult<LayerCustomProps> getLayerCustomProps(PagingLoadConfig config, Rule rule) throws ApplicationException;

    /**
     * Sets the details props.
     * 
     * @param ruleId
     *            the rule id
     * @param customProps
     *            the custom props
     * @throws ApplicationException
     *             the application exception
     */
    public void setDetailsProps(Long ruleId, List<LayerCustomProps> customProps) throws ApplicationException;
}
