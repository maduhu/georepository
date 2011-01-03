/*
 * $Header: it.geosolutions.georepo.gui.client.widget.tab.TabWidget,v. 0.1 09/lug/2010 10.22.53 created by frank $
 * $Revision: 0.1 $
 * $Date: 09/lug/2010 10.22.53 $
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
package it.geosolutions.georepo.gui.client.widget.tab;

import it.geosolutions.georepo.gui.client.AdministrationMode;
import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.model.GeoConstraint;
import it.geosolutions.georepo.gui.client.service.FeatureServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.MembersRemoteAsync;
import it.geosolutions.georepo.gui.client.service.WatchServiceRemoteAsync;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;

/**
 * @author frank
 * 
 */
public class TabWidget extends TabPanel implements Listener {

	private GeoRSSTabItem geoRSS;
    private GeoConstraintTabItem geoConstraint;
    private GCDNTabItem gcdn;
//	private AOITabItem aoi;
	private WatchTabItem watch;
	private ExecutedWatchesTabItem executedWatches;

    private AdministrationMode currentAdminMode;

	/**
	 * 
	 */
	public TabWidget(FeatureServiceRemoteAsync featureRemote,
            MembersRemoteAsync memberRemote, WatchServiceRemoteAsync watchService) {
		super();
		initTab();
		initTabItem(featureRemote, memberRemote, watchService);

        addListener(DGWATCHEvents.ADMIN_MODE_CHANGE, this);
        addListener(DGWATCHEvents.BIND_SELECTED_MEMBER, this);
        addListener(DGWATCHEvents.GEOCONSTRAINT_DELETED, this);
        addListener(DGWATCHEvents.RELOAD_GEOCONSTRAINTS, this);
	}

	private void initTab() {
		setAutoWidth(true);
		setAutoHeight(true);
	}

	private void initTabItem(FeatureServiceRemoteAsync featureService, 
			MembersRemoteAsync memberService, WatchServiceRemoteAsync watchService) {
		this.geoRSS = new GeoRSSTabItem(featureService);
        this.gcdn = new GCDNTabItem();
        this.geoConstraint = new GeoConstraintTabItem(memberService);
//		this.aoi = new AOITabItem(aoiService);
		this.watch = new WatchTabItem(watchService);
		this.executedWatches = new ExecutedWatchesTabItem(watchService);

		add(geoRSS);
		add(gcdn);
//		add(aoi);
		add(watch);
		add(executedWatches);

		super.doLayout();

	}

	/**
	 * @return the geoRSS
	 */
	public GeoRSSTabItem getGeoRSS() {
		return geoRSS;
	}

	/**
	 * @return the gcdn
	 */
	public GCDNTabItem getGcdn() {
		return gcdn;
	}

//	/**
//	 * @return the aoi
//	 */
//	public AOITabItem getAoi() {
//		return aoi;
//	}
	
	/**
	 * @return the watch
	 */
	public WatchTabItem getWatch() {
		return watch;
	}
	
	/**
	 * @return the watch
	 */
	public ExecutedWatchesTabItem getExecutedWatches() {
		return executedWatches;
	}

//	public void removeAOI(AOI aoi) {
//		this.aoi.removeAOI(aoi);
//	}
//
//	public void updateAOITitle(AOI aoi) {
//		this.aoi.updateAOITitle(aoi);
//	}
//	
//	public void updateAOIStatus(AOI aoi) {
//		this.aoi.updateAOIStatus(aoi);
//	}

     public void handleEvent(BaseEvent e) {
         if (e.getType() == DGWATCHEvents.ADMIN_MODE_CHANGE) {
             onAdminModeChange((AppEvent) e);
         }
         if (e.getType() == DGWATCHEvents.BIND_SELECTED_MEMBER) {
             onBindMember((AppEvent) e);
         }
         if (e.getType() == DGWATCHEvents.GEOCONSTRAINT_DELETED) {
             onGeoConstraintDeleted((AppEvent) e);
         }
         if (e.getType() == DGWATCHEvents.RELOAD_GEOCONSTRAINTS) {
             onReloadGeoConstraints((AppEvent) e);
         }
     }

    /**
     * Handles any reorganization of any tabs if the administration mode changes.
     *
     * @param event the triggering event
     */
    private void onAdminModeChange(AppEvent event) {
        this.currentAdminMode = event.getData();

        this.removeAll();
        switch (this.currentAdminMode) {
            case NOTIFICATION_DISTRIBUTION:
                add(geoRSS);
                add(gcdn);
//                add(aoi);
                add(watch);
                break;
            case GEOCONSTRAINTS:
                add(geoConstraint);
                break;
            case MEMBER:
                add(gcdn);
//                add(aoi);
                break;
            default:
                assert false : "invalid AdministrationMode: " + this.currentAdminMode;
        }
    }

    private void forwardToAllTabs(AppEvent event) {
        for (TabItem tabItem : this.getItems()) {
            tabItem.fireEvent(event.getType(), event);
        }
    }

    private void onBindMember(AppEvent event) {
        forwardToAllTabs(event);
    }

    private void onReloadGeoConstraints(AppEvent event) {
        forwardToAllTabs(event);
    }

    private void onGeoConstraintDeleted(AppEvent event) {
        this.geoConstraint.removeGeoConstraint((Integer) event.getData());
    }

    public boolean contains(GeoConstraint gc) {
        return this.geoConstraint.contains(gc);
    }
}
