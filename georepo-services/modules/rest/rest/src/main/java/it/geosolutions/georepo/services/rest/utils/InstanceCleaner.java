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
package it.geosolutions.georepo.services.rest.utils;

import java.util.List;

import it.geosolutions.georepo.core.model.GSInstance;
import it.geosolutions.georepo.services.GRUserAdminService;
import it.geosolutions.georepo.services.InstanceAdminService;
import it.geosolutions.georepo.services.ProfileAdminService;
import it.geosolutions.georepo.services.RuleAdminService;
import it.geosolutions.georepo.services.UserAdminService;
import it.geosolutions.georepo.services.dto.ShortProfile;
import it.geosolutions.georepo.services.dto.ShortRule;
import it.geosolutions.georepo.services.dto.ShortUser;
import it.geosolutions.georepo.services.exception.NotFoundServiceEx;

import org.apache.log4j.Logger;


/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class InstanceCleaner
{
    private static final Logger LOGGER = Logger.getLogger(InstanceCleaner.class.toString());

    private RuleAdminService ruleAdminService;
    private ProfileAdminService profileAdminService;
    private UserAdminService userAdminService;
    private GRUserAdminService grUserAdminService;
    private InstanceAdminService instanceAdminService;

    public void removeAll() throws NotFoundServiceEx
    {
        LOGGER.warn("***** removeAll()");
        removeAllRules();
        removeAllUsers();
        removeAllGRUsers();
        removeAllProfiles();
        removeAllInstances();
    }

    public void removeAllRules() throws NotFoundServiceEx
    {
        List<ShortRule> list = ruleAdminService.getAll();
        for (ShortRule item : list)
        {
            LOGGER.warn("Removing " + item);

            boolean ret = ruleAdminService.delete(item.getId());
            if (!ret)
            {
                LOGGER.error("Could not remove " + item);
            }
        }

        long count = ruleAdminService.getCountAll();
        if (count > 0)
        {
            LOGGER.error("Items not removed");
        }
    }

    public void removeAllUsers() throws NotFoundServiceEx
    {
        List<ShortUser> list = userAdminService.getList(null, null, null);
        for (ShortUser item : list)
        {
            LOGGER.warn("Removing " + item);

            boolean ret = userAdminService.delete(item.getId());
            if (!ret)
            {
                LOGGER.error("Could not remove " + item);
            }
        }

        long count = userAdminService.getCount(null);
        if (count > 0)
        {
            LOGGER.error("Items not removed");
        }
    }

    public void removeAllGRUsers() throws NotFoundServiceEx
    {
        List<ShortUser> list = grUserAdminService.getList(null, null, null);
        for (ShortUser item : list)
        {
            LOGGER.warn("Removing " + item);

            boolean ret = grUserAdminService.delete(item.getId());
            if (!ret)
            {
                LOGGER.error("Could not remove " + item);
            }
        }

        long count = grUserAdminService.getCount(null);
        if (count > 0)
        {
            LOGGER.error("Items not removed");
        }
    }

    public void removeAllProfiles() throws NotFoundServiceEx
    {
        List<ShortProfile> list = profileAdminService.getList(null, null, null);
        for (ShortProfile item : list)
        {
            LOGGER.warn("Removing " + item);

            boolean ret = profileAdminService.delete(item.getId());
            if (!ret)
            {
                LOGGER.error("Could not remove " + item);
            }
        }

        long count = profileAdminService.getCount(null);
        if (count > 0)
        {
            LOGGER.error("Items not removed");
        }
    }

    public void removeAllInstances() throws NotFoundServiceEx
    {
        List<GSInstance> list = instanceAdminService.getAll();
        for (GSInstance item : list)
        {
            LOGGER.warn("Removing " + item);

            boolean ret = instanceAdminService.delete(item.getId());
            if (!ret)
            {
                LOGGER.error("Could not remove " + item);
            }
        }

        long count = instanceAdminService.getCount(null);
        if (count > 0)
        {
            LOGGER.error("Items not removed");
        }
    }

    // ==========================================================================

    public void setGrUserAdminService(GRUserAdminService grUserAdminService)
    {
        this.grUserAdminService = grUserAdminService;
    }

    public void setInstanceAdminService(InstanceAdminService instanceAdminService)
    {
        this.instanceAdminService = instanceAdminService;
    }

    public void setProfileAdminService(ProfileAdminService profileAdminService)
    {
        this.profileAdminService = profileAdminService;
    }

    public void setRuleAdminService(RuleAdminService ruleAdminService)
    {
        this.ruleAdminService = ruleAdminService;
    }

    public void setUserAdminService(UserAdminService userAdminService)
    {
        this.userAdminService = userAdminService;
    }


}
