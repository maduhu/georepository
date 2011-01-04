/*
 * $ Header: it.geosolutions.georepo.gui.server.DGWATCHKeySessionValues,v. 0.1 3-gen-2011 17.06.55 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 17.06.55 $
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
package it.geosolutions.georepo.gui.server;

// TODO: Auto-generated Javadoc
/**
 * The Enum DGWATCHKeySessionValues.
 */
public enum DGWATCHKeySessionValues {

    /** The USE r_ logge d_ token. */
    USER_LOGGED_TOKEN("userLoggedToken");

    /** The value. */
    private String value;

    /**
     * Instantiates a new dGWATCH key session values.
     * 
     * @param value
     *            the value
     */
    DGWATCHKeySessionValues(String value) {
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
