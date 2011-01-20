/*
 * $Header: com.digitalglobe.dgwatch.gui.client.widget.tab.GeoRSSTabItem,v. 0.1 09/lug/2010 10.23.26 created by frank $
 * $Revision: 0.1 $
 * $Date: 09/lug/2010 10.23.26 $
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

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.GSUser;
import it.geosolutions.georepo.gui.server.service.IGsUsersManagerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

/**
 * @author tobaro
 *
 */
@Component("gsUsersManagerServiceGWT")
public class GsUsersManagerServiceImpl implements IGsUsersManagerService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	@Autowired
//	private DGWATCHRemoteService dgwatchRemoteService;
	
	/* (non-Javadoc)
	 * @see com.digitalglobe.dgwatch.gui.server.service.IFeatureService#loadFeature(com.extjs.gxt.ui.client.data.PagingLoadConfig, java.lang.String)
	 */
	public PagingLoadResult<GSUser> getGsUsers(PagingLoadConfig config) throws ApplicationException {
	    List<GSUser> usersListDTO = new ArrayList<GSUser>();
            
	    GSUser alfa = new GSUser();
            alfa.setName("afabiani");
            alfa.setFullName("Alessio Fabiani");
            alfa.setEnabled(false);
            alfa.setEmailAddress("alessio.fabiani@gmail.com");
            alfa.setDateCreation(new Date());
            
            usersListDTO.add(alfa);
            
            return new BasePagingLoadResult<GSUser>(usersListDTO, 0, 1);
	}
}
