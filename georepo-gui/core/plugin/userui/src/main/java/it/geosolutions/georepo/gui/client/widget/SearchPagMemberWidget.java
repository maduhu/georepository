/*
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
import it.geosolutions.georepo.gui.client.model.Member;
import it.geosolutions.georepo.gui.client.model.MemberKeyValue;
import it.geosolutions.georepo.gui.client.service.MembersRemoteAsync;
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

public class SearchPagMemberWidget  extends DGWATCHSearchWidget<Member> {
	
	private MembersRemoteAsync service;
	
	public SearchPagMemberWidget(MembersRemoteAsync service) {
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
		
		this.proxy = new RpcProxy<PagingLoadResult<Member>>() {
			
			@Override
			protected void load(Object loadConfig,
								AsyncCallback<PagingLoadResult<Member>> callback) {
				
				service.loadMembers((PagingLoadConfig) loadConfig, searchText,
								  callback);
			}
		};
		
		loader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);
		loader.setRemoteSort(false);
		
		store = new ListStore<Member>(loader);
		
		this.toolBar.bind(loader);
		
		setUpLoadListener();
	}
	
	@Override
	public void setGridProperties() {
		grid.setAutoExpandColumn(MemberKeyValue.MEMBER_NAME.getValue());
		
		grid.setWidth(350);
		grid.setHeight(270);
	}
	
	@Override
	public ColumnModel prepareColumnModel() {
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
		
		ColumnConfig memberNameColumn = new ColumnConfig();
		memberNameColumn.setId(MemberKeyValue.MEMBER_NAME.getValue());
		memberNameColumn.setHeader("Member Name");
		memberNameColumn.setWidth(80);
		configs.add(memberNameColumn);
		
		ColumnConfig memberUIDAddress = new ColumnConfig();
		memberUIDAddress.setId(MemberKeyValue.MEMBER_ORG.getValue());
		memberUIDAddress.setHeader("Organization");
		memberUIDAddress.setWidth(120);
		configs.add(memberUIDAddress);
		
		return new ColumnModel(configs);
	}
	
	@Override
	public void select() {
		searchStatus.setBusy("Get Member Details....");
		Dispatcher.forwardEvent(DGWATCHEvents.BIND_SELECTED_MEMBER, grid
								.getSelectionModel().getSelectedItem());
	}
	
	private void setUpLoadListener() {
		loader.addLoadListener(new LoadListener() {
			
			@Override
            public void loaderBeforeLoad(LoadEvent le) {
				searchStatus.setBusy("Connecting to the Server");
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
					setSearchStatus(EnumSearchStatus.STATUS_SEARCH_ERROR,
									EnumSearchStatus.STATUS_MESSAGE_SEARCH_ERROR);
				}
			}
			
		});
		
	}
}
