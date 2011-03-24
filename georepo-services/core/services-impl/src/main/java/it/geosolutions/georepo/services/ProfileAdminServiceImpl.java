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

    private ProfileDAO profileDAO;

    // ==========================================================================
    @Override
    public long insert(ShortProfile profile) {
        Profile p = new Profile();
        p.setName(profile.getName());
        p.setEnabled(profile.isEnabled());
        profileDAO.persist(p);
        return p.getId();
    }

    @Override
    public long update(ShortProfile profile) throws ResourceNotFoundFault {
        Profile orig = profileDAO.find(profile.getId());
        if (orig == null) {
            throw new ResourceNotFoundFault("Profile not found", profile.getId());
        }

        orig.setName(profile.getName());
        orig.setEnabled(profile.isEnabled());

        profileDAO.merge(orig);
        return orig.getId();
    }

    @Override
    public Profile get(long id) throws ResourceNotFoundFault {
        Profile profile = profileDAO.find(id);

        if (profile == null) {
            throw new ResourceNotFoundFault("Profile not found", id);
        }

//        return new ShortProfile(profile);
        return profile;
    }

    @Override
    public boolean delete(long id) throws ResourceNotFoundFault {
        Profile profile = profileDAO.find(id);

        if (profile == null) {
            throw new ResourceNotFoundFault("Profile not found", id);
        }

        // data on ancillary tables should be deleted by cascading
        return profileDAO.remove(profile);
    }

    @Override
    public List<ShortProfile> getAll() {
        List<Profile> found = profileDAO.findAll();
        return convertToShortList(found);
    }

    @Override
    public List<ShortProfile> getList(String nameLike, int page, int entries) {
        Search searchCriteria = new Search(Profile.class);
        searchCriteria.setMaxResults(entries);
        searchCriteria.setPage(page);
        searchCriteria.addSortAsc("name");

        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }

        List<Profile> found = profileDAO.search(searchCriteria);
//        return found;
        return convertToShortList(found);
    }

    @Override
    public long getCount(String nameLike) {
        Search searchCriteria = new Search(Profile.class);

        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }

        return profileDAO.count(searchCriteria);
    }

    // ==========================================================================

    @Override
    public Map<String, String> getCustomProps(Long id) {
        return profileDAO.getCustomProps(id);
    }

    @Override
    public void setCustomProps(Long id, Map<String, String> props) {
        profileDAO.setCustomProps(id, props);
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

    public void setProfileDAO(ProfileDAO profileDao) {
        this.profileDAO = profileDao;
    }

}
