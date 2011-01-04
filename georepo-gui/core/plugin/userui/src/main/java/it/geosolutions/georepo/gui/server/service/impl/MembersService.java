/*
 * $ Header: it.geosolutions.georepo.gui.server.service.impl.MembersService,v. 0.1 3-gen-2011 17.06.53 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 17.06.53 $
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

import it.geosolutions.georepo.api.dto.RegisteredUser;
import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.Member;
import it.geosolutions.georepo.gui.server.DGWATCHKeySessionValues;
import it.geosolutions.georepo.gui.server.service.IMembersService;
import it.geosolutions.georepo.gui.service.DGWATCHRemoteService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

// TODO: Auto-generated Javadoc
/**
 * The Class MembersService.
 */
@Component("membersService")
public class MembersService implements IMembersService {

    /** The logger. */
    private final Logger logger = Logger.getLogger(this.getClass());

    /** The dgwatch remote service. */
    @Autowired
    private DGWATCHRemoteService dgwatchRemoteService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.server.service.IMembersService#loadMembers(javax.servlet.http
     * .HttpSession, com.extjs.gxt.ui.client.data.PagingLoadConfig, java.lang.String)
     */
    public PagingLoadResult<Member> loadMembers(HttpSession session, PagingLoadConfig config,
            String searchText) throws ApplicationException {

        String token = (String) session.getAttribute(DGWATCHKeySessionValues.USER_LOGGED_TOKEN
                .getValue());

        if (token == null)
            throw new ApplicationException("Session Expired");

        List<Member> memberListDTO = new ArrayList<Member>();
        int start = config.getOffset();

        long memberCount = dgwatchRemoteService.getUserProvider().getUsersCount(searchText);
        // long memberCount = dgwatchRemoteService.getInternalService().getUsersCount(token);

        Long t = new Long(memberCount);

        // int page = start == 0 ? start : start / config.getLimit();

        List<RegisteredUser> memberList = dgwatchRemoteService.getUserProvider().getUsers(
                searchText, start, config.getLimit());
        // List<RegisteredUser> memberList =
        // dgwatchRemoteService.getInternalService().getUsers(token, start, config.getLimit());

        if (memberList == null) {
            throw new ApplicationException("There are no results");
        }

        Iterator<RegisteredUser> it = memberList.iterator();

        while (it.hasNext()) {
            RegisteredUser registeredUser = it.next();
            Member member = new Member();
            member.setConnectId(registeredUser.getId());
            member.setMemberName(registeredUser.getUsername());
            member.setOrganization("DUMMY_ORG");
            memberListDTO.add(member);
        }

        return new BasePagingLoadResult<Member>(memberListDTO, config.getOffset(), t.intValue());
    }

    // @Override
    // public PagingLoadResult<GeoConstraint> getGeoConstraints(HttpSession session,
    // PagingLoadConfig config,
    // GeoConstraint searchCriteria) {
    //
    // String token = (String)
    // session.getAttribute(DGWATCHKeySessionValues.USER_LOGGED_TOKEN.getValue());
    //
    // if (token == null) {
    // throw new ApplicationException("Session Expired");
    // }
    //
    // int geoConstraintCount = dgwatchRemoteService.getInternalService().getGeoConstraintCount(
    // token, convertToInternalGeoConstraint(searchCriteria));
    //
    // List<GeoConstraint> geoConstraints = new ArrayList<GeoConstraint>();
    //
    // List<it.geosolutions.georepo.internal.response.GeoConstraint> geoConstraintDtoList =
    // dgwatchRemoteService.getInternalService().getGeoConstraints(
    // token, convertToInternalGeoConstraint(searchCriteria), config.getOffset() + 1,
    // config.getLimit());
    //
    // if (geoConstraintDtoList == null) {
    // throw new ApplicationException("There are no results");
    // }
    //
    // for (it.geosolutions.georepo.internal.response.GeoConstraint dto : geoConstraintDtoList) {
    // geoConstraints.add(convertToModelGeoConstraint(dto));
    // }
    //
    // return new BasePagingLoadResult<GeoConstraint>(geoConstraints, config.getOffset(),
    // geoConstraintCount);
    // }
    //
    // @Override
    // public PagingLoadResult<GeoConstraint> getMemberGeoConstraints(HttpSession session,
    // PagingLoadConfig config,
    // String connectId) {
    //
    // String token = (String)
    // session.getAttribute(DGWATCHKeySessionValues.USER_LOGGED_TOKEN.getValue());
    //
    // if (token == null) {
    // throw new ApplicationException("Session Expired");
    // }
    //
    // List<GeoConstraint> geoConstraints = new ArrayList<GeoConstraint>();
    //
    // List<it.geosolutions.georepo.internal.response.GeoConstraint> geoConstraintDtoList =
    // dgwatchRemoteService.getInternalService().getMemberGeoConstraints(token, connectId);
    //
    // if (geoConstraintDtoList == null) {
    // throw new ApplicationException("There are no results");
    // }
    //
    // for (it.geosolutions.georepo.internal.response.GeoConstraint dto : geoConstraintDtoList) {
    // geoConstraints.add(convertToModelGeoConstraint(dto));
    // }
    //
    // return new BasePagingLoadResult<GeoConstraint>(geoConstraints, config.getOffset(),
    // geoConstraints.size());
    // }
    //
    // /**
    // * Converts the internal DGWatch model representation of a GeoConstraint to the internal
    // DGWatch API
    // * representation of a GeoConstraint.
    // *
    // * @param gcIn
    // * @return
    // */
    // private it.geosolutions.georepo.internal.response.GeoConstraint
    // convertToInternalGeoConstraint(GeoConstraint gcIn) {
    // it.geosolutions.georepo.internal.response.GeoConstraint gcOut =
    // new it.geosolutions.georepo.internal.response.GeoConstraint();
    //
    // gcOut.setId(gcIn.getId());
    // gcOut.setName(gcIn.getName());
    // gcOut.setWkt(gcIn.getWkt());
    // return gcOut;
    // }
    //
    // /**
    // * Converts the internal DGWatch API representation of a GeoConstraint to the internal DGWatch
    // model
    // * representation of a GeoConstraint.
    // *
    // * @param gcIn
    // * @return
    // */
    // private GeoConstraint
    // convertToModelGeoConstraint(it.geosolutions.georepo.internal.response.GeoConstraint gcIn) {
    // GeoConstraint gcOut = new GeoConstraint();
    //
    // gcOut.setId(gcIn.getId());
    // gcOut.setName(gcIn.getName());
    // gcOut.setWkt(gcIn.getWkt());
    // return gcOut;
    // }
    //
    // @Override
    // public GeoConstraint saveGeoConstraint(HttpSession session, GeoConstraint geoConstraint) {
    // String token = (String)
    // session.getAttribute(DGWATCHKeySessionValues.USER_LOGGED_TOKEN.getValue());
    //
    // if (token == null) {
    // throw new ApplicationException("Session Expired");
    // }
    //
    // it.geosolutions.georepo.internal.response.GeoConstraint geoConstraintToSave =
    // convertToInternalGeoConstraint(geoConstraint);
    //
    // it.geosolutions.georepo.internal.response.GeoConstraint savedGeoConstraint =
    // dgwatchRemoteService.getInternalService().saveGeoConstraint(token, geoConstraintToSave);
    //
    // if (savedGeoConstraint == null) {
    // throw new ApplicationException("Error saving GeoConstraint");
    // }
    //
    // return convertToModelGeoConstraint(savedGeoConstraint);
    // }
    //
    // @Override
    // public void removeGeoConstraint(HttpSession session, GeoConstraint geoConstraint) {
    // String token = (String)
    // session.getAttribute(DGWATCHKeySessionValues.USER_LOGGED_TOKEN.getValue());
    //
    // if (token == null) {
    // throw new ApplicationException("Session Expired");
    // }
    //
    // it.geosolutions.georepo.internal.response.GeoConstraint geoConstraintToDelete =
    // convertToInternalGeoConstraint(geoConstraint);
    //
    // dgwatchRemoteService.getInternalService().removeGeoConstraint(token, geoConstraintToDelete);
    // }
    //
    // @Override
    // public GeoConstraint saveMemberGeoConstraint(HttpSession session, String connectId,
    // GeoConstraint geoConstraint) {
    // String token = (String)
    // session.getAttribute(DGWATCHKeySessionValues.USER_LOGGED_TOKEN.getValue());
    //
    // if (token == null) {
    // throw new ApplicationException("Session Expired");
    // }
    //
    // it.geosolutions.georepo.internal.response.GeoConstraint geoConstraintToSave =
    // convertToInternalGeoConstraint(geoConstraint);
    //
    // it.geosolutions.georepo.internal.response.GeoConstraint savedGeoConstraint =
    // dgwatchRemoteService.getInternalService().saveMemberGeoConstraint(
    // token, connectId, geoConstraintToSave);
    //
    // if (savedGeoConstraint == null) {
    // throw new ApplicationException("Error saving Member GeoConstraint");
    // }
    //
    // return convertToModelGeoConstraint(savedGeoConstraint);
    // }
    //
    // @Override
    // public void removeMemberGeoConstraint(HttpSession session, String connectId, GeoConstraint
    // geoConstraint) {
    // String token = (String)
    // session.getAttribute(DGWATCHKeySessionValues.USER_LOGGED_TOKEN.getValue());
    //
    // if (token == null) {
    // throw new ApplicationException("Session Expired");
    // }
    //
    // it.geosolutions.georepo.internal.response.GeoConstraint geoConstraintToDelete =
    // convertToInternalGeoConstraint(geoConstraint);
    //
    // dgwatchRemoteService.getInternalService().removeMemberGeoConstraint(token, connectId,
    // geoConstraintToDelete);
    // }

}
