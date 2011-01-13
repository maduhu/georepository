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

import com.vividsolutions.jts.geom.MultiPolygon;
import it.geosolutions.georepo.core.model.adapter.MultiPolygonAdapter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
@Entity(name = "ServicePermission")
@Table(name = "gr_perm_service")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "ServicePermission")
@XmlRootElement(name = "ServicePermission")
public class ServicePermission {

    /** The id. */
    @Id
    @GeneratedValue
    @Column
    private long id;

    @Column(nullable=false)
    private String service;

    @Column
    private String serviceUrl;

    @Column(nullable=false)
    private boolean enabled;

	@Type(type = "org.hibernatespatial.GeometryUserType")
	@Column(name = "area")
	private MultiPolygon area;

    @ManyToOne(optional = false)
    @ForeignKey(name="fk_service_profile")
    private Profile profile;

    @XmlJavaTypeAdapter(MultiPolygonAdapter.class)
    public MultiPolygon getArea() {
        return area;
    }

    public void setArea(MultiPolygon area) {
        this.area = area;
    }

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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

}
