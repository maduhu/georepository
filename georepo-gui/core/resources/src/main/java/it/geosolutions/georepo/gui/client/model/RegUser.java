/*
 * $Header: it.geosolutions.georepo.gui.client.model.RegUser,v. 0.1 09/set/2010 09.25.35 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 09/set/2010 09.25.35 $
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

import com.extjs.gxt.ui.client.data.BeanModel;

/**
 * @author giuseppe
 * 
 */
public class RegUser extends BeanModel implements Comparable<RegUser> {

	public enum RegUserKeyValue {
		CONNECTE_ID("connectId"), USER_NAME("userName"), FIRST_NAME("firstName"), LAST_NAME(
				"lastName"), MAIL_ADDRESS("mailAddress"), ORGANIZATIO_NAME(
				"organizationName"), CHOISE("choise");

		private String value;

		RegUserKeyValue(String value) {
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
	private static final long serialVersionUID = 838276364210417929L;

	private String connectId;
	private String userName;
	private String firstName;
	private String lastName;
	private String mailAddress;
	private String organizationName;

	private String choise;

	/**
	 * @return the connectId
	 */
	public String getConnectId() {
		return connectId;
	}

	/**
	 * @param connectId
	 *            the connectId to set
	 */
	public void setConnectId(String connectId) {
		this.connectId = connectId;
		set(RegUserKeyValue.CONNECTE_ID.getValue(), this.connectId);
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
		this.userName = userName;
		set(RegUserKeyValue.USER_NAME.getValue(), this.userName);
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		set(RegUserKeyValue.FIRST_NAME.getValue(), this.firstName);
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
		set(RegUserKeyValue.LAST_NAME.getValue(), this.lastName);
	}

	/**
	 * @return the mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * @param mailAddress
	 *            the mailAddress to set
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
		set(RegUserKeyValue.MAIL_ADDRESS.getValue(), this.mailAddress);
	}

	/**
	 * @return the organizationName
	 */
	public String getOrganizationName() {
		return organizationName;
	}

	/**
	 * @param organizationName
	 *            the organizationName to set
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
		set(RegUserKeyValue.ORGANIZATIO_NAME.getValue(), this.organizationName);
	}

	/**
	 * @return the choise
	 */
	public String getChoise() {
		return choise;
	}

	/**
	 * @param choise
	 *            the choise to set value for Combo Box parameter Binding
	 */
	public void setChoise(String choise) {
		this.choise = choise;
		set(RegUserKeyValue.CHOISE.getValue(), this.choise);
	}

	/**
	 * 
	 * @param userName
	 * @param organizationName
	 */
	public void checkChoise(String userName, String organizationName) {
		if (organizationName != null)
			setChoise(userName + " (" + organizationName + ")");
		else
			setChoise(userName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RegUser [connectId=" + connectId + ", userName=" + userName
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", mailAddress=" + mailAddress + ", organizationName="
				+ organizationName + "]";
	}
	
    @Override
    public boolean equals(Object obj) {
        return obj instanceof RegUser && compareTo((RegUser) obj) == 0;
    }

	public int compareTo(RegUser o) {
		// TODO Auto-generated method stub
		return this.choise.compareTo(o.getChoise());
	}

}
