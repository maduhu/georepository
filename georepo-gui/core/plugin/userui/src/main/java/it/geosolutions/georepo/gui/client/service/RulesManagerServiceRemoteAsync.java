/*
 * $ Header: it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemoteAsync,v. 0.1 25-gen-2011 11.45.59 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.45.59 $
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
     * @param callback
     *            the callback
     * @return the rules
     */
    public void getRules(PagingLoadConfig config, boolean full, AsyncCallback<PagingLoadResult<Rule>> callback);

}
