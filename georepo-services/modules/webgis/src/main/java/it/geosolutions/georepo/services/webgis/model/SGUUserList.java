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

@XmlRootElement(name="SGUUserList")
public class SGUUserList {

    private List<SGUUser> userList;

    public SGUUserList() {
        this(10);
    }

    public SGUUserList(int initialCapacity) {
        userList = new ArrayList<SGUUser>(initialCapacity);
    }

    @XmlElement(name="SGUUser")
    public List<SGUUser> getUserList() {
        return userList;
    }

    public void setUserList(List<SGUUser> userList) {
        this.userList = userList;
    }

    public void add(SGUUser user) {
        userList.add(user);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +"[" + userList.size() + " users]";
    }

}

