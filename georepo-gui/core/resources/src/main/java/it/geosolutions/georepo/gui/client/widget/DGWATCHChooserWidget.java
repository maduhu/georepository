/*
 * $Header: it.geosolutions.georepo.gui.client.widget.DGWATCHChooserWidget,v. 0.1 22/lug/2010 10.30.25 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 22/lug/2010 10.30.25 $
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
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.core.XTemplate;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Util;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

/**
 * @author giuseppe
 * 
 */
public abstract class DGWATCHChooserWidget<T extends BeanModel> {

	protected ListStore<T> store;
	protected SimpleComboBox<String> sort;
	protected XTemplate detailTp;
	protected ListView<T> view;
	protected LayoutContainer details;
	protected Dialog chooser;
	protected ToolBar bar;
	protected RpcProxy<PagingLoadResult<T>> proxy;
	protected PagingLoader<PagingLoadResult<ModelData>> loader;
	protected PagingToolBar toolBar;
	protected TextField<String> search;
	protected ContentPanel main;
	protected Button ok;
	protected Button cancel;
	protected SearchStatus searchStatus;

	protected String searchText;

	public DGWATCHChooserWidget() {
		createTemplate();
		createStore();
		createChooser();
		createMainPanel();
		initListView();
	}

	private void initListView() {
		view = new ListView<T>();

		view.setId("img-chooser-view");
		view.setBorders(false);
		view.setStore(store);

		setListProperties();

		view.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		view.getSelectionModel().addListener(Events.SelectionChange,
				new Listener<SelectionChangedEvent<T>>() {
					public void handleEvent(SelectionChangedEvent<T> be) {
						onSelectionChange(be);
					}
				});
		main.add(view);

		createDetails();

	}

	private void createDetails() {
		details = new LayoutContainer();
		details.setBorders(true);
		details.setStyleAttribute("backgroundColor", "white");

		BorderLayoutData eastData = new BorderLayoutData(LayoutRegion.EAST,
				150, 150, 250);
		eastData.setSplit(true);

		BorderLayoutData centerData = new BorderLayoutData(LayoutRegion.CENTER);
		centerData.setMargins(new Margins(0, 5, 0, 0));

		chooser.add(main, centerData);
		chooser.add(details, eastData);

	}

	private void createMainPanel() {
		main = new ContentPanel();
		main.setBorders(true);
		main.setBodyBorder(false);
		main.setLayout(new FitLayout());
		main.setHeaderVisible(false);

		bar = new ToolBar();
		bar.add(new LabelToolItem("Search: "));

		search = new TextField<String>();
		search.setWidth(100);

		search.addKeyListener(new KeyListener() {

			@Override
            public void componentKeyUp(ComponentEvent event) {
				if ((event.getKeyCode() == 8) && (search.getValue() == null)) {
					reset();
				}
			}

			@Override
            public void componentKeyPress(ComponentEvent event) {
				if (event.getKeyCode() == 13) {
					searchText = search.getValue() == null ? "" : search
							.getValue();
					loader.load(0, 25);
				}
			}

		});

		bar.add(search);
		bar.add(new SeparatorToolItem());
		bar.add(new LabelToolItem("Sort By:"));

		sort = new SimpleComboBox<String>();
		sort.setTriggerAction(TriggerAction.ALL);
		sort.setEditable(false);
		sort.setForceSelection(true);
		sort.setWidth(90);

		setComboProperties();

		bar.add(sort);

		main.setTopComponent(bar);
	}

	private void createChooser() {
		chooser = new Dialog();
		chooser.setMinWidth(500);
		chooser.setMinHeight(300);
		chooser.setModal(true);
		chooser.setLayout(new BorderLayout());
		chooser.setBodyStyle("border: none;background: none");
		chooser.setBodyBorder(false);
		chooser.setButtons("");
		chooser.setHideOnButtonClick(true);

		setDialogProperties();
		
		this.searchStatus = new SearchStatus();
		this.searchStatus.setAutoWidth(true);

		chooser.getButtonBar().add(this.searchStatus);
		
		this.ok = new Button("Ok", new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				if (!view.getSelectionModel().getSelection().isEmpty()) {
					onDispatch(view.getSelectionModel().getSelectedItem());
				}

			}
		});

		this.ok.disable();

		chooser.addButton(ok);

		this.cancel = new Button("Cancel",
				new SelectionListener<ButtonEvent>() {

					@Override
					public void componentSelected(ButtonEvent ce) {
						cancel();
					}
				});
		

		chooser.addButton(cancel);

	}

	private void onSelectionChange(SelectionChangedEvent<T> be) {
		if (!be.getSelection().isEmpty()) {
			detailTp.overwrite(details.getElement(),
					Util.getJsObject(be.getSelection().get(0)));
			ok.enable();
		} else {
			ok.disable();
			details.el().setInnerHtml("");
		}

	}

	public void cancel() {
		chooser.hide();
		reset();
	}

	public void reset() {
		this.search.reset();
		this.store.removeAll();
		this.toolBar.clear();
		this.toolBar.disable();
		ok.disable();
		view.getSelectionModel().deselectAll();
		this.searchStatus.clearStatus("");
	}

	public void clearListViewElements() {
		this.store.removeAll();
		this.toolBar.clear();
		this.toolBar.disable();
	}

	/**
	 * Set the correct Status Iconn Style
	 */
	public void setSearchStatus(EnumSearchStatus status,
			EnumSearchStatus message) {
		this.searchStatus.setIconStyle(status.getValue());
		this.searchStatus.setText(message.getValue());
	}

	public abstract void setListProperties();

	public abstract void createStore();

	public abstract void createTemplate();

	public abstract void setComboProperties();

	public abstract void onDispatch(T model);

	public abstract void setDialogProperties();

	/**
	 * @return the store
	 */
	public ListStore<T> getStore() {
		return store;
	}

	/**
	 * @return the detailTp
	 */
	public XTemplate getDetailTp() {
		return detailTp;
	}

	/**
	 * @return the details
	 */
	public LayoutContainer getDetails() {
		return details;
	}

	/**
	 * @return the chooser
	 */
	public Dialog getChooser() {
		return chooser;
	}

}