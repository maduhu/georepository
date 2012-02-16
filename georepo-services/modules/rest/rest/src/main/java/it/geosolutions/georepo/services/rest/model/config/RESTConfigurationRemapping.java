/*
 *  Copyright (C) 2007 - 2012 GeoSolutions S.A.S.
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
package it.geosolutions.georepo.services.rest.model.config;

import it.geosolutions.georepo.core.model.GRUser;
import it.geosolutions.georepo.core.model.GSInstance;
import it.geosolutions.georepo.core.model.GSUser;
import it.geosolutions.georepo.core.model.Profile;
import it.geosolutions.georepo.core.model.Rule;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import it.geosolutions.georepo.services.rest.model.config.adapter.RemapperAdapter;


/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
@XmlRootElement(name = "Remapping")
public class RESTConfigurationRemapping
{

    private Map<Long, Long> users = new HashMap<Long, Long>();
    private Map<Long, Long> profiles = new HashMap<Long, Long>();
    private Map<Long, Long> instances = new HashMap<Long, Long>();
    private Map<Long, Long> rules = new HashMap<Long, Long>();
    private Map<Long, Long> grUsers = new HashMap<Long, Long>();

    public void remap(Long newId, GSUser old) {
        users.put(old.getId(), newId);
    }
    public void remap(Long newId, GRUser old) {
        grUsers.put(old.getId(), newId);
    }
    public void remap(Long newId, Profile old) {
        profiles.put(old.getId(), newId);
    }
    public void remap(Long newId, GSInstance old) {
        instances.put(old.getId(), newId);
    }
    public void remap(Long newId, Rule old) {
        rules.put(old.getId(), newId);
    }

    @XmlJavaTypeAdapter(RemapperAdapter.class)
    @XmlElement(name = "instances")
    public Map<Long, Long> getInstances()
    {
        return instances;
    }

    @XmlJavaTypeAdapter(RemapperAdapter.class)
    @XmlElement(name = "profiles")
    public Map<Long, Long> getProfiles()
    {
        return profiles;
    }

    @XmlJavaTypeAdapter(RemapperAdapter.class)
    @XmlElement(name = "rules")
    public Map<Long, Long> getRules()
    {
        return rules;
    }

    @XmlJavaTypeAdapter(RemapperAdapter.class)
    @XmlElement(name = "users")
    public Map<Long, Long> getUsers()
    {
        return users;
    }

    @XmlJavaTypeAdapter(RemapperAdapter.class)
    @XmlElement(name = "internalUsers")
    public Map<Long, Long> getGRUsers()
    {
        return grUsers;
    }

}
