/*
 * $ Header: it.geosolutions.georepo.gui.server.gwt.ProfilesManagerServiceImpl,v. 0.1 10-feb-2011 17.06.19 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 10-feb-2011 17.06.19 $
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
package it.geosolutions.georepo.gui.server.gwt;

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.Profile;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemote;
import it.geosolutions.georepo.gui.server.service.IProfilesManagerService;
import it.geosolutions.georepo.gui.spring.ApplicationContextUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// TODO: Auto-generated Javadoc
/**
 * The Class ProfilesManagerServiceImpl.
 */
public class ProfilesManagerServiceImpl extends RemoteServiceServlet implements
        ProfilesManagerServiceRemote {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1466494799053878981L;

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** The profiles manager service. */
    private IProfilesManagerService profilesManagerService;

    /**
     * Instantiates a new profiles manager service impl.
     */
    public ProfilesManagerServiceImpl() {
        this.profilesManagerService = (IProfilesManagerService) ApplicationContextUtil.getInstance().getBean(
                "profilesManagerServiceGWT");
    }

    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemote#getProfiles(com.extjs.gxt.ui.client.data.PagingLoadConfig)
     */
    public PagingLoadResult<Profile> getProfiles(PagingLoadConfig config, boolean full) throws ApplicationException {
        return profilesManagerService.getProfiles(config, full);
    }

    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemote#deleteProfile(it.geosolutions.georepo.gui.client.model.Profile)
     */
    public void deleteProfile(Profile profile) throws ApplicationException {
        profilesManagerService.deleteProfile(profile);
    }

    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemote#saveProfile(it.geosolutions.georepo.gui.client.model.Profile)
     */
    public void saveProfile(Profile profile) throws ApplicationException {
        profilesManagerService.saveProfile(profile);
    }
    
}