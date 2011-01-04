/*
 * $ Header: it.geosolutions.georepo.gui.client.service.MembersRemote,v. 0.1 3-gen-2011 17.06.11 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.Member;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

// TODO: Auto-generated Javadoc
/**
 * The Interface MembersRemote.
 */
public interface MembersRemote extends RemoteService {

    /**
     * The Class Util.
     */
    public static class Util {

        /** The instance. */
        private static MembersRemoteAsync instance;

        /**
         * Gets the single instance of Util.
         * 
         * @return single instance of Util
         */
        public static MembersRemoteAsync getInstance() {
            if (instance == null) {
                instance = (MembersRemoteAsync) GWT.create(MembersRemote.class);
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint(GWT.getModuleBaseURL() + "MembersRemote");
            }
            return instance;
        }
    }

    /**
     * Load members.
     * 
     * @param config
     *            the config
     * @param searchText
     *            the search text
     * @return the paging load result
     * @throws ApplicationException
     *             the application exception
     */
    public PagingLoadResult<Member> loadMembers(PagingLoadConfig config, String searchText)
            throws ApplicationException;

    // /**
    // * Returns a paged set of GeoConstraints that match the passed-in search criteria.
    // *
    // * @param config the current state of paging for the result set
    // * @param searchCriteria search criteria for the GeoConstraint to look for
    // * @return paged set of GeoConstraints
    // * @throws ApplicationException if any exception occurs
    // */
    // public PagingLoadResult<GeoConstraint> getGeoConstraints(PagingLoadConfig config,
    // GeoConstraint searchCriteria) throws ApplicationException;
    //
    // /**
    // * Returns a paged set of GeoConstraints for the passed-in member.
    // *
    // * @param config the current state of paging for the result set
    // * @param connectId the ID of the member for which to retrieve the GeoConstraints
    // * @return paged set of GeoConstraints
    // * @throws ApplicationException if any exception occurs
    // */
    // public PagingLoadResult<GeoConstraint> getMemberGeoConstraints(PagingLoadConfig config,
    // String connectId) throws ApplicationException;
    //
    // /**
    // * Saves a new GeoConstraint to the database.
    // *
    // * @param geoConstraint the GeoConstraint details
    // * @return the saved version of the GeoConstraint, possibly with an updated ID value
    // * @throws ApplicationException if any exception occurs
    // */
    // public GeoConstraint saveGeoConstraint(GeoConstraint geoConstraint) throws
    // ApplicationException;
    //
    // /**
    // * Removes a GeoConstraint from the database, implicitly unassigning it from any Members that
    // are currently
    // * assigned.
    // *
    // * @param geoConstraint the GeoConstraint details
    // * @throws ApplicationException if any exception occurs
    // */
    // public void removeGeoConstraint(GeoConstraint geoConstraint) throws ApplicationException;
    //
    // /**
    // * Assigns a GeoConstraint to a member, saving the passed-in GeoConstraint before setting up
    // the
    // * association.
    // *
    // * @param connectId the connectId of the member with which the GeoConstraint will be
    // associated
    // * @param geoConstraint the GeoConstraint details
    // * @throws ApplicationException if any exception occurs
    // */
    // public GeoConstraint saveMemberGeoConstraint(String connectId, GeoConstraint geoConstraint)
    // throws ApplicationException;
    //
    // /**
    // * Unassigns a GeoConstraint from a member.
    // *
    // * @param connectId the connectId of the member with which the GeoConstraint will be
    // unassigned
    // * @param geoConstraint the GeoConstraint details
    // * @throws ApplicationException if any exception occurs
    // */
    // public void removeMemberGeoConstraint(String connectId, GeoConstraint geoConstraint) throws
    // ApplicationException;
    //
    // /**
    // * Retrieves the list of distribution nodes that are allowed to be chosen by the calling user
    // (e.g., the currently
    // * logged-in user).
    // *
    // * @return List of DistributionNode objects
    // * @throws ApplicationException
    // */
    // public List<DistributionNode> getSourceNodes() throws ApplicationException;
    //
    // /**
    // * Retrieves the list of distribution nodes to which the passed-in user will be distributed.
    // *
    // * @return List of DistributionNode objects
    // * @throws ApplicationException
    // */
    // public List<DistributionNode> getMemberNodes(String connectId) throws ApplicationException;
    //
    // /**
    // * Retrieves the list of distribution nodes to which the passed-in user will be distributed.
    // *
    // * @return List of DistributionNode objects
    // * @throws ApplicationException
    // */
    // public void saveMemberNodes(String connectId, List<Integer> nodeIds) throws
    // ApplicationException;

}