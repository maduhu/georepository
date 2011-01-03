/*
 * $Header: it.geosolutions.georepo.gui.client.model.AOI,v. 0.1 16/lug/2010 18.03.32 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 16/lug/2010 18.03.32 $
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
 * @author giuseppe
 * 
 */
public class AOI extends BeanModel {

	public enum AOIKeyValue {
		ID("id"), SHARED("shared"), DATE_CREATION("dateCreation"), LAST_UPDATE(
				"lastUpdate"), EXPIRATION("expiration"), AREA("area"), OWNER(
				"owner"), TITLE("title"), AOI("aoi");

		private String value;

		AOIKeyValue(String value) {
			this.value = value;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -483120103393713402L;

	private long id;
	private String title;
	private boolean shared;
	private Date dateCreation;
	private Date lastUpdate;
	private Date expiration;
	private String area;
	private String wkt;
	private User owner;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
		set(AOIKeyValue.ID.getValue(), this.id);
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
		set(AOIKeyValue.TITLE.getValue(), this.title);
	}

	/**
	 * @return the shared
	 */
	public boolean isShared() {
		return shared;
	}

	/**
	 * @param shared
	 *            the shared to set
	 */
	public void setShared(boolean shared) {
		this.shared = shared;
		set(AOIKeyValue.SHARED.getValue(), this.shared);
	}

	/**
	 * @return the dateCreation
	 */
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * @param dateCreation
	 *            the dateCreation to set
	 */
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
		set(AOIKeyValue.DATE_CREATION.getValue(), this.dateCreation);
	}

	/**
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * @param lastUpdate
	 *            the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
		set(AOIKeyValue.LAST_UPDATE.getValue(), this.lastUpdate);
	}

	/**
	 * @return the expiration
	 */
	public Date getExpiration() {
		return expiration;
	}

	/**
	 * @param expiration
	 *            the expiration to set
	 */
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
		set(AOIKeyValue.EXPIRATION.getValue(), this.expiration);
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
		set(AOIKeyValue.AREA.getValue(), this.area);
	}

	/**
	 * @return the wkt
	 */
	public String getWkt() {
		return wkt;
	}

	/**
	 * @param wkt
	 *            the wkt to set
	 */
	public void setWkt(String wkt) {
		this.wkt = wkt;
	}

	/**
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
		set(AOIKeyValue.OWNER.getValue(), this.owner == null ? "Public AOI"
				: this.owner.getUserName());
	}

	/**
	 * @param aoi
	 *            the aoi to set
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
		return "AOI [id=" + id + ", shared=" + shared + ", dateCreation="
				+ dateCreation + ", lastUpdate=" + lastUpdate + ", expiration="
				+ expiration + ", area=" + area + ", owner=" + owner + "]";
	}

//	public void notifyState() {
//		if (shared)
//			Dispatcher.forwardEvent(DGWATCHEvents.SHARE_AOI_ENABLE);
//		else
//			Dispatcher.forwardEvent(DGWATCHEvents.SHARE_AOI_DISABLE);
//
//		Dispatcher.forwardEvent(DGWATCHEvents.AOI_SELECTED, this);
//	}
}
