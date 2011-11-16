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

package it.geosolutions.georepo.services.rest.model;

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import it.geosolutions.georepo.core.model.adapter.MapAdapter;


/**
 * A compact representation of Profile holding only the insertable/updatadable fields
 *
 * @author Etj (etj at geo-solutions.it)
 */
@XmlRootElement(name = "Profile")
public class RESTInputProfile implements Serializable
{

    private static final long serialVersionUID = -8410646966443187827L;

    private String name;
    private String extId;
    private Boolean enabled;

    private Map<String, String> customProps;

    public RESTInputProfile()
    {
    }

    public Boolean getEnabled()
    {
        return enabled;
    }

    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }

    public String getExtId()
    {
        return extId;
    }

    public void setExtId(String extId)
    {
        this.extId = extId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @XmlJavaTypeAdapter(MapAdapter.class)
    public Map<String, String> getCustomProps()
    {
        return customProps;
    }

    public void setCustomProps(Map<String, String> customProps)
    {
        this.customProps = customProps;
    }


    @Override
    public String toString()
    {
        return getClass().getSimpleName() +
            "[extid=" + extId +
            " name=" + name +
            " enabled=" + enabled +
            " cprops=" + ((customProps == null) ? "-" : customProps.keySet()) +
            ']';
    }

}
