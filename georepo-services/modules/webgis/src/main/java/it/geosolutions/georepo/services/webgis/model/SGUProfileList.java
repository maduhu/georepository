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

package it.geosolutions.georepo.services.webgis.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */

@XmlRootElement(name="SGUProfileList")
public class SGUProfileList {

    private List<SGUProfile> profileList;

    public SGUProfileList() {
        this(10);
    }

    public SGUProfileList(int initialCapacity) {
        profileList = new ArrayList<SGUProfile>(initialCapacity);
    }


    @XmlElement(name="SGUProfile")
    public List<SGUProfile> getProfileList() {
        return profileList;
    }

    public void setProfileList(List<SGUProfile> profileList) {
        this.profileList = profileList;
    }

    public void add(SGUProfile profile) {
        profileList.add(profile);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +"[" + profileList.size() + " profiles]";
    }

}
