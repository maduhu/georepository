/**
 * 
 */
package it.geosolutions.georepo.gui.client.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.data.BeanModel;

/**
 * @author Alessio
 *
 */
public class Profile extends BeanModel {

    /**
     * 
     */
    private static final long serialVersionUID = 3475163929906592234L;

    private long id;

    /** The name. */
    private String name;

    /** The date creation. */
    private Date dateCreation;

    /** The enabled. */
    private boolean enabled;

    /** Custom properties associated to the profile */
    private Map<String, String> customProps = new HashMap<String, String>();

    /** The path. */
    private String path;
    
    public Profile() {
        setPath("georepo/resources/images/profile.jpg");
    }
    
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the dateCreation
     */
    public Date getDateCreation() {
        return dateCreation;
    }

    /**
     * @param dateCreation the dateCreation to set
     */
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the customProps
     */
    public Map<String, String> getCustomProps() {
        return customProps;
    }

    /**
     * @param customProps the customProps to set
     */
    public void setCustomProps(Map<String, String> customProps) {
        this.customProps = customProps;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customProps == null) ? 0 : customProps.hashCode());
        result = prime * result + ((dateCreation == null) ? 0 : dateCreation.hashCode());
        result = prime * result + (enabled ? 1231 : 1237);
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Profile)) {
            return false;
        }
        Profile other = (Profile) obj;
        if (customProps == null) {
            if (other.customProps != null) {
                return false;
            }
        } else if (!customProps.equals(other.customProps)) {
            return false;
        }
        if (dateCreation == null) {
            if (other.dateCreation != null) {
                return false;
            }
        } else if (!dateCreation.equals(other.dateCreation)) {
            return false;
        }
        if (enabled != other.enabled) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (path == null) {
            if (other.path != null) {
                return false;
            }
        } else if (!path.equals(other.path)) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Profile [");
        if (customProps != null)
            builder.append("customProps=").append(customProps).append(", ");
        if (dateCreation != null)
            builder.append("dateCreation=").append(dateCreation).append(", ");
        builder.append("enabled=").append(enabled).append(", id=").append(id).append(", ");
        if (name != null)
            builder.append("name=").append(name).append(", ");
        if (path != null)
            builder.append("path=").append(path);
        builder.append("]");
        return builder.toString();
    }
}
