/*
 * $ Header: it.geosolutions.georepo.gui.server.service.impl.ProfilesManagerServiceImpl,v. 0.1 10-feb-2011 17.07.06 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 10-feb-2011 17.07.06 $
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
import it.geosolutions.georepo.gui.client.model.data.LayerCustomProps;
import it.geosolutions.georepo.gui.client.model.data.ProfileCustomProps;
import it.geosolutions.georepo.gui.server.service.IProfilesManagerService;
import it.geosolutions.georepo.gui.service.GeoRepoRemoteService;
import it.geosolutions.georepo.services.dto.ShortProfile;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public PagingLoadResult<Profile> getProfiles(PagingLoadConfig config, boolean full)
            throws ApplicationException {

        int start = config.getOffset();

        List<Profile> profileListDTO = new ArrayList<Profile>();

        if (full) {
            Profile all_profile = new Profile();
            all_profile.setId(-1);
            all_profile.setName("*");
            all_profile.setEnabled(true);
            all_profile.setDateCreation(null);
            profileListDTO.add(all_profile);
        }
        
        long profilesCount = georepoRemoteService.getProfileAdminService().getCount(null) + 1;

        Long t = new Long(profilesCount);

        int page = start == 0 ? start : start / config.getLimit();

        List<ShortProfile> profilesList = georepoRemoteService.getProfileAdminService().getList(
                null, page, config.getLimit());

        if (profilesList == null) {
            if (logger.isErrorEnabled())
                logger.error("No profile found on server");
            throw new ApplicationException("No profile found on server");
        }

        Iterator<ShortProfile> it = profilesList.iterator();

        while (it.hasNext()) {
            ShortProfile short_profile = it.next();

            it.geosolutions.georepo.core.model.Profile remote_profile;
            try {
                remote_profile = georepoRemoteService.getProfileAdminService().get(
                        short_profile.getId());
            } catch (ResourceNotFoundFault e) {
                if (logger.isErrorEnabled())
                    logger.error("Details for profile " + short_profile.getName()
                            + " not found on Server!");
                throw new ApplicationException("Details for profile " + short_profile.getName()
                        + " not found on Server!");
            }
            Profile local_profile = new Profile();

            local_profile.setId(short_profile.getId());
            local_profile.setName(remote_profile.getName());
            local_profile.setDateCreation(remote_profile.getDateCreation());
            local_profile.setEnabled(remote_profile.getEnabled());
            // TODO: use specific API methods in order to load Profile custom props
            //local_profile.setCustomProps(remote_profile.getCustomProps());

            profileListDTO.add(local_profile);
        }

        return new BasePagingLoadResult<Profile>(profileListDTO, config.getOffset(), t.intValue());
    }

    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.server.service.IProfilesManagerService#deleteProfile(it.geosolutions.georepo.gui.client.model.Profile)
     */
    public void deleteProfile(Profile profile) {
        it.geosolutions.georepo.core.model.Profile remote_profile = null;
        try {
            remote_profile = georepoRemoteService.getProfileAdminService().get(profile.getId()); 
            georepoRemoteService.getProfileAdminService().delete(remote_profile.getId());
        } catch (ResourceNotFoundFault e) {
            logger.error(e.getLocalizedMessage(), e.getCause());
            throw new ApplicationException(e.getLocalizedMessage(), e.getCause());
        }
    }

    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.server.service.IProfilesManagerService#saveProfile(it.geosolutions.georepo.gui.client.model.Profile)
     */
    public void saveProfile(Profile profile) {
        it.geosolutions.georepo.core.model.Profile remote_profile = null;
        if (profile.getId() >= 0) {
            try {
                remote_profile = georepoRemoteService.getProfileAdminService().get(profile.getId()); 
                ShortProfile short_profile = new ShortProfile();
                short_profile.setId(remote_profile.getId());
                short_profile.setName(profile.getName());
                short_profile.setEnabled(profile.isEnabled());                
                georepoRemoteService.getProfileAdminService().update(short_profile);
            } catch (ResourceNotFoundFault e) {
                logger.error(e.getLocalizedMessage(), e.getCause());
                throw new ApplicationException(e.getLocalizedMessage(), e.getCause());
            } 
        } else {
            try {
                remote_profile = new it.geosolutions.georepo.core.model.Profile();
                ShortProfile short_profile = new ShortProfile();
                short_profile.setName(profile.getName());
                short_profile.setEnabled(profile.isEnabled());
                short_profile.setDateCreation(profile.getDateCreation());
                georepoRemoteService.getProfileAdminService().insert(short_profile);
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage(), e.getCause());
                throw new ApplicationException(e.getLocalizedMessage(), e.getCause());
            } 
        }
    }
    
    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.server.service.IProfilesManagerService#getProfileCustomProps(com.extjs.gxt.ui.client.data.PagingLoadConfig, it.geosolutions.georepo.gui.client.model.Rule)
     */
    public PagingLoadResult<ProfileCustomProps> getProfileCustomProps(PagingLoadConfig config, Profile profile){
        int start = config.getOffset();
        Long t = new Long(0);

        List<ProfileCustomProps> customPropsDTO = new ArrayList<ProfileCustomProps>();

        if (profile != null && profile.getId() >= 0) {
            try {
                Map<String, String> customProperties = georepoRemoteService.getProfileAdminService()
                        .getCustomProps(profile.getId());

                if (customProperties == null) {
                    if (logger.isErrorEnabled())
                        logger.error("No property found on server");
                    throw new ApplicationException("No rule found on server");
                }

                long rulesCount = customProperties.size();

                t = new Long(rulesCount);

                int page = start == 0 ? start : start / config.getLimit();

                SortedSet<String> sortedset = new TreeSet<String>(customProperties.keySet());
                Iterator<String> it = sortedset.iterator();

                while (it.hasNext()) {
                    String key = it.next();
                    ProfileCustomProps property = new ProfileCustomProps();
                    property.setPropKey(key);
                    property.setPropValue(customProperties.get(key));
                    customPropsDTO.add(property);
                }
                
//                for (String key : customProperties.keySet()) {
//                    ProfileCustomProps property = new ProfileCustomProps();
//                    property.setPropKey(key);
//                    property.setPropValue(customProperties.get(key));
//                    customPropsDTO.add(property);
//                }
            } catch (Exception e) {
                // do nothing!
            }
        }

        return new BasePagingLoadResult<ProfileCustomProps>(customPropsDTO, config.getOffset(), t
                .intValue());
    }
    
    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.server.service.IProfilesManagerService#setProfileProps(java.lang.Long, java.util.List)
     */
    public void setProfileProps(Long profileId, List<ProfileCustomProps> customProps){
        Map<String, String> props = new HashMap<String, String>();

        for (ProfileCustomProps prop : customProps) {
            props.put(prop.getPropKey(), prop.getPropValue());
        }
        
//        LayerDetails details = null;
//        try {
//            details = georepoRemoteService.getRuleAdminService().getDetails(ruleId);
//        } catch (Exception e) {
//            details = new LayerDetails();
//            georepoRemoteService.getRuleAdminService().setDetails(ruleId, details);
//        }
        
        georepoRemoteService.getProfileAdminService().setCustomProps(profileId, props);
    }
}
