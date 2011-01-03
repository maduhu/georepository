/*
 * $Header: it.geosolutions.georepo.gui.client.action.menu.RunTrigger24,v. 0.1 30/set/2010 17.08.25 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 30/set/2010 17.08.25 $
 *
 * ====================================================================
 *
 * Copyright (C) 2010 GeoSolutions S.A.S.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. 
 *
 * ====================================================================
 *
 * This software consists of voluntary contributions made by developers
 * of GeoSolutions.  For more information on GeoSolutions, please see
 * <http://www.geo-solutions.it/>.
 *
 */
package it.geosolutions.georepo.gui.client.action.menu;

import it.geosolutions.georepo.gui.client.Category;
import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;

/**
 * @author giuseppe
 * 
 */
public class RunTrigger24 extends MenuAction {

	public RunTrigger24() {
		super(I18nProvider.getMessages().quartzTriggerMenuOption24Hours(), Category.CLOCK);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.extjs.gxt.ui.client.event.SelectionListener#componentSelected(com
	 * .extjs.gxt.ui.client.event.ComponentEvent)
	 */
	@Override
	public void componentSelected(MenuEvent ce) {
		// TODO Auto-generated method stub
		Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE, new String[] {
				I18nProvider.getMessages().quartzTriggerNotificationTitle(),
                I18nProvider.getMessages().quartzTriggerMenuOption24Hours() });
		Dispatcher.forwardEvent(DGWATCHEvents.QUARTZ_TRIGGER, new Integer(24));
	}

}
