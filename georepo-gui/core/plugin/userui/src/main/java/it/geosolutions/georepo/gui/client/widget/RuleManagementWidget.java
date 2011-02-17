/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.RuleManagementWidget,v. 0.1 25-gen-2011 12.18.52 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 12.18.52 $
 *
 * ====================================================================
 *
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 * http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
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
package it.geosolutions.georepo.gui.client.widget;

import it.geosolutions.georepo.gui.client.service.GsUsersManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.InstancesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.WorkspacesManagerServiceRemoteAsync;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class RuleManagementWidget.
 */
public class RuleManagementWidget extends ContentPanel {

    /** The rules info. */
    private RuleGridWidget rulesInfo;

    /**
     * Instantiates a new rule management widget.
     * 
     * @param rulesManagerServiceRemote
     *            the rules manager service remote
     */
    public RuleManagementWidget(RulesManagerServiceRemoteAsync rulesService,
            GsUsersManagerServiceRemoteAsync gsUsersService,
            ProfilesManagerServiceRemoteAsync profilesService,
            InstancesManagerServiceRemoteAsync instancesService,
            WorkspacesManagerServiceRemoteAsync workspacesService) {
        setHeaderVisible(false);
        setFrame(true);
        //setHeight("100%");
        setLayout(new FitLayout());

        setRulesInfo(new RuleGridWidget(rulesService, gsUsersService, profilesService, instancesService, workspacesService));

        add(getRulesInfo().getGrid());

        super.setMonitorWindowResize(true);

        setScrollMode(Scroll.NONE);

        setBottomComponent(this.getRulesInfo().getToolBar());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.widget.Component#onWindowResize(int, int)
     */
    @Override
    protected void onWindowResize(int width, int height) {
        // TODO Auto-generated method stub
        super.setWidth(width - 5);
        super.layout();
    }

    /**
     * Sets the profiles info.
     * 
     * @param rulesInfo
     *            the new profiles info
     */
    public void setRulesInfo(RuleGridWidget rulesInfo) {
        this.rulesInfo = rulesInfo;
    }

    /**
     * Gets the profiles info.
     * 
     * @return the profiles info
     */
    public RuleGridWidget getRulesInfo() {
        return rulesInfo;
    }

}
