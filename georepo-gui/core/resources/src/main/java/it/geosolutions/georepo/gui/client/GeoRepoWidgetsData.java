/*
 * $ Header: it.geosolutions.georepo.gui.client.DGWATCHData,v. 0.1 3-gen-2011 17.06.10 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 17.06.10 $
 *
 * ====================================================================
 *
 * Copyright (C) 2010 GeoSolutions S.A.S.
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
package it.geosolutions.georepo.gui.client;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class DGWATCHData.
 */
public class GeoRepoWidgetsData {

    /**
     * Gets the send types.
     * 
     * @return the send types
     */
    public static List<SendType> getSendTypes() {
        List<SendType> sendTypes = new ArrayList<SendType>();

        sendTypes.add(new SendType("Email"));
        sendTypes.add(new SendType("RSS"));
        // sendTypes.add(new SendType("Both"));
        sendTypes.add(new SendType("EMailAndRSS"));

        return sendTypes;
    }

    /**
     * Gets the times.
     * 
     * @return the times
     */
    public static List<UpdateInterval> getTimes() {
        List<UpdateInterval> times = new ArrayList<UpdateInterval>();

        times.add(new UpdateInterval("1h"));
        times.add(new UpdateInterval("4h"));
        times.add(new UpdateInterval("24h"));

        return times;
    }

    /**
     * Gets the distrib times.
     * 
     * @return the distrib times
     */
    public static List<DistribUpdateInterval> getDistribTimes() {
        List<DistribUpdateInterval> times = new ArrayList<DistribUpdateInterval>();

        times.add(new DistribUpdateInterval("Manual"));
        times.add(new DistribUpdateInterval("Ongoing"));

        return times;
    }

    /**
     * Gets the retrieval type.
     * 
     * @return the retrieval type
     */
    public static List<RetrievalType> getRetrievalType() {
        List<RetrievalType> retrievalType = new ArrayList<RetrievalType>();

        retrievalType.add(new RetrievalType("Incremental"));
        retrievalType.add(new RetrievalType("Cumulative"));

        return retrievalType;
    }

    /**
     * Gets the content types.
     * 
     * @return the content types
     */
    public static List<ContentType> getContentTypes() {
        List<ContentType> types = new ArrayList<ContentType>();

        types.add(new ContentType("Metadata"));
        types.add(new ContentType("MetadataAndGeometry"));
        types.add(new ContentType("BareNotification"));

        return types;
    }

    /**
     * Gets the distrib content types.
     * 
     * @return the distrib content types
     */
    public static List<DistribContentType> getDistribContentTypes() {
        List<DistribContentType> types = new ArrayList<DistribContentType>();

        types.add(new DistribContentType("Metadata"));
        types.add(new DistribContentType("MetadataAndContent"));

        return types;
    }

    /**
     * Gets the val filter op.
     * 
     * @return the val filter op
     */
    public static List<FilterType> getValFilterOp() {
        List<FilterType> filterTypes = new ArrayList<FilterType>();

        filterTypes.add(new FilterType("isEqualTo"));
        filterTypes.add(new FilterType("isNotEqualTo"));
        filterTypes.add(new FilterType("isGreaterThan"));
        filterTypes.add(new FilterType("isLessThan"));
        filterTypes.add(new FilterType("isGreaterOrEqualTo"));
        filterTypes.add(new FilterType("isLessOrEqualTo"));

        return filterTypes;
    }

    /**
     * Gets the text filter op.
     * 
     * @return the text filter op
     */
    public static List<FilterType> getTextFilterOp() {
        List<FilterType> filterTypes = new ArrayList<FilterType>();

        filterTypes.add(new FilterType("isEqualTo"));
        filterTypes.add(new FilterType("like"));

        return filterTypes;
    }

}