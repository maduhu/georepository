/*
 * $ Header: it.geosolutions.georepo.gui.client.model.User,v. 0.1 3-gen-2011 17.06.11 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.georepo.gui.client.SendType.SendTypeEnum;
import it.geosolutions.georepo.gui.client.UpdateInterval.UpdateIntervalEnum;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BeanModel;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
public class User extends BeanModel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5117714882113396553L;

    /** The id. */
    private long id;

    /** The account state. */
    private boolean accountState;

    /** The name. */
    private String name;

    /** The user name. */
    private String userName;

    /** The connect id. */
    private String connectId;

    /** The password. */
    private String password;

    /** The email address. */
    private String emailAddress;

    /** The enabled. */
    private boolean enabled;

    /** The email enable. */
    private boolean emailEnable;

    /** The rss enable. */
    private boolean rssEnable;

    /** The reduced content. */
    private boolean reducedContent;

    /** The send type. */
    private String sendType;

    /** The update interval. */
    private int updateInterval;

    /** The up inteval. */
    private String upInteval;

    /** The path. */
    private String path;

    /** The areas oi. */
    private List<AOI> areasOI = new ArrayList<AOI>();

    /** The granted authorizations. */
    private List<Authorization> grantedAuthorizations;

    /**
     * Instantiates a new user.
     */
    public User() {
        setPath("georepo/resources/images/userChoose.jpg");
    }

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
    }

    /**
     * Checks if is account state.
     * 
     * @return true, if is account state
     */
    public boolean isAccountState() {
        return accountState;
    }

    /**
     * Sets the account state.
     * 
     * @param accountState
     *            the new account state
     */
    public void setAccountState(boolean accountState) {
        this.accountState = accountState;
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
        set(BeanKeyValue.NAME.getValue(), this.name);
    }

    /**
     * Gets the user name.
     * 
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name.
     * 
     * @param userName
     *            the new user name
     */
    public void setUserName(String userName) {
        set(BeanKeyValue.USER_NAME.getValue(), userName);
        this.userName = userName;
    }

    /**
     * Gets the connect id.
     * 
     * @return the connect id
     */
    public String getConnectId() {
        return connectId;
    }

    /**
     * Sets the connect id.
     * 
     * @param connectId
     *            the new connect id
     */
    public void setConnectId(String connectId) {
        this.connectId = connectId;
    }

    /**
     * Gets the password.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * 
     * @param password
     *            the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the email address.
     * 
     * @return the email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the email address.
     * 
     * @param emailAddress
     *            the new email address
     */
    public void setEmailAddress(String emailAddress) {
        set(BeanKeyValue.EMAIL.getValue(), emailAddress);
        this.emailAddress = emailAddress;
    }

    /**
     * Checks if is enabled.
     * 
     * @return true, if is enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled.
     * 
     * @param enabled
     *            the new enabled
     */
    public void setEnabled(boolean enabled) {
        set(BeanKeyValue.USER_ENABLED.getValue(), enabled);
        this.enabled = enabled;
    }

    /**
     * Checks if is email enable.
     * 
     * @return true, if is email enable
     */
    public boolean isEmailEnable() {
        return emailEnable;
    }

    /**
     * Sets the email enable.
     * 
     * @param emailEnable
     *            the new email enable
     */
    public void setEmailEnable(boolean emailEnable) {
        set(BeanKeyValue.EMAIL_ENABLE.getValue(), emailEnable);
        this.emailEnable = emailEnable;
    }

    /**
     * Checks if is rss enable.
     * 
     * @return true, if is rss enable
     */
    public boolean isRssEnable() {
        return rssEnable;
    }

    /**
     * Sets the rss enable.
     * 
     * @param rssEnable
     *            the new rss enable
     */
    public void setRssEnable(boolean rssEnable) {
        set(BeanKeyValue.RSS_ENABLE.getValue(), rssEnable);
        this.rssEnable = rssEnable;
    }

    /**
     * Checks if is reduced content.
     * 
     * @return true, if is reduced content
     */
    public boolean isReducedContent() {
        return reducedContent;
    }

    /**
     * Sets the reduced content.
     * 
     * @param reducedContent
     *            the new reduced content
     */
    public void setReducedContent(boolean reducedContent) {
        set(BeanKeyValue.REDUCED_CONTENT.getValue(), reducedContent);
        this.reducedContent = reducedContent;
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
        set(SendTypeEnum.TYPE.getValue(), this.sendType);
        checkSendType();
    }

    /**
     * Check send type.
     */
    private void checkSendType() {
        if (sendType.equals("Both")) {
            setEmailEnable(true);
            setRssEnable(true);
        }

        if (sendType.equals("Email")) {
            setEmailEnable(true);
            setRssEnable(false);
        }

        if (sendType.equals("RSS")) {
            setRssEnable(true);
            setEmailEnable(false);
        }

    }

    /**
     * Check send type.
     * 
     * @param emailEnable
     *            the email enable
     * @param rssEnable
     *            the rss enable
     */
    public void checkSendType(boolean emailEnable, boolean rssEnable) {
        if (emailEnable && rssEnable) {
            setEmailEnable(true);
            setRssEnable(true);
            setSendType("Both");
        } else if (emailEnable) {
            setEmailEnable(true);
            setSendType("Email");
        } else if (rssEnable) {
            setRssEnable(true);
            setSendType("RSS");
        }
    }

    /**
     * Gets the update interval.
     * 
     * @return the update interval
     */
    public int getUpdateInterval() {
        return updateInterval;
    }

    /**
     * Sets the update interval.
     * 
     * @param updateInterval
     *            the new update interval
     */
    public void setUpdateInterval(int updateInterval) {
        this.updateInterval = updateInterval;
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
        set(UpdateIntervalEnum.TIME.getValue(), this.upInteval);
        checkUpInterval();
    }

    /**
     * Check up interval.
     */
    private void checkUpInterval() {
        if (upInteval.equals("1h")) {
            setUpdateInterval(1);
        }

        if (upInteval.equals("4h")) {
            setUpdateInterval(4);
        }

        if (upInteval.equals("24h")) {
            setUpdateInterval(24);
        }

    }

    /**
     * Gets the path.
     * 
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the path.
     * 
     * @param path
     *            the new path
     */
    public void setPath(String path) {
        this.path = path;
        set(BeanKeyValue.PATH.getValue(), this.path);
    }

    /**
     * Gets the areas oi.
     * 
     * @return the areas oi
     */
    public List<AOI> getAreasOI() {
        return areasOI;
    }

    /**
     * Adds the area oi.
     * 
     * @param aoi
     *            the aoi
     */
    public void addAreaOI(AOI aoi) {
        this.areasOI.add(aoi);
    }

    /**
     * Removes the area oi.
     * 
     * @param aoi
     *            the aoi
     */
    public void removeAreaOI(AOI aoi) {
        this.areasOI.remove(aoi);
    }

    /**
     * Removes the area oi.
     * 
     * @param i
     *            the i
     */
    public void removeAreaOI(int i) {
        this.areasOI.remove(i);
    }

    /**
     * Gets the area oi.
     * 
     * @param i
     *            the i
     * @return the area oi
     */
    public AOI getAreaOI(int i) {
        if (i < 0 || i > this.areasOI.size())
            throw new IllegalArgumentException("Invalid Position.");
        return this.areasOI.get(i);
    }

    /**
     * Gets the all areas oi.
     * 
     * @return the all areas oi
     */
    public int getAllAreasOI() {
        return this.areasOI.size();
    }

    /**
     * Sets the areas oi.
     * 
     * @param areasOI
     *            the new areas oi
     */
    public void setAreasOI(List<AOI> areasOI) {
        this.areasOI = areasOI;
    }

    /**
     * Sets the granted authorizations.
     * 
     * @param ga
     *            the new granted authorizations
     */
    public void setGrantedAuthorizations(List<Authorization> ga) {
        this.grantedAuthorizations = ga;
    }

    /**
     * Gets the granted authorizations.
     * 
     * @return the granted authorizations
     */
    public List<Authorization> getGrantedAuthorizations() {
        return this.grantedAuthorizations;
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
        User other = (User) obj;
        return id == other.id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "User [name=" + name + ", userName=" + userName + ", password=" + password
                + ", emailAddress=" + emailAddress + ", enabled=" + enabled + ", emailEnable="
                + emailEnable + ", rssEnable=" + rssEnable + ", reducedContent=" + reducedContent
                + ", sendType=" + sendType + ", updateInterval=" + updateInterval + ", upInteval="
                + upInteval + "]";
    }

}
