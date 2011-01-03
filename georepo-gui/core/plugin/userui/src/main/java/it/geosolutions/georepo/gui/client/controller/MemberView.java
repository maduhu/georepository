package it.geosolutions.georepo.gui.client.controller;

import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.DistributionNode;
import it.geosolutions.georepo.gui.client.model.Member;
import it.geosolutions.georepo.gui.client.service.MembersRemote;
import it.geosolutions.georepo.gui.client.service.MembersRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.DGWATCHSearchWidget;
import it.geosolutions.georepo.gui.client.widget.DistributionNodeManagementWidget;
import it.geosolutions.georepo.gui.client.widget.MemberManagementWidget;
import it.geosolutions.georepo.gui.client.widget.SearchPagMemberWidget;
import it.geosolutions.georepo.gui.client.widget.SearchStatus;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

/**
 * View that handles GUI manipulation for Member-related functionality.
 */
public class MemberView extends View {

    // TODO: move this out of the View into the Controller if possible
    private MembersRemoteAsync membersRemote = MembersRemote.Util.getInstance();
    
    private MemberManagementWidget memberManagementWidget;
    private DistributionNodeManagementWidget nodeManagementWidget;
    private DGWATCHSearchWidget<Member> searchMemberWidget;

    /**
     * @param controller
     */
    public MemberView (Controller controller) {
        super(controller);
    }

    @Override
    protected void initialize() {
        initWidget();
    }

    /**
     * Init All Controller Widgets
     */
    private void initWidget() {
        this.searchMemberWidget = new SearchPagMemberWidget(this.membersRemote);
    }

    /* (non-Javadoc)
     * @see com.extjs.gxt.ui.client.mvc.View#handleEvent(com.extjs.gxt.ui.client.mvc.AppEvent)
     */
    @Override
    public void handleEvent(AppEvent event) {

        if (event.getType() == DGWATCHEvents.ATTACH_MEMBER_WIDGET) {
            onAttachMemberWidget(event);
        }

        if (event.getType() == DGWATCHEvents.ATTACH_GEOCONSTRAINT_MEMBER_WIDGET) {
            onAttachGeoConstraintMemberWidget(event);
        }

        if (event.getType() == DGWATCHEvents.ATTACH_NODE_SELECTION_WIDGET) {
            onAttachNodeSelectionWidget(event);
        }

        if (event.getType() == DGWATCHEvents.SHOW_SEARCH_MEMBER_WIDGET) {
            onShowSearchMemberWidget();
        }
        if (event.getType() == DGWATCHEvents.BIND_SOURCE_DISTRIBUTION_NODES) {
            onBindSourceNodes(event);
        }
        if (event.getType() == DGWATCHEvents.BIND_MEMBER_DISTRIBUTION_NODES) {
            onBindMemberNodes(event);
        }
    }

    /**
     * @param status
     * @param message
     */
    public void setSearchStatus(SearchStatus.EnumSearchStatus status, SearchStatus.EnumSearchStatus message) {
        this.searchMemberWidget.setSearchStatus(status, message);
    }

    public void cancelSearch() {
        this.searchMemberWidget.cancel();
    }

    /**
     * @param member
     */
    public void bindMember(Member member) {
        this.memberManagementWidget.getMemberInfo().unBindModel();
        this.memberManagementWidget.getMemberInfo().bindModel(member);
        this.memberManagementWidget.enableButtons();
    }
    
    /**
     * 
     */
    public void unBindMember() {
        this.memberManagementWidget.getMemberInfo().unBindModel();
        this.memberManagementWidget.disableButtons();
    }

    /**
     *
     */
    private void onShowSearchMemberWidget() {
        this.searchMemberWidget.show();
    }

    /**
     * @param event
     */
    private void onAttachMemberWidget(AppEvent event) {
        ContentPanel east = (ContentPanel) event.getData();
        this.memberManagementWidget = new MemberManagementWidget();
        east.add(memberManagementWidget);
        east.layout();
    }

    /**
     * @param event
     */
    private void onAttachGeoConstraintMemberWidget(AppEvent event) {
        ContentPanel east = (ContentPanel) event.getData();
        this.memberManagementWidget = new MemberManagementWidget(MemberManagementWidget.SEARCH_BUTTON);
        east.add(memberManagementWidget);
        east.layout();
    }

    /**
	 * @return the memberManagementWidget
	 */
	public MemberManagementWidget getMemberManagementWidget() {
		return memberManagementWidget;
	}

	/**
     * @param event
     */
    private void onAttachNodeSelectionWidget(AppEvent event) {
        ContentPanel east = (ContentPanel) event.getData();
        this.nodeManagementWidget = new DistributionNodeManagementWidget();
        east.add(this.nodeManagementWidget);
        east.layout();

//        // load source distribution nodes
//        this.membersRemote.getSourceNodes(new AsyncCallback<List<DistributionNode>>() {
//
//            public void onFailure(Throwable caught) {
//
//                Dispatcher.forwardEvent(
//                        DGWATCHEvents.SEND_ERROR_MESSAGE,
//                        new String[] {
//                                I18nProvider.getMessages().memberServiceName(),
//                                I18nProvider.getMessages().distributionNodeFetchFailureMessage() });
//            }
//
//            public void onSuccess(List<DistributionNode> result) {
//
//                Dispatcher.forwardEvent(DGWATCHEvents.BIND_SOURCE_DISTRIBUTION_NODES, result);
//                Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
//                        new String[] {
//                                I18nProvider.getMessages().memberServiceName(),
//                                I18nProvider.getMessages().distributionNodeFetchSuccessMessage() });
//            }
//        });
    }

    private void onBindSourceNodes(AppEvent event) {
        this.nodeManagementWidget.setSourceNodes(event.<List<DistributionNode>>getData());
    }

    private void onBindMemberNodes(AppEvent event) {
        // the member panel is used in all administration modes so the user may select a user on all administration
        // modes, but the nodeManagementWidget may not have been created yet (i.e., the user may not have gone
        // to the member management administration mode yet, which causes the creation of this node management panel)
        if (this.nodeManagementWidget != null) {
            this.nodeManagementWidget.setDestinationNodes(event.<List<DistributionNode>>getData());
        }
    }



}
