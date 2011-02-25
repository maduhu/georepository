/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.AddGsUserWidget,v. 0.1 10-feb-2011 12.01.49 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 10-feb-2011 12.01.49 $
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

import it.geosolutions.georepo.gui.client.Constants;
import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.Resources;
import it.geosolutions.georepo.gui.client.form.GeoRepoFormWidget;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.BeanKeyValue;
import it.geosolutions.georepo.gui.client.model.GSInstance;
import it.geosolutions.georepo.gui.client.model.GSUser;
import it.geosolutions.georepo.gui.client.model.Profile;
import it.geosolutions.georepo.gui.client.model.Rule;
import it.geosolutions.georepo.gui.client.model.data.Grant;
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

import com.extjs.gxt.ui.client.Style.SelectionMode;
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
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.LoadListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.widget.BoxComponent;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class AddGsUserWidget.
 */
public class EditRuleWidget extends GeoRepoFormWidget {// GeoRepoEditGridWidget {

    /** The submit event. */
    private EventType submitEvent;

    /** The close on submit. */
    private boolean closeOnSubmit;

    /** The rules service. */
    private RulesManagerServiceRemoteAsync rulesService;

    /** The gs users service. */
    private GsUsersManagerServiceRemoteAsync gsUsersService;

    /** The profiles service. */
    private ProfilesManagerServiceRemoteAsync profilesService;

    /** The instances service. */
    private InstancesManagerServiceRemoteAsync instancesService;

    /** The workspaces service. */
    private WorkspacesManagerServiceRemoteAsync workspacesService;

    /** The proxy. */
    private RpcProxy<PagingLoadResult<Rule>> proxy;

    /** The loader. */
    private PagingLoader<PagingLoadResult<ModelData>> loader;

    /** The tool bar. */
    // private PagingToolBar toolBar;

    /** The column priority width. */
    private static final int COLUMN_PRIORITY_WIDTH = 30;

    /** The column user width. */
    private static final int COLUMN_USER_WIDTH = 130;

    /** The column profile width. */
    private static final int COLUMN_PROFILE_WIDTH = 160;

    /** The column instance width. */
    private static final int COLUMN_INSTANCE_WIDTH = 160;

    /** The column service width. */
    private static final int COLUMN_SERVICE_WIDTH = 100;

    /** The column request width. */
    private static final int COLUMN_REQUEST_WIDTH = 190;

    /** The column workspace width. */
    private static final int COLUMN_WORKSPACE_WIDTH = 130;

    /** The column layer width. */
    private static final int COLUMN_LAYER_WIDTH = 130;

    /** The column grant width. */
    private static final int COLUMN_GRANT_WIDTH = 100;

    /** The profile. */
    public Rule model = new Rule();

    /** The store. */
    public ListStore<Rule> store = new ListStore<Rule>();

    /** The grid. */
    public Grid<Rule> grid;

    public String status = "UPDATE";
    
    boolean unique = false;
    
    /** The RuleGridWidget. */
    public Grid<Rule> parentGrid;
    
    boolean priorityEdited = false;
    
    RulesView rulesView;
    
    



    /**
     * Instantiates a new adds the gs profile widget.
     * @param controller 
     * 
     * @param submitEvent
     *            the submit event
     * @param closeOnSubmit
     *            the close on submit
     */
    public EditRuleWidget(EventType submitEvent, boolean closeOnSubmit,
            RulesManagerServiceRemoteAsync rulesService,
            GsUsersManagerServiceRemoteAsync gsUsersService,
            ProfilesManagerServiceRemoteAsync profilesService,
            InstancesManagerServiceRemoteAsync instancesService,
            WorkspacesManagerServiceRemoteAsync workspacesService) {
        super();
        this.submitEvent = submitEvent;
        this.closeOnSubmit = closeOnSubmit;
        this.rulesService = rulesService;
        this.gsUsersService = gsUsersService;
        this.profilesService = profilesService;
        this.instancesService = instancesService;
        this.workspacesService = workspacesService;
        
        //createStore();
        //initGrid();
    }

    /**
     * Instantiates a new geo repo grid widget.
     * 
     * @param models
     *            the models
     */
    public EditRuleWidget(List models) {
        createStore();
        this.store.add(models);
        initGrid();
    }

    /**
     * Instantiates a new geo repo grid widget.
     */
    public EditRuleWidget() {
        createStore();
        initGrid();
    }

    /**
     * Instantiates a new rule grid widget.
     * 
     * @param models
     *            the models
     */
    /*
     * public EditRuleWidget(List<Rule> models) { super(models); }
     */

    /**
     * Gets the submit event.
     * 
     * @return the submit event
     */
    protected EventType getSubmitEvent() {
        return this.submitEvent;
    }
   
    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.form.IForm#execute()
     */
    public void execute() {
        this.saveStatus.setBusy("Operation in progress");
        /*
         * this.user.setId(-1); this.user.setName(userName.getValue());
         * this.user.setPassword(password.getValue()); this.user.setFullName(fullName.getValue());
         * this.user.setEmailAddress(eMail.getValue()); this.user.setDateCreation(new Date());
         * this.user.setEnabled(true); this.user.setProfile(profilesComboBox.getValue());
         */
        List<Rule> rules = null;
        
        if(parentGrid!=null)rules = new ArrayList<Rule>(parentGrid.getStore().getModels());
        
        /*
        if(!status.equals("UPDATE")){
            try {
                this.rulesService.findRule(model, new AsyncCallback<PagingLoadResult<Rule>>() {
    
                    public void onFailure(Throwable caught) {
                        unique = false;
                    }
    
                    public void onSuccess(PagingLoadResult<Rule> result) {
                        unique = true;
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE, new String[] {
                                I18nProvider.getMessages().ruleServiceName(),
                                "There's just another rule with this setting!!" });
                        closeOnSubmit=false;
                        return;
                    }
    
                });
            } catch (ApplicationException e) {
                e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        */
        /*if (rules!=null && !Constants.getInstance().checkUniqueRule(rules, model)) {
        }else{
            Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE, new String[] {
                    I18nProvider.getMessages().ruleServiceName(),
                    "There's just another rule with this setting!!" });
            closeOnSubmit=false;
        } */ 
            if(status.equals("UPDATE")){
                Dispatcher.forwardEvent(GeoRepoEvents.RULE_SAVE, model);
                closeOnSubmit=true;
            }else{
                //if(model.getPriority()==0)model.setPriority(-1);
                Dispatcher.forwardEvent(GeoRepoEvents.RULE_ADD, model);
                closeOnSubmit=true;
            }

        
        if (this.closeOnSubmit) {
            cancel();
        }

        this.injectEvent();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.digitalglobe.dgwatch.gui.client.form.DGWATCHFormWidget#addComponentToForm ()
     */
    @Override
    public void addComponentToForm() {

        // createProfilesComboBox();
        /*
         * ColumnModel cm = prepareColumnModel(); this.formPanel.add(cm);
         */
        if (grid != null)
            this.formPanel.add(grid);
        addOtherComponents();
    }
   
    public ColumnModel prepareColumnModel() {
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

        ColumnConfig rulePriorityColumn = new ColumnConfig();
        rulePriorityColumn.setId(BeanKeyValue.PRIORITY.getValue());
        rulePriorityColumn.setWidth(COLUMN_PRIORITY_WIDTH);
        rulePriorityColumn.setRenderer(this.createPriorityCustomField());// CustomField//createUsersComboBox
        rulePriorityColumn.setMenuDisabled(false);
        rulePriorityColumn.setSortable(true);
        configs.add(rulePriorityColumn);

        ColumnConfig ruleUserColumn = new ColumnConfig();
        ruleUserColumn.setId(BeanKeyValue.USER.getValue());
        ruleUserColumn.setHeader("User");
        ruleUserColumn.setWidth(COLUMN_USER_WIDTH);
        ruleUserColumn.setRenderer(this.createUsersComboBox());// CustomField//createUsersComboBox
        ruleUserColumn.setMenuDisabled(true);
        ruleUserColumn.setSortable(false);
        configs.add(ruleUserColumn);

        ColumnConfig ruleProfileColumn = new ColumnConfig();
        ruleProfileColumn.setId(BeanKeyValue.PROFILE.getValue());
        ruleProfileColumn.setHeader("Profile");
        ruleProfileColumn.setWidth(COLUMN_PROFILE_WIDTH);
        ruleProfileColumn.setRenderer(this.createProfilesComboBox());
        ruleProfileColumn.setMenuDisabled(true);
        ruleProfileColumn.setSortable(false);
        configs.add(ruleProfileColumn);

        ColumnConfig ruleInstanceColumn = new ColumnConfig();
        ruleInstanceColumn.setId(BeanKeyValue.INSTANCE.getValue());
        ruleInstanceColumn.setHeader("Instance");
        ruleInstanceColumn.setWidth(COLUMN_INSTANCE_WIDTH);
        ruleInstanceColumn.setRenderer(this.createInstancesComboBox());
        ruleInstanceColumn.setMenuDisabled(true);
        ruleInstanceColumn.setSortable(false);
        configs.add(ruleInstanceColumn);

        ColumnConfig ruleServiceColumn = new ColumnConfig();
        ruleServiceColumn.setId(BeanKeyValue.SERVICE.getValue());
        ruleServiceColumn.setHeader("Service");
        ruleServiceColumn.setWidth(COLUMN_SERVICE_WIDTH);
        ruleServiceColumn.setRenderer(this.createServicesComboBox());
        ruleServiceColumn.setMenuDisabled(true);
        ruleServiceColumn.setSortable(false);
        configs.add(ruleServiceColumn);

        ColumnConfig ruleServiceRequestColumn = new ColumnConfig();
        ruleServiceRequestColumn.setId(BeanKeyValue.REQUEST.getValue());
        ruleServiceRequestColumn.setHeader("Request");
        ruleServiceRequestColumn.setWidth(COLUMN_REQUEST_WIDTH);
        ruleServiceRequestColumn.setRenderer(this.createServicesRequestComboBox());
        ruleServiceRequestColumn.setMenuDisabled(true);
        ruleServiceRequestColumn.setSortable(false);
        configs.add(ruleServiceRequestColumn);

        ColumnConfig ruleServiceWorkspacesColumn = new ColumnConfig();
        ruleServiceWorkspacesColumn.setId(BeanKeyValue.WORKSPACE.getValue());
        ruleServiceWorkspacesColumn.setHeader("Workspace");
        ruleServiceWorkspacesColumn.setWidth(COLUMN_WORKSPACE_WIDTH);
        ruleServiceWorkspacesColumn.setRenderer(this.createServiceWorkspacesComboBox());
        ruleServiceWorkspacesColumn.setMenuDisabled(true);
        ruleServiceWorkspacesColumn.setSortable(false);
        configs.add(ruleServiceWorkspacesColumn);

        ColumnConfig ruleWorkspaceLayersColumn = new ColumnConfig();
        ruleWorkspaceLayersColumn.setId(BeanKeyValue.LAYER.getValue());
        ruleWorkspaceLayersColumn.setHeader("Layer");
        ruleWorkspaceLayersColumn.setWidth(COLUMN_LAYER_WIDTH);
        ruleWorkspaceLayersColumn.setRenderer(this.createWorkspacesLayersComboBox());
        ruleWorkspaceLayersColumn.setMenuDisabled(true);
        ruleWorkspaceLayersColumn.setSortable(false);
        configs.add(ruleWorkspaceLayersColumn);

        ColumnConfig ruleGrantsColumn = new ColumnConfig();
        ruleGrantsColumn.setId(BeanKeyValue.GRANT.getValue());
        ruleGrantsColumn.setHeader("Grant");
        ruleGrantsColumn.setWidth(COLUMN_GRANT_WIDTH);
        ruleGrantsColumn.setRenderer(this.createGrantsComboBox());
        ruleGrantsColumn.setMenuDisabled(true);
        ruleGrantsColumn.setSortable(false);
        configs.add(ruleGrantsColumn);

        return new ColumnModel(configs);
    }

    /**
     * Creates the users field.
     * 
     * @return the grid cell renderer
     */
    private GridCellRenderer<Rule> createPriorityCustomField() {
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
                                    ((BoxComponent) be.getGrid().getView()
                                            .getWidget(i, be.getColIndex())).setWidth(be.getWidth() - 10);
                                }
                            }
                        }
                    });
                }

                // TODO: generalize this!
                ArrayList list = new ArrayList();

                for (int i = 0; i < store.getCount(); i++) {
                    Rule rule = ((Rule) store.getAt(i));
                    list.add(rule);
                }

                TextField priorityCustomField = new TextField();// (ListField)
                                                               // getAvailablepriority().getModels()
                priorityCustomField.setId("rulePriorityCombo");
                priorityCustomField.setName("rulePriorityCombo");
                priorityCustomField.setEmptyText("*");
                priorityCustomField.setFieldLabel(BeanKeyValue.PRIORITY.getValue());// DisplayField
                priorityCustomField.setValue(BeanKeyValue.PRIORITY.getValue());
                priorityCustomField.setReadOnly(false);

                //List<GSUser> au = getAvailablepriority().getModels();
                priorityCustomField.setWidth(COLUMN_PRIORITY_WIDTH - 10);
                priorityCustomField.show();

                if (model.getPriority() != -1) {
                    long name2 = model.getPriority();
                    priorityCustomField.setValue(name2);
                }else{
                    priorityCustomField.setValue("-1");
                }

                priorityCustomField.addListener(Events.OnChange, new Listener<FieldEvent>() {

                    public void handleEvent(FieldEvent be) {
                        /*(be.getComponent()).show();
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
                                new String[] { "GeoServer Rules",
                                        "Rule " + model.getPriority() + ": User changed" });

                        model.setUser((GSUser) be.getField().getValue());
                        Dispatcher.forwardEvent(GeoRepoEvents.RULE_UPDATE_GRID_COMBO, model);
                        */
                        //Dispatcher.forwardEvent(GeoRepoEvents.RULE_UPDATE_EDIT_GRID_COMBO, model);
                        
                        //if(model.getPriority()!=-1 && (be.getField().getValue()!=null) && model.getPriority()!=Long.parseLong(BeanKeyValue.PRIORITY.getValue()))
                        priorityEdited = true;
                        try{
                            model.setPriority(Long.parseLong((String) be.getField().getValue()));
                        }catch(Exception e){                    
                            Dispatcher.forwardEvent(GeoRepoEvents.SEND_ALERT_MESSAGE, new String[] {
                                I18nProvider.getMessages().remoteServiceName(),
                                e.getMessage() });
                        }
                        Dispatcher.forwardEvent(GeoRepoEvents.RULE_UPDATE_EDIT_GRID_COMBO, model);
                    }
                });

                return priorityCustomField;
            }

            /**
             * TODO: Call User Service here!!
             * 
             * @return
             */
            private ListStore<GSUser> getAvailablePriority() {
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
                ListStore<GSUser> availablePriority = new ListStore<GSUser>(usersLoader);

                return availablePriority;
            }
        };

        return comboRendered;
    }

    /**
     * Creates the users combo box.
     * 
     * @return the grid cell renderer
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
                                    ((BoxComponent) be.getGrid().getView()
                                            .getWidget(i, be.getColIndex())).setWidth(be.getWidth() - 10);
                                }
                            }
                        }
                    });
                }

                // TODO: generalize this!
                ComboBox<GSUser> usersComboBox = new ComboBox<GSUser>();
                usersComboBox.setId("ruleUsersCombo");
                usersComboBox.setName("ruleUsersCombo");
                usersComboBox.setEmptyText("(No profile available)");
                usersComboBox.setDisplayField(BeanKeyValue.NAME.getValue());
                usersComboBox.setEditable(false);
                usersComboBox.setStore(getAvailableUsers());
                usersComboBox.setTypeAhead(true);
                usersComboBox.setTriggerAction(TriggerAction.ALL);
                usersComboBox.setWidth(120);

                if (model.getUser() != null) {
                    usersComboBox.setValue(model.getUser());
                }

                usersComboBox.addListener(Events.Select, new Listener<FieldEvent>() {

                    public void handleEvent(FieldEvent be) {
                        Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
                                new String[] { "GeoServer Rules",
                                        "Rule " + model.getPriority() + ": Rule changed" });

                        model.setUser((GSUser) be.getField().getValue());
                        Dispatcher.forwardEvent(GeoRepoEvents.RULE_UPDATE_EDIT_GRID_COMBO, model);
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
     * TODO: Call Rule Service here!!
     * 
     * @return
     */
    private ListStore<Rule> getAvailableRules() {
        ListStore<Rule> availableRules = new ListStore<Rule>();
        RpcProxy<PagingLoadResult<Rule>> ruleProxy = new RpcProxy<PagingLoadResult<Rule>>() {

            @Override
            protected void load(Object loadConfig, AsyncCallback<PagingLoadResult<Rule>> callback) {
                rulesService.getRules((PagingLoadConfig) loadConfig, false, callback);
            }

        };
        BasePagingLoader<PagingLoadResult<ModelData>> profilesLoader = new BasePagingLoader<PagingLoadResult<ModelData>>(
                ruleProxy);
        profilesLoader.setRemoteSort(false);
        availableRules = new ListStore<Rule>(profilesLoader);

        return availableRules;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.digitalglobe.dgwatch.gui.client.form.DGWATCHFormWidget#cancel()
     */
    @SuppressWarnings("deprecation")
    @Override
    public void cancel() {
        resetComponents();
        super.close();
        hide();
    }

    /**
     * Reset components.
     */
    public void resetComponents() {
        if(grid!=null && grid.getStore()!=null){
            //this.grid.getStore().removeAll(); 
            //this.grid.getStore().getLoader().load();
        }

        this.saveStatus.clearStatus("");
        // Dispatcher.forwardEvent(DGWATCHEvents.DISABLE_DRAW_BUTTON);
        // Dispatcher.forwardEvent(DGWATCHEvents.ERASE_AOI_FEATURES);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.digitalglobe.dgwatch.gui.client.widget.AddGenericAOIWidget# addOtherComponents()
     */
    public void addOtherComponents() {
        // wktArea = new TextArea();
        // wktArea.setFieldLabel(I18nProvider.getMessages().wktAbbreviation());
        // wktArea.setAllowBlank(false);
        // fieldSet.add(wktArea);
        //
        // draw = new Button(I18nProvider.getMessages().drawAoiButton(),
        // new SelectionListener<ButtonEvent>() {
        //
        // @Override
        // public void componentSelected(ButtonEvent ce) {
        // hide();
        // Dispatcher
        // .forwardEvent(DGWATCHEvents.ENABLE_DRAW_BUTTON, AddAOIWidget.this);
        // }
        // });
        //
        // draw.setIcon(Resources.ICONS.drawFeature());
        //
        // this.formPanel.addButton(draw);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.digitalglobe.dgwatch.gui.client.form.DGWATCHFormWidget#initSize()
     */
    @Override
    public void initSize() {
        setHeading(/* TODO: I18nProvider.getMessages().addAoiDialogTitle() */"Edit rule");
        setSize(1205, 175);
        // setAutoWidth(true);
        // setHeight(150);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.digitalglobe.dgwatch.gui.client.form.DGWATCHFormWidget#initSizeFormPanel ()
     */
    @Override
    public void initSizeFormPanel() {
        formPanel.setHeaderVisible(false);
        // formPanel.setSize(450, 350);
    }

    @Override
    public void injectEvent() {
        Dispatcher.forwardEvent(getSubmitEvent(), this.model);
    }

    public void setRuleService(RulesManagerServiceRemoteAsync rulesManagerServiceRemote) {
        this.rulesService = rulesManagerServiceRemote;
    }

    public void setUserService(GsUsersManagerServiceRemoteAsync usersManagerServiceRemote) {
        this.gsUsersService = usersManagerServiceRemote;
    }

    /**
     * Creates the profiles combo box.
     * 
     * @return the grid cell renderer
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
                                    ((BoxComponent) be.getGrid().getView()
                                            .getWidget(i, be.getColIndex())).setWidth(be.getWidth() - 10);
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
                        /*Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "GeoServer Rules",
                                "Rule " + model.getPriority() + ": Profile changed" });*/

                        model.setProfile((Profile) be.getField().getValue());
                        Dispatcher.forwardEvent(GeoRepoEvents.RULE_UPDATE_EDIT_GRID_COMBO, model);
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
     * Creates the instances combo box.
     * 
     * @return the grid cell renderer
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
                                    ((BoxComponent) be.getGrid().getView()
                                            .getWidget(i, be.getColIndex())).setWidth(be.getWidth() - 10);
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
                instancesComboBox.setDisplayField(BeanKeyValue.NAME.getValue());
                instancesComboBox.setEditable(false);
                instancesComboBox.setStore(getAvailableInstances());
                instancesComboBox.setTypeAhead(true);
                instancesComboBox.setTriggerAction(TriggerAction.ALL);
                instancesComboBox.setWidth(150);

                if (model.getInstance() != null) {
                    instancesComboBox.setValue(model.getInstance());
                    instancesComboBox.setSelection(Arrays.asList(model.getInstance()));
                } else {
                    GSInstance all = new GSInstance();
                    all.setId(-1);
                    all.setName("*");
                    all.setBaseURL("*");
                    instancesComboBox.setValue(all);
                    instancesComboBox.setSelection(Arrays.asList(all));
                }

                instancesComboBox.addListener(Events.Select, new Listener<FieldEvent>() {

                    public void handleEvent(FieldEvent be) {
                        final GSInstance instance = (GSInstance) be.getField().getValue();
                        /*Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "GeoServer Rules",
                                "Rule " + model.getPriority() + ": Instance changed" });*/

                        model.setInstance(instance);
                        model.setService("*");
                        model.setRequest("*");
                        model.setWorkspace("*");
                        model.setLayer("*");
                        Dispatcher.forwardEvent(GeoRepoEvents.RULE_UPDATE_EDIT_GRID_COMBO, model);
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
     * Creates the services combo box.
     * 
     * @return the grid cell renderer
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
                                    ((BoxComponent) be.getGrid().getView()
                                            .getWidget(i, be.getColIndex())).setWidth(be.getWidth() - 10);
                                }
                            }
                        }
                    });
                }

                // TODO: generalize this!
                final ComboBox<Service> servicesComboBox = new ComboBox<Service>();
                servicesComboBox.setId("ruleServicesCombo");
                servicesComboBox.setName("ruleServicesCombo");
                servicesComboBox.setEmptyText("(No service available)");
                servicesComboBox.setDisplayField(BeanKeyValue.SERVICE.getValue());
                servicesComboBox.setStore(getAvailableServices(model.getInstance()));
                servicesComboBox.setEditable(true);
                servicesComboBox.setTypeAhead(true);
                servicesComboBox.setTriggerAction(TriggerAction.ALL);
                servicesComboBox.setWidth(90);

                KeyListener keyListener = new KeyListener() {

                    @Override
                    public void componentKeyUp(ComponentEvent event) {
                        if (event.getKeyCode() == '\r') {
                            event.cancelBubble();

                            /*Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                    "GeoServer Rules",
                                    "Rule " + model.getPriority() + ": Service changed -> "
                                            + servicesComboBox.getRawValue() });*/

                            model.setService(servicesComboBox.getRawValue());
                            model.setRequest("*");
                            Dispatcher.forwardEvent(GeoRepoEvents.RULE_UPDATE_EDIT_GRID_COMBO, model);
                        }
                    }
                };

                servicesComboBox.addKeyListener(keyListener);

                if (model.getService() != null) {
                    servicesComboBox.setValue(new Service(model.getService()));
                    servicesComboBox.setSelection(Arrays.asList(new Service(model.getService())));
                }

                servicesComboBox.addListener(Events.Select, new Listener<FieldEvent>() {

                    public void handleEvent(FieldEvent be) {
                        final Service service = (Service) be.getField().getValue();
                        /*Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
                                new String[] {
                                        "GeoServer Rules",
                                        "Rule " + model.getPriority() + ": Service changed -> "
                                                + service.getService() });*/

                        model.setService(service.getService());
                        model.setRequest("*");
                        Dispatcher.forwardEvent(GeoRepoEvents.RULE_UPDATE_EDIT_GRID_COMBO, model);
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

                if (gsInstance != null && gsInstance.getBaseURL() != null
                        && !gsInstance.getBaseURL().equals("*")) {
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
     * Creates the services request combo box.
     * 
     * @return the grid cell renderer
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
                                    ((BoxComponent) be.getGrid().getView()
                                            .getWidget(i, be.getColIndex())).setWidth(be.getWidth() - 10);
                                }
                            }
                        }
                    });
                }

                // TODO: generalize this!
                final ComboBox<Request> serviceRequestsComboBox = new ComboBox<Request>();
                serviceRequestsComboBox.setId("ruleServicesRequestCombo");
                serviceRequestsComboBox.setName("ruleServicesRequestCombo");
                serviceRequestsComboBox.setEmptyText("(No service request available)");
                serviceRequestsComboBox.setDisplayField(BeanKeyValue.REQUEST.getValue());
                serviceRequestsComboBox.setStore(getAvailableServicesRequest(model.getInstance(),
                        model.getService()));
                serviceRequestsComboBox.setEditable(true);
                serviceRequestsComboBox.setTypeAhead(true);
                serviceRequestsComboBox.setTriggerAction(TriggerAction.ALL);
                serviceRequestsComboBox.setWidth(180);

                KeyListener keyListener = new KeyListener() {

                    @Override
                    public void componentKeyUp(ComponentEvent event) {
                        if (event.getKeyCode() == '\r') {
                            event.cancelBubble();

                            /*Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                    "GeoServer Rules",
                                    "Rule " + model.getPriority() + ": Request changed -> "
                                            + serviceRequestsComboBox.getRawValue() });*/

                            model.setRequest(serviceRequestsComboBox.getRawValue());
                            Dispatcher.forwardEvent(GeoRepoEvents.RULE_UPDATE_EDIT_GRID_COMBO, model);
                        }
                    }
                };

                serviceRequestsComboBox.addKeyListener(keyListener);

                if (model.getService() != null) {
                    serviceRequestsComboBox.setValue(new Request(model.getRequest()));
                    serviceRequestsComboBox.setSelection(Arrays.asList(new Request(model
                            .getRequest())));
                }

                serviceRequestsComboBox.addListener(Events.Select, new Listener<FieldEvent>() {

                    public void handleEvent(FieldEvent be) {
                        final Request request = (Request) be.getField().getValue();
                        /*Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
                                new String[] {
                                        "GeoServer Rules",
                                        "Rule " + model.getPriority() + ": Request changed -> "
                                                + request.getRequest() });*/

                        model.setRequest(request.getRequest());
                        Dispatcher.forwardEvent(GeoRepoEvents.RULE_UPDATE_EDIT_GRID_COMBO, model);
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

                if (service != null && service.equalsIgnoreCase("WMS")) {
                    Request getMap = new Request("GetMap");
                    Request describeLayer = new Request("DescribeLayer");
                    requests.add(getMap);
                    requests.add(describeLayer);
                }

                if (service != null && service.equalsIgnoreCase("WCS")) {
                    Request getCoverage = new Request("GetCoverage");
                    Request describeCoverage = new Request("DescribeCoverage");
                    requests.add(getCoverage);
                    requests.add(describeCoverage);
                }

                if (service != null && service.equalsIgnoreCase("WFS")) {
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
     * Creates the service workspaces combo box.
     * 
     * @return the grid cell renderer
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
                                    ((BoxComponent) be.getGrid().getView()
                                            .getWidget(i, be.getColIndex())).setWidth(be.getWidth() - 10);
                                }
                            }
                        }
                    });
                }

                // TODO: generalize this!
                ComboBox<Workspace> workspacesComboBox = new ComboBox<Workspace>();
                workspacesComboBox.setId("ruleWorkspacesCombo");
                workspacesComboBox.setName("ruleWorkspacesCombo");

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
                workspacesComboBox.setEmptyText("(No workspace available)");
                workspacesComboBox.addListener(Events.Select, new Listener<FieldEvent>() {

                    public void handleEvent(FieldEvent be) {
                        final Workspace workspace = (Workspace) be.getField().getValue();
                        /*Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "GeoServer Workspaces",
                                "Rule " + model.getPriority() + ": Workspace changed" });*/

                        model.setWorkspace(workspace.getWorkspace());
                        model.setLayer("*");
                        Dispatcher.forwardEvent(GeoRepoEvents.RULE_UPDATE_EDIT_GRID_COMBO, model);
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
                        workspacesService.getWorkspaces((PagingLoadConfig) loadConfig,
                                gsInstance != null ? gsInstance.getBaseURL() : null, gsInstance,
                                callback);
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
     * Creates the workspaces layers combo box.
     * 
     * @return the grid cell renderer
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
                                    ((BoxComponent) be.getGrid().getView()
                                            .getWidget(i, be.getColIndex())).setWidth(be.getWidth() - 10);
                                }
                            }
                        }
                    });
                }

                // TODO: generalize this!
                ComboBox<Layer> workspaceLayersComboBox = new ComboBox<Layer>();
                workspaceLayersComboBox.setId("ruleLayersCombo");
                workspaceLayersComboBox.setName("ruleLayersCombo");

                workspaceLayersComboBox.setDisplayField(BeanKeyValue.LAYER.getValue());
                workspaceLayersComboBox.setEditable(false);
                workspaceLayersComboBox.setStore(getAvailableLayers(model.getInstance(),
                        model.getWorkspace()));
                workspaceLayersComboBox.setTypeAhead(true);
                workspaceLayersComboBox.setTriggerAction(TriggerAction.ALL);
                workspaceLayersComboBox.setWidth(120);

                if (model.getLayer() != null) {
                    workspaceLayersComboBox.setValue(new Layer(model.getLayer()));
                    workspaceLayersComboBox
                            .setSelection(Arrays.asList(new Layer(model.getLayer())));
                }
                workspaceLayersComboBox.setEmptyText("(No layer available)");
                workspaceLayersComboBox.addListener(Events.Select, new Listener<FieldEvent>() {

                    public void handleEvent(FieldEvent be) {
                        final Layer layer = (Layer) be.getField().getValue();
                        /*Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "GeoServer Layers",
                                "Rule " + model.getPriority() + ": Layers changed" });*/

                        model.setLayer(layer.getLayer());
                        Dispatcher.forwardEvent(GeoRepoEvents.RULE_UPDATE_EDIT_GRID_COMBO, model);
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
                        workspacesService.getLayers((PagingLoadConfig) loadConfig,
                                gsInstance != null ? gsInstance.getBaseURL() : null, workspace,
                                callback);
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

    /**
     * Creates the grants combo box.
     * 
     * @return the grid cell renderer
     */
    private GridCellRenderer<Rule> createGrantsComboBox() {
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
                                    ((BoxComponent) be.getGrid().getView()
                                            .getWidget(i, be.getColIndex())).setWidth(be.getWidth() - 10);
                                }
                            }
                        }
                    });
                }

                // TODO: generalize this!
                ComboBox<Grant> grantsComboBox = new ComboBox<Grant>();
                grantsComboBox.setId("grantsCombo");
                grantsComboBox.setName("grantsCombo");

                grantsComboBox.setDisplayField(BeanKeyValue.GRANT.getValue());
                grantsComboBox.setEditable(false);
                grantsComboBox.setStore(getAvailableGrants());
                grantsComboBox.setTypeAhead(true);
                grantsComboBox.setTriggerAction(TriggerAction.ALL);
                grantsComboBox.setWidth(80);

                if (model.getGrant() != null) {
                    grantsComboBox.setValue(new Grant(model.getGrant()));
                    grantsComboBox.setSelection(Arrays.asList(new Grant(model.getGrant())));
                }
                grantsComboBox.setEmptyText("(No grant types available)");
                grantsComboBox.addListener(Events.Select, new Listener<FieldEvent>() {

                    public void handleEvent(FieldEvent be) {
                        final Grant grant = (Grant) be.getField().getValue();
                        /*Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                "GeoServer Rules",
                                "Rule " + model.getPriority() + ": Grant changed" });*/

                        model.setGrant(grant.getGrant());
                        Dispatcher.forwardEvent(GeoRepoEvents.RULE_UPDATE_EDIT_GRID_COMBO, model);
                    }
                });

                return grantsComboBox;
            }

            /**
             * TODO: Call User Service here!!
             * 
             * @param workspace
             * @param rule
             * 
             * @return
             */
            private ListStore<Grant> getAvailableGrants() {
                ListStore<Grant> availableRuleGrants = new ListStore<Grant>();
                List<Grant> grants = new ArrayList<Grant>();

                Grant allow = new Grant("ALLOW");
                Grant deny = new Grant("DENY");
                Grant limit = new Grant("LIMIT");

                grants.add(allow);
                grants.add(deny);
                grants.add(limit);

                availableRuleGrants.add(grants);

                return availableRuleGrants;
            }
        };

        return comboRendered;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#createStore()
     */
    // @Override
    public void createStore() {
        /*
         * this.toolBar = new PagingToolBar(
         * it.geosolutions.georepo.gui.client.Constants.DEFAULT_PAGESIZE);
         */
        // Loader fro rulesService
        this.proxy = new RpcProxy<PagingLoadResult<Rule>>() {

            @Override
            protected void load(Object loadConfig, AsyncCallback<PagingLoadResult<Rule>> callback) {
                //rulesService.getRules((PagingLoadConfig) loadConfig, false, callback);
            }

        };
        loader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);
        loader.setRemoteSort(false);
        loader.setSortField(BeanKeyValue.PRIORITY.getValue());
        loader.setSortDir(SortDir.ASC);
        store = new ListStore<Rule>(loader);
        if (store != null) {
            // store.sort(BeanKeyValue.PRIORITY.getValue(), SortDir.ASC);
            store.setSortField(BeanKeyValue.PRIORITY.getValue());
            store.setSortDir(SortDir.ASC);
        }

        // Search tool
        SearchFilterField<Rule> filter = new SearchFilterField<Rule>() {

            @Override
            protected boolean doSelect(Store<Rule> store, Rule parent, Rule record,
                    String property, String filter) {

                Long priority = parent.get(BeanKeyValue.PRIORITY.getValue());
                if (priority == Long.valueOf(filter)) {
                    return true;
                }
                return false;
            }

        };
        filter.setWidth(130);
        filter.setIcon(Resources.ICONS.search());
        // Bind the filter field to your grid store (grid.getStore())
        filter.bind(store);

        // Apply Changes button
        // TODO: generalize this!
        Button addRuleButton = new Button("Add Rule");
        addRuleButton.setIcon(Resources.ICONS.add());

        addRuleButton.addListener(Events.OnClick, new Listener<ButtonEvent>() {

            public void handleEvent(ButtonEvent be) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                        "GeoServer Rules", "Add Rule" });

                Rule new_rule = new Rule();
                new_rule.setId(-1);
                new_rule.setPriority(-1);
                new_rule = Constants.getInstance().createNewRule(new_rule);
                
                Dispatcher.forwardEvent(GeoRepoEvents.RULE_ADD, new GridStatus(grid,new_rule));

            }
        });
        /*
         * ric mod 20100217 Button saveRulesButton = new Button("Apply Changes");
         * saveRulesButton.setIcon(Resources.ICONS.save());
         * 
         * saveRulesButton.addListener(Events.OnClick, new Listener<ButtonEvent>() {
         * 
         * public void handleEvent(ButtonEvent be) {
         * Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
         * "GeoServer Rules", "Apply Changes" });
         * 
         * Dispatcher.forwardEvent(GeoRepoEvents.RULE_APPLY_CHANGES_GRID_COMBO); } });
         */
        /*
         * this.toolBar.bind(loader); this.toolBar.add(new SeparatorToolItem());
         * this.toolBar.add(addRuleButton); // this.toolBar.add(saveRulesButton);<<-- ric mod
         * 20100217 this.toolBar.add(new SeparatorToolItem()); this.toolBar.add(filter);
         * this.toolBar.add(new SeparatorToolItem());
         */
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
    /*
     * public PagingToolBar getToolBar() { return toolBar; }
     */

    /**
     * Clear grid elements.
     */
    public void clearGridElements() {
        this.store.removeAll();
        // this.toolBar.clear();
        // this.toolBar.disable();
    }

    /**
     * Sets the up load listener.
     */
    private void setUpLoadListener() {
        loader.addLoadListener(new LoadListener() {

            @Override
            public void loaderBeforeLoad(LoadEvent le) {
                /*
                 * if (!toolBar.isEnabled()) toolBar.enable();
                 */
            }

            @Override
            public void loaderLoad(LoadEvent le) {

                // TODO: change messages here!!

                BasePagingLoadResult<?> result = le.getData();
                if (!result.getData().isEmpty()) {
                    int size = result.getData().size();
                    String message = "";
                    if (size == 1)
                        message = I18nProvider.getMessages().recordLabel();
                    else
                        message = I18nProvider.getMessages().recordPluralLabel();
                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                            I18nProvider.getMessages().remoteServiceName(),
                            I18nProvider.getMessages().foundLabel() + " " + result.getData().size()
                                    + " " + message });
                } else {
                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_ALERT_MESSAGE, new String[] {
                            I18nProvider.getMessages().remoteServiceName(),
                            I18nProvider.getMessages().recordNotFoundMessage() });
                }
            }

        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#setGridProperties ()
     */
    // @Override
    public void setGridProperties() {
        // grid.setAutoExpandColumn(BeanKeyValue.NAME.getValue());
        // grid.addPlugin(emailEnable);
        // grid.addPlugin(rssEnable);

        grid.setLoadMask(true);
        grid.setAutoWidth(true);
        if (grid.getStore() != null) {
            grid.getStore().setSortField(BeanKeyValue.PRIORITY.getValue());
            grid.getStore().setSortDir(SortDir.ASC);
        }
        // grid.setHeight("100%");<<-- ric mod re 20100217
    }

    /**
     * Inits the grid.
     */
    // @Override
    public void initGrid() {
        ColumnModel cm = prepareColumnModel();

        grid = new Grid<Rule>(store, cm);
        grid.setBorders(true);

        grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
     // if(grid.getParent()!=null && grid.getParent().getParent()!=null && grid.getParent().getParent().getParent()!=null)grid.getParent().getParent().getParent().setHeight("95%");
     // if(grid.getParent()!=null &&  grid.getParent().getParent()!=null)grid.getParent().getParent().setHeight("95%");
     // if(grid.getParent()!=null)grid.getParent().setHeight("95%");
        grid.setHeight("70px");//95% <<-- ric add 20100216
        // grid.setAutoHeight(true);
        // grid.set
        grid.setLazyRowRender(0);// <<-- ric add 20100217
        setGridProperties();
    }
    // @Override
    public void initGrid(ListStore<Rule> store) {
        ColumnModel cm = prepareColumnModel();

        grid = new Grid<Rule>(store, cm);
        grid.setBorders(true);

        grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
     // if(grid.getParent()!=null && grid.getParent().getParent()!=null && grid.getParent().getParent().getParent()!=null)grid.getParent().getParent().getParent().setHeight("95%");
     // if(grid.getParent()!=null &&  grid.getParent().getParent()!=null)grid.getParent().getParent().setHeight("95%");
     // if(grid.getParent()!=null)grid.getParent().setHeight("95%");
        grid.setHeight("70px");//95% <<-- ric add 20100216
        // grid.setAutoHeight(true);
        // grid.set
        grid.setLazyRowRender(0);// <<-- ric add 20100217
        setGridProperties();
    }
    /**
     * Sets the model.
     * 
     * @param model
     *            the new model
     */
    public void setModel(Rule model) {
        this.model = model;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.widget.Component#getModel()
     */
    @SuppressWarnings({ "unchecked"})
    public Rule getModel() {
        return model;
    }

    public void setInstanceService(InstancesManagerServiceRemoteAsync instancesManagerServiceRemote) {
        this.instancesService = instancesManagerServiceRemote;
    }

    public void setWorkspaceService(
            WorkspacesManagerServiceRemoteAsync workspacesManagerServiceRemote) {
        this.workspacesService = workspacesManagerServiceRemote;
    }

    public void setProfileService(ProfilesManagerServiceRemoteAsync profilesManagerServiceRemote) {
        this.profilesService = profilesManagerServiceRemote;
    }

    public void setGsUserService(GsUsersManagerServiceRemoteAsync usersManagerServiceRemote) {
        this.gsUsersService = usersManagerServiceRemote;
    }
    
    public Grid<Rule> getParentGrid() {
        return parentGrid;
    }

    public void setParentGrid(Grid<Rule> parentGrid) {
        this.parentGrid = parentGrid;
    }

    public RulesView getRulesView() {
        return rulesView;
    }

    public void setRulesView(RulesView rulesView) {
        this.rulesView = rulesView;
    }

    /**
     * gsuser_id, profile_id, instance_id, service, request, workspace, layer
     * 
     * @param list
     * @param rule
     * @return
     *//*
    public boolean checkUniqueRule(List<Rule> list, Rule rule) {
        boolean res = false;
        if (list.size() > 0) {
            Iterator itr = list.iterator();
            while (itr.hasNext() && !res) {
                Rule r = (Rule) itr.next();
                if ( ((r.getUser() != null && rule.getUser() != null && r.getUser().getName()
                                .equals(rule.getUser().getName())) || (r.getUser() == null && rule
                                .getUser() == null))
                        && (r.getProfile() != null && rule.getProfile() != null
                                && r.getProfile().getName().equals(rule.getProfile().getName()) || (r
                                .getProfile() == null && rule.getProfile() == null))
                        && (r.getInstance() != null && rule.getInstance() != null
                                && r.getInstance().getName().equals(rule.getInstance().getName()) || (r
                                .getInstance() == null && rule.getInstance() == null))
                        && (r.getService() != null && rule.getService() != null
                                && r.getService().equals(rule.getService()) || (r.getService() == null && rule
                                .getService() == null))
                        && (r.getRequest() != null && rule.getRequest() != null
                                && r.getRequest().equals(rule.getRequest()) || (r.getRequest() == null && rule
                                .getRequest() == null))
                        && (r.getWorkspace() != null && rule.getWorkspace() != null
                                && r.getWorkspace().equals(rule.getWorkspace()) || (r
                                .getWorkspace() == null && rule.getWorkspace() == null))
                        && (r.getLayer() != null && rule.getLayer() != null
                                && r.getLayer().equals(rule.getLayer()) || (r.getLayer() == null && rule
                                .getLayer() == null))) {
                    res = true;
                }
            }
        }
        return res;
    }
*/
}
