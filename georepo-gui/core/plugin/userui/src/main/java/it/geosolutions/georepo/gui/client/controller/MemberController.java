/*
 * $ Header: it.geosolutions.georepo.gui.client.controller.MemberController,v. 0.1 3-gen-2011 17.06.54 created by afabiani <alessio.fabiani at geo-solutions.it> $
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
package it.geosolutions.georepo.gui.client.controller;

import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.Member;
import it.geosolutions.georepo.gui.client.service.MembersRemote;
import it.geosolutions.georepo.gui.client.service.MembersRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.SearchStatus.EnumSearchStatus;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;

// TODO: Auto-generated Javadoc
/**
 * The Class MemberController.
 */
public class MemberController extends Controller {

    /** The member view. */
    private MemberView memberView;

    /** The members remote. */
    private MembersRemoteAsync membersRemote = MembersRemote.Util.getInstance();

    /**
     * Instantiates a new member controller.
     */
    public MemberController() {
        registerEventTypes(DGWATCHEvents.ATTACH_MEMBER_WIDGET,
                DGWATCHEvents.ATTACH_GEOCONSTRAINT_MEMBER_WIDGET,
                DGWATCHEvents.ATTACH_NODE_SELECTION_WIDGET,
                DGWATCHEvents.SHOW_SEARCH_MEMBER_WIDGET, DGWATCHEvents.BIND_SELECTED_MEMBER,
                DGWATCHEvents.UNBIND_SELECTED_MEMBER, DGWATCHEvents.BIND_SOURCE_DISTRIBUTION_NODES,
                DGWATCHEvents.BIND_MEMBER_DISTRIBUTION_NODES);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.Controller#initialize()
     */
    @Override
    protected void initialize() {
        this.memberView = new MemberView(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.Controller#handleEvent(com.extjs.gxt.ui.client.mvc.AppEvent)
     */
    @Override
    public void handleEvent(AppEvent event) {

        if (event.getType() == DGWATCHEvents.BIND_SELECTED_MEMBER) {
            onBindSelectedMember(event);
        }

        if (event.getType() == DGWATCHEvents.UNBIND_SELECTED_MEMBER) {
            onUnBindSelectedMember();
        }

        forwardToView(this.memberView, event);
    }

    /**
     * On un bind selected member.
     */
    private void onUnBindSelectedMember() {
        unBindMember();
    }

    /**
     * On bind selected member.
     * 
     * @param event
     *            the event
     */
    private void onBindSelectedMember(AppEvent event) {
        final Member member = (Member) event.getData();

        this.memberView.setSearchStatus(EnumSearchStatus.STATUS_SEARCH,
                EnumSearchStatus.STATUS_MESSAGE_MEMBER_DETAIL);
        Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE, new String[] {
                I18nProvider.getMessages().memberDetails(),
                I18nProvider.getMessages().bindingMemberDetail() });
        this.memberView.cancelSearch();
        bindMember(member);

        // load member/node assignments
        // this.membersRemote.getMemberNodes(member.getConnectId(), new
        // AsyncCallback<List<DistributionNode>>() {
        //
        // public void onFailure(Throwable caught) {
        //
        // Dispatcher.forwardEvent(
        // DGWATCHEvents.SEND_ERROR_MESSAGE,
        // new String[] {
        // I18nProvider.getMessages().memberServiceName(),
        // I18nProvider.getMessages().memberDistributionNodeFetchFailureMessage(member.getMemberName())
        // });
        // }
        //
        // public void onSuccess(List<DistributionNode> result) {
        //
        // Dispatcher.forwardEvent(DGWATCHEvents.BIND_MEMBER_DISTRIBUTION_NODES, result);
        // Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
        // new String[] {
        // I18nProvider.getMessages().memberServiceName(),
        // I18nProvider.getMessages().memberDistributionNodeFetchSuccessMessage(member.getMemberName())
        // });
        // }
        // });
    }

    /**
     * Bind member.
     * 
     * @param member
     *            the member
     */
    private void bindMember(Member member) {

        // ////////////////////////
        // bind data to view
        // ////////////////////////

        this.memberView.bindMember(member);

        // ///////////////////////////
        // Reset the grids contents
        // ///////////////////////////

        Dispatcher.forwardEvent(DGWATCHEvents.RESET_AOI_GRID);
        Dispatcher.forwardEvent(DGWATCHEvents.RESET_RSS_GRID);

        memberView.getMemberManagementWidget().setHeading(
                I18nProvider.getMessages().memberManagementLabel() + " (" + member.getMemberName()
                        + ")");

        memberView.getMemberManagementWidget().getMemberInfo().setSelected(true);

    }

    /**
     * Un bind member.
     */
    private void unBindMember() {

        // ///////////////////////////
        // unbind data to view
        // ///////////////////////////

        this.memberView.unBindMember();

        memberView.getMemberManagementWidget().setHeading(
                I18nProvider.getMessages().memberManagementLabel());

        memberView.getMemberManagementWidget().getMemberInfo().setSelected(false);

    }

}