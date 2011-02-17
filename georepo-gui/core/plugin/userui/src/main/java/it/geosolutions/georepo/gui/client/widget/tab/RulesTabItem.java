/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.tab.RulesTabItem,v. 0.1 25-gen-2011 12.22.14 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 12.22.14 $
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
package it.geosolutions.georepo.gui.client.widget.tab;

import it.geosolutions.georepo.gui.client.Resources;
import it.geosolutions.georepo.gui.client.service.GsUsersManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.InstancesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.WorkspacesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.RuleManagementWidget;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.TabItem;

// TODO: Auto-generated Javadoc
/**
 * The Class RulesTabItem.
 */
public class RulesTabItem extends TabItem {

    /** The rules management widget. */
    private RuleManagementWidget rulesManagementWidget;

    /**
     * Instantiates a new rules tab item.
     */
    public RulesTabItem(String tabItemId) {
        // TODO: add I18n message
        // super(I18nProvider.getMessages().profiles());
        super("Rules");
        setId(tabItemId);
        setIcon(Resources.ICONS.table());
    }

    /**
     * Instantiates a new rules tab item.
     * @param tabItemId 
     * 
     * @param rulesManagerServiceRemote
     *            the rules manager service remote
     */
    public RulesTabItem(String tabItemId, RulesManagerServiceRemoteAsync rulesService,
            GsUsersManagerServiceRemoteAsync gsUsersService,
            ProfilesManagerServiceRemoteAsync profilesService,
            InstancesManagerServiceRemoteAsync instancesService,
            WorkspacesManagerServiceRemoteAsync workspacesService) {
        this(tabItemId);
        setRuleManagementWidget(new RuleManagementWidget(rulesService, gsUsersService,
                profilesService, instancesService, workspacesService));
        add(getRuleManagementWidget());

        setScrollMode(Scroll.NONE);
        //setHeight("100%");
        getRuleManagementWidget().getRulesInfo().getLoader().load(0, 25);

    }

    /**
     * Sets the rule management widget.
     * 
     * @param rulesManagementWidget
     *            the new rule management widget
     */
    public void setRuleManagementWidget(RuleManagementWidget rulesManagementWidget) {
        this.rulesManagementWidget = rulesManagementWidget;
    }

    /**
     * Gets the rule management widget.
     * 
     * @return the rule management widget
     */
    public RuleManagementWidget getRuleManagementWidget() {
        return rulesManagementWidget;
    }

}
