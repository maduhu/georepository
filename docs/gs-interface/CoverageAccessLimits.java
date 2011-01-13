/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.security;

import org.opengis.filter.Filter;
import org.opengis.parameter.GeneralParameterValue;

import com.vividsolutions.jts.geom.MultiPolygon;

/**
 * Describes security limits on a raster layers
 * 
 * @author Andrea Aime - GeoSolutions
 */
public class CoverageAccessLimits extends DataAccessLimits {
    /**
     * Used as a ROI filter on raster data
     */
    MultiPolygon rasterFilter;

    /**
     * Param overrides when grabbing a reader
     */
    GeneralParameterValue[] params;

    /**
     * Builds a raster limit
     * 
     * @param readFilter
     *            The read filter, used if the reader takes a OGC filter among the read parameters
     * @param rasterFilter
     *            Used as a ROI on the returned coverage
     * @param params
     *            Read parameters overrides
     */
    public CoverageAccessLimits(CatalogMode mode, Filter readFilter, MultiPolygon rasterFilter,
            GeneralParameterValue[] params) {
        super(mode, readFilter);
        this.rasterFilter = rasterFilter;
        this.params = params;
    }

    public MultiPolygon getRasterFilter() {
        return rasterFilter;
    }

    public GeneralParameterValue[] getParams() {
        return params;
    }

}