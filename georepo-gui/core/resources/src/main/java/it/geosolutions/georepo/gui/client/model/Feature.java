/*
 * $ Header: it.geosolutions.georepo.gui.client.model.Feature,v. 0.1 3-gen-2011 17.06.12 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 17.06.12 $
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
 * The Class Feature.
 */
public class Feature extends BeanModel {

    /**
     * The Enum FeatureKeyValue.
     */
    public enum FeatureKeyValue {

        /** The ID. */
        ID("id"),
        /** The DAT e_ creation. */
        DATE_CREATION("creationDate"),
        /** The EXTERNA l_ id. */
        EXTERNAL_ID("externalId"),
        /** The EXTERNA l_ sortin g_ date. */
        EXTERNAL_SORTING_DATE("externalSortingDate"),
        /** The USER. */
        USER("user"),
        /** The TITLE. */
        TITLE("title"),
        /** The AOI. */
        AOI("aoi"),
        /** The WF s_ respons e_ blob. */
        WFS_RESPONSE_BLOB("wfsResponseBlob"),
        /** The LAS t_ sen t_ b y_ mail. */
        LAST_SENT_BY_MAIL("lastSentByMail"),
        /** The LAS t_ sen t_ b y_ rss. */
        LAST_SENT_BY_RSS("lastSentByRss");

        /** The value. */
        private String value;

        /**
         * Instantiates a new feature key value.
         * 
         * @param value
         *            the value
         */
        FeatureKeyValue(String value) {
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
    private static final long serialVersionUID = 5790884760115450217L;

    /** The id. */
    private long id;

    /** The title. */
    private String title;

    /** The user. */
    private User user;

    /** The creation date. */
    private Date creationDate;

    /** The external id. */
    private String externalId;

    /** The external sorting date. */
    private Date externalSortingDate;

    /** The wfs response blob. */
    private String wfsResponseBlob;

    /** The last sent by mail. */
    private Date lastSentByMail;

    /** The last sent by rss. */
    private Date lastSentByRSS;

    // private Polygon geometry;

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
        set(FeatureKeyValue.ID.getValue(), this.id);
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
        set(FeatureKeyValue.TITLE.getValue(), this.title);
    }

    /**
     * Gets the user.
     * 
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user.
     * 
     * @param user
     *            the new user
     */
    public void setUser(User user) {
        this.user = user;
        set(FeatureKeyValue.USER.getValue(), this.user == null ? null : this.user.getName());
    }

    /**
     * Gets the creation date.
     * 
     * @return the creation date
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date.
     * 
     * @param creationDate
     *            the new creation date
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        set(FeatureKeyValue.DATE_CREATION.getValue(), this.creationDate);
    }

    /**
     * Gets the ID of this feature in the source system.
     * 
     * @return the ID of this feature in the source system
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * Sets the ID of this feature in the source system.
     * 
     * @param externalId
     *            the new ID of this feature in the source system
     */
    public void setExternalId(String externalId) {
        this.externalId = externalId;
        set(FeatureKeyValue.EXTERNAL_ID.getValue(), this.externalId);
    }

    /**
     * Gets the a Date attribute in the source system that is monotonically ascending.
     * 
     * @return the a Date attribute in the source system that is monotonically ascending
     */
    public Date getExternalSortingDate() {
        return externalSortingDate;
    }

    /**
     * Sets the a Date attribute in the source system that is monotonically ascending.
     * 
     * @param externalSortingDate
     *            the new a Date attribute in the source system that is monotonically ascending
     */
    public void setExternalSortingDate(Date externalSortingDate) {
        this.externalSortingDate = externalSortingDate;
        set(FeatureKeyValue.EXTERNAL_SORTING_DATE.getValue(), this.externalSortingDate);
    }

    /**
     * Gets the wfs response blob.
     * 
     * @return the wfs response blob
     */
    public String getWfsResponseBlob() {
        return wfsResponseBlob;
    }

    /**
     * Sets the wfs response blob.
     * 
     * @param wfsResponseBlob
     *            the new wfs response blob
     */
    public void setWfsResponseBlob(String wfsResponseBlob) {
        this.wfsResponseBlob = wfsResponseBlob;
        set(FeatureKeyValue.WFS_RESPONSE_BLOB.getValue(), this.wfsResponseBlob);
    }

    /**
     * Gets the last sent by mail.
     * 
     * @return the last sent by mail
     */
    public Date getLastSentByMail() {
        return lastSentByMail;
    }

    /**
     * Sets the last sent by mail.
     * 
     * @param lastSentByMail
     *            the new last sent by mail
     */
    public void setLastSentByMail(Date lastSentByMail) {
        this.lastSentByMail = lastSentByMail;
        set(FeatureKeyValue.LAST_SENT_BY_MAIL.getValue(), this.lastSentByMail);
    }

    /**
     * Gets the last sent by rss.
     * 
     * @return the last sent by rss
     */
    public Date getLastSentByRSS() {
        return lastSentByRSS;
    }

    /**
     * Sets the last sent by rss.
     * 
     * @param lastSentByRSS
     *            the new last sent by rss
     */
    public void setLastSentByRSS(Date lastSentByRSS) {
        this.lastSentByRSS = lastSentByRSS;
        set(FeatureKeyValue.LAST_SENT_BY_RSS.getValue(), this.lastSentByRSS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Feature [id=" + id + ", title=" + title + ", user=" + user + ", creationDate="
                + creationDate + ", externalId=" + externalId + ", externalSortingDate="
                + externalSortingDate + ", wfsResponseBlob=" + wfsResponseBlob
                + ", lastSentByMail=" + lastSentByMail + ", lastSentByRSS=" + lastSentByRSS + "]";
    }
}
