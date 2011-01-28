/*
 * $ Header: it.geosolutions.georepo.gui.server.service.impl.InstancesManagerServiceImpl,v. 0.1 28-gen-2011 11.33.07 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 28-gen-2011 11.33.07 $
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
package it.geosolutions.georepo.gui.server.service.impl;

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.GSInstance;
import it.geosolutions.georepo.gui.server.service.IInstancesManagerService;
import it.geosolutions.georepo.gui.service.GeoRepoRemoteService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

// TODO: Auto-generated Javadoc
/**
 * The Class InstancesManagerServiceImpl.
 */
@Component("instancesManagerServiceGWT")
public class InstancesManagerServiceImpl implements IInstancesManagerService {

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** The georepo remote service. */
    @Autowired
    private GeoRepoRemoteService georepoRemoteService;

    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.server.service.IInstancesManagerService#getInstances(com.extjs.gxt.ui.client.data.PagingLoadConfig)
     */
    public PagingLoadResult<GSInstance> getInstances(PagingLoadConfig config, boolean full)
            throws ApplicationException {
        int start = config.getOffset();

        List<GSInstance> instancesListDTO = new ArrayList<GSInstance>();

        if (full) {
            GSInstance all = new GSInstance();
            all.setId(-1);
            all.setName("*");
            all.setBaseURL("*");
            instancesListDTO.add(all);
        }

        long instancesCount = georepoRemoteService.getInstanceAdminService().getCount(null) + 1;

        Long t = new Long(instancesCount);

        int page = start == 0 ? start : start / config.getLimit();

        List<it.geosolutions.georepo.core.model.GSInstance> instancesList = georepoRemoteService
                .getInstanceAdminService().getList(null, page, config.getLimit());

        if (instancesList == null) {
            if (logger.isErrorEnabled())
                logger.error("No server instace found on server");
            throw new ApplicationException("No server instance found on server");
        }

        Iterator<it.geosolutions.georepo.core.model.GSInstance> it = instancesList.iterator();

        while (it.hasNext()) {
            it.geosolutions.georepo.core.model.GSInstance remote_instance = it.next();
            GSInstance local_instance = new GSInstance();

            local_instance.setId(remote_instance.getId());
            local_instance.setName(remote_instance.getName());
            local_instance.setDescription(remote_instance.getDescription());
            local_instance.setDateCreation(remote_instance.getDateCreation());
            local_instance.setBaseURL(remote_instance.getBaseURL());

            instancesListDTO.add(local_instance);
        }

        return new BasePagingLoadResult<GSInstance>(instancesListDTO, config.getOffset(), t
                .intValue());
    }
}
