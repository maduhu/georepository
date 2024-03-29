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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.namespace.QName;
import org.apache.log4j.Logger;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
@XmlRootElement(name="layer")
public class TOCLayer {
    private final static Logger LOGGER = Logger.getLogger(TOCLayer.class);

    public final static int POSITION_DEFAULT = Integer.MAX_VALUE;

    public enum TOCProps {
        groupName("NOGROUP"),
        format("image/png"),
        baseLayer("false"), // fisso
        visible("false"),
        filtro1(""),        // fisso
        singleTile("false"),
        isInternal("false"),// fisso
        isQueryable("false"),
        infoPossible("false"),
        typeLayer("0"),
        minScale(""),
        maxScale(""),
        bgGroup(null),
        position(Integer.toString(POSITION_DEFAULT))
        ;

        private String defaultValue;

        private TOCProps(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public String getDefault() {
            return defaultValue;
        }
    }

//    <layer name="RealItaly_tiles" title="RealItaly" abstract="RealItaly"
//       srs="EPSG:4326"
//       format="image/png"
//       minX="6.62684258" minY="35.49180286" maxX="18.52055698" maxY="47.09192081"
//       baseLayer="false" visible="false"
//       filtro1="" singleTile="false" isInternal="false" isQueryable="false"
//       infoPossible="false" typeLayer="0">

    private long georepoId;
    
    private String name;
    private String title;
    private String abs;
    private String srs;
    private double minX, minY, maxX, maxY;

    private Map<String,String> properties = new HashMap<String, String>();

    private List<TOCAttrib> attributes = new ArrayList<TOCAttrib>();

    public TOCLayer() {
        for (TOCProps tocProp : TOCProps.values()) {
            if( null != tocProp.getDefault())
                properties.put(tocProp.name(), tocProp.getDefault());
        }
    }

    @XmlAttribute(name="abstract")
    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlAttribute
    public String getSrs() {
        return srs;
    }

    public void setSrs(String srs) {
        this.srs = srs;
    }

    @XmlAttribute
    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    @XmlAttribute
    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    @XmlAttribute
    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    @XmlAttribute
    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    @XmlAttribute
    public long getGeorepoId() {
        return georepoId;
    }

    public void setGeorepoId(long georepoId) {
        this.georepoId = georepoId;
    }

    @XmlTransient
    public int getPosition() {
        String spos = properties.get(TOCProps.position.name());
        if ( spos != null ) {
            try {
                return Integer.parseInt(spos);
            } catch (NumberFormatException ex) {
                LOGGER.warn("Could not parse position attr for TOCLayer "+ georepoId + ":" + name, ex);
                return POSITION_DEFAULT;
            }
        } else {
            LOGGER.info("No position set for TOCLayer "+ georepoId + ":" + name);
            return POSITION_DEFAULT;
        }
    }

    @XmlTransient // will be encoded by getAny()
    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @XmlAnyAttribute
    public Map<QName,Object> getAny(){
        Map<QName,Object> any = new HashMap<QName, Object>();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            any.put(new QName(entry.getKey()), entry.getValue());
        }
        return any;
    }

    @XmlElementWrapper(name="attributes")
    @XmlElement(name="attribute")
    public List<TOCAttrib> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<TOCAttrib> attributes) {
        this.attributes = attributes;
    }

    public static class TOCLayerComparator implements Comparator<TOCLayer> {
        private final static Logger LOGGER = Logger.getLogger(TOCLayerComparator.class);

        @Override
        public int compare(TOCLayer o1, TOCLayer o2) {
            int delta = o1.getPosition() - o2.getPosition();
            if (delta != 0)
                return delta;
            LOGGER.warn("Layers have same position: "
                    + o1.getGeorepoId()+':'+o1.getName() + ' '
                    + o2.getGeorepoId()+':'+o2.getName());
            delta = o1.getName().compareTo(o2.getName());
            if (delta != 0)
                return delta;
            LOGGER.warn("Layers have same name: "
                    + o1.getGeorepoId()+':'+o1.getName() + ' '
                    + o2.getGeorepoId()+':'+o2.getName());

            return (int)(o1.getGeorepoId() - o2.getGeorepoId());
        }
    }
 }
