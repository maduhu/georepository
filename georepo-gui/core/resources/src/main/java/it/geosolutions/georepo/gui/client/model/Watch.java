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
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.data.BeanModel;

/**
 * @author Tobia di Pisa
 *
 */
public class Watch extends BeanModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3750043319345149007L;
	
	// ////////////////////////////
	// ALL
	// ////////////////////////////
	
	private long id;
	private String title;
	private Date beginDate;
	private Date expirationDate;
	
	private long actionId;	
	private boolean notification;
	
	private long aoiId;
	private Member member;
	private Filter filter;
	
	// ///////////////////////////////
	// Notification fields
	// ///////////////////////////////

	private String sendType;
	private String upInteval;
	private String retrievalType;
	private String contentType;
	private List<String> watchMail;
	private String mailContent;	
	
	// ///////////////////////////////
	// Distribution fields
	// ///////////////////////////////

	private String distContentType;
	private String distUpInterval;
	private Map<Long, String> nodeMap;
	
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
//		set(WatchKeyValue.WATCH_ID.getValue(), this.id);
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
//		set(WatchKeyValue.WATCH_TITLE.getValue(), this.title);
	}
	
	/**
	 * @return the beginDate
	 */
	public Date getBeginDate() {
		return beginDate;
	}
	
	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
//		set(WatchKeyValue.WATCH_BEGIN_DATE.getValue(), this.beginDate);
	}
	
	/**
	 * @return the expirationDate
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}
	
	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
//		set(WatchKeyValue.WATCH_EXPIRATION.getValue(), this.expirationDate);
	}
	
	/**
	 * @return the sendType
	 */
	public String getSendType() {
		return sendType;
	}

	/**
	 * @param sendType
	 *            the sendType to set
	 */
	public void setSendType(String sendType) {
		this.sendType = sendType;
//		set(SendTypeEnum.TYPE.getValue(), this.sendType);
	}

	/**
	 * @return the upInteval
	 */
	public String getUpInteval() {
		return upInteval;
	}

	/**
	 * @param upInteval the upInteval to set
	 */
	public void setUpInteval(String upInteval) {
		this.upInteval = upInteval;
//		set(UpdateIntervalEnum.TIME.getValue(), this.upInteval);
	}
	
	/**
	 * @return the retrievalType
	 */
	public String getRetrievalType() {
		return retrievalType;
	}

	/**
	 * @param retrievalType the retrievalType to set
	 */
	public void setRetrievalType(String retrievalType) {
		this.retrievalType = retrievalType;
//		set(RetrievalTypeEnum.TYPE.getValue(), this.retrievalType);
	}
	
	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
//		set(ContentTypeEnum.TYPE.getValue(), this.contentType);
	}
	
	/**
	 * @return the watchMail
	 */
	public List<String> getWatchMail() {
		return watchMail;
	}

	/**
	 * @param watchMail the watchMail to set
	 */
	public void setWatchMail(List<String> watchMail) {
		this.watchMail = watchMail;
	}
	
	/**
	 * @return the mailContent
	 */
	public String getMailContent() {
		return mailContent;
	}

	/**
	 * @param mailContent the mailContent to set
	 */
	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}
	
	/**
	 * @return the aoiId
	 */
	public long getAoiId() {
		return aoiId;
	}

	/**
	 * @param aoiId the aoiId to set
	 */
	public void setAoiId(long aoiId) {
		this.aoiId = aoiId;
//		set(WatchKeyValue.WATCH_AOI_ID.getValue(), this.aoiId);
	}

	/**
	 * @return the member
	 */
	public Member getMember() {
		return member;
	}

	/**
	 * @param member the member to set
	 */
	public void setMember(Member member) {
		this.member = member;
//		set(WatchKeyValue.WATCH_MEMBER.getValue(), this.member);
	}

	/**
	 * @return the filter
	 */
	public Filter getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(Filter filter) {
		this.filter = filter;
//		set(WatchKeyValue.WATCH_FILTER.getValue(), this.filter);
	}
	
	/**
	 * @return the actionId
	 */
	public long getActionId() {
		return actionId;
	}

	/**
	 * @param actionId the actionId to set
	 */
	public void setActionId(long actionId) {
		this.actionId = actionId;
//		set(WatchKeyValue.WATCH_ACTION_ID.getValue(), this.actionId);
	}
	
	/**
	 * @return the notification
	 */
	public boolean isNotification() {
		return notification;
	}

	/**
	 * @param notification the notification to set
	 */
	public void setNotification(boolean notification) {
		this.notification = notification;
//		set(WatchKeyValue.WATCH_NOTIFICATION.getValue(), this.notification);
	}

	/**
	 * @return the distcontentType
	 */
	public String getDistContentType() {
		return distContentType;
	}

	/**
	 * @param distcontentType the distcontentType to set
	 */
	public void setDistContentType(String distContentType) {
		this.distContentType = distContentType;
//		set(DistribContentTypeEnum.TYPE.getValue(), this.distContentType);
	}

	/**
	 * @return the distUpInterval
	 */
	public String getDistUpInterval() {
		return distUpInterval;
	}

	/**
	 * @param distUpInterval the distUpInterval to set
	 */
	public void setDistUpInterval(String distUpInterval) {
		this.distUpInterval = distUpInterval;
//		set(DistribUpdateIntervalEnum.TIME.getValue(), this.distUpInterval);
	}

	/**
	 * @return the nodeList
	 */
	public Map<Long, String> getNodeMap() {
		return nodeMap;
	}

	/**
	 * @param nodeList the nodeList to set
	 */
	public void setNodeMap(Map<Long, String> nodeMap) {
		this.nodeMap = nodeMap;
	}
	
}
