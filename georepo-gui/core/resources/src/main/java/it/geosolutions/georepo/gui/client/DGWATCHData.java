/*
 * $Header: it.geosolutions.georepo.gui.client.DGWATCHData,v. 0.1 28/lug/2010 14.42.14 created by frank $
 * $Revision: 0.1 $
 * $Date: 28/lug/2010 14.42.14 $
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

/**
 * @author frank
 * 
 */
public class DGWATCHData {

	/**
	 * @return List<SendType>
	 */
	public static List<SendType> getSendTypes() {
		List<SendType> sendTypes = new ArrayList<SendType>();

		sendTypes.add(new SendType("Email"));
		sendTypes.add(new SendType("RSS"));
//		sendTypes.add(new SendType("Both"));
		sendTypes.add(new SendType("EMailAndRSS"));

		return sendTypes;
	}

	/**
	 * @return List<UpdateInterval>
	 */
	public static List<UpdateInterval> getTimes() {
		List<UpdateInterval> times = new ArrayList<UpdateInterval>();

		times.add(new UpdateInterval("1h"));
		times.add(new UpdateInterval("4h"));
		times.add(new UpdateInterval("24h"));

		return times;
	}
	
	/**
	 * @return List<DistribUpdateInterval>
	 */
	public static List<DistribUpdateInterval> getDistribTimes() {
		List<DistribUpdateInterval> times = new ArrayList<DistribUpdateInterval>();

		times.add(new DistribUpdateInterval("Manual"));
		times.add(new DistribUpdateInterval("Ongoing"));

		return times;
	}
	
	/**
	 * @return List<RetrievalType>
	 */
	public static List<RetrievalType> getRetrievalType() {
		List<RetrievalType> retrievalType = new ArrayList<RetrievalType>();

		retrievalType.add(new RetrievalType("Incremental"));
		retrievalType.add(new RetrievalType("Cumulative"));

		return retrievalType;
	}
	
	/**
	 * @return List<ContentType>
	 */
	public static List<ContentType> getContentTypes() {
		List<ContentType> types = new ArrayList<ContentType>();

		types.add(new ContentType("Metadata"));
		types.add(new ContentType("MetadataAndGeometry"));
		types.add(new ContentType("BareNotification"));

		return types;
	}
	
	/**
	 * @return List<DistribContentType>
	 */
	public static List<DistribContentType> getDistribContentTypes() {
		List<DistribContentType> types = new ArrayList<DistribContentType>();

		types.add(new DistribContentType("Metadata"));
		types.add(new DistribContentType("MetadataAndContent"));

		return types;
	}

	/**
	 * @return List<FilterType>
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
	 * @return List<FilterType>
	 */
	public static List<FilterType> getTextFilterOp() {
		List<FilterType> filterTypes = new ArrayList<FilterType>();

		filterTypes.add(new FilterType("isEqualTo"));
		filterTypes.add(new FilterType("like"));

		return filterTypes;
	}

}
