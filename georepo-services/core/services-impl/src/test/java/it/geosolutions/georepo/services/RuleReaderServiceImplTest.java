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
import it.geosolutions.georepo.services.dto.AccessInfo;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RuleReaderServiceImplTest extends ServiceTestBase {

    public RuleReaderServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
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

        assertEquals(0, ruleReaderService.getMatchingRules("","","",            null, null,null,null).size());
//        LOGGER.error(ruleAdminService.getMatchingRules(-1L,p1.getId(),-1L,    null, null,null,null));
        assertEquals(3, ruleReaderService.getMatchingRules("",p1.getName(),"",  null, null,null,null).size());
        assertEquals(1, ruleReaderService.getMatchingRules("",p2.getName(),"",  null, null,null,null).size());
        assertEquals(2, ruleReaderService.getMatchingRules("",p1.getName(),"",  "s1", null,null,null).size());
        assertEquals(0, ruleReaderService.getMatchingRules("","","",            "ZZ", null,null,null).size());

    }

    @Test
    public void testGetInfo() {
        assertEquals(0, ruleAdminService.getCount("*","*","*", "*","*", "*","*"));

        int pri = -1;
        Rule rules[] = new Rule[100];

        pri++; rules[pri] = new Rule(pri, null, null, null,   "WCS", null, null, null, GrantType.ALLOW);
        pri++; rules[pri] = new Rule(pri, null, null, null,   "s1", "r2", "w2", "l2", GrantType.ALLOW);
        pri++; rules[pri] = new Rule(pri, null, null, null,   "s3", "r3", "w3", "l3", GrantType.ALLOW);
        pri++; rules[pri] = new Rule(pri, null, null, null,    null, null, null, null, GrantType.DENY);

        for (Rule rule : rules) {
            if(rule != null)
                ruleAdminService.insert(rule);
        }

//        ruleAdminService.insert(r1);
//        ruleAdminService.insert(r2);
//        ruleAdminService.insert(r3);
//        ruleAdminService.insert(r4);

//        assertEquals(0, ruleReaderService.getMatchingRules("","","",            null, null,null,null).size());
////        LOGGER.error(ruleAdminService.getMatchingRules(-1L,p1.getId(),-1L,    null, null,null,null));
//        assertEquals(3, ruleReaderService.getMatchingRules("","p1","",  null, null,null,null).size());
//        assertEquals(1, ruleReaderService.getMatchingRules("","p2","",  null, null,null,null).size());
//        assertEquals(2, ruleReaderService.getMatchingRules("","p1","",  "s1", null,null,null).size());
//        assertEquals(0, ruleReaderService.getMatchingRules("","","",            "ZZ", null,null,null).size());


        AccessInfo accessInfo;

        assertEquals(2, ruleReaderService.getMatchingRules("u0","p0","i0", "WCS", null,"W0","l0").size());
        accessInfo = ruleReaderService.getAccessInfo("u0","p0","i0", "WCS", null,"W0","l0");
        assertEquals(GrantType.ALLOW, accessInfo.getGrant());
        assertNull(accessInfo.getArea());

        assertEquals(1, ruleReaderService.getMatchingRules("u0","p0","i0", "UNMATCH", null,"W0","l0").size());
        accessInfo = ruleReaderService.getAccessInfo("u0","p0","i0", "UNMATCH", null,"W0","l0");
        assertEquals(GrantType.DENY, accessInfo.getGrant());
    }

    @Test
    public void testNoDefault() {
        assertEquals(0, ruleAdminService.getCount("*","*","*", "*","*", "*","*"));

        int pri = -1;
        Rule rules[] = new Rule[100];

        pri++; rules[pri] = new Rule(pri, null, null, null,   "WCS", null, null, null, GrantType.ALLOW);

        for (Rule rule : rules) {
            if(rule != null)
                ruleAdminService.insert(rule);
        }

        AccessInfo accessInfo;

        assertEquals(1, ruleReaderService.getMatchingRules("u0","p0","i0", "WCS", null,"W0","l0").size());
        accessInfo = ruleReaderService.getAccessInfo("u0","p0","i0", "WCS", null,"W0","l0");
        assertEquals(GrantType.ALLOW, accessInfo.getGrant());

        assertEquals(0, ruleReaderService.getMatchingRules("u0","p0","i0", "UNMATCH", null,"W0","l0").size());
        accessInfo = ruleReaderService.getAccessInfo("u0","p0","i0", "UNMATCH", null,"W0","l0");
        assertEquals(GrantType.DENY, accessInfo.getGrant());
    }
}
