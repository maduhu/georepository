/**
 * 
 */
package it.geosolutions.georepo.gui.client.i18n;

import com.google.gwt.core.client.GWT;

/**
 * @author giuseppe
 * 
 */
public final class I18nProvider {

	private static final ApplicationMessages MESSAGES = GWT
			.create(ApplicationMessages.class);

	public static ApplicationMessages getMessages() {
		return MESSAGES;
	}

}
