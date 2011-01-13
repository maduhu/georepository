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

import it.geosolutions.georepo.core.model.enums.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Entity(name = "AttributePermission")
@Table(name = "gr_perm_attr")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "AttributePermission")
@XmlRootElement(name = "AttributePermission")
public class AttributePermission {

    /** The id. */
    @Id
    @GeneratedValue
    @Column
    private long id;

    @Column(nullable=false)
    private String attribute;

    @Column(length=5, nullable=false)
    @Enumerated(EnumType.STRING)
    private AccessType accessType;

    @ManyToOne(optional = false)
    @ForeignKey(name="fk_attr_layer")
    private LayerPermission layerPermission;

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LayerPermission getLayerPermission() {
        return layerPermission;
    }

    public void setLayerPermission(LayerPermission layerPermission) {
        this.layerPermission = layerPermission;
    }

}
