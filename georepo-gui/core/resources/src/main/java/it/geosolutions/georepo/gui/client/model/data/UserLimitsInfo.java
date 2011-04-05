package it.geosolutions.georepo.gui.client.model.data;

import it.geosolutions.georepo.gui.client.model.BeanKeyValue;

import com.extjs.gxt.ui.client.data.BeanModel;

/**
 * Class UserInfo.
 * 
 * @author Tobia di Pisa
 *
 */
public class UserLimitsInfo extends BeanModel{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1760111388826438170L;
    
    private Long userId;

    private String allowedArea;

    /**
     * Instantiates a new limits.
     */
    public UserLimitsInfo() {
        super();
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the allowedArea
     */
    public String getAllowedArea() {
        return allowedArea;
    }

    /**
     * @param allowedArea the allowedArea to set
     */
    public void setAllowedArea(String allowedArea) {
        this.allowedArea = allowedArea;
        set(BeanKeyValue.USER_ALLOWED_AREA.getValue(), this.allowedArea);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((allowedArea == null) ? 0 : allowedArea.hashCode());
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
        if (!(obj instanceof Grant)) {
            return false;
        }

        UserLimitsInfo other = (UserLimitsInfo) obj;

        if (userId == null) {
            if (other.userId != null) {
                return false;
            }
        } else if (!userId.equals(other.userId)) {
            return false;
        }

        if (allowedArea == null) {
            if (other.allowedArea != null) {
                return false;
            }
        } else if (!allowedArea.equals(other.allowedArea)) {
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
        builder.append("LayerLimitsForm [");

        if (userId != null)
            builder.append("userId=").append(userId).append(", ");
        if (allowedArea != null)
            builder.append("allowedArea=").append(allowedArea).append(", ");

        builder.append("]");
        return builder.toString();
    }

}
