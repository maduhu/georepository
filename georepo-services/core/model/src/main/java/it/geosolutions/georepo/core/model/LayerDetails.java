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
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;

/**
 * Details may be set only for ules with non-wildcarded profile, instance, workspace,layer.
 *
 *
 * @author ETj (etj at geo-solutions.it)
 */
@Entity(name = "LayerDetails")
@Table(name = "gr_layer_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "LayerDetails")
@XmlRootElement(name = "LayerDetails")
public class LayerDetails {

    /** The id. */
    @Id
    @GeneratedValue
    @Column
    private long id;

    @Column(nullable=false)
    private String layerName;

//    @Column(length=5, nullable=false)
//    @Enumerated(EnumType.STRING)
//    private AccessType accessType;

    @Column
    private String defaultStyle;

    @Column(length=4000)
    private String cqlFilter;

	@Type(type = "org.hibernatespatial.GeometryUserType")
	@Column(name = "area")
	private MultiPolygon area;

//    @OneToOne(optional = false)
//    @ForeignKey(name="fk_detail_rule")
//    private Rule rule;

    /** Custom properties associated to the Layer */
    @org.hibernate.annotations.CollectionOfElements
    @JoinTable( name = "gr_layer_custom_props",
                joinColumns = @JoinColumn(name = "layerperm_id"))
    @org.hibernate.annotations.MapKey(columns =@Column(name = "propkey"))
    @Column(name = "propvalue")
    @ForeignKey(name="fk_custom_layer")
    private Map<String, String> customProps = new HashMap<String, String>();


    @XmlJavaTypeAdapter(MultiPolygonAdapter.class)
    public MultiPolygon getArea() {
        return area;
    }

    public void setArea(MultiPolygon area) {
        this.area = area;
    }

//    public AccessType getAccessType() {
//        return accessType;
//    }
//
//    public void setAccessType(AccessType accessType) {
//        this.accessType = accessType;
//    }

    public String getCqlFilter() {
        return cqlFilter;
    }

    public void setCqlFilter(String cqlFilter) {
        this.cqlFilter = cqlFilter;
    }

    public String getDefaultStyle() {
        return defaultStyle;
    }

    public void setDefaultStyle(String defaultStyle) {
        this.defaultStyle = defaultStyle;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

//    public WorkspacePermission getWorkspacePermission() {
//        return workspacePermission;
//    }
//
//    public void setWorkspacePermission(WorkspacePermission workspacePermission) {
//        this.workspacePermission = workspacePermission;
//    }

    public Map<String, String> getCustomProps() {
        return customProps;
    }

    public void setCustomProps(Map<String, String> customProps) {
        this.customProps = customProps;
    }

//    public Rule getRule() {
//        return rule;
//    }
//
//    public void setRule(Rule rule) {
//        this.rule = rule;
//    }

}
