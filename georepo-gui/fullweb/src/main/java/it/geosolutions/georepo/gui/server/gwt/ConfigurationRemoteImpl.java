/*
 * $Header: it.geosolutions.georepo.gui.server.gwt.ConfigurationRemoteImpl,v. 0.1 09/lug/2010 11.30.26 created by frank $
 * $Revision: 0.1 $
 * $Date: 09/lug/2010 11.30.26 $
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
package it.geosolutions.georepo.gui.server.gwt;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import it.geosolutions.georepo.gui.client.configuration.DGWATCHGlobalConfiguration;
import it.geosolutions.georepo.gui.client.service.ConfigurationRemote;
import it.geosolutions.georepo.gui.server.service.IStartupService;
import it.geosolutions.georepo.gui.spring.ApplicationContextUtil;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author frank
 * 
 */
public class ConfigurationRemoteImpl extends RemoteServiceServlet implements
		ConfigurationRemote {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6320939080552026131L;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private IStartupService startupService;

	@Override
    public void init(ServletConfig config) throws ServletException {
		super.init(config);

		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());

		ApplicationContextUtil.getInstance().setSpringContext(context);

		this.startupService = (IStartupService) ApplicationContextUtil
				.getInstance().getBean("startupService");

		logger.info("SPRING CONTEXT INITIALIZED" + this.startupService);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.geosolutions.georepo.gui.client.service.ConfigurationRemote#
	 * initServerConfiguration()
	 */
	public DGWATCHGlobalConfiguration initServerConfiguration() {
		// TODO Auto-generated method stub
		return startupService.initServerConfiguration();
	}

}
