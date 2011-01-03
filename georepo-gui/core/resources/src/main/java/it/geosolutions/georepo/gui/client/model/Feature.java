package it.geosolutions.georepo.gui.client.model;

import java.util.Date;

import it.geosolutions.georepo.gui.client.model.AOI.AOIKeyValue;
import com.extjs.gxt.ui.client.data.BeanModel;

/**
 * @author tobaro
 *
 */
public class Feature extends BeanModel{

	public enum FeatureKeyValue {
		ID("id"), DATE_CREATION("creationDate"), EXTERNAL_ID(
				"externalId"), EXTERNAL_SORTING_DATE("externalSortingDate"), USER(
				"user"), TITLE("title"), AOI("aoi"), WFS_RESPONSE_BLOB("wfsResponseBlob"), 
				LAST_SENT_BY_MAIL("lastSentByMail"), LAST_SENT_BY_RSS("lastSentByRss");

		private String value;

		FeatureKeyValue(String value) {
			this.value = value;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}
	}
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5790884760115450217L;
	
	private long id;
	private String title;
	private User user;
	private AOI aoi;
	private Date creationDate;

    /**
     * The ID of this feature in the source system.
     */
    private String externalId;

    /**
     * A Date attribute in the source system that is monotonically ascending.
     * <BR>We use it as a marker to retrieve only the newest features.
     */
    private Date externalSortingDate;
    
    private String wfsResponseBlob;
	private Date lastSentByMail;
	private Date lastSentByRSS;
//	private Polygon geometry;
	
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
		set(FeatureKeyValue.ID.getValue(), this.id);
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
		set(FeatureKeyValue.TITLE.getValue(), this.title);
	}
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
		set(FeatureKeyValue.USER.getValue(), this.user == null ? null
				: this.user.getUserName());
	}
	
	/**
	 * @return the aoi
	 */
	public AOI getAoi() {
		return aoi;
	}
	
	/**
	 * @param aoi the aoi to set
	 */
	public void setAoi(AOI aoi) {
		this.aoi = aoi;
		set(FeatureKeyValue.AOI.getValue(), this.aoi == null ? null
				: this.user.getName());
	}
	
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
		set(FeatureKeyValue.DATE_CREATION.getValue(), this.creationDate);
	}
	
	/**
	 * @return the externalId
	 */
	public String getExternalId() {
		return externalId;
	}
	
	/**
	 * @param externalId the externalId to set
	 */
	public void setExternalId(String externalId) {
		this.externalId = externalId;
		set(FeatureKeyValue.EXTERNAL_ID.getValue(), this.externalId);
	}
	
	/**
	 * @return the externalSortingDate
	 */
	public Date getExternalSortingDate() {
		return externalSortingDate;
	}
	
	/**
	 * @param externalSortingDate the externalSortingDate to set
	 */
	public void setExternalSortingDate(Date externalSortingDate) {
		this.externalSortingDate = externalSortingDate;
		set(FeatureKeyValue.EXTERNAL_SORTING_DATE.getValue(), this.externalSortingDate);
	}
	
	/**
	 * @return the wfsResponseBlob
	 */
	public String getWfsResponseBlob() {
		return wfsResponseBlob;
	}
	
	/**
	 * @param wfsResponseBlob the wfsResponseBlob to set
	 */
	public void setWfsResponseBlob(String wfsResponseBlob) {
		this.wfsResponseBlob = wfsResponseBlob;
		set(FeatureKeyValue.WFS_RESPONSE_BLOB.getValue(), this.wfsResponseBlob);
	}
	
	/**
	 * @return the lastSentByMail
	 */
	public Date getLastSentByMail() {
		return lastSentByMail;
	}
	
	/**
	 * @param lastSentByMail the lastSentByMail to set
	 */
	public void setLastSentByMail(Date lastSentByMail) {
		this.lastSentByMail = lastSentByMail;
		set(FeatureKeyValue.LAST_SENT_BY_MAIL.getValue(), this.lastSentByMail);
	}
	
	/**
	 * @return the lastSentByRSS
	 */
	public Date getLastSentByRSS() {
		return lastSentByRSS;
	}
	
	/**
	 * @param lastSentByRSS the lastSentByRSS to set
	 */
	public void setLastSentByRSS(Date lastSentByRSS) {
		this.lastSentByRSS = lastSentByRSS;
		set(FeatureKeyValue.LAST_SENT_BY_RSS.getValue(), this.lastSentByRSS);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Feature [id=" + id + ", title=" + title + ", user=" + user
				+ ", aoi=" + aoi + ", creationDate=" + creationDate
				+ ", externalId=" + externalId + ", externalSortingDate="
				+ externalSortingDate + ", wfsResponseBlob=" + wfsResponseBlob
				+ ", lastSentByMail=" + lastSentByMail + ", lastSentByRSS="
				+ lastSentByRSS + "]";
	}
}
