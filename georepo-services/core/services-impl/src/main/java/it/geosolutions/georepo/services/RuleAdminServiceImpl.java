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

import it.geosolutions.georepo.core.model.LayerDetails;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.trg.search.Search;
import it.geosolutions.georepo.core.dao.LayerDetailsDAO;
import it.geosolutions.georepo.core.dao.RuleDAO;
import it.geosolutions.georepo.core.dao.RuleLimitsDAO;
import it.geosolutions.georepo.core.model.Rule;
import it.geosolutions.georepo.core.model.RuleLimits;
import it.geosolutions.georepo.core.model.enums.GrantType;
import it.geosolutions.georepo.services.dto.ShortRule;
import it.geosolutions.georepo.services.exception.BadRequestWebEx;
import it.geosolutions.georepo.services.exception.NotFoundWebEx;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * <B>Note:</B> <TT>service</TT> and <TT>request</TT> params are usually set by
 * the client, and by OGC specs they are not case sensitive, so we're going to
 * turn all of them uppercase. See also {@link RuleReaderServiceImpl}.
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RuleAdminServiceImpl implements RuleAdminService {

    private final static Logger LOGGER = Logger.getLogger(RuleAdminServiceImpl.class);

    private RuleDAO ruleDAO;
    private RuleLimitsDAO limitsDAO;
    private LayerDetailsDAO detailsDAO;

    // =========================================================================
    // Basic operations
    // =========================================================================

    @Override
    public long insert(Rule rule) {
        sanitizeFields(rule);
        ruleDAO.persist(rule);
        return rule.getId();
    }

    @Override
    public long update(Rule rule) throws ResourceNotFoundFault {
        Rule orig = ruleDAO.find(rule.getId());
        if (orig == null) {
            throw new ResourceNotFoundFault("Rule not found", rule.getId());
        }

        sanitizeFields(rule); 
        ruleDAO.merge(rule);
        return orig.getId();
    }

    /**
     * <TT>service</TT> and <TT>request</TT> params are usually set by
     * the client, and by OGC specs they are not case sensitive, so we're going to
     * turn all of them uppercase. See also {@link RuleReaderServiceImpl}.
     */
    protected void sanitizeFields(Rule rule) {
        // read class' javadoc
        if (rule.getService() != null) {
            rule.setService(rule.getService().toUpperCase());
        }
        if (rule.getRequest() != null) {
            rule.setRequest(rule.getRequest().toUpperCase());
        }
    }

    @Override
    public Rule get(long id) throws ResourceNotFoundFault {
        Rule rule = ruleDAO.find(id);

        if (rule == null) {
            throw new ResourceNotFoundFault("Rule not found", id);
        }

        return rule;
    }

    @Override
    public boolean delete(long id) throws ResourceNotFoundFault {
        Rule rule = ruleDAO.find(id);

        if (rule == null) {
            throw new ResourceNotFoundFault("Rule not found", id);
        }

        // data on ancillary tables should be deleted by cascading
        return ruleDAO.remove(rule);
    }

    @Override
    public List<ShortRule> getAll() {
        List<Rule> found = ruleDAO.findAll();
        return convertToShortList(found);
    }

    @Override
    public List<ShortRule> getList(String userId, String profileId, String instanceId,
            String service, String request,
            String workspace, String layer,
            Integer page, Integer entries) {

        if( (page != null && entries == null) || (page ==null && entries != null)) {
            throw new BadRequestWebEx("Page and entries params should be declared together.");
        }

        Search searchCriteria = buildRuleSearch(userId, profileId, instanceId, service, request, workspace, layer);
        searchCriteria.addSortAsc("priority");

        if(entries != null) {
            searchCriteria.setMaxResults(entries);
            searchCriteria.setPage(page);
        }

        List<Rule> found = ruleDAO.search(searchCriteria);
        return convertToShortList(found);
    }

    @Override
    public long getCountAll() {
        return getCount("*", "*", "*", "*", "*", "*", "*");
    }

    @Override
    public long getCount(String userId, String profileId, String instanceId, String service, String request, String workspace, String layer) {
        Search searchCriteria = buildRuleSearch(userId, profileId, instanceId, service, request, workspace, layer);
        return ruleDAO.count(searchCriteria);
    }

    // =========================================================================
    // Search stuff

    private Search buildRuleSearch(String userId, String profileId, String instanceId,
                                String service, String request,
                                String workspace, String layer) {
        Search searchCriteria = new Search(Rule.class);

        addLongSearchCriteria(searchCriteria, userId,     "gsuser");
        addLongSearchCriteria(searchCriteria, profileId,  "profile");
        addLongSearchCriteria(searchCriteria, instanceId, "instance");

        addStringSearchCriteria(searchCriteria, service==null?null:service.toUpperCase(),   "service");  // read class' javadoc
        addStringSearchCriteria(searchCriteria, request==null?null:request.toUpperCase(),   "request");  // read class' javadoc
        addStringSearchCriteria(searchCriteria, workspace,  "workspace");
        addStringSearchCriteria(searchCriteria, layer,      "layer");

        return searchCriteria;
    }

    /**
     * Add criteria for <B>searching</B>:
     * <UL>
     * <LI>a null id will only match a null field</LI>
     * <LI>an id=="*" will match everything, so no filter condition is needed</LI>
     * <LI>a valid numeric id will only match that numeric value</LI>
     * </UL>
     * We're dealing with IDs here, so <U>we'll suppose that the related object id field is called "id"</U>.
     */
    protected void addLongSearchCriteria(Search searchCriteria, String id, String fieldName) throws BadRequestWebEx {
        if (id == null) {
            searchCriteria.addFilterNull(fieldName);
        } else if (id.equals("*")) {
            // do not add any filter
        } else {
            try {
                searchCriteria.addFilterEqual(fieldName + ".id", Long.valueOf(id));
            } catch (NumberFormatException ex) {
                throw new BadRequestWebEx("Bad "+fieldName+" '" + id + "'");
            }
        }
    }

    /**
     * Add criteria for <B>searching</B>:
     * <LI>a null value will only match a null field</LI>
     * <LI>a value=="*" will match everything, so no filter condition is needed</LI>
     * <LI>any other value will only match that value</LI>
     */
    protected void addStringSearchCriteria(Search searchCriteria, String value, String fieldName) throws BadRequestWebEx {
        if (value == null) {
            searchCriteria.addFilterNull(fieldName);
        } else if (value.equals("*")) {
            // do not add any filter
        } else {
            searchCriteria.addFilterEqual(fieldName, value);
        }
    }

    // =========================================================================
    // Limits
    // =========================================================================

    @Override
    public void setLimits(Long ruleId, RuleLimits limits) {
        Rule rule = ruleDAO.find(ruleId);
        if(rule == null)
            throw new NotFoundWebEx("Rule not found");

        if(rule.getAccess() != GrantType.LIMIT && limits != null)
            throw new BadRequestWebEx("Rule is not of LIMIT type");

        // remove old limits if any
        if(rule.getRuleLimits() != null) {
            limitsDAO.remove(rule.getRuleLimits());
        }

        if(limits != null) {
            limits.setId(ruleId);
            limits.setRule(rule);
            limitsDAO.persist(limits);
        } else {
            LOGGER.info("Removing limits for " + rule);
            // TODO: remove limits (already removed above?)
        }
    }

    // =========================================================================
    // Details
    // =========================================================================

    @Override
    public LayerDetails getDetails(long id) throws ResourceNotFoundFault {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    @Override
    public void setDetails(Long ruleId, LayerDetails details) {
        Rule rule = ruleDAO.find(ruleId);
        if(rule == null)
            throw new NotFoundWebEx("Rule not found");

        if(rule.getLayer() == null && details != null)
            throw new BadRequestWebEx("Rule does not refer to a fixed layer");

        if(rule.getAccess() != GrantType.ALLOW && details != null)
            throw new BadRequestWebEx("Rule is not of ALLOW type");

        final Map<String, String> oldProps;

        // remove old details if any
        if(rule.getLayerDetails() != null) {
            oldProps = detailsDAO.getCustomProps(ruleId); 
            detailsDAO.remove(rule.getLayerDetails());
        } else
            oldProps = null;

        rule = ruleDAO.find(ruleId);
        if(rule.getLayerDetails() != null)
            throw new IllegalStateException("LayerDetails should be null");

        if(details != null) {
            details.setRule(rule);
            detailsDAO.persist(details);
            // restore old properties
            if(oldProps != null) {
                LOGGER.info("Restoring " + oldProps.size() + " props from older LayerDetails (id:"+ruleId+")");
                //cannot reuse the same Map returned by Hibernate, since it is detached now.
                Map<String, String> newProps = new HashMap<String, String>();
                newProps.putAll(oldProps);
                detailsDAO.setCustomProps(ruleId, newProps);
            }
        } else {
            LOGGER.info("Removing details for " + rule);
        }
    }

    @Override
    public void setDetailsProps(Long ruleId, Map<String, String> props) {
        Rule rule = ruleDAO.find(ruleId);
        if(rule == null)
            throw new NotFoundWebEx("Rule not found");

        if(rule.getLayerDetails() == null) {
            throw new NotFoundWebEx("Rule has no details associated");
        }

        detailsDAO.setCustomProps(ruleId, props);
    }

    @Override
    public Map<String, String> getDetailsProps(Long ruleId) {
        Rule rule = ruleDAO.find(ruleId);
        if(rule == null)
            throw new NotFoundWebEx("Rule not found");

        if(rule.getLayerDetails() == null) {
            throw new NotFoundWebEx("Rule has no details associated");
        }
        return detailsDAO.getCustomProps(ruleId);
    }


    // ==========================================================================

    private List<ShortRule> convertToShortList(List<Rule> list) {
        List<ShortRule> shortList = new ArrayList<ShortRule>(list.size());
        for (Rule rule : list) {
            shortList.add(new ShortRule(rule));
        }

        return shortList;
    }

    // ==========================================================================
    
    public void setRuleDAO(RuleDAO ruleDAO) {
        this.ruleDAO = ruleDAO;
    }

    public void setRuleLimitsDAO(RuleLimitsDAO ruleLimitsDAO) {
        this.limitsDAO = ruleLimitsDAO;
    }

    public void setLayerDetailsDAO(LayerDetailsDAO detailsDAO) {
        this.detailsDAO = detailsDAO;
    }
}
