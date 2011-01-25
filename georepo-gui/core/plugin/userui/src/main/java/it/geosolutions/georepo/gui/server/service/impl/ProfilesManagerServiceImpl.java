/*
 * $ Header: it.geosolutions.georepo.gui.server.service.impl.ProfilesManagerServiceImpl,v. 0.1 25-gen-2011 11.23.49 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.23.49 $
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
package it.geosolutions.georepo.gui.server.service.impl;

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.Profile;
import it.geosolutions.georepo.gui.server.service.IProfilesManagerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

// TODO: Auto-generated Javadoc
/**
 * The Class ProfilesManagerServiceImpl.
 */
@Component("profilesManagerServiceGWT")
public class ProfilesManagerServiceImpl implements IProfilesManagerService {

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // @Autowired
    // private DGWATCHRemoteService dgwatchRemoteService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.digitalglobe.dgwatch.gui.server.service.IFeatureService#loadFeature(com.extjs.gxt.ui.
     * client.data.PagingLoadConfig, java.lang.String)
     */
    public PagingLoadResult<Profile> getProfiles(PagingLoadConfig config)
            throws ApplicationException {
        List<Profile> profileListDTO = new ArrayList<Profile>();

        Profile profile_base = new Profile();
        Profile profile_analysis = new Profile();
        Profile profile_advanced = new Profile();

        profile_base.setName("BASE");
        profile_base.setDateCreation(new Date());
        profile_base.setEnabled(true);

        profile_analysis.setName("ANALYSIS");
        profile_analysis.setDateCreation(new Date());
        profile_analysis.setEnabled(true);

        profile_advanced.setName("ADVANCED");
        profile_advanced.setDateCreation(new Date());
        profile_advanced.setEnabled(true);

        profileListDTO.add(profile_base);
        profileListDTO.add(profile_analysis);
        profileListDTO.add(profile_advanced);

        return new BasePagingLoadResult<Profile>(profileListDTO, 0, profileListDTO.size());
    }
}
