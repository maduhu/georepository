/*
 * $ Header: it.geosolutions.georepo.gui.client.controller.MemberView,v. 0.1 3-gen-2011 17.06.54 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.model.DistributionNode;
import it.geosolutions.georepo.gui.client.model.Member;
import it.geosolutions.georepo.gui.client.service.MembersRemote;
import it.geosolutions.georepo.gui.client.service.MembersRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.GeoRepoSearchWidget;
import it.geosolutions.georepo.gui.client.widget.DistributionNodeManagementWidget;
import it.geosolutions.georepo.gui.client.widget.MemberManagementWidget;
import it.geosolutions.georepo.gui.client.widget.SearchPagMemberWidget;
import it.geosolutions.georepo.gui.client.widget.SearchStatus;

import java.util.List;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.ContentPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class MemberView.
 */
public class MemberView extends View {

    // TODO: move this out of the View into the Controller if possible
    /** The members remote. */
    private MembersRemoteAsync membersRemote = MembersRemote.Util.getInstance();

    /** The member management widget. */
    private MemberManagementWidget memberManagementWidget;

    /** The node management widget. */
    private DistributionNodeManagementWidget nodeManagementWidget;

    /** The search member widget. */
    private GeoRepoSearchWidget<Member> searchMemberWidget;

    /**
     * Instantiates a new member view.
     * 
     * @param controller
     *            the controller
     */
    public MemberView(Controller controller) {
        super(controller);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.View#initialize()
     */
    @Override
    protected void initialize() {
        initWidget();
    }

    /**
     * Inits the widget.
     */
    private void initWidget() {
        this.searchMemberWidget = new SearchPagMemberWidget(this.membersRemote);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.View#handleEvent(com.extjs.gxt.ui.client.mvc.AppEvent)
     */
    @Override
    public void handleEvent(AppEvent event) {

        if (event.getType() == GeoRepoEvents.ATTACH_MEMBER_WIDGET) {
            onAttachMemberWidget(event);
        }

        if (event.getType() == GeoRepoEvents.ATTACH_GEOCONSTRAINT_MEMBER_WIDGET) {
            onAttachGeoConstraintMemberWidget(event);
        }

        if (event.getType() == GeoRepoEvents.ATTACH_NODE_SELECTION_WIDGET) {
            onAttachNodeSelectionWidget(event);
        }

        if (event.getType() == GeoRepoEvents.SHOW_SEARCH_MEMBER_WIDGET) {
            onShowSearchMemberWidget();
        }
        if (event.getType() == GeoRepoEvents.BIND_SOURCE_DISTRIBUTION_NODES) {
            onBindSourceNodes(event);
        }
        if (event.getType() == GeoRepoEvents.BIND_MEMBER_DISTRIBUTION_NODES) {
            onBindMemberNodes(event);
        }
    }

    /**
     * Sets the search status.
     * 
     * @param status
     *            the status
     * @param message
     *            the message
     */
    public void setSearchStatus(SearchStatus.EnumSearchStatus status,
            SearchStatus.EnumSearchStatus message) {
        this.searchMemberWidget.setSearchStatus(status, message);
    }

    /**
     * Cancel search.
     */
    public void cancelSearch() {
        this.searchMemberWidget.cancel();
    }

    /**
     * Bind member.
     * 
     * @param member
     *            the member
     */
    public void bindMember(Member member) {
        this.memberManagementWidget.getMemberInfo().unBindModel();
        this.memberManagementWidget.getMemberInfo().bindModel(member);
        this.memberManagementWidget.enableButtons();
    }

    /**
     * Un bind member.
     */
    public void unBindMember() {
        this.memberManagementWidget.getMemberInfo().unBindModel();
        this.memberManagementWidget.disableButtons();
    }

    /**
     * On show search member widget.
     */
    private void onShowSearchMemberWidget() {
        this.searchMemberWidget.show();
    }

    /**
     * On attach member widget.
     * 
     * @param event
     *            the event
     */
    private void onAttachMemberWidget(AppEvent event) {
        ContentPanel east = (ContentPanel) event.getData();
        this.memberManagementWidget = new MemberManagementWidget();
        east.add(memberManagementWidget);
        east.layout();
    }

    /**
     * On attach geo constraint member widget.
     * 
     * @param event
     *            the event
     */
    private void onAttachGeoConstraintMemberWidget(AppEvent event) {
        ContentPanel east = (ContentPanel) event.getData();
        this.memberManagementWidget = new MemberManagementWidget(
                MemberManagementWidget.SEARCH_BUTTON);
        east.add(memberManagementWidget);
        east.layout();
    }

    /**
     * Gets the member management widget.
     * 
     * @return the member management widget
     */
    public MemberManagementWidget getMemberManagementWidget() {
        return memberManagementWidget;
    }

    /**
     * On attach node selection widget.
     * 
     * @param event
     *            the event
     */
    private void onAttachNodeSelectionWidget(AppEvent event) {
        ContentPanel east = (ContentPanel) event.getData();
        this.nodeManagementWidget = new DistributionNodeManagementWidget();
        east.add(this.nodeManagementWidget);
        east.layout();

        // // load source distribution nodes
        // this.membersRemote.getSourceNodes(new AsyncCallback<List<DistributionNode>>() {
        //
        // public void onFailure(Throwable caught) {
        //
        // Dispatcher.forwardEvent(
        // DGWATCHEvents.SEND_ERROR_MESSAGE,
        // new String[] {
        // I18nProvider.getMessages().memberServiceName(),
        // I18nProvider.getMessages().distributionNodeFetchFailureMessage() });
        // }
        //
        // public void onSuccess(List<DistributionNode> result) {
        //
        // Dispatcher.forwardEvent(DGWATCHEvents.BIND_SOURCE_DISTRIBUTION_NODES, result);
        // Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
        // new String[] {
        // I18nProvider.getMessages().memberServiceName(),
        // I18nProvider.getMessages().distributionNodeFetchSuccessMessage() });
        // }
        // });
    }

    /**
     * On bind source nodes.
     * 
     * @param event
     *            the event
     */
    private void onBindSourceNodes(AppEvent event) {
        this.nodeManagementWidget.setSourceNodes(event.<List<DistributionNode>> getData());
    }

    /**
     * On bind member nodes.
     * 
     * @param event
     *            the event
     */
    private void onBindMemberNodes(AppEvent event) {
        // the member panel is used in all administration modes so the user may select a user on all
        // administration
        // modes, but the nodeManagementWidget may not have been created yet (i.e., the user may not
        // have gone
        // to the member management administration mode yet, which causes the creation of this node
        // management panel)
        if (this.nodeManagementWidget != null) {
            this.nodeManagementWidget.setDestinationNodes(event.<List<DistributionNode>> getData());
        }
    }

}
