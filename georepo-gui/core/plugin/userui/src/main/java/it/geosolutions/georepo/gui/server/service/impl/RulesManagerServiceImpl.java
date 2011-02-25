/*
 * $ Header: it.geosolutions.georepo.gui.server.service.impl.RulesManagerServiceImpl,v. 0.1 9-feb-2011 13.03.26 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 9-feb-2011 13.03.26 $
 *
 * ====================================================================
 *
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 * http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. 
 *
 * ====================================================================
 *
 * This software consists of voluntary contributions made by developers
 * of GeoSolutions.  For more information on GeoSolutions, please see
 * <http://www.geo-solutions.it/>.
 *
 */
package it.geosolutions.georepo.gui.server.service.impl;

import it.geosolutions.georepo.core.model.LayerDetails;
import it.geosolutions.georepo.core.model.enums.GrantType;
import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.GSInstance;
import it.geosolutions.georepo.gui.client.model.GSUser;
import it.geosolutions.georepo.gui.client.model.Profile;
import it.geosolutions.georepo.gui.client.model.Rule;
import it.geosolutions.georepo.gui.client.model.data.LayerCustomProps;
import it.geosolutions.georepo.gui.server.service.IRulesManagerService;
import it.geosolutions.georepo.gui.service.GeoRepoRemoteService;
import it.geosolutions.georepo.services.dto.RuleFilter;
import it.geosolutions.georepo.services.dto.ShortRule;
import it.geosolutions.georepo.services.dto.RuleFilter.SpecialFilterType;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

// TODO: Auto-generated Javadoc
/**
 * The Class RulesManagerServiceImpl.
 */
@Component("rulesManagerServiceGWT")
public class RulesManagerServiceImpl implements IRulesManagerService {

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** The georepo remote service. */
    @Autowired
    private GeoRepoRemoteService georepoRemoteService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.digitalglobe.dgwatch.gui.server.service.IFeatureService#loadFeature(com.extjs.gxt.ui.
     * client.data.PagingLoadConfig, java.lang.String)
     */
    public PagingLoadResult<Rule> getRules(PagingLoadConfig config, boolean full)
            throws ApplicationException {
        int start = config.getOffset();

        List<Rule> ruleListDTO = new ArrayList<Rule>();

        long rulesCount = georepoRemoteService.getRuleAdminService().getCountAll();

        Long t = new Long(rulesCount);

        int page = start == 0 ? start : start / config.getLimit();

        RuleFilter any = new RuleFilter(SpecialFilterType.ANY);
        List<ShortRule> rulesList = georepoRemoteService.getRuleAdminService().getList(any, page,
                config.getLimit());

        if (rulesList == null) {
            if (logger.isErrorEnabled())
                logger.error("No rule found on server");
            throw new ApplicationException("No rule found on server");
        }

        Iterator<ShortRule> it = rulesList.iterator();

        while (it.hasNext()) {
            ShortRule short_rule = it.next();

            it.geosolutions.georepo.core.model.Rule remote_rule;
            try {
                remote_rule = georepoRemoteService.getRuleAdminService().get(short_rule.getId());
            } catch (ResourceNotFoundFault e) {
                if (logger.isErrorEnabled())
                    logger.error("Details for rule " + short_rule.getPriority()
                            + " not found on Server!");
                throw new ApplicationException("Details for profile " + short_rule.getPriority()
                        + " not found on Server!");
            }
            Rule local_rule = new Rule();

            local_rule.setId(short_rule.getId());
            local_rule.setPriority(remote_rule.getPriority());

            if (remote_rule.getGsuser() == null) {
                GSUser all = new GSUser();
                all.setId(-1);
                all.setName("*");
                local_rule.setUser(all);
            } else {
                it.geosolutions.georepo.core.model.GSUser remote_user = remote_rule.getGsuser();
                GSUser local_user = new GSUser();
                local_user.setId(remote_user.getId());
                local_user.setName(remote_user.getName());
                local_rule.setUser(local_user);
            }

            if (remote_rule.getProfile() == null) {
                Profile all = new Profile();
                all.setId(-1);
                all.setName("*");
                local_rule.setProfile(all);
            } else {
                it.geosolutions.georepo.core.model.Profile remote_profile = remote_rule
                        .getProfile();
                Profile local_profile = new Profile();
                local_profile.setId(remote_profile.getId());
                local_profile.setName(remote_profile.getName());
                local_rule.setProfile(local_profile);
            }

            if (remote_rule.getInstance() == null) {
                GSInstance all = new GSInstance();
                all.setId(-1);
                all.setName("*");
                all.setBaseURL("*");
            } else {
                it.geosolutions.georepo.core.model.GSInstance remote_instance = remote_rule
                        .getInstance();
                GSInstance local_instance = new GSInstance();
                local_instance.setId(remote_instance.getId());
                local_instance.setName(remote_instance.getName());
                local_instance.setBaseURL(remote_instance.getBaseURL());
                local_rule.setInstance(local_instance);
            }

            local_rule
                    .setService(remote_rule.getService() != null ? remote_rule.getService() : "*");
            local_rule
                    .setRequest(remote_rule.getRequest() != null ? remote_rule.getRequest() : "*");
            local_rule.setWorkspace(remote_rule.getWorkspace() != null ? remote_rule.getWorkspace()
                    : "*");
            local_rule.setLayer(remote_rule.getLayer() != null ? remote_rule.getLayer() : "*");
            local_rule.setGrant(remote_rule.getAccess() != null ? remote_rule.getAccess()
                    .toString() : "ALLOW");

            ruleListDTO.add(local_rule);
        }

        return new BasePagingLoadResult<Rule>(ruleListDTO, config.getOffset(), t.intValue());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.server.service.IRulesManagerService#saveAllRules(java.util.List)
     */
    public void saveRule(Rule rule) throws ApplicationException {

        it.geosolutions.georepo.core.model.Rule rule2 = new it.geosolutions.georepo.core.model.Rule(
                rule.getPriority(), getUser(rule.getUser()), getProfile(rule.getProfile()),
                getInstance(rule.getInstance()), "*".equals(rule.getService()) ? null
                        : rule.getService(), "*".equals(rule.getRequest()) ? null
                        : rule.getRequest(), "*".equals(rule.getWorkspace()) ? null
                        : rule.getWorkspace(),
                "*".equals(rule.getLayer()) ? null : rule.getLayer(),
                getAccessType(rule.getGrant()));
        rule2.setId(rule.getId());
        if (rule.getId() == -1) {
            rule2.setId(null);
            georepoRemoteService.getRuleAdminService().insert(rule2);
        } else {
            try {
                georepoRemoteService.getRuleAdminService().update(rule2);
            } catch (ResourceNotFoundFault e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.server.service.IRulesManagerService#saveAllRules(java.util.List)
     */
    public void deleteRule(Rule rule) throws ApplicationException {

        if (rule.getId() != -1) {
            try {
                georepoRemoteService.getRuleAdminService().delete(rule.getId());
            } catch (ResourceNotFoundFault e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    /*
     * 
     */
    public void updatePriorities(Rule rule, long shift) {
        georepoRemoteService.getRuleAdminService().shift(rule.getPriority(), 1);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.server.service.IRulesManagerService#saveAllRules(java.util.List)
     */
    public void saveAllRules(List<Rule> rules) throws ApplicationException {
        for (ShortRule rule : georepoRemoteService.getRuleAdminService().getAll()) {
            try {
                georepoRemoteService.getRuleAdminService().delete(rule.getId());
            } catch (ResourceNotFoundFault e) {
                logger.error(e.getMessage(), e);
                throw new ApplicationException(e.getMessage(), e);
            }
        }

        for (Rule localRule : rules) {
            it.geosolutions.georepo.core.model.Rule rule = new it.geosolutions.georepo.core.model.Rule(
                    localRule.getPriority(), getUser(localRule.getUser()),
                    getProfile(localRule.getProfile()), getInstance(localRule.getInstance()),
                    "*".equals(localRule.getService()) ? null : localRule.getService(),
                    "*".equals(localRule.getRequest()) ? null : localRule.getRequest(),
                    "*".equals(localRule.getWorkspace()) ? null : localRule.getWorkspace(),
                    "*".equals(localRule.getLayer()) ? null : localRule.getLayer(),
                    getAccessType(localRule.getGrant()));
            georepoRemoteService.getRuleAdminService().insert(rule);
        }
    }

    /**
     * Gets the access type.
     * 
     * @param grant
     *            the grant
     * @return the access type
     */
    private GrantType getAccessType(String grant) {
        if (grant != null)
            return GrantType.valueOf(grant);
        else
            return GrantType.ALLOW;
    }

    /**
     * Gets the single instance of RulesManagerServiceImpl.
     * 
     * @param instance
     *            the instance
     * @return single instance of RulesManagerServiceImpl
     */
    private it.geosolutions.georepo.core.model.GSInstance getInstance(GSInstance instance) {
        it.geosolutions.georepo.core.model.GSInstance remote_instance = null;
        try {
            if (instance != null && instance.getId() != -1)
                remote_instance = georepoRemoteService.getInstanceAdminService().get(
                        instance.getId());
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }

        return remote_instance;
    }

    /**
     * Gets the profile.
     * 
     * @param profile
     *            the profile
     * @return the profile
     */
    private it.geosolutions.georepo.core.model.Profile getProfile(Profile profile) {
        it.geosolutions.georepo.core.model.Profile remote_profile = null;
        try {
            if (profile != null && profile.getId() != -1)
                remote_profile = georepoRemoteService.getProfileAdminService().get(profile.getId());
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }

        return remote_profile;
    }

    /**
     * Gets the profile.
     * 
     * @param profile
     *            the profile
     * @return the profile
     */
    private it.geosolutions.georepo.core.model.GSUser getUser(GSUser user) {
        it.geosolutions.georepo.core.model.GSUser remote_user = null;
        try {
            if (user != null && user.getId() != -1)
                remote_user = georepoRemoteService.getUserAdminService().get(user.getId());
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }

        return remote_user;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.server.service.IRulesManagerService#getLayerCustomProps(com.extjs
     * .gxt.ui.client.data.PagingLoadConfig, it.geosolutions.georepo.gui.client.model.Rule)
     */
    public PagingLoadResult<LayerCustomProps> getLayerCustomProps(PagingLoadConfig config, Rule rule) {
        int start = config.getOffset();
        Long t = new Long(0);

        List<LayerCustomProps> customPropsDTO = new ArrayList<LayerCustomProps>();

        if (rule != null && rule.getId() >= 0) {
            try {
                Map<String, String> customProperties = georepoRemoteService.getRuleAdminService()
                        .getDetailsProps(rule.getId());

                if (customProperties == null) {
                    if (logger.isErrorEnabled())
                        logger.error("No property found on server");
                    throw new ApplicationException("No rule found on server");
                }

                long rulesCount = customProperties.size();

                t = new Long(rulesCount);

                int page = start == 0 ? start : start / config.getLimit();

                for (String key : customProperties.keySet()) {
                    LayerCustomProps property = new LayerCustomProps();
                    property.setPropKey(key);
                    property.setPropValue(customProperties.get(key));
                    customPropsDTO.add(property);
                }
            } catch (Exception e) {
                // do nothing!
            }
        }

        return new BasePagingLoadResult<LayerCustomProps>(customPropsDTO, config.getOffset(),
                t.intValue());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.server.service.IRulesManagerService#setDetailsProps(java.lang
     * .Long, it.geosolutions.georepo.gui.client.model.data.LayerCustomProps)
     */
    public void setDetailsProps(Long ruleId, List<LayerCustomProps> customProps) {
        Map<String, String> props = new HashMap<String, String>();

        for (LayerCustomProps prop : customProps) {
            props.put(prop.getPropKey(), prop.getPropValue());
        }
        LayerDetails details = null;
        try {
            details = georepoRemoteService.getRuleAdminService().getDetails(ruleId);
        } catch (Exception e) {
            details = new LayerDetails();
            georepoRemoteService.getRuleAdminService().setDetails(ruleId, details);
        }
        georepoRemoteService.getRuleAdminService().setDetailsProps(ruleId, props);
    }

    public void shift(long priorityStart, long offset) {
        if (priorityStart != -1) {
            georepoRemoteService.getRuleAdminService().shift(priorityStart, offset);
        }
    }

    public void swap(long id1, long id2) {
        if (id1 != -1 && id2 != -1) {
            georepoRemoteService.getRuleAdminService().swap(id1, id2);
        }

    }

    public void findRule(Rule rule) throws ApplicationException, Exception {
        it.geosolutions.georepo.core.model.Rule ret = null;
        ret = georepoRemoteService.getRuleAdminService().get(rule.getId());
        // return ret;
    }
}
