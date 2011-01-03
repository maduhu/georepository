/*
 * $Header: it.geosolutions.georepo.gui.client.User,v. 0.1 08/lug/2010 11.17.31 created by frank $
 * $Revision: 0.1 $
 * $Date: 08/lug/2010 11.17.31 $
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


import java.util.ArrayList;
import java.util.List;

import it.geosolutions.georepo.gui.client.SendType.SendTypeEnum;
import it.geosolutions.georepo.gui.client.UpdateInterval.UpdateIntervalEnum;
import com.extjs.gxt.ui.client.data.BeanModel;

/**
 * @author frank
 * 
 */
public class User extends BeanModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5117714882113396553L;

	private long id;
	private boolean accountState;
	private String name;
	private String userName;
	private String connectId;
	private String password;
	private String emailAddress;
	private boolean enabled;
	private boolean emailEnable;
	private boolean rssEnable;
	private boolean reducedContent;
	private String sendType;
	private int updateInterval;
	private String upInteval;
	private String path;
	private List<AOI> areasOI = new ArrayList<AOI>();
    private List<Authorization> grantedAuthorizations;

	public User() {
		setPath("dgwatch/resources/images/userChoose.jpg");
	}

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
	}

	/**
	 * @return the accountState
	 */
	public boolean isAccountState() {
		return accountState;
	}

	/**
	 * @param accountState
	 *            the accountState to set
	 */
	public void setAccountState(boolean accountState) {
		this.accountState = accountState;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
		set(BeanKeyValue.NAME.getValue(), this.name);
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		set(BeanKeyValue.USER_NAME.getValue(), userName);
		this.userName = userName;
	}

	/**
	 * @return the connectId
	 */
	public String getConnectId() {
		return connectId;
	}

	/**
	 * @param connectId the connectId to set
	 */
	public void setConnectId(String connectId) {
		this.connectId = connectId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *            the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		set(BeanKeyValue.EMAIL.getValue(), emailAddress);
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		set(BeanKeyValue.USER_ENABLED.getValue(), enabled);
		this.enabled = enabled;
	}

	/**
	 * @return the emailEnable
	 */
	public boolean isEmailEnable() {
		return emailEnable;
	}

	/**
	 * @param emailEnable
	 *            the emailEnable to set
	 */
	public void setEmailEnable(boolean emailEnable) {
		set(BeanKeyValue.EMAIL_ENABLE.getValue(), emailEnable);
		this.emailEnable = emailEnable;
	}

	/**
	 * @return the rssEnable
	 */
	public boolean isRssEnable() {
		return rssEnable;
	}

	/**
	 * @param rssEnable
	 *            the rssEnable to set
	 */
	public void setRssEnable(boolean rssEnable) {
		set(BeanKeyValue.RSS_ENABLE.getValue(), rssEnable);
		this.rssEnable = rssEnable;
	}

	/**
	 * @return the reducedContent
	 */
	public boolean isReducedContent() {
		return reducedContent;
	}

	/**
	 * @param reducedContent
	 */
	public void setReducedContent(boolean reducedContent) {
		set(BeanKeyValue.REDUCED_CONTENT.getValue(), reducedContent);
		this.reducedContent = reducedContent;
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
		set(SendTypeEnum.TYPE.getValue(), this.sendType);
		checkSendType();
	}

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
	 * 
	 * @param emailEnable
	 * @param rssEnable
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
	 * @return the updateInterval
	 */
	public int getUpdateInterval() {
		return updateInterval;
	}

	/**
	 * @param updateInterval
	 *            the updateInterval to set
	 */
	public void setUpdateInterval(int updateInterval) {
		this.updateInterval = updateInterval;
	}

	/**
	 * @return the upInteval
	 */
	public String getUpInteval() {
		return upInteval;
	}

	/**
	 * @param upInteval
	 *            the upInteval to set
	 */
	public void setUpInteval(String upInteval) {
		this.upInteval = upInteval;
		set(UpdateIntervalEnum.TIME.getValue(), this.upInteval);
		checkUpInterval();
	}

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
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
		set(BeanKeyValue.PATH.getValue(), this.path);
	}

	/**
	 * @return the areasOI
	 */
	public List<AOI> getAreasOI() {
		return areasOI;
	}

	/**
	 * 
	 * @param aoi
	 *            to add to AreasOI
	 */
	public void addAreaOI(AOI aoi) {
		this.areasOI.add(aoi);
	}

	/**
	 * 
	 * @param aoi
	 *            to remove from AreasOI
	 */
	public void removeAreaOI(AOI aoi) {
		this.areasOI.remove(aoi);
	}

	/**
	 * 
	 * @param i
	 */
	public void removeAreaOI(int i) {
		this.areasOI.remove(i);
	}

	/**
	 * 
	 * @param i
	 * @return AOI
	 */
	public AOI getAreaOI(int i) {
		if (i < 0 || i > this.areasOI.size())
			throw new IllegalArgumentException("Invalid Position.");
		return this.areasOI.get(i);
	}

	/**
	 * 
	 * @return the number of All Areas Of Interest
	 */
	public int getAllAreasOI() {
		return this.areasOI.size();
	}

	/**
	 * @param areasOI
	 *            the areasOI to set
	 */
	public void setAreasOI(List<AOI> areasOI) {
		this.areasOI = areasOI;
	}

    public void setGrantedAuthorizations(List<Authorization> ga) {
         this.grantedAuthorizations = ga;
    }

    public List<Authorization> getGrantedAuthorizations() {
         return this.grantedAuthorizations;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/* (non-Javadoc)
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
		return "User [name=" + name + ", userName=" + userName + ", password="
				+ password + ", emailAddress=" + emailAddress + ", enabled="
				+ enabled + ", emailEnable=" + emailEnable + ", rssEnable="
				+ rssEnable + ", reducedContent=" + reducedContent
				+ ", sendType=" + sendType + ", updateInterval="
				+ updateInterval + ", upInteval=" + upInteval + "]";
	}

}
