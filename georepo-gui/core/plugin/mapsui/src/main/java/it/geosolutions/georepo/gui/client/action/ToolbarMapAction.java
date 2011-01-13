/*
 * $ Header: it.geosolutions.georepo.gui.client.action.ToolbarMapAction,v. 0.1 3-gen-2011 16.16.36 created by afabiani <alessio.fabiani at geo-solutions.it> $
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
 * The Class ToolbarMapAction.
 */
public abstract class ToolbarMapAction extends ToolbarAction {

    /** The tooltip. */
    private String tooltip;

    /**
     * Instantiates a new toolbar map action.
     * 
     * @param tooltip
     *            the tooltip
     * @param category
     *            the category
     */
    public ToolbarMapAction(String tooltip, Category category) {
        super(category);
        this.tooltip = tooltip;
    }

    /**
     * Gets the tooltip.
     * 
     * @return the tooltip
     */
    public String getTooltip() {
        return tooltip;
    }

    /**
     * Sets the tooltip.
     * 
     * @param tooltip
     *            the new tooltip
     */
    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }
}