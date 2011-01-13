/*
 * $ Header: it.geosolutions.georepo.core.model.ServiceFilter,v. 0.1 4-gen-2011 16.53.20 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 4-gen-2011 16.53.20 $
 *
 * ====================================================================
 *
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 * http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. 
 *
 * ====================================================================
 *
 * This software consists of voluntary contributions made by developers
 * of GeoSolutions.  For more information on GeoSolutions, please see
 * <http://www.geo-solutions.it/>.
 *
 */

package it.geosolutions.georepo.core.model;

import it.geosolutions.georepo.core.model.enums.ServiceType;

import java.io.Serializable;
import java.util.Date;

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

/**
 * The Class ServiceFilter.
 */

@Entity(name = "ServiceFilter")
@Table(name = "gr_servicefilter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "servicefilter")
@XmlRootElement(name = "ServiceFilter")
public class ServiceFilter implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5766758830127243524L;

    /** The id. */
    @Id
    @GeneratedValue
    @Column
    private long id;

    /** The service. */
    @Column(nullable = false, updatable = true)
    private String service;

    /** The property. */
    @Column(nullable = false, updatable = true)
    private String property;

    /** The date creation. */
    @Column
    private Date dateCreation;

    /** The service type. */
    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    /** The profile. */
//    @ManyToOne(optional = false)
    private Profile profile;

    /** The instance. */
//    @ManyToOne(optional = true)
    private Instance instance;
    
    /** The value. */
    @Column(length = 4000)
    private String value;

    /**
     * Instantiates a new service filter.
     */
    public ServiceFilter() {
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id
     *            the new id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the property.
     * 
     * @return the property
     */
    public String getProperty() {
        return property;
    }

    /**
     * Sets the property.
     * 
     * @param property
     *            the new property
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * Gets the service.
     * 
     * @return the service
     */
    public String getService() {
        return service;
    }

    /**
     * Sets the service.
     * 
     * @param service
     *            the new service
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value.
     * 
     * @param value
     *            the new value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the date creation.
     * 
     * @return the date creation
     */
    public Date getDateCreation() {
        return dateCreation;
    }

    /**
     * Sets the date creation.
     * 
     * @param dateCreation
     *            the new date creation
     */
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    /**
     * Gets the filter group.
     * 
     * @return the filter group
     */
    public ServiceType getServiceType() {
        return serviceType;
    }

    /**
     * Sets the filter group.
     * 
     * @param serviceType
     *            the new filter group
     */
    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * Gets the user.
     * 
     * @return the user
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Sets the user.
     * 
     * @param profile
     *            the new user
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    /**
     * @param instance the instance to set
     */
    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    /**
     * @return the instance
     */
    public Instance getInstance() {
        return instance;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dateCreation == null) ? 0 : dateCreation.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((instance == null) ? 0 : instance.hashCode());
        result = prime * result + ((profile == null) ? 0 : profile.hashCode());
        result = prime * result + ((property == null) ? 0 : property.hashCode());
        result = prime * result + ((service == null) ? 0 : service.hashCode());
        result = prime * result + ((serviceType == null) ? 0 : serviceType.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ServiceFilter)) {
            return false;
        }
        ServiceFilter other = (ServiceFilter) obj;
        if (dateCreation == null) {
            if (other.dateCreation != null) {
                return false;
            }
        } else if (!dateCreation.equals(other.dateCreation)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (instance == null) {
            if (other.instance != null) {
                return false;
            }
        } else if (!instance.equals(other.instance)) {
            return false;
        }
        if (profile == null) {
            if (other.profile != null) {
                return false;
            }
        } else if (!profile.equals(other.profile)) {
            return false;
        }
        if (property == null) {
            if (other.property != null) {
                return false;
            }
        } else if (!property.equals(other.property)) {
            return false;
        }
        if (service == null) {
            if (other.service != null) {
                return false;
            }
        } else if (!service.equals(other.service)) {
            return false;
        }
        if (serviceType == null) {
            if (other.serviceType != null) {
                return false;
            }
        } else if (!serviceType.equals(other.serviceType)) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ServiceFilter [");
        if (dateCreation != null)
            builder.append("dateCreation=").append(dateCreation).append(", ");
        builder.append("id=").append(id).append(", ");
        if (instance != null)
            builder.append("instance=").append(instance).append(", ");
        if (profile != null)
            builder.append("profile=").append(profile).append(", ");
        if (property != null)
            builder.append("property=").append(property).append(", ");
        if (service != null)
            builder.append("service=").append(service).append(", ");
        if (serviceType != null)
            builder.append("serviceType=").append(serviceType).append(", ");
        if (value != null)
            builder.append("value=").append(value);
        builder.append("]");
        return builder.toString();
    }

}
