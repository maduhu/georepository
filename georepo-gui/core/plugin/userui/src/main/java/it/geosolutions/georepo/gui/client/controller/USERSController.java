/*
 * $ Header: it.geosolutions.georepo.gui.client.controller.USERSController,v. 0.1 31-gen-2011 13.41.27 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 31-gen-2011 13.41.27 $
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
package it.geosolutions.georepo.gui.client.controller;

import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.BeanKeyValue;
import it.geosolutions.georepo.gui.client.model.GSInstance;
import it.geosolutions.georepo.gui.client.model.GSUser;
import it.geosolutions.georepo.gui.client.model.Profile;
import it.geosolutions.georepo.gui.client.model.Rule;
import it.geosolutions.georepo.gui.client.service.GsUsersManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.GsUsersManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.InstancesManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.InstancesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.WorkspacesManagerServiceRemote;
import it.geosolutions.georepo.gui.client.service.WorkspacesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.RuleGridWidget;
import it.geosolutions.georepo.gui.client.widget.tab.GsUsersTabItem;
import it.geosolutions.georepo.gui.client.widget.tab.ProfilesTabItem;
import it.geosolutions.georepo.gui.client.widget.tab.RulesTabItem;
import it.geosolutions.georepo.gui.client.widget.tab.TabWidget;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class USERSController.
 */
public class USERSController extends Controller {

    /** The Constant USERS_TAB_ITEM_ID. */
    private static final String USERS_TAB_ITEM_ID = "UsersTabItem";

    /** The Constant PROFILES_TAB_ITEM_ID. */
    private static final String PROFILES_TAB_ITEM_ID = "ProfilesTabItem";

    /** The Constant RULES_TAB_ITEM_ID. */
    private static final String RULES_TAB_ITEM_ID = "RulesTabItem";

    /** The gs manager service remote. */
    private GsUsersManagerServiceRemoteAsync gsManagerServiceRemote = GsUsersManagerServiceRemote.Util
            .getInstance();

    /** The profiles manager service remote. */
    private ProfilesManagerServiceRemoteAsync profilesManagerServiceRemote = ProfilesManagerServiceRemote.Util
            .getInstance();

    /** The instances manager service remote. */
    private InstancesManagerServiceRemoteAsync instancesManagerServiceRemote = InstancesManagerServiceRemote.Util
            .getInstance();

    /** The workspaces manager service remote. */
    private WorkspacesManagerServiceRemoteAsync workspacesManagerServiceRemote = WorkspacesManagerServiceRemote.Util
            .getInstance();

    /** The rules manager service remote. */
    private RulesManagerServiceRemoteAsync rulesManagerServiceRemote = RulesManagerServiceRemote.Util
            .getInstance();

    /** The tab widget. */
    private TabWidget tabWidget;

    // /** The feature remote. */
    // private FeatureServiceRemoteAsync featureRemote = FeatureServiceRemote.Util.getInstance();

    // /** The tab widget. */
    // private TabWidget tabWidget;

    // /** The aoi search widget. */
    // private GeoRepoSearchWidget<AOI> aoiSearchWidget;
    //
    // /** The geo constraint search widget. */
    // private GeoRepoSearchWidget<GeoConstraint> geoConstraintSearchWidget;

    /**
     * Instantiates a new uSERS controller.
     */
    public USERSController() {
        registerEventTypes(GeoRepoEvents.INIT_MAPS_UI_MODULE,
                GeoRepoEvents.ATTACH_BOTTOM_TAB_WIDGETS, GeoRepoEvents.RULE_UPDATE_GRID_COMBO,
                GeoRepoEvents.RULE_APPLY_CHANGES_GRID_COMBO, GeoRepoEvents.RULE_ADD,
                GeoRepoEvents.RULE_DEL, GeoRepoEvents.RULE_PRIORITY_UP,
                GeoRepoEvents.RULE_PRIORITY_DOWN);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.Controller#initialize()
     */
    @Override
    protected void initialize() {
        initWidget();
    }

    /**
     * Inits the widget.
     */
    private void initWidget() {
        // this.aoiSearchWidget = new SearchPagAOIWidget(this.aoiRemote);
        // this.geoConstraintSearchWidget = new SearchPagingGeoConstraintWidget(this.membersRemote);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.Controller#handleEvent(com.extjs.gxt.ui.client
     * .mvc.AppEvent)
     */
    @Override
    public void handleEvent(AppEvent event) {
        if (event.getType() == GeoRepoEvents.ATTACH_BOTTOM_TAB_WIDGETS)
            onAttachTabWidgets(event);

        if (event.getType() == GeoRepoEvents.RULE_UPDATE_GRID_COMBO)
            onUpdateRuleRequestsCombo(event);

        if (event.getType() == GeoRepoEvents.RULE_APPLY_CHANGES_GRID_COMBO)
            onApplyChangesRules(event);

        if (event.getType() == GeoRepoEvents.RULE_ADD)
            onAddRule(event);

        if (event.getType() == GeoRepoEvents.RULE_DEL)
            onRemoveRule(event);

        if (event.getType() == GeoRepoEvents.RULE_PRIORITY_UP)
            onRulePriorityUp(event);

        if (event.getType() == GeoRepoEvents.RULE_PRIORITY_DOWN)
            onRulePriorityDown(event);

        // forwardToView(aoiView, event);
    }

    /**
     * On attach tab widgets.
     * 
     * @param event
     *            the event
     */
    private void onAttachTabWidgets(AppEvent event) {
        if (tabWidget == null) {
            tabWidget = (TabWidget) event.getData();
            tabWidget.add(new GsUsersTabItem(USERS_TAB_ITEM_ID, gsManagerServiceRemote,
                    profilesManagerServiceRemote));
            tabWidget.add(new ProfilesTabItem(PROFILES_TAB_ITEM_ID, profilesManagerServiceRemote));
            tabWidget.add(new RulesTabItem(RULES_TAB_ITEM_ID, rulesManagerServiceRemote,
                    gsManagerServiceRemote, profilesManagerServiceRemote,
                    instancesManagerServiceRemote, workspacesManagerServiceRemote));
        }
    }

    /**
     * On update rule requests combo.
     * 
     * @param event
     *            the event
     */
    private void onUpdateRuleRequestsCombo(AppEvent event) {
        if (tabWidget != null) {
            Object tabData = event.getData();

            if (tabData instanceof Rule) {
                Rule model = (Rule) tabData;

                RulesTabItem rulesTabItem = (RulesTabItem) tabWidget
                        .getItemByItemId(RULES_TAB_ITEM_ID);
                final RuleGridWidget rulesInfoWidget = rulesTabItem.getRuleManagementWidget()
                        .getRulesInfo();
                final Grid<Rule> grid = rulesInfoWidget.getGrid();
                grid.getStore().remove(model);
                grid.getStore().add(model);
                grid.repaint();
            }
        }
    }

    /**
     * On apply changes rules.
     * 
     * @param event
     *            the event
     */
    private void onApplyChangesRules(AppEvent event) {
        if (tabWidget != null) {

            RulesTabItem rulesTabItem = (RulesTabItem) tabWidget.getItemByItemId(RULES_TAB_ITEM_ID);
            final RuleGridWidget rulesInfoWidget = rulesTabItem.getRuleManagementWidget()
                    .getRulesInfo();
            final Grid<Rule> grid = rulesInfoWidget.getGrid();

            if (grid != null && grid.getStore() != null) {
                ListStore<Rule> store = grid.getStore();

                if (store != null && store.getModels() != null && store.getModels().size() > 0) {
                    rulesManagerServiceRemote.saveAllRules(store.getModels(),
                            new AsyncCallback<PagingLoadResult<Rule>>() {

                                public void onFailure(Throwable caught) {

                                    Dispatcher
                                            .forwardEvent(
                                                    GeoRepoEvents.SEND_ERROR_MESSAGE,
                                                    new String[] {
                                                            I18nProvider.getMessages()
                                                                    .ruleServiceName(),
                                                            I18nProvider
                                                                    .getMessages()
                                                                    .ruleFetchFailureMessage() });
                                }

                                public void onSuccess(PagingLoadResult<Rule> result) {

                                    Dispatcher.forwardEvent(
                                            GeoRepoEvents.BIND_MEMBER_DISTRIBUTION_NODES, result);
                                    Dispatcher
                                            .forwardEvent(
                                                    GeoRepoEvents.SEND_INFO_MESSAGE,
                                                    new String[] {
                                                            I18nProvider.getMessages()
                                                                    .ruleServiceName(),
                                                            I18nProvider
                                                                    .getMessages()
                                                                    .ruleFetchSuccessMessage() });
                                }
                            });
                }
            }

            grid.getStore().sort(BeanKeyValue.PRIORITY.getValue(), SortDir.ASC);
            grid.repaint();
        }
    }

    /**
     * On remove rule.
     * 
     * @param event
     *            the event
     */
    private void onRemoveRule(AppEvent event) {
        if (tabWidget != null) {
            Object tabData = event.getData();

            if (tabData instanceof Rule) {
                Rule model = (Rule) tabData;
                RulesTabItem rulesTabItem = (RulesTabItem) tabWidget
                        .getItemByItemId(RULES_TAB_ITEM_ID);
                final RuleGridWidget rulesInfoWidget = rulesTabItem.getRuleManagementWidget()
                        .getRulesInfo();
                final Grid<Rule> grid = rulesInfoWidget.getGrid();

                if (grid != null && grid.getStore() != null) {
                    ListStore<Rule> store = grid.getStore();

                    if (store != null && store.getModels() != null && store.getModels().size() > 0) {
                        store.remove(model);

                        for (Rule r : store.getModels()) {
                            if (r.getPriority() > model.getPriority()) {
                                grid.getStore().remove(r);
                                r.setPriority(r.getPriority() - 1);
                                grid.getStore().add(r);
                            }
                        }
                    }
                }

                grid.getStore().sort(BeanKeyValue.PRIORITY.getValue(), SortDir.ASC);
                grid.repaint();
            }

        }
    }

    /**
     * On add rule.
     * 
     * @param event
     *            the event
     */
    private void onAddRule(AppEvent event) {
        if (tabWidget != null) {
            Object tabData = event.getData();

            if (tabData instanceof Rule) {
                Rule model = (Rule) tabData;

                RulesTabItem rulesTabItem = (RulesTabItem) tabWidget
                        .getItemByItemId(RULES_TAB_ITEM_ID);
                final RuleGridWidget rulesInfoWidget = rulesTabItem.getRuleManagementWidget()
                        .getRulesInfo();
                final Grid<Rule> grid = rulesInfoWidget.getGrid();

                for (Rule r : grid.getStore().getModels()) {
                    if (r.getPriority() > model.getPriority()) {
                        grid.getStore().remove(r);
                        r.setPriority(r.getPriority() + 1);
                        grid.getStore().add(r);
                    }
                }

                Rule new_rule = createNewRule(model);

                grid.getStore().add(new_rule);
                grid.getStore().sort(BeanKeyValue.PRIORITY.getValue(), SortDir.ASC);

                grid.repaint();
            }
        }
    }

    /**
     * On rule priority up.
     * 
     * @param event
     *            the event
     */
    private void onRulePriorityUp(AppEvent event) {
        if (tabWidget != null) {
            Object tabData = event.getData();

            if (tabData instanceof Rule) {
                Rule model = (Rule) tabData;

                RulesTabItem rulesTabItem = (RulesTabItem) tabWidget
                        .getItemByItemId(RULES_TAB_ITEM_ID);
                final RuleGridWidget rulesInfoWidget = rulesTabItem.getRuleManagementWidget()
                        .getRulesInfo();
                final Grid<Rule> grid = rulesInfoWidget.getGrid();

                if (model.getPriority() > 0) {
                    for (Rule r : grid.getStore().getModels()) {
                        if (!r.equals(model) && r.getPriority() >= model.getPriority() - 1) {
                            grid.getStore().remove(r);
                            r.setPriority(r.getPriority() + 1);
                            grid.getStore().add(r);
                        }
                    }

                    grid.getStore().remove(model);
                    model.setPriority(model.getPriority() - 1);
                    grid.getStore().add(model);
                    grid.getStore().sort(BeanKeyValue.PRIORITY.getValue(), SortDir.ASC);

                    grid.repaint();
                }
            }
        }
    }

    /**
     * On rule priority down.
     * 
     * @param event
     *            the event
     */
    private void onRulePriorityDown(AppEvent event) {
        if (tabWidget != null) {
            Object tabData = event.getData();

            if (tabData instanceof Rule) {
                Rule model = (Rule) tabData;

                RulesTabItem rulesTabItem = (RulesTabItem) tabWidget
                        .getItemByItemId(RULES_TAB_ITEM_ID);
                final RuleGridWidget rulesInfoWidget = rulesTabItem.getRuleManagementWidget()
                        .getRulesInfo();
                final Grid<Rule> grid = rulesInfoWidget.getGrid();

                if (model.getPriority() < grid.getStore().getModels().size() - 1) {
                    for (Rule r : grid.getStore().getModels()) {
                        if (!r.equals(model) && r.getPriority() == model.getPriority() + 1) {
                            grid.getStore().remove(r);
                            r.setPriority(r.getPriority() - 1);
                            grid.getStore().add(r);
                        }
                    }

                    grid.getStore().remove(model);
                    model.setPriority(model.getPriority() + 1);
                    grid.getStore().add(model);
                    grid.getStore().sort(BeanKeyValue.PRIORITY.getValue(), SortDir.ASC);

                    grid.repaint();
                }
            }
        }
    }

    /**
     * Creates the new rule.
     * 
     * @param model
     *            the model
     * @return the rule
     */
    private Rule createNewRule(Rule model) {
        Rule new_rule = new Rule();
        new_rule.setPriority(model.getPriority() + 1);
        GSUser all_user = new GSUser();
        all_user.setId(-1);
        all_user.setName("*");
        new_rule.setUser(all_user);
        Profile all_profile = new Profile();
        all_profile.setId(-1);
        all_profile.setName("*");
        new_rule.setProfile(all_profile);
        GSInstance all_instance = new GSInstance();
        all_instance.setId(-1);
        all_instance.setName("*");
        all_instance.setBaseURL("*");
        new_rule.setInstance(all_instance);
        new_rule.setGrant("ALLOW");
        new_rule.setService("*");
        return new_rule;
    }

    /**
     * Forward to tab widget.
     * 
     * @param event
     *            the event
     */
    private void forwardToTabWidget(AppEvent event) {
        // this.tabWidget.fireEvent(event.getType(), event);
    }

}
