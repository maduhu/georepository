/*
 * $ Header: it.geosolutions.georepo.gui.client.model.Watch,v. 0.1 3-gen-2011 17.06.12 created by afabiani <alessio.fabiani at geo-solutions.it> $
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
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.data.BeanModel;

// TODO: Auto-generated Javadoc
/**
 * The Class Watch.
 */
public class Watch extends BeanModel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3750043319345149007L;

    // ////////////////////////////
    // ALL
    // ////////////////////////////

    /** The id. */
    private long id;

    /** The title. */
    private String title;

    /** The begin date. */
    private Date beginDate;

    /** The expiration date. */
    private Date expirationDate;

    /** The action id. */
    private long actionId;

    /** The notification. */
    private boolean notification;

    /** The aoi id. */
    private long aoiId;

    /** The member. */
    private Member member;

    /** The filter. */
    private Filter filter;

    // ///////////////////////////////
    // Notification fields
    // ///////////////////////////////

    /** The send type. */
    private String sendType;

    /** The up inteval. */
    private String upInteval;

    /** The retrieval type. */
    private String retrievalType;

    /** The content type. */
    private String contentType;

    /** The watch mail. */
    private List<String> watchMail;

    /** The mail content. */
    private String mailContent;

    // ///////////////////////////////
    // Distribution fields
    // ///////////////////////////////

    /** The dist content type. */
    private String distContentType;

    /** The dist up interval. */
    private String distUpInterval;

    /** The node map. */
    private Map<Long, String> nodeMap;

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
        // set(WatchKeyValue.WATCH_ID.getValue(), this.id);
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
        // set(WatchKeyValue.WATCH_TITLE.getValue(), this.title);
    }

    /**
     * Gets the begin date.
     * 
     * @return the begin date
     */
    public Date getBeginDate() {
        return beginDate;
    }

    /**
     * Sets the begin date.
     * 
     * @param beginDate
     *            the new begin date
     */
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
        // set(WatchKeyValue.WATCH_BEGIN_DATE.getValue(), this.beginDate);
    }

    /**
     * Gets the expiration date.
     * 
     * @return the expiration date
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the expiration date.
     * 
     * @param expirationDate
     *            the new expiration date
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
        // set(WatchKeyValue.WATCH_EXPIRATION.getValue(), this.expirationDate);
    }

    /**
     * Gets the send type.
     * 
     * @return the send type
     */
    public String getSendType() {
        return sendType;
    }

    /**
     * Sets the send type.
     * 
     * @param sendType
     *            the new send type
     */
    public void setSendType(String sendType) {
        this.sendType = sendType;
        // set(SendTypeEnum.TYPE.getValue(), this.sendType);
    }

    /**
     * Gets the up inteval.
     * 
     * @return the up inteval
     */
    public String getUpInteval() {
        return upInteval;
    }

    /**
     * Sets the up inteval.
     * 
     * @param upInteval
     *            the new up inteval
     */
    public void setUpInteval(String upInteval) {
        this.upInteval = upInteval;
        // set(UpdateIntervalEnum.TIME.getValue(), this.upInteval);
    }

    /**
     * Gets the retrieval type.
     * 
     * @return the retrieval type
     */
    public String getRetrievalType() {
        return retrievalType;
    }

    /**
     * Sets the retrieval type.
     * 
     * @param retrievalType
     *            the new retrieval type
     */
    public void setRetrievalType(String retrievalType) {
        this.retrievalType = retrievalType;
        // set(RetrievalTypeEnum.TYPE.getValue(), this.retrievalType);
    }

    /**
     * Gets the content type.
     * 
     * @return the content type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the content type.
     * 
     * @param contentType
     *            the new content type
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
        // set(ContentTypeEnum.TYPE.getValue(), this.contentType);
    }

    /**
     * Gets the watch mail.
     * 
     * @return the watch mail
     */
    public List<String> getWatchMail() {
        return watchMail;
    }

    /**
     * Sets the watch mail.
     * 
     * @param watchMail
     *            the new watch mail
     */
    public void setWatchMail(List<String> watchMail) {
        this.watchMail = watchMail;
    }

    /**
     * Gets the mail content.
     * 
     * @return the mail content
     */
    public String getMailContent() {
        return mailContent;
    }

    /**
     * Sets the mail content.
     * 
     * @param mailContent
     *            the new mail content
     */
    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    /**
     * Gets the aoi id.
     * 
     * @return the aoi id
     */
    public long getAoiId() {
        return aoiId;
    }

    /**
     * Sets the aoi id.
     * 
     * @param aoiId
     *            the new aoi id
     */
    public void setAoiId(long aoiId) {
        this.aoiId = aoiId;
        // set(WatchKeyValue.WATCH_AOI_ID.getValue(), this.aoiId);
    }

    /**
     * Gets the member.
     * 
     * @return the member
     */
    public Member getMember() {
        return member;
    }

    /**
     * Sets the member.
     * 
     * @param member
     *            the new member
     */
    public void setMember(Member member) {
        this.member = member;
        // set(WatchKeyValue.WATCH_MEMBER.getValue(), this.member);
    }

    /**
     * Gets the filter.
     * 
     * @return the filter
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     * Sets the filter.
     * 
     * @param filter
     *            the new filter
     */
    public void setFilter(Filter filter) {
        this.filter = filter;
        // set(WatchKeyValue.WATCH_FILTER.getValue(), this.filter);
    }

    /**
     * Gets the action id.
     * 
     * @return the action id
     */
    public long getActionId() {
        return actionId;
    }

    /**
     * Sets the action id.
     * 
     * @param actionId
     *            the new action id
     */
    public void setActionId(long actionId) {
        this.actionId = actionId;
        // set(WatchKeyValue.WATCH_ACTION_ID.getValue(), this.actionId);
    }

    /**
     * Checks if is notification.
     * 
     * @return true, if is notification
     */
    public boolean isNotification() {
        return notification;
    }

    /**
     * Sets the notification.
     * 
     * @param notification
     *            the new notification
     */
    public void setNotification(boolean notification) {
        this.notification = notification;
        // set(WatchKeyValue.WATCH_NOTIFICATION.getValue(), this.notification);
    }

    /**
     * Gets the dist content type.
     * 
     * @return the dist content type
     */
    public String getDistContentType() {
        return distContentType;
    }

    /**
     * Sets the dist content type.
     * 
     * @param distContentType
     *            the new dist content type
     */
    public void setDistContentType(String distContentType) {
        this.distContentType = distContentType;
        // set(DistribContentTypeEnum.TYPE.getValue(), this.distContentType);
    }

    /**
     * Gets the dist up interval.
     * 
     * @return the dist up interval
     */
    public String getDistUpInterval() {
        return distUpInterval;
    }

    /**
     * Sets the dist up interval.
     * 
     * @param distUpInterval
     *            the new dist up interval
     */
    public void setDistUpInterval(String distUpInterval) {
        this.distUpInterval = distUpInterval;
        // set(DistribUpdateIntervalEnum.TIME.getValue(), this.distUpInterval);
    }

    /**
     * Gets the node map.
     * 
     * @return the node map
     */
    public Map<Long, String> getNodeMap() {
        return nodeMap;
    }

    /**
     * Sets the node map.
     * 
     * @param nodeMap
     *            the node map
     */
    public void setNodeMap(Map<Long, String> nodeMap) {
        this.nodeMap = nodeMap;
    }

}
