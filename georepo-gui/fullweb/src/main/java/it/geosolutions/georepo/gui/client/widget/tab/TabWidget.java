/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.tab.TabWidget,v. 0.1 3-gen-2011 16.58.12 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 16.58.12 $
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

//import it.geosolutions.georepo.gui.client.AdministrationMode;
import it.geosolutions.georepo.gui.client.GeoRepoEvents;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class TabWidget.
 */
public class TabWidget extends TabPanel implements Listener {

    /**
     * Instantiates a new tab widget.
     * 
     * @param featureRemote
     *            the feature remote
     * @param memberRemote
     *            the member remote
     * @param watchService
     *            the watch service
     */
    public TabWidget(/*FeatureServiceRemoteAsync featureRemote, MembersRemoteAsync memberRemote,
            WatchServiceRemoteAsync watchService*/) {
        super();
        initTab();
        initTabItem(/*featureRemote, memberRemote, watchService*/);

        addListener(GeoRepoEvents.BIND_SELECTED_MEMBER, this);
    }

    /**
     * Inits the tab.
     */
    private void initTab() {
        setAutoWidth(true);
        setAutoHeight(true);
    }

    /**
     * Inits the tab item.
     * 
     * @param featureService
     *            the feature service
     * @param memberService
     *            the member service
     * @param watchService
     *            the watch service
     */
    private void initTabItem(/*FeatureServiceRemoteAsync featureService,
            MembersRemoteAsync memberService, WatchServiceRemoteAsync watchService*/) {
//        this.geoRSS = new GeoRSSTabItem(featureService);
//        this.gcdn = new GCDNTabItem();
//        this.geoConstraint = new GeoConstraintTabItem(memberService);
//        // this.aoi = new AOITabItem(aoiService);
//        this.watch = new WatchTabItem(watchService);
//        this.executedWatches = new ExecutedWatchesTabItem(watchService);
//
//        add(geoRSS);
//        add(gcdn);
//        // add(aoi);
//        add(watch);
//        add(executedWatches);

        super.doLayout();

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.extjs.gxt.ui.client.event.Listener#handleEvent(com.extjs.gxt.ui.client.event.BaseEvent)
     */
    public void handleEvent(BaseEvent e) {
//        if (e.getType() == GeoRepoEvents.ADMIN_MODE_CHANGE) {
//            onAdminModeChange((AppEvent) e);
//        }
//        if (e.getType() == GeoRepoEvents.BIND_SELECTED_MEMBER) {
//            onBindMember((AppEvent) e);
//        }
//        if (e.getType() == GeoRepoEvents.GEOCONSTRAINT_DELETED) {
//            onGeoConstraintDeleted((AppEvent) e);
//        }
//        if (e.getType() == GeoRepoEvents.RELOAD_GEOCONSTRAINTS) {
//            onReloadGeoConstraints((AppEvent) e);
//        }
    }

    /**
     * Forward to all tabs.
     * 
     * @param event
     *            the event
     */
    private void forwardToAllTabs(AppEvent event) {
        for (TabItem tabItem : this.getItems()) {
            tabItem.fireEvent(event.getType(), event);
        }
    }

    /**
     * On bind member.
     * 
     * @param event
     *            the event
     */
    private void onBindMember(AppEvent event) {
        forwardToAllTabs(event);
    }

    /**
     * On reload geo constraints.
     * 
     * @param event
     *            the event
     */
    private void onReloadGeoConstraints(AppEvent event) {
        forwardToAllTabs(event);
    }

}
