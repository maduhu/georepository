/*
 * $ Header: it.geosolutions.georepo.gui.client.model.WatchNode,v. 0.1 3-gen-2011 17.06.11 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import com.extjs.gxt.ui.client.data.BaseModel;

// TODO: Auto-generated Javadoc
/**
 * The Class WatchNode.
 */
public class WatchNode extends BaseModel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1111659793475229355L;

    /**
     * Instantiates a new watch node.
     * 
     * @param name
     *            the name
     * @param id
     *            the id
     */
    public WatchNode(String name, Long id) {
        set("name", name);
        set("id", id);
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return (String) get("name");
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public Long getId() {
        return get("id");
    }

}
