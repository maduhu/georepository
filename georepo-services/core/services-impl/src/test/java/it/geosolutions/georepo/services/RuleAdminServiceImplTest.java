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

import it.geosolutions.georepo.core.model.Profile;
import it.geosolutions.georepo.core.model.Rule;
import it.geosolutions.georepo.core.model.enums.GrantType;
import it.geosolutions.georepo.services.exception.ResourceNotFoundFault;
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


        assertEquals(0, ruleAdminService.getMatchingRules(-1L,-1L,-1L,          null, null,null,null).size());
//        LOGGER.error(ruleAdminService.getMatchingRules(-1L,p1.getId(),-1L,   null, null,null,null));
        assertEquals(3, ruleAdminService.getMatchingRules(-1L,p1.getId(),-1L,   null, null,null,null).size());
        assertEquals(1, ruleAdminService.getMatchingRules(-1L,p2.getId(),-1L,   null, null,null,null).size());
        assertEquals(2, ruleAdminService.getMatchingRules(-1L,p1.getId(),-1L,   "s1", null,null,null).size());
        assertEquals(0, ruleAdminService.getMatchingRules(-1L,-1L,-1L,          "ZZ", null,null,null).size());

    }

}
