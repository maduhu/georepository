/*
 * $Header: it.geosolutions.georepo.gui.client.widget.SearchPagUserWidget,v. 0.1 30/lug/2010 09.59.29 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 30/lug/2010 09.59.29 $
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
package it.geosolutions.georepo.gui.client.widget;


import java.util.ArrayList;
import java.util.List;

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.model.BeanKeyValue;
import it.geosolutions.georepo.gui.client.model.User;
import it.geosolutions.georepo.gui.client.service.LoginRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.DGWATCHSearchWidget;
import it.geosolutions.georepo.gui.client.widget.SearchStatus.EnumSearchStatus;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.LoadListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.event.WindowListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author giuseppe
 * 
 */
public class SearchPagUserWidget extends DGWATCHSearchWidget<User> {
	
	private LoginRemoteAsync service;
	
	public SearchPagUserWidget(LoginRemoteAsync service) {
		super();
		this.service = service;
	}
	
	@Override
	public void setWindowProperties() {
		setHeading("Search for Registered Member");
		super.setSize(420, 490);
		
		super.addWindowListener(new WindowListener() {
			
			@Override
            public void windowShow(WindowEvent we) {
				searchText = "";
				loader.load(0, 25);
			}
			
		});
	}
	
	@Override
	public void createStore() {
		toolBar = new PagingToolBar(25);
		
		this.proxy = new RpcProxy<PagingLoadResult<User>>() {
			
			@Override
			protected void load(Object loadConfig,
								AsyncCallback<PagingLoadResult<User>> callback) {

                // TODO REFACTOR GG
//				service.loadUsers((PagingLoadConfig) loadConfig, searchText,
//								  callback);
			}
		};
		
		loader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);
		loader.setRemoteSort(false);
		
		store = new ListStore<User>(loader);
		
		this.toolBar.bind(loader);
		//		toolBar.disable();
		
		setUpLoadListener();
	}
	
	@Override
	public void setGridProperties() {
		grid.setAutoExpandColumn(BeanKeyValue.USER_NAME.getValue());
		
		grid.setWidth(350);
		grid.setHeight(270);
	}
	
	@Override
	public ColumnModel prepareColumnModel() {
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
		
		ColumnConfig userNameColumn = new ColumnConfig();
		userNameColumn.setId(BeanKeyValue.USER_NAME.getValue());
		userNameColumn.setHeader("User Name");
		userNameColumn.setWidth(80);
		configs.add(userNameColumn);
		
		ColumnConfig emailAddress = new ColumnConfig();
		emailAddress.setId(BeanKeyValue.EMAIL.getValue());
		emailAddress.setHeader("Email");
		emailAddress.setWidth(120);
		configs.add(emailAddress);
		
		return new ColumnModel(configs);
	}
	
	@Override
	public void select() {
		searchStatus.setBusy("Get User Details....");
		Dispatcher.forwardEvent(DGWATCHEvents.BIND_SELECTED_USER, grid
								.getSelectionModel().getSelectedItem());
	}
	
	private void setUpLoadListener() {
		loader.addLoadListener(new LoadListener() {
			
			@Override
            public void loaderBeforeLoad(LoadEvent le) {
				searchStatus.setBusy("Connection to the Server");
				if (select.isEnabled())
					select.disable();
			}
			
			@Override
            public void loaderLoad(LoadEvent le) {
				setSearchStatus(EnumSearchStatus.STATUS_SEARCH,
								EnumSearchStatus.STATUS_MESSAGE_SEARCH);
				//				toolBar.enable();
			}
			
			@Override
            public void loaderLoadException(LoadEvent le) {
				clearGridElements();
				try {
					throw le.exception;
				} catch (ApplicationException e) {
					setSearchStatus(EnumSearchStatus.STATUS_NO_SEARCH,
									EnumSearchStatus.STATUS_MESSAGE_NOT_SEARCH);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					setSearchStatus(EnumSearchStatus.STATUS_SEARCH_ERROR,
									EnumSearchStatus.STATUS_MESSAGE_SEARCH_ERROR);
				}
			}
			
		});
		
	}
}