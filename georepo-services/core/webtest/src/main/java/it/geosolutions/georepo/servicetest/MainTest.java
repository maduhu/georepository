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

package it.geosolutions.georepo.servicetest;

import it.geosolutions.georepo.core.model.GSInstance;
import it.geosolutions.georepo.core.model.GSUser;
import it.geosolutions.georepo.core.model.LayerAttribute;
import it.geosolutions.georepo.core.model.LayerDetails;
import it.geosolutions.georepo.core.model.Profile;
import it.geosolutions.georepo.core.model.Rule;
import it.geosolutions.georepo.core.model.RuleLimits;
import it.geosolutions.georepo.core.model.enums.AccessType;
import it.geosolutions.georepo.core.model.enums.GrantType;
import it.geosolutions.georepo.services.InstanceAdminService;
import it.geosolutions.georepo.services.ProfileAdminService;
import it.geosolutions.georepo.services.RuleAdminService;
import it.geosolutions.georepo.services.RuleReaderService;
import it.geosolutions.georepo.services.UserAdminService;
import it.geosolutions.georepo.services.dto.AccessInfo;
import it.geosolutions.georepo.services.dto.ShortProfile;
import it.geosolutions.georepo.services.dto.ShortRule;
import it.geosolutions.georepo.services.dto.ShortUser;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.io.WKTReader;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class MainTest implements InitializingBean, ApplicationContextAware {

    private final static Logger LOGGER = Logger.getLogger(MainTest.class);

    private XmlWebApplicationContext applicationContext;

    private UserAdminService userAdminService;
    private ProfileAdminService profileAdminService;
    private InstanceAdminService instanceAdminService;
    private RuleAdminService ruleAdminService;
    private RuleReaderService ruleReaderService;

    protected final static String MULTIPOLYGONWKT = "MULTIPOLYGON(((48 62, 48 63, 49 63, 49 62, 48 62)))";

    public void afterPropertiesSet() throws Exception {
//        LOGGER.info("===== RESETTING DB DATA =====");
//        removeAll();
//        
//        LOGGER.info("===== Creating Profiles (not actually needed while testing GS) =====");
//        ShortProfile shortProfile = new ShortProfile();
//        shortProfile.setName("basic");
//        long pid1 = profileAdminService.insert(shortProfile);
//        Profile p1 = profileAdminService.get(pid1);
//
//        ShortProfile shortProfile2 = new ShortProfile();
//        shortProfile2.setName("advanced");
//        long pid2 = profileAdminService.insert(shortProfile2);
//        Profile p2 = profileAdminService.get(pid2);
//
//
//        LOGGER.info("===== Creating Users =====");
//        GSUser cite = createUser("cite");
//        cite.setProfile(p1);
//        userAdminService.insert(cite);
//
//        GSUser wmsUser = createUser("wmsuser");
//        wmsUser.setProfile(p1);
//        userAdminService.insert(wmsUser);
//        
//        GSUser areaUser = createUser("area");
//        areaUser.setProfile(p1);
//        userAdminService.insert(areaUser);
//        
//        GSUser uStates = createUser("u-states");
//        uStates.setProfile(p1);
//        userAdminService.insert(uStates);
//
//        LOGGER.info("===== Creating Rules =====");
//
//        LayerDetails ld1 = new LayerDetails();
//        ld1.getAllowedStyles().add("style1");
//        ld1.getAllowedStyles().add("style2");
//        ld1.getAttributes().add(new LayerAttribute("attr1", AccessType.NONE));
//        ld1.getAttributes().add(new LayerAttribute("attr2", AccessType.READONLY));
//        ld1.getAttributes().add(new LayerAttribute("attr3", AccessType.READWRITE));
//
//        int priority = 0;
//        
//        /* Cite user rules */
//        // allow user cite full control over the cite workspace
//        ruleAdminService.insert(new Rule(priority++, cite, null, null, null, null, "cite", null, GrantType.ALLOW));
//        // allow only getmap, getcapatbilities and reflector usage on workspace sf
//        ruleAdminService.insert((new Rule(priority++, cite, null, null, "wms", "GetMap", "sf", null, GrantType.ALLOW)));
//        ruleAdminService.insert((new Rule(priority++, cite, null, null, "wms", "GetCapabilities", "sf", null, GrantType.ALLOW)));
//        ruleAdminService.insert((new Rule(priority++, cite, null, null, "wms", "reflect", "sf", null, GrantType.ALLOW)));
//        // allow only GetMap and GetFeature the topp workspace
//        
//        /* wms user rules */
//        ruleAdminService.insert((new Rule(priority++, wmsUser, null, null, "wms", null, null, null, GrantType.ALLOW)));
//        
//        /* all powerful but only in a restricted area */
//        Rule areaRestriction = new Rule(priority++, areaUser, null, null, null, null, null, null, GrantType.LIMIT);
//        RuleLimits limits = new RuleLimits();
//        limits.setAllowedArea((MultiPolygon) new WKTReader().read(MULTIPOLYGONWKT));
//        long ruleId = ruleAdminService.insert(areaRestriction);
//        ruleAdminService.setLimits(ruleId, limits);
//        //ruleAdminService.insert((new Rule(priority++, areaUser, null, null, null, null, null, null, GrantType.ALLOW)));
//        
//        /* some users for interactive testing with the default data directory */
//        // uStates can do whatever, but only on topp:states
//        ruleAdminService.insert(new Rule(priority++, uStates, null, null, null, null, "topp", "states", GrantType.ALLOW));
//        
//        // deny everything else
//        ruleAdminService.insert(new Rule(priority++, null, null, null,  null, null, null, null, GrantType.DENY));
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                boolean success = false;
//                int cnt = 5;
//
//                while( ! success && cnt-->0) {
//                    try{
//                        LOGGER.info("Waiting 5 secs...");
//                        Thread.sleep(5000);
//
//                        LOGGER.info("Trying creating spring remoting client...");
//                        instantiateAndRunSpringRemoting();
//
//                        success = true;
//
//                    } catch (InterruptedException ex) {
//                    }catch(Exception e) {
//                        LOGGER.warn("Failed creating spring remoting client...");
//                    }
//                }
//            }
//        }).start();


        try {
            LOGGER.info("===== User List =====");

//            List<ShortUser> users = userAdminService.getAll();
//            for (ShortUser loop : users) {
//                System.out.println("User -> " + loop);
//            }



        } finally {
        }
    }


    public void instantiateAndRunSpringRemoting() {
        HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
        httpInvokerProxyFactoryBean.setServiceInterface(it.geosolutions.georepo.services.RuleReaderService.class);
        httpInvokerProxyFactoryBean.setServiceUrl("http://localhost:9191/remoting/RuleReader");
        httpInvokerProxyFactoryBean.afterPropertiesSet();
        RuleReaderService rrs = (RuleReaderService) httpInvokerProxyFactoryBean.getObject();

        AccessInfo accessInfo = rrs.getAccessInfo("pippo", null, "gs1", "WMS", null, null, null);
        LOGGER.info(accessInfo);
        AccessInfo accessInfo2 = rrs.getAccessInfo("pippo", null, "gs1", "WCS", null, null, null);
        LOGGER.info(accessInfo2);
    }

    //==========================================================================

    protected GSUser createUser(String baseName) {
        GSUser user = new GSUser();
        user.setName(baseName);
        return user;
    }

    //==========================================================================

    protected void removeAll() throws ResourceNotFoundFault {
        LOGGER.info("***** removeAll()");
        removeAllRules();
        removeAllUsers();
        removeAllProfiles();
        removeAllInstances();
    }

    protected void removeAllRules() throws ResourceNotFoundFault {
        List<ShortRule> list = ruleAdminService.getAll();
        for (ShortRule item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = ruleAdminService.delete(item.getId());
            if(!ret)
                throw new IllegalStateException("Rule not removed");
        }

        if( ruleAdminService.getCountAll() != 0)
                throw new IllegalStateException("Rules have not been properly deleted");
    }

    protected void removeAllUsers() throws ResourceNotFoundFault {
        List<ShortUser> list = userAdminService.getAll();
        for (ShortUser item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = userAdminService.delete(item.getId());
            if(!ret)
                throw new IllegalStateException("User not removed");
        }

        if( userAdminService.getCount(null) != 0)
                throw new IllegalStateException("Users have not been properly deleted");
    }

    protected void removeAllProfiles() throws ResourceNotFoundFault {
        List<ShortProfile> list = profileAdminService.getAll();
        for (ShortProfile item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = profileAdminService.delete(item.getId());
            if(!ret)
                throw new IllegalStateException("Profile not removed");
        }

        if( profileAdminService.getCount(null) != 0)
                throw new IllegalStateException("Profiles have not been properly deleted");
    }

    protected void removeAllInstances() throws ResourceNotFoundFault {
        List<GSInstance> list = instanceAdminService.getAll();
        for (GSInstance item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = instanceAdminService.delete(item.getId());
            if(!ret)
                throw new IllegalStateException("GSInstance not removed");

        }

        if( instanceAdminService.getCount(null) != 0)
                throw new IllegalStateException("Instances have not been properly deleted");
    }

    //==========================================================================
    
    public void setInstanceAdminService(InstanceAdminService instanceAdminService) {
        this.instanceAdminService = instanceAdminService;
    }

    public void setProfileAdminService(ProfileAdminService profileAdminService) {
        this.profileAdminService = profileAdminService;
    }

    public void setRuleAdminService(RuleAdminService ruleAdminService) {
        this.ruleAdminService = ruleAdminService;
    }

    public void setUserAdminService(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

    public void setRuleReaderService(RuleReaderService ruleReaderService) {
        this.ruleReaderService = ruleReaderService;
    }


    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        this.applicationContext = (XmlWebApplicationContext)ac;

    }

}
