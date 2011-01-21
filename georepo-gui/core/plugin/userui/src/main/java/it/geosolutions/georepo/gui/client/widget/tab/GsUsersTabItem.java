/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.tab.GeoRSSTabItem,v. 0.1 14-gen-2011 19.28.38 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 14-gen-2011 19.28.38 $
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
package it.geosolutions.georepo.gui.client.widget.tab;

import it.geosolutions.georepo.gui.client.Resources;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.service.GsUsersManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.UserManagementWidget;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.TabItem;

// TODO: Auto-generated Javadoc
/**
 * The Class GeoRSSTabItem.
 */
public class GsUsersTabItem extends TabItem {

    /** The feature management widget. */
    private UserManagementWidget userManagementWidget;

    /**
     * Instantiates a new geo rss tab item.
     */
    public GsUsersTabItem() {
        super(I18nProvider.getMessages().userManagementLabel());
        setIcon(Resources.ICONS.user());
    }

    public GsUsersTabItem(GsUsersManagerServiceRemoteAsync gsManagerServiceRemote) {
        this();
        setUserManagementWidget(new UserManagementWidget(gsManagerServiceRemote));
        add(getUserManagementWidget());

        setScrollMode(Scroll.NONE);

        getUserManagementWidget().getUsersInfo().getLoader().load(0, 25);

    }

    /**
     * @param userManagementWidget the userManagementWidget to set
     */
    public void setUserManagementWidget(UserManagementWidget userManagementWidget) {
        this.userManagementWidget = userManagementWidget;
    }

    /**
     * @return the userManagementWidget
     */
    public UserManagementWidget getUserManagementWidget() {
        return userManagementWidget;
    }

}
