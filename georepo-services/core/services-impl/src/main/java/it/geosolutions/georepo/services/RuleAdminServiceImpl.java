/*
 *  Copyright (C) 2007 - 2010 GeoSolutions S.A.S.
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

import com.trg.search.Filter;
import it.geosolutions.georepo.core.model.LayerDetails;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.trg.search.Search;
import it.geosolutions.georepo.core.dao.RuleDAO;
import it.geosolutions.georepo.core.model.Rule;
import it.geosolutions.georepo.services.dto.ShortRule;
import it.geosolutions.georepo.services.exception.BadRequestWebEx;

/**
 * 
 * @author ETj (etj at geo-solutions.it)
 */
public class RuleAdminServiceImpl implements RuleAdminService {

    private final static Logger LOGGER = Logger.getLogger(RuleAdminServiceImpl.class);

    private RuleDAO ruleDAO;

    // ==========================================================================
    @Override
    public long insert(Rule rule) {
        ruleDAO.persist(rule);
        return rule.getId();
    }

    @Override
    public long update(Rule rule) throws ResourceNotFoundFault {
        Rule orig = ruleDAO.find(rule.getId());
        if (orig == null) {
            throw new ResourceNotFoundFault("Rule not found", rule.getId());
        }

        ruleDAO.merge(rule);
        return orig.getId();
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

        Search searchCriteria = new Search(Rule.class);
        searchCriteria.addSortAsc("priority");

        addLongSearchCriteria(searchCriteria, userId,       "gsuser");
        addLongSearchCriteria(searchCriteria, profileId,    "profile");
        addLongSearchCriteria(searchCriteria, instanceId,   "instance");

        addStringSearchCriteria(searchCriteria, service,    "service");
        addStringSearchCriteria(searchCriteria, request,    "request");
        addStringSearchCriteria(searchCriteria, workspace,  "workspace");
        addStringSearchCriteria(searchCriteria, layer,      "layer");

        if(entries != null) {
            searchCriteria.setMaxResults(entries);
            searchCriteria.setPage(page);
        }

        List<Rule> found = ruleDAO.search(searchCriteria);
        return convertToShortList(found);
    }

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

    protected void addStringSearchCriteria(Search searchCriteria, String value, String fieldName) throws BadRequestWebEx {
        if (value == null) {
            searchCriteria.addFilterNull(fieldName);
        } else if (value.equals("*")) {
            // do not add any filter
        } else {
            searchCriteria.addFilterEqual(fieldName, value);
        }
    }

    @Override
    public long getCount(String userId, String profileId, String instanceId, String service, String request, String workspace, String layer) {
        Search searchCriteria = new Search(Rule.class);

        addLongSearchCriteria(searchCriteria, userId,       "gsuser");
        addLongSearchCriteria(searchCriteria, profileId,    "profile");
        addLongSearchCriteria(searchCriteria, instanceId,   "instance");

        addStringSearchCriteria(searchCriteria, service,    "service");
        addStringSearchCriteria(searchCriteria, request,    "request");
        addStringSearchCriteria(searchCriteria, workspace,  "workspace");
        addStringSearchCriteria(searchCriteria, layer,      "layer");

        return ruleDAO.count(searchCriteria);
    }

    @Override
    public List<ShortRule> getMatchingRules(Long userId, Long profileId, Long instanceId, String service, String request, String workspace, String layer) {
        Search searchCriteria = new Search(Rule.class);
        searchCriteria.addSortAsc("priority");

        addIdMatchSearchCriteria(searchCriteria, userId,       "gsuser");
        addIdMatchSearchCriteria(searchCriteria, profileId,    "profile");
        addIdMatchSearchCriteria(searchCriteria, instanceId,   "instance");

        addStringMatchSearchCriteria(searchCriteria, service,    "service");
        addStringMatchSearchCriteria(searchCriteria, request,    "request");
        addStringMatchSearchCriteria(searchCriteria, workspace,  "workspace");
        addStringMatchSearchCriteria(searchCriteria, layer,      "layer");

        List<Rule> found = ruleDAO.search(searchCriteria);
        return convertToShortList(found);

    }

    protected void addIdMatchSearchCriteria(Search searchCriteria, Long id, String fieldName) throws BadRequestWebEx {
        if (id == null)
            throw new BadRequestWebEx(fieldName + " is null");

        searchCriteria.addFilterOr(
                Filter.isNull(fieldName),
                Filter.equal(fieldName, id));
    }

    protected void addStringMatchSearchCriteria(Search searchCriteria, String value, String fieldName) throws BadRequestWebEx {

        searchCriteria.addFilterOr(
                Filter.isNull(fieldName),
                Filter.equal(fieldName, value));
    }


    @Override
    public LayerDetails getDetails(long id) throws ResourceNotFoundFault {
        throw new UnsupportedOperationException("Not supported yet.");
    }

//    @Override
//    public long getCount(String nameLike) {
//        Search searchCriteria = new Search(GSUser.class);
//
//        if (nameLike != null) {
//            searchCriteria.addFilterILike("name", nameLike);
//        }
//
//        return ruleDAO.count(searchCriteria);
//    }

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

}
