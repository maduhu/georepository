/* Copyright (c) 2001 - 2009 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.security;

import org.geoserver.catalog.LayerInfo;
import org.geoserver.catalog.ResourceInfo;
import org.geoserver.catalog.WorkspaceInfo;
import org.springframework.security.Authentication;

/**
 * Provides the {@link SecureCatalogImpl} with directives on what the specified user can access.
 * 
 * @author Andrea Aime - GeoSolutions
 */
public interface ResourceAccessManager {

    /**
     * Whether the use can access the workspace and its stores. For specific
     * resource access and published resource access see the other two methods
     * @param user
     * @param workspace
     * @return
     */
    public AccessLimits canAccess(Authentication user, WorkspaceInfo workspace);

    /**
     * Returns the access limits for the specified layer
     */
    public DataAccessLimits canAccess(Authentication user, LayerInfo layer);

    /**
     * Returns the access limits for the specified resource
     */
    public DataAccessLimits canAccess(Authentication user, ResourceInfo resource);

}