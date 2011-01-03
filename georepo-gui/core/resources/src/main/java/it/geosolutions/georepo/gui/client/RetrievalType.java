package it.geosolutions.georepo.gui.client;

import com.extjs.gxt.ui.client.data.BeanModel;

public class RetrievalType extends BeanModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 578387084077752687L;

	public enum RetrievalTypeEnum {
		TYPE("retrievalType");

		private String value;

		RetrievalTypeEnum(String value) {
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
	public RetrievalType(String type) {
		setType(type);
	}

	public void setType(String type) {
		set(RetrievalTypeEnum.TYPE.getValue(), type);
	}

	public String getType() {
		return get(RetrievalTypeEnum.TYPE.getValue());
	}
}
