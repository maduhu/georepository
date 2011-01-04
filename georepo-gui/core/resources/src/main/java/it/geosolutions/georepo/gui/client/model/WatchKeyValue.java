/*
 * $ Header: it.geosolutions.georepo.gui.client.model.WatchKeyValue,v. 0.1 3-gen-2011 17.06.11 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 17.06.11 $
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
package it.geosolutions.georepo.gui.client.model;

// TODO: Auto-generated Javadoc
/**
 * The Enum WatchKeyValue.
 */
public enum WatchKeyValue {

    /** The WATC h_ title. */
    WATCH_TITLE("watchTitle"), /** The WATC h_ begi n_ date. */
    WATCH_BEGIN_DATE("watchBeginDate"), /** The WATC h_ expiration. */
    WATCH_EXPIRATION("watchExpirationDate"),
    /** The WATC h_ id. */
    WATCH_ID("watchId"),
    /** The WATC h_ ao i_ id. */
    WATCH_AOI_ID("watchAOIId"),
    /** The WATC h_ member. */
    WATCH_MEMBER("watchMember"),
    /** The WATC h_ filter. */
    WATCH_FILTER("watchFilter"),
    /** The WATC h_ actio n_ id. */
    WATCH_ACTION_ID("watchActionId"),
    /** The WATC h_ notification. */
    WATCH_NOTIFICATION("watchNotification"),
    /** The WATC h_ runtime. */
    WATCH_RUNTIME("watchRunetime");

    /** The value. */
    private String value;

    /**
     * Instantiates a new watch key value.
     * 
     * @param value
     *            the value
     */
    WatchKeyValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue() {
        return value;
    }
}
