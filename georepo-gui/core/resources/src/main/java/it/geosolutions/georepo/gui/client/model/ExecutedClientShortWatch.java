/*
 * $ Header: it.geosolutions.georepo.gui.client.model.ExecutedClientShortWatch,v. 0.1 3-gen-2011 17.06.11 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import java.util.Date;

import com.extjs.gxt.ui.client.data.BeanModel;

// TODO: Auto-generated Javadoc
/**
 * The Class ExecutedClientShortWatch.
 */
public class ExecutedClientShortWatch extends BeanModel {

    /**
     * The Enum ExecutedShortWatchKeyValue.
     */
    public enum ExecutedShortWatchKeyValue {

        /** The ID. */
        ID("executedShortId"),
        /** The TITLE. */
        TITLE("executedShortTitle"),
        /** The LO g_ msg. */
        LOG_MSG("executedShortMsg"),
        /** The LO g_ type. */
        LOG_TYPE("executedShortType"),
        /** The LO g_ date. */
        LOG_DATE("executedShortDate");

        /** The value. */
        private String value;

        /**
         * Instantiates a new executed short watch key value.
         * 
         * @param value
         *            the value
         */
        ExecutedShortWatchKeyValue(String value) {
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

    /** The log msg. */
    private String logMsg;

    /** The log type. */
    private String logType;

    /** The log date. */
    private Date logDate;

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
        set(ExecutedShortWatchKeyValue.ID.getValue(), this.id);
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
        set(ExecutedShortWatchKeyValue.TITLE.getValue(), this.title);
    }

    /**
     * Gets the log msg.
     * 
     * @return the log msg
     */
    public String getLogMsg() {
        return logMsg;
    }

    /**
     * Sets the log msg.
     * 
     * @param logMsg
     *            the new log msg
     */
    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
        set(ExecutedShortWatchKeyValue.LOG_MSG.getValue(), this.logMsg);
    }

    /**
     * Gets the log type.
     * 
     * @return the log type
     */
    public String getLogType() {
        return logType;
    }

    /**
     * Sets the log type.
     * 
     * @param logType
     *            the new log type
     */
    public void setLogType(String logType) {
        this.logType = logType;
        set(ExecutedShortWatchKeyValue.LOG_TYPE.getValue(), this.logType);
    }

    /**
     * Gets the log date.
     * 
     * @return the log date
     */
    public Date getLogDate() {
        return logDate;
    }

    /**
     * Sets the log date.
     * 
     * @param logDate
     *            the new log date
     */
    public void setLogDate(Date logDate) {
        this.logDate = logDate;
        set(ExecutedShortWatchKeyValue.LOG_DATE.getValue(), this.logDate);
    }
}
