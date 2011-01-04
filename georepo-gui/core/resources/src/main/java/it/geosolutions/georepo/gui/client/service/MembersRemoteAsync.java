/*
 * $ Header: it.geosolutions.georepo.gui.client.service.MembersRemoteAsync,v. 0.1 3-gen-2011 17.06.11 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 17.06.11 $
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

import it.geosolutions.georepo.gui.client.model.Member;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Interface MembersRemoteAsync.
 */
public interface MembersRemoteAsync {

    /**
     * Load members.
     * 
     * @param config
     *            the config
     * @param searchText
     *            the search text
     * @param callback
     *            the callback
     */
    public void loadMembers(PagingLoadConfig config, String searchText,
            AsyncCallback<PagingLoadResult<Member>> callback);

    // public void getGeoConstraints(PagingLoadConfig config, GeoConstraint searchCriteria,
    // AsyncCallback<PagingLoadResult<GeoConstraint>> callback);
    //
    // public void getMemberGeoConstraints(PagingLoadConfig config, String connectId,
    // AsyncCallback<PagingLoadResult<GeoConstraint>> callback);
    //
    // public void saveGeoConstraint(GeoConstraint geoConstraint,
    // AsyncCallback<GeoConstraint> callback);
    //
    // public void removeGeoConstraint(GeoConstraint geoConstraint,
    // AsyncCallback<Void> callback);
    //
    // public void saveMemberGeoConstraint(String connectId, GeoConstraint geoConstraint,
    // AsyncCallback<GeoConstraint> callback);
    //
    // public void removeMemberGeoConstraint(String connectId, GeoConstraint geoConstraint,
    // AsyncCallback<Void> callback);
    //
    // public void getSourceNodes(
    // AsyncCallback<List<DistributionNode>> callback) throws ApplicationException;
    //
    // public void getMemberNodes(String connectId,
    // AsyncCallback<List<DistributionNode>> callback) throws ApplicationException;
    //
    // public void saveMemberNodes(String connectId, List<Integer> nodeIds,
    // AsyncCallback<Void> callback) throws ApplicationException;
}
