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

import it.geosolutions.georepo.core.model.LayerAttribute;
import it.geosolutions.georepo.core.model.LayerDetails;
import it.geosolutions.georepo.core.model.RuleLimits;
import it.geosolutions.georepo.core.model.enums.AccessType;
import it.geosolutions.georepo.core.model.enums.GrantType;
import it.geosolutions.georepo.core.model.enums.LayerType;
import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.GSInstance;
import it.geosolutions.georepo.gui.client.model.GSUser;
import it.geosolutions.georepo.gui.client.model.Profile;
import it.geosolutions.georepo.gui.client.model.Rule;
import it.geosolutions.georepo.gui.client.model.data.LayerAttribUI;
import it.geosolutions.georepo.gui.client.model.data.LayerCustomProps;
import it.geosolutions.georepo.gui.client.model.data.LayerDetailsInfo;
import it.geosolutions.georepo.gui.client.model.data.LayerLimitsInfo;
import it.geosolutions.georepo.gui.client.model.data.LayerStyle;
import it.geosolutions.georepo.gui.server.service.IRulesManagerService;
import it.geosolutions.georepo.gui.service.GeoRepoRemoteService;
import it.geosolutions.georepo.services.dto.RuleFilter;
import it.geosolutions.georepo.services.dto.RuleFilter.SpecialFilterType;
import it.geosolutions.georepo.services.dto.ShortRule;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTFeatureType;
import it.geosolutions.geoserver.rest.decoder.RESTLayer;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

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
     * @see it.geosolutions.georepo.gui.server.service.IFeatureService#loadFeature(com.extjs.gxt.ui.
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
                local_instance.setUsername(remote_instance.getUsername());
                local_instance.setPassword(remote_instance.getPassword());
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

        long count = checkUniqueness(rule); 
        
        if(count < 1){
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
                    e.printStackTrace();
                }
            }
        }else{
            String message = "This rule is already present !";
            logger.error(message);
            throw new ApplicationException(message);
        }
    }
    
    /**
     * @param rule
     * @return long
     */
    private long checkUniqueness(Rule rule){
        RuleFilter filter = new RuleFilter();
        
        if(!rule.getUser().getName().equalsIgnoreCase("*"))
            filter.setUser(rule.getUser().getId());
        else
            filter.setUser(SpecialFilterType.DEFAULT);
        
        if(!rule.getProfile().getName().equalsIgnoreCase("*"))
            filter.setProfile(rule.getProfile().getId());
        else
            filter.setProfile(SpecialFilterType.DEFAULT);
        
        if(!rule.getInstance().getName().equalsIgnoreCase("*"))
            filter.setInstance(rule.getInstance().getId());
        else
            filter.setInstance(SpecialFilterType.DEFAULT);
        
        if(!rule.getService().equalsIgnoreCase("*"))
            filter.setService(rule.getService());
        else
            filter.setService(SpecialFilterType.DEFAULT);
        
        if(!rule.getRequest().equalsIgnoreCase("*"))
            filter.setRequest(rule.getRequest());
        else
            filter.setRequest(SpecialFilterType.DEFAULT);
        
        if(!rule.getWorkspace().equalsIgnoreCase("*"))
            filter.setWorkspace(rule.getWorkspace());
        else
            filter.setWorkspace(SpecialFilterType.DEFAULT);
        
        if(!rule.getLayer().equalsIgnoreCase("*"))
            filter.setLayer(rule.getLayer());
        else
            filter.setLayer(SpecialFilterType.DEFAULT);
        
        long count = 0;
        
        List<ShortRule> listShortRule = georepoRemoteService.getRuleAdminService().getList(filter, null, null);
        Iterator<ShortRule> iterator = listShortRule.iterator();
        
        while(iterator.hasNext()){
            ShortRule shortRule = iterator.next();
            if(shortRule.getId() != rule.getId()){
                count ++;     
            }else{
                if(!shortRule.getAccess().name().equalsIgnoreCase(rule.getGrant())){
                    long ruleId = rule.getId();
                    georepoRemoteService.getRuleAdminService().setDetails(ruleId, null);
                    georepoRemoteService.getRuleAdminService().setLimits(ruleId, null);
                }
            }
        }

        return count;
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

                SortedSet<String> sortedset = new TreeSet<String>(customProperties.keySet());
                Iterator<String> it = sortedset.iterator();

                while (it.hasNext()) {
                    String key = it.next();
                    LayerCustomProps property = new LayerCustomProps();
                    property.setPropKey(key);
                    property.setPropValue(customProperties.get(key));
                    customPropsDTO.add(property);
                }

                // for (String key : customProperties.keySet()) {
                //
                // LayerCustomProps property = new LayerCustomProps();
                // property.setPropKey(key);
                // property.setPropValue(customProperties.get(key));
                // customPropsDTO.add(property);
                // }
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
            details = georepoRemoteService.getRuleAdminService().get(ruleId).getLayerDetails();

            if (details == null) {
                details = new LayerDetails();

                it.geosolutions.georepo.core.model.Rule rule = georepoRemoteService
                        .getRuleAdminService().get(ruleId);
                it.geosolutions.georepo.core.model.GSInstance gsInstance = rule.getInstance();
                GeoServerRESTReader gsreader = new GeoServerRESTReader(gsInstance.getBaseURL(),
                        gsInstance.getUsername(), gsInstance.getPassword());
                RESTLayer layer = gsreader.getLayer(rule.getLayer());

                if (layer.getType().equals(RESTLayer.TYPE.VECTOR)) {
                    details.setType(LayerType.VECTOR);
                } else {
                    details.setType(LayerType.RASTER);
                }

                details.setCustomProps(props);
                georepoRemoteService.getRuleAdminService().setDetails(ruleId, details);
            } else {
                georepoRemoteService.getRuleAdminService().setDetailsProps(ruleId, props);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.server.service.IRulesManagerService#getLayerAttributes(com.extjs
     * .gxt.ui.client.data.PagingLoadConfig, it.geosolutions.georepo.gui.client.model.Rule)
     */
    public List<LayerAttribUI> getLayerAttributes(Rule rule) {

        LayerDetails layerDetails = null;
        List<LayerAttribUI> layerAttributesDTO = new ArrayList<LayerAttribUI>();

        try {

            layerDetails = georepoRemoteService.getRuleAdminService().get(rule.getId())
                    .getLayerDetails();

            layerAttributesDTO = loadAttribute(rule);
            
            if (layerDetails != null && layerAttributesDTO != null) {
                Set<LayerAttribute> layerAttributes = layerDetails.getAttributes();
                
                if(layerAttributes.size() > 0){
                    if (layerDetails.getType().equals(LayerType.VECTOR)) {
                        // ///////////////////////
                        // Vector Layer
                        // ///////////////////////

                        Iterator<LayerAttribute> iterator = layerAttributes.iterator();

                        while (iterator.hasNext()) {
                            LayerAttribute layerAttribute = iterator.next();

                            for(int i=0; i<layerAttributesDTO.size(); i++){
                                String attrName = layerAttributesDTO.get(i).getName();
                                if(layerAttribute.getName().equalsIgnoreCase(attrName)){
                                    LayerAttribUI layAttrUI = new LayerAttribUI();
                                    layAttrUI.setName(layerAttribute.getName());
                                    layAttrUI.setDataType(layerAttribute.getDatatype());
                                    layAttrUI.setAccessType(layerAttribute.getAccess().toString());
                                    
                                    layerAttributesDTO.set(i, layAttrUI);
                                }
                            }
                        }
                    }
                }        
            }

        } catch (ResourceNotFoundFault e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }

        return layerAttributesDTO;
    }
    
    /**
     * @param rule
     * @return List<LayerAttribUI>
     */
    private List<LayerAttribUI> loadAttribute(Rule rule){
        List<LayerAttribUI> layerAttributesDTO = new ArrayList<LayerAttribUI>();
        Set<LayerAttribute> layerAttributes = null;

        GSInstance gsInstance = rule.getInstance();
        GeoServerRESTReader gsreader;

        try {
            gsreader = new GeoServerRESTReader(gsInstance.getBaseURL(),
                    gsInstance.getUsername(), gsInstance.getPassword());

            RESTLayer layer = gsreader.getLayer(rule.getLayer());

            if (layer.getType().equals(RESTLayer.TYPE.VECTOR)) {
                layerAttributes = new HashSet<LayerAttribute>();

                // ///////////////////////
                // Vector Layer
                // ///////////////////////

                RESTFeatureType featureType = gsreader.getFeatureType(layer);

                for (RESTFeatureType.Attribute attribute : featureType.getAttributes()) {
                    LayerAttribute attr = new LayerAttribute();
                    attr.setName(attribute.getName());
                    attr.setDatatype(attribute.getBinding());

                    layerAttributes.add(attr);
                }

                layerAttributesDTO = new ArrayList<LayerAttribUI>();
                Iterator<LayerAttribute> iterator = layerAttributes.iterator();

                while (iterator.hasNext()) {
                    LayerAttribute layerAttribute = iterator.next();

                    LayerAttribUI layAttrUI = new LayerAttribUI();
                    layAttrUI.setName(layerAttribute.getName());
                    layAttrUI.setDataType(layerAttribute.getDatatype());

                    layerAttributesDTO.add(layAttrUI);
                }
            } else {
                // ///////////////////////
                // Raster Layer
                // ///////////////////////
                layerAttributesDTO = null;
            }
            
        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }

        return layerAttributesDTO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.server.service.IRulesManagerService#setLayerAttributes(java.lang
     * .Long, java.util.List)
     */
    public void setLayerAttributes(Long ruleId, List<LayerAttribUI> layerAttributes) {

        LayerDetails details = null;

        try {
            details = georepoRemoteService.getRuleAdminService().get(ruleId).getLayerDetails();

            if (details == null) {
                details = new LayerDetails();
                details.setType(LayerType.VECTOR);
            }

            Set<LayerAttribute> layerAttribs = new HashSet<LayerAttribute>();

            Iterator<LayerAttribUI> iterator = layerAttributes.iterator();
            while (iterator.hasNext()) {
                LayerAttribUI layerAttribUI = iterator.next();
                
                String accessType = layerAttribUI.getAccessType();
                
                if(accessType != null){
                    LayerAttribute attr = new LayerAttribute();

                    attr.setName(layerAttribUI.getName());
                    attr.setDatatype(layerAttribUI.getDataType());

                    if (accessType.equalsIgnoreCase("NONE")) {
                        attr.setAccess(AccessType.NONE);
                    } else if (accessType.equalsIgnoreCase("READONLY")) {
                        attr.setAccess(AccessType.READONLY);
                    } else {
                        attr.setAccess(AccessType.READWRITE);
                    }

                    layerAttribs.add(attr);
                }

            }

            details.setAttributes(layerAttribs);            
            georepoRemoteService.getRuleAdminService().setDetails(ruleId, details);
            
        } catch (ResourceNotFoundFault e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.server.service.IRulesManagerService#saveLayerDetails(it.geosolutions
     * .georepo.gui.client.model.data.LayerDetailsForm)
     */
    public LayerDetailsInfo saveLayerDetailsInfo(LayerDetailsInfo layerDetailsInfo,
            List<LayerStyle> layerStyles) {

        Long ruleId = layerDetailsInfo.getRuleId();
        LayerDetails layerDetails = null;

        try {
            layerDetails = georepoRemoteService.getRuleAdminService().get(ruleId).getLayerDetails();

            if (layerDetails == null)
                layerDetails = new LayerDetails();

            it.geosolutions.georepo.core.model.Rule rule = georepoRemoteService
                    .getRuleAdminService().get(ruleId);
            it.geosolutions.georepo.core.model.GSInstance gsInstance = rule.getInstance();
            GeoServerRESTReader gsreader = new GeoServerRESTReader(gsInstance.getBaseURL(),
                    gsInstance.getUsername(), gsInstance.getPassword());
            RESTLayer layer = gsreader.getLayer(rule.getLayer());

            if (layer.getType().equals(RESTLayer.TYPE.VECTOR)) {
                layerDetails.setType(LayerType.VECTOR);

                layerDetails.setCqlFilterRead(layerDetailsInfo.getCqlFilterRead());
                layerDetails.setCqlFilterWrite(layerDetailsInfo.getCqlFilterWrite());
            } else {
                layerDetails.setType(LayerType.RASTER);
            }

            // ///////////////////////////////////
            // Saving the layer details info
            // ///////////////////////////////////

            layerDetails.setDefaultStyle(layerDetailsInfo.getDefaultStyle());
            
            String allowedArea = layerDetailsInfo.getAllowedArea();
            if(allowedArea != null){
                WKTReader wktReader = new WKTReader();  
                MultiPolygon the_geom = (MultiPolygon) wktReader.read(allowedArea);
                layerDetails.setArea(the_geom);
            }

            // ///////////////////////////////////
            // Saving the available styles if any
            // ///////////////////////////////////

            Set<String> allowedStyles = new HashSet<String>();

            Iterator<LayerStyle> iterator = layerStyles.iterator();

            while (iterator.hasNext()) {
                LayerStyle style = iterator.next();

                if (style.isEnabled()) {
                    allowedStyles.add(style.getStyle());
                }
            }

            if (allowedStyles.size() > 0)
                layerDetails.setAllowedStyles(allowedStyles);

            georepoRemoteService.getRuleAdminService().setDetails(ruleId, layerDetails);

        } catch (ResourceNotFoundFault e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }

        return layerDetailsInfo;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeit.geosolutions.georepo.gui.server.service.IRulesManagerService#getLayerDetailsInfo(it.
     * geosolutions.georepo.gui.client.model.Rule)
     */
    public LayerDetailsInfo getLayerDetailsInfo(Rule rule) {
        Long ruleId = rule.getId();
        LayerDetails layerDetails = null;
        LayerDetailsInfo layerDetailsInfo = null;

        try {
            layerDetails = georepoRemoteService.getRuleAdminService().get(ruleId).getLayerDetails();

            if (layerDetails != null) {
                layerDetailsInfo = new LayerDetailsInfo();
                layerDetailsInfo.setRuleId(ruleId);
                layerDetailsInfo.setCqlFilterRead(layerDetails.getCqlFilterRead());
                layerDetailsInfo.setCqlFilterWrite(layerDetails.getCqlFilterWrite());
                layerDetailsInfo.setDefaultStyle(layerDetails.getDefaultStyle());
                layerDetailsInfo.setAllowedArea(layerDetails.getArea() != null ? layerDetails
                        .getArea().toText() : null);

                if (layerDetails.getType().equals(LayerType.RASTER)) {
                    layerDetailsInfo.setType("raster");
                } else {
                    layerDetailsInfo.setType("vector");
                }
            }

        } catch (ResourceNotFoundFault e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }

        return layerDetailsInfo;
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

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.server.service.IRulesManagerService#saveLayerLimitsInfo(it.
     * geosolutions.georepo.gui.client.model.data.LayerLimitsInfo)
     */
    public LayerLimitsInfo saveLayerLimitsInfo(LayerLimitsInfo layerLimitsForm) {

        Long ruleId = layerLimitsForm.getRuleId();
        RuleLimits ruleLimits = null;

        try {
            ruleLimits = georepoRemoteService.getRuleAdminService().get(ruleId).getRuleLimits();

            if (ruleLimits == null)
                ruleLimits = new RuleLimits();
            
            String allowedArea = layerLimitsForm.getAllowedArea();
            
            if(allowedArea != null){
                WKTReader wktReader = new WKTReader();
                MultiPolygon the_geom = (MultiPolygon) wktReader.read(allowedArea);
                ruleLimits.setAllowedArea(the_geom);
            }

            georepoRemoteService.getRuleAdminService().setLimits(ruleId, ruleLimits);

        } catch (ResourceNotFoundFault e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }

        return layerLimitsForm;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.server.service.IRulesManagerService#getLayerLimitsInfo(it.
     * geosolutions.georepo.gui.client.model.Rule)
     */
    public LayerLimitsInfo getLayerLimitsInfo(Rule rule) {
        Long ruleId = rule.getId();
        RuleLimits ruleLimits = null;
        LayerLimitsInfo layerLimitsInfo = null;

        try {
            ruleLimits = georepoRemoteService.getRuleAdminService().get(ruleId).getRuleLimits();

            if (ruleLimits != null) {
                layerLimitsInfo = new LayerLimitsInfo();
                layerLimitsInfo.setRuleId(ruleId);
                layerLimitsInfo.setAllowedArea(ruleLimits.getAllowedArea() != null ? ruleLimits
                        .getAllowedArea().toText() : null);
            }

        } catch (ResourceNotFoundFault e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }

        return layerLimitsInfo;
    }
}
