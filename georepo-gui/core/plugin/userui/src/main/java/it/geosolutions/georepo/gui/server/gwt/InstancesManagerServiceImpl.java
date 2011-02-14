/*
 * $ Header: it.geosolutions.georepo.gui.server.gwt.InstancesManagerServiceImpl,v. 0.1 28-gen-2011 11.36.40 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 28-gen-2011 11.36.40 $
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
import it.geosolutions.georepo.gui.client.model.GSInstance;
import it.geosolutions.georepo.gui.client.service.InstancesManagerServiceRemote;
import it.geosolutions.georepo.gui.server.service.IInstancesManagerService;
import it.geosolutions.georepo.gui.spring.ApplicationContextUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// TODO: Auto-generated Javadoc
/**
 * The Class InstancesManagerServiceImpl.
 */
public class InstancesManagerServiceImpl extends RemoteServiceServlet implements
InstancesManagerServiceRemote{


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4502086167905144601L;    

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** The instances manager service. */
    private IInstancesManagerService instancesManagerService;

    /**
     * Instantiates a new instances manager service impl.
     */
    public InstancesManagerServiceImpl() {
        this.instancesManagerService = (IInstancesManagerService) ApplicationContextUtil
                .getInstance().getBean("instancesManagerServiceGWT");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.client.service.InstancesManagerServiceRemote#getInstances(com.extjs
     * .gxt.ui.client.data.PagingLoadConfig)
     */
    public PagingLoadResult<GSInstance> getInstances(PagingLoadConfig config, boolean full)
            throws ApplicationException {
        return instancesManagerService.getInstances(config, full);
    }
    


    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.client.service.InstancesManagerServiceRemote#deleteInstance(it.geosolutions.georepo.gui.client.model.Instance)
     */
    public void deleteInstance(GSInstance instance) throws ApplicationException {
        instancesManagerService.deleteInstance(instance);
    }

    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.client.service.InstancesManagerServiceRemote#saveInstance(it.geosolutions.georepo.gui.client.model.Instance)
     */
    public void saveInstance(GSInstance instance) throws ApplicationException {
        instancesManagerService.saveInstance(instance);
    }

}