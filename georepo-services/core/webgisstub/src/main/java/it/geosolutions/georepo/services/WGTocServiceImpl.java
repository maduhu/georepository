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
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class WGTocServiceImpl implements WebGisTOCService {
    private final static Logger LOGGER = Logger.getLogger(WGTocServiceImpl.class);

    private Map<Integer, TOCLayer> layerStore = new HashMap<Integer, TOCLayer>();

    @Override
    public TOC getTOC(WebGisProfile profile) throws IllegalParameterFault {
        TOC toc = new TOC();

        int baseLayerId = profile == WebGisProfile.Base ? 100 :
                          profile == WebGisProfile.Analisi ? 200 :
                          profile == WebGisProfile.Avanzato ? 300 : 0;

        Server s;
        s = new TOC.Server();
        s.setName("server1 for profile " + profile);
        s.setUrl("http://server1");
        s.getLayerList().add(createLayer("1_1", baseLayerId + 1));
        s.getLayerList().add(createLayer("1_2", baseLayerId + 2));
        s.getLayerList().add(createLayer("1_3", baseLayerId + 3));
        toc.getServerList().add(s);

        s = new TOC.Server();
        s.setName("server2 for profile " + profile);
        s.setUrl("http://server2");
        s.getLayerList().add(createLayer("2_1", baseLayerId + 11));
        toc.getServerList().add(s);

        return toc;
    }

    private TOCLayer createLayer(String base, int id) {
        TOCLayer l = new TOCLayer();
        l.setName("name_"+ base);
        l.setTitle("title_"+base);
        l.setAbs("abstract_"+base);
        l.getProperties().put("keyA"+base, "valA"+base);
        l.getProperties().put("keyB"+base, "valB"+base);
        l.getProperties().put("format", "image/png");
        l.getProperties().put("baseLayer", "false");
        l.setGeorepoLayerId(id);

        layerStore.put(id, l);
        return l;
    }

    @Override
    public String setProperty(int layerId, String propertyName, String propertyValue) {

        // associate a profile for testing
        WebGisProfile profile;
        if(layerId >= 300)
            profile = WebGisProfile.Avanzato;
        else if(layerId >= 200)
            profile = WebGisProfile.Analisi;
        else if(layerId >= 100)
            profile = WebGisProfile.Base;
        else
            throw new IllegalArgumentException("Bad layerid");

        TOCLayer l = layerStore.get(layerId);
        if(l == null)
            throw new IllegalArgumentException("Layer "+layerId+" not found");

        if(l.getProperties().containsKey(propertyName)) {
            LOGGER.info("Setting property " +propertyName + " to " + propertyValue + " for layer " + layerId + " in profile " + profile);
            String oldValue = l.getProperties().put(propertyName, propertyValue);
            return oldValue;
        } else {
            LOGGER.info("Will not set property " +propertyName + " to " + propertyValue + " for layer " + layerId + ": property not found");
            return "ERROR: property " + propertyName + " not found"; // need a better way to return error status: http code?
        }
    }

    @Override
    public String getProperty(int layerId, String propertyName) {
        TOCLayer l = layerStore.get(layerId);
        if(l == null)
            throw new IllegalArgumentException("Layer "+layerId+" not found");

        LOGGER.info("Getting property " +propertyName );
        return l.getProperties().get(propertyName);
    }
    
}
