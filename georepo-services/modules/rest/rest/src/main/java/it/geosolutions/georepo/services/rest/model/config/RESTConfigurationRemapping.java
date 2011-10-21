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
package it.geosolutions.georepo.services.rest.model.config;

import it.geosolutions.georepo.services.rest.model.config.adapter.RemapperAdapter;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
@XmlRootElement(name="Remapping")
public class RESTConfigurationRemapping {

//    public static enum TYPE { PROFILE,USER,GSINSTANCE,RULE};
//
//    public static class Remap {
//        TYPE type;
//        Long oldId;
//        Long newId;
//
//        public Long getNewId() {
//            return newId;
//        }
//
//        public void setNewId(Long newId) {
//            this.newId = newId;
//        }
//
//        public Long getOldId() {
//            return oldId;
//        }
//
//        public void setOldId(Long oldId) {
//            this.oldId = oldId;
//        }
//
//        public TYPE getType() {
//            return type;
//        }
//
//        public void setType(TYPE type) {
//            this.type = type;
//        }
//
//    }

    private Map<Long,Long> users = new HashMap<Long, Long>();
    private Map<Long,Long> profiles = new HashMap<Long, Long>();
    private Map<Long,Long> instances = new HashMap<Long, Long>();
    private Map<Long,Long> rules = new HashMap<Long, Long>();

    @XmlJavaTypeAdapter(RemapperAdapter.class)
    @XmlElement(name="instances")
    public Map<Long, Long> getInstances() {
        return instances;
    }

    @XmlJavaTypeAdapter(RemapperAdapter.class)
    @XmlElement(name="profiles")
    public Map<Long, Long> getProfiles() {
        return profiles;
    }

    @XmlJavaTypeAdapter(RemapperAdapter.class)
    @XmlElement(name="rules")
    public Map<Long, Long> getRules() {
        return rules;
    }

    @XmlJavaTypeAdapter(RemapperAdapter.class)
    @XmlElement(name="users")
    public Map<Long, Long> getUsers() {
        return users;
    }

}
