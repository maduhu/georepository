/*
 * $ Header: it.geosolutions.georepo.gui.client.configuration.AOIBeanManager,v. 0.1 3-gen-2011 16.52.25 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 16.52.25 $
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
package it.geosolutions.georepo.gui.client.configuration;

import it.geosolutions.georepo.gui.client.model.AOI;
import it.geosolutions.georepo.gui.client.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

// TODO: Auto-generated Javadoc
/**
 * The Class AOIBeanManager.
 */

@Component("aoiBeanManager")
public class AOIBeanManager implements IAOIBeanManager {

    /** The areas oi. */
    private List<AOI> areasOI = new ArrayList<AOI>();

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.configuration.IAOIBeanManager#getAreasOI ()
     */
    public List<AOI> getAreasOI() {
        // TODO Auto-generated method stub
        return this.areasOI;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.configuration.IAOIBeanManager#setAreasOI
     * (java.util.List)
     */
    public void setAreasOI(List<AOI> areasOI) {
        this.areasOI = areasOI;
    }

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {

        User user = new User();
        user.setUserName("FRANCESCO");
        for (int i = 0; i < 1000; i++) {
            AOI aoi = new AOI();
            aoi.setTitle("AOI" + i);
            aoi.setId(i);
            if (i % 2 == 0) {
                aoi.setShared(true);
            } else {
                aoi.setOwner(user);
                aoi.setShared(false);
            }

            aoi.setDateCreation(new Date());
            aoi.setLastUpdate(new Date());
            aoi.setExpiration(new Date());
            // aoi.setArea(i);

            this.areasOI.add(aoi);
        }
    }

}
