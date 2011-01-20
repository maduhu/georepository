/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.UserManagementWidget,v. 0.1 14-gen-2011 19.29.51 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 14-gen-2011 19.29.51 $
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

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class UserManagementWidget.
 */
public class UserManagementWidget extends ContentPanel {

    /** The user info. */
    private UserGridWidget usersInfo;

    /**
     * Instantiates a new user management widget.
     */
    public UserManagementWidget(GsUsersManagerServiceRemoteAsync gsManagerServiceRemote) {
        setHeaderVisible(false);
        setFrame(true);
        setHeight(170);
        setLayout(new FitLayout());

        setUsersInfo(new UserGridWidget(gsManagerServiceRemote));

        add(getUsersInfo().getGrid());

        super.setMonitorWindowResize(true);

        setScrollMode(Scroll.NONE);

        setBottomComponent(this.getUsersInfo().getToolBar());
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
     * @param usersInfo the usersInfo to set
     */
    public void setUsersInfo(UserGridWidget usersInfo) {
        this.usersInfo = usersInfo;
    }

    /**
     * @return the usersInfo
     */
    public UserGridWidget getUsersInfo() {
        return usersInfo;
    }
    
}
