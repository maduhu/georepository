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

package it.geosolutions.georepo.services.dto;

import it.geosolutions.georepo.core.model.Profile;
import java.io.Serializable;
import java.util.Date;

/**
 * A compact representation of Profile useful in lists.
 * 
 * @author Etj (etj at geo-solutions.it)
 */
public class ShortProfile implements Serializable {

    private static final long serialVersionUID = -8410646966443187827L;
    private long id;
    private String name;
    private String extId;
    private Date dateCreation;
    private boolean enabled;

    public ShortProfile() {
    }

    public ShortProfile(Profile profile) {
        this.id = profile.getId();
        this.name = profile.getName();
        this.dateCreation = profile.getDateCreation();
        this.enabled = profile.getEnabled();
        this.extId = profile.getExtId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                +"[id=" + id
                + " name=" + name
                + " enabled=" + enabled
                + ']';
    }

}
