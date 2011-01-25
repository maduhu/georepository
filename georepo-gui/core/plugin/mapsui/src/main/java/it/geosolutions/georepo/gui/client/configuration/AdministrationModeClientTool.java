/*
 * $ Header: it.geosolutions.georepo.gui.client.configuration.AdministrationModeClientTool,v. 0.1 25-gen-2011 11.30.33 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.30.33 $
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
package it.geosolutions.georepo.gui.client.configuration;

import it.geosolutions.georepo.gui.client.AdministrationMode;
import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.Authorization;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;

// TODO: Auto-generated Javadoc
/**
 * The Class AdministrationModeClientTool.
 */
public class AdministrationModeClientTool extends DropdownClientTool implements Listener<AppEvent> {

    /**
     * Inits the.
     */
    public void init() {
        addOption(new DropdownOption(AdministrationMode.NOTIFICATION_DISTRIBUTION.name(),
                AdministrationMode.NOTIFICATION_DISTRIBUTION.name()));
        addOption(new DropdownOption(AdministrationMode.GEOCONSTRAINTS.name(),
                AdministrationMode.GEOCONSTRAINTS.name()));
        addOption(new DropdownOption(AdministrationMode.MEMBER.name(), AdministrationMode.MEMBER
                .name()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.extjs.gxt.ui.client.event.Listener#handleEvent(com.extjs.gxt.ui.client.event.BaseEvent)
     */
    public void handleEvent(AppEvent e) {
        if (e.getType() == GeoRepoEvents.LOGIN_SUCCESS) {

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.client.configuration.DropdownClientTool#getDropdownOptionDisplayValue
     * (java.lang.String)
     */
    @Override
    public String getDropdownOptionDisplayValue(String dataValue) {
        AdministrationMode adminMode = AdministrationMode.valueOf(dataValue);
        switch (adminMode) {
        case NOTIFICATION_DISTRIBUTION:
            return I18nProvider.getMessages().adminModeNotificationDistribution();
        case GEOCONSTRAINTS:
            return I18nProvider.getMessages().adminModeGeoConstraints();
        case MEMBER:
            return I18nProvider.getMessages().adminModeMember();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.client.configuration.DropdownClientTool#injectSecurity(com.extjs
     * .gxt.ui.client.widget.form.ComboBox, java.util.List)
     */
    @Override
    public void injectSecurity(ComboBox combo, List<Authorization> auths) {
        if ((auths == null) || auths.isEmpty()) {
            return;
        }

        ListStore<ModelData> store = combo.getStore();
        ModelData origSelected = combo.getValue();

        // if we remove the currently selected item, we'll also need to send an ADMIN_MODE_CHANGE
        // to the rest of the GUI
        boolean changeSelectedItem = false;

        for (ModelData md : store.getModels()) {
            String value = md.get(DropdownOption.VALUE_KEY);

            if (AdministrationMode.NOTIFICATION_DISTRIBUTION.name().equals(value)) {
                if (!auths.contains(Authorization.NOTIFICATION)
                        && !auths.contains(Authorization.DISTRIBUTION)) {
                    if (md == origSelected) {
                        changeSelectedItem = true;
                    }
                    store.remove(md);
                }
            } else if (AdministrationMode.GEOCONSTRAINTS.name().equals(value)) {
                if (!auths.contains(Authorization.GEOCONSTRAINT)) {
                    if (md == origSelected) {
                        changeSelectedItem = true;
                    }
                    store.remove(md);
                }
            } else if (AdministrationMode.MEMBER.name().equals(value)) {
                if (!auths.contains(Authorization.MEMBER_DISTRIBUTION)) {
                    if (md == origSelected) {
                        changeSelectedItem = true;
                    }
                    store.remove(md);
                }
            }
        }

        if (changeSelectedItem) {
            combo.select(0);
            ModelData selectedItem = (ModelData) combo.getSelection().get(0);
            Dispatcher.forwardEvent(GeoRepoEvents.ADMIN_MODE_CHANGE, AdministrationMode
                    .valueOf(selectedItem.<String> get(DropdownOption.VALUE_KEY)));
        }
    }
}
