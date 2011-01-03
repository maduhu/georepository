/*
 * $Header: it.geosolutions.georepo.gui.client.mvc.AppController,v. 0.1 08/lug/2010 12.00.54 created by frank $
 * $Revision: 0.1 $
 * $Date: 08/lug/2010 12.00.54 $
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
import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.*;
import it.geosolutions.georepo.gui.client.service.AOIServiceRemote;
import it.geosolutions.georepo.gui.client.service.AOIServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.MembersRemote;
import it.geosolutions.georepo.gui.client.service.MembersRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.*;
import it.geosolutions.georepo.gui.client.StringUtil;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @author frank
 * 
 */
public class AppController extends Controller {

    private AdministrationMode currentAdminMode = AdministrationMode.NOTIFICATION_DISTRIBUTION;
	private AppView appView;
	
	private AOIServiceRemoteAsync aoiRemote = AOIServiceRemote.Util.getInstance();
    private MembersRemoteAsync membersRemote = MembersRemote.Util.getInstance();

	public AppController() {
		registerEventTypes(
                DGWATCHEvents.ADMIN_MODE_CHANGE,
                DGWATCHEvents.INIT_DGWATCH_MAIN_UI,
                DGWATCHEvents.SESSION_EXPIRED,
				DGWATCHEvents.SAVE               
        );
	}

	@Override
    public void initialize() {
		appView = new AppView(this);
	}

	protected void onError(AppEvent ae) {
		System.out.println("error: " + ae.<Object> getData());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.extjs.gxt.ui.client.mvc.Controller#handleEvent(com.extjs.gxt.ui.client
	 * .mvc.AppEvent)
	 */
	@Override
	public void handleEvent(AppEvent event) {
        if(event.getType() == DGWATCHEvents.SESSION_EXPIRED) {
            appView.reload();
        }
        
        if(event.getType() == DGWATCHEvents.ADMIN_MODE_CHANGE) {
            this.currentAdminMode = event.getData();
        }
        
		if (event.getType() == DGWATCHEvents.SAVE) {
			onSaveContext();
		}

		forwardToView(appView, event);
	}

    private void saveWatch() {
        WatchesManagementWidget watchWidget = (WatchesManagementWidget)appView.east.getItemByItemId("watchesManagementWidget");
        MemberManagementWidget memberWidget = (MemberManagementWidget)appView.east.getItemByItemId("memberManagementWidget");
        AOISWidget aoiWidget = (AOISWidget)appView.east.getItemByItemId("aoisWidget");
        AOISFilter filterWidget = (AOISFilter)appView.east.getItemByItemId("aoisFilter");

        final WatchesInfoBindingWidget watchesInfo = watchWidget.getWatchesInfo();
        final MemberInfoBindingWidget memberInfo = memberWidget.getMemberInfo();
        final FilterBindingWidget filterBinding = filterWidget.getFilterBinding();
        final AOIBindingWidget aoiBinding = (AOIBindingWidget)aoiWidget.getAoiBinding();

        if(watchesInfo.isSelected() && (watchesInfo.getFormPanel().isDirty() ||
                filterBinding.getFormPanel().isDirty() || memberInfo.getFormPanel().isDirty())){

            // ///////////////////////////////////////////////////////////////////////////////////////
            // WATCH Selected and Edited. Check if the necessary fields are filled in watch panel
            // ///////////////////////////////////////////////////////////////////////////////////////

            if(watchesInfo.checkValidation()){

                if(memberInfo.isSelected() && aoiBinding.isSelected()){

                    if(aoiBinding.getFormPanel().isDirty()){

                        // //////////////////////////////
                        // Update AOI
                        // //////////////////////////////

                        final AOI newAoi = aoiBinding.checkIfUpdate(aoiBinding.getModel());

                        if(newAoi != null){
                            this.aoiRemote.updateAOI(newAoi, new AsyncCallback<AOI>() {

                                public void onFailure(Throwable caught) {
                                    Dispatcher.forwardEvent(
                                            DGWATCHEvents.SEND_ERROR_MESSAGE,
                                            new String[] {
                                                    "AOI Service",
                                                    "There was an error updating the AOI "
                                                            + newAoi.getTitle() });
                                }

                                public void onSuccess(AOI result) {

                                    Dispatcher.forwardEvent(DGWATCHEvents.AOI_MANAGEMENT_BIND, result);
                                    Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
                                            new String[] { "AOI Service",
                                                    "The aoi has been successfully updated." });

                                    // ////////////////////////////
                                    // Update WATCH
                                    // ////////////////////////////

                                    updateWatch(watchesInfo, memberInfo, filterBinding, aoiBinding);
                                }
                            });
                        }

                    }else{

                        // ////////////////////////////
                        // Update WATCH
                        // ////////////////////////////

                        updateWatch(watchesInfo, memberInfo, filterBinding, aoiBinding);
                    }

                }else{
                    MessageBox.alert("Update", "You can't update, Member and/or AOI not selected !", null);
                }
            }else{
                MessageBox.alert("Update", "You can't update, some fields are incorrect!", null);
            }

        }else if(!watchesInfo.isSelected() && watchesInfo.getFormPanel().isDirty()){

            // ///////////////////////////////////////////////////////////////////////////////////////
            // WATCH Edited. Check if the necessary fields are filled in watch panel
            // ///////////////////////////////////////////////////////////////////////////////////////

            if(watchesInfo.checkValidation()){

                if(memberInfo.isSelected() && aoiBinding.isSelected()){

                    if(aoiBinding.getFormPanel().isDirty()){

                        // /////////////////////
                        // Update AOI
                        // /////////////////////

                        final AOI newAoi = aoiBinding.checkIfUpdate(aoiBinding.getModel());

                        if(newAoi != null){
                            this.aoiRemote.updateAOI(newAoi, new AsyncCallback<AOI>() {

                                public void onFailure(Throwable caught) {
                                    Dispatcher.forwardEvent(
                                            DGWATCHEvents.SEND_ERROR_MESSAGE,
                                            new String[] {
                                                    I18nProvider.getMessages().aoiServiceName(),
                                                    "There was an error updating the AOI "
                                                            + newAoi.getTitle() });
                                }

                                public void onSuccess(AOI result) {

                                    Dispatcher.forwardEvent(DGWATCHEvents.AOI_MANAGEMENT_BIND, result);
                                    Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
                                            new String[] {
                                                    I18nProvider.getMessages().aoiServiceName(),
                                                    "The aoi has been successfully updated." });

                                    // ////////////////////////////
                                    // Save WATCH
                                    // ////////////////////////////

                                    saveWatch(watchesInfo, memberInfo, filterBinding, aoiBinding);
                                }
                            });
                        }

                    }else{

                        // ////////////////////////////
                        // Save WATCH
                        // ////////////////////////////

                        saveWatch(watchesInfo, memberInfo, filterBinding, aoiBinding);
                    }

                }else{
                    MessageBox.alert("Save", "You can't save, Member and/or AOI not selected !", null);
                }
            }else{
                MessageBox.alert("Save", "You can't save, some fields are incorrect!", null);
            }
        }
    }

    private boolean validateGeoConstraint(GeoConstraint gc) {
        boolean success = true;
        if ((gc == null) || StringUtil.isEmpty(gc.getName())) {
            MessageBox.alert(
                    I18nProvider.getMessages().geoConstraintSaveErrorDialogTitle(),
                    I18nProvider.getMessages().geoConstraintSaveErrorMissingGeoConstraint(),
                    null);
            success = false;
        }
        else if (StringUtil.isEmpty(gc.getWkt())) {
            MessageBox.alert(
                    I18nProvider.getMessages().geoConstraintSaveErrorDialogTitle(),
                    I18nProvider.getMessages().geoConstraintSaveErrorMissingGeoConstraintWkt(),
                    null);
            success = false;
        }

        return success;
    }

    private boolean validateMember(Member member) {
        boolean success = true;
        if ((member == null) || StringUtil.isEmpty(member.getConnectId())) {
            MessageBox.alert(
                    I18nProvider.getMessages().geoConstraintSaveErrorDialogTitle(),
                    I18nProvider.getMessages().geoConstraintSaveErrorMissingMember(),
                    null);
            success = false;
        }

        return success;
    }

    private boolean validateNodeAssignments(List<DistributionNode> nodes) {
        boolean success = true;

        // this is an assert because the List should never be null - it can be empty, but not null
        assert nodes != null : "Distribution nodes should never be null";
        
        return success;
    }

    private void saveGeoConstraint() {
        MemberManagementWidget memberWidget = (MemberManagementWidget) appView.east.getItemByItemId("memberManagementWidget");
        GeoConstraintWidget geoConstraintWidget = (GeoConstraintWidget) appView.east.getItemByItemId("geoConstraintWidget");

        final GeoConstraintBindingWidget geoConstraintInfo = geoConstraintWidget.getGeoConstraintInfo();
        final MemberInfoBindingWidget memberInfo = memberWidget.getMemberInfo();

        GeoConstraint geoConstraint = geoConstraintInfo.getModel();
        final Member member = memberInfo.getModel();

        if (validateGeoConstraint(geoConstraint) && validateMember(member)) {
//GEOREPO REFACT
//            this.membersRemote.saveMemberGeoConstraint(member.getConnectId(), geoConstraint, new AsyncCallback<GeoConstraint>() {
//
//                public void onFailure(Throwable caught) {
//                    Dispatcher.forwardEvent(
//                            DGWATCHEvents.SEND_ERROR_MESSAGE,
//                            new String[] {
//                                    I18nProvider.getMessages().memberServiceName(),
//                                    I18nProvider.getMessages().geoConstraintBindFailure()
//                            });
//                }
//
//                public void onSuccess(GeoConstraint result) {
//                    //Dispatcher.forwardEvent(DGWATCHEvents.BIND_SELECTED_GEOCONSTRAINT, result);
//                    // clear out the GeoConstraint
//                    Dispatcher.forwardEvent(DGWATCHEvents.BIND_SELECTED_GEOCONSTRAINT, null);
//                    Dispatcher.forwardEvent(DGWATCHEvents.RELOAD_GEOCONSTRAINTS, member);
//                    Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
//                            new String[] {
//                                    I18nProvider.getMessages().memberServiceName(),
//                                    I18nProvider.getMessages().geoConstraintBindSuccess() });
//                }
//            });
        }
    }

    private void saveMemberNodeAssignment() {
        MemberManagementWidget memberWidget = (MemberManagementWidget) appView.east.getItemByItemId("memberManagementWidget");
        DistributionNodeManagementWidget nodeWidget = (DistributionNodeManagementWidget) appView.east.getItemByItemId("distributionNodeManagementWidget");

        final MemberInfoBindingWidget memberInfo = memberWidget.getMemberInfo();

        Member member = memberInfo.getModel();
        List<DistributionNode> nodes = nodeWidget.getSelectedNodes();

        List<Integer> nodeIds = new ArrayList<Integer>();
        for (DistributionNode node : nodes) {
            nodeIds.add(node.getId());
        }

        if (validateNodeAssignments(nodes) && validateMember(member)) {
//GEOREPO REFACT
//            this.membersRemote.saveMemberNodes(member.getConnectId(), nodeIds, new AsyncCallback<Void>() {
//
//                public void onFailure(Throwable caught) {
//                    Dispatcher.forwardEvent(
//                            DGWATCHEvents.SEND_ERROR_MESSAGE,
//                            new String[] {
//                                    I18nProvider.getMessages().memberServiceName(),
//                                    I18nProvider.getMessages().memberDistributionNodeSaveFailureMessage()
//                            });
//                }
//
//                public void onSuccess(Void result) {
//                    Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
//                            new String[] {
//                                    I18nProvider.getMessages().memberServiceName(),
//                                    I18nProvider.getMessages().memberDistributionNodeSaveSuccessMessage() });
//                }
//            });
        }

    }

	/**
	 *
	 */
	private void onSaveContext() {
        // TODO: this logic should probably not occur in the Controller, and should not
        // have to assume names of controls, etc.  Some Controller/View reorganization is probably necessary,
        // possibly a View/Controller for each admin mode
        switch (this.currentAdminMode) {
            case NOTIFICATION_DISTRIBUTION:
                saveWatch();
                break;
            case GEOCONSTRAINTS:
                saveGeoConstraint();
                break;
            case MEMBER:
                saveMemberNodeAssignment();
                break;
        }
	}
	
	/**
	 * @param watchesInfo
	 * @param memberInfo
	 * @param filterBinding
	 * @param aoiBinding
	 */
	private void updateWatch(WatchesInfoBindingWidget watchesInfo, MemberInfoBindingWidget memberInfo,
			FilterBindingWidget filterBinding, AOIBindingWidget aoiBinding) {
		
		Watch watch = watchesInfo.getModelData();	
		watch.setAoiId(aoiBinding.getModel().getId());
		
		Member member = memberInfo.getModel();		
		watch.setMember(member);
		
		if(filterBinding.noFilterAttibute()){
			if(filterBinding.getFormPanel().isDirty()){
				if(filterBinding.checkValidation()){
					Filter filter = filterBinding.injectFilterWidgetValues();
					watch.setFilter(filter);
				}else{
					MessageBox.alert("Update", "You can't update , some fields are incorrect!", null);
					return;
				}
			}else{
				Filter filter = filterBinding.injectFilterWidgetValues();
				watch.setFilter(filter);
			}
		}else{
			watch.setFilter(null);
		}
	
		Dispatcher.forwardEvent(DGWATCHEvents.UPDATE_WATCH, watch);
	}

	/**
	 * @param watchesInfo
	 * @param memberInfo
	 * @param filterBinding
	 * @param aoiBinding
	 */
	private void saveWatch(WatchesInfoBindingWidget watchesInfo, MemberInfoBindingWidget memberInfo,
			FilterBindingWidget filterBinding, AOIBindingWidget aoiBinding){		
		
			Watch watch = watchesInfo.getModelData();	
			
			Member member = memberInfo.getModel();
			watch.setMember(member);
			
			AOI aoi = aoiBinding.getModel();
			watch.setAoiId(aoi.getId());	
			
			if(filterBinding.noFilterAttibute()){
				if(filterBinding.getFormPanel().isDirty()){
					if(filterBinding.checkValidation()){
						Filter filter = filterBinding.injectFilterWidgetValues();
						watch.setFilter(filter);
					}else{
						MessageBox.alert("Save", "You can't save , some fields are incorrect!", null);
						return;
					}
				}else{
					Filter filter = filterBinding.injectFilterWidgetValues();
					watch.setFilter(filter);
				}
			}else{
				watch.setFilter(null);
			}

			Dispatcher.forwardEvent(DGWATCHEvents.SAVE_WATCH, watch);
	}

    public AdministrationMode getAdministrationMode() {
        return this.currentAdminMode;
    }

    public void setAdministrationMode(AdministrationMode currentAdminMode) {
        this.currentAdminMode = currentAdminMode;
    }
}
