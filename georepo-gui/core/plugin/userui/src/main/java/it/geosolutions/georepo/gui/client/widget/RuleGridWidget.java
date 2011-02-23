/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.RuleGridWidget,v. 0.1 10-feb-2011 13.21.50 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 10-feb-2011 13.21.50 $
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
import java.util.Iterator;
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
import com.extjs.gxt.ui.client.event.ComponentEvent;
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
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.Field.FieldMessages;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ComponentHelper;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;

// TODO: Auto-generated Javadoc
/**
 * The Class RuleGridWidget.
 */
public class RuleGridWidget extends GeoRepoGridWidget<Rule> {

	private static final int COLUMN_USER_WIDTH = 130;

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
	private PagingToolBar toolBar;

	/**
	 * Instantiates a new rule grid widget.
	 * 
	 * @param rulesService
	 *            the rules service
	 * @param gsUsersService
	 *            the gs users service
	 * @param profilesService
	 *            the profiles service
	 * @param instancesService
	 *            the instances service
	 * @param workspacesService
	 *            the workspaces service
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
	 * @see
	 * it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#setGridProperties
	 * ()
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#
	 * prepareColumnModel()
	 */
	@Override
	public ColumnModel prepareColumnModel() {
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		ColumnConfig rulePriorityColumn = new ColumnConfig();
		rulePriorityColumn.setId(BeanKeyValue.PRIORITY.getValue());
		rulePriorityColumn.setWidth(30);
		rulePriorityColumn.setMenuDisabled(false);
		rulePriorityColumn.setSortable(true);
		configs.add(rulePriorityColumn);

		ColumnConfig ruleUserColumn = new ColumnConfig();
		ruleUserColumn.setId(BeanKeyValue.USER.getValue());
		ruleUserColumn.setHeader("User");
		ruleUserColumn.setWidth(COLUMN_USER_WIDTH);
		ruleUserColumn.setRenderer(this.createUsersComboBox());// CustomField//createUsersCustomField
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
		ruleInstanceColumn.setWidth(160);
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
		ruleServiceRequestColumn.setRenderer(this
				.createServicesRequestComboBox());
		ruleServiceRequestColumn.setMenuDisabled(true);
		ruleServiceRequestColumn.setSortable(false);
		configs.add(ruleServiceRequestColumn);

		ColumnConfig ruleServiceWorkspacesColumn = new ColumnConfig();
		ruleServiceWorkspacesColumn.setId(BeanKeyValue.WORKSPACE.getValue());
		ruleServiceWorkspacesColumn.setHeader("Workspace");
		ruleServiceWorkspacesColumn.setWidth(130);
		ruleServiceWorkspacesColumn.setRenderer(this
				.createServiceWorkspacesComboBox());
		ruleServiceWorkspacesColumn.setMenuDisabled(true);
		ruleServiceWorkspacesColumn.setSortable(false);
		configs.add(ruleServiceWorkspacesColumn);

		ColumnConfig ruleWorkspaceLayersColumn = new ColumnConfig();
		ruleWorkspaceLayersColumn.setId(BeanKeyValue.LAYER.getValue());
		ruleWorkspaceLayersColumn.setHeader("Layer");
		ruleWorkspaceLayersColumn.setWidth(130);
		ruleWorkspaceLayersColumn.setRenderer(this
				.createWorkspacesLayersComboBox());
		ruleWorkspaceLayersColumn.setMenuDisabled(true);
		ruleWorkspaceLayersColumn.setSortable(false);
		configs.add(ruleWorkspaceLayersColumn);

		ColumnConfig ruleGrantsColumn = new ColumnConfig();
		ruleGrantsColumn.setId(BeanKeyValue.GRANT.getValue());
		ruleGrantsColumn.setHeader("Grant");
		ruleGrantsColumn.setWidth(100);
		ruleGrantsColumn.setRenderer(this.createGrantsComboBox());
		ruleGrantsColumn.setMenuDisabled(true);
		ruleGrantsColumn.setSortable(false);
		configs.add(ruleGrantsColumn);

		ColumnConfig detailsActionColumn = new ColumnConfig();
		detailsActionColumn.setId("ruleDetails");
		detailsActionColumn.setWidth(80);
		detailsActionColumn.setRenderer(this.createRuleDetailsButton());
		detailsActionColumn.setMenuDisabled(true);
		detailsActionColumn.setSortable(false);
		configs.add(detailsActionColumn);

		ColumnConfig removeActionColumn = new ColumnConfig();
		removeActionColumn.setId("removeRule");
		removeActionColumn.setWidth(80);
		removeActionColumn.setRenderer(this.createRuleDeleteButton());
		removeActionColumn.setMenuDisabled(true);
		removeActionColumn.setSortable(false);
		configs.add(removeActionColumn);

		ColumnConfig addActionColumn = new ColumnConfig();
		addActionColumn.setId("addRule");
		addActionColumn.setWidth(30);
		addActionColumn.setRenderer(this.createRuleAddButton());
		addActionColumn.setMenuDisabled(true);
		addActionColumn.setSortable(false);
		configs.add(addActionColumn);

		ColumnConfig priorityUpActionColumn = new ColumnConfig();
		priorityUpActionColumn.setId("rulePriorityUp");
		priorityUpActionColumn.setWidth(30);
		priorityUpActionColumn.setRenderer(this.createRulePriorityUpButton());
		priorityUpActionColumn.setMenuDisabled(true);
		priorityUpActionColumn.setSortable(false);
		configs.add(priorityUpActionColumn);

		ColumnConfig priorityDownActionColumn = new ColumnConfig();
		priorityDownActionColumn.setId("rulePriorityDwn");
		priorityDownActionColumn.setWidth(30);
		priorityDownActionColumn.setRenderer(this
				.createRulePriorityDownButton());
		priorityDownActionColumn.setMenuDisabled(true);
		priorityDownActionColumn.setSortable(false);
		configs.add(priorityDownActionColumn);

		return new ColumnModel(configs);
	}

	/**
	 * Creates the users combo box.
	 * 
	 * @return the grid cell renderer
	 */
	private GridCellRenderer<Rule> createUsersComboBox() {
		GridCellRenderer<Rule> comboRendered = new GridCellRenderer<Rule>() {

			private boolean init;

			public Object render(final Rule model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<Rule> store, Grid<Rule> grid) {

				if (!init) {
					init = true;
					grid.addListener(Events.ColumnResize,
							new Listener<GridEvent<Rule>>() {

								public void handleEvent(GridEvent<Rule> be) {
									for (int i = 0; i < be.getGrid().getStore()
											.getCount(); i++) {
										if (be.getGrid().getView()
												.getWidget(i, be.getColIndex()) != null
												&& be.getGrid()
														.getView()
														.getWidget(
																i,
																be.getColIndex()) instanceof BoxComponent) {
											((BoxComponent) be
													.getGrid()
													.getView()
													.getWidget(i,
															be.getColIndex()))
													.setWidth(be.getWidth() - 10);
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
					usersComboBox.setSelection(Arrays.asList(model.getUser()));
				}

				usersComboBox.addListener(Events.Select,
						new Listener<FieldEvent>() {

							public void handleEvent(FieldEvent be) {
								Dispatcher.forwardEvent(
										GeoRepoEvents.SEND_INFO_MESSAGE,
										new String[] {
												"GeoServer Rules",
												"Rule " + model.getPriority()
														+ ": User changed" });

								model.setUser((GSUser) be.getField().getValue());
								Dispatcher.forwardEvent(
										GeoRepoEvents.RULE_UPDATE_GRID_COMBO,
										model);
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
						gsUsersService.getGsUsers(
								(PagingLoadConfig) loadConfig, true, callback);
					}

				};
				BasePagingLoader<PagingLoadResult<ModelData>> usersLoader = new BasePagingLoader<PagingLoadResult<ModelData>>(
						userProxy);
				usersLoader.setRemoteSort(false);
				ListStore<GSUser> availableUsers = new ListStore<GSUser>(
						usersLoader);

				return availableUsers;
			}
		};

		return comboRendered;
	}

	/**
	 * Creates the users combo box.
	 * 
	 * @return the grid cell renderer
	 */
	private GridCellRenderer<Rule> createUsersCustomField() {
		GridCellRenderer<Rule> comboRendered = new GridCellRenderer<Rule>() {

			private boolean init;

			public Object render(final Rule model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<Rule> store, Grid<Rule> grid) {

				if (!init) {
					init = true;
					grid.addListener(Events.ColumnResize,
							new Listener<GridEvent<Rule>>() {

								public void handleEvent(GridEvent<Rule> be) {
									for (int i = 0; i < be.getGrid().getStore()
											.getCount(); i++) {
										if (be.getGrid().getView()
												.getWidget(i, be.getColIndex()) != null
												&& be.getGrid()
														.getView()
														.getWidget(
																i,
																be.getColIndex()) instanceof BoxComponent) {
											((BoxComponent) be
													.getGrid()
													.getView()
													.getWidget(i,
															be.getColIndex()))
													.setWidth(be.getWidth() - 10);
										}
										if (be.getGrid().getView()
												.getWidget(i, be.getColIndex()) != null
												&& be.getGrid()
														.getView()
														.getWidget(
																i,
																be.getColIndex()) instanceof CustomField) {
											((CustomField) be
													.getGrid()
													.getView()
													.getWidget(i,
															be.getColIndex()))
													.setWidth(be.getWidth() - 10);
										}
									}
								}
							});
				}

				// TODO: generalize this!
				ArrayList list = new ArrayList();
				
				for(int i = 0;i<store.getCount();i++){
					Rule rule = ((Rule)store.getAt(i));
					list.add(rule);
				}

				CustomField<Rule> usersCustomField = new CustomField<Rule>(list);// (ListField) getAvailableUsers().getModels()
				usersCustomField.first.setId("ruleUsersCombo");
				usersCustomField.first.setName("ruleUsersCombo");
				usersCustomField.first.setEmptyText("*");
				usersCustomField.first.setFieldLabel(BeanKeyValue.NAME.getValue());// DisplayField
				usersCustomField.first.setValue(BeanKeyValue.NAME.getValue());
				String name = model.getUser()!=null?model.getUser().getName():"*";
				usersCustomField.first.setValue(name);
				usersCustomField.first.setReadOnly(true);// setEditable(false)
				// usersCustomField.setStore(getAvailableUsers());
				// usersCustomField.setTypeAhead(true);
				// usersCustomField.setTriggerAction(TriggerAction.ALL);
				List au = getAvailableUsers().getModels();
				// usersCustomField.panel.setZIndex(20000000);
				usersCustomField.h.hide();
				usersCustomField.first.setWidth(COLUMN_USER_WIDTH - 10);
				usersCustomField.first.show();
				//usersCustomField.container.setWidth(COLUMN_USER_WIDTH - 10);

				//LabelField lf = new LabelField();
				//LabelField lf2 = new LabelField();
				//lf.setText("pippo");
				//lf2.setText("pippo2");
   			    //usersCustomField.panel.add(lf);
				//usersCustomField.panel.add(lf2);
				//usersCustomField.panel.hide();
				
				if (model.getUser() != null) {
					usersCustomField.first.setValue(model.getUser().getFullName());
				}
				
				usersCustomField.addListener(Events.OnClick,
						new Listener<FieldEvent>() {

							public void handleEvent(FieldEvent be) {
								((CustomField) be.getComponent()).h.show();
								/*Dispatcher.forwardEvent(
										GeoRepoEvents.SEND_INFO_MESSAGE,
										new String[] {
												"GeoServer Rules",
												"Rule " + model.getPriority()
														+ ": User changed" });

								model.setUser((GSUser) be.getField().getValue());
								Dispatcher.forwardEvent(
										GeoRepoEvents.RULE_UPDATE_GRID_COMBO,
										model);*/
							}
						});

				return usersCustomField;
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
						gsUsersService.getGsUsers(
								(PagingLoadConfig) loadConfig, true, callback);
					}

				};
				BasePagingLoader<PagingLoadResult<ModelData>> usersLoader = new BasePagingLoader<PagingLoadResult<ModelData>>(
						userProxy);
				usersLoader.setRemoteSort(false);
				ListStore<GSUser> availableUsers = new ListStore<GSUser>(
						usersLoader);

				return availableUsers;
			}
		};

		return comboRendered;
	}

	/**
	 * Creates the profiles combo box.
	 * 
	 * @return the grid cell renderer
	 */
	private GridCellRenderer<Rule> createProfilesComboBox() {
		GridCellRenderer<Rule> comboRendered = new GridCellRenderer<Rule>() {

			private boolean init;

			public Object render(final Rule model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<Rule> store, Grid<Rule> grid) {

				if (!init) {
					init = true;
					grid.addListener(Events.ColumnResize,
							new Listener<GridEvent<Rule>>() {

								public void handleEvent(GridEvent<Rule> be) {
									for (int i = 0; i < be.getGrid().getStore()
											.getCount(); i++) {
										if (be.getGrid().getView()
												.getWidget(i, be.getColIndex()) != null
												&& be.getGrid()
														.getView()
														.getWidget(
																i,
																be.getColIndex()) instanceof BoxComponent) {
											((BoxComponent) be
													.getGrid()
													.getView()
													.getWidget(i,
															be.getColIndex()))
													.setWidth(be.getWidth() - 10);
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
					profilesComboBox.setSelection(Arrays.asList(model
							.getProfile()));
				}

				profilesComboBox.addListener(Events.Select,
						new Listener<FieldEvent>() {

							public void handleEvent(FieldEvent be) {
								Dispatcher
										.forwardEvent(
												GeoRepoEvents.SEND_INFO_MESSAGE,
												new String[] {
														"GeoServer Rules",
														"Rule "
																+ model.getPriority()
																+ ": Profile changed" });

								model.setProfile((Profile) be.getField()
										.getValue());
								Dispatcher.forwardEvent(
										GeoRepoEvents.RULE_UPDATE_GRID_COMBO,
										model);
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
						profilesService.getProfiles(
								(PagingLoadConfig) loadConfig, true, callback);
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

			public Object render(final Rule model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<Rule> store, Grid<Rule> grid) {

				if (!init) {
					init = true;
					grid.addListener(Events.ColumnResize,
							new Listener<GridEvent<Rule>>() {

								public void handleEvent(GridEvent<Rule> be) {
									for (int i = 0; i < be.getGrid().getStore()
											.getCount(); i++) {
										if (be.getGrid().getView()
												.getWidget(i, be.getColIndex()) != null
												&& be.getGrid()
														.getView()
														.getWidget(
																i,
																be.getColIndex()) instanceof BoxComponent) {
											((BoxComponent) be
													.getGrid()
													.getView()
													.getWidget(i,
															be.getColIndex()))
													.setWidth(be.getWidth() - 10);
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
					instancesComboBox.setSelection(Arrays.asList(model
							.getInstance()));
				} else {
					GSInstance all = new GSInstance();
					all.setId(-1);
					all.setName("*");
					all.setBaseURL("*");
					instancesComboBox.setValue(all);
					instancesComboBox.setSelection(Arrays.asList(all));
				}

				instancesComboBox.addListener(Events.Select,
						new Listener<FieldEvent>() {

							public void handleEvent(FieldEvent be) {
								final GSInstance instance = (GSInstance) be
										.getField().getValue();
								Dispatcher
										.forwardEvent(
												GeoRepoEvents.SEND_INFO_MESSAGE,
												new String[] {
														"GeoServer Rules",
														"Rule "
																+ model.getPriority()
																+ ": Instance changed" });

								model.setInstance(instance);
								model.setService("*");
								model.setRequest("*");
								model.setWorkspace("*");
								model.setLayer("*");
								Dispatcher.forwardEvent(
										GeoRepoEvents.RULE_UPDATE_GRID_COMBO,
										model);
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
						instancesService.getInstances(
								(PagingLoadConfig) loadConfig, true, callback);
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

			public Object render(final Rule model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<Rule> store, Grid<Rule> grid) {

				if (!init) {
					init = true;
					grid.addListener(Events.ColumnResize,
							new Listener<GridEvent<Rule>>() {

								public void handleEvent(GridEvent<Rule> be) {
									for (int i = 0; i < be.getGrid().getStore()
											.getCount(); i++) {
										if (be.getGrid().getView()
												.getWidget(i, be.getColIndex()) != null
												&& be.getGrid()
														.getView()
														.getWidget(
																i,
																be.getColIndex()) instanceof BoxComponent) {
											((BoxComponent) be
													.getGrid()
													.getView()
													.getWidget(i,
															be.getColIndex()))
													.setWidth(be.getWidth() - 10);
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
				servicesComboBox.setDisplayField(BeanKeyValue.SERVICE
						.getValue());
				servicesComboBox.setStore(getAvailableServices(model
						.getInstance()));
				servicesComboBox.setEditable(true);
				servicesComboBox.setTypeAhead(true);
				servicesComboBox.setTriggerAction(TriggerAction.ALL);
				servicesComboBox.setWidth(90);

				KeyListener keyListener = new KeyListener() {

					@Override
					public void componentKeyUp(ComponentEvent event) {
						if (event.getKeyCode() == '\r') {
							event.cancelBubble();

							Dispatcher.forwardEvent(
									GeoRepoEvents.SEND_INFO_MESSAGE,
									new String[] {
											"GeoServer Rules",
											"Rule "
													+ model.getPriority()
													+ ": Service changed -> "
													+ servicesComboBox
															.getRawValue() });

							model.setService(servicesComboBox.getRawValue());
							model.setRequest("*");
							Dispatcher
									.forwardEvent(
											GeoRepoEvents.RULE_UPDATE_GRID_COMBO,
											model);
						}
					}
				};

				servicesComboBox.addKeyListener(keyListener);

				if (model.getService() != null) {
					servicesComboBox.setValue(new Service(model.getService()));
					servicesComboBox.setSelection(Arrays.asList(new Service(
							model.getService())));
				}

				servicesComboBox.addListener(Events.Select,
						new Listener<FieldEvent>() {

							public void handleEvent(FieldEvent be) {
								final Service service = (Service) be.getField()
										.getValue();
								Dispatcher
										.forwardEvent(
												GeoRepoEvents.SEND_INFO_MESSAGE,
												new String[] {
														"GeoServer Rules",
														"Rule "
																+ model.getPriority()
																+ ": Service changed -> "
																+ service
																		.getService() });

								model.setService(service.getService());
								model.setRequest("*");
								Dispatcher.forwardEvent(
										GeoRepoEvents.RULE_UPDATE_GRID_COMBO,
										model);
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
			private ListStore<Service> getAvailableServices(
					GSInstance gsInstance) {
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

			public Object render(final Rule model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<Rule> store, Grid<Rule> grid) {

				if (!init) {
					init = true;
					grid.addListener(Events.ColumnResize,
							new Listener<GridEvent<Rule>>() {

								public void handleEvent(GridEvent<Rule> be) {
									for (int i = 0; i < be.getGrid().getStore()
											.getCount(); i++) {
										if (be.getGrid().getView()
												.getWidget(i, be.getColIndex()) != null
												&& be.getGrid()
														.getView()
														.getWidget(
																i,
																be.getColIndex()) instanceof BoxComponent) {
											((BoxComponent) be
													.getGrid()
													.getView()
													.getWidget(i,
															be.getColIndex()))
													.setWidth(be.getWidth() - 10);
										}
									}
								}
							});
				}

				// TODO: generalize this!
				final ComboBox<Request> serviceRequestsComboBox = new ComboBox<Request>();
				serviceRequestsComboBox.setId("ruleServicesRequestCombo");
				serviceRequestsComboBox.setName("ruleServicesRequestCombo");
				serviceRequestsComboBox
						.setEmptyText("(No service request available)");
				serviceRequestsComboBox.setDisplayField(BeanKeyValue.REQUEST
						.getValue());
				serviceRequestsComboBox.setStore(getAvailableServicesRequest(
						model.getInstance(), model.getService()));
				serviceRequestsComboBox.setEditable(true);
				serviceRequestsComboBox.setTypeAhead(true);
				serviceRequestsComboBox.setTriggerAction(TriggerAction.ALL);
				serviceRequestsComboBox.setWidth(180);

				KeyListener keyListener = new KeyListener() {

					@Override
					public void componentKeyUp(ComponentEvent event) {
						if (event.getKeyCode() == '\r') {
							event.cancelBubble();

							Dispatcher.forwardEvent(
									GeoRepoEvents.SEND_INFO_MESSAGE,
									new String[] {
											"GeoServer Rules",
											"Rule "
													+ model.getPriority()
													+ ": Request changed -> "
													+ serviceRequestsComboBox
															.getRawValue() });

							model.setRequest(serviceRequestsComboBox
									.getRawValue());
							Dispatcher
									.forwardEvent(
											GeoRepoEvents.RULE_UPDATE_GRID_COMBO,
											model);
						}
					}
				};

				serviceRequestsComboBox.addKeyListener(keyListener);

				if (model.getService() != null) {
					serviceRequestsComboBox.setValue(new Request(model
							.getRequest()));
					serviceRequestsComboBox.setSelection(Arrays
							.asList(new Request(model.getRequest())));
				}

				serviceRequestsComboBox.addListener(Events.Select,
						new Listener<FieldEvent>() {

							public void handleEvent(FieldEvent be) {
								final Request request = (Request) be.getField()
										.getValue();
								Dispatcher
										.forwardEvent(
												GeoRepoEvents.SEND_INFO_MESSAGE,
												new String[] {
														"GeoServer Rules",
														"Rule "
																+ model.getPriority()
																+ ": Request changed -> "
																+ request
																		.getRequest() });

								model.setRequest(request.getRequest());
								Dispatcher.forwardEvent(
										GeoRepoEvents.RULE_UPDATE_GRID_COMBO,
										model);
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
			private ListStore<Request> getAvailableServicesRequest(
					GSInstance gsInstance, String service) {
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
					Request describeFeatureType = new Request(
							"DescribeFeatureType");
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

			public Object render(final Rule model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<Rule> store, Grid<Rule> grid) {

				if (!init) {
					init = true;
					grid.addListener(Events.ColumnResize,
							new Listener<GridEvent<Rule>>() {

								public void handleEvent(GridEvent<Rule> be) {
									for (int i = 0; i < be.getGrid().getStore()
											.getCount(); i++) {
										if (be.getGrid().getView()
												.getWidget(i, be.getColIndex()) != null
												&& be.getGrid()
														.getView()
														.getWidget(
																i,
																be.getColIndex()) instanceof BoxComponent) {
											((BoxComponent) be
													.getGrid()
													.getView()
													.getWidget(i,
															be.getColIndex()))
													.setWidth(be.getWidth() - 10);
										}
									}
								}
							});
				}

				// TODO: generalize this!
				ComboBox<Workspace> workspacesComboBox = new ComboBox<Workspace>();
				workspacesComboBox.setId("ruleWorkspacesCombo");
				workspacesComboBox.setName("ruleWorkspacesCombo");

				workspacesComboBox.setDisplayField(BeanKeyValue.WORKSPACE
						.getValue());
				workspacesComboBox.setEditable(false);
				workspacesComboBox.setStore(getAvailableWorkspaces(model
						.getInstance()));
				workspacesComboBox.setTypeAhead(true);
				workspacesComboBox.setTriggerAction(TriggerAction.ALL);
				workspacesComboBox.setWidth(120);

				if (model.getWorkspace() != null) {
					workspacesComboBox.setValue(new Workspace(model
							.getWorkspace()));
					workspacesComboBox.setSelection(Arrays
							.asList(new Workspace(model.getWorkspace())));
				}
				workspacesComboBox.setEmptyText("(No workspace available)");
				workspacesComboBox.addListener(Events.Select,
						new Listener<FieldEvent>() {

							public void handleEvent(FieldEvent be) {
								final Workspace workspace = (Workspace) be
										.getField().getValue();
								Dispatcher
										.forwardEvent(
												GeoRepoEvents.SEND_INFO_MESSAGE,
												new String[] {
														"GeoServer Workspaces",
														"Rule "
																+ model.getPriority()
																+ ": Workspace changed" });

								model.setWorkspace(workspace.getWorkspace());
								model.setLayer("*");
								Dispatcher.forwardEvent(
										GeoRepoEvents.RULE_UPDATE_GRID_COMBO,
										model);
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
			private ListStore<Workspace> getAvailableWorkspaces(
					final GSInstance gsInstance) {
				RpcProxy<PagingLoadResult<Workspace>> workspacesProxy = new RpcProxy<PagingLoadResult<Workspace>>() {

					@Override
					protected void load(Object loadConfig,
							AsyncCallback<PagingLoadResult<Workspace>> callback) {
						workspacesService.getWorkspaces(
								(PagingLoadConfig) loadConfig,
								gsInstance != null ? gsInstance.getBaseURL()
										: null, gsInstance, callback);
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

			public Object render(final Rule model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<Rule> store, Grid<Rule> grid) {

				if (!init) {
					init = true;
					grid.addListener(Events.ColumnResize,
							new Listener<GridEvent<Rule>>() {

								public void handleEvent(GridEvent<Rule> be) {
									for (int i = 0; i < be.getGrid().getStore()
											.getCount(); i++) {
										if (be.getGrid().getView()
												.getWidget(i, be.getColIndex()) != null
												&& be.getGrid()
														.getView()
														.getWidget(
																i,
																be.getColIndex()) instanceof BoxComponent) {
											((BoxComponent) be
													.getGrid()
													.getView()
													.getWidget(i,
															be.getColIndex()))
													.setWidth(be.getWidth() - 10);
										}
									}
								}
							});
				}

				// TODO: generalize this!
				ComboBox<Layer> workspaceLayersComboBox = new ComboBox<Layer>();
				workspaceLayersComboBox.setId("ruleLayersCombo");
				workspaceLayersComboBox.setName("ruleLayersCombo");

				workspaceLayersComboBox.setDisplayField(BeanKeyValue.LAYER
						.getValue());
				workspaceLayersComboBox.setEditable(false);
				workspaceLayersComboBox.setStore(getAvailableLayers(
						model.getInstance(), model.getWorkspace()));
				workspaceLayersComboBox.setTypeAhead(true);
				workspaceLayersComboBox.setTriggerAction(TriggerAction.ALL);
				workspaceLayersComboBox.setWidth(120);

				if (model.getLayer() != null) {
					workspaceLayersComboBox
							.setValue(new Layer(model.getLayer()));
					workspaceLayersComboBox.setSelection(Arrays
							.asList(new Layer(model.getLayer())));
				}
				workspaceLayersComboBox.setEmptyText("(No layer available)");
				workspaceLayersComboBox.addListener(Events.Select,
						new Listener<FieldEvent>() {

							public void handleEvent(FieldEvent be) {
								final Layer layer = (Layer) be.getField()
										.getValue();
								Dispatcher.forwardEvent(
										GeoRepoEvents.SEND_INFO_MESSAGE,
										new String[] {
												"GeoServer Layers",
												"Rule " + model.getPriority()
														+ ": Layers changed" });

								model.setLayer(layer.getLayer());
								Dispatcher.forwardEvent(
										GeoRepoEvents.RULE_UPDATE_GRID_COMBO,
										model);
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
			private ListStore<Layer> getAvailableLayers(
					final GSInstance gsInstance, final String workspace) {
				RpcProxy<PagingLoadResult<Layer>> workspacesProxy = new RpcProxy<PagingLoadResult<Layer>>() {

					@Override
					protected void load(Object loadConfig,
							AsyncCallback<PagingLoadResult<Layer>> callback) {
						workspacesService.getLayers(
								(PagingLoadConfig) loadConfig,
								gsInstance != null ? gsInstance.getBaseURL()
										: null, workspace, callback);
					}

				};
				BasePagingLoader<PagingLoadResult<ModelData>> workspacesLoader = new BasePagingLoader<PagingLoadResult<ModelData>>(
						workspacesProxy);
				workspacesLoader.setRemoteSort(false);
				ListStore<Layer> availableWorkspaceLayers = new ListStore<Layer>(
						workspacesLoader);

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

			public Object render(final Rule model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<Rule> store, Grid<Rule> grid) {

				if (!init) {
					init = true;
					grid.addListener(Events.ColumnResize,
							new Listener<GridEvent<Rule>>() {

								public void handleEvent(GridEvent<Rule> be) {
									for (int i = 0; i < be.getGrid().getStore()
											.getCount(); i++) {
										if (be.getGrid().getView()
												.getWidget(i, be.getColIndex()) != null
												&& be.getGrid()
														.getView()
														.getWidget(
																i,
																be.getColIndex()) instanceof BoxComponent) {
											((BoxComponent) be
													.getGrid()
													.getView()
													.getWidget(i,
															be.getColIndex()))
													.setWidth(be.getWidth() - 10);
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
					grantsComboBox.setSelection(Arrays.asList(new Grant(model
							.getGrant())));
				}
				grantsComboBox.setEmptyText("(No grant types available)");
				grantsComboBox.addListener(Events.Select,
						new Listener<FieldEvent>() {

							public void handleEvent(FieldEvent be) {
								final Grant grant = (Grant) be.getField()
										.getValue();
								Dispatcher.forwardEvent(
										GeoRepoEvents.SEND_INFO_MESSAGE,
										new String[] {
												"GeoServer Rules",
												"Rule " + model.getPriority()
														+ ": Grant changed" });

								model.setGrant(grant.getGrant());
								Dispatcher.forwardEvent(
										GeoRepoEvents.RULE_UPDATE_GRID_COMBO,
										model);
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

	/**
	 * Creates the rule delete button.
	 * 
	 * @return the grid cell renderer
	 */
	private GridCellRenderer<Rule> createRuleDeleteButton() {
		GridCellRenderer<Rule> buttonRendered = new GridCellRenderer<Rule>() {

			private boolean init;

			public Object render(final Rule model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<Rule> store, Grid<Rule> grid) {

				if (!init) {
					init = true;
					grid.addListener(Events.ColumnResize,
							new Listener<GridEvent<Rule>>() {

								public void handleEvent(GridEvent<Rule> be) {
									for (int i = 0; i < be.getGrid().getStore()
											.getCount(); i++) {
										if (be.getGrid().getView()
												.getWidget(i, be.getColIndex()) != null
												&& be.getGrid()
														.getView()
														.getWidget(
																i,
																be.getColIndex()) instanceof BoxComponent) {
											((BoxComponent) be
													.getGrid()
													.getView()
													.getWidget(i,
															be.getColIndex()))
													.setWidth(be.getWidth() - 10);
										}
									}
								}
							});
				}

				// TODO: generalize this!
				Button removeRuleButton = new Button("Remove");
				removeRuleButton.setIcon(Resources.ICONS.delete());
				// TODO: add correct tooltip text here!
				// removeUserButton.setToolTip("...");
				removeRuleButton.setEnabled(true);

				removeRuleButton.addListener(Events.OnClick,
						new Listener<ButtonEvent>() {

							public void handleEvent(ButtonEvent be) {
								Dispatcher
										.forwardEvent(
												GeoRepoEvents.SEND_INFO_MESSAGE,
												new String[] {
														"GeoServer Rules",
														"Remove Rule #"
																+ model.getPriority() });

								Dispatcher.forwardEvent(GeoRepoEvents.RULE_DEL,
										model);
							}
						});

				return removeRuleButton;
			}

		};

		return buttonRendered;
	}

	/**
	 * Creates the rule details button.
	 * 
	 * @return the grid cell renderer
	 */
	private GridCellRenderer<Rule> createRuleDetailsButton() {
		GridCellRenderer<Rule> buttonRendered = new GridCellRenderer<Rule>() {

			private boolean init;

			public Object render(final Rule model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<Rule> store, Grid<Rule> grid) {

				if (!init) {
					init = true;
					grid.addListener(Events.ColumnResize,
							new Listener<GridEvent<Rule>>() {

								public void handleEvent(GridEvent<Rule> be) {
									for (int i = 0; i < be.getGrid().getStore()
											.getCount(); i++) {
										if (be.getGrid().getView()
												.getWidget(i, be.getColIndex()) != null
												&& be.getGrid()
														.getView()
														.getWidget(
																i,
																be.getColIndex()) instanceof BoxComponent) {
											((BoxComponent) be
													.getGrid()
													.getView()
													.getWidget(i,
															be.getColIndex()))
													.setWidth(be.getWidth() - 10);
										}
									}
								}
							});
				}

				// TODO: generalize this!
				Button ruleDetailsButton = new Button("Details");
				ruleDetailsButton.setIcon(Resources.ICONS.table());
				// TODO: add correct tooltip text here!
				// userDetailsButton.setToolTip("...");
				ruleDetailsButton.setEnabled(true);

				ruleDetailsButton.addListener(Events.OnClick,
						new Listener<ButtonEvent>() {

							public void handleEvent(ButtonEvent be) {
								// if (model.getId() < 0) {
								// Dispatcher.forwardEvent(GeoRepoEvents.SEND_ALERT_MESSAGE,
								// new String[] {
								// "GeoServer Rules",
								// "Please apply changes before editing details!"
								// });
								// } else {
								Dispatcher.forwardEvent(
										GeoRepoEvents.EDIT_RULE_DETAILS, model);
								// }

							}
						});

				return ruleDetailsButton;
			}

		};

		return buttonRendered;
	}

	/**
	 * Creates the rule add button.
	 * 
	 * @return the grid cell renderer
	 */
	private GridCellRenderer<Rule> createRuleAddButton() {
		GridCellRenderer<Rule> buttonRendered = new GridCellRenderer<Rule>() {

			private boolean init;

			public Object render(final Rule model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<Rule> store, Grid<Rule> grid) {

				if (!init) {
					init = true;
					grid.addListener(Events.ColumnResize,
							new Listener<GridEvent<Rule>>() {

								public void handleEvent(GridEvent<Rule> be) {
									for (int i = 0; i < be.getGrid().getStore()
											.getCount(); i++) {
										if (be.getGrid().getView()
												.getWidget(i, be.getColIndex()) != null
												&& be.getGrid()
														.getView()
														.getWidget(
																i,
																be.getColIndex()) instanceof BoxComponent) {
											((BoxComponent) be
													.getGrid()
													.getView()
													.getWidget(i,
															be.getColIndex()))
													.setWidth(be.getWidth() - 10);
										}
									}
								}
							});
				}

				// TODO: generalize this!
				Button ruleAddButton = new Button();
				ruleAddButton.setBorders(false);
				ruleAddButton.setIcon(Resources.ICONS.add());
				// TODO: add correct tooltip text here!
				// userDetailsButton.setToolTip("...");
				ruleAddButton.setEnabled(true);

				ruleAddButton.addListener(Events.OnClick,
						new Listener<ButtonEvent>() {

							public void handleEvent(ButtonEvent be) {
								Dispatcher
										.forwardEvent(
												GeoRepoEvents.SEND_INFO_MESSAGE,
												new String[] {
														"GeoServer Rules",
														"Selected Rule #"
																+ model.getPriority() });

								Dispatcher.forwardEvent(GeoRepoEvents.RULE_ADD,
										model);
							}
						});

				return ruleAddButton;
			}

		};

		return buttonRendered;
	}

	/**
	 * Creates the rule priority up button.
	 * 
	 * @return the grid cell renderer
	 */
	private GridCellRenderer<Rule> createRulePriorityUpButton() {
		GridCellRenderer<Rule> buttonRendered = new GridCellRenderer<Rule>() {

			private boolean init;

			public Object render(final Rule model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<Rule> store, Grid<Rule> grid) {

				if (!init) {
					init = true;
					grid.addListener(Events.ColumnResize,
							new Listener<GridEvent<Rule>>() {

								public void handleEvent(GridEvent<Rule> be) {
									for (int i = 0; i < be.getGrid().getStore()
											.getCount(); i++) {
										if (be.getGrid().getView()
												.getWidget(i, be.getColIndex()) != null
												&& be.getGrid()
														.getView()
														.getWidget(
																i,
																be.getColIndex()) instanceof BoxComponent) {
											((BoxComponent) be
													.getGrid()
													.getView()
													.getWidget(i,
															be.getColIndex()))
													.setWidth(be.getWidth() - 10);
										}
									}
								}
							});
				}

				// TODO: generalize this!
				Button priorityUpButton = new Button();
				priorityUpButton.setBorders(false);
				priorityUpButton.setIcon(Resources.ICONS.arrowUp());
				// TODO: add correct tooltip text here!
				// userDetailsButton.setToolTip("...");
				priorityUpButton.setEnabled(true);

				priorityUpButton.addListener(Events.OnClick,
						new Listener<ButtonEvent>() {

							public void handleEvent(ButtonEvent be) {
								Dispatcher
										.forwardEvent(
												GeoRepoEvents.SEND_INFO_MESSAGE,
												new String[] {
														"GeoServer Rules",
														"Selected Rule #"
																+ model.getPriority() });

								Dispatcher.forwardEvent(
										GeoRepoEvents.RULE_PRIORITY_UP, model);
							}
						});

				return priorityUpButton;
			}

		};

		return buttonRendered;
	}

	/**
	 * Creates the rule priority down button.
	 * 
	 * @return the grid cell renderer
	 */
	private GridCellRenderer<Rule> createRulePriorityDownButton() {
		GridCellRenderer<Rule> buttonRendered = new GridCellRenderer<Rule>() {

			private boolean init;

			public Object render(final Rule model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<Rule> store, Grid<Rule> grid) {

				if (!init) {
					init = true;
					grid.addListener(Events.ColumnResize,
							new Listener<GridEvent<Rule>>() {

								public void handleEvent(GridEvent<Rule> be) {
									for (int i = 0; i < be.getGrid().getStore()
											.getCount(); i++) {
										if (be.getGrid().getView()
												.getWidget(i, be.getColIndex()) != null
												&& be.getGrid()
														.getView()
														.getWidget(
																i,
																be.getColIndex()) instanceof BoxComponent) {
											((BoxComponent) be
													.getGrid()
													.getView()
													.getWidget(i,
															be.getColIndex()))
													.setWidth(be.getWidth() - 10);
										}
									}
								}
							});
				}

				// TODO: generalize this!
				Button priorityDownButton = new Button();
				priorityDownButton.setBorders(false);
				priorityDownButton.setIcon(Resources.ICONS.arrowDown());
				// TODO: add correct tooltip text here!
				// userDetailsButton.setToolTip("...");
				priorityDownButton.setEnabled(true);

				priorityDownButton.addListener(Events.OnClick,
						new Listener<ButtonEvent>() {

							public void handleEvent(ButtonEvent be) {
								Dispatcher
										.forwardEvent(
												GeoRepoEvents.SEND_INFO_MESSAGE,
												new String[] {
														"GeoServer Rules",
														"Selected Rule #"
																+ model.getPriority() });

								Dispatcher
										.forwardEvent(
												GeoRepoEvents.RULE_PRIORITY_DOWN,
												model);
							}
						});

				return priorityDownButton;
			}

		};

		return buttonRendered;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#createStore()
	 */
	@Override
	public void createStore() {
		this.toolBar = new PagingToolBar(
				it.geosolutions.georepo.gui.client.Constants.DEFAULT_PAGESIZE);

		// Loader fro rulesService
		this.proxy = new RpcProxy<PagingLoadResult<Rule>>() {

			@Override
			protected void load(Object loadConfig,
					AsyncCallback<PagingLoadResult<Rule>> callback) {
				rulesService.getRules((PagingLoadConfig) loadConfig, false,
						callback);
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
			protected boolean doSelect(Store<Rule> store, Rule parent,
					Rule record, String property, String filter) {

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
				Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
						new String[] { "GeoServer Rules", "Add Rule" });

				Rule new_rule = new Rule();
				new_rule.setId(-1);
				new_rule.setPriority(-1);
				Dispatcher.forwardEvent(GeoRepoEvents.RULE_ADD, new_rule);

			}
		});
		/*
		 * ric mod 20100217 Button saveRulesButton = new
		 * Button("Apply Changes");
		 * saveRulesButton.setIcon(Resources.ICONS.save());
		 * 
		 * saveRulesButton.addListener(Events.OnClick, new
		 * Listener<ButtonEvent>() {
		 * 
		 * public void handleEvent(ButtonEvent be) {
		 * Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[]
		 * { "GeoServer Rules", "Apply Changes" });
		 * 
		 * Dispatcher.forwardEvent(GeoRepoEvents.RULE_APPLY_CHANGES_GRID_COMBO);
		 * } });
		 */
		this.toolBar.bind(loader);
		this.toolBar.add(new SeparatorToolItem());
		this.toolBar.add(addRuleButton);
		// this.toolBar.add(saveRulesButton);<<-- ric mod 20100217
		this.toolBar.add(new SeparatorToolItem());
		this.toolBar.add(filter);
		this.toolBar.add(new SeparatorToolItem());
		// this.toolBar.setStyleAttribute("height", "100%");//<<-- ric add
		// 20100216
		// this.toolBar.setStyleAttribute("top", "95%");//<<-- ric add 20100217
		// this.toolBar.setStyleAttribute("position", "absolute");//<<-- ric add
		// 20100217
		// this.toolBar.disable();
		// this.toolBar.setStyleAttribute("vertical-align", "bottom");//ric add
		// 20100216
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
						message = I18nProvider.getMessages().recordLabel();
					else
						message = I18nProvider.getMessages()
								.recordPluralLabel();
					Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
							new String[] {
									I18nProvider.getMessages()
											.remoteServiceName(),
									I18nProvider.getMessages().foundLabel()
											+ " " + result.getData().size()
											+ " " + message });
				} else {
					Dispatcher.forwardEvent(GeoRepoEvents.SEND_ALERT_MESSAGE,
							new String[] {
									I18nProvider.getMessages()
											.remoteServiceName(),
									I18nProvider.getMessages()
											.recordNotFoundMessage() });
				}
			}

		});
	}
	
	private static final class CustomField<T> extends TextField<T> {

		private final LayoutContainer container = new LayoutContainer();
		//private final Dialog container = new Dialog();
		private final TextField<String> first = new TextField<String>();
		// private final ListField second = new ListField();
		//private TextArea panel = new TextArea();
		//private ContentPanel panel = new ContentPanel();
		private Html h = new Html("<div  class='x-combo-list-item'>*</div>");
		//ListField lfield = new ListField();
		

		private CustomField(List list) {//ListField users
        	container.setLayout(new RowLayout());
        	
            //panel.setZIndex(20000000);
            //panel.hide();
            //panel.setWidth(120);
            //LabelField lf = new LabelField();
            
            //LabelField lf2 = new LabelField();
            //lf.setText("pippo");
            //lf.setStyleAttribute("position", "absolute");
            
        	//lf2.setText("pippo2");
            //lf2.setStyleAttribute("position", "absolute");
        	String toHtml = "<div  style='position:absolute;z-index:200000; background-color:#fff'>";
            Iterator itr = list.iterator();
            while(itr.hasNext()){
            	Rule r = (Rule)itr.next();
            	
            
            	 /*LabelField lf = new LabelField();
            	 lf.setText(name);
                 lf.setStyleAttribute("position", "absolute");
                 lf.addListener(Events.OnClick, new Listener<FieldEvent>(){
                 	public void handleEvent(FieldEvent be) {
                        System.out.println("lf di '"+be.getField().getValue()+"' clecked");
                    }
                    
                });*/
            	 String name = r.getUser()!=null?r.getUser().getName()!=null?r.getUser().getName():"*":"*";
            	toHtml+="<div onClick='alert(clikkato "+name+")'>"+name+"</div>";
            	
                 //panel.setData(key, data);
            	//panel.add(lf);
            	//lfield.setData(name, name);
            	
            }
            toHtml+="<img class='x-form-trigger x-form-trigger-arrow' src='data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==' id='x-auto-207'>" +
    		        "</div>";
        	h = new Html(toHtml);
            /*h = new Html("<div  style='position:absolute;z-index:200000; background-color:#fff'>" +
            		"<div>"+lf.getText()+"</div>"+
            		   "<div>"+lf2.getText()+"</div>" +
            		"<img class='x-form-trigger x-form-trigger-arrow' src='data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==' id='x-auto-207'>" +
            		"</div>");
            panel.add(lf);
            panel.add(lf2);*/

            //panel.add(h);
            h.setWidth(COLUMN_USER_WIDTH);
            //.setStyleAttribute(attr, value);
            //container.add(h);
            //container.add(panel);
           
            h.setStyleAttribute("position", "absolute");
            container.add(first);//, new RowData(1, -1, new Margins(3)));
            container.add(h);
            container.setStyleAttribute("position", "absolute");
            h.hide();
            first.show();
            /*lf.addListener(Events.OnClick, new Listener<FieldEvent>(){
            	public void handleEvent(FieldEvent be) {
                    System.out.println("lf clecked");
                }
                
            });
            lf2.addListener(Events.OnClick, new Listener<FieldEvent>(){
            	public void handleEvent(FieldEvent be) {
                    System.out.println("lf clecked");
                }
                
            });*/
           
            first.addListener(Events.CellClick, new Listener<FieldEvent>(){

               
				public void handleEvent(FieldEvent be) {
                	System.out.println("first clicked");
                }
                
            });
            first.addListener(Events.OnClick, new Listener<FieldEvent>(){

                
				public void handleEvent(FieldEvent be) {
                	System.out.println("first OnClick");
                	if(!h.isVisible()){
                		container.show();
                		h.show();
                	}else{
                		container.hide();
                		h.hide();
                	}
                	
                	System.out.println("first OnClick end");
                }
                
            });
            h.addListener(Events.OnClick, new Listener<FieldEvent>(){

                
				public void handleEvent(FieldEvent be) {
                	System.out.println("first OnClick");
            		container.hide();
            		h.hide();
                	System.out.println("first OnClick end");
                }
                
            });
            /*lfield.addListener(Events.OnClick, new Listener<FieldEvent>(){

                
				public void handleEvent(FieldEvent be) {
                	System.out.println("lfield OnClick");
                	lfield.show();
                	System.out.println("lfield OnClick end");
                }
            });
            h.addListener(Events.OnClick, new Listener<FieldEvent>(){

                
				public void handleEvent(FieldEvent be) {
                	System.out.println("h OnClick");
                	h.show();
                	System.out.println("h OnClick end");
                }
            });
            container.addListener(Events.OnClick, new Listener<FieldEvent>(){

                
				public void handleEvent(FieldEvent be) {
                	System.out.println("panel OnClick");
                	container.show();
                	panel.show();
                	System.out.println("panel OnClick end");
                }
            });*/
            
        }

		@Override
		protected void onRender(Element parent, int index) {
			// first.setWidth(15);
			// second.setWidth(15);
			container.setLayout(new RowLayout());
			// panel.setZIndex(20000000);//non qui, dopo il render
			//container.hide();
			container.setWidth(120);
			LabelField lf = new LabelField();
			LabelField lf2 = new LabelField();
			lf.setText("pippo");
			lf2.setText("pippo2");
			// panel.add(lf);
			// panel.add(lf2);
			// panel.add(h);
			container.add(first);
			//first.show();
			container.add(h);
			// , new RowData(1, -1, new Margins(3)));
			// container.add(second, new RowData(1, -1, new Margins(3)));
			//panel.render(parent, index);
			setElement(first.getElement());
			// panel.hide();
		}

		@Override
		protected void doAttachChildren() {
			ComponentHelper.doAttach(container);
		}

		@Override
		protected void doDetachChildren() {
			ComponentHelper.doDetach(container);
		}

		@Override
		protected void onDisable() {
			super.onDisable();
			container.disable();
		}

		@Override
		protected void onEnable() {
			super.onEnable();
			container.enable();
		}

	}

}