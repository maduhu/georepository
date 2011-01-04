/*
 * $ Header: it.geosolutions.georepo.gui.client.model.GeoConstraint,v. 0.1 3-gen-2011 17.06.11 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.georepo.gui.client.DGWATCHEvents;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.mvc.Dispatcher;

// TODO: Auto-generated Javadoc
/**
 * The Class GeoConstraint.
 */
public class GeoConstraint extends BeanModel {

    /**
     * The Enum GeoConstraintKeyValue.
     */
    public enum GeoConstraintKeyValue {

        /** The ID. */
        ID("id"),
        /** The NAME. */
        NAME("name"),
        /** The GEOCONSTRAINT. */
        GEOCONSTRAINT("geoConstraint");

        /** The value. */
        private String value;

        /**
         * Instantiates a new geo constraint key value.
         * 
         * @param value
         *            the value
         */
        GeoConstraintKeyValue(String value) {
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
    private static final long serialVersionUID = -483120103393713423L;

    /** The id. */
    private Integer id;

    /** The name. */
    private String name;

    /** The wkt. */
    private String wkt;

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id
     *            the new id
     */
    public void setId(Integer id) {
        this.id = id;
        set(GeoConstraintKeyValue.ID.getValue(), this.id);
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *            the new name
     */
    public void setName(String name) {
        this.name = name;
        set(GeoConstraintKeyValue.NAME.getValue(), this.name);
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
        set(GeoConstraintKeyValue.GEOCONSTRAINT.getValue(), this.wkt);
    }

    // /**
    // * the aoi to set
    // */
    // public void setGeoConstraint() {
    // set(GeoConstraintKeyValue.GEOCONSTRAINT.getValue(), this.id + " - " + this.name);
    // }

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
        GeoConstraint other = (GeoConstraint) obj;
        return id == other.id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GeoConstraint [id=" + id + ", name=" + name + "]";
    }

    /**
     * Notify state.
     */
    public void notifyState() {
        Dispatcher.forwardEvent(DGWATCHEvents.GEOCONSTRAINT_SELECTED, this);
    }

}