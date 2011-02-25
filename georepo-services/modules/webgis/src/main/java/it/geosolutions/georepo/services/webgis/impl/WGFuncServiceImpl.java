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
import it.geosolutions.georepo.core.dao.ProfileDAO;
import it.geosolutions.georepo.core.model.Profile;
import it.geosolutions.georepo.services.exception.BadRequestWebEx;
import it.geosolutions.georepo.services.exception.IllegalParameterFault;
import it.geosolutions.georepo.services.exception.InternalErrorWebEx;
import it.geosolutions.georepo.services.exception.NotFoundWebEx;
import it.geosolutions.georepo.services.webgis.model.WebGisProperty;
import it.geosolutions.georepo.services.webgis.WebGisFuncService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class WGFuncServiceImpl implements WebGisFuncService {
    private final static Logger LOGGER = Logger.getLogger(WGFuncServiceImpl.class);

    private ProfileDAO profileDAO;

    @Override
    public String getProperty(String profileName, String property) throws IllegalParameterFault {
        if(property == null)
            throw new BadRequestWebEx("Property is null");

        Profile profile = getProfile(profileName);
        Map<String, String> customProps = profileDAO.getCustomProps(profile.getId());
        String prop = customProps.get(property);
        return prop; // what if null (not found)?
    }

    @Override
    public List<WebGisProperty> getProfileProperties(String profileName) throws IllegalParameterFault {
        Profile profile = getProfile(profileName);
        Map<String, String> customProps = profileDAO.getCustomProps(profile.getId());
        List<WebGisProperty> ret = new ArrayList<WebGisProperty>(customProps.size());
        for (Map.Entry<String, String> entry : customProps.entrySet()) {
            ret.add(new WebGisProperty(entry.getKey(), entry.getValue()));
        }
        return ret;
    }

    protected Profile getProfile(String profileName) throws InternalErrorWebEx, BadRequestWebEx, NotFoundWebEx {
        if (profileName == null) {
            throw new BadRequestWebEx("Profile is null");
        }
        Search searchCriteria = new Search(Profile.class);
        searchCriteria.addFilterEqual("name", profileName);
        List<Profile> profiles = profileDAO.search(searchCriteria);
        final Profile profile;
        switch (profiles.size()) {
            case 0:
                throw new NotFoundWebEx("Profile not found");
            case 1:
                profile = profiles.get(0);
                break;
            default:
                throw new InternalErrorWebEx("Too many profiles found (name:" + profileName + " #" + profiles.size() + ")");
        }
        return profile;
    }

    //==========================================================================

    public void setProfileDAO(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }

}
