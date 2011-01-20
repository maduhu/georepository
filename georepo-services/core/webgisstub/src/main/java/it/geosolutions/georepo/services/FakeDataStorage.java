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

import it.geosolutions.georepo.core.model.GSInstance;
import it.geosolutions.georepo.core.model.InstancePermission;
import it.geosolutions.georepo.core.model.LayerPermission;
import it.geosolutions.georepo.core.model.Profile;
import it.geosolutions.georepo.services.webgis.model.TOCLayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class FakeDataStorage {
    private static final Map<Integer, LayerPermission> layerStore = new HashMap<Integer, LayerPermission>();

    /**
     * The class hierachy is not completed yet; we're using this Map to relate
     * Layers to instances.
     */
    private static final Map<InstancePermission, List<LayerPermission>> domain = new LinkedHashMap<InstancePermission, List<LayerPermission>>();

    static {
        Profile basepr = new Profile();
        basepr.setId(1);
        basepr.setName("Base");
        basepr.setEnabled(Boolean.TRUE);

        int icnt = 100;
        int lcnt = 1000;

        {
            GSInstance i1 = new GSInstance();
            i1.setId(icnt++);
            i1.setName("i1");
            i1.setBaseURL("http://i1");

            InstancePermission ip1 = new InstancePermission();
            ip1.setId(i1.getId());
            ip1.setInstance(i1);
            ip1.setProfile(basepr);
            ip1.setEnabled(true);

            List<LayerPermission> ll1 = new ArrayList<LayerPermission>();
            ll1.add(createLayerPermission("layer901", lcnt++, "Gruppo 1"));
            ll1.add(createLayerPermission("layer902", lcnt++, "Gruppo 1"));
            ll1.add(createLayerPermission("layer903", lcnt++, "Gruppo 2"));

            domain.put(ip1, ll1);
        }
        {
            GSInstance i1 = new GSInstance();
            i1.setId(icnt++);
            i1.setName("i2");
            i1.setBaseURL("http://i2");

            InstancePermission ip1 = new InstancePermission();
            ip1.setId(i1.getId());
            ip1.setInstance(i1);
            ip1.setProfile(basepr);
            ip1.setEnabled(true);

            List<LayerPermission> ll1 = new ArrayList<LayerPermission>();
            ll1.add(createLayerPermission("layer904", lcnt++, "Gruppo 3"));
            ll1.add(createLayerPermission("layer905", lcnt++, "Gruppo 3"));
            ll1.add(createLayerPermission("layer906", lcnt++, "Gruppo 2")); // should be diffrent from grp in previous instnance with same name

            domain.put(ip1, ll1);
        }

    }

    private static LayerPermission createLayerPermission(String base, int id, String group) {
        LayerPermission lp = new LayerPermission();
        lp.setId(id);
        lp.setLayerName("name_"+ base);
        lp.setCqlFilter("CQL_"+base);

        lp.getCustomProps().put(TOCLayer.TOCProps.format.name(), "image/png");
        lp.getCustomProps().put(TOCLayer.TOCProps.baseLayer.name(), "false");
        lp.getCustomProps().put(TOCLayer.TOCProps.groupName.name(), group);

        layerStore.put(id, lp);
        return lp;
    }


    public Set<InstancePermission> getInstancePermissions(String profile) {
        return domain.keySet();
    }

    public List<LayerPermission> getLayerPermissions(InstancePermission instancePermission) {
        return domain.get(instancePermission);
    }

    public LayerPermission getLayerPermission(Long id) {
        for (InstancePermission instancePermission : domain.keySet()) {
            for (LayerPermission layerPermission : domain.get(instancePermission)) {
                if(layerPermission.getId() == id.longValue()) {
                    return layerPermission;
                }
            }
        }
        return null;
    }

}
