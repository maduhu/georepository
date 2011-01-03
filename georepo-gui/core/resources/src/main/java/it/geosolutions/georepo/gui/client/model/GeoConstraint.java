package it.geosolutions.georepo.gui.client.model;

import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.mvc.Dispatcher;

/**
 * GWT model object that represents a GeoConstraint.
 *
 * @author gmurray
 * 
 */
public class GeoConstraint extends BeanModel {

    public enum GeoConstraintKeyValue {
        ID("id"),
        NAME("name"),
        GEOCONSTRAINT("geoConstraint");

        private String value;

        GeoConstraintKeyValue(String value) {
            this.value = value;
        }

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }

    }

	private static final long serialVersionUID = -483120103393713423L;

	private Integer id;
	private String name;
	private String wkt;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
		set(GeoConstraintKeyValue.ID.getValue(), this.id);
	}

	/**
	 * @return the title
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the title to set
	 */
	public void setName(String name) {
		this.name = name;
		set(GeoConstraintKeyValue.NAME.getValue(), this.name);
	}

	/**
	 * @return the wkt
	 */
	public String getWkt() {
		return wkt;
	}

	/**
	 * @param wkt
	 *            the wkt to set
	 */
	public void setWkt(String wkt) {
		this.wkt = wkt;
        set(GeoConstraintKeyValue.GEOCONSTRAINT.getValue(), this.wkt);
	}

//	/**
//	 *            the aoi to set
//	 */
//	public void setGeoConstraint() {
//		set(GeoConstraintKeyValue.GEOCONSTRAINT.getValue(), this.id + " - " + this.name);
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeoConstraint other = (GeoConstraint) obj;
        return id == other.id;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  "GeoConstraint [id=" + id + ", name=" + name + "]";
	}

    public void notifyState() {
        Dispatcher.forwardEvent(DGWATCHEvents.GEOCONSTRAINT_SELECTED, this);
    }

}