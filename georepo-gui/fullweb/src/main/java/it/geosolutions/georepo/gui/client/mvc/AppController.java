/*
 * $ Header: it.geosolutions.georepo.gui.client.mvc.AppController,v. 0.1 3-gen-2011 17.04.42 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 17.04.42 $
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
package it.geosolutions.georepo.gui.client.mvc;

import it.geosolutions.georepo.gui.client.AdministrationMode;
import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.StringUtil;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.AOI;
import it.geosolutions.georepo.gui.client.model.DistributionNode;
import it.geosolutions.georepo.gui.client.model.Filter;
import it.geosolutions.georepo.gui.client.model.GeoConstraint;
import it.geosolutions.georepo.gui.client.model.Member;
import it.geosolutions.georepo.gui.client.model.Watch;
//import it.geosolutions.georepo.gui.client.service.AOIServiceRemote;
//import it.geosolutions.georepo.gui.client.service.AOIServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.MembersRemote;
import it.geosolutions.georepo.gui.client.service.MembersRemoteAsync;
//import it.geosolutions.georepo.gui.client.widget.AOIBindingWidget;
//import it.geosolutions.georepo.gui.client.widget.AOISFilter;
//import it.geosolutions.georepo.gui.client.widget.AOISWidget;
import it.geosolutions.georepo.gui.client.widget.DistributionNodeManagementWidget;
//import it.geosolutions.georepo.gui.client.widget.FilterBindingWidget;
//import it.geosolutions.georepo.gui.client.widget.GeoConstraintBindingWidget;
//import it.geosolutions.georepo.gui.client.widget.GeoConstraintWidget;
import it.geosolutions.georepo.gui.client.widget.MemberInfoBindingWidget;
import it.geosolutions.georepo.gui.client.widget.MemberManagementWidget;
//import it.geosolutions.georepo.gui.client.widget.WatchesInfoBindingWidget;
//import it.geosolutions.georepo.gui.client.widget.WatchesManagementWidget;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class AppController.
 */
public class AppController extends Controller {

    /** The current admin mode. */
    private AdministrationMode currentAdminMode = AdministrationMode.NOTIFICATION_DISTRIBUTION;

    /** The app view. */
    private AppView appView;

//    /** The aoi remote. */
//    private AOIServiceRemoteAsync aoiRemote = AOIServiceRemote.Util.getInstance();

    /** The members remote. */
    private MembersRemoteAsync membersRemote = MembersRemote.Util.getInstance();

    /**
     * Instantiates a new app controller.
     */
    public AppController() {
        registerEventTypes(GeoRepoEvents.ADMIN_MODE_CHANGE, GeoRepoEvents.INIT_GEOREPO_MAIN_UI,
                GeoRepoEvents.SESSION_EXPIRED, GeoRepoEvents.SAVE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.Controller#initialize()
     */
    @Override
    public void initialize() {
        appView = new AppView(this);
    }

    /**
     * On error.
     * 
     * @param ae
     *            the ae
     */
    protected void onError(AppEvent ae) {
        System.out.println("error: " + ae.<Object> getData());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.Controller#handleEvent(com.extjs.gxt.ui.client
     * .mvc.AppEvent)
     */
    @Override
    public void handleEvent(AppEvent event) {
        if (event.getType() == GeoRepoEvents.SESSION_EXPIRED) {
            appView.reload();
        }

        if (event.getType() == GeoRepoEvents.ADMIN_MODE_CHANGE) {
            this.currentAdminMode = event.getData();
        }

        if (event.getType() == GeoRepoEvents.SAVE) {
            onSaveContext();
        }

        forwardToView(appView, event);
    }

//    /**
//     * Save watch.
//     */
//    private void saveWatch() {
//        WatchesManagementWidget watchWidget = (WatchesManagementWidget) appView.east
//                .getItemByItemId("watchesManagementWidget");
//        MemberManagementWidget memberWidget = (MemberManagementWidget) appView.east
//                .getItemByItemId("memberManagementWidget");
//        AOISWidget aoiWidget = (AOISWidget) appView.east.getItemByItemId("aoisWidget");
//        AOISFilter filterWidget = (AOISFilter) appView.east.getItemByItemId("aoisFilter");
//
//        final WatchesInfoBindingWidget watchesInfo = watchWidget.getWatchesInfo();
//        final MemberInfoBindingWidget memberInfo = memberWidget.getMemberInfo();
//        final FilterBindingWidget filterBinding = filterWidget.getFilterBinding();
//        final AOIBindingWidget aoiBinding = (AOIBindingWidget) aoiWidget.getAoiBinding();
//
//        if (watchesInfo.isSelected()
//                && (watchesInfo.getFormPanel().isDirty() || filterBinding.getFormPanel().isDirty() || memberInfo
//                        .getFormPanel().isDirty())) {
//
//            // ///////////////////////////////////////////////////////////////////////////////////////
//            // WATCH Selected and Edited. Check if the necessary fields are filled in watch panel
//            // ///////////////////////////////////////////////////////////////////////////////////////
//
//            if (watchesInfo.checkValidation()) {
//
//                if (memberInfo.isSelected() && aoiBinding.isSelected()) {
//
//                    if (aoiBinding.getFormPanel().isDirty()) {
//
//                        // //////////////////////////////
//                        // Update AOI
//                        // //////////////////////////////
//
//                        final AOI newAoi = aoiBinding.checkIfUpdate(aoiBinding.getModel());
//
//                        if (newAoi != null) {
//                            this.aoiRemote.updateAOI(newAoi, new AsyncCallback<AOI>() {
//
//                                public void onFailure(Throwable caught) {
//                                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE,
//                                            new String[] {
//                                                    "AOI Service",
//                                                    "There was an error updating the AOI "
//                                                            + newAoi.getTitle() });
//                                }
//
//                                public void onSuccess(AOI result) {
//
//                                    Dispatcher.forwardEvent(GeoRepoEvents.AOI_MANAGEMENT_BIND,
//                                            result);
//                                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
//                                            new String[] { "AOI Service",
//                                                    "The aoi has been successfully updated." });
//
//                                    // ////////////////////////////
//                                    // Update WATCH
//                                    // ////////////////////////////
//
//                                    updateWatch(watchesInfo, memberInfo, filterBinding, aoiBinding);
//                                }
//                            });
//                        }
//
//                    } else {
//
//                        // ////////////////////////////
//                        // Update WATCH
//                        // ////////////////////////////
//
//                        updateWatch(watchesInfo, memberInfo, filterBinding, aoiBinding);
//                    }
//
//                } else {
//                    MessageBox.alert("Update",
//                            "You can't update, Member and/or AOI not selected !", null);
//                }
//            } else {
//                MessageBox.alert("Update", "You can't update, some fields are incorrect!", null);
//            }
//
//        } else if (!watchesInfo.isSelected() && watchesInfo.getFormPanel().isDirty()) {
//
//            // ///////////////////////////////////////////////////////////////////////////////////////
//            // WATCH Edited. Check if the necessary fields are filled in watch panel
//            // ///////////////////////////////////////////////////////////////////////////////////////
//
//            if (watchesInfo.checkValidation()) {
//
//                if (memberInfo.isSelected() && aoiBinding.isSelected()) {
//
//                    if (aoiBinding.getFormPanel().isDirty()) {
//
//                        // /////////////////////
//                        // Update AOI
//                        // /////////////////////
//
//                        final AOI newAoi = aoiBinding.checkIfUpdate(aoiBinding.getModel());
//
//                        if (newAoi != null) {
//                            this.aoiRemote.updateAOI(newAoi, new AsyncCallback<AOI>() {
//
//                                public void onFailure(Throwable caught) {
//                                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE,
//                                            new String[] {
//                                                    I18nProvider.getMessages().aoiServiceName(),
//                                                    "There was an error updating the AOI "
//                                                            + newAoi.getTitle() });
//                                }
//
//                                public void onSuccess(AOI result) {
//
//                                    Dispatcher.forwardEvent(GeoRepoEvents.AOI_MANAGEMENT_BIND,
//                                            result);
//                                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
//                                            new String[] {
//                                                    I18nProvider.getMessages().aoiServiceName(),
//                                                    "The aoi has been successfully updated." });
//
//                                    // ////////////////////////////
//                                    // Save WATCH
//                                    // ////////////////////////////
//
//                                    saveWatch(watchesInfo, memberInfo, filterBinding, aoiBinding);
//                                }
//                            });
//                        }
//
//                    } else {
//
//                        // ////////////////////////////
//                        // Save WATCH
//                        // ////////////////////////////
//
//                        saveWatch(watchesInfo, memberInfo, filterBinding, aoiBinding);
//                    }
//
//                } else {
//                    MessageBox.alert("Save", "You can't save, Member and/or AOI not selected !",
//                            null);
//                }
//            } else {
//                MessageBox.alert("Save", "You can't save, some fields are incorrect!", null);
//            }
//        }
//    }

    /**
     * Validate geo constraint.
     * 
     * @param gc
     *            the gc
     * @return true, if successful
     */
    private boolean validateGeoConstraint(GeoConstraint gc) {
        boolean success = true;
        if ((gc == null) || StringUtil.isEmpty(gc.getName())) {
            MessageBox.alert(I18nProvider.getMessages().geoConstraintSaveErrorDialogTitle(),
                    I18nProvider.getMessages().geoConstraintSaveErrorMissingGeoConstraint(), null);
            success = false;
        } else if (StringUtil.isEmpty(gc.getWkt())) {
            MessageBox.alert(I18nProvider.getMessages().geoConstraintSaveErrorDialogTitle(),
                    I18nProvider.getMessages().geoConstraintSaveErrorMissingGeoConstraintWkt(),
                    null);
            success = false;
        }

        return success;
    }

    /**
     * Validate member.
     * 
     * @param member
     *            the member
     * @return true, if successful
     */
    private boolean validateMember(Member member) {
        boolean success = true;
        if ((member == null) || StringUtil.isEmpty(member.getConnectId())) {
            MessageBox.alert(I18nProvider.getMessages().geoConstraintSaveErrorDialogTitle(),
                    I18nProvider.getMessages().geoConstraintSaveErrorMissingMember(), null);
            success = false;
        }

        return success;
    }

    /**
     * Validate node assignments.
     * 
     * @param nodes
     *            the nodes
     * @return true, if successful
     */
    private boolean validateNodeAssignments(List<DistributionNode> nodes) {
        boolean success = true;

        // this is an assert because the List should never be null - it can be empty, but not null
        assert nodes != null : "Distribution nodes should never be null";

        return success;
    }

//    /**
//     * Save geo constraint.
//     */
//    private void saveGeoConstraint() {
//        MemberManagementWidget memberWidget = (MemberManagementWidget) appView.east
//                .getItemByItemId("memberManagementWidget");
//        GeoConstraintWidget geoConstraintWidget = (GeoConstraintWidget) appView.east
//                .getItemByItemId("geoConstraintWidget");
//
//        final GeoConstraintBindingWidget geoConstraintInfo = geoConstraintWidget
//                .getGeoConstraintInfo();
//        final MemberInfoBindingWidget memberInfo = memberWidget.getMemberInfo();
//
//        GeoConstraint geoConstraint = geoConstraintInfo.getModel();
//        final Member member = memberInfo.getModel();
//
//        if (validateGeoConstraint(geoConstraint) && validateMember(member)) {
//            // GEOREPO REFACT
//            // this.membersRemote.saveMemberGeoConstraint(member.getConnectId(), geoConstraint, new
//            // AsyncCallback<GeoConstraint>() {
//            //
//            // public void onFailure(Throwable caught) {
//            // Dispatcher.forwardEvent(
//            // DGWATCHEvents.SEND_ERROR_MESSAGE,
//            // new String[] {
//            // I18nProvider.getMessages().memberServiceName(),
//            // I18nProvider.getMessages().geoConstraintBindFailure()
//            // });
//            // }
//            //
//            // public void onSuccess(GeoConstraint result) {
//            // //Dispatcher.forwardEvent(DGWATCHEvents.BIND_SELECTED_GEOCONSTRAINT, result);
//            // // clear out the GeoConstraint
//            // Dispatcher.forwardEvent(DGWATCHEvents.BIND_SELECTED_GEOCONSTRAINT, null);
//            // Dispatcher.forwardEvent(DGWATCHEvents.RELOAD_GEOCONSTRAINTS, member);
//            // Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
//            // new String[] {
//            // I18nProvider.getMessages().memberServiceName(),
//            // I18nProvider.getMessages().geoConstraintBindSuccess() });
//            // }
//            // });
//        }
//    }

    /**
     * Save member node assignment.
     */
    private void saveMemberNodeAssignment() {
        MemberManagementWidget memberWidget = (MemberManagementWidget) appView.east
                .getItemByItemId("memberManagementWidget");
        DistributionNodeManagementWidget nodeWidget = (DistributionNodeManagementWidget) appView.east
                .getItemByItemId("distributionNodeManagementWidget");

        final MemberInfoBindingWidget memberInfo = memberWidget.getMemberInfo();

        Member member = memberInfo.getModel();
        List<DistributionNode> nodes = nodeWidget.getSelectedNodes();

        List<Integer> nodeIds = new ArrayList<Integer>();
        for (DistributionNode node : nodes) {
            nodeIds.add(node.getId());
        }

        if (validateNodeAssignments(nodes) && validateMember(member)) {
            // GEOREPO REFACT
            // this.membersRemote.saveMemberNodes(member.getConnectId(), nodeIds, new
            // AsyncCallback<Void>() {
            //
            // public void onFailure(Throwable caught) {
            // Dispatcher.forwardEvent(
            // DGWATCHEvents.SEND_ERROR_MESSAGE,
            // new String[] {
            // I18nProvider.getMessages().memberServiceName(),
            // I18nProvider.getMessages().memberDistributionNodeSaveFailureMessage()
            // });
            // }
            //
            // public void onSuccess(Void result) {
            // Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
            // new String[] {
            // I18nProvider.getMessages().memberServiceName(),
            // I18nProvider.getMessages().memberDistributionNodeSaveSuccessMessage() });
            // }
            // });
        }

    }

    /**
     * On save context.
     */
    private void onSaveContext() {
        // TODO: this logic should probably not occur in the Controller, and should not
        // have to assume names of controls, etc. Some Controller/View reorganization is probably
        // necessary,
        // possibly a View/Controller for each admin mode
//        switch (this.currentAdminMode) {
//        case NOTIFICATION_DISTRIBUTION:
//            saveWatch();
//            break;
//        case GEOCONSTRAINTS:
//            saveGeoConstraint();
//            break;
//        case MEMBER:
//            saveMemberNodeAssignment();
//            break;
//        }
    }

//    /**
//     * Update watch.
//     * 
//     * @param watchesInfo
//     *            the watches info
//     * @param memberInfo
//     *            the member info
//     * @param filterBinding
//     *            the filter binding
//     * @param aoiBinding
//     *            the aoi binding
//     */
//    private void updateWatch(WatchesInfoBindingWidget watchesInfo,
//            MemberInfoBindingWidget memberInfo, FilterBindingWidget filterBinding,
//            AOIBindingWidget aoiBinding) {
//
//        Watch watch = watchesInfo.getModelData();
//        watch.setAoiId(aoiBinding.getModel().getId());
//
//        Member member = memberInfo.getModel();
//        watch.setMember(member);
//
//        if (filterBinding.noFilterAttibute()) {
//            if (filterBinding.getFormPanel().isDirty()) {
//                if (filterBinding.checkValidation()) {
//                    Filter filter = filterBinding.injectFilterWidgetValues();
//                    watch.setFilter(filter);
//                } else {
//                    MessageBox.alert("Update", "You can't update , some fields are incorrect!",
//                            null);
//                    return;
//                }
//            } else {
//                Filter filter = filterBinding.injectFilterWidgetValues();
//                watch.setFilter(filter);
//            }
//        } else {
//            watch.setFilter(null);
//        }
//
//        Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_WATCH, watch);
//    }

//    /**
//     * Save watch.
//     * 
//     * @param watchesInfo
//     *            the watches info
//     * @param memberInfo
//     *            the member info
//     * @param filterBinding
//     *            the filter binding
//     * @param aoiBinding
//     *            the aoi binding
//     */
//    private void saveWatch(WatchesInfoBindingWidget watchesInfo,
//            MemberInfoBindingWidget memberInfo, FilterBindingWidget filterBinding,
//            AOIBindingWidget aoiBinding) {
//
//        Watch watch = watchesInfo.getModelData();
//
//        Member member = memberInfo.getModel();
//        watch.setMember(member);
//
//        AOI aoi = aoiBinding.getModel();
//        watch.setAoiId(aoi.getId());
//
//        if (filterBinding.noFilterAttibute()) {
//            if (filterBinding.getFormPanel().isDirty()) {
//                if (filterBinding.checkValidation()) {
//                    Filter filter = filterBinding.injectFilterWidgetValues();
//                    watch.setFilter(filter);
//                } else {
//                    MessageBox.alert("Save", "You can't save , some fields are incorrect!", null);
//                    return;
//                }
//            } else {
//                Filter filter = filterBinding.injectFilterWidgetValues();
//                watch.setFilter(filter);
//            }
//        } else {
//            watch.setFilter(null);
//        }
//
//        Dispatcher.forwardEvent(GeoRepoEvents.SAVE_WATCH, watch);
//    }

    /**
     * Gets the administration mode.
     * 
     * @return the administration mode
     */
    public AdministrationMode getAdministrationMode() {
        return this.currentAdminMode;
    }

    /**
     * Sets the administration mode.
     * 
     * @param currentAdminMode
     *            the new administration mode
     */
    public void setAdministrationMode(AdministrationMode currentAdminMode) {
        this.currentAdminMode = currentAdminMode;
    }
}
