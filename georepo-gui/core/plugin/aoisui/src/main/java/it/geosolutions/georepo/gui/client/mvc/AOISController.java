/*
 * $Header: it.geosolutions.georepo.gui.client.mvc.AOISController,v. 0.1 19/lug/2010 09.34.09 created by frank $
 * $Revision: 0.1 $
 * $Date: 19/lug/2010 09.34.09 $
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

import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.AOI;
import it.geosolutions.georepo.gui.client.model.GeoConstraint;
import it.geosolutions.georepo.gui.client.model.Member;
import it.geosolutions.georepo.gui.client.model.User;
import it.geosolutions.georepo.gui.client.model.Watch;
import it.geosolutions.georepo.gui.client.service.AOIServiceRemote;
import it.geosolutions.georepo.gui.client.service.AOIServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.FeatureServiceRemote;
import it.geosolutions.georepo.gui.client.service.FeatureServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.MembersRemote;
import it.geosolutions.georepo.gui.client.service.MembersRemoteAsync;
import it.geosolutions.georepo.gui.client.service.WatchServiceRemote;
import it.geosolutions.georepo.gui.client.service.WatchServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.AOIBindingWidget;
import it.geosolutions.georepo.gui.client.widget.DGWATCHSearchWidget;
import it.geosolutions.georepo.gui.client.widget.GeoConstraintBindingWidget;
import it.geosolutions.georepo.gui.client.widget.SaveStaus.EnumSaveStatus;
import it.geosolutions.georepo.gui.client.widget.SearchPagAOIWidget;
import it.geosolutions.georepo.gui.client.widget.SearchPagingGeoConstraintWidget;
import it.geosolutions.georepo.gui.client.widget.SearchStatus.EnumSearchStatus;
import it.geosolutions.georepo.gui.client.widget.tab.TabWidget;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author frank
 * 
 */
public class AOISController extends Controller {

    private MembersRemoteAsync membersRemote = MembersRemote.Util.getInstance();
	private AOIServiceRemoteAsync aoiRemote = AOIServiceRemote.Util.getInstance();
	private FeatureServiceRemoteAsync featureRemote = FeatureServiceRemote.Util.getInstance();
	private WatchServiceRemoteAsync watchRemote = WatchServiceRemote.Util.getInstance();

	private AOIView aoiView;
	private TabWidget tabWidget;

    private DGWATCHSearchWidget<AOI> aoiSearchWidget;
    private DGWATCHSearchWidget<GeoConstraint> geoConstraintSearchWidget;

	/**
	 * 
	 */
	public AOISController() {
		registerEventTypes(
				DGWATCHEvents.INIT_AOIS_UI_MODULE,// DGWATCHEvents.INIT_DGWATCH_WIDGET,
				DGWATCHEvents.SHOW_UPLOAD_WIDGET,
				DGWATCHEvents.ATTACH_AOI_WIDGET,
				DGWATCHEvents.ATTACH_AOI_TAB_WIDGET,
				DGWATCHEvents.AOI_MANAGEMENT_BIND,
				DGWATCHEvents.AOI_MANAGEMENT_UNBIND,
				DGWATCHEvents.SHOW_ADD_AOI, DGWATCHEvents.SHOW_ADD_GEOCONSTRAINT,
				DGWATCHEvents.INJECT_WKT, DGWATCHEvents.SAVE_AOI,
				DGWATCHEvents.SEARCH_AOI, DGWATCHEvents.BIND_SELECTED_AOI,
				DGWATCHEvents.DELETE_AOI, DGWATCHEvents.UPDATE_AOI,
				DGWATCHEvents.INJECT_WKT_FROM_SHP,
				DGWATCHEvents.SAVE_AOI_FROM_SHP, DGWATCHEvents.CHECK_AOI_OWNER,
				DGWATCHEvents.SEARCH_USER_GEORSS, DGWATCHEvents.RESET_AOI_GRID,
				DGWATCHEvents.RESET_RSS_GRID, DGWATCHEvents.CHECK_AOI_STATUS,
        		DGWATCHEvents.ADMIN_MODE_CHANGE,
        		DGWATCHEvents.ATTACH_GEOCONSTRAINT_AOI_WIDGET,
        		DGWATCHEvents.BIND_SELECTED_GEOCONSTRAINT,
        		DGWATCHEvents.DELETE_CONTENT, DGWATCHEvents.BIND_SELECTED_MEMBER,
        		DGWATCHEvents.SAVE_GEOCONSTRAINT, DGWATCHEvents.ENABLE_DRAW_BUTTON,
        		DGWATCHEvents.SEARCH_GEOCONSTRAINT, DGWATCHEvents.DELETE_GEOCONSTRAINT,
        		DGWATCHEvents.SEARCH_MEMBER_WATCHES,
				DGWATCHEvents.LOAD_WATCH_AOI, DGWATCHEvents.RELOAD_GEOCONSTRAINTS,
                DGWATCHEvents.GEOCONSTRAINT_DELETED,
				DGWATCHEvents.RESET_WATCH_GRID,
				DGWATCHEvents.REFRESH_WATCH_GRID, DGWATCHEvents.MEMBER_GEOCONSTRAINT_UNBOUND);
	}

	@Override
    protected void initialize() {
		this.aoiView = new AOIView(this);
		initWidget();
	}

	/**
	 * Init All Controller Widgets
	 */
	private void initWidget() {
        this.aoiSearchWidget = new SearchPagAOIWidget(this.aoiRemote);
        this.geoConstraintSearchWidget = new SearchPagingGeoConstraintWidget(this.membersRemote);
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
		if (event.getType() == DGWATCHEvents.ATTACH_AOI_TAB_WIDGET)
			onAttachAOITabWidget(event);

		if (event.getType() == DGWATCHEvents.SAVE_AOI)
			onSaveAOI(event);

        if (event.getType() == DGWATCHEvents.SAVE_AOI_FROM_SHP)
            onSaveAOIFromShp(event);

        if (event.getType() == DGWATCHEvents.SAVE_GEOCONSTRAINT)
            onSaveGeoConstraint(event);

        if (event.getType() == DGWATCHEvents.SEARCH_AOI)
            onShowSearchPagAOIWidget();

        if (event.getType() == DGWATCHEvents.SEARCH_GEOCONSTRAINT)
            onShowPagingSearchGeoConstraintWidget();

        if (event.getType() == DGWATCHEvents.BIND_SELECTED_AOI)
            onBindSelectedAOI(event);

        if (event.getType() == DGWATCHEvents.BIND_SELECTED_GEOCONSTRAINT)
            onBindSelectedGeoConstraint(event);

        if (event.getType() == DGWATCHEvents.DELETE_AOI)
            onDeleteAOI(event);

        if (event.getType() == DGWATCHEvents.DELETE_GEOCONSTRAINT)
            onDeleteGeoConstraint(event);

		if (event.getType() == DGWATCHEvents.UPDATE_AOI)
			onUpdateAOI(event);

		if (event.getType() == DGWATCHEvents.SEARCH_USER_GEORSS)
			onSearchUserFeature(event);

		if (event.getType() == DGWATCHEvents.RESET_RSS_GRID)
			onResetRSSGrid();

        if (event.getType() == DGWATCHEvents.ADMIN_MODE_CHANGE)
            onAdminModeChange(event);

        if (event.getType() == DGWATCHEvents.BIND_SELECTED_MEMBER)
            onBindSelectedMember(event);
        
        if (event.getType() == DGWATCHEvents.SEARCH_MEMBER_WATCHES) {
        	onSearchMemberWatches(event);
        }
        
        if (event.getType() == DGWATCHEvents.LOAD_WATCH_AOI) {
            onLoadWatchAoi(event);
        }
        
        if (event.getType() == DGWATCHEvents.RESET_WATCH_GRID) {
            onResetWatchTab();
        }

        if (event.getType() == DGWATCHEvents.RELOAD_GEOCONSTRAINTS) {
            onReloadGeoConstraints(event);
        }

        if (event.getType() == DGWATCHEvents.GEOCONSTRAINT_DELETED) {
            onGeoConstraintDeleted(event);
        }
        
        if (event.getType() == DGWATCHEvents.REFRESH_WATCH_GRID) {
            onRefreshWatchGrid();
        }

        if (event.getType() == DGWATCHEvents.MEMBER_GEOCONSTRAINT_UNBOUND) {
            onMemberGeoConstraintUnbound(event);
        }

		forwardToView(aoiView, event);
	}

	/**
	 * 
	 */
	private void onRefreshWatchGrid() {
		tabWidget.getWatch().getWatchGridManagementWidget().getWatchPagGridWidget().getToolBar().refresh();
	}

	/**
	 * 
	 */
	private void onResetWatchTab() {
		tabWidget.getWatch().getWatchGridManagementWidget().getWatchPagGridWidget().clearGridElements();
	}

	private void onLoadWatchAoi(AppEvent event) {
		final Watch watch = (Watch) event.getData();
		this.aoiRemote.getWatchAOIDetails(watch.getAoiId(), new AsyncCallback<AOI>() {

			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(
						DGWATCHEvents.SEND_ERROR_MESSAGE,
						new String[] {
								"AOI Service",
								"There was an error binding the AOI "
										+ watch.getAoiId()});
			}

			public void onSuccess(AOI result) {
				Dispatcher.forwardEvent(
						DGWATCHEvents.SEND_INFO_MESSAGE,
						new String[] {
								"AOI retrieved",
								"The AOI has been retrieved"});
				bindAOI(result);
				Dispatcher.forwardEvent(DGWATCHEvents.DRAW_AOI_ON_MAP,
						result.getWkt());
			}
		});		
	}

	/**
	 * @param event
	 */
	private void onSearchMemberWatches(AppEvent event) {
		Member member = (Member) event.getData();
		tabWidget.getWatch().getWatchGridManagementWidget().getWatchPagGridWidget().setMemberName(member.getMemberName());
		tabWidget.getWatch().getWatchGridManagementWidget().getWatchPagGridWidget().getLoader().load(0, 25);
		tabWidget.setSelection(tabWidget.getWatch());
	}

	private void onResetRSSGrid() {
		tabWidget.getGeoRSS().getFeatureManagementWidget()
				.getFeaturePagGridWidget().clearGridElements();
	}

	private void onSearchUserFeature(AppEvent event) {
		User user = (User) event.getData();
		tabWidget.getGeoRSS().getFeatureManagementWidget()
				.getFeaturePagGridWidget().setUserId(user.getId());
		tabWidget.getGeoRSS().getFeatureManagementWidget()
				.getFeaturePagGridWidget().getLoader().load(0, 25);
		tabWidget.setSelection(tabWidget.getGeoRSS());
	}

	@SuppressWarnings("deprecation")
	private void onSaveAOIFromShp(AppEvent event) {
		this.aoiRemote.saveAOI((AOI) event.getData(), new AsyncCallback<AOI>() {

			public void onFailure(Throwable caught) {
				aoiView.getAddAOIFromShp().setSaveStatus(
						EnumSaveStatus.STATUS_SAVE_ERROR,
						EnumSaveStatus.STATUS_MESSAGE_SAVE_ERROR);
				Dispatcher.forwardEvent(DGWATCHEvents.SEND_ERROR_MESSAGE,
						new String[] { "AOI Service",
								"There was a problem with saving aoi." });
			}

			public void onSuccess(AOI result) {
				aoiView.getAddAOIFromShp().setSaveStatus(
						EnumSaveStatus.STATUS_SAVE,
						EnumSaveStatus.STATUS_MESSAGE_SAVE);
				Dispatcher.forwardEvent(
						DGWATCHEvents.SEND_INFO_MESSAGE,
						new String[] {
								"AOI Saved",
								"The AOI has been saved with ID: "
										+ result.getId() });

				aoiView.getAddAOIFromShp().close();
				bindAOI(result);
				Dispatcher.forwardEvent(DGWATCHEvents.DRAW_AOI_ON_MAP,
						result.getWkt());
			}
		});

	}

	/**
	 * 
	 */
	private void onShowSearchPagAOIWidget() {
		this.aoiSearchWidget.show();
	}

    private void onShowPagingSearchGeoConstraintWidget() {
        this.geoConstraintSearchWidget.show();
    }

	/**
	 * 
	 * @param event
	 */
	private void onUpdateAOI(AppEvent event) {
		final AOI aoi = (AOI) event.getData();

		this.aoiRemote.updateAOI(aoi, new AsyncCallback<AOI>() {

			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(
						DGWATCHEvents.SEND_ERROR_MESSAGE,
						new String[] {
								"AOI Service",
								"There was an error updating the AOI "
										+ aoi.getTitle() });
			}

			public void onSuccess(AOI result) {
				bindAOI(result);
				Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
						new String[] { "AOI Service",
								"The aoi has been successfully updated." });
			}
		});
	}

	/**
	 * 
	 * @param event
	 */
    private void onDeleteAOI(AppEvent event) {
        final AOI aoi = (AOI) event.getData();
        this.aoiRemote.deleteAOI(aoi.getId(), new AsyncCallback<Object>() {

            public void onFailure(Throwable caught) {            	
                Dispatcher.forwardEvent(
                        DGWATCHEvents.SEND_ERROR_MESSAGE,
                        new String[] {
                                "AOI Service",
                                caught.getMessage() });
            }

            public void onSuccess(Object result) {
                Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
                        new String[] {
                                "AOI Service",
                                "The AOI with TITLE: " + aoi.getTitle()
                                        + " was successfully deleted." });
                ((AOIBindingWidget) aoiView.getAois().getAoiBinding())
                        .unBindModel();
                ((AOIBindingWidget) aoiView.getAois().getAoiBinding()).setSelected(false);
                
                Dispatcher.forwardEvent(DGWATCHEvents.ERASE_AOI_FEATURES);
            }
        });
    }

    private void onDeleteGeoConstraint(AppEvent event) {
        final GeoConstraint gc = (GeoConstraint) event.getData();
//GEOREPO REFACT
//        this.membersRemote.removeGeoConstraint(gc, new AsyncCallback<Void>() {
//
//            public void onFailure(Throwable caught) {
//                Dispatcher.forwardEvent(
//                        DGWATCHEvents.SEND_ERROR_MESSAGE,
//                        new String[] {
//                                I18nProvider.getMessages().memberServiceName(),
//                                "There was an error deleting the GeoConstraint "
//                                        + gc.getName() });
//            }
//
//            public void onSuccess(Void v) {
//                Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
//                        new String[] {
//                                I18nProvider.getMessages().memberServiceName(),
//                                "The GeoConstraint with name: " + gc.getName()
//                                        + " was successfully deleted." });
//                ((GeoConstraintBindingWidget) aoiView.getGeoConstraint().getGeoConstraintBinding())
//                        .unBindModel();
//                Dispatcher.forwardEvent(DGWATCHEvents.ERASE_AOI_FEATURES);
//                Dispatcher.forwardEvent(DGWATCHEvents.GEOCONSTRAINT_DELETED, gc.getId());
//                Dispatcher.forwardEvent(DGWATCHEvents.GEOCONSTRAINT_SELECTED, null);
//            }
//        });
    }

	/**
	 * 
	 * @param event
	 */
    private void onBindSelectedAOI(AppEvent event) {
        final AOI aoi = (AOI) event.getData();
        this.aoiRemote.getAOIDetail(aoi, new AsyncCallback<AOI>() {

            public void onFailure(Throwable caught) {
                aoiSearchWidget.setSearchStatus(
                        EnumSearchStatus.STATUS_SEARCH_ERROR,
                        EnumSearchStatus.STATUS_MESSAGE_AOI_DETAIL_ERROR);
            }

            public void onSuccess(AOI result) {
                aoiSearchWidget.setSearchStatus(EnumSearchStatus.STATUS_SEARCH,
                        EnumSearchStatus.STATUS_MESSAGE_AOI_DETAIL);
                aoiSearchWidget.cancel();
                bindAOI(result);
                Dispatcher.forwardEvent(DGWATCHEvents.DRAW_AOI_ON_MAP,
                        result.getWkt());
            }
        });
    }

    private void onBindSelectedGeoConstraint(AppEvent event) {
        GeoConstraint gc = event.getData();
        this.geoConstraintSearchWidget.cancel();
        unbindGeoConstraint();

        if (gc != null) {
            bindGeoConstraint(gc);
            Dispatcher.forwardEvent(DGWATCHEvents.DRAW_AOI_ON_MAP, gc.getWkt());
        }
    }

    private void bindAOI(AOI aoi) {
		((AOIBindingWidget) aoiView.getAois().getAoiBinding()).unBindModel();
		((AOIBindingWidget) aoiView.getAois().getAoiBinding()).bindModel(aoi);
        ((AOIBindingWidget) aoiView.getAois().getAoiBinding()).enableButtons();

        aoiView.getAois().setHeading(I18nProvider.getMessages().aoiManagementLabel() +
                " (" + aoi.getTitle() + ")");

        ((AOIBindingWidget) aoiView.getAois().getAoiBinding()).setSelected(true);
    }

    private void unbindGeoConstraint() {
        this.aoiView.getGeoConstraint().getGeoConstraintBinding().unBindModel();
        this.aoiView.getGeoConstraint().setHeading(I18nProvider.getMessages().geoConstraintManagementLabel());
    }

    private void bindGeoConstraint(GeoConstraint gc) {

        assert gc != null : "geoConstraint was null";

        this.aoiView.getGeoConstraint().getGeoConstraintBinding().bindModel(gc);
        ((GeoConstraintBindingWidget) this.aoiView.getGeoConstraint().getGeoConstraintBinding()).enableButtons();

        StringBuilder sb = new StringBuilder(I18nProvider.getMessages().geoConstraintManagementLabel() + " (" + gc.getName());

        if ((gc.getId() == null) || (!this.tabWidget.contains(gc))) {
            sb.append(" - " + I18nProvider.getMessages().savePending());
        }

        sb.append(")");

        this.aoiView.getGeoConstraint().setHeading(sb.toString());
    }

    private void onBindSelectedMember(AppEvent event) {
        forwardToTabWidget(event);
    }

    private void onReloadGeoConstraints(AppEvent event) {
        forwardToTabWidget(event);
    }

    private void onGeoConstraintDeleted(AppEvent event) {
        // clear the current context
        unbindGeoConstraint();

        forwardToTabWidget(event);
    }

    private void onMemberGeoConstraintUnbound(AppEvent event) {
        GeoConstraint gc = event.getData();
        if (this.aoiView.getGeoConstraint().getGeoConstraintBinding().getModel().getId().equals(gc.getId())) {
            unbindGeoConstraint();
        }
    }


    @SuppressWarnings("deprecation")
    private void onSaveAOI(AppEvent event) {
        this.aoiRemote.saveAOI((AOI) event.getData(), new AsyncCallback<AOI>() {

            public void onFailure(Throwable caught) {
                aoiView.getAddAOI().setSaveStatus(
                        EnumSaveStatus.STATUS_SAVE_ERROR,
                        EnumSaveStatus.STATUS_MESSAGE_SAVE_ERROR);

                Dispatcher.forwardEvent(DGWATCHEvents.SEND_ERROR_MESSAGE,
                        new String[] { "AOI Service",
                                "There was a problem with saving aoi." });
            }

            public void onSuccess(AOI result) {
                aoiView.getAddAOI().setSaveStatus(EnumSaveStatus.STATUS_SAVE,
                        EnumSaveStatus.STATUS_MESSAGE_SAVE);
                Dispatcher.forwardEvent(
                        DGWATCHEvents.SEND_INFO_MESSAGE,
                        new String[] {
                                "AOI Saved",
                                "The AOI has been saved with ID: "
                                        + result.getId() });

                aoiView.getAddAOI().close();
                bindAOI(result);
                Dispatcher.forwardEvent(DGWATCHEvents.DRAW_AOI_ON_MAP,
                        result.getWkt());
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void onSaveGeoConstraint(AppEvent event) {
        GeoConstraint geoConstraint = event.getData();

//GEOREPO REFACT
//        this.membersRemote.saveGeoConstraint(geoConstraint, new AsyncCallback<GeoConstraint>() {
//
//            public void onFailure(Throwable caught) {
//                aoiView.getAddAOI().setSaveStatus(
//                        EnumSaveStatus.STATUS_SAVE_ERROR,
//                        EnumSaveStatus.STATUS_MESSAGE_SAVE_ERROR);
//
//                Dispatcher.forwardEvent(DGWATCHEvents.SEND_ERROR_MESSAGE,
//                        new String[] { I18nProvider.getMessages().memberServiceName(),
//                                I18nProvider.getMessages().geoConstraintSaveFailureMessage() });
//            }
//
//            public void onSuccess(GeoConstraint result) {
//                aoiView.getAddAOI().setSaveStatus(EnumSaveStatus.STATUS_SAVE,
//                        EnumSaveStatus.STATUS_MESSAGE_SAVE);
//                Dispatcher.forwardEvent(
//                        DGWATCHEvents.SEND_INFO_MESSAGE,
//                        new String[] {
//                                I18nProvider.getMessages().geoConstraintShortSaveSuccessMessage(),
//                                I18nProvider.getMessages().geoConstraintSaveSuccessMessage()
//                                        + result.getId() });
//
//                aoiView.getAddGeoConstraint().close();
//                bindGeoConstraint(result);
//                result.notifyState();
//                Dispatcher.forwardEvent(DGWATCHEvents.DRAW_AOI_ON_MAP,
//                        result.getWkt());
//            }
//        });
    }

	private void onAttachAOITabWidget(AppEvent event) {
		ContentPanel south = (ContentPanel) event.getData();
		this.tabWidget = new TabWidget(featureRemote, membersRemote, watchRemote);

		south.add(this.tabWidget);
		south.layout();
	}

    /**
     * Handles any reorganization of any AOI GUI elements if the administration mode changes.
     *
     * @param event the triggering event
     */
    private void onAdminModeChange(AppEvent event) {
        forwardToTabWidget(event);
    }

    private void forwardToTabWidget(AppEvent event) {
        this.tabWidget.fireEvent(event.getType(), event);
    }

}
