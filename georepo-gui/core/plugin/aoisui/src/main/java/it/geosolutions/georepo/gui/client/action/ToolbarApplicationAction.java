/*
 * $ Header: it.geosolutions.georepo.gui.client.action.ToolbarApplicationAction,v. 0.1 3-gen-2011 16.16.36 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 16.16.36 $
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
package it.geosolutions.georepo.gui.client.action;

import it.geosolutions.georepo.gui.client.Category;

// TODO: Auto-generated Javadoc
/**
 * The Class ToolbarApplicationAction.
 */
public abstract class ToolbarApplicationAction extends ToolbarAction {

    /** The button name. */
    private String buttonName;

    /**
     * Instantiates a new toolbar application action.
     * 
     * @param buttonName
     *            the button name
     * @param category
     *            the category
     */
    public ToolbarApplicationAction(String buttonName, Category category) {
        super(category);
        this.buttonName = buttonName;
    }

    /**
     * Gets the button name.
     * 
     * @return the button name
     */
    public String getButtonName() {
        return buttonName;
    }

    /**
     * Sets the button name.
     * 
     * @param buttonName
     *            the new button name
     */
    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

}
