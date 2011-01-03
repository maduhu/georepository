/*
 * $Header: it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget,v. 0.1 09/lug/2010 14.35.12 created by frank $
 * $Revision: 0.1 $
 * $Date: 09/lug/2010 14.35.12 $
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

import java.util.List;

import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;

/**
 * @author frank
 * 
 */
public abstract class DGWATCHGridWidget<T extends BaseModel> {

	protected ListStore<T> store;
	protected Grid<T> grid;

	public DGWATCHGridWidget() {
		createStore();
		initGrid();
	}

	public DGWATCHGridWidget(List<T> models) {
		createStore();
		this.store.add(models);
		initGrid();
	}

	private void initGrid() {
		ColumnModel cm = prepareColumnModel();

		grid = new Grid<T>(store, cm);
		grid.setBorders(true);

		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		setGridProperties();
	}

	public abstract void setGridProperties();

	public abstract ColumnModel prepareColumnModel();
	
	public abstract void createStore();

	/**
	 * @return the grid
	 */
	public Grid<T> getGrid() {
		return grid;
	}

	/**
	 * @return the store
	 */
	public ListStore<T> getStore() {
		return store;
	}

}
