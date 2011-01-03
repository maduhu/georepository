/*
 * $Header: it.geosolutions.georepo.gui.client.widget.DGWATCHSearchWidget,v. 0.1 30/lug/2010 14.52.32 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 30/lug/2010 14.52.32 $
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

import it.geosolutions.georepo.gui.client.widget.SearchStatus.EnumSearchStatus;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.event.WindowListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;

/**
 * @author giuseppe
 * 
 */
public abstract class DGWATCHSearchWidget<T extends BaseModel> extends Window {

	private VerticalPanel vp;
	protected FormPanel formPanel;
	protected ListStore<T> store;
	protected Grid<T> grid;
	protected TextField<String> search;
	protected RpcProxy<PagingLoadResult<T>> proxy;
	protected PagingLoader<PagingLoadResult<ModelData>> loader;
	protected PagingToolBar toolBar;
	protected Button select;
	protected Button cancel;
	protected SearchStatus searchStatus;

	protected String searchText;

	public DGWATCHSearchWidget() {
		initWindow();
		initVerticalPanel();
		initFormPanel();
		add(vp);
	}

	private void initWindow() {
		setModal(true);
		setResizable(false);
		setLayout(new FlowLayout());
		setPlain(true);
		setMaximizable(false);

		addWindowListener(new WindowListener() {

			@Override
            public void windowHide(WindowEvent we) {
				cancel();
			}

		});

		setWindowProperties();
	}

	private void initVerticalPanel() {
		vp = new VerticalPanel();
		vp.setSpacing(10);
		createStore();
		initGrid();
	}

	private void initFormPanel() {
		formPanel = new FormPanel();
		formPanel.setHeaderVisible(false);
		formPanel.setFrame(true);
		formPanel.setLayout(new FlowLayout());

		FieldSet searchFieldSet = new FieldSet();
		searchFieldSet.setHeading("Search");

		FormLayout layout = new FormLayout();
		layout.setLabelWidth(80);
		searchFieldSet.setLayout(layout);

		search = new TextField<String>();
		search.setFieldLabel("Find");

		search.addKeyListener(new KeyListener() {

			@Override
            public void componentKeyUp(ComponentEvent event) {
				if ((event.getKeyCode() == 8) && (search.getValue() == null)) {
					reset();
					loader.load(0, 25);
				}
			}

			@Override
            public void componentKeyPress(ComponentEvent event) {
				if ((event.getKeyCode() == 13)) {
					// && (!search.getValue().equals(""))) {
					// searchStatus.setBusy("Connection to the Server");
					if(search.getValue() != null){
						searchText = search.getValue() == null ? "" : search
								.getValue();
						loader.load(0, 25);
					}
				}
			}

		});

		BorderLayoutData data = new BorderLayoutData(LayoutRegion.CENTER);
		data.setMargins(new Margins(5, 5, 5, 5));

		searchFieldSet.add(search, data);

		formPanel.add(searchFieldSet);

		formPanel.add(this.grid);

		this.searchStatus = new SearchStatus();
		searchStatus.setAutoWidth(true);

		formPanel.getButtonBar().add(this.searchStatus);

		formPanel.getButtonBar().add(new LabelToolItem("    "));

		formPanel.setButtonAlign(HorizontalAlignment.RIGHT);

		select = new Button("Select", new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				select();
			}
		});

		select.setIconStyle("x-dgwatch-select");
		select.disable();

		formPanel.addButton(this.select);

		cancel = new Button("Cancel", new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				cancel();
			}
		});
		
		cancel.setIconStyle("x-dgwatch-cancel");

		formPanel.addButton(cancel);

		formPanel.setBottomComponent(this.toolBar);

		vp.add(formPanel);
	}

	private void initGrid() {
		ColumnModel cm = prepareColumnModel();

		grid = new Grid<T>(store, cm);
		grid.setBorders(true);

		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		grid.addListener(Events.CellClick, new Listener<BaseEvent>() {

			public void handleEvent(BaseEvent be) {
				if (!grid.getSelectionModel().getSelection().isEmpty())
					select.enable();
				else
					select.disable();
			}
		});

		grid.addListener(Events.CellDoubleClick, new Listener<BaseEvent>() {

			public void handleEvent(BaseEvent be) {
				select();
			}
		});

		setGridProperties();
	}

	/**
	 * Remove all beans from the Store and after Hide the window
	 */
	@SuppressWarnings("deprecation")
	public void cancel() {
		super.close();
		reset();
	}

	public void reset() {
		this.search.reset();
		this.store.removeAll();
		this.toolBar.clear();
		this.select.disable();
		this.searchStatus.clearStatus("");
	}

	public void clearGridElements() {
		this.store.removeAll();
		this.toolBar.clear();
	}

	/**
	 * Set the correct Status Iconn Style
	 */
	public void setSearchStatus(EnumSearchStatus status,
			EnumSearchStatus message) {
		this.searchStatus.setIconStyle(status.getValue());
		this.searchStatus.setText(message.getValue());
	}

	public abstract void setWindowProperties();

	public abstract void createStore();

	public abstract void setGridProperties();

	public abstract ColumnModel prepareColumnModel();

	public abstract void select();

}
