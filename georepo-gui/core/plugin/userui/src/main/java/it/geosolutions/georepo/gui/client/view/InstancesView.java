/*
 * $ Header: it.geosolutions.georepo.gui.client.view.UsersView,v. 0.1 10-feb-2011 11.50.15 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 10-feb-2011 11.50.15 $
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
package it.geosolutions.georepo.gui.client.view;

import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.widget.AddInstanceWidget;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersView.
 */
public class InstancesView extends View {

    private AddInstanceWidget addInstance;

    /**
     * Instantiates a new users view.
     * 
     * @param controller
     *            the controller
     */
    public InstancesView(Controller controller) {
        super(controller);

        this.addInstance = new AddInstanceWidget(GeoRepoEvents.SAVE_INSTANCE, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.View#handleEvent(com.extjs.gxt.ui.client.mvc.AppEvent)
     */
    @Override
    protected void handleEvent(AppEvent event) {
        if (event.getType() == GeoRepoEvents.CREATE_NEW_INSTANCE)
            onCreateNewInstance(event);

    }

    /**
     * On create new instance.
     * 
     * @param event
     *            the event
     */
    private void onCreateNewInstance(AppEvent event) {
        this.addInstance.show();
    }

}
