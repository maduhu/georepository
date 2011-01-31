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

import it.geosolutions.georepo.core.model.LayerAttribute;
import it.geosolutions.georepo.core.model.enums.GrantType;

import java.util.Map;
import java.util.Set;

import com.vividsolutions.jts.geom.Geometry;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class AccessInfo {
    
    /**
     * Default "allow everything" AccessInfo
     */
    public static final AccessInfo ALLOW_ALL = new AccessInfo() {
        {
            setGrant(GrantType.ALLOW);
        }
    };
    
    /**
     * Default "deny everything" AccessInfo
     */
    public static final AccessInfo DENY_ALL = new AccessInfo() {
        {
            setGrant(GrantType.DENY);
        }
    };

    /**
     * The resulting grant: allow or deny.
     */
    private GrantType grant = GrantType.DENY;

    private Geometry area;

    private String defaultStyle;

    private String cqlFilterRead;
    private String cqlFilterWrite;

    private Set<LayerAttribute> attributes;
    private Map<String, String> customProps;
    private Set<String> allowedStyles;

    public Geometry getArea() {
        return area;
    }

    public void setArea(Geometry area) {
        this.area = area;
    }

    public Set<LayerAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<LayerAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getCqlFilterRead() {
        return cqlFilterRead;
    }

    public void setCqlFilterRead(String cqlFilterRead) {
        this.cqlFilterRead = cqlFilterRead;
    }

    public String getCqlFilterWrite() {
        return cqlFilterWrite;
    }

    public void setCqlFilterWrite(String cqlFilterWrite) {
        this.cqlFilterWrite = cqlFilterWrite;
    }

    public Map<String, String> getCustomProps() {
        return customProps;
    }

    public void setCustomProps(Map<String, String> customProps) {
        this.customProps = customProps;
    }

    public String getDefaultStyle() {
        return defaultStyle;
    }

    public void setDefaultStyle(String defaultStyle) {
        this.defaultStyle = defaultStyle;
    }

    public Set<String> getAllowedStyles() {
        return allowedStyles;
    }

    public void setAllowedStyles(Set<String> allowedStyles) {
        this.allowedStyles = allowedStyles;
    }

    public GrantType getGrant() {
        return grant;
    }

    public void setGrant(GrantType grant) {
        if(grant != GrantType.ALLOW && grant != GrantType.DENY)
            throw new IllegalArgumentException("Bad grant type " + grant);
        this.grant = grant;
    }
}
