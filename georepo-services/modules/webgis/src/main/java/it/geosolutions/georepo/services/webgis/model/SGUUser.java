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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
@XmlRootElement(name="SGUUser")
public class SGUUser {

    private String sguId;
    private String userName;

    private String profileId;

    private String geomTableName;
    private String geomIdField;
    private String geomId;
    
    @XmlAttribute(name="id", required=true)
    public String getSguId() {
        return sguId;
    }

    public void setSguId(String sguId) {
        this.sguId = sguId;
    }

    @XmlElement
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @XmlElement
    public String getGeomId() {
        return geomId;
    }

    public void setGeomId(String geomId) {
        this.geomId = geomId;
    }

    @XmlElement
    public String getGeomIdField() {
        return geomIdField;
    }

    public void setGeomIdField(String geomIdField) {
        this.geomIdField = geomIdField;
    }

    @XmlElement
    public String getGeomTableName() {
        return geomTableName;
    }

    public void setGeomTableName(String geomTableName) {
        this.geomTableName = geomTableName;
    }

    @XmlElement
    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    @Override
    public String toString() {
        return "SGUUser{" + "sguId=" + sguId 
                + " userName=" + userName
                + " profileId=" + profileId
                + " geomTableName=" + geomTableName
                + " geomIdField=" + geomIdField +
                " geomId=" + geomId + '}';
    }

}
