/*
 * $ Header: it.geosolutions.georepo.gui.client.controller.RulesController,v. 0.1 10-feb-2011 11.33.31 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 10-feb-2011 11.33.31 $
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.google.gwt.user.client.rpc.AsyncCallback;

import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.BeanKeyValue;
import it.geosolutions.georepo.gui.client.model.Rule;
import it.geosolutions.georepo.gui.client.service.GsUsersManagerRemoteServiceAsync;
import it.geosolutions.georepo.gui.client.service.InstancesManagerRemoteServiceAsync;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerRemoteServiceAsync;
import it.geosolutions.georepo.gui.client.service.RulesManagerRemoteServiceAsync;
import it.geosolutions.georepo.gui.client.service.WorkspacesManagerRemoteServiceAsync;
import it.geosolutions.georepo.gui.client.view.RulesView;
import it.geosolutions.georepo.gui.client.widget.RuleGridWidget;
import it.geosolutions.georepo.gui.client.widget.tab.RulesTabItem;
import it.geosolutions.georepo.gui.client.widget.tab.TabWidget;


/**
 * The Class RulesController.
 */
public class RulesController extends Controller
{

    // final private static Logger LOGGER = Logger.getLogger(RulesController.class);
    /** The Constant RULES_TAB_ITEM_ID. */
    private static final String RULES_TAB_ITEM_ID = "RulesTabItem";

    /** The gs manager service remote. */
    private GsUsersManagerRemoteServiceAsync gsManagerServiceRemote =
        GsUsersManagerRemoteServiceAsync.Util.getInstance();

    /** The profiles manager service remote. */
    private ProfilesManagerRemoteServiceAsync profilesManagerServiceRemote =
        ProfilesManagerRemoteServiceAsync.Util.getInstance();

    /** The instances manager service remote. */
    private InstancesManagerRemoteServiceAsync instancesManagerServiceRemote =
        InstancesManagerRemoteServiceAsync.Util.getInstance();

    /** The workspaces manager service remote. */
    private WorkspacesManagerRemoteServiceAsync workspacesManagerServiceRemote =
        WorkspacesManagerRemoteServiceAsync.Util.getInstance();

    /** The rules manager service remote. */
    private RulesManagerRemoteServiceAsync rulesManagerServiceRemote =
        RulesManagerRemoteServiceAsync.Util.getInstance();

    /** The tab widget. */
    private TabWidget tabWidget;

    /** The rules view. */
    private RulesView rulesView;

    /**
     * Instantiates a new rules controller.
     */
    public RulesController()
    {
        registerEventTypes(GeoRepoEvents.INIT_MAPS_UI_MODULE,
            GeoRepoEvents.ATTACH_BOTTOM_TAB_WIDGETS,




            GeoRepoEvents.RULE_UPDATE_GRID_COMBO, GeoRepoEvents.RULE_UPDATE_EDIT_GRID_COMBO,
            GeoRepoEvents.RULE_APPLY_CHANGES_GRID_COMBO, GeoRepoEvents.RULE_ADD,
            GeoRepoEvents.RULE_DEL, GeoRepoEvents.RULE_PRIORITY_UP,
            GeoRepoEvents.RULE_PRIORITY_DOWN, GeoRepoEvents.RULE_SAVE,




            GeoRepoEvents.EDIT_RULE_DETAILS, GeoRepoEvents.EDIT_RULE,
            GeoRepoEvents.EDIT_RULE_UPDATE, GeoRepoEvents.RULE_CUSTOM_PROP_ADD,
            GeoRepoEvents.RULE_CUSTOM_PROP_DEL, GeoRepoEvents.RULE_CUSTOM_PROP_UPDATE_KEY,
            GeoRepoEvents.RULE_CUSTOM_PROP_UPDATE_VALUE,
            GeoRepoEvents.RULE_CUSTOM_PROP_APPLY_CHANGES,




            GeoRepoEvents.INJECT_WKT,




            GeoRepoEvents.ATTRIBUTE_UPDATE_GRID_COMBO,




            GeoRepoEvents.SAVE_LAYER_DETAILS, GeoRepoEvents.LOAD_LAYER_DETAILS,




            GeoRepoEvents.AVAILABLE_STYLES_UPDATE_GRID,




            GeoRepoEvents.RULE_PROFILE_CUSTOM_PROP_UPDATE_KEY,
            GeoRepoEvents.RULE_PROFILE_CUSTOM_PROP_UPDATE_VALUE,
            GeoRepoEvents.RULE_PROFILE_CUSTOM_PROP_DEL,
            GeoRepoEvents.RULE_PROFILE_CUSTOM_PROP_ADD,
            GeoRepoEvents.RULE_PROFILE_CUSTOM_PROP_APPLY_CHANGES,




            GeoRepoEvents.EDIT_PROFILE_DETAILS,




            GeoRepoEvents.SAVE_LAYER_LIMITS, GeoRepoEvents.LOAD_LAYER_LIMITS,
            GeoRepoEvents.LOAD_RULES,




            GeoRepoEvents.EDIT_USER_DETAILS, GeoRepoEvents.LOAD_USER_LIMITS,
            GeoRepoEvents.SAVE_USER_LIMITS);

    }

    /*
     * (non-Javadoc)
     *
     * @see com.extjs.gxt.ui.client.mvc.Controller#initialize()
     */
    @Override
    protected void initialize()
    {
        this.rulesView = new RulesView(this);
        initWidget();
    }

    /**
     * Inits the widget.
     */
    private void initWidget()
    {

    }

    /*
     * (non-Javadoc)
     *
     * @see com.extjs.gxt.ui.client.mvc.Controller#handleEvent(com.extjs.gxt.ui.client
     * .mvc.AppEvent)
     */
    @Override
    public void handleEvent(AppEvent event)
    {

        if (event.getType() == GeoRepoEvents.ATTACH_BOTTOM_TAB_WIDGETS)
        {
            onAttachTabWidgets(event);
        }

        if (event.getType() == GeoRepoEvents.RULE_UPDATE_GRID_COMBO)
        {
            onUpdateRuleRequestsCombo(event);
        }

        if (event.getType() == GeoRepoEvents.RULE_UPDATE_EDIT_GRID_COMBO)
        {
            onUpdateEditRuleRequestsCombo(event);
        }

        if (event.getType() == GeoRepoEvents.RULE_APPLY_CHANGES_GRID_COMBO)
        {
            onApplyChangesRules(event);
        }

        if (event.getType() == GeoRepoEvents.RULE_ADD)
        {
            onAddRule(event);
        }

        if (event.getType() == GeoRepoEvents.RULE_SAVE)
        {
            onSaveRule(event);
        }

        if (event.getType() == GeoRepoEvents.RULE_DEL)
        {
            onRemoveRule(event);
        }

        if (event.getType() == GeoRepoEvents.RULE_PRIORITY_UP)
        {
            onRulePriorityUp(event);
        }

        if (event.getType() == GeoRepoEvents.RULE_PRIORITY_DOWN)
        {
            onRulePriorityDown(event);
        }

        if (event.getType() == GeoRepoEvents.INJECT_WKT)
        {
            onInjectWKT(event);
        }

        if (event.getType() == GeoRepoEvents.LOAD_RULES)
        {
            onLoadRules();
        }

        forwardToView(this.rulesView, event);
    }

    /**
     *
     */
    private void onLoadRules()
    {
        RulesTabItem rulesTabItem = (RulesTabItem) tabWidget.getItemByItemId(RULES_TAB_ITEM_ID);
        final RuleGridWidget rulesInfoWidget = rulesTabItem.getRuleManagementWidget().getRulesInfo();
        rulesInfoWidget.getLoader().load(0,
            it.geosolutions.georepo.gui.client.Constants.DEFAULT_PAGESIZE);
    }

    /**
     * On inject wkt.
     *
     * @param event
     *            the event
     */
    private void onInjectWKT(AppEvent event)
    {
        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[]
            {
                "WKT",
                (String) event.getData()
            });
    }

    /**
     * On attach tab widgets.
     *
     * @param event
     *            the event
     */
    private void onAttachTabWidgets(AppEvent event)
    {
        if (tabWidget == null)
        {
            tabWidget = (TabWidget) event.getData();
            tabWidget.add(new RulesTabItem(RULES_TAB_ITEM_ID, rulesManagerServiceRemote,
                    gsManagerServiceRemote, profilesManagerServiceRemote,
                    instancesManagerServiceRemote, workspacesManagerServiceRemote));

            RulesTabItem rulesTabItem = (RulesTabItem) tabWidget.getItemByItemId(RULES_TAB_ITEM_ID);
            final RuleGridWidget rulesInfoWidget = rulesTabItem.getRuleManagementWidget().getRulesInfo();
            final Grid<Rule> grid = rulesInfoWidget.getGrid();
            grid.getStore().setSortField(BeanKeyValue.PRIORITY.getValue());
            grid.getStore().setSortDir(SortDir.ASC);
            grid.getStore().sort(BeanKeyValue.PRIORITY.getValue(), SortDir.ASC);
        }
    }

    /**
     * On update rule requests combo.
     *
     * @param event
     *            the event
     */
    private void onUpdateRuleRequestsCombo(AppEvent event)
    {
        if (tabWidget != null)
        {
            Object tabData = event.getData();

            if (tabData instanceof Rule)
            {
                RulesTabItem rulesTabItem = (RulesTabItem) tabWidget.getItemByItemId(RULES_TAB_ITEM_ID);
                final RuleGridWidget rulesInfoWidget = rulesTabItem.getRuleManagementWidget().getRulesInfo();
                final Grid<Rule> grid = rulesInfoWidget.getGrid();

                List<Rule> rules = new ArrayList<Rule>(grid.getStore().getModels());
                if (checkUniqueRule(rules, (Rule) tabData))
                {
                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE,
                        new String[]
                        {
                            I18nProvider.getMessages().ruleServiceName(),
                            "There's just another rule with this setting!!"
                        });
                    grid.getStore().getLoader().setSortDir(SortDir.ASC);
                    grid.getStore().getLoader().setSortField(BeanKeyValue.PRIORITY.getValue());
                    grid.getStore().getLoader().load();

                    return;
                }

                boolean res;
                try
                {
                    res = saveRule(event);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    res = false;
                }

                Rule model = (Rule) tabData;
                if (res)
                {
                    grid.getStore().remove(model);
                    grid.getStore().add(model);
                }

            }
        }
    }

    /**
     * On update rule requests combo.
     *
     * @param event
     *            the event
     */
    private void onSaveRule(AppEvent event)
    {
        if (tabWidget != null)
        {
            Object tabData = event.getData();

            if (tabData instanceof Rule)
            {
                RulesTabItem rulesTabItem = (RulesTabItem) tabWidget.getItemByItemId(RULES_TAB_ITEM_ID);
                final RuleGridWidget rulesInfoWidget = rulesTabItem.getRuleManagementWidget().getRulesInfo();
                final Grid<Rule> grid = rulesInfoWidget.getGrid();

                boolean res;
                try
                {
                    res = saveRule(event);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    res = false;
                }

                Rule model = (Rule) tabData;
                if (res)
                {
                    grid.getStore().remove(model);
                    grid.getStore().add(model);
                }

            }
        }
    }

    /**
     * On update rule requests combo.
     *
     * @param event
     *            the event
     */
    private void onUpdateEditRuleRequestsCombo(AppEvent event)
    {
        if (tabWidget != null)
        {
            Object tabData = event.getData();

            if (tabData instanceof Rule)
            {
                Rule model = (Rule) tabData;
                this.rulesView.ruleRowEditor.grid.getStore().remove(model);
                this.rulesView.ruleRowEditor.grid.getStore().add(model);
                this.rulesView.ruleRowEditor.setModel(model);
            }
        }
    }

    /**
     *
     * @param event
     * @return
     * @throws Exception
     */
    private boolean saveRule(AppEvent event) throws Exception
    {
        if (tabWidget != null)
        {

            RulesTabItem rulesTabItem = (RulesTabItem) tabWidget.getItemByItemId(RULES_TAB_ITEM_ID);
            final RuleGridWidget rulesInfoWidget = rulesTabItem.getRuleManagementWidget().getRulesInfo();
            final Grid<Rule> grid = rulesInfoWidget.getGrid();
            if ((grid != null) && (grid.getStore() != null))
            {
                ListStore<Rule> store = grid.getStore();

                if ((store != null) && (store.getModels() != null) &&
                        (store.getModels().size() > 0))
                {
                    // TODO: details?
                    Rule lRule = (Rule) event.getData();

                    rulesManagerServiceRemote.shift(lRule.getPriority(), 1,
                        new AsyncCallback<Void>()
                        {
                            public void onFailure(Throwable caught)
                            {
                                System.out.println("ERROR: " + caught.getMessage());
                                Dispatcher.forwardEvent(
                                    GeoRepoEvents.SEND_ERROR_MESSAGE,
                                    new String[]
                                    {
                                        I18nProvider.getMessages().ruleServiceName(),
                                        "There's just another rule with this setting!!"
                                    });
                            }

                            public void onSuccess(Void result)
                            {
                            }
                        });

                    rulesManagerServiceRemote.saveRule(lRule, new AsyncCallback<Void>()
                        {

                            public void onFailure(Throwable caught)
                            {
                                String message = caught.getMessage();
                                if (message != null)
                                {
                                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE,
                                        new String[]
                                        {
                                            I18nProvider.getMessages().ruleServiceName(),
                                            caught.getMessage()
                                        });
                                }
                                else
                                {
                                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE,
                                        new String[]
                                        {
                                            I18nProvider.getMessages().ruleServiceName(),
                                            I18nProvider.getMessages().ruleFetchFailureMessage()
                                        });
                                }
                            }

                            public void onSuccess(Void result)
                            {
                                grid.getStore().getLoader().setSortDir(SortDir.ASC);
                                grid.getStore().getLoader().setSortField(
                                    BeanKeyValue.PRIORITY.getValue());
                                grid.getStore().getLoader().load();

                                Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
                                    new String[]
                                    {
                                        I18nProvider.getMessages().ruleServiceName(),
                                        I18nProvider.getMessages().ruleFetchSuccessMessage()
                                    });
                            }

                        });

                    // grid.getStore().getLoader().setSortDir(SortDir.ASC);
                    // grid.getStore().getLoader().setSortField(
                    // BeanKeyValue.PRIORITY.getValue());
                    // grid.getStore().getLoader().load();

                    return true;
                }

                return false;
            }

            return false;
        }
        else
        {
            return false;
        }
    }

    /**
     * On apply changes rules.
     *
     * @param event
     *            the event
     */
    private void onApplyChangesRules(AppEvent event)
    {
        if (tabWidget != null)
        {

            RulesTabItem rulesTabItem = (RulesTabItem) tabWidget.getItemByItemId(RULES_TAB_ITEM_ID);
            final RuleGridWidget rulesInfoWidget = rulesTabItem.getRuleManagementWidget().getRulesInfo();
            final Grid<Rule> grid = rulesInfoWidget.getGrid();

            if ((grid != null) && (grid.getStore() != null))
            {
                ListStore<Rule> store = grid.getStore();

                if ((store != null) && (store.getModels() != null) &&
                        (store.getModels().size() > 0))
                {
                    // TODO: details?
                    rulesManagerServiceRemote.saveAllRules(store.getModels(),
                        new AsyncCallback<Void>()
                        {

                            public void onFailure(Throwable caught)
                            {

                                Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE,
                                    new String[]
                                    {
                                        I18nProvider.getMessages().ruleServiceName(),
                                        I18nProvider.getMessages().ruleFetchFailureMessage()
                                    });
                                grid.getStore().getLoader().setSortDir(SortDir.ASC);
                                grid.getStore().getLoader().setSortField(
                                    BeanKeyValue.PRIORITY.getValue());
                                grid.getStore().getLoader().load();
                                grid.repaint();
                            }

                            public void onSuccess(Void result)
                            {
                                grid.getStore().getLoader().setSortDir(SortDir.ASC);
                                grid.getStore().getLoader().setSortField(
                                    BeanKeyValue.PRIORITY.getValue());
                                grid.getStore().getLoader().load();

                                Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
                                    new String[]
                                    {
                                        I18nProvider.getMessages().ruleServiceName(),
                                        I18nProvider.getMessages().ruleFetchSuccessMessage()
                                    });
                            }
                        });
                }
            }
        }
    }

    /**
     * On remove rule.
     *
     * @param event
     *            the event
     */
    private void onRemoveRule(AppEvent event)
    {
        if (tabWidget != null)
        {
            Object tabData = event.getData();

            if (tabData instanceof Rule)
            {
                Rule model = (Rule) tabData;
                RulesTabItem rulesTabItem = (RulesTabItem) tabWidget.getItemByItemId(RULES_TAB_ITEM_ID);
                final RuleGridWidget rulesInfoWidget = rulesTabItem.getRuleManagementWidget().getRulesInfo();
                final Grid<Rule> grid = rulesInfoWidget.getGrid();

                if ((grid != null) && (grid.getStore() != null))
                {
                    ListStore<Rule> store = grid.getStore();

                    if ((store != null) && (store.getModels() != null) &&
                            (store.getModels().size() > 0))
                    {
                        rulesManagerServiceRemote.deleteRule(model,
                            new AsyncCallback<Void>()
                            {

                                public void onFailure(Throwable caught)
                                {

                                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE,
                                        new String[]
                                        {
                                            I18nProvider.getMessages().ruleServiceName(),
                                            I18nProvider.getMessages().ruleFetchFailureMessage()
                                        });
                                }

                                public void onSuccess(Void result)
                                {
                                    grid.getStore().getLoader().setSortDir(SortDir.ASC);
                                    grid.getStore().getLoader().setSortField(
                                        BeanKeyValue.PRIORITY.getValue());
                                    grid.getStore().getLoader().load();

                                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
                                        new String[]
                                        {
                                            I18nProvider.getMessages().ruleServiceName(),
                                            I18nProvider.getMessages().ruleFetchSuccessMessage()
                                        });
                                }

                            });
                    }
                }
            }

        }
    }

    /**
     * On add rule.
     *
     * @param event
     *            the event
     */
    private void onAddRule(AppEvent event)
    {
        if (tabWidget != null)
        {
            Object tabData = event.getData();

            if (tabData instanceof Rule)
            {
                Rule model = (Rule) tabData;

                RulesTabItem rulesTabItem = (RulesTabItem) tabWidget.getItemByItemId(RULES_TAB_ITEM_ID);
                final RuleGridWidget rulesInfoWidget = rulesTabItem.getRuleManagementWidget().getRulesInfo();
                final Grid<Rule> grid = rulesInfoWidget.getGrid();

                List<Rule> rules = new ArrayList<Rule>(grid.getStore().getModels());
                Rule new_rule = model;
                rulesManagerServiceRemote.shift(new_rule.getPriority(), 1,
                    new AsyncCallback<Void>()
                    {
                        public void onFailure(Throwable caught)
                        {
                            System.out.println("ERROR: " + caught.getMessage());
                            Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE,
                                new String[]
                                {
                                    I18nProvider.getMessages().ruleServiceName(),
                                    "There's just another rule with this setting!!"
                                });
                        }

                        public void onSuccess(Void result)
                        {
                            System.out.println("SUCCESS: ");
                        }
                    });
                rulesManagerServiceRemote.saveRule(new_rule,
                    new AsyncCallback<Void>()
                    {

                        public void onFailure(Throwable caught)
                        {
                            String message = caught.getMessage();
                            if (message != null)
                            {
                                Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE,
                                    new String[]
                                    {
                                        I18nProvider.getMessages().ruleServiceName(),
                                        caught.getMessage()
                                    });
                            }
                            else
                            {
                                Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE,
                                    new String[]
                                    {
                                        I18nProvider.getMessages().ruleServiceName(),
                                        I18nProvider.getMessages().ruleFetchFailureMessage()
                                    });
                            }
                        }

                        public void onSuccess(Void result)
                        {
                            grid.getStore().getLoader().setSortDir(SortDir.ASC);
                            grid.getStore().getLoader().setSortField(
                                BeanKeyValue.PRIORITY.getValue());
                            grid.getStore().getLoader().load();

                            Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
                                new String[]
                                {
                                    I18nProvider.getMessages().ruleServiceName(),
                                    I18nProvider.getMessages().ruleFetchSuccessMessage()
                                });
                        }

                    });

                rules.add(new_rule);
            }
        }
    }

    /**
     * On rule priority up.
     *
     * @param event
     *            the event
     */
    private void onRulePriorityUp(AppEvent event)
    {
        if (tabWidget != null)
        {
            Object tabData = event.getData();

            if (tabData instanceof Rule)
            {
                Rule model = (Rule) tabData;

                RulesTabItem rulesTabItem = (RulesTabItem) tabWidget.getItemByItemId(RULES_TAB_ITEM_ID);
                final RuleGridWidget rulesInfoWidget = rulesTabItem.getRuleManagementWidget().getRulesInfo();
                final Grid<Rule> grid = rulesInfoWidget.getGrid();

                List<Rule> rules = new ArrayList<Rule>(grid.getStore().getModels());
                if (model.getPriority() > 0)
                {
                    int indexUp = rules.lastIndexOf(model);
                    Rule rup = null;
                    Rule rupup = null;

                    rup = ((Rule) (rules.get(indexUp)));
                    if ((indexUp - 1) >= 0)
                    {
                        rupup = ((Rule) (rules.get(indexUp - 1)));
                    }

                    if (rupup != null)
                    {
                        rulesManagerServiceRemote.swap(rup.getId(), rupup.getId(),
                            new AsyncCallback<Void>()
                            {

                                public void onFailure(Throwable caught)
                                {

                                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE,
                                        new String[]
                                        {
                                            I18nProvider.getMessages().ruleServiceName(),
                                            I18nProvider.getMessages().ruleFetchFailureMessage()
                                        });
                                }

                                public void onSuccess(Void result)
                                {
                                    grid.getStore().getLoader().setSortDir(SortDir.ASC);
                                    grid.getStore().getLoader().setSortField(
                                        BeanKeyValue.PRIORITY.getValue());
                                    grid.getStore().getLoader().load();

                                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
                                        new String[]
                                        {
                                            I18nProvider.getMessages().ruleServiceName(),
                                            I18nProvider.getMessages().ruleFetchSuccessMessage()
                                        });
                                }

                            });

                    }

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
    private void onRulePriorityDown(AppEvent event)
    {
        if (tabWidget != null)
        {
            tabWidget.setShim(true);

            Object tabData = event.getData();

            if (tabData instanceof Rule)
            {
                Rule model = (Rule) tabData;

                RulesTabItem rulesTabItem = (RulesTabItem) tabWidget.getItemByItemId(RULES_TAB_ITEM_ID);
                final RuleGridWidget rulesInfoWidget = rulesTabItem.getRuleManagementWidget().getRulesInfo();
                final Grid<Rule> grid = rulesInfoWidget.getGrid();

                List<Rule> rules = new ArrayList<Rule>(grid.getStore().getModels());
                if (model.getPriority() >= 0)
                {
                    int indexUp = rules.lastIndexOf(model);
                    Rule rup = null;
                    Rule rupup = null;

                    rup = ((Rule) (rules.get(indexUp)));
                    if ((indexUp + 1) != rules.size())
                    {
                        rupup = ((Rule) (rules.get(indexUp + 1)));
                    }

                    if (rupup != null)
                    {
                        rulesManagerServiceRemote.swap(rupup.getId(), rup.getId(),
                            new AsyncCallback<Void>()
                            {

                                public void onFailure(Throwable caught)
                                {

                                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE,
                                        new String[]
                                        {
                                            I18nProvider.getMessages().ruleServiceName(),
                                            I18nProvider.getMessages().ruleFetchFailureMessage()
                                        });
                                }

                                public void onSuccess(Void result)
                                {
                                    grid.getStore().getLoader().setSortDir(SortDir.ASC);
                                    grid.getStore().getLoader().setSortField(
                                        BeanKeyValue.PRIORITY.getValue());
                                    grid.getStore().getLoader().load();

                                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
                                        new String[]
                                        {
                                            I18nProvider.getMessages().ruleServiceName(),
                                            I18nProvider.getMessages().ruleFetchSuccessMessage()
                                        });
                                }

                            });

                    }

                }
            }
        }
    }

    /**
     * gsuser_id, profile_id, instance_id, service, request, workspace, layer
     *
     * @param list
     * @param rule
     * @return
     */
    public boolean checkUniqueRule(List<Rule> list, Rule rule)
    {
        boolean res = false;
        if (list.size() > 0)
        {
            Iterator<Rule> itr = list.iterator();
            while (itr.hasNext() && !res)
            {
                Rule r = (Rule) itr.next();
                if ((r.getId() != rule.getId()) &&
                        (((r.getUser() != null) && (rule.getUser() != null) && r.getUser().getName().equals(rule.getUser().getName())) || ((r.getUser() == null) && (rule.getUser() == null))) &&
                        (((r.getProfile() != null) && (rule.getProfile() != null) && r.getProfile().getName().equals(rule.getProfile().getName())) || ((r.getProfile() == null) && (rule.getProfile() == null))) &&
                        (((r.getInstance() != null) && (rule.getInstance() != null) && r.getInstance().getName().equals(rule.getInstance().getName())) || ((r.getInstance() == null) && (rule.getInstance() == null))) &&
                        (((r.getService() != null) && (rule.getService() != null) && r.getService().equals(rule.getService())) || ((r.getService() == null) && (rule.getService() == null))) &&
                        (((r.getRequest() != null) && (rule.getRequest() != null) && r.getRequest().equals(rule.getRequest())) || ((r.getRequest() == null) && (rule.getRequest() == null))) &&
                        (((r.getWorkspace() != null) && (rule.getWorkspace() != null) && r.getWorkspace().equals(rule.getWorkspace())) || ((r.getWorkspace() == null) && (rule.getWorkspace() == null))) &&
                        (((r.getLayer() != null) && (rule.getLayer() != null) && r.getLayer().equals(rule.getLayer())) || ((r.getLayer() == null) && (rule.getLayer() == null))))
                {
                    res = true;
                }
            }
        }

        return res;
    }

    /**
     * Forward to tab widget.
     *
     * @param event
     *            the event
     */
    @SuppressWarnings("unused")
    private void forwardToTabWidget(AppEvent event)
    {
        this.tabWidget.fireEvent(event.getType(), event);
    }

}
