/*
 * $ Header: it.geosolutions.georepo.gui.client.mvc.AppView,v. 0.1 3-gen-2011 17.04.42 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 17.04.42 $
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
import it.geosolutions.georepo.gui.client.configuration.ConfigurationMainUI;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.ui.RootPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class AppView.
 */
public class AppView extends View {

    /** The viewport. */
    private Viewport viewport;

    /** The east. */
    protected ContentPanel east;

    /** The south. */
    protected ContentPanel south;

    /** The center. */
    protected ContentPanel center;

    /** The north. */
    protected ContentPanel north;

    /**
     * Instantiates a new app view.
     * 
     * @param controller
     *            the controller
     */
    public AppView(Controller controller) {
        super(controller);
    }

    /**
     * Inits the ui.
     */
    private void initUI() {
        this.viewport = new Viewport();
        this.viewport.setLayout(new BorderLayout());

        createNorth();
        createEast();
        createSouth();
        createCenter();

        // registry serves as a global context
        Registry.register(ConfigurationMainUI.VIEWPORT.getValue(), viewport);
        Registry.register(ConfigurationMainUI.EAST.getValue(), east);
        Registry.register(ConfigurationMainUI.SOUTH.getValue(), south);
        Registry.register(ConfigurationMainUI.CENTER.getValue(), center);

        RootPanel.get().add(viewport);
    }

    /**
     * Creates the north.
     */
    private void createNorth() {
        north = new ContentPanel();
        north.setHeaderVisible(false);

        BorderLayoutData data = new BorderLayoutData(LayoutRegion.NORTH, 30);
        data.setMargins(new Margins(0, 5, 0, 5));
        viewport.add(north, data);
    }

    /**
     * Creates the east.
     */
    private void createEast() {
        BorderLayoutData data = new BorderLayoutData(LayoutRegion.EAST, 430, 430, 430);
        data.setMargins(new Margins(5, 0, 5, 5));

        east = new ContentPanel();
        east.setBodyBorder(false);
        east.setLayout(new AccordionLayout());
        east.setHeading(I18nProvider.getMessages().accordionLabel());
        east.setScrollMode(Scroll.AUTO);

        east.addListener(Events.Resize, new Listener<BaseEvent>() {

            public void handleEvent(BaseEvent be) {
                Dispatcher.forwardEvent(DGWATCHEvents.UPDATE_MAP_SIZE);

            }
        });

        configureAccordionPanel();

        viewport.add(east, data);
    }

    /**
     * Creates the south.
     */
    private void createSouth() {
        BorderLayoutData data = new BorderLayoutData(LayoutRegion.SOUTH, 200, 200, 200);
        data.setMargins(new Margins(5, 0, 5, 5));

        south = new ContentPanel();
        south.setBodyBorder(false);
        south.setLayout(new FitLayout());
        south.setLayoutOnChange(true);
        south.setScrollMode(Scroll.NONE);
        south.setHeaderVisible(false);

        Dispatcher.forwardEvent(DGWATCHEvents.ATTACH_AOI_TAB_WIDGET, south);

        viewport.add(south, data);
    }

    /**
     * Creates the center.
     */
    private void createCenter() {
        center = new ContentPanel();
        center.setLayout(new FitLayout());
        center.setHeaderVisible(false);
        BorderLayoutData data = new BorderLayoutData(LayoutRegion.CENTER);
        data.setMargins(new Margins(5, 5, 5, 5));

        viewport.add(center, data);

        Dispatcher.forwardEvent(DGWATCHEvents.ATTACH_MAP_WIDGET, this.center);
        Dispatcher.forwardEvent(DGWATCHEvents.ATTACH_TOOLBAR, this.north);
    }

    /**
     * Configure accordion panel.
     */
    private void configureAccordionPanel() {
        AppController controller = (AppController) this.getController();
        switch (controller.getAdministrationMode()) {
        case NOTIFICATION_DISTRIBUTION:
            Dispatcher.forwardEvent(DGWATCHEvents.ATTACH_MEMBER_WIDGET, east);
            Dispatcher.forwardEvent(DGWATCHEvents.ATTACH_WATCHES_WIDGET, east);
            // Dispatcher.forwardEvent(DGWATCHEvents.ATTACH_USER_WIDGET, east);
            Dispatcher.forwardEvent(DGWATCHEvents.ATTACH_AOI_WIDGET, east);
            Dispatcher.forwardEvent(DGWATCHEvents.ATTACH_AOI_FILTER, east);
            break;
        case GEOCONSTRAINTS:
            Dispatcher.forwardEvent(DGWATCHEvents.ATTACH_GEOCONSTRAINT_MEMBER_WIDGET, east);
            Dispatcher.forwardEvent(DGWATCHEvents.ATTACH_GEOCONSTRAINT_AOI_WIDGET, east);
            break;
        case MEMBER:
            Dispatcher.forwardEvent(DGWATCHEvents.ATTACH_MEMBER_WIDGET, east);
            Dispatcher.forwardEvent(DGWATCHEvents.ATTACH_NODE_SELECTION_WIDGET, east);
            break;
        default:
            assert false : "invalid AdministrationMode: " + controller.getAdministrationMode();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.View#handleEvent(com.extjs.gxt.ui.client.mvc.AppEvent)
     */
    @Override
    protected void handleEvent(AppEvent event) {
        if (event.getType() == DGWATCHEvents.INIT_DGWATCH_MAIN_UI) {
            initUI();
        }
        if (event.getType() == DGWATCHEvents.ADMIN_MODE_CHANGE) {
            onAdminModeChange(event);
        }
    }

    /**
     * On admin mode change.
     * 
     * @param event
     *            the event
     */
    private void onAdminModeChange(AppEvent event) {
        this.east.removeAll();
        Dispatcher.forwardEvent(DGWATCHEvents.ERASE_AOI_FEATURES);
        Dispatcher.forwardEvent(DGWATCHEvents.AOI_MANAGEMENT_UNBIND);
        configureAccordionPanel();
        this.east.layout();
    }

    /**
     * Reload.
     */
    public native void reload()/*-{
                               $wnd.window.location.reload();
                               }-*/;

}