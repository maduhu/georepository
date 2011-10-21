/*
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S. http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.georepo.services.rest.impl;

import it.geosolutions.georepo.core.model.GSInstance;
import it.geosolutions.georepo.core.model.GSUser;
import it.geosolutions.georepo.core.model.LayerDetails;
import it.geosolutions.georepo.core.model.Profile;
import it.geosolutions.georepo.core.model.Rule;
import it.geosolutions.georepo.services.GetProviderService;
import it.geosolutions.georepo.services.InstanceAdminService;
import it.geosolutions.georepo.services.ProfileAdminService;
import it.geosolutions.georepo.services.RuleAdminService;
import it.geosolutions.georepo.services.UserAdminService;
import it.geosolutions.georepo.services.dto.ShortProfile;
import it.geosolutions.georepo.services.exception.NotFoundServiceEx;
import it.geosolutions.georepo.services.rest.RESTConfigService;
import it.geosolutions.georepo.services.rest.exception.BadRequestRestEx;
import it.geosolutions.georepo.services.rest.exception.InternalErrorRestEx;
import it.geosolutions.georepo.services.rest.exception.NotFoundRestEx;
import it.geosolutions.georepo.services.rest.model.config.RESTConfigurationRemapping;
import it.geosolutions.georepo.services.rest.model.config.RESTFullConfiguration;
import it.geosolutions.georepo.services.rest.model.config.RESTFullGSInstanceList;
import it.geosolutions.georepo.services.rest.model.config.RESTFullProfileList;
import it.geosolutions.georepo.services.rest.model.config.RESTFullRuleList;
import it.geosolutions.georepo.services.rest.model.config.RESTFullUserList;
import it.geosolutions.georepo.services.rest.utils.InstanceCleaner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RESTConfigServiceImpl implements RESTConfigService {
    private final static Logger LOGGER = Logger.getLogger(RESTConfigServiceImpl.class.toString());

    private UserAdminService userAdminService;
    private ProfileAdminService profileAdminService;
    private RuleAdminService ruleAdminService;
    private InstanceAdminService instanceAdminService;

    private InstanceCleaner instanceCleaner;

    @Override
    public RESTFullConfiguration getConfiguration() {
        RESTFullConfiguration cfg = new RESTFullConfiguration();

        RESTFullUserList users = new RESTFullUserList();
        users.setList(userAdminService.getFullList(null, null, null));
        cfg.setUserList(users);

        RESTFullProfileList profiles = new RESTFullProfileList();
        profiles.setList(profileAdminService.getFullList(null, null, null));
        cfg.setProfileList(profiles);

        RESTFullGSInstanceList instances = new RESTFullGSInstanceList();
        instances.setList(instanceAdminService.getList(null, null, null));
        cfg.setGsInstanceList(instances);

        RESTFullRuleList rules = new RESTFullRuleList();
        rules.setList(ruleAdminService.getListFull(null, null, null));
        cfg.setRuleList(rules);

        return cfg;
    }

    @Override
    public synchronized RESTConfigurationRemapping setConfiguration(RESTFullConfiguration config) {
        LOGGER.warn("SETTING CONFIGURATION");
        
        instanceCleaner.removeAll();
        RESTConfigurationRemapping remap = new RESTConfigurationRemapping();

        RemapperCache<Profile, ProfileAdminService> profileCache =
                new RemapperCache<Profile, ProfileAdminService>(profileAdminService, remap.getProfiles());
        RemapperCache<GSUser, UserAdminService> userCache =
                new RemapperCache<GSUser, UserAdminService>(userAdminService, remap.getUsers());
        RemapperCache<GSInstance, InstanceAdminService> instanceCache =
                new RemapperCache<GSInstance, InstanceAdminService>(instanceAdminService, remap.getInstances());


        // === Profiles
        for (Profile profile : config.getProfileList().getList()) {
            Long oldId = profile.getId();
            ShortProfile sp = new ShortProfile(profile);
            long newId = profileAdminService.insert(sp);
            LOGGER.info("Remapping profile " + oldId + " -> "  + newId);
            remap.getProfiles().put(oldId, newId);
            if(profile.getCustomProps() != null && ! profile.getCustomProps().isEmpty()) {
                profileAdminService.setCustomProps(newId, profile.getCustomProps());
            }
        }

        // === Users
        for (GSUser user : config.getUserList().getList()) {
            Long oldProfileId = user.getProfile().getId();

            Profile profile = profileCache.get(oldProfileId);
            user.setProfile(profile);            
            Long oldId = user.getId();
            user.setId(null);
            long newId = userAdminService.insert(user);
            LOGGER.info("Remapping user " + oldId + " -> "  + newId);
            remap.getUsers().put(oldId, newId);
        }


        // === GSInstances
        for (GSInstance instance : config.getGsInstanceList().getList()) {
            Long oldId = instance.getId();
            instance.setId(null);
            long newId = instanceAdminService.insert(instance);
            LOGGER.info("Remapping gsInstance " + oldId + " -> "  + newId);
            remap.getInstances().put(oldId, newId);
        }

        //=== Rules
        for (Rule rule : config.getRuleList().getList()) {
            Long oldId = rule.getId();
            rule.setId(null);

            if(rule.getProfile() != null)
                rule.setProfile(profileCache.get(rule.getProfile().getId()));

            if(rule.getGsuser() != null) {
                rule.setGsuser(userCache.get(rule.getGsuser().getId()));
            }

            if(rule.getInstance() != null) {
                rule.setInstance(instanceCache.get(rule.getInstance().getId()));
            }

            // the prob here is that layerdetails is a reverse reference, so only hibernate should be setting it.
            // using JAXB, it's injected, but we have to make hibernate eat it.
            LayerDetails ld = rule.getLayerDetails();
            rule.setLayerDetails(null);

            long newId = ruleAdminService.insert(rule);
            LOGGER.info("Remapping rule " + oldId + " -> "  + newId);
            remap.getRules().put(oldId, newId);

            if(ld != null) {
                ruleAdminService.setDetails(newId, ld);
            }
        }

        return remap;
    }


    @Override
    public RESTFullUserList getUsers() throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx {
        List<GSUser> users = userAdminService.getFullList(null,null,null);

        RESTFullUserList ret = new RESTFullUserList();
        ret.setList(users);

        return ret;
    }

    @Override
    public RESTFullProfileList getProfiles() throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx {
        List<Profile> profiles = profileAdminService.getFullList(null,null,null);

        RESTFullProfileList ret = new RESTFullProfileList();
        ret.setList(profiles);

        return ret;

    }

    //==========================================================================

//    class ProfileCache {
//        private final Map<String, Profile> cache = new HashMap<String, Profile>();
//        private final ProfileDAO profileDAO;
//
//        public ProfileCache(ProfileDAO profileDAO) {
//            this.profileDAO = profileDAO;
//        }
//
//        public Profile get(String sguId) {
//            Profile profile = cache.get(sguId);
//            if(profile == null) {
//                Search search = new Search(Profile.class).addFilterEqual("extId", sguId);
//                List<Profile> profileList = profileDAO.search(search);
//                if(profileList.isEmpty()) {
//                    LOGGER.warn("Profile sgu:" + sguId + " not found in GeoRepository. Make sure the profiles are in synch.");
//                    // should we put a null in the cache?
//                } else {
//                    profile = profileList.get(0);
//                    cache.put(sguId, profile);
//                }
//            }
//            return profile;
//        }
//    }

    //==========================================================================

    public void setInstanceCleaner(InstanceCleaner instanceCleaner) {
        this.instanceCleaner = instanceCleaner;
    }
    
    //==========================================================================

    public void setProfileAdminService(ProfileAdminService profileAdminService) {
        this.profileAdminService = profileAdminService;
    }

    public void setUserAdminService(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

    public void setInstanceAdminService(InstanceAdminService instanceAdminService) {
        this.instanceAdminService = instanceAdminService;
    }

    public void setRuleAdminService(RuleAdminService ruleAdminService) {
        this.ruleAdminService = ruleAdminService;
    }

    //==========================================================================


    class RemapperCache<TYPE, SERVICE extends GetProviderService<TYPE>> {
        private Map<Long, TYPE> cache = new HashMap<Long, TYPE>();
        private final Map<Long, Long> idRemapper;
        private final SERVICE service;

        public RemapperCache(SERVICE service, Map<Long, Long> idRemapper) {
            this.idRemapper = idRemapper;
            this.service = service;
        }


        TYPE get(Long oldId) {
            Long newId = idRemapper.get(oldId);
            if(newId == null) {
                LOGGER.error("Can't remap " + oldId );
                LOGGER.error("Configuration will be erased");
                instanceCleaner.removeAll();
                throw new BadRequestRestEx("Can't remap " + oldId);
            }

            TYPE cached = cache.get(newId);
            try {
                if (cached == null) {
                    cached = service.get(newId.longValue()); // may throw NotFoundServiceEx
                    cache.put(newId, cached);
                }
                return cached;
            } catch (NotFoundServiceEx ex) {
                LOGGER.error(ex.getMessage(), ex);
                throw new NotFoundRestEx(ex.getMessage());
            }
        }
    }

//    class ProfileCache {
//
//        private Map<Long, Profile> profileCache = new HashMap<Long, Profile>();
//        private final Map<Long, Long> idRemapper;
//
//        public ProfileCache(Map<Long, Long> idRemapper) {
//            this.idRemapper = idRemapper;
//        }
//
//
//        Profile getProfile(Long oldId) {
//            Long newProfileId = idRemapper.get(oldId);
//            if(newProfileId == null) {
//                LOGGER.error("Can't remap profile " + oldId);
//                LOGGER.error("Configuration will be erased");
//                instanceCleaner.removeAll();
//                throw new BadRequestRestEx("Can't remap profile " + oldId);
//            }
//
//            Profile profile = profileCache.get(newProfileId);
//            try {
//                if (profile == null) {
//                    profile = profileAdminService.get(newProfileId); // may throw NotFoundServiceEx
//                    profileCache.put(newProfileId, profile);
//                }
//                return profile;
//            } catch (NotFoundServiceEx ex) {
//                LOGGER.error(ex.getMessage(), ex);
//                throw new NotFoundRestEx(ex.getMessage());
//            }
//        }
//    }

}
