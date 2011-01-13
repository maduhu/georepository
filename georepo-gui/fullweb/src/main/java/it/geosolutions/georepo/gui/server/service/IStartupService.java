/*
 * $ Header: it.geosolutions.georepo.gui.server.service.IStartupService,v. 0.1 3-gen-2011 17.05.06 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 17.05.06 $
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
package it.geosolutions.georepo.gui.server.service;

import it.geosolutions.georepo.gui.client.configuration.GeoRepoGlobalConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Interface IStartupService.
 */
public interface IStartupService {

    /**
     * Inits the server configuration.
     * 
     * @return the dGWATCH global configuration
     */
    public GeoRepoGlobalConfiguration initServerConfiguration();

}
