/*
 * $Header: it.geosolutions.georepo.gui.client.model.Filter,v. 0.1 18/ago/2010 09.33.09 created by frank $
 * $Revision: 0.1 $
 * $Date: 18/ago/2010 09.33.09 $
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

/**
 * @author frank
 * 
 */
public class Filter extends BeanModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3211603941157005133L;

	public enum FilterKeyValue {
		LEGACY_ID("legacyId"), AOI("aoi"), OWNER("ownerFilter"), ACQ_DATE(
				"acqDate"), DATA_LAYER("dataLayer"), CLOUD_COVER("cloudCover"), /*RETRIEVE_TYPE(
				"retrieveType"), RETRIEVE_TYPE_INCREMENTAL(
				"retrieveTypeIncremental"),*/ FILTER_LEGACY("filterLegacy"), FILTER_ACQ_DATE(
				"filterAcqDate"), FILTER_DATA_LAYER("filterDataLayer"), FILTER_CLOUD_COVER(
				"filterCloudCover"), SOURCE_ID("sourceId"), FILTER_SOURCE("filterSource");

		private String value;

		FilterKeyValue(String value) {
			this.value = value;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

	}

	private String legacyId;
	private String filterLegacy;
	private Date acqDate;
	private String filterAcqDate;
	private String dataLayer;
	private String filterDataLayer;
	private Float cloudCover;
	private String filterCloudCover;
	private String sourceId;
	private String filterSource;
	private long userID;
	private long aoiID;

	/**
	 * @return the legacyId
	 */
	public String getLegacyId() {
		return legacyId;
	}

	/**
	 * @param legacyId
	 *            the legacyId to set
	 */
	public void setLegacyId(String legacyId) {
		this.legacyId = legacyId;
		set(FilterKeyValue.LEGACY_ID.getValue(), this.legacyId);
	}

	/**
	 * @return the acqDate
	 */
	public Date getAcqDate() {
		return acqDate;
	}

	/**
	 * @param acqDate
	 *            the acqDate to set
	 */
	public void setAcqDate(Date acqDate) {
		this.acqDate = acqDate;
		set(FilterKeyValue.ACQ_DATE.getValue(), this.acqDate);
	}

	/**
	 * @return the dataLayer
	 */
	public String getDataLayer() {
		return dataLayer;
	}

	/**
	 * @param dataLayer
	 *            the dataLayer to set
	 */
	public void setDataLayer(String dataLayer) {
		this.dataLayer = dataLayer;
		set(FilterKeyValue.DATA_LAYER.getValue(), this.dataLayer);
	}

	/**
	 * @return the cloudCover
	 */
	public Float getCloudCover() {
		return cloudCover;
	}

	/**
	 * @param cloudCover
	 *            the cloudCover to set
	 */
	public void setCloudCover(Float cloudCover) {
		this.cloudCover = cloudCover;
		set(FilterKeyValue.CLOUD_COVER.getValue(), this.cloudCover);
	}

//	/**
//	 * @return the retrieveType
//     * @deprecated
//	 */
//	public String getRetrieveType() {
//		return retrieveType;
//	}
//
//	/**
//	 * @param retrieveType
//	 *            the retrieveType to set
//     * @deprecated
//	 */
//	public void setRetrieveType(String retrieveType) {
//		this.retrieveType = retrieveType;
//		set(FilterKeyValue.RETRIEVE_TYPE.getValue(), this.retrieveType);
//	}

	/**
	 * @return the userID
	 */
	public long getUserID() {
		return userID;
	}

	/**
	 * @param userID
	 *            the userID to set
	 */
	public void setUserID(long userID) {
		this.userID = userID;
	}

	/**
	 * @return the aoiID
	 */
	public long getAoiID() {
		return aoiID;
	}

	/**
	 * @param aoiID
	 *            the aoiID to set
	 */
	public void setAoiID(long aoiID) {
		this.aoiID = aoiID;
	}

	/**
	 * @return the filterLegacy
	 */
	public String getFilterLegacy() {
		return filterLegacy;
	}

	/**
	 * @param filterLegacy
	 *            the filterLegacy to set
	 */
	public void setFilterLegacy(String filterLegacy) {
		this.filterLegacy = filterLegacy;
		set(FilterKeyValue.FILTER_LEGACY.getValue(), this.filterLegacy);
	}

	/**
	 * @return the filterAcqDate
	 */
	public String getFilterAcqDate() {
		return filterAcqDate;
	}

	/**
	 * @param filterAcqDate
	 *            the filterAcqDate to set
	 */
	public void setFilterAcqDate(String filterAcqDate) {
		this.filterAcqDate = filterAcqDate;
		set(FilterKeyValue.FILTER_ACQ_DATE.getValue(), this.filterAcqDate);
	}

	/**
	 * @return the filterDataLayer
	 */
	public String getFilterDataLayer() {
		return filterDataLayer;
	}

	/**
	 * @param filterDataLayer
	 *            the filterDataLayer to set
	 */
	public void setFilterDataLayer(String filterDataLayer) {
		this.filterDataLayer = filterDataLayer;
		set(FilterKeyValue.FILTER_DATA_LAYER.getValue(), this.filterDataLayer);
	}

	/**
	 * @return the filterCloudCover
	 */
	public String getFilterCloudCover() {
		return filterCloudCover;
	}

	/**
	 * @param filterCloudCover
	 *            the filterCloudCover to set
	 */
	public void setFilterCloudCover(String filterCloudCover) {
		this.filterCloudCover = filterCloudCover;
		set(FilterKeyValue.FILTER_CLOUD_COVER.getValue(), this.filterCloudCover);
	}
	
	/**
	 * @return the sourceId
	 */
	public String getSourceId() {
		return sourceId;
	}

	/**
	 * @param sourceId the sourceId to set
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
		set(FilterKeyValue.SOURCE_ID.getValue(), this.sourceId);
	}
	
	/**
	 * @return the filterSource
	 */
	public String getFilterSource() {
		return filterSource;
	}

	/**
	 * @param filterSource the filterSource to set
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
		return "Filter [legacyId=" + legacyId + ", filterLegacy="
				+ filterLegacy + ", acqDate=" + acqDate + ", filterAcqDate="
				+ filterAcqDate + ", dataLayer=" + dataLayer
				+ ", filterDataLayer=" + filterDataLayer + ", cloudCover="
				+ cloudCover + ", filterCloudCover=" + filterCloudCover
				/*+ ", retrieveType=" + retrieveType*/ + ", userID=" + userID
				+ ", aoiID=" + aoiID + "]";
	}

}
