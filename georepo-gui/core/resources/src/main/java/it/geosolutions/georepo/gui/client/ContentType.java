package it.geosolutions.georepo.gui.client;

import com.extjs.gxt.ui.client.data.BeanModel;

/**
 * @author Tobia di Pisa
 *
 */
public class ContentType extends BeanModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5218642838039771231L;

	public enum ContentTypeEnum {
		TYPE("contentType");

		private String value;

		ContentTypeEnum(String value) {
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
	 * 
	 */
	public ContentType(String type) {
		setType(type);
	}

	public void setType(String type) {
		set(ContentTypeEnum.TYPE.getValue(), type);
	}

	public String getType() {
		return get(ContentTypeEnum.TYPE.getValue());
	}
}
