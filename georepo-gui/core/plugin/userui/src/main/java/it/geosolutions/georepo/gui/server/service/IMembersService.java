/*
 * $ Header: it.geosolutions.georepo.gui.server.service.IMembersService,v. 0.1 3-gen-2011 17.06.54 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 17.06.54 $
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

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.Member;

import javax.servlet.http.HttpSession;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

// TODO: Auto-generated Javadoc
/**
 * The Interface IMembersService.
 */
public interface IMembersService {

    /**
     * Load members.
     * 
     * @param session
     *            the session
     * @param config
     *            the config
     * @param searchText
     *            the search text
     * @return the paging load result
     * @throws ApplicationException
     *             the application exception
     */
    public PagingLoadResult<Member> loadMembers(HttpSession session, PagingLoadConfig config,
            String searchText) throws ApplicationException;

    // public PagingLoadResult<GeoConstraint> getGeoConstraints(HttpSession session,
    // PagingLoadConfig config,
    // GeoConstraint searchCriteria);
    //
    // public PagingLoadResult<GeoConstraint> getMemberGeoConstraints(HttpSession session,
    // PagingLoadConfig config,
    // String connectId);
    //
    // public GeoConstraint saveGeoConstraint(HttpSession session, GeoConstraint geoConstraint);
    //
    // public void removeGeoConstraint(HttpSession session, GeoConstraint geoConstraint);
    //
    // public GeoConstraint saveMemberGeoConstraint(HttpSession session, String connectId,
    // GeoConstraint geoConstraint);
    //
    // public void removeMemberGeoConstraint(HttpSession session, String connectId, GeoConstraint
    // geoConstraint);

}
