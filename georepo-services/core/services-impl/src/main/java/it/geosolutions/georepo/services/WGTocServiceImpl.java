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

package it.geosolutions.georepo.services;

import it.geosolutions.georepo.services.exception.IllegalParameterFault;
import it.geosolutions.georepo.services.webgis.model.TOC;
import it.geosolutions.georepo.services.webgis.model.WebGisProfile;
import it.geosolutions.georepo.services.webgis.WebGisTOCService;
import it.geosolutions.georepo.services.webgis.model.TOC.Server;
import it.geosolutions.georepo.services.webgis.model.TOCLayer;
import org.apache.log4j.Logger;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class WGTocServiceImpl implements WebGisTOCService {
    private final static Logger LOGGER = Logger.getLogger(WGTocServiceImpl.class);

    @Override
    public TOC getTOC(WebGisProfile profile) throws IllegalParameterFault {
        TOC toc = new TOC();

        Server s;
        s = new TOC.Server();
        s.setName("server1 for profile " + profile);
        s.setUrl("http://server1");
        s.getLayerList().add(createLayer("1_1"));
        s.getLayerList().add(createLayer("1_2"));
        s.getLayerList().add(createLayer("1_3"));
        toc.getServerList().add(s);

        s = new TOC.Server();
        s.setName("server2 for profile " + profile);
        s.setUrl("http://server2");
        s.getLayerList().add(createLayer("2_1"));
        toc.getServerList().add(s);

        return toc;
    }

    private static TOCLayer createLayer(String base) {
        TOCLayer l = new TOCLayer();
        l.setName("name_"+ base);
        l.setTitle("title_"+base);
        l.setAbs("abstract_"+base);
        l.getProperties().put("keyA"+base, "valA"+base);
        l.getProperties().put("keyB"+base, "valB"+base);
        return l;
    }

    @Override
    public String getProperty(int layerId, String propertyName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String setProperty(int layerId, String propertyName, String propertyValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

//    @Override
//    public WebGisProperty getProperty(String propertyName) {
//        WebGisProperty property = new WebGisProperty();
//        property.setPropertyName(propertyName);
//        property.setProfile(WebGisProfile.Base);
//        property.setValue("42");
//        return property;
//    }
//
//    @Override
//    public List<WebGisProperty> getProfileProperties(WebGisProfile profile) throws IllegalParameterFault {
//        LOGGER.warn("Profile is " + profile);
//        System.out.println("Profile is " + profile);
//        if(profile == null)
//            throw new IllegalParameterFault("Bad profile");
//
//        List<WebGisProperty> ret = new ArrayList<WebGisProperty>();
//        ret.add(getProperty("test1"));
//        ret.add(getProperty("test2"));
//        return ret;
////        return (WebGisProperty[])ret.toArray();
//    }

}
