/*
 * $Header: it.geosolutions.georepo.gui.client.ApplicationException,v. 0.1 16/lug/2010 16.01.39 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 16/lug/2010 16.01.39 $
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
package it.geosolutions.georepo.gui.client;

import java.io.Serializable;

/**
 * @author giuseppe
 * 
 */
public class ApplicationException extends RuntimeException implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5240255747375099784L;

	private String message;

	public ApplicationException() {
	}

	public ApplicationException(String message) {
		this.message = message;
	}

    public ApplicationException(Throwable e) {
        super(e);
    }

    public ApplicationException(String message, Throwable e) {
        super(e);
        this.message = message;
    }

	@Override
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    public String getDetailedMessage() {
        return super.getMessage();
    }
}
