/*
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
 * @author Tobia di Pisa
 *
 */
public class ExecutedClientShortWatch extends BeanModel {

	public enum ExecutedShortWatchKeyValue {
		ID("executedShortId"),
        TITLE("executedShortTitle"),
        LOG_MSG("executedShortMsg"),
		LOG_TYPE("executedShortType"),
		LOG_DATE("executedShortDate");

		private String value;

		ExecutedShortWatchKeyValue(String value) {
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
	private static final long serialVersionUID = 6410938170911933374L;
	
	private long id;
	private String title;
	private String logMsg;
	private String logType;
	private Date logDate;


	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
		set(ExecutedShortWatchKeyValue.ID.getValue(), this.id);
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
		set(ExecutedShortWatchKeyValue.TITLE.getValue(), this.title);
	}
	
	/**
	 * @return the logMsg
	 */
	public String getLogMsg() {
		return logMsg;
	}

	/**
	 * @param logMsg the logMsg to set
	 */
	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
		set(ExecutedShortWatchKeyValue.LOG_MSG.getValue(), this.logMsg);
	}

	/**
	 * @return the logType
	 */
	public String getLogType() {
		return logType;
	}

	/**
	 * @param logType the logType to set
	 */
	public void setLogType(String logType) {
		this.logType = logType;
		set(ExecutedShortWatchKeyValue.LOG_TYPE.getValue(), this.logType);
	}
	
	/**
	 * @return the logDate
	 */
	public Date getLogDate() {
		return logDate;
	}

	/**
	 * @param logDate the logDate to set
	 */
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
		set(ExecutedShortWatchKeyValue.LOG_DATE.getValue(), this.logDate);
	}
}
