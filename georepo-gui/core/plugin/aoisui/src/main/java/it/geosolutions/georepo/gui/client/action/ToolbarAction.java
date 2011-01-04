/*
 * $ Header: it.geosolutions.georepo.gui.client.action.ToolbarAction,v. 0.1 3-gen-2011 16.16.36 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;

// TODO: Auto-generated Javadoc
/**
 * The Class ToolbarAction.
 */
public abstract class ToolbarAction implements Listener<BaseEvent> {

    /** The category. */
    private Category category;

    /** The id. */
    private String id;

    /** The enabled. */
    private boolean enabled;

    /**
     * Instantiates a new toolbar action.
     * 
     * @param category
     *            the category
     */
    public ToolbarAction(Category category) {
        this.category = category;
    }

    /**
     * Gets the category.
     * 
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the category.
     * 
     * @param category
     *            the new category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id
     *            the new id
     */
    public void setId(String id) {
        this.id = id;
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
        this.enabled = enabled;
    }

}
