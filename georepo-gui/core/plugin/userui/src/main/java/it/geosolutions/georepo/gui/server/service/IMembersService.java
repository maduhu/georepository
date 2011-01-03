/*
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

import javax.servlet.http.HttpSession;

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.DistributionNode;
import it.geosolutions.georepo.gui.client.model.GeoConstraint;
import it.geosolutions.georepo.gui.client.model.Member;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

import java.util.List;

/**
 * @author Tobia Di Pisa
 *
 */
public interface IMembersService {

	/**
	 * @param config
	 * @param searchText
	 * @return
	 * @throws ApplicationException
	 */
	public PagingLoadResult<Member> loadMembers(HttpSession session, PagingLoadConfig config,
			String searchText) throws ApplicationException;

//    public PagingLoadResult<GeoConstraint> getGeoConstraints(HttpSession session, PagingLoadConfig config,
//			GeoConstraint searchCriteria);
//
//    public PagingLoadResult<GeoConstraint> getMemberGeoConstraints(HttpSession session, PagingLoadConfig config,
//			String connectId);
//
//    public GeoConstraint saveGeoConstraint(HttpSession session, GeoConstraint geoConstraint);
//
//    public void removeGeoConstraint(HttpSession session, GeoConstraint geoConstraint);
//
//    public GeoConstraint saveMemberGeoConstraint(HttpSession session, String connectId, GeoConstraint geoConstraint);
//
//    public void removeMemberGeoConstraint(HttpSession session, String connectId, GeoConstraint geoConstraint);

}
