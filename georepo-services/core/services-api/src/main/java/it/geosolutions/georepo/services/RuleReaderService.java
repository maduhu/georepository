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

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.PathParam;

import it.geosolutions.georepo.core.model.Rule;
import it.geosolutions.georepo.services.dto.AccessInfo;
import it.geosolutions.georepo.services.dto.RuleFilter;
import it.geosolutions.georepo.services.dto.ShortRule;


/**
 * Operations on
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
@WebService(name = "RuleReaderService", targetNamespace = "http://geosolutions.it/georepo")
public interface RuleReaderService
{

    /**
     * Return info on resource accessibility.
     * <P>
     * All parameters reference instances by name.<BR>
     * <LI>If a given parameter is "<TT>*</TT>", it will match <B>any</B> value in the related {@link Rule} field.</LI>
     * <LI>If a given parameter is <TT>null</TT>, it will match only null (default) values in the related {@link Rule} field.</LI>
     * </UL>
     * In order to have a better control on the query, please use {@link #getAccessInfo(RuleFilter filter) }.
     *
     * @deprecated Use {@link #getAccessInfo(RuleFilter filter) }
     */
    AccessInfo getAccessInfo(@PathParam("user") String userName,
        @PathParam("profile") String profileName,
        @PathParam("instance") String instanceName,
        @PathParam("service") String service,
        @PathParam("request") String request,
        @PathParam("workspace") String workspace,
        @PathParam("layer") String layer);

    /**
     * Return info on resource accessibility.
     */
    AccessInfo getAccessInfo(RuleFilter filter);

    /**
     * Return the unprocessed {@link Rule} list matching a given filter, sorted
     * by priority.
     * <P>
     * Use {@link getAccessInfo(String,String,String,String,String,String,String) getAccessInfo}
     * if you need the resulting coalesced access info.
     * <P>
     * Differently from {@link RuleAdminService#getList(String,String,String,String,String,String,String,Integer,Integer) RuleAdminService.getList(...)},
     *  when a param is set, it will match
     * all the rules with the corresponding matching field,
     * plus all the rules having that field set to <T>null</TT>.
     * <BR>Null params will only match null values.
     * <BR>The "*" string will always match.
     *
     * @deprecated Use {@link #getMatchingRules(RuleFilter filter) }
     */

    List<ShortRule> getMatchingRules(@PathParam("user") String userName,
        @PathParam("profile") String profileName,
        @PathParam("instance") String instanceName,
        @PathParam("service") String service,
        @PathParam("request") String request,
        @PathParam("workspace") String workspace,
        @PathParam("layer") String layer);

    /**
     * Return the unprocessed {@link Rule} list matching a given filter, sorted
     * by priority.
     * <P>
     * Use {@link getAccessInfo(RuleFilter) getAccessInfo(RuleFilter)}
     * if you need the resulting coalesced access info.
     */
    List<ShortRule> getMatchingRules(RuleFilter filter);

    boolean isAdmin(String userName);


    // ==========================================================================

}
