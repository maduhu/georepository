/*
 * $ Header: it.geosolutions.georepo.gui.client.model.BeanKeyValue,v. 0.1 25-gen-2011 16.50.11 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 16.50.11 $
 *
 * ====================================================================
 *
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 * http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
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

// TODO: Auto-generated Javadoc
/**
 * The Enum BeanKeyValue.
 */
public enum BeanKeyValue {

    /** The USER name. */
    USER_NAME("userName"),

    /** The FULL name. */
    FULL_NAME("fullName"),

    /** The USER. */
    USER("user"),

    /** The PROFILE. */
    PROFILE("profile"),
    
    /** The RULE. */
    RULE("rule"),
    
    /** The SERVICE. */
    SERVICE("service"),

    /** The REQUEST. */
    REQUEST("request"),

    /** The WORKSPACE. */
    WORKSPACE("workspace"),

    /** The LAYER. */
    LAYER("layer"),
    
    /** The GRANT. */
    GRANT("grant"),

    /** The DATE creation. */
    DATE_CREATION("dateCreation"),

    /** The EMAIL. */
    EMAIL("emailAddress"),

    /** The USER enabled. */
    USER_ENABLED("userEnabled"),

    /** The PROFILE enabled. */
    PROFILE_ENABLED("profileEnabled"),

    /** The NAME. */
    NAME("name"),

    /** The DESCRIPTION. */
    DESCRIPTION("description"),
    
    /** The PRIORITY. */
    PRIORITY("rulePriority"),
    
    /** The BASE url. */
    BASE_URL("baseUrl"),
    
    /** The INSTANCE. */
    INSTANCE("instance"),
    
    /** The PATH. */
    PATH("path"), 
    
    PROP_KEY("prop_key"),
    
    PROP_VALUE("prop_value");

    /** The value. */
    private String value;

    /**
     * Instantiates a new bean key value.
     * 
     * @param value
     *            the value
     */
    BeanKeyValue(String value) {
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
