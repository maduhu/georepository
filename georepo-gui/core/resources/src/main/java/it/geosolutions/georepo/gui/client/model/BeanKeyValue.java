/*
 * $ Header: it.geosolutions.georepo.gui.client.model.BeanKeyValue,v. 0.1 14-gen-2011 19.29.23 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 14-gen-2011 19.29.23 $
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

    /** The USE r_ name. */
    USER_NAME("userName"), 
 /** The EMAIL. */
    EMAIL("emailAddress"), 
 /** The EMAI l_ enable. */
    EMAIL_ENABLE("emailEnable"), 
 /** The RS s_ enable. */
    RSS_ENABLE("rssEnable"),
    
    /** The REDUCE d_ content. */
    REDUCED_CONTENT("reducedContent"),
    
    /** The USE r_ enabled. */
    USER_ENABLED("userEnabled"),
    
    /** The NAME. */
    NAME("name"),
    
    /** The PATH. */
    PATH("path"),
    
    /** The REDUCE d_ conten t_ update. */
    REDUCED_CONTENT_UPDATE("REDUCED_CONTENT_UPDATE");

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
