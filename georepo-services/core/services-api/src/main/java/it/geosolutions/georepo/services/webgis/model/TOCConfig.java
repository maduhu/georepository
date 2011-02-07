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

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
@XmlRootElement(name="configurations")
public class TOCConfig {
//                <server name="Foto Aeree" url="http://maps.telespazio.it/snam/snam2-wms">
//                        <layer name="RealItaly_tiles" title="RealItaly" abstract="RealItaly"
//                                srs="EPSG:4326" format="image/png" minX="6.62684258" minY="35.49180286"
//                                maxX="18.52055698" maxY="47.09192081" baseLayer="false" visible="false"
//                                filtro1="" singleTile="false" isInternal="false" isQueryable="false"
//                                infoPossible="false" typeLayer="0">
//                        </layer>
//                </server>

    private TOC toc;

    public TOCConfig() {
    }

    public TOCConfig(TOC toc) {
        this.toc = toc;
    }
    
    public TOC getToc() {
        return toc;
    }

    public void setToc(TOC toc) {
        this.toc = toc;
    }

}
