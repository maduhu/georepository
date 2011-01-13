/*
 * $ Header: it.geosolutions.georepo.gui.client.action.menu.MenuActionRegistry,v. 0.1 3-gen-2011 16.51.45 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 16.51.45 $
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
package it.geosolutions.georepo.gui.client.action.menu;

import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuActionRegistry.
 */
public final class MenuActionRegistry {

    /** The Constant REGISTRY. */
    private static final Map<String, MenuActionCreator> REGISTRY;

    static {
        REGISTRY = new HashMap<String, MenuActionCreator>();

        REGISTRY.put("quartzTriggerA", new MenuActionCreator() {

            public MenuAction createAction() {
                // TODO Auto-generated method stub
                return new RunTrigger1();
            }
        });

        REGISTRY.put("quartzTriggerB", new MenuActionCreator() {

            public MenuAction createAction() {
                // TODO Auto-generated method stub
                return new RunTrigger4();
            }
        });

        REGISTRY.put("quartzTriggerC", new MenuActionCreator() {

            public MenuAction createAction() {
                // TODO Auto-generated method stub
                return new RunTrigger24();
            }
        });
    }

    /**
     * Put.
     * 
     * @param key
     *            the key
     * @param menuActionCreator
     *            the menu action creator
     */
    public static void put(String key, MenuActionCreator menuActionCreator) {
        if (key != null && menuActionCreator != null)
            REGISTRY.put(key, menuActionCreator);
    }

    /**
     * Gets the.
     * 
     * @param key
     *            the key
     * @return the menu action
     */
    public static MenuAction get(String key) {
        MenuActionCreator menuActionCreator = REGISTRY.get(key);
        if (menuActionCreator == null)
            return null;
        return menuActionCreator.createAction();
    }

}
