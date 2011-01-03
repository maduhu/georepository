/*
 * $Header: it.geosolutions.georepo.gui.client.widget.ChooseUserWidget,v. 0.1 22/lug/2010 12.03.28 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 22/lug/2010 12.03.28 $
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


import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.model.BeanKeyValue;
import it.geosolutions.georepo.gui.client.model.User;
import it.geosolutions.georepo.gui.client.service.LoginRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.DGWATCHChooserWidget;
import it.geosolutions.georepo.gui.client.widget.SearchStatus.EnumSearchStatus;
import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.core.XTemplate;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.LoadListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author giuseppe
 * 
 */
public class ChooseUserPagWidget extends DGWATCHChooserWidget<User> {

	private LoginRemoteAsync service;

	public ChooseUserPagWidget(LoginRemoteAsync service) {
		super();
		this.service = service;
	}

	@Override
	public void setListProperties() {
		view.setDisplayProperty(BeanKeyValue.USER_NAME.getValue());
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
//						callback);
				
			}
		};

		loader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);

		loader.setRemoteSort(false);

		store = new ListStore<User>(loader);

		this.toolBar.bind(loader);
		this.toolBar.disable();
		
		setUpLoadListener();

	}

	@Override
	public void createTemplate() {
		detailTp = XTemplate.create(getDetailTemplate());
	}

	@Override
	public void setComboProperties() {
		sort.add("Sort Results");
		sort.add("User Name");
		sort.setSimpleValue("Sort Results");
		sort.addListener(Events.Select, new Listener<FieldEvent>() {
			public void handleEvent(FieldEvent be) {
				if(!sort.getSimpleValue().equals("Sort Results"))
					sort();
			}
		});

		main.setBottomComponent(this.toolBar);

	}

	private void sort() {
		if(!store.getModels().isEmpty())
			store.sort("userName", SortDir.ASC);
	}

	public native String getDetailTemplate() /*-{ 
		return ['<div class="details">', 
			'<tpl for=".">', 
			'<img src="{path}"><div class="details-info">', 
			'<b>User Name:</b>', 
			'<span>{userName}</span>', 
			'</tpl>', 
			'</div>'].join(""); 
	}-*/;

	@Override
	public void onDispatch(User model) {
		searchStatus.setBusy("UnShare AOI....");
		Dispatcher.forwardEvent(DGWATCHEvents.UNSHARE_AOI, model);
	}

	@Override
	public void setDialogProperties() {
		chooser.setHeading("Choose User");
		chooser.setResizable(false);

		chooser.addListener(Events.Show, new Listener<WindowEvent>() {

			public void handleEvent(WindowEvent be) {
				searchText = "";
				loader.load(0, 25);
			}
		});

		chooser.addListener(Events.Hide, new Listener<WindowEvent>() {

			public void handleEvent(WindowEvent be) {
				reset();
			}
		});
	}

	private void setUpLoadListener() {
		loader.addLoadListener(new LoadListener() {

			@Override
            public void loaderBeforeLoad(LoadEvent le) {
				searchStatus.setBusy("Connection to the Server");
				if (ok.isEnabled())
					ok.disable();
			}

			@Override
            public void loaderLoad(LoadEvent le) {
				setSearchStatus(EnumSearchStatus.STATUS_SEARCH,
						EnumSearchStatus.STATUS_MESSAGE_SEARCH);
				toolBar.enable();
			}

			@Override
            public void loaderLoadException(LoadEvent le) {
				clearListViewElements();
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