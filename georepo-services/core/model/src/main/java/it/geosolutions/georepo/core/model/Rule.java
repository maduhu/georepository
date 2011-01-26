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

import it.geosolutions.georepo.core.model.enums.GrantType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

/**
 * A Rule expresses if a given combination of request access is allowed or not.
 * <P>
 * In a given Rule, you may specify a precise combination of filters or a general
 * behavior. <BR>
 * Filtering can be done on <UL>
 * <LI> the requesting user </LI>
 * <LI> the profile associated to the requesting user</LI>
 * <LI> the instance of the accessed geoserver</LI>
 * <LI> the accessed service (e.g.: WMS)</LI>
 * <LI> the requested operation inside the accessed service (e.g.: getMap)</LI>
 * <LI> the workspace in geoserver</LI>
 * <LI> the requested layer </LI>
 * </UL>
 * <P><B>Example</B>: In order to allow access to every request to the WMS service in the instance GS1,
 * you will need to create a Rule, by only setting Service=WMS and Instance=GS1,
 * leaving the other fields to <TT>null</TT>.
 * <P>
 * When an access has to be checked for filtering, all the matching rules are read;
 * they are then evaluated according to their priority: the first rule found having
 * accessType <TT><B>{@link GrantType#ALLOW}</B></TT> or <TT><B>{@link GrantType#DENY}</B></TT> wins,
 * and the access is granted or denied accordingly.
 * <BR>Matching rules with accessType=<TT><B>{@link GrantType#LIMIT}</B></TT> are collected and evaluated at the end,
 * only if the request is Allowed by some other rule with lower priority.
 * <BR>These rules will have an associated {@link RuleLimits RuleLimits} that
 * defines some restrictions for using the data (such as area limitation).
 *
 * @author ETj (etj at geo-solutions.it)
 */
@Entity(name = "Rule")
@Table(name = "gr_rule",
    uniqueConstraints = {@UniqueConstraint(columnNames={"gsuser_id", "profile_id","instance_id","service","request","workspace","layer"})})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Rule")
@XmlRootElement(name = "Rule")
public class Rule {

    /** The id. */
    @Id
    @GeneratedValue
    @Column
    private Long id;

    /** Lower numbers have higher priority */
    @Column(nullable=false)
    @Index(name="idx_rule_priority")
    private long priority;

    @ManyToOne(optional = true, fetch=FetchType.EAGER)
    @ForeignKey(name="fk_rule_user")
    private GSUser gsuser;

    @ManyToOne(optional = true, fetch=FetchType.EAGER)
    @ForeignKey(name="fk_rule_profile")
    private Profile profile;

    @ManyToOne(optional = true, fetch=FetchType.EAGER)
    @ForeignKey(name="fk_rule_instance")
    private GSInstance instance;


    @Column
    @Index(name="idx_rule_service")
    private String service;

    @Column
    @Index(name="idx_rule_request")
    private String request;

    @Column
    @Index(name="idx_rule_workspace")
    private String workspace;

    @Column
    @Index(name="idx_rule_layer")
    private String layer;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private GrantType access;

    @OneToOne(optional = true, cascade=CascadeType.REMOVE)
    @ForeignKey(name="fk_rule_details")
    private LayerDetails layerDetails;

    public Rule() {
    }

    public Rule(long priority, GSUser gsuser, Profile profile, GSInstance instance, String service, String request, String workspace, String layer, GrantType access) {
        this.priority = priority;
        this.gsuser = gsuser;
        this.profile = profile;
        this.instance = instance;
        this.service = service;
        this.request = request;
        this.workspace = workspace;
        this.layer = layer;
        this.access = access;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getPriority() {
        return priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }
    
    public GSUser getGsuser() {
        return gsuser;
    }

    public void setGsuser(GSUser gsuser) {
        this.gsuser = gsuser;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public GSInstance getInstance() {
        return instance;
    }

    public void setInstance(GSInstance instance) {
        this.instance = instance;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public LayerDetails getLayerDetails() {
        return layerDetails;
    }

    public void setLayerDetails(LayerDetails layerDetails) {
        this.layerDetails = layerDetails;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public GrantType getAccess() {
        return access;
    }

    public void setAccess(GrantType access) {
        this.access = access;
    }
}
