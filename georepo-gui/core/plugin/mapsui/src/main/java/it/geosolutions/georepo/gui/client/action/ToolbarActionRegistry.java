/*
 * $ Header: it.geosolutions.georepo.gui.client.action.ToolbarActionRegistry,v. 0.1 25-gen-2011 11.30.33 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.30.33 $
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
package it.geosolutions.georepo.gui.client.action;

import it.geosolutions.georepo.gui.client.action.application.GeoRepoInfoAppAction;
import it.geosolutions.georepo.gui.client.action.application.LogoutAction;
import it.geosolutions.georepo.gui.client.action.toolbar.ZoomInAction;
import it.geosolutions.georepo.gui.client.action.toolbar.ZoomOutAction;

import java.util.HashMap;
import java.util.Map;

import org.gwtopenmaps.openlayers.client.MapWidget;

// TODO: Auto-generated Javadoc
/**
 * The Class ToolbarActionRegistry.
 */
public final class ToolbarActionRegistry {

    /** The Constant REGISTRY. */
    private static final Map<String, ToolActionCreator> REGISTRY;

    static {
        REGISTRY = new HashMap<String, ToolActionCreator>();

        REGISTRY.put("dgwatchInfoApp", new ToolActionCreator() {

            public ToolbarAction createActionTool(MapWidget mapWidget) {
                return new GeoRepoInfoAppAction();
            }
        });

        REGISTRY.put("zoomIn", new ToolActionCreator() {

            public ToolbarAction createActionTool(MapWidget mapWidget) {
                return new ZoomInAction(mapWidget);
            }
        });

        REGISTRY.put("zoomOut", new ToolActionCreator() {

            public ToolbarAction createActionTool(MapWidget mapWidget) {
                return new ZoomOutAction(mapWidget);
            }
        });

//        REGISTRY.put("drawFeature", new ToolActionCreator() {
//
//            public ToolbarAction createActionTool(MapWidget mapWidget) {
//                return new DrawFeatureAction();
//            }
//        });
//
//        REGISTRY.put("uploadSHP", new ToolActionCreator() {
//
//            public ToolbarAction createActionTool(MapWidget mapWidget) {
//                return new UploadAction();
//            }
//        });

        REGISTRY.put("logout", new ToolActionCreator() {

            public ToolbarAction createActionTool(MapWidget mapWidget) {
                return new LogoutAction();
            }
        });

//        REGISTRY.put("cleanDGWMenu", new ToolActionCreator() {
//
//            public ToolbarAction createActionTool(MapWidget mapWidget) {
//                return new CleanDgWatchMenu();
//            }
//        });
//
//        REGISTRY.put("quartzMonitoring", new ToolActionCreator() {
//
//            public ToolbarAction createActionTool(MapWidget mapWidget) {
//                return new QuartzMonitoringAction();
//            }
//        });
//
//        REGISTRY.put("save", new ToolActionCreator() {
//
//            public ToolbarAction createActionTool(MapWidget mapWidget) {
//                return new SaveAction();
//            }
//        });
//
//        REGISTRY.put("deleteContent", new ToolActionCreator() {
//
//            public ToolbarAction createActionTool(MapWidget mapWidget) {
//                return new DeleteContentAction();
//            }
//        });
//
//        REGISTRY.put("administrationMode", new ToolActionCreator() {
//
//            public ToolbarAction createActionTool(MapWidget mapWidget) {
//                return new AdministrationModeAction();
//            }
//        });
    }

    /**
     * Put.
     * 
     * @param key
     *            the key
     * @param toolActionCreator
     *            the tool action creator
     */
    public static void put(String key, ToolActionCreator toolActionCreator) {
        if (key != null && toolActionCreator != null)
            REGISTRY.put(key, toolActionCreator);
    }

    /**
     * Gets the.
     * 
     * @param key
     *            the key
     * @param mapWidget
     *            the map widget
     * @return the toolbar action
     */
    public static ToolbarAction get(String key, MapWidget mapWidget) {
        ToolActionCreator toolActionCreator = REGISTRY.get(key);
        if (toolActionCreator == null)
            return null;
        return toolActionCreator.createActionTool(mapWidget);
    }
}
