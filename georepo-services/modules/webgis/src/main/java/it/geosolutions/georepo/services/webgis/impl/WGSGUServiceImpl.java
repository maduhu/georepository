/*
 *  Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 * 
 *  GPLv3 + Classpath exception
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.geosolutions.georepo.services.webgis.impl;

import com.trg.search.Search;
import it.geosolutions.georepo.core.dao.GSUserDAO;
import it.geosolutions.georepo.core.dao.ProfileDAO;
import it.geosolutions.georepo.core.model.GSUser;
import it.geosolutions.georepo.core.model.Profile;
import it.geosolutions.georepo.services.webgis.SGUService;
import it.geosolutions.georepo.services.webgis.model.SGUProfile;
import it.geosolutions.georepo.services.webgis.model.SGUProfileList;
import it.geosolutions.georepo.services.webgis.model.SGUUser;
import it.geosolutions.georepo.services.webgis.model.SGUUserList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class WGSGUServiceImpl implements SGUService {
    private final static Logger LOGGER = Logger.getLogger(WGSGUServiceImpl.class);

    private static final String EXTERNAL_ID_KEY = "sgu_id";

    private GSUserDAO userDAO;
    private ProfileDAO profileDAO;

    @Override
    public SGUProfileList getProfiles() {
        SGUProfileList list = new SGUProfileList();

        SGUProfile p1 = new SGUProfile();
        p1.setSguId("p1");
        p1.setName("Sample profile 1");
        list.getProfileList().add(p1);

        p1 = new SGUProfile();
        p1.setSguId("p2");
        p1.setName("Sample profile 2");
        list.getProfileList().add(p1);

        return list;
    }

    @Override
    public SGUUserList getUsers() {
        SGUUserList list = new SGUUserList();

        SGUUser user = new SGUUser();
        user.setSguId("u1");
        user.setUserName("sample_user_1");
        user.setGeomTableName("table1");
        user.setGeomIdField("field1");
        user.setGeomId("fid#100");
        list.getUserList().add(user);

        user = new SGUUser();
        user.setSguId("u2");
        user.setUserName("sample_user_2");
        user.setGeomTableName("table2");
        user.setGeomIdField("field2");
        user.setGeomId("fid#200");
        list.getUserList().add(user);

        return list;
    }

    //==========================================================================

    /**
     * TODO: we should create the profiles in a standalone thread.
     * Call performed when one thread is running should return an error message
     * to the caller into the return String
     *
     * TODO: disable in georepo all the profiles not included in the list.
     *
     * @param list
     * @return "OK" if the importing procedure started correctly or "ALREADY RUNNING" if another procedure is still running.
     */
    @Override
    public String setProfiles(SGUProfileList list) {
        LOGGER.info("Got " + list);
        int cntNew = 0;
        int cntUp = 0;
        int cntOld = 0;

        for (SGUProfile sguProfile : list.getProfileList()) {
            Search search = new Search(Profile.class).addFilterEqual("extId", sguProfile.getSguId());
            List<Profile> profileList = profileDAO.search(search);
//            List<Profile> profileList = profileDAO.searchByCustomProp(EXTERNAL_ID_KEY, sguProfile.getSguId());
            switch (profileList.size()) {
                case 0:
                    cntNew++;
                    LOGGER.info("Importing new " + sguProfile);
                    Profile profile = createProfile(sguProfile);
                    profileDAO.persist(profile);
                    break;
                case 1:
                    Profile oldProfile = profileList.get(0);
                    if( oldProfile.getName().equals(sguProfile.getName())) {
                        cntOld++;
                    } else {
                        cntUp++;
                        LOGGER.info("Updating " + sguProfile + " -- old name : " + oldProfile.getName());
                        oldProfile.setName(sguProfile.getName());
                        profileDAO.merge(oldProfile);
                    }
                    break;
                default:
                    LOGGER.error("Found " + profileList.size() + " profiles related to " + sguProfile + " Please check and fix the DB.");
            }

        }
        LOGGER.info("setProfiles: new:" + cntNew + " updated:" + cntUp + " existing:" + cntOld);
        return "OK";
    }

    private static Profile createProfile(SGUProfile sp) {
        Profile profile = new Profile();
        profile.setName(sp.getName());
        profile.setExtId(sp.getSguId());
//        profile.getCustomProps().put(EXTERNAL_ID_KEY, sp.getSguId());
        return profile;
    }

    //==========================================================================
    
    @Override
    public String setUsers(SGUUserList sguList) {
        LOGGER.info("Got " + sguList);
        int cntNew = 0;
        int cntErr = 0;
        int cntUp = 0;
        int cntOld = 0;

        ProfileCache profileCache = new ProfileCache(profileDAO);

        for (SGUUser sguUser : sguList.getUserList()) {
            Search search = new Search(GSUser.class).addFilterEqual("extId", sguUser.getSguId());
            List<GSUser> geoRepoList = userDAO.search(search);

            switch (geoRepoList.size()) {
                case 0:
                    LOGGER.info("Importing new " + sguUser);
                    GSUser user = createUser(sguUser, profileCache);
                    if(user == null) {
                        cntErr++;
                        LOGGER.error("Error creating new user from " + sguUser);
                    } else {
                        cntNew++;
                        userDAO.persist(user);
                    }
                    break;
                case 1:
                    GSUser oldUser = geoRepoList.get(0);                    

                    if( oldUser.getName().equals(sguUser.getUserName())) { // TODO: define policy for updating user (geom may be changed)
                        cntOld++;
                    } else {
                        cntUp++;
                        LOGGER.info("Updating " + sguUser + " -- old name : " + oldUser.getName());
                        oldUser.setName(sguUser.getUserName()); // TODO: maybe some other stuff to update...
                        userDAO.merge(oldUser);
                    }
                    break;
                default:
                    LOGGER.error("Found " + geoRepoList.size() + " users related to " + sguUser + " Please check and fix the DB.");
            }

        }
        LOGGER.info("setUsers: new:" + cntNew + " updated:" + cntUp + " existing:" + cntOld + " err:"+cntErr);
        return "OK";
    }

    private GSUser createUser(SGUUser su, ProfileCache profileCache) {
        GSUser user = new GSUser();
        user.setName(su.getUserName());
        user.setExtId(su.getSguId());
        Profile profile = profileCache.get(su.getProfileId());
        if(profile == null)
            return null;
        user.setProfile(profile);


        // TODO:::: RETRIEVE GEOMETRY FROM EXTERNAL TABLE

        return user;
    }

    //==========================================================================

    public void setProfileDAO(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }

    public void setUserDAO(GSUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    //==========================================================================


    class ProfileCache {
        private final Map<String, Profile> cache = new HashMap<String, Profile>();
        private final ProfileDAO profileDAO;

        public ProfileCache(ProfileDAO profileDAO) {
            this.profileDAO = profileDAO;
        }

        public Profile get(String sguId) {
            Profile profile = cache.get(sguId);
            if(profile == null) {
                Search search = new Search(Profile.class).addFilterEqual("extId", sguId);
                List<Profile> profileList = profileDAO.search(search);
                if(profileList.isEmpty()) {
                    LOGGER.warn("Profile sgu:" + sguId + " not found in GeoRepository. Make sure the profiles are in synch.");
                    // should we put a null in the cache?
                } else {
                    profile = profileList.get(0);
                    cache.put(sguId, profile);
                }
            }
            return profile;
        }
    }
}
