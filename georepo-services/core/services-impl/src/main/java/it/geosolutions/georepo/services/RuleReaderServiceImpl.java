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

import com.trg.search.Filter;
import it.geosolutions.georepo.services.dto.AccessInfo;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.trg.search.Search;
import com.vividsolutions.jts.geom.Geometry;
import it.geosolutions.georepo.core.dao.LayerDetailsDAO;
import it.geosolutions.georepo.core.dao.RuleDAO;
import it.geosolutions.georepo.core.dao.RuleLimitsDAO;
import it.geosolutions.georepo.core.model.LayerDetails;
import it.geosolutions.georepo.core.model.Rule;
import it.geosolutions.georepo.core.model.RuleLimits;
import it.geosolutions.georepo.core.model.enums.GrantType;
import it.geosolutions.georepo.services.dto.ShortRule;
import it.geosolutions.georepo.services.exception.BadRequestWebEx;

/**
 * 
 * @author ETj (etj at geo-solutions.it)
 */
public class RuleReaderServiceImpl implements RuleReaderService {

    private final static Logger LOGGER = Logger.getLogger(RuleReaderServiceImpl.class);

    private RuleDAO ruleDAO;
    private RuleLimitsDAO limitsDAO;
    private LayerDetailsDAO detailsDAO;

    @Override
    public List<ShortRule> getMatchingRules(
                    String userName, String profileName, String instanceName,
                    String service, String request,
                    String workspace, String layer) {

        List<Rule> found = getRules(userName, profileName, instanceName, service, request, workspace, layer);
        return convertToShortList(found);
    }


    @Override
    public AccessInfo getAccessInfo(String userName, String profileName, String instanceName, String service, String request, String workspace, String layer) {

        List<Rule> found = getRules(userName, profileName, instanceName, service, request, workspace, layer);

        List<RuleLimits> limits = new ArrayList<RuleLimits>();

        for (Rule rule : found) {
            switch(rule.getAccess()) {
                case LIMIT:
                    
                   RuleLimits rl = rule.getRuleLimits();
                   if(rl != null) {
                       LOGGER.info("Collectiong limits: " + rl);
                       limits.add(rl);
                    } else
                       LOGGER.warn(rule + " has no associated limits");
                    break;

                case DENY:
                    AccessInfo accessInfo = new AccessInfo();
                    accessInfo.setGrant(GrantType.DENY);
                    return accessInfo;
                    
                case ALLOW:
                    return buildAllowAccessInfo(rule, limits);

                default:
                    throw new IllegalStateException("Unknown GrantType " + rule.getAccess());
            }
        }

        LOGGER.warn("No rule matching request u:"+userName
                + " p:"+profileName + " i:"+instanceName
                + " s:"+service + " r:"+request
                + " w:"+workspace + " l:"+layer);

        // Denying by default
        AccessInfo accessInfo = new AccessInfo();
        accessInfo.setGrant(GrantType.DENY);
        return accessInfo;
    }

    private AccessInfo buildAllowAccessInfo(Rule rule, List<RuleLimits> limits) {
        AccessInfo accessInfo = new AccessInfo();
        accessInfo.setGrant(GrantType.ALLOW);

        Geometry area = intersect(limits);
        
        LayerDetails details = rule.getLayerDetails();                
        if(details != null ) {
            area = intersect(area, details.getArea());

            accessInfo.setAttributes(details.getAttributes());
            accessInfo.setCqlFilterRead(details.getCqlFilterRead());
            accessInfo.setCqlFilterWrite(details.getCqlFilterWrite());
            accessInfo.setDefaultStyle(details.getDefaultStyle());
            accessInfo.setAllowedStyles(details.getAllowedStyles());

            accessInfo.setCustomProps(details.getCustomProps()); // uhm, gotta use dao?
        }

        accessInfo.setArea(area);

        return accessInfo;
    }

    private Geometry intersect(List<RuleLimits> limits) {
        Geometry g = null;
        for (RuleLimits limit : limits) {
            Geometry area = limit.getAllowedArea();
            if(area != null) {
                if( g == null) {
                    g = area;
                } else {
                    g = g.intersection(area);
                }
            }
        }
        return g;
    }

    private Geometry intersect(Geometry g1, Geometry g2) {
        if(g1!=null) {
            if(g2==null)
                return g1;
            else
                return g1.intersection(g2);
        } else {
            return g2;
        }
    }

    //==========================================================================
    
    protected List<Rule> getRules(String userName, String profileName, String instanceName, String service, String request, String workspace, String layer) throws BadRequestWebEx {
        Search searchCriteria = new Search(Rule.class);
        searchCriteria.addSortAsc("priority");

        addNameMatchCriteria(searchCriteria, userName, "gsuser");
        addNameMatchCriteria(searchCriteria, profileName, "profile");
        addNameMatchCriteria(searchCriteria, instanceName, "instance");

        addStringMatchCriteria(searchCriteria, service, "service");
        addStringMatchCriteria(searchCriteria, request, "request");
        addStringMatchCriteria(searchCriteria, workspace, "workspace");
        addStringMatchCriteria(searchCriteria, layer, "layer");

        List<Rule> found = ruleDAO.search(searchCriteria);
        return found;
    }

    /**
     * Add criteria for <B>matching</B> names:
     * <UL>
     * <LI><STRIKE>null names will not be accepted: that is: user, profile, instance are required (note you can trick this check by setting empty strings)</STRIKE>a null param will match everything</LI>
     * <LI>a valid string will match that specific value and any rules with that name set to null</LI>
     * </UL>
     * We're dealing with <TT><I>name</I></TT>s here, so <U>we'll suppose that the related object's name field is called "<TT>name</TT>"</U>.
     */
    protected void addNameMatchCriteria(Search searchCriteria, String name, String fieldName) throws BadRequestWebEx {
        if (name == null)
            return; // TODO: check desired behaviour
//            throw new BadRequestWebEx(fieldName + " is null");

        searchCriteria.addFilterOr(
                Filter.isNull(fieldName),
                Filter.equal(fieldName + ".name", name));
    }

    /**
     * Add criteria for <B>matching</B>:
     * <UL>
     * <LI>null values will not add a constraint criteria</LI>
     * <LI>any string will match that specific value and any rules with that field set to null</LI>
     * </UL>
     */
    protected void addStringMatchCriteria(Search searchCriteria, String value, String fieldName) throws BadRequestWebEx {
        if(value != null) {
            searchCriteria.addFilterOr(
                    Filter.isNull(fieldName),
                    Filter.equal(fieldName, value));
        }
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
