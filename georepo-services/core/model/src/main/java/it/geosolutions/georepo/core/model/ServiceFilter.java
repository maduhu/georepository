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

import it.geosolutions.georepo.core.model.enums.FilterGroup;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Basic user info
 * 
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */

@Entity(name = "ServiceFilter")
@Table(name = "gr_servicefilter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "servicefilter")

@XmlRootElement(name = "ServiceFilter")
public class ServiceFilter implements Serializable {

    @Id
    @GeneratedValue//(strategy=GenerationType.SEQUENCE, generator="DG_WATCH_SEQ")
//    @SequenceGenerator(name="DG_WATCH_SEQ", sequenceName="DG_WATCH_SEQ")
    @Column
    private long id;

    @Column(nullable=false, updatable=false)
    private String service;

    @Column(nullable=false, updatable=false)
    private String property;

    @Column(nullable=false, updatable=false)
    @Enumerated(EnumType.STRING)
    private FilterGroup filterGroup;

    @ManyToOne(optional=true)
    private User user;

    /**
     * Encoded array of values.
     * Use {@link it.geosolutions.georepo.core.model.util.FilterValueUtil} to deal with this field.
     */
    @Column(length=4000)
    private String value;

    public ServiceFilter() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public FilterGroup getFilterGroup() {
        return filterGroup;
    }

    public void setFilterGroup(FilterGroup filterGroup) {
        this.filterGroup = filterGroup;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
