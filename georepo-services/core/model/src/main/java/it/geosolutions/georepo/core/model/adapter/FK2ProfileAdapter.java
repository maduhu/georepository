/*
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

package it.geosolutions.georepo.core.model.adapter;

import it.geosolutions.georepo.core.model.adapter.dual.IdNameBundle;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.vividsolutions.jts.io.ParseException;
import it.geosolutions.georepo.core.model.Profile;

/**
 * Transform a Profile into its id.
 *
 */
public class FK2ProfileAdapter extends XmlAdapter<IdNameBundle, Profile> {

    @Override
    public Profile unmarshal(IdNameBundle in) throws ParseException {

            Profile ret = new Profile();
            ret.setId(in.getId());
            ret.setName(in.getName());
            return ret;
    }

    @Override
    public IdNameBundle marshal(Profile u) throws ParseException {
        IdNameBundle in = new IdNameBundle();
        if (u != null) {
            in.setId(u.getId());
            in.setName(u.getName());
        }
        return in;
    }
}
