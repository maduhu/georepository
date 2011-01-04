/*
 * $ Header: it.geosolutions.georepo.gui.client.action.application.LogoutAction,v. 0.1 3-gen-2011 16.51.33 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 16.51.33 $
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
package it.geosolutions.georepo.gui.client.action.application;

import it.geosolutions.georepo.gui.client.Category;
import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.action.ToolbarApplicationAction;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;

// TODO: Auto-generated Javadoc
/**
 * The Class LogoutAction.
 */
public class LogoutAction extends ToolbarApplicationAction {

    /**
     * Instantiates a new logout action.
     */
    public LogoutAction() {
        super(I18nProvider.getMessages().logoutTooltip(), Category.LOGOUT);
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.extjs.gxt.ui.client.event.Listener#handleEvent(com.extjs.gxt.ui.client.event.BaseEvent)
     */
    public void handleEvent(BaseEvent ce) {
        MessageBox.confirm(I18nProvider.getMessages().logoutDialogTitle(), I18nProvider
                .getMessages().logoutDialogMessage(), new Listener<MessageBoxEvent>() {

            public void handleEvent(MessageBoxEvent be) {
                Button btn = be.getButtonClicked();
                if (btn.getText().equalsIgnoreCase("YES"))
                    Dispatcher.forwardEvent(DGWATCHEvents.LOGOUT);
            }
        });
    }
}
