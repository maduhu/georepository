/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.StatusWidget,v. 0.1 14-gen-2011 19.29.22 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 14-gen-2011 19.29.22 $
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
package it.geosolutions.georepo.gui.client.widget;

import com.extjs.gxt.ui.client.widget.Status;

// TODO: Auto-generated Javadoc
/**
 * The Class StatusWidget.
 */
public class StatusWidget extends Status {

    /**
     * Instantiates a new status widget.
     */
    public StatusWidget() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.widget.Status#setBusy(java.lang.String)
     */
    @Override
    public void setBusy(String text) {
        setIconStyle("x-loading-status");
        setText(text);
    }

}
