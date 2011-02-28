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

package it.geosolutions.georepo.services.webgis;

import it.geosolutions.georepo.core.model.GSInstance;
import it.geosolutions.georepo.core.model.LayerDetails;
import it.geosolutions.georepo.core.model.Profile;
import it.geosolutions.georepo.core.model.Rule;
import it.geosolutions.georepo.core.model.RuleLimits;
import it.geosolutions.georepo.services.InstanceAdminService;
import it.geosolutions.georepo.services.RuleAdminService;
import it.geosolutions.georepo.services.dto.RuleFilter;
import it.geosolutions.georepo.services.dto.ShortRule;
import it.geosolutions.georepo.services.exception.NotFoundWebEx;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;
import it.geosolutions.georepo.services.webgis.model.TOCLayer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class FakeServices {

//    private Map<Long, ShortRule> data = new HashMap<Long, ShortRule>();
    private final static Map<Long, Rule> staticRules = new LinkedHashMap<Long, Rule>();
    private final static Map<Long, LayerDetails> staticDetails = new LinkedHashMap<Long, LayerDetails>();
    private final static Map<Long, GSInstance> staticInstances = new LinkedHashMap<Long, GSInstance>();

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
            staticInstances.put(i1.getId(), i1);

            createRule(i1, "layer901", "Gruppo1");
            createRule(i1, "layer902", "Gruppo1");
            createRule(i1, "layer903", "Gruppo2");
        }

        {
            GSInstance i1 = new GSInstance();
            i1.setId(icnt++);
            i1.setName("i2");
            i1.setBaseURL("http://i2");
            staticInstances.put(i1.getId(), i1);

            createRule(i1, "layer904", "Gruppo3");
            createRule(i1, "layer905", "Gruppo3");
            createRule(i1, "layer906", "Gruppo2"); // should be diffrent from grp in previous instnance with same name

            createRule(i1, "new layer not yet edited and forgotted somewhere", null);

            createRule(i1, "mappa01", null, "Mappa");

            createRule(i1, "orto01", null, "Ortofoto");
            createRule(i1, "orto02", null, "Ortofoto");

            createRule(i1, "ibrido01", null, "Ibrido");
            createRule(i1, "ibrido02", null, "Ibrido");
            createRule(i1, "ibrido03", null, "Ibrido");
        }
    }

    public InstanceAdminService getInstanceAdminService() {
        return new FakeInstanceAdminService();
    }

    public RuleAdminService getRuleAdminService() {
        return new FakeRuleAdminService();
    }

    private static long ruleCnt= 100;

    private static Rule createRule(GSInstance gsi, String layerName, String group) {
        return createRule(gsi, layerName, group, null);
    }
    
    private static Rule createRule(GSInstance gsi, String layerName, String group, String bgGroup) {
        Rule rule = new Rule();
        rule.setId(ruleCnt);
        rule.setPriority(ruleCnt++);
        rule.setInstance(gsi);
        rule.setLayer(layerName);

        staticRules.put(rule.getId(), rule);

        LayerDetails details = new LayerDetails();
        details.setCqlFilterRead("CQL_"+layerName);
        details.getCustomProps().put(TOCLayer.TOCProps.format.name(), "image/png");
        details.getCustomProps().put(TOCLayer.TOCProps.baseLayer.name(), "false");

        if(group != null)
            details.getCustomProps().put(TOCLayer.TOCProps.groupName.name(), group);

        if(bgGroup != null)
            details.getCustomProps().put(TOCLayer.TOCProps.bgGroup.name(), bgGroup);

        details.setRule(rule);

        staticDetails.put(rule.getId(), details);

        return rule;
    }


    public class FakeRuleAdminService implements  RuleAdminService {

        @Override
        public List<ShortRule> getList(String userId, String profileId, String instanceId, String service, String request, String workspace, String layer, Integer page, Integer entries) {
            List<ShortRule> ret = new ArrayList<ShortRule>();
            for (Rule rule : staticRules.values()) {
                ret.add(new ShortRule(rule));
            }
            return ret;
        }

        @Override
        public Map<String, String> getDetailsProps(Long ruleId) {
            Rule rule = staticRules.get(ruleId);
            if(rule == null)
                throw new NotFoundWebEx("rule not found: " + ruleId);
            LayerDetails details = staticDetails.get(ruleId);
            if(details == null)
                throw new NotFoundWebEx("details not found: " + ruleId);
            return details.getCustomProps();
        }

        @Override
        public void setDetailsProps(Long ruleId, Map<String, String> props) {
            Rule rule = staticRules.get(ruleId);
            if(rule == null)
                throw new NotFoundWebEx("rule not found: " + ruleId);
            LayerDetails details = staticDetails.get(ruleId);
            if(details == null)
                throw new NotFoundWebEx("details not found: " + ruleId);
            details.setCustomProps(props);
        }

        @Override
        public boolean delete(long id) throws ResourceNotFoundFault {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Rule get(long id) throws ResourceNotFoundFault {
            return staticRules.get(id);
        }

        @Override
        public List<ShortRule> getAll() {
            List<ShortRule> ret = new ArrayList<ShortRule>();
            for (Rule rule : staticRules.values()) {
                ret.add(new ShortRule(rule));
            }
            return ret;
        }

        @Override
        public long getCount(String userId, String profileId, String instanceId, String service, String request, String workspace, String layer) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public long getCountAll() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public long insert(Rule rule) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setDetails(Long ruleId, LayerDetails details) {
            throw new UnsupportedOperationException("Not supported yet.");
        }


        @Override
        public void setLimits(Long ruleId, RuleLimits limits) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public long update(Rule rule) throws ResourceNotFoundFault {
            throw new UnsupportedOperationException("Not supported yet.");
        }

//        @Override
//        public LayerDetails getDetails(long id) throws ResourceNotFoundFault {
//            throw new UnsupportedOperationException("Not supported yet.");
//        }

        @Override
        public List<ShortRule> getList(RuleFilter filter, Integer page, Integer entries) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public long getCount(RuleFilter filter) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int shift(long priorityStart, long offset) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void swap(long id1, long id2) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public class FakeInstanceAdminService implements  InstanceAdminService {

        @Override
        public long insert(GSInstance instance) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public long update(GSInstance instance) throws ResourceNotFoundFault {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean delete(long id) throws ResourceNotFoundFault {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public GSInstance get(long id) throws ResourceNotFoundFault {
            return staticInstances.get(id);
        }

        @Override
        public List<GSInstance> getAll() {
            return new ArrayList(staticInstances.values());
        }

        @Override
        public List<GSInstance> getList(String nameLike, int page, int entries) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public long getCount(String nameLike) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }


}
