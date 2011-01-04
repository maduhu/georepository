/*
 * $ Header: it.geosolutions.georepo.core.model.ServiceFilterValueTest,v. 0.1 4-gen-2011 17.55.13 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 4-gen-2011 17.55.13 $
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

package it.geosolutions.georepo.core.model;

import java.util.ArrayList;
import java.util.List;

import it.geosolutions.georepo.core.model.util.FilterValueDecoder;
import it.geosolutions.georepo.core.model.util.FilterValueEncoder;
import junit.framework.TestCase;

import org.junit.Test;

/**
 * The Class ServiceFilterValueTest.
 */
public class ServiceFilterValueTest extends TestCase {

    /**
     * Instantiates a new service filter value test.
     */
    public ServiceFilterValueTest() {
    }

    /**
     * Test marhsal.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void testVelueDecoding() throws Exception {

        ServiceFilter filter = new ServiceFilter();
        filter.setService("WCS");
        filter.setProperty("access");

        FilterValueEncoder fveBoolean = new FilterValueEncoder();
        fveBoolean.add(Boolean.TRUE);

        filter.setValue(fveBoolean.encodeValue());

        FilterValueDecoder fvd1 = new FilterValueDecoder(filter);
        assertEquals(1, fvd1.getValueCount());

        assertTrue("Value is not a Boolean type!", fvd1.getBoolean(0) == Boolean.TRUE);

        FilterValueEncoder fveStringList = new FilterValueEncoder();
        List<String> stringList = new ArrayList<String>();
        stringList.add("String_1");
        stringList.add("String_2");
        stringList.add("String_3");
        stringList.add("String_4");

        fveStringList.add(stringList);
        
        filter.setValue(fveStringList.encodeValue());

        FilterValueDecoder fvd2 = new FilterValueDecoder(filter);
        assertEquals(1, fvd2.getValueCount());

        assertTrue("String List sizes do not match!", fvd2.getStringList(0).size() == 4);

        // DGAoi aoi = new DGAoi();
        // aoi.setArea(42);
        // aoi.setCreationDate(new Date(2000, 01, 01));
        //
        // WKTReader reader = new WKTReader();
        // MultiPolygon mp = (MultiPolygon) reader
        // .read("MULTIPOLYGON(((48.6894038 62.33877482, 48.7014874 62.33877482, 48.7014874 62.33968662, 48.6894038 62.33968662, 48.6894038 62.33877482)))");
        // mp.setSRID(4326);
        // aoi.setGeometry(mp);
        //
        // JAXBContext context = JAXBContext.newInstance(DGAoi.class);
        // Marshaller marshaller = context.createMarshaller();
        // marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // pretty print XML
        // marshaller.marshal(aoi, System.out);
    }

}
