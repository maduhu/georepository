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
package it.geosolutions.georepo.services;

import it.geosolutions.georepo.core.dao.ProfileDAO;
import it.geosolutions.georepo.core.model.Profile;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;

import java.util.List;

import org.apache.log4j.Logger;

import com.trg.search.Search;
import it.geosolutions.georepo.services.dto.ShortProfile;
import java.util.ArrayList;
import java.util.Map;

/**
 * 
 * @author ETj (etj at geo-solutions.it)
 */
public class ProfileAdminServiceImpl implements ProfileAdminService {

    private final static Logger LOGGER = Logger.getLogger(ProfileAdminServiceImpl.class);

    private ProfileDAO profileDao;

    // ==========================================================================
    @Override
    public long insertProfile(ShortProfile profile) {
        Profile p = new Profile();
        p.setName(profile.getName());
        p.setEnabled(profile.isEnabled());
        profileDao.persist(p);
        return p.getId();
    }

    @Override
    public long updateProfile(ShortProfile profile) throws ResourceNotFoundFault {
        Profile orig = profileDao.find(profile.getId());
        if (orig == null) {
            throw new ResourceNotFoundFault("Profile not found", profile.getId());
        }

        orig.setName(profile.getName());
        orig.setEnabled(profile.isEnabled());

        profileDao.merge(orig);
        return orig.getId();
    }

    @Override
    public Profile getProfile(long id) throws ResourceNotFoundFault {
        Profile profile = profileDao.find(id);

        if (profile == null) {
            throw new ResourceNotFoundFault("Profile not found", id);
        }

//        return new ShortProfile(profile);
        return profile;
    }

    @Override
    public boolean deleteProfile(long id) throws ResourceNotFoundFault {
        Profile profile = profileDao.find(id);

        if (profile == null) {
            throw new ResourceNotFoundFault("Profile not found", id);
        }

        // data on ancillary tables should be deleted by cascading
        return profileDao.remove(profile);
    }

    @Override
    public List<ShortProfile> getAllProfiles() {
        List<Profile> found = profileDao.findAll();
        return convertToShortList(found);
    }

    @Override
    public List<ShortProfile> getProfiles(String nameLike, int page, int entries) {
        Search searchCriteria = new Search(Profile.class);
        searchCriteria.setMaxResults(entries);
        searchCriteria.setPage(page);
        searchCriteria.addSortAsc("name");

        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }

        List<Profile> found = profileDao.search(searchCriteria);
//        return found;
        return convertToShortList(found);
    }

    @Override
    public long getProfilesCount(String nameLike) {
        Search searchCriteria = new Search(Profile.class);

        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }

        return profileDao.count(searchCriteria);
    }

    // ==========================================================================

    @Override
    public Map<String, String> getCustomProps(Long id) {
        return profileDao.getCustomProps(id);
    }

    @Override
    public void setCustomProps(Long id, Map<String, String> props) {
        profileDao.setCustomProps(id, props);
    }

    // ==========================================================================

    private List<ShortProfile> convertToShortList(List<Profile> list) {
        List<ShortProfile> swList = new ArrayList<ShortProfile>(list.size());
        for (Profile profile : list) {
            swList.add(new ShortProfile(profile));
        }

        return swList;
    }

    // ==========================================================================

    public void setProfileDao(ProfileDAO profileDao) {
        this.profileDao = profileDao;
    }

}
