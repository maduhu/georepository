/*
 * $Header: it.geosolutions.georepo.gui.client.service.ConfigurationRemote,v. 0.1 09/lug/2010 11.19.55 created by frank $
 * $Revision: 0.1 $
 * $Date: 09/lug/2010 11.19.55 $
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
package it.geosolutions.georepo.gui.client.service;


import it.geosolutions.georepo.gui.client.configuration.DGWATCHGlobalConfiguration;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * @author frank
 * 
 */
public interface ConfigurationRemote extends RemoteService {

	public static class Util {
		private static ConfigurationRemoteAsync instance;

		public static ConfigurationRemoteAsync getInstance() {
			if (instance == null) {
				instance = (ConfigurationRemoteAsync) GWT
						.create(ConfigurationRemote.class);
				ServiceDefTarget target = (ServiceDefTarget) instance;
				target.setServiceEntryPoint(GWT.getModuleBaseURL()
						+ "ConfigurationRemote");
			}
			return instance;
		}
	}
	
	public DGWATCHGlobalConfiguration initServerConfiguration();

}