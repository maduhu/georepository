/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.RuleGridWidget,v. 0.1 25-gen-2011 12.16.45 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 12.16.45 $
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
package it.geosolutions.georepo.gui.client.widget;

import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.Resources;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.BeanKeyValue;
import it.geosolutions.georepo.gui.client.model.GSInstance;
import it.geosolutions.georepo.gui.client.model.GSUser;
import it.geosolutions.georepo.gui.client.model.Profile;
import it.geosolutions.georepo.gui.client.model.Rule;
import it.geosolutions.georepo.gui.client.model.data.Layer;
import it.geosolutions.georepo.gui.client.model.data.Request;
import it.geosolutions.georepo.gui.client.model.data.Service;
import it.geosolutions.georepo.gui.client.model.data.Workspace;
import it.geosolutions.georepo.gui.client.service.GsUsersManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.InstancesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.WorkspacesManagerServiceRemoteAsync;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.LoadListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.widget.BoxComponent;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class RuleGridWidget.
 */
public class RuleGridWidget extends GeoRepoGridWidget<Rule> {

    /** The rulesService. */
    private RulesManagerServiceRemoteAsync rulesService;

    private GsUsersManagerServiceRemoteAsync gsUsersService;

    private ProfilesManagerServiceRemoteAsync profilesService;

    private InstancesManagerServiceRemoteAsync instancesService;

    private WorkspacesManagerServiceRemoteAsync workspacesService;

    /** The proxy. */
    private RpcProxy<PagingLoadResult<Rule>> proxy;

    /** The loader. */
    private PagingLoader<PagingLoadResult<ModelData>> loader;

    /** The tool bar. */
    private PagingToolBar toolBar;

    /**
     * Instantiates a new rule grid widget.
     * 
     * @param rulesService
     *            the rulesService
     */
    public RuleGridWidget(RulesManagerServiceRemoteAsync rulesService,
            GsUsersManagerServiceRemoteAsync gsUsersService,
            ProfilesManagerServiceRemoteAsync profilesService,
            InstancesManagerServiceRemoteAsync instancesService,
            WorkspacesManagerServiceRemoteAsync workspacesService) {
        super();
        this.rulesService = rulesService;
        this.gsUsersService = gsUsersService;
        this.profilesService = profilesService;
        this.instancesService = instancesService;
        this.workspacesService = workspacesService;
    }

    /**
     * Instantiates a new rule grid widget.
     * 
     * @param models
     *            the models
     */
    public RuleGridWidget(List<Rule> models) {
        super(models);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#setGridProperties ()
     */
    @Override
    public void setGridProperties() {
        // grid.setAutoExpandColumn(BeanKeyValue.NAME.getValue());
        // grid.addPlugin(emailEnable);
        // grid.addPlugin(rssEnable);

        grid.setLoadMask(true);
        grid.setAutoWidth(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget# prepareColumnModel()
     */
    @Override
    public ColumnModel prepareColumnModel() {
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

        ColumnConfig rulePriorityColumn = new ColumnConfig();
        rulePriorityColumn.setId(BeanKeyValue.PRIORITY.getValue());
        rulePriorityColumn.setWidth(30);
        rulePriorityColumn.setMenuDisabled(true);
        rulePriorityColumn.setSortable(false);
        configs.add(rulePriorityColumn);

        ColumnConfig ruleUserColumn = new ColumnConfig();
        ruleUserColumn.setId(BeanKeyValue.USER.getValue());
        ruleUserColumn.setHeader("User");
        ruleUserColumn.setWidth(130);
        ruleUserColumn.setRenderer(this.createUsersComboBox());
        ruleUserColumn.setMenuDisabled(true);
        ruleUserColumn.setSortable(false);
        configs.add(ruleUserColumn);

        ColumnConfig ruleProfileColumn = new ColumnConfig();
        ruleProfileColumn.setId(BeanKeyValue.PROFILE.getValue());
        ruleProfileColumn.setHeader("Profile");
        ruleProfileColumn.setWidth(160);
        ruleProfileColumn.setRenderer(this.createProfilesComboBox());
        ruleProfileColumn.setMenuDisabled(true);
        ruleProfileColumn.setSortable(false);
        configs.add(ruleProfileColumn);

        ColumnConfig ruleInstanceColumn = new ColumnConfig();
        ruleInstanceColumn.setId(BeanKeyValue.INSTANCE.getValue());
        ruleInstanceColumn.setHeader("Instance");
        ruleInstanceColumn.setWidth(260);
        ruleInstanceColumn.setRenderer(this.createInstancesComboBox());
        ruleInstanceColumn.setMenuDisabled(true);
        ruleInstanceColumn.setSortable(false);
        configs.add(ruleInstanceColumn);

        ColumnConfig ruleServiceColumn = new ColumnConfig();
        ruleServiceColumn.setId(BeanKeyValue.SERVICE.getValue());
        ruleServiceColumn.setHeader("Service");
        ruleServiceColumn.setWidth(100);
        ruleServiceColumn.setRenderer(this.createServicesComboBox());
        ruleServiceColumn.setMenuDisabled(true);
        ruleServiceColumn.setSortable(false);
        configs.add(ruleServiceColumn);

        ColumnConfig ruleServiceRequestColumn = new ColumnConfig();
        ruleServiceRequestColumn.setId(BeanKeyValue.REQUEST.getValue());
        ruleServiceRequestColumn.setHeader("Request");
        ruleServiceRequestColumn.setWidth(190);
        ruleServiceRequestColumn.setRenderer(this.createServicesRequestComboBox());
        ruleServiceRequestColumn.setMenuDisabled(true);
        ruleServiceRequestColumn.setSortable(false);
        configs.add(ruleServiceRequestColumn);

        ColumnConfig ruleServiceWorkspacesColumn = new ColumnConfig();
        ruleServiceWorkspacesColumn.setId(BeanKeyValue.WORKSPACE.getValue());
        ruleServiceWorkspacesColumn.setHeader("Workspace");
        ruleServiceWorkspacesColumn.setWidth(190);
        ruleServiceWorkspacesColumn.setRenderer(this.createServiceWorkspacesComboBox());
        ruleServiceWorkspacesColumn.setMenuDisabled(true);
        ruleServiceWorkspacesColumn.setSortable(false);
        configs.add(ruleServiceWorkspacesColumn);

        ColumnConfig ruleworkspaceLayersColumn = new ColumnConfig();
        ruleworkspaceLayersColumn.setId(BeanKeyValue.LAYER.getValue());
        ruleworkspaceLayersColumn.setHeader("Layer");
        ruleworkspaceLayersColumn.setWidth(190);
        ruleworkspaceLayersColumn.setRenderer(this.createWorkspacesLayersComboBox());
        ruleworkspaceLayersColumn.setMenuDisabled(true);
        ruleworkspaceLayersColumn.setSortable(false);
        configs.add(ruleworkspaceLayersColumn);

        // ColumnConfig dateCreationColumn = new ColumnConfig();
        // dateCreationColumn.setId(BeanKeyValue.DATE_CREATION.getValue());
        // dateCreationColumn.setHeader("Date Creation");
        // dateCreationColumn.setWidth(180);
        // configs.add(dateCreationColumn);
        //
        // ColumnConfig profileEnabledColumn = new ColumnConfig();
        // profileEnabledColumn.setId(BeanKeyValue.PROFILE_ENABLED.getValue());
        // profileEnabledColumn.setHeader("Enabled");
        // profileEnabledColumn.setWidth(80);
        // profileEnabledColumn.setRenderer(this.createEnableCheckBox());
        // profileEnabledColumn.setMenuDisabled(true);
        // profileEnabledColumn.setSortable(false);
        // configs.add(profileEnabledColumn);
        //
        // ColumnConfig removeActionColumn = new ColumnConfig();
        // removeActionColumn.setId("removeProfile");
        // // removeActionColumn.setHeader("Remove");
        // removeActionColumn.setWidth(80);
        // removeActionColumn.setRenderer(this.createProfileDeleteButton());
        // removeActionColumn.setMenuDisabled(true);
        // removeActionColumn.setSortable(false);
        // configs.add(removeActionColumn);

        return new ColumnModel(configs);
    }

    /**
     * 
     * @return
     */
    private GridCellRenderer<Rule> createUsersComboBox() {
        GridCellRenderer<Rule> comboRendered = new GridCellRenderer<Rule>() {

            private boolean init;

            public Object render(final Rule model, String property, ColumnData config,
                    int rowIndex, int colIndex, ListStore<Rule> store, Grid<Rule> grid) {

                if (!init) {
                    init = true;
                    grid.addListener(Events.ColumnResize, new Listener<GridEvent<Rule>>() {

                        public void handleEvent(GridEvent<Rule> be) {
                            for (int i = 0; i < be.getGrid().getStore().getCount(); i++) {
                                if (be.getGrid().getView().getWidget(i, be.getColIndex()) != null
                                        && be.getGrid().getView().getWidget(i, be.getColIndex()) instanceof BoxComponent) {
                                    ((BoxComponent) be.getGrid().getView().getWidget(i,
                                            be.getColIndex())).setWidth(be.getWidth() - 10);
                                }
                            }
                        }
                    });
                }

                // TODO: generalize this!
                ComboBox<GSUser> usersComboBox = new ComboBox<GSUser>();
                usersComboBox.setId("ruleUsersCombo");
                usersComboBox.setName("ruleUsersCombo");
                usersComboBox.setEmptyText("(No user available)");
                usersComboBox.setDisplayField(BeanKeyValue.NAME.getValue());
                usersComboBox.setEditable(false);
                usersComboBox.setStore(getAvailableUsers());
                usersComboBox.setTypeAhead(true);
                usersComboBox.setTriggerAction(TriggerAction.ALL);
                usersComboBox.setWidth(120);

                if (model.getUser() != null) {
                    usersComboBox.setValue(model.getUser());
                    usersComboBox.setSelection(Arrays.asList(model.getUser()));
                }

                usersComboBox.addListener(Events.Select, new Listener<FieldEvent>() {

                    public void handleEvent(FieldEvent be) {
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
                                new String[] { "GeoServer Rules",
                                        "Rule " + model.getPriority() + ": User changed" });
                    }
                });

                return usersComboBox;
            }

            /**
             * TODO: Call User Service here!!
             * 
             * @return
             */
            private ListStore<GSUser> getAvailableUsers() {
                RpcProxy<PagingLoadResult<GSUser>> userProxy = new RpcProxy<PagingLoadResult<GSUser>>() {

                    @Override
                    protected void load(Object loadConfig,
                            AsyncCallback<PagingLoadResult<GSUser>> callback) {
                        gsUsersService.getGsUsers((PagingLoadConfig) loadConfig, true, callback);
                    }

                };
                BasePagingLoader<PagingLoadResult<ModelData>> usersLoader = new BasePagingLoader<PagingLoadResult<ModelData>>(
                        userProxy);
                usersLoader.setRemoteSort(false);
                ListStore<GSUser> availableUsers = new ListStore<GSUser>(usersLoader);

                return availableUsers;
            }
        };

        return comboRendered;
    }

    /**
     * 
     * @return
     */
    private GridCellRenderer<Rule> createProfilesComboBox() {
        GridCellRenderer<Rule> comboRendered = new GridCellRenderer<Rule>() {

            private boolean init;

            public Object render(final Rule model, String property, ColumnData config,
                    int rowIndex, int colIndex, ListStore<Rule> store, Grid<Rule> grid) {

                if (!init) {
                    init = true;
                    grid.addListener(Events.ColumnResize, new Listener<GridEvent<Rule>>() {

                        public void handleEvent(GridEvent<Rule> be) {
                            for (int i = 0; i < be.getGrid().getStore().getCount(); i++) {
                                if (be.getGrid().getView().getWidget(i, be.getColIndex()) != null
                                        && be.getGrid().getView().getWidget(i, be.getColIndex()) instanceof BoxComponent) {
                                    ((BoxComponent) be.getGrid().getView().getWidget(i,
                                            be.getColIndex())).setWidth(be.getWidth() - 10);
                                }
                            }
                        }
                    });
                }

                // TODO: generalize this!
                ComboBox<Profile> profilesComboBox = new ComboBox<Profile>();
                profilesComboBox.setId("ruleProfilesCombo");
                profilesComboBox.setName("ruleProfilesCombo");
                profilesComboBox.setEmptyText("(No profile available)");
                profilesComboBox.setDisplayField(BeanKeyValue.NAME.getValue());
                profilesComboBox.setEditable(false);
                profilesComboBox.setStore(getAvailableProfiles());
                profilesComboBox.setTypeAhead(true);
                profilesComboBox.setTriggerAction(TriggerAction.ALL);
                profilesComboBox.setWidth(150);

                if (model.getProfile() != null) {
                    profilesComboBox.setValue(model.getProfile());
                    profilesComboBox.setSelection(Arrays.asList(model.getProfile()));
                }

                profilesComboBox.addListener(Events.Select, new Listener<FieldEvent>() {

                    public void handleEvent(FieldEvent be) {
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "GeoServer Rules",
                                "Rule " + model.getPriority() + ": Profile changed" });
                    }
                });

                return profilesComboBox;
            }

            /**
             * TODO: Call Profile Service here!!
             * 
             * @return
             */
            private ListStore<Profile> getAvailableProfiles() {
                ListStore<Profile> availableProfiles = new ListStore<Profile>();
                RpcProxy<PagingLoadResult<Profile>> profileProxy = new RpcProxy<PagingLoadResult<Profile>>() {

                    @Override
                    protected void load(Object loadConfig,
                            AsyncCallback<PagingLoadResult<Profile>> callback) {
                        profilesService.getProfiles((PagingLoadConfig) loadConfig, true, callback);
                    }

                };
                BasePagingLoader<PagingLoadResult<ModelData>> profilesLoader = new BasePagingLoader<PagingLoadResult<ModelData>>(
                        profileProxy);
                profilesLoader.setRemoteSort(false);
                availableProfiles = new ListStore<Profile>(profilesLoader);

                return availableProfiles;
            }
        };

        return comboRendered;
    }

    /**
     * 
     * @return
     */
    private GridCellRenderer<Rule> createInstancesComboBox() {
        GridCellRenderer<Rule> comboRendered = new GridCellRenderer<Rule>() {

            private boolean init;

            public Object render(final Rule model, String property, ColumnData config,
                    int rowIndex, int colIndex, ListStore<Rule> store, Grid<Rule> grid) {

                if (!init) {
                    init = true;
                    grid.addListener(Events.ColumnResize, new Listener<GridEvent<Rule>>() {

                        public void handleEvent(GridEvent<Rule> be) {
                            for (int i = 0; i < be.getGrid().getStore().getCount(); i++) {
                                if (be.getGrid().getView().getWidget(i, be.getColIndex()) != null
                                        && be.getGrid().getView().getWidget(i, be.getColIndex()) instanceof BoxComponent) {
                                    ((BoxComponent) be.getGrid().getView().getWidget(i,
                                            be.getColIndex())).setWidth(be.getWidth() - 10);
                                }
                            }
                        }
                    });
                }

                // TODO: generalize this!
                ComboBox<GSInstance> instancesComboBox = new ComboBox<GSInstance>();
                instancesComboBox.setId("ruleInstancesCombo");
                instancesComboBox.setName("ruleInstancesCombo");
                instancesComboBox.setEmptyText("(No instances available)");
                instancesComboBox.setDisplayField(BeanKeyValue.BASE_URL.getValue());
                instancesComboBox.setEditable(false);
                instancesComboBox.setStore(getAvailableInstances());
                instancesComboBox.setTypeAhead(true);
                instancesComboBox.setTriggerAction(TriggerAction.ALL);
                instancesComboBox.setWidth(250);

                if (model.getInstance() != null) {
                    instancesComboBox.setValue(model.getInstance());
                    instancesComboBox.setSelection(Arrays.asList(model.getInstance()));
                }

                instancesComboBox.addListener(Events.Select, new Listener<FieldEvent>() {

                    public void handleEvent(FieldEvent be) {
                        final GSInstance instance = (GSInstance) be.getField().getValue();
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "GeoServer Rules",
                                "Rule " + model.getPriority() + ": Instance changed" });

                        model.setInstance(instance);
                        model.setService("*");
                        model.setRequest("*");
                        model.setWorkspace("*");
                        model.setLayer("*");
                        Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_RULES_GRID_COMBOS, model);
                    }
                });

                return instancesComboBox;
            }

            /**
             * TODO: Call Instance Service here!!
             * 
             * @return
             */
            private ListStore<GSInstance> getAvailableInstances() {
                RpcProxy<PagingLoadResult<GSInstance>> gsInstancesProxy = new RpcProxy<PagingLoadResult<GSInstance>>() {

                    @Override
                    protected void load(Object loadConfig,
                            AsyncCallback<PagingLoadResult<GSInstance>> callback) {
                        instancesService
                                .getInstances((PagingLoadConfig) loadConfig, true, callback);
                    }

                };
                BasePagingLoader<PagingLoadResult<ModelData>> gsInstancesLoader = new BasePagingLoader<PagingLoadResult<ModelData>>(
                        gsInstancesProxy);
                gsInstancesLoader.setRemoteSort(false);
                ListStore<GSInstance> availableInstances = new ListStore<GSInstance>(
                        gsInstancesLoader);

                return availableInstances;
            }
        };

        return comboRendered;
    }

    /**
     * 
     * @return
     */
    private GridCellRenderer<Rule> createServicesComboBox() {
        GridCellRenderer<Rule> comboRendered = new GridCellRenderer<Rule>() {

            private boolean init;

            public Object render(final Rule model, String property, ColumnData config,
                    int rowIndex, int colIndex, ListStore<Rule> store, Grid<Rule> grid) {

                if (!init) {
                    init = true;
                    grid.addListener(Events.ColumnResize, new Listener<GridEvent<Rule>>() {

                        public void handleEvent(GridEvent<Rule> be) {
                            for (int i = 0; i < be.getGrid().getStore().getCount(); i++) {
                                if (be.getGrid().getView().getWidget(i, be.getColIndex()) != null
                                        && be.getGrid().getView().getWidget(i, be.getColIndex()) instanceof BoxComponent) {
                                    ((BoxComponent) be.getGrid().getView().getWidget(i,
                                            be.getColIndex())).setWidth(be.getWidth() - 10);
                                }
                            }
                        }
                    });
                }

                // TODO: generalize this!
                ComboBox<Service> servicesComboBox = new ComboBox<Service>();
                servicesComboBox.setId("ruleServicesCombo");
                servicesComboBox.setName("ruleServicesCombo");
                servicesComboBox.setEmptyText("(No service available)");
                servicesComboBox.setDisplayField(BeanKeyValue.SERVICE.getValue());
                servicesComboBox.setStore(getAvailableServices(model.getInstance()));
                servicesComboBox.setEditable(false);
                servicesComboBox.setTypeAhead(true);
                servicesComboBox.setTriggerAction(TriggerAction.ALL);
                servicesComboBox.setWidth(90);

                if (model.getService() != null) {
                    servicesComboBox.setValue(new Service(model.getService()));
                    servicesComboBox.setSelection(Arrays.asList(new Service(model.getService())));
                }

                servicesComboBox.addListener(Events.Select, new Listener<FieldEvent>() {

                    public void handleEvent(FieldEvent be) {
                        final Service service = (Service) be.getField().getValue();
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "GeoServer Rules",
                                "Rule " + model.getPriority() + ": Service changed -> "
                                        + service.getService() });

                        model.setService(service.getService());
                        model.setRequest("*");
                        Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_RULES_GRID_COMBOS, model);
                    }

                });

                return servicesComboBox;
            }

            /**
             * TODO: Call Services Service here!!
             * 
             * @param gsInstance
             * 
             * @return
             */
            private ListStore<Service> getAvailableServices(GSInstance gsInstance) {
                ListStore<Service> availableServices = new ListStore<Service>();
                List<Service> services = new ArrayList<Service>();

                Service all = new Service("*");

                services.add(all);

                if (!gsInstance.getBaseURL().equals("*")) {
                    Service wms = new Service("WMS");
                    Service wcs = new Service("WCS");
                    Service wfs = new Service("WFS");

                    services.add(wms);
                    services.add(wcs);
                    services.add(wfs);
                }

                availableServices.add(services);

                return availableServices;
            }
        };

        return comboRendered;
    }

    /**
     * 
     * @return
     */
    private GridCellRenderer<Rule> createServicesRequestComboBox() {
        GridCellRenderer<Rule> comboRendered = new GridCellRenderer<Rule>() {

            private boolean init;

            public Object render(final Rule model, String property, ColumnData config,
                    int rowIndex, int colIndex, ListStore<Rule> store, Grid<Rule> grid) {

                if (!init) {
                    init = true;
                    grid.addListener(Events.ColumnResize, new Listener<GridEvent<Rule>>() {

                        public void handleEvent(GridEvent<Rule> be) {
                            for (int i = 0; i < be.getGrid().getStore().getCount(); i++) {
                                if (be.getGrid().getView().getWidget(i, be.getColIndex()) != null
                                        && be.getGrid().getView().getWidget(i, be.getColIndex()) instanceof BoxComponent) {
                                    ((BoxComponent) be.getGrid().getView().getWidget(i,
                                            be.getColIndex())).setWidth(be.getWidth() - 10);
                                }
                            }
                        }
                    });
                }

                // TODO: generalize this!
                ComboBox<Request> serviceRequestsComboBox = new ComboBox<Request>();
                serviceRequestsComboBox.setId("ruleServicesRequestCombo");
                serviceRequestsComboBox.setName("ruleServicesRequestCombo");
                serviceRequestsComboBox.setEmptyText("(No service request available)");
                serviceRequestsComboBox.setDisplayField(BeanKeyValue.REQUEST.getValue());
                serviceRequestsComboBox.setStore(getAvailableServicesRequest(model.getInstance(),
                        model.getService()));
                serviceRequestsComboBox.setEditable(false);
                serviceRequestsComboBox.setTypeAhead(true);
                serviceRequestsComboBox.setTriggerAction(TriggerAction.ALL);
                serviceRequestsComboBox.setWidth(180);

                if (model.getService() != null) {
                    serviceRequestsComboBox.setValue(new Request(model.getRequest()));
                    serviceRequestsComboBox.setSelection(Arrays.asList(new Request(model
                            .getRequest())));
                }

                serviceRequestsComboBox.addListener(Events.Select, new Listener<FieldEvent>() {

                    public void handleEvent(FieldEvent be) {
                        final Request request = (Request) be.getField().getValue();
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "GeoServer Rules",
                                "Rule " + model.getPriority() + ": Request changed -> "
                                        + request.getRequest() });

                        model.setRequest(request.getRequest());
                        Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_RULES_GRID_COMBOS, model);
                    }
                });

                return serviceRequestsComboBox;
            }

            /**
             * TODO: Call Services Service here!!
             * 
             * @param gsInstance
             * @param rulesService
             * 
             * @return
             */
            private ListStore<Request> getAvailableServicesRequest(GSInstance gsInstance,
                    String service) {
                ListStore<Request> availableServicesRequests = new ListStore<Request>();
                List<Request> requests = new ArrayList<Request>();

                Request all = new Request("*");
                Request getCapabilities = new Request("GetCapabilities");

                requests.add(all);
                requests.add(getCapabilities);

                if (service.equalsIgnoreCase("WMS")) {
                    Request getMap = new Request("GetMap");
                    Request describeLayer = new Request("DescribeLayer");
                    requests.add(getMap);
                    requests.add(describeLayer);
                }

                if (service.equalsIgnoreCase("WCS")) {
                    Request getCoverage = new Request("GetCoverage");
                    Request describeCoverage = new Request("DescribeCoverage");
                    requests.add(getCoverage);
                    requests.add(describeCoverage);
                }

                if (service.equalsIgnoreCase("WFS")) {
                    Request getFeatureType = new Request("GetFeatureType");
                    Request describeFeatureType = new Request("DescribeFeatureType");
                    requests.add(getFeatureType);
                    requests.add(describeFeatureType);
                }

                availableServicesRequests.add(requests);

                return availableServicesRequests;
            }
        };

        return comboRendered;
    }

    /**
     * 
     * @return
     */
    private GridCellRenderer<Rule> createServiceWorkspacesComboBox() {
        GridCellRenderer<Rule> comboRendered = new GridCellRenderer<Rule>() {

            private boolean init;

            public Object render(final Rule model, String property, ColumnData config,
                    int rowIndex, int colIndex, ListStore<Rule> store, Grid<Rule> grid) {

                if (!init) {
                    init = true;
                    grid.addListener(Events.ColumnResize, new Listener<GridEvent<Rule>>() {

                        public void handleEvent(GridEvent<Rule> be) {
                            for (int i = 0; i < be.getGrid().getStore().getCount(); i++) {
                                if (be.getGrid().getView().getWidget(i, be.getColIndex()) != null
                                        && be.getGrid().getView().getWidget(i, be.getColIndex()) instanceof BoxComponent) {
                                    ((BoxComponent) be.getGrid().getView().getWidget(i,
                                            be.getColIndex())).setWidth(be.getWidth() - 10);
                                }
                            }
                        }
                    });
                }

                // TODO: generalize this!
                ComboBox<Workspace> workspacesComboBox = new ComboBox<Workspace>();
                workspacesComboBox.setId("ruleWorkspacesCombo");
                workspacesComboBox.setName("ruleWorkspacesCombo");
                workspacesComboBox.setEmptyText("(No workspace available)");
                workspacesComboBox.setDisplayField(BeanKeyValue.WORKSPACE.getValue());
                workspacesComboBox.setEditable(false);
                workspacesComboBox.setStore(getAvailableWorkspaces(model.getInstance()));
                workspacesComboBox.setTypeAhead(true);
                workspacesComboBox.setTriggerAction(TriggerAction.ALL);
                workspacesComboBox.setWidth(120);

                if (model.getWorkspace() != null) {
                    workspacesComboBox.setValue(new Workspace(model.getWorkspace()));
                    workspacesComboBox.setSelection(Arrays.asList(new Workspace(model
                            .getWorkspace())));
                }

                workspacesComboBox.addListener(Events.Select, new Listener<FieldEvent>() {

                    public void handleEvent(FieldEvent be) {
                        final Workspace workspace = (Workspace) be.getField().getValue();
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "GeoServer Workspaces",
                                "Rule " + model.getPriority() + ": Workspace changed" });

                        model.setWorkspace(workspace.getWorkspace());
                        model.setLayer("*");
                        Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_RULES_GRID_COMBOS, model);
                    }
                });

                return workspacesComboBox;
            }

            /**
             * TODO: Call User Service here!!
             * 
             * @param rule
             * 
             * @return
             */
            private ListStore<Workspace> getAvailableWorkspaces(final GSInstance gsInstance) {
                RpcProxy<PagingLoadResult<Workspace>> workspacesProxy = new RpcProxy<PagingLoadResult<Workspace>>() {

                    @Override
                    protected void load(Object loadConfig,
                            AsyncCallback<PagingLoadResult<Workspace>> callback) {
                        workspacesService.getWorkspaces((PagingLoadConfig) loadConfig, gsInstance
                                .getBaseURL(), callback);
                    }

                };
                BasePagingLoader<PagingLoadResult<ModelData>> workspacesLoader = new BasePagingLoader<PagingLoadResult<ModelData>>(
                        workspacesProxy);
                workspacesLoader.setRemoteSort(false);
                ListStore<Workspace> availableWorkspaces = new ListStore<Workspace>(
                        workspacesLoader);

                return availableWorkspaces;
            }
        };

        return comboRendered;
    }

    /**
     * 
     * @return
     */
    private GridCellRenderer<Rule> createWorkspacesLayersComboBox() {
        GridCellRenderer<Rule> comboRendered = new GridCellRenderer<Rule>() {

            private boolean init;

            public Object render(final Rule model, String property, ColumnData config,
                    int rowIndex, int colIndex, ListStore<Rule> store, Grid<Rule> grid) {

                if (!init) {
                    init = true;
                    grid.addListener(Events.ColumnResize, new Listener<GridEvent<Rule>>() {

                        public void handleEvent(GridEvent<Rule> be) {
                            for (int i = 0; i < be.getGrid().getStore().getCount(); i++) {
                                if (be.getGrid().getView().getWidget(i, be.getColIndex()) != null
                                        && be.getGrid().getView().getWidget(i, be.getColIndex()) instanceof BoxComponent) {
                                    ((BoxComponent) be.getGrid().getView().getWidget(i,
                                            be.getColIndex())).setWidth(be.getWidth() - 10);
                                }
                            }
                        }
                    });
                }

                // TODO: generalize this!
                ComboBox<Layer> workspaceLayersComboBox = new ComboBox<Layer>();
                workspaceLayersComboBox.setId("ruleLayersCombo");
                workspaceLayersComboBox.setName("ruleLayersCombo");
                workspaceLayersComboBox.setEmptyText("(No layer available)");
                workspaceLayersComboBox.setDisplayField(BeanKeyValue.LAYER.getValue());
                workspaceLayersComboBox.setEditable(false);
                workspaceLayersComboBox.setStore(getAvailableLayers(model.getInstance(), model
                        .getWorkspace()));
                workspaceLayersComboBox.setTypeAhead(true);
                workspaceLayersComboBox.setTriggerAction(TriggerAction.ALL);
                workspaceLayersComboBox.setWidth(120);

                if (model.getLayer() != null) {
                    workspaceLayersComboBox.setValue(new Layer(model.getLayer()));
                    workspaceLayersComboBox
                            .setSelection(Arrays.asList(new Layer(model.getLayer())));
                }

                workspaceLayersComboBox.addListener(Events.Select, new Listener<FieldEvent>() {

                    public void handleEvent(FieldEvent be) {
                        final Layer layer = (Layer) be.getField().getValue();
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "GeoServer Layers",
                                "Rule " + model.getPriority() + ": Layers changed" });

                        model.setLayer(layer.getLayer());
                        Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_RULES_GRID_COMBOS, model);
                    }
                });

                return workspaceLayersComboBox;
            }

            /**
             * TODO: Call User Service here!!
             * 
             * @param workspace
             * @param rule
             * 
             * @return
             */
            private ListStore<Layer> getAvailableLayers(final GSInstance gsInstance,
                    final String workspace) {
                RpcProxy<PagingLoadResult<Layer>> workspacesProxy = new RpcProxy<PagingLoadResult<Layer>>() {

                    @Override
                    protected void load(Object loadConfig,
                            AsyncCallback<PagingLoadResult<Layer>> callback) {
                        workspacesService.getLayers((PagingLoadConfig) loadConfig, gsInstance
                                .getBaseURL(), workspace, callback);
                    }

                };
                BasePagingLoader<PagingLoadResult<ModelData>> workspacesLoader = new BasePagingLoader<PagingLoadResult<ModelData>>(
                        workspacesProxy);
                workspacesLoader.setRemoteSort(false);
                ListStore<Layer> availableWorkspaceLayers = new ListStore<Layer>(workspacesLoader);

                return availableWorkspaceLayers;
            }
        };

        return comboRendered;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#createStore()
     */
    @Override
    public void createStore() {
        this.toolBar = new PagingToolBar(25);

        // Loader fro rulesService
        this.proxy = new RpcProxy<PagingLoadResult<Rule>>() {

            @Override
            protected void load(Object loadConfig, AsyncCallback<PagingLoadResult<Rule>> callback) {
                rulesService.getRules((PagingLoadConfig) loadConfig, false, callback);
            }

        };
        loader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);
        loader.setRemoteSort(false);
        store = new ListStore<Rule>(loader);
        store.sort(BeanKeyValue.PRIORITY.getValue(), SortDir.ASC);

        // Search tool
        SearchFilterField<Rule> filter = new SearchFilterField<Rule>() {

            @Override
            protected boolean doSelect(Store<Rule> store, Rule parent, Rule record,
                    String property, String filter) {

                String name = parent.get("name");
                name = name.toLowerCase();
                if (name.indexOf(filter.toLowerCase()) != -1) {
                    return true;
                }
                return false;
            }

        };
        filter.setWidth(130);
        filter.setIcon(Resources.ICONS.search());
        // Bind the filter field to your grid store (grid.getStore())
        filter.bind(store);

        // Add Rule button
        // TODO: generalize this!
        Button addRuleButton = new Button("Add Rule");
        addRuleButton.setIcon(Resources.ICONS.add());

        addRuleButton.addListener(Events.OnClick, new Listener<ButtonEvent>() {

            public void handleEvent(ButtonEvent be) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                        "GeoServer Rule", "Add Rule" });
            }
        });

        this.toolBar.bind(loader);
        this.toolBar.add(new SeparatorToolItem());
        this.toolBar.add(addRuleButton);
        this.toolBar.add(new SeparatorToolItem());
        this.toolBar.add(filter);
        this.toolBar.add(new SeparatorToolItem());

        // this.toolBar.disable();

        setUpLoadListener();
    }

    /**
     * Gets the loader.
     * 
     * @return the loader
     */
    public PagingLoader<PagingLoadResult<ModelData>> getLoader() {
        return loader;
    }

    /**
     * Gets the tool bar.
     * 
     * @return the tool bar
     */
    public PagingToolBar getToolBar() {
        return toolBar;
    }

    /**
     * Clear grid elements.
     */
    public void clearGridElements() {
        this.store.removeAll();
        this.toolBar.clear();
        this.toolBar.disable();
    }

    /**
     * Sets the up load listener.
     */
    private void setUpLoadListener() {
        loader.addLoadListener(new LoadListener() {

            @Override
            public void loaderBeforeLoad(LoadEvent le) {
                if (!toolBar.isEnabled())
                    toolBar.enable();
            }

            @Override
            public void loaderLoad(LoadEvent le) {

                // TODO: change messages here!!

                BasePagingLoadResult<?> result = le.getData();
                if (!result.getData().isEmpty()) {
                    int size = result.getData().size();
                    String message = "";
                    if (size == 1)
                        message = I18nProvider.getMessages().featureLabel();
                    else
                        message = I18nProvider.getMessages().featurePluralLabel();
                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                            I18nProvider.getMessages().featureServiceName(),
                            I18nProvider.getMessages().foundLabel() + " " + result.getData().size()
                                    + " " + message });
                } else {
                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_ALERT_MESSAGE, new String[] {
                            I18nProvider.getMessages().featureServiceName(),
                            I18nProvider.getMessages().featureNotFoundMessage() });
                }
            }

        });
    }

}