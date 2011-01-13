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

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.namespace.QName;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
@XmlRootElement(name="layer")
public class TOCLayer {

//    <layer name="RealItaly_tiles" title="RealItaly" abstract="RealItaly"
//       srs="EPSG:4326"
//       format="image/png"
//       minX="6.62684258" minY="35.49180286" maxX="18.52055698" maxY="47.09192081"
//       baseLayer="false" visible="false"
//       filtro1="" singleTile="false" isInternal="false" isQueryable="false"
//       infoPossible="false" typeLayer="0">

    private int georepoLayerId;
    
    private String name;
    private String title;
    private String abs;
    private String srs;
    private double minX, minY, maxX, maxY;

    private Map<String,String> properties = new HashMap<String, String>();

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
    public int getGeorepoLayerId() {
        return georepoLayerId;
    }

    public void setGeorepoLayerId(int georepoLayerId) {
        this.georepoLayerId = georepoLayerId;
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

}

