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
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */

@XmlRootElement(name="group")
public class TOCGroup {

    private String title;
    private String url;
    private boolean visible = true;
    private List<TOCLayer> layerList = new ArrayList<TOCLayer>();

    @XmlElement(name="layer")
    public List<TOCLayer> getLayerList() {
        // reorder list on output
        SortedSet<TOCLayer> set = new TreeSet<TOCLayer>(new TOCLayer.TOCLayerComparator());
        set.addAll(layerList);
        List<TOCLayer> ret = new ArrayList<TOCLayer>(set);
        return Collections.unmodifiableList(ret);
    }

    public void setLayerList(List<TOCLayer> layerList) {
        this.layerList = layerList;
    }

    public void addLayer(TOCLayer layer) {
        layerList.add(layer);
    }

    @XmlAttribute
    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    @XmlAttribute
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlAttribute
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "Group{" + "title=" + title + " url=" + url + " layerList=" + layerList + '}';
    }
}

