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

package it.geosolutions.georepo.services.dto;

import java.io.Serializable;

import it.geosolutions.georepo.core.model.Rule;
import it.geosolutions.georepo.core.model.enums.GrantType;


/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class ShortRule implements Serializable
{

    private static final long serialVersionUID = -9127101015688510863L;

    private Long id;
    private Long priority;

    private Long userId;
    private String userName;

    private Long profileId;
    private String profileName;

    private Long instanceId;
    private String instanceName;

    private String service;
    private String request;
    private String workspace;
    private String layer;

    private GrantType access;

    public ShortRule()
    {
    }

    public ShortRule(Rule rule)
    {
        setId(rule.getId());
        setPriority(rule.getPriority());

        if (rule.getGsuser() != null)
        {
            setUserId(rule.getGsuser().getId());
            setUserName(rule.getGsuser().getName());
        }
        if (rule.getProfile() != null)
        {
            setProfileId(rule.getProfile().getId());
            setProfileName(rule.getProfile().getName());
        }
        if (rule.getInstance() != null)
        {
            setInstanceId(rule.getInstance().getId());
            setInstanceName(rule.getInstance().getName());
        }

        setService(rule.getService());
        setRequest(rule.getRequest());
        setWorkspace(rule.getWorkspace());
        setLayer(rule.getLayer());

        setAccess(rule.getAccess());
    }

    public GrantType getAccess()
    {
        return access;
    }

    public void setAccess(GrantType access)
    {
        this.access = access;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getInstanceId()
    {
        return instanceId;
    }

    public void setInstanceId(Long instanceId)
    {
        this.instanceId = instanceId;
    }

    public String getInstanceName()
    {
        return instanceName;
    }

    public void setInstanceName(String instanceName)
    {
        this.instanceName = instanceName;
    }

    public String getLayer()
    {
        return layer;
    }

    public void setLayer(String layer)
    {
        this.layer = layer;
    }

    public long getPriority()
    {
        return priority;
    }

    public void setPriority(long priority)
    {
        this.priority = priority;
    }

    public Long getProfileId()
    {
        return profileId;
    }

    public void setProfileId(Long profileId)
    {
        this.profileId = profileId;
    }

    public String getProfileName()
    {
        return profileName;
    }

    public void setProfileName(String profileName)
    {
        this.profileName = profileName;
    }

    public String getRequest()
    {
        return request;
    }

    public void setRequest(String request)
    {
        this.request = request;
    }

    public String getService()
    {
        return service;
    }

    public void setService(String service)
    {
        this.service = service;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getWorkspace()
    {
        return workspace;
    }

    public void setWorkspace(String workspace)
    {
        this.workspace = workspace;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("[id:").append(id).append(" pri:").append(
                priority);

        if (userId != null)
        {
            sb.append(" uId:").append(userId);
        }
        if (userName != null)
        {
            sb.append(" uName:").append(userName);
        }

        if (profileId != null)
        {
            sb.append(" pId:").append(profileId);
        }
        if (profileName != null)
        {
            sb.append(" pName:").append(profileName);
        }

        if (instanceId != null)
        {
            sb.append(" iId:").append(instanceId);
        }
        if (instanceName != null)
        {
            sb.append(" iName:").append(instanceName);
        }

        if (service != null)
        {
            sb.append(" srv:").append(service);
        }
        if (request != null)
        {
            sb.append(" req:").append(request);
        }

        if (workspace != null)
        {
            sb.append(" ws:").append(workspace);
        }
        if (layer != null)
        {
            sb.append(" l:").append(layer);
        }

        sb.append(" acc:").append(access);
        sb.append(']');

        return sb.toString();

    }


}
