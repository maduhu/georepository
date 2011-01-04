/*
 * $ Header: it.geosolutions.georepo.gui.client.model.AOI,v. 0.1 3-gen-2011 17.06.11 created by afabiani <alessio.fabiani at geo-solutions.it> $
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
 * The Class AOI.
 */
public class AOI extends BeanModel {

    /**
     * The Enum AOIKeyValue.
     */
    public enum AOIKeyValue {

        /** The ID. */
        ID("id"),
        /** The SHARED. */
        SHARED("shared"),
        /** The DAT e_ creation. */
        DATE_CREATION("dateCreation"),
        /** The LAS t_ update. */
        LAST_UPDATE("lastUpdate"),
        /** The EXPIRATION. */
        EXPIRATION("expiration"),
        /** The AREA. */
        AREA("area"),
        /** The OWNER. */
        OWNER("owner"),
        /** The TITLE. */
        TITLE("title"),
        /** The AOI. */
        AOI("aoi");

        /** The value. */
        private String value;

        /**
         * Instantiates a new aOI key value.
         * 
         * @param value
         *            the value
         */
        AOIKeyValue(String value) {
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
    private static final long serialVersionUID = -483120103393713402L;

    /** The id. */
    private long id;

    /** The title. */
    private String title;

    /** The shared. */
    private boolean shared;

    /** The date creation. */
    private Date dateCreation;

    /** The last update. */
    private Date lastUpdate;

    /** The expiration. */
    private Date expiration;

    /** The area. */
    private String area;

    /** The wkt. */
    private String wkt;

    /** The owner. */
    private User owner;

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
        set(AOIKeyValue.ID.getValue(), this.id);
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
        set(AOIKeyValue.TITLE.getValue(), this.title);
    }

    /**
     * Checks if is shared.
     * 
     * @return true, if is shared
     */
    public boolean isShared() {
        return shared;
    }

    /**
     * Sets the shared.
     * 
     * @param shared
     *            the new shared
     */
    public void setShared(boolean shared) {
        this.shared = shared;
        set(AOIKeyValue.SHARED.getValue(), this.shared);
    }

    /**
     * Gets the date creation.
     * 
     * @return the date creation
     */
    public Date getDateCreation() {
        return dateCreation;
    }

    /**
     * Sets the date creation.
     * 
     * @param dateCreation
     *            the new date creation
     */
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
        set(AOIKeyValue.DATE_CREATION.getValue(), this.dateCreation);
    }

    /**
     * Gets the last update.
     * 
     * @return the last update
     */
    public Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the last update.
     * 
     * @param lastUpdate
     *            the new last update
     */
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
        set(AOIKeyValue.LAST_UPDATE.getValue(), this.lastUpdate);
    }

    /**
     * Gets the expiration.
     * 
     * @return the expiration
     */
    public Date getExpiration() {
        return expiration;
    }

    /**
     * Sets the expiration.
     * 
     * @param expiration
     *            the new expiration
     */
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
        set(AOIKeyValue.EXPIRATION.getValue(), this.expiration);
    }

    /**
     * Gets the area.
     * 
     * @return the area
     */
    public String getArea() {
        return area;
    }

    /**
     * Sets the area.
     * 
     * @param area
     *            the new area
     */
    public void setArea(String area) {
        this.area = area;
        set(AOIKeyValue.AREA.getValue(), this.area);
    }

    /**
     * Gets the wkt.
     * 
     * @return the wkt
     */
    public String getWkt() {
        return wkt;
    }

    /**
     * Sets the wkt.
     * 
     * @param wkt
     *            the new wkt
     */
    public void setWkt(String wkt) {
        this.wkt = wkt;
    }

    /**
     * Gets the owner.
     * 
     * @return the owner
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Sets the owner.
     * 
     * @param owner
     *            the new owner
     */
    public void setOwner(User owner) {
        this.owner = owner;
        set(AOIKeyValue.OWNER.getValue(), this.owner == null ? "Public AOI" : this.owner
                .getUserName());
    }

    /**
     * Sets the aoi.
     */
    public void setAoi() {
        set(AOIKeyValue.AOI.getValue(), this.id + " - " + this.title);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AOI other = (AOI) obj;
        return id == other.id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AOI [id=" + id + ", shared=" + shared + ", dateCreation=" + dateCreation
                + ", lastUpdate=" + lastUpdate + ", expiration=" + expiration + ", area=" + area
                + ", owner=" + owner + "]";
    }

    // public void notifyState() {
    // if (shared)
    // Dispatcher.forwardEvent(DGWATCHEvents.SHARE_AOI_ENABLE);
    // else
    // Dispatcher.forwardEvent(DGWATCHEvents.SHARE_AOI_DISABLE);
    //
    // Dispatcher.forwardEvent(DGWATCHEvents.AOI_SELECTED, this);
    // }
}
