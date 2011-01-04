/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.UserManagementWidget,v. 0.1 3-gen-2011 17.06.54 created by afabiani <alessio.fabiani at geo-solutions.it> $
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
package it.geosolutions.georepo.gui.client.widget;

import it.geosolutions.georepo.gui.client.i18n.I18nProvider;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.WidgetListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class UserManagementWidget.
 */
public class UserManagementWidget extends ContentPanel {

    /** The user info. */
    private UserInfoBindingWidget userInfo;

    /**
     * Instantiates a new user management widget.
     */
    public UserManagementWidget() {
        setHeading(I18nProvider.getMessages().userManagementLabel());
        setLayout(new FitLayout());

        setLayoutOnChange(true);

        userInfo = new UserInfoBindingWidget();

        add(userInfo.getFormPanel());

        addWidgetListener(new WidgetListener() {
            @Override
            public void widgetResized(ComponentEvent ce) {
                userInfo.getFormPanel().setHeight(getHeight());
            }
        });

        setScrollMode(Scroll.AUTOY);
    }

    /**
     * Gets the user info.
     * 
     * @return the user info
     */
    public UserInfoBindingWidget getUserInfo() {
        return userInfo;
    }

    /**
     * Enable buttons.
     */
    public void enableButtons() {
        this.userInfo.enableButtons();
    }

    /**
     * Disable buttons.
     */
    public void disableButtons() {
        this.userInfo.disableButtons();
    }
}
