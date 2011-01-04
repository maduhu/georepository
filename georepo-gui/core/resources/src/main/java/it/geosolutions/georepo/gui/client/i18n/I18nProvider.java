/*
 * $ Header: it.geosolutions.georepo.gui.client.i18n.I18nProvider,v. 0.1 3-gen-2011 17.06.11 created by afabiani <alessio.fabiani at geo-solutions.it> $
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
package it.geosolutions.georepo.gui.client.i18n;

import com.google.gwt.core.client.GWT;

// TODO: Auto-generated Javadoc
/**
 * The Class I18nProvider.
 */
public final class I18nProvider {

    /** The Constant MESSAGES. */
    private static final ApplicationMessages MESSAGES = GWT.create(ApplicationMessages.class);

    /**
     * Gets the messages.
     * 
     * @return the messages
     */
    public static ApplicationMessages getMessages() {
        return MESSAGES;
    }

}
