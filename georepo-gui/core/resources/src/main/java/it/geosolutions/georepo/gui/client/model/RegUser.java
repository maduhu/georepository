/*
 * $ Header: it.geosolutions.georepo.gui.client.model.RegUser,v. 0.1 3-gen-2011 17.06.11 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import com.extjs.gxt.ui.client.data.BeanModel;

// TODO: Auto-generated Javadoc
/**
 * The Class RegUser.
 */
public class RegUser extends BeanModel implements Comparable<RegUser> {

    /**
     * The Enum RegUserKeyValue.
     */
    public enum RegUserKeyValue {

        /** The CONNECT e_ id. */
        CONNECTE_ID("connectId"),
        /** The USE r_ name. */
        USER_NAME("userName"),
        /** The FIRS t_ name. */
        FIRST_NAME("firstName"),
        /** The LAS t_ name. */
        LAST_NAME("lastName"),
        /** The MAI l_ address. */
        MAIL_ADDRESS("mailAddress"),
        /** The ORGANIZATI o_ name. */
        ORGANIZATIO_NAME("organizationName"),
        /** The CHOISE. */
        CHOISE("choise");

        /** The value. */
        private String value;

        /**
         * Instantiates a new reg user key value.
         * 
         * @param value
         *            the value
         */
        RegUserKeyValue(String value) {
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
    private static final long serialVersionUID = 838276364210417929L;

    /** The connect id. */
    private String connectId;

    /** The user name. */
    private String userName;

    /** The first name. */
    private String firstName;

    /** The last name. */
    private String lastName;

    /** The mail address. */
    private String mailAddress;

    /** The organization name. */
    private String organizationName;

    /** The choise. */
    private String choise;

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
        set(RegUserKeyValue.CONNECTE_ID.getValue(), this.connectId);
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
        this.userName = userName;
        set(RegUserKeyValue.USER_NAME.getValue(), this.userName);
    }

    /**
     * Gets the first name.
     * 
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     * 
     * @param firstName
     *            the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        set(RegUserKeyValue.FIRST_NAME.getValue(), this.firstName);
    }

    /**
     * Gets the last name.
     * 
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     * 
     * @param lastName
     *            the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
        set(RegUserKeyValue.LAST_NAME.getValue(), this.lastName);
    }

    /**
     * Gets the mail address.
     * 
     * @return the mail address
     */
    public String getMailAddress() {
        return mailAddress;
    }

    /**
     * Sets the mail address.
     * 
     * @param mailAddress
     *            the new mail address
     */
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
        set(RegUserKeyValue.MAIL_ADDRESS.getValue(), this.mailAddress);
    }

    /**
     * Gets the organization name.
     * 
     * @return the organization name
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * Sets the organization name.
     * 
     * @param organizationName
     *            the new organization name
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
        set(RegUserKeyValue.ORGANIZATIO_NAME.getValue(), this.organizationName);
    }

    /**
     * Gets the choise.
     * 
     * @return the choise
     */
    public String getChoise() {
        return choise;
    }

    /**
     * Sets the choise.
     * 
     * @param choise
     *            the new choise
     */
    public void setChoise(String choise) {
        this.choise = choise;
        set(RegUserKeyValue.CHOISE.getValue(), this.choise);
    }

    /**
     * Check choise.
     * 
     * @param userName
     *            the user name
     * @param organizationName
     *            the organization name
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
        return "RegUser [connectId=" + connectId + ", userName=" + userName + ", firstName="
                + firstName + ", lastName=" + lastName + ", mailAddress=" + mailAddress
                + ", organizationName=" + organizationName + "]";
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof RegUser && compareTo((RegUser) obj) == 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(RegUser o) {
        // TODO Auto-generated method stub
        return this.choise.compareTo(o.getChoise());
    }

}
