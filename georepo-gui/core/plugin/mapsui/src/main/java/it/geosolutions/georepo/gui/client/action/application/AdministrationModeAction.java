/*
 * $ Header: it.geosolutions.georepo.gui.client.action.application.AdministrationModeAction,v. 0.1 14-gen-2011 19.28.38 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 14-gen-2011 19.28.38 $
 *
 * ====================================================================
 *
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 * http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
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
package it.geosolutions.georepo.gui.client.action.application;

import it.geosolutions.georepo.gui.client.AdministrationMode;
import it.geosolutions.georepo.gui.client.Category;
import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.action.ToolbarApplicationAction;
import it.geosolutions.georepo.gui.client.configuration.DropdownOption;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.form.ComboBox;

// TODO: Auto-generated Javadoc
/**
 * The Class AdministrationModeAction.
 */
public class AdministrationModeAction extends ToolbarApplicationAction {

    /**
     * Instantiates a new administration mode action.
     */
    public AdministrationModeAction() {
        super(I18nProvider.getMessages().adminModeLabel(), Category.ADMINISTRATION_MODE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.extjs.gxt.ui.client.event.Listener#handleEvent(com.extjs.gxt.ui.client.event.BaseEvent)
     */
    public void handleEvent(BaseEvent be) {
        ComboBox source = (ComboBox) be.getSource();
        ModelData selectedModel = source.getValue();
        AdministrationMode adminMode = AdministrationMode.valueOf(selectedModel.get(
                DropdownOption.VALUE_KEY).toString());
        assert adminMode != null : "invalid AdministrationMode: " + adminMode;

        Dispatcher.forwardEvent(GeoRepoEvents.ADMIN_MODE_CHANGE, adminMode);
    }

}