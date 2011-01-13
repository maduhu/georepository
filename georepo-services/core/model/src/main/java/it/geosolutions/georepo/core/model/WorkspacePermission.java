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

package it.geosolutions.georepo.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
@Entity(name = "WorkspacePermission")
@Table(name = "gr_perm_workspace")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "WorkspacePermission")
@XmlRootElement(name = "WorkspacePermission")
public class WorkspacePermission {

    /** The id. */
    @Id
    @GeneratedValue
    @Column
    private long id;

    @Column(nullable=false)
    private String workspace;

    @Column(nullable=false)
    private boolean enabled;

    @ManyToOne(optional = false)
    @ForeignKey(name="fk_workspace_service")
    private ServicePermission servicePermission;


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public ServicePermission getServicePermission() {
        return servicePermission;
    }

    public void setServicePermission(ServicePermission servicePermission) {
        this.servicePermission = servicePermission;
    }


}
