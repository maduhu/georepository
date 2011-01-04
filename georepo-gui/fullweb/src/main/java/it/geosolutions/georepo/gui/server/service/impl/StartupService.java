/*
 * $ Header: it.geosolutions.georepo.gui.server.service.impl.StartupService,v. 0.1 3-gen-2011 17.05.14 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 17.05.14 $
 *
 * ====================================================================
 *
 * Copyright (C) 2010 GeoSolutions S.A.S.
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
package it.geosolutions.georepo.gui.server.service.impl;

import it.geosolutions.georepo.gui.client.configuration.DGWATCHGlobalConfiguration;
import it.geosolutions.georepo.gui.server.service.IStartupService;

import org.springframework.beans.factory.annotation.Autowired;

// TODO: Auto-generated Javadoc
/**
 * The Class StartupService.
 */
public class StartupService implements IStartupService {

    /** The dgwatch global configuration. */
    @Autowired
    private DGWATCHGlobalConfiguration dgwatchGlobalConfiguration;

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.server.service.IStartupService#initServerConfiguration()
     */
    public DGWATCHGlobalConfiguration initServerConfiguration() {
        // TODO Auto-generated method stub
        return dgwatchGlobalConfiguration;
    }

}
