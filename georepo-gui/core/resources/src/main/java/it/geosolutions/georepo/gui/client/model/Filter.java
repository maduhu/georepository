/*
 * $ Header: it.geosolutions.georepo.gui.client.model.Filter,v. 0.1 3-gen-2011 17.06.11 created by afabiani <alessio.fabiani at geo-solutions.it> $
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
 * The Class Filter.
 */
public class Filter extends BeanModel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3211603941157005133L;

    /**
     * The Enum FilterKeyValue.
     */
    public enum FilterKeyValue {

        /** The LEGAC y_ id. */
        LEGACY_ID("legacyId"),
        /** The AOI. */
        AOI("aoi"),
        /** The OWNER. */
        OWNER("ownerFilter"),
        /** The AC q_ date. */
        ACQ_DATE("acqDate"),
        /** The DAT a_ layer. */
        DATA_LAYER("dataLayer"),
        /** The CLOU d_ cover. */
        CLOUD_COVER("cloudCover"), /*
                                    * RETRIEVE_TYPE( "retrieveType"), RETRIEVE_TYPE_INCREMENTAL(
                                    * "retrieveTypeIncremental"),
                                    *//** The FILTE r_ legacy. */
        FILTER_LEGACY("filterLegacy"),
        /** The FILTE r_ ac q_ date. */
        FILTER_ACQ_DATE("filterAcqDate"),
        /** The FILTE r_ dat a_ layer. */
        FILTER_DATA_LAYER("filterDataLayer"),
        /** The FILTE r_ clou d_ cover. */
        FILTER_CLOUD_COVER("filterCloudCover"),
        /** The SOURC e_ id. */
        SOURCE_ID("sourceId"),
        /** The FILTE r_ source. */
        FILTER_SOURCE("filterSource");

        /** The value. */
        private String value;

        /**
         * Instantiates a new filter key value.
         * 
         * @param value
         *            the value
         */
        FilterKeyValue(String value) {
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

    /** The legacy id. */
    private String legacyId;

    /** The filter legacy. */
    private String filterLegacy;

    /** The acq date. */
    private Date acqDate;

    /** The filter acq date. */
    private String filterAcqDate;

    /** The data layer. */
    private String dataLayer;

    /** The filter data layer. */
    private String filterDataLayer;

    /** The cloud cover. */
    private Float cloudCover;

    /** The filter cloud cover. */
    private String filterCloudCover;

    /** The source id. */
    private String sourceId;

    /** The filter source. */
    private String filterSource;

    /** The user id. */
    private long userID;

    /** The aoi id. */
    private long aoiID;

    /**
     * Gets the legacy id.
     * 
     * @return the legacy id
     */
    public String getLegacyId() {
        return legacyId;
    }

    /**
     * Sets the legacy id.
     * 
     * @param legacyId
     *            the new legacy id
     */
    public void setLegacyId(String legacyId) {
        this.legacyId = legacyId;
        set(FilterKeyValue.LEGACY_ID.getValue(), this.legacyId);
    }

    /**
     * Gets the acq date.
     * 
     * @return the acq date
     */
    public Date getAcqDate() {
        return acqDate;
    }

    /**
     * Sets the acq date.
     * 
     * @param acqDate
     *            the new acq date
     */
    public void setAcqDate(Date acqDate) {
        this.acqDate = acqDate;
        set(FilterKeyValue.ACQ_DATE.getValue(), this.acqDate);
    }

    /**
     * Gets the data layer.
     * 
     * @return the data layer
     */
    public String getDataLayer() {
        return dataLayer;
    }

    /**
     * Sets the data layer.
     * 
     * @param dataLayer
     *            the new data layer
     */
    public void setDataLayer(String dataLayer) {
        this.dataLayer = dataLayer;
        set(FilterKeyValue.DATA_LAYER.getValue(), this.dataLayer);
    }

    /**
     * Gets the cloud cover.
     * 
     * @return the cloud cover
     */
    public Float getCloudCover() {
        return cloudCover;
    }

    /**
     * Sets the cloud cover.
     * 
     * @param cloudCover
     *            the new cloud cover
     */
    public void setCloudCover(Float cloudCover) {
        this.cloudCover = cloudCover;
        set(FilterKeyValue.CLOUD_COVER.getValue(), this.cloudCover);
    }

    // /**
    // * @return the retrieveType
    // * @deprecated
    // */
    // public String getRetrieveType() {
    // return retrieveType;
    // }
    //
    // /**
    // * @param retrieveType
    // * the retrieveType to set
    // * @deprecated
    // */
    // public void setRetrieveType(String retrieveType) {
    // this.retrieveType = retrieveType;
    // set(FilterKeyValue.RETRIEVE_TYPE.getValue(), this.retrieveType);
    // }

    /**
     * Gets the user id.
     * 
     * @return the user id
     */
    public long getUserID() {
        return userID;
    }

    /**
     * Sets the user id.
     * 
     * @param userID
     *            the new user id
     */
    public void setUserID(long userID) {
        this.userID = userID;
    }

    /**
     * Gets the aoi id.
     * 
     * @return the aoi id
     */
    public long getAoiID() {
        return aoiID;
    }

    /**
     * Sets the aoi id.
     * 
     * @param aoiID
     *            the new aoi id
     */
    public void setAoiID(long aoiID) {
        this.aoiID = aoiID;
    }

    /**
     * Gets the filter legacy.
     * 
     * @return the filter legacy
     */
    public String getFilterLegacy() {
        return filterLegacy;
    }

    /**
     * Sets the filter legacy.
     * 
     * @param filterLegacy
     *            the new filter legacy
     */
    public void setFilterLegacy(String filterLegacy) {
        this.filterLegacy = filterLegacy;
        set(FilterKeyValue.FILTER_LEGACY.getValue(), this.filterLegacy);
    }

    /**
     * Gets the filter acq date.
     * 
     * @return the filter acq date
     */
    public String getFilterAcqDate() {
        return filterAcqDate;
    }

    /**
     * Sets the filter acq date.
     * 
     * @param filterAcqDate
     *            the new filter acq date
     */
    public void setFilterAcqDate(String filterAcqDate) {
        this.filterAcqDate = filterAcqDate;
        set(FilterKeyValue.FILTER_ACQ_DATE.getValue(), this.filterAcqDate);
    }

    /**
     * Gets the filter data layer.
     * 
     * @return the filter data layer
     */
    public String getFilterDataLayer() {
        return filterDataLayer;
    }

    /**
     * Sets the filter data layer.
     * 
     * @param filterDataLayer
     *            the new filter data layer
     */
    public void setFilterDataLayer(String filterDataLayer) {
        this.filterDataLayer = filterDataLayer;
        set(FilterKeyValue.FILTER_DATA_LAYER.getValue(), this.filterDataLayer);
    }

    /**
     * Gets the filter cloud cover.
     * 
     * @return the filter cloud cover
     */
    public String getFilterCloudCover() {
        return filterCloudCover;
    }

    /**
     * Sets the filter cloud cover.
     * 
     * @param filterCloudCover
     *            the new filter cloud cover
     */
    public void setFilterCloudCover(String filterCloudCover) {
        this.filterCloudCover = filterCloudCover;
        set(FilterKeyValue.FILTER_CLOUD_COVER.getValue(), this.filterCloudCover);
    }

    /**
     * Gets the source id.
     * 
     * @return the source id
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * Sets the source id.
     * 
     * @param sourceId
     *            the new source id
     */
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
        set(FilterKeyValue.SOURCE_ID.getValue(), this.sourceId);
    }

    /**
     * Gets the filter source.
     * 
     * @return the filter source
     */
    public String getFilterSource() {
        return filterSource;
    }

    /**
     * Sets the filter source.
     * 
     * @param filterSource
     *            the new filter source
     */
    public void setFilterSource(String filterSource) {
        this.filterSource = filterSource;
        set(FilterKeyValue.FILTER_SOURCE.getValue(), this.filterSource);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Filter [legacyId=" + legacyId + ", filterLegacy=" + filterLegacy + ", acqDate="
                + acqDate + ", filterAcqDate=" + filterAcqDate + ", dataLayer=" + dataLayer
                + ", filterDataLayer=" + filterDataLayer + ", cloudCover=" + cloudCover
                + ", filterCloudCover=" + filterCloudCover
                /* + ", retrieveType=" + retrieveType */+ ", userID=" + userID + ", aoiID=" + aoiID
                + "]";
    }

}
