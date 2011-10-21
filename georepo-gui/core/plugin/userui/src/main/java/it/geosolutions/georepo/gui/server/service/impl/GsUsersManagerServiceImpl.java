/*
 * $ Header: it.geosolutions.georepo.gui.server.service.impl.GsUsersManagerServiceImpl,v. 0.1 10-feb-2011 11.10.03 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 10-feb-2011 11.10.03 $
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
import it.geosolutions.georepo.gui.client.model.GSUser;
import it.geosolutions.georepo.gui.client.model.Profile;
import it.geosolutions.georepo.gui.client.model.data.UserLimitsInfo;
import it.geosolutions.georepo.gui.server.service.IGsUsersManagerService;
import it.geosolutions.georepo.gui.service.GeoRepoRemoteService;
import it.geosolutions.georepo.services.dto.ShortUser;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import it.geosolutions.georepo.services.exception.NotFoundServiceEx;

/**
 * The Class GsUsersManagerServiceImpl.
 */
@Component("gsUsersManagerServiceGWT")
public class GsUsersManagerServiceImpl implements IGsUsersManagerService {

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** The georepo remote service. */
    @Autowired
    private GeoRepoRemoteService georepoRemoteService;

    /*
     * (non-Javadoc)
     *
     * @see
     * it.geosolutions.georepo.gui.server.service.IFeatureService#loadFeature(com.extjs.gxt.ui.
     * client.data.PagingLoadConfig, java.lang.String)
     */
    public PagingLoadResult<GSUser> getGsUsers(PagingLoadConfig config, boolean full) throws ApplicationException {
        int start = config.getOffset();

        List<GSUser> usersListDTO = new ArrayList<GSUser>();

        if (full) {
            GSUser all_user = new GSUser();
            all_user.setId(-1);
            all_user.setName("*");
            all_user.setFullName("*");
            all_user.setEnabled(true);
            all_user.setAdmin(false);
            all_user.setEmailAddress(null);
            all_user.setDateCreation(null);
            usersListDTO.add(all_user);
        }

        long usersCount = georepoRemoteService.getUserAdminService().getCount(null) + 1;

        Long t = new Long(usersCount);

        int page = start == 0 ? start : start / config.getLimit();

        List<ShortUser> usersList = georepoRemoteService.getUserAdminService().getList(null, page,
                config.getLimit());

        if (usersList == null) {
            if (logger.isErrorEnabled())
                logger.error("No profile found on server");
            throw new ApplicationException("No profile found on server");
        }

        for (ShortUser short_usr : usersList) {
            it.geosolutions.georepo.core.model.GSUser remote_user;
            try {
                remote_user = georepoRemoteService.getUserAdminService().get(short_usr.getId());
            } catch (NotFoundServiceEx e) {
                if (logger.isErrorEnabled())
                    logger.error("Details for profile " + short_usr.getName()
                            + " not found on Server!");
                throw new ApplicationException(e);
            }
            GSUser local_user = new GSUser();
            local_user.setId(short_usr.getId());
            local_user.setName(remote_user.getName());
            local_user.setFullName(remote_user.getFullName());
            local_user.setEnabled(remote_user.getEnabled());
            local_user.setAdmin(remote_user.isAdmin());
            local_user.setEmailAddress(remote_user.getEmailAddress());
            local_user.setDateCreation(remote_user.getDateCreation());
            local_user.setPassword(remote_user.getPassword());

            it.geosolutions.georepo.core.model.Profile remote_profile = remote_user.getProfile();

            Profile local_profile = new Profile();
            local_profile.setId(remote_profile.getId());
            local_profile.setName(remote_profile.getName());
            local_profile.setDateCreation(remote_profile.getDateCreation());
            local_profile.setEnabled(remote_profile.getEnabled());
            local_user.setProfile(local_profile);

            usersListDTO.add(local_user);
        }

        return new BasePagingLoadResult<GSUser>(usersListDTO, config.getOffset(), t.intValue());
    }

    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.server.service.IGsUsersManagerService#saveUser(it.geosolutions.georepo.gui.client.model.GSUser)
     */
    public void saveUser(GSUser user) throws ApplicationException {
        it.geosolutions.georepo.core.model.GSUser remote_user = null;
        if (user.getId() >= 0) {
            try {
                remote_user = georepoRemoteService.getUserAdminService().get(user.getId());
                copyUser(user, remote_user);
                georepoRemoteService.getUserAdminService().update(remote_user);
            } catch (NotFoundServiceEx e) {
                logger.error(e.getLocalizedMessage(), e.getCause());
                throw new ApplicationException(e.getLocalizedMessage(), e.getCause());
            }
        } else {
            try {
                remote_user = new it.geosolutions.georepo.core.model.GSUser();
                copyUser(user, remote_user);
                georepoRemoteService.getUserAdminService().insert(remote_user);
            } catch (NotFoundServiceEx e) {
                logger.error(e.getLocalizedMessage(), e.getCause());
                throw new ApplicationException(e.getLocalizedMessage(), e.getCause());
            }
        }

    }

    /**
     * @param profile
     * @param remote_user
     * @throws ResourceNotFoundFault
     */
    private void copyUser(GSUser user, it.geosolutions.georepo.core.model.GSUser remote_user)
            throws NotFoundServiceEx {
        remote_user.setName(user.getName());
        remote_user.setFullName(user.getFullName());
        remote_user.setEmailAddress(user.getEmailAddress());
        remote_user.setEnabled(user.isEnabled());
        remote_user.setAdmin(user.isAdmin());
        remote_user.setPassword(user.getPassword());
        remote_user.setDateCreation(user.getDateCreation());
        if (user.getProfile() != null && user.getProfile().getId() >= 0) {
            it.geosolutions.georepo.core.model.Profile remote_profile = georepoRemoteService.getProfileAdminService().get(user.getProfile().getId());
            remote_user.setProfile(remote_profile);
        }
    }

    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.server.service.IGsUsersManagerService#deleteUser(it.geosolutions.georepo.gui.client.model.GSUser)
     */
    public void deleteUser(GSUser user) throws ApplicationException {
        it.geosolutions.georepo.core.model.GSUser remote_user = null;
        try {
            remote_user = georepoRemoteService.getUserAdminService().get(user.getId());
            georepoRemoteService.getUserAdminService().delete(remote_user.getId());
        } catch (NotFoundServiceEx e) {
            logger.error(e.getLocalizedMessage(), e.getCause());
            throw new ApplicationException(e.getLocalizedMessage(), e.getCause());
        }
    }

    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.server.service.IGsUsersManagerService#getUserLimitsInfo(it.geosolutions.georepo.gui.client.model.GSUser)
     */
    public UserLimitsInfo getUserLimitsInfo(GSUser user) throws ApplicationException {
        Long userId = user.getId();
        it.geosolutions.georepo.core.model.GSUser gsUser = null;
        UserLimitsInfo userLimitInfo = null;

        try {
            gsUser = georepoRemoteService.getUserAdminService().get(userId);

            if (gsUser != null) {
                userLimitInfo = new UserLimitsInfo();
                userLimitInfo.setUserId(userId);

                MultiPolygon the_geom = gsUser.getAllowedArea();

                if(the_geom != null){
                    userLimitInfo.setAllowedArea(the_geom.toText());
                    userLimitInfo.setSrid(String.valueOf(the_geom.getSRID()));
                }else{
                    userLimitInfo.setAllowedArea(null);
                    userLimitInfo.setSrid(null);
                }
            }
        } catch (NotFoundServiceEx e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }

        return userLimitInfo;
    }

    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.server.service.IGsUsersManagerService#saveUserLimitsInfo(it.geosolutions.georepo.gui.client.model.GSUser)
     */
    public UserLimitsInfo saveUserLimitsInfo(UserLimitsInfo userLimitInfo) throws ApplicationException {

        Long userId = userLimitInfo.getUserId();
        it.geosolutions.georepo.core.model.GSUser gsUser = null;

        try {
            gsUser = georepoRemoteService.getUserAdminService().get(userId);

            String allowedArea = userLimitInfo.getAllowedArea();

            if(allowedArea != null){
                WKTReader wktReader = new WKTReader();
                MultiPolygon the_geom = (MultiPolygon) wktReader.read(allowedArea);
                the_geom.setSRID(Integer.valueOf(userLimitInfo.getSrid()).intValue());
                gsUser.setAllowedArea(the_geom);
            }else{
                gsUser.setAllowedArea(null);
            }

            georepoRemoteService.getUserAdminService().update(gsUser);
        } catch (NotFoundServiceEx e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }

        return userLimitInfo;
    }
}
