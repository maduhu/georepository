package it.geosolutions.georepo.gui.client.widget;

import it.geosolutions.georepo.gui.client.model.ClientShortWatch;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

/**
 * @author Tobia di Pisa
 *
 */
public class GridWatchButton extends Button{
	
	public GridWatchButton() {
		super();
	}

	public GridWatchButton(String text, AbstractImagePrototype icon,
			SelectionListener<ButtonEvent> listener) {
		super(text, icon, listener);
	}

	public GridWatchButton(String text, AbstractImagePrototype icon) {
		super(text, icon);
	}

	public GridWatchButton(String text,
			SelectionListener<ButtonEvent> listener) {
		super(text, listener);
	}

	public GridWatchButton(String text) {
		super(text);
	}

	private ClientShortWatch clientShortWatch;

	/**
	 * @return the clientShortWatch
	 */
	public ClientShortWatch getClientShortWatch() {
		return clientShortWatch;
	}

	/**
	 * @param clientShortWatch the clientShortWatch to set
	 */
	public void setClientShortWatch(ClientShortWatch clientShortWatch) {
		this.clientShortWatch = clientShortWatch;
	}

	

}
