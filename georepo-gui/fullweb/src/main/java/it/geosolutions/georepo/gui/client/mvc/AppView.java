/*
 * $ Header: it.geosolutions.georepo.gui.client.mvc.AppView,v. 0.1 14-gen-2011 19.27.23 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 14-gen-2011 19.27.23 $
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
package it.geosolutions.georepo.gui.client.mvc;

import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.configuration.ConfigurationMainUI;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.widget.tab.TabWidget;
import com.extjs.gxt.ui.client.event.ResizeEvent;
import com.extjs.gxt.ui.client.event.ResizeListener;
import com.extjs.gxt.ui.client.event.BorderLayoutEvent;
//import com.gwtext.client.widgets.event.PanelListenerAdapter;
//import com.gwtext.client.widgets.event.ResizableListenerAdapter;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.fx.Resizable;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.BoxComponent;
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

    /** The tab widget. */
    private TabWidget tabWidget;

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
        // createEast();<<-- ric mod 20100217
        createSouth();
        /*
         * if(GXT.isIE || GXT.isIE7 || GXT.isIE8){ //createCenter();//<<-- ric mod 20100217
         * System.out.println("SONO NEL IE"); }
         */
        // registry serves as a global context
        Registry.register(ConfigurationMainUI.VIEWPORT.getValue(), viewport);
        // Registry.register(ConfigurationMainUI.EAST.getValue(), east);<<-- ric mod 20100217
        Registry.register(ConfigurationMainUI.SOUTH.getValue(), south);
        // Registry.register(ConfigurationMainUI.CENTER.getValue(), center);<<-- ric mod 20100217

        RootPanel.get().add(viewport);
    }

    /**
     * Creates the north.
     */
    private void createNorth() {
        north = new ContentPanel();
        north.setHeaderVisible(false);
        north.addListener(Events.Resize, new Listener<BaseEvent>() {

            public void handleEvent(BaseEvent be) {
                // Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_MAP_SIZE);
                Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_SOUTH_SIZE);
            }
        });

        BorderLayoutData data = new BorderLayoutData(LayoutRegion.NORTH, 30);
        data.setMargins(new Margins(0, 5, 0, 5));
        // data.setSplit(true);

        viewport.add(north, data);
    }

    /**
     * Creates the east.
     */
    private void createEast() {
        BorderLayoutData data = new BorderLayoutData(LayoutRegion.EAST, 430);// , 430, 430
        data.setMargins(new Margins(5, 0, 5, 5));
        data.setMinSize(430);// ric add 20100216
        data.setMaxSize(1830);// ric add 20100216
        data.setCollapsible(true);// ric add 20100216
        // data.setFloatable(true);//ric add 20100216
        data.setSplit(true);
        east = new ContentPanel();
        east.setBodyBorder(false);
        east.setLayout(new AccordionLayout());
        east.setHeading(I18nProvider.getMessages().accordionLabel());
        east.setScrollMode(Scroll.AUTO);

        east.addListener(Events.Resize, new Listener<BaseEvent>() {

            public void handleEvent(BaseEvent be) {
                Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_MAP_SIZE);

            }
        });
        east.addListener(Events.Move, new Listener<BaseEvent>() {

            public void handleEvent(BaseEvent be) {
                // Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_MAP_SIZE);
                Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_SOUTH_SIZE);
            }
        });
        // east.setStyleAttribute("height", "auto");
        // east.setStyleAttribute("width", "auto");
        // configureAccordionPanel();
        east.setMonitorWindowResize(true);// <<-- ric add 20100216
        east.setLayoutOnChange(true);// <<-- ric add 20100216
        viewport.add(east, data);
    }

    /**
     * Creates the south.
     */
    private void createSouth() {
        BorderLayoutData data = new BorderLayoutData(LayoutRegion.CENTER, 300, 300, 300);// //ric
                                                                                         // add
                                                                                         // 20100216
        data.setMargins(new Margins(5, 0, 5, 5));
        // data.setMinSize(300);
        // data.setMaxSize(1900);<<-- ric mod 20100217

        // data.setSplit(true);
        data.setHideCollapseTool(false);

        // data.setHideCollapseTool(false);//ric add 20100216
        south = new ContentPanel();
        south.setBodyBorder(false);
        south.setAnimCollapse(true);
        south.setCollapsible(true);
        south.setLayout(new FitLayout());
        south.setLayoutOnChange(true);
        south.setScrollMode(Scroll.NONE);
        south.setHeaderVisible(false);
        // south.setHeading(I18nProvider.getMessages().accordionLabel());
        // south.setHeight("100%");
        south.addListener(Events.Resize, new Listener<BaseEvent>() {

            public void handleEvent(BaseEvent be) {
                // Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_MAP_SIZE);
                Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_SOUTH_SIZE);
            }
        });
        south.addListener(Events.Move, new Listener<BaseEvent>() {

            public void handleEvent(BaseEvent be) {
                // Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_MAP_SIZE);
                Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_SOUTH_SIZE);
            }
        });
        // south.setStyleAttribute("height", "100%");
        south.setMonitorWindowResize(true);// <<-- ric add 20100216
        south.setLayoutOnChange(true);// <<-- ric add 20100216
        this.tabWidget = new TabWidget();

        south.add(this.tabWidget);
        if(south.getParent()!=null){
            System.out.println("south.getParent().getOffsetHeight()=="+south.getParent().getOffsetHeight());
            System.out.println("south.getParent().getElement().getOffsetHeight()=="+south.getParent().getElement().getOffsetHeight());
        }
        south.setStyleAttribute("height", "96%");
        south.setHeight("96%");
     // if(south.getParent()!=null)south.getParent().setHeight("96%");
     // if(south.getParent()!=null && south.getParent().getParent()!=null)south.getParent().getParent().setHeight("96%");
        // south.getBottomComponent().setStyleAttribute("top", "96%");
        south.layout();
        // south.setHeight(viewport.getHeight());???
        Dispatcher.forwardEvent(GeoRepoEvents.ATTACH_BOTTOM_TAB_WIDGETS, this.tabWidget);
        // Dispatcher.forwardEvent(GeoRepoEvents.ATTACH.ATTACH_USER_WIDGET, this.center);
        Dispatcher.forwardEvent(GeoRepoEvents.ATTACH_TOOLBAR, this.north);
        south.setMonitorWindowResize(true);// <<-- ric add 20110223
        south.setLayoutOnChange(true);// <<-- ric add 20110223
        viewport.add(south, data);
    }

    /**
     * Creates the center.
     */
    private void createCenter() {
        center = new ContentPanel();
        center.setLayout(new FitLayout());
        center.setHeaderVisible(false);
        // center.setLayout(new AccordionLayout());
        center.addListener(Events.Resize, new Listener<BaseEvent>() {

            public void handleEvent(BaseEvent be) {
                Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_MAP_SIZE);
                // Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_SOUTH_SIZE);
            }
        });
        center.addListener(Events.Move, new Listener<BaseEvent>() {

            public void handleEvent(BaseEvent be) {
                Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_MAP_SIZE);
                // Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_SOUTH_SIZE);
            }
        });
        // center.setStyleAttribute("height", "auto");
        // center.setStyleAttribute("width", "auto");
        // center.setMonitorWindowResize(true);//<<-- ric add 20100216
        // center.setLayoutOnChange(true);//<<-- ric add 20100216

        BorderLayoutData data = new BorderLayoutData(LayoutRegion.CENTER, 0);
        // data.setMinSize(500);
        // data.setMaxSize(2800);
        // data.setCollapsible(true);//ric add 20100216
        // data.setFloatable(true);
        // data.setSplit(true);
        data.setMargins(new Margins(5, 5, 5, 5));

        viewport.add(center, data);

        Dispatcher.forwardEvent(GeoRepoEvents.ATTACH_MAP_WIDGET, this.center);
        Dispatcher.forwardEvent(GeoRepoEvents.ATTACH_TOOLBAR, this.north);
    }

    /**
     * Configure accordion panel.
     */
    private void configureAccordionPanel() {
        AppController controller = (AppController) this.getController();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.View#handleEvent(com.extjs.gxt.ui.client.mvc.AppEvent)
     */
    @Override
    protected void handleEvent(AppEvent event) {
        if (event.getType() == GeoRepoEvents.INIT_GEOREPO_MAIN_UI) {
            initUI();
        }
        if (event.getType() == GeoRepoEvents.ADMIN_MODE_CHANGE) {
            // onAdminModeChange(event);
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
        Dispatcher.forwardEvent(GeoRepoEvents.ERASE_AOI_FEATURES);
        Dispatcher.forwardEvent(GeoRepoEvents.AOI_MANAGEMENT_UNBIND);
        configureAccordionPanel();
        this.east.layout();
    }

    /**
     * Reload.
     */
    public native void reload()
    /*-{
        $wnd.window.location.reload();
    }-*/;

}