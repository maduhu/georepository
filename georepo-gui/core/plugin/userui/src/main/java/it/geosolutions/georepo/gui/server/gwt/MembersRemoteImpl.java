/*
 * $ Header: it.geosolutions.georepo.gui.server.gwt.MembersRemoteImpl,v. 0.1 3-gen-2011 17.06.54 created by afabiani <alessio.fabiani at geo-solutions.it> $
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
package it.geosolutions.georepo.gui.server.gwt;

import it.geosolutions.georepo.gui.client.model.Member;
import it.geosolutions.georepo.gui.client.service.MembersRemote;
import it.geosolutions.georepo.gui.server.service.IMembersService;
import it.geosolutions.georepo.gui.spring.ApplicationContextUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// TODO: Auto-generated Javadoc
/**
 * The Class MembersRemoteImpl.
 */
public class MembersRemoteImpl extends RemoteServiceServlet implements MembersRemote {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8113897159826674221L;

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** The members service. */
    private IMembersService membersService;

    /**
     * Instantiates a new members remote impl.
     */
    public MembersRemoteImpl() {
        this.membersService = (IMembersService) ApplicationContextUtil.getInstance().getBean(
                "membersService");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.client.service.MembersRemote#loadMembers(com.extjs.gxt.ui.client
     * .data.PagingLoadConfig, java.lang.String)
     */
    public PagingLoadResult<Member> loadMembers(PagingLoadConfig config, String searchText) {
        return membersService.loadMembers(getThreadLocalRequest().getSession(), config, searchText);
    }

    // @Override
    // public PagingLoadResult<GeoConstraint> getGeoConstraints(PagingLoadConfig config,
    // GeoConstraint searchCriteria) {
    // return membersService.getGeoConstraints(getThreadLocalRequest().getSession(), config,
    // searchCriteria);
    // }
    //
    // @Override
    // public PagingLoadResult<GeoConstraint> getMemberGeoConstraints(PagingLoadConfig config,
    // String connectId) {
    // return membersService.getMemberGeoConstraints(getThreadLocalRequest().getSession(), config,
    // connectId);
    // }
    //
    // @Override
    // public GeoConstraint saveGeoConstraint(GeoConstraint geoConstraint) throws
    // ApplicationException {
    // return membersService.saveGeoConstraint(getThreadLocalRequest().getSession(), geoConstraint);
    // }
    //
    // @Override
    // public void removeGeoConstraint(GeoConstraint geoConstraint) throws ApplicationException {
    // membersService.removeGeoConstraint(getThreadLocalRequest().getSession(), geoConstraint);
    // }
    //
    // @Override
    // public GeoConstraint saveMemberGeoConstraint(String connectId, GeoConstraint geoConstraint)
    // throws ApplicationException {
    // return membersService.saveMemberGeoConstraint(getThreadLocalRequest().getSession(),
    // connectId, geoConstraint);
    // }
    //
    // @Override
    // public void removeMemberGeoConstraint(String connectId, GeoConstraint geoConstraint) throws
    // ApplicationException {
    // membersService.removeMemberGeoConstraint(getThreadLocalRequest().getSession(), connectId,
    // geoConstraint);
    // }
    //
    // @Override
    // public List<DistributionNode> getSourceNodes() throws ApplicationException {
    // return membersService.getSourceNodes(getThreadLocalRequest().getSession());
    // }
    //
    // @Override
    // public List<DistributionNode> getMemberNodes(String connectId) throws ApplicationException {
    // return membersService.getMemberNodes(getThreadLocalRequest().getSession(), connectId);
    // }
    //
    // @Override
    // public void saveMemberNodes(String connectId, List<Integer> nodeIds) throws
    // ApplicationException {
    // membersService.saveMemberNodes(getThreadLocalRequest().getSession(), connectId, nodeIds);
    // }
}
