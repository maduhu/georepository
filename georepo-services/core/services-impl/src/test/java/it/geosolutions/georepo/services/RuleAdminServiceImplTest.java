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

import it.geosolutions.georepo.core.model.LayerAttribute;
import it.geosolutions.georepo.core.model.LayerDetails;
import it.geosolutions.georepo.core.model.Profile;
import it.geosolutions.georepo.core.model.Rule;
import it.geosolutions.georepo.core.model.RuleLimits;
import it.geosolutions.georepo.core.model.enums.AccessType;
import it.geosolutions.georepo.core.model.enums.GrantType;
import it.geosolutions.georepo.services.exception.BadRequestWebEx;
import it.geosolutions.georepo.services.exception.NotFoundWebEx;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RuleAdminServiceImplTest extends ServiceTestBase {

    public RuleAdminServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testInsertDeleteRule() throws ResourceNotFoundFault {

        Profile profile = createProfile(getName());
        Rule rule = new Rule();
        rule.setProfile(profile);
        rule.setAccess(GrantType.ALLOW);
        ruleAdminService.insert(rule);
        ruleAdminService.get(rule.getId()); // will throw if not found
        assertTrue("Could not delete rule", ruleAdminService.delete(rule.getId()));
    }

    @Test
    public void testUpdateRule() throws Exception {
        Profile p1 = createProfile("p1");
        Profile p2 = createProfile("p2");

        Rule rule = new Rule(10, null, p1,null, "s1", "r1", "w1", "l1", GrantType.ALLOW);
        ruleAdminService.insert(rule);

        {
            Rule loaded = ruleAdminService.get(rule.getId());
            assertNotNull(loaded);

            assertEquals("p1", loaded.getProfile().getName());
            assertEquals("s1", loaded.getService());
            assertEquals("l1", loaded.getLayer());

            loaded.setProfile(p2);
            loaded.setService("s2");
            loaded.setLayer("l2");

            ruleAdminService.update(loaded);
        }
        {
            Rule loaded = ruleAdminService.get(rule.getId());
            assertNotNull(loaded);

            assertEquals("p2", loaded.getProfile().getName());
            assertEquals("s2", loaded.getService());
            assertEquals("l2", loaded.getLayer());
        }
    }

    @Test
    public void testGetAllRules() {
        assertEquals(0, ruleAdminService.getAll().size());

        Profile p1 = createProfile("p1");

        Rule r1 = new Rule(10, null, p1, null, "s1", "r1", "w1", "l1", GrantType.ALLOW);
        Rule r2 = new Rule(20, null, p1, null, "s2", "r2", "w2", "l2", GrantType.ALLOW);
        Rule r3 = new Rule(30, null, p1, null, "s3", "r3", "w3", "l3", GrantType.ALLOW);

        ruleAdminService.insert(r1);
        ruleAdminService.insert(r2);
        ruleAdminService.insert(r3);

        assertEquals(3, ruleAdminService.getAll().size());
    }

    @Test
    public void testGetRules() {
        assertEquals(0, ruleAdminService.getCount("*","*","*", "*","*", "*","*"));

        Profile p1 = createProfile("p1");
        Profile p2 = createProfile("p2");

        Rule r1 = new Rule(10, null, p1, null,      "s1", "r1", "w1", "l1", GrantType.ALLOW);
        Rule r2 = new Rule(20, null, p2, null,      "s1", "r2", "w2", "l2", GrantType.ALLOW);
        Rule r3 = new Rule(30, null, p1, null,      "s3", "r3", "w3", "l3", GrantType.ALLOW);
        Rule r4 = new Rule(40, null, p1, null,      null, null, null, null, GrantType.ALLOW);

        ruleAdminService.insert(r1);
        ruleAdminService.insert(r2);
        ruleAdminService.insert(r3);
        ruleAdminService.insert(r4);

        assertEquals(4, ruleAdminService.getCount("*","*","*",              "*","*", "*","*"));
        assertEquals(3, ruleAdminService.getCount("*",""+p1.getId(),"*",    "*","*", "*","*"));
        assertEquals(1, ruleAdminService.getCount("*",""+p2.getId(),"*",    "*","*", "*","*"));
        assertEquals(2, ruleAdminService.getCount("*","*","*",              "s1","*", "*","*"));
        assertEquals(0, ruleAdminService.getCount("*","*","*",              "ZZ","*", "*","*"));
    }

    public void testRuleLimits() throws ResourceNotFoundFault {
        final Long id;

        {
            Rule r1 = new Rule(10, null, null, null,      "s1", "r1", "w1", "l1", GrantType.LIMIT);
            ruleAdminService.insert(r1);
            id = r1.getId();
        }

        // save limits and check it has been saved
        final Long lid1;
        {
            RuleLimits limits = new RuleLimits();
            ruleAdminService.setLimits(id, limits);
            lid1 = limits.getId();
            assertNotNull(lid1);
        }

        // check limits have been set in Rule
        {
            Rule loaded = ruleAdminService.get(id);
            assertNotNull(loaded.getRuleLimits());
            assertEquals(lid1, loaded.getRuleLimits().getId());
            LOGGER.info("Found " + loaded + " --> " + loaded.getRuleLimits());
        }

        // set new limits
        final Long lid2;
        {
            RuleLimits limits = new RuleLimits();
            ruleAdminService.setLimits(id, limits);
            lid2 = limits.getId();
            assertNotNull(lid2);
        }

        // remove limits
        {
            Rule loaded = ruleAdminService.get(id);
            assertNotNull(loaded.getRuleLimits());
            ruleAdminService.setLimits(id, null);

            Rule loaded2 = ruleAdminService.get(id);
            assertNull(loaded2.getRuleLimits());
        }

        // remove Rule and cascade on Limits
        {
            RuleLimits limits = new RuleLimits();
            ruleAdminService.setLimits(id, limits);
            Rule loaded = ruleAdminService.get(id);
            assertNotNull(loaded.getRuleLimits());

            ruleAdminService.delete(id);
        }

    }

    public void testRuleLimitsErrors() throws ResourceNotFoundFault {

        try {
            RuleLimits limits = new RuleLimits();
            ruleAdminService.setLimits(-10L, limits);
            fail("Failed recognising not existent rule");
        } catch (NotFoundWebEx e) {
            // OK
        }

        final Long id;
        Rule r1 = new Rule(10, null, null, null,      "s1", "r1", "w1", "l1", GrantType.ALLOW);
        ruleAdminService.insert(r1);
        id = r1.getId();

        try {
            RuleLimits limits = new RuleLimits();
            ruleAdminService.setLimits(id, limits);
            fail("Failed recognising bad rule type");
        } catch (BadRequestWebEx e) {
            // OK
        }

    }

    public void testRuleDetails() throws ResourceNotFoundFault {
        final Long id;

        {
            Rule r1 = new Rule(10, null, null, null,      "s1", "r1", "w1", "l1", GrantType.ALLOW);
            ruleAdminService.insert(r1);
            id = r1.getId();
        }

        // save details and check it has been saved
        final Long lid1;
        {
            LayerDetails details = new LayerDetails();
            details.getAllowedStyles().add("FIRST_style1");
            details.getAttributes().add(new LayerAttribute("FIRST_attr1", AccessType.NONE));
            ruleAdminService.setDetails(id, details);
            lid1 = details.getId();
            assertNotNull(lid1);
        }

        // check details have been set in Rule
        {
            Rule loaded = ruleAdminService.get(id);
            LayerDetails details = loaded.getLayerDetails();
            assertNotNull(details);
            assertEquals(lid1, details.getId());
            assertEquals(1, details.getAttributes().size());
            assertEquals(1, details.getAllowedStyles().size());
            LOGGER.info("Found " + loaded + " --> " + loaded.getLayerDetails());
        }

        // set new details
        final Long lid2;
        {
            LayerDetails details = new LayerDetails();
            details.getAllowedStyles().add("style1");
            details.getAllowedStyles().add("style2");
            details.getAttributes().add(new LayerAttribute("attr1", AccessType.NONE));
            details.getAttributes().add(new LayerAttribute("attr2", AccessType.READONLY));
            details.getAttributes().add(new LayerAttribute("attr3", AccessType.READWRITE));

            assertEquals(3, details.getAttributes().size());

            ruleAdminService.setDetails(id, details);
            lid2 = details.getId();
            assertNotNull(lid2);
        }

        // check details
        {
            Rule loaded = ruleAdminService.get(id);
            LayerDetails details = loaded.getLayerDetails();
            assertNotNull(details);
            for (LayerAttribute layerAttribute : details.getAttributes()) {
                LOGGER.error(layerAttribute);
            }

            assertEquals(3, details.getAttributes().size());
            assertEquals(2, details.getAllowedStyles().size());
            assertTrue(details.getAllowedStyles().contains("style1"));
        }

        // remove details
        {
            assertNotNull(ruleAdminService.get(id).getLayerDetails());
            ruleAdminService.setDetails(id, null);

            Rule loaded2 = ruleAdminService.get(id);
            assertNull(loaded2.getLayerDetails());
        }

        // remove Rule and cascade on details
        {
            LayerDetails details = new LayerDetails();
            ruleAdminService.setDetails(id, details);
            Rule loaded = ruleAdminService.get(id);
            assertNotNull(loaded.getLayerDetails());

            ruleAdminService.delete(id);
        }

    }

    public void testRuleDetailsErrors() throws ResourceNotFoundFault {

        try {
            LayerDetails details = new LayerDetails();
            ruleAdminService.setDetails(-10L, details);
            fail("Failed recognising not existent rule");
        } catch (NotFoundWebEx e) {
            // OK
        }

        try {
            Rule r1 = new Rule(10, null, null, null,      "s1", "r1", "w1", "l1", GrantType.DENY);
            ruleAdminService.insert(r1);
            Long id1 = r1.getId();

            LayerDetails details = new LayerDetails();
            ruleAdminService.setDetails(id1, details);
            fail("Failed recognising bad rule type");
        } catch (BadRequestWebEx e) {
            // OK
        }

        try {            
            Rule r1 = new Rule(10, null, null, null,      "s1", "r1", "w1", null, GrantType.ALLOW);
            ruleAdminService.insert(r1);
            Long id1 = r1.getId();

            LayerDetails details = new LayerDetails();
            ruleAdminService.setDetails(id1, details);
            fail("Failed recognising bad rule constraints");
        } catch (BadRequestWebEx e) {
            // OK
        }

    }

    public void testRuleDetailsProps() throws ResourceNotFoundFault {
        final Long id;
        final Long lid1;

        {
            Rule r1 = new Rule(10, null, null, null,      "s1", "r1", "w1", "l1", GrantType.ALLOW);
            ruleAdminService.insert(r1);
            id = r1.getId();

            LayerDetails details = new LayerDetails();
            ruleAdminService.setDetails(id, details);
            lid1 = details.getId();
            assertNotNull(lid1);

            Map<String, String> map = new HashMap<String, String>();
            map.put("k1", "v1");
            ruleAdminService.setDetailsProps(id, map);
        }

        // check props have been set in LD
        {
            Rule loaded = ruleAdminService.get(id);
            assertNotNull(loaded.getLayerDetails());

            Map<String, String> map = ruleAdminService.getDetailsProps(id);
            assertEquals(1, map.size());
        }

        // set new details, old map should be retained
        final Long lid2;
        {
            LayerDetails details = new LayerDetails();
            ruleAdminService.setDetails(id, details);
            lid2 = details.getId();
            assertNotNull(lid2);

            Map<String, String> map = ruleAdminService.getDetailsProps(id);
            assertEquals(1, map.size());
        }

        // remove details
        {
            Rule loaded = ruleAdminService.get(id);
            assertNotNull(loaded.getLayerDetails());
            ruleAdminService.setDetails(id, null);
            // props should be cascaded
        }

        // remove Rule and cascade on details
        {
            LayerDetails details = new LayerDetails();
            ruleAdminService.setDetails(id, details);

            Map<String, String> map = new HashMap<String, String>();
            map.put("k1", "v1");
            ruleAdminService.setDetailsProps(id, map);

            Rule loaded = ruleAdminService.get(id);
            assertNotNull(loaded.getLayerDetails());

            ruleAdminService.delete(id);
        }

    }


}
