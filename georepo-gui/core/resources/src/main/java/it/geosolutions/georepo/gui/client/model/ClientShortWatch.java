/*
 * $ Header: it.geosolutions.georepo.gui.client.model.ClientShortWatch,v. 0.1 3-gen-2011 17.06.11 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import com.extjs.gxt.ui.client.data.BeanModel;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientShortWatch.
 */
public class ClientShortWatch extends BeanModel {

    /**
     * The Enum ShortWatchKeyValue.
     */
    public enum ShortWatchKeyValue {

        /** The ID. */
        ID("shortId"),
        /** The TITLE. */
        TITLE("shortTitle"),
        /** The ACTION. */
        ACTION("shortAction"),
        /** The AOITITLE. */
        AOITITLE("shortAoiTitle");

        /** The value. */
        private String value;

        /**
         * Instantiates a new short watch key value.
         * 
         * @param value
         *            the value
         */
        ShortWatchKeyValue(String value) {
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

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6410938170911933374L;

    /** The id. */
    private long id;

    /** The title. */
    private String title;

    /** The action. */
    private String action;

    /** The aoi title. */
    private String aoiTitle;

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id
     *            the new id
     */
    public void setId(long id) {
        this.id = id;
        set(ShortWatchKeyValue.ID.getValue(), this.id);
    }

    /**
     * Gets the title.
     * 
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     * 
     * @param title
     *            the new title
     */
    public void setTitle(String title) {
        this.title = title;
        set(ShortWatchKeyValue.TITLE.getValue(), this.title);
    }

    /**
     * Gets the action.
     * 
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the action.
     * 
     * @param action
     *            the new action
     */
    public void setAction(String action) {
        this.action = action;
        set(ShortWatchKeyValue.ACTION.getValue(), this.action);
    }

    /**
     * Gets the aoi title.
     * 
     * @return the aoi title
     */
    public String getAoiTitle() {
        return aoiTitle;
    }

    /**
     * Sets the aoi title.
     * 
     * @param aoiTitle
     *            the new aoi title
     */
    public void setAoiTitle(String aoiTitle) {
        this.aoiTitle = aoiTitle;
        set(ShortWatchKeyValue.AOITITLE.getValue(), this.aoiTitle);
    }

}
