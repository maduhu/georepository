/*
 * $ Header: it.geosolutions.georepo.gui.server.gwt.RulesManagerServiceImpl,v. 0.1 25-gen-2011 11.54.21 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.54.21 $
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
import it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemote;
import it.geosolutions.georepo.gui.server.service.IRulesManagerService;
import it.geosolutions.georepo.gui.spring.ApplicationContextUtil;

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
     * it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemote#getProfiles(com.extjs
     * .gxt.ui.client.data.PagingLoadConfig)
     */
    public PagingLoadResult<Rule> getRules(PagingLoadConfig config) throws ApplicationException {
        return rulesManagerService.getRules(config);
    }

}