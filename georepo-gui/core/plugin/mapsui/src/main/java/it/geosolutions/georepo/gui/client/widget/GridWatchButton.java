/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.GridWatchButton,v. 0.1 3-gen-2011 16.52.57 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 16.52.57 $
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
package it.geosolutions.georepo.gui.client.widget;

import it.geosolutions.georepo.gui.client.model.ClientShortWatch;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

// TODO: Auto-generated Javadoc
/**
 * The Class GridWatchButton.
 */
public class GridWatchButton extends Button {

    /**
     * Instantiates a new grid watch button.
     */
    public GridWatchButton() {
        super();
    }

    /**
     * Instantiates a new grid watch button.
     * 
     * @param text
     *            the text
     * @param icon
     *            the icon
     * @param listener
     *            the listener
     */
    public GridWatchButton(String text, AbstractImagePrototype icon,
            SelectionListener<ButtonEvent> listener) {
        super(text, icon, listener);
    }

    /**
     * Instantiates a new grid watch button.
     * 
     * @param text
     *            the text
     * @param icon
     *            the icon
     */
    public GridWatchButton(String text, AbstractImagePrototype icon) {
        super(text, icon);
    }

    /**
     * Instantiates a new grid watch button.
     * 
     * @param text
     *            the text
     * @param listener
     *            the listener
     */
    public GridWatchButton(String text, SelectionListener<ButtonEvent> listener) {
        super(text, listener);
    }

    /**
     * Instantiates a new grid watch button.
     * 
     * @param text
     *            the text
     */
    public GridWatchButton(String text) {
        super(text);
    }

    /** The client short watch. */
    private ClientShortWatch clientShortWatch;

    /**
     * Gets the client short watch.
     * 
     * @return the client short watch
     */
    public ClientShortWatch getClientShortWatch() {
        return clientShortWatch;
    }

    /**
     * Sets the client short watch.
     * 
     * @param clientShortWatch
     *            the new client short watch
     */
    public void setClientShortWatch(ClientShortWatch clientShortWatch) {
        this.clientShortWatch = clientShortWatch;
    }

}
