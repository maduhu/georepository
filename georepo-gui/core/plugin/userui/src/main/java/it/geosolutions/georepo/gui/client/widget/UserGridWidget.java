/*
 * $Header: it.geosolutions.georepo.gui.client.widget.UserGridWidget,v. 0.1 09/lug/2010 14.38.23 created by frank $
 * $Revision: 0.1 $
 * $Date: 09/lug/2010 14.38.23 $
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

import it.geosolutions.georepo.gui.client.model.BeanKeyValue;
import it.geosolutions.georepo.gui.client.model.User;
import it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;

/**
 * @author frank
 * 
 */
public class UserGridWidget extends DGWATCHGridWidget<User> {

	private CheckColumnConfig emailEnable;
	private CheckColumnConfig rssEnable;
	
	/**
	 * 
	 */
	public UserGridWidget() {
		super();
	}

	/**
	 * @param models
	 */
	public UserGridWidget(List<User> models) {
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
		grid.setAutoExpandColumn(BeanKeyValue.USER_NAME.getValue());
		grid.addPlugin(emailEnable);
		grid.addPlugin(rssEnable);
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

		ColumnConfig userNameColumn = new ColumnConfig();
		userNameColumn.setId(BeanKeyValue.USER_NAME.getValue());
		userNameColumn.setHeader("User Name");
		userNameColumn.setWidth(80);
		configs.add(userNameColumn);

		ColumnConfig emailAddress = new ColumnConfig();
		emailAddress.setId(BeanKeyValue.EMAIL.getValue());
		emailAddress.setHeader("Email");
		emailAddress.setWidth(100);
		configs.add(emailAddress);

//		CellEditor checkBoxMailEditor = new CellEditor(new CheckBox());

		emailEnable = new CheckColumnConfig(
				BeanKeyValue.EMAIL_ENABLE.getValue(), "Mail", 60);

//		emailAddress.setEditor(checkBoxMailEditor);
		configs.add(emailEnable);

//		CellEditor checkBoxRSSEditor = new CellEditor(new CheckBox());

		rssEnable = new CheckColumnConfig(BeanKeyValue.RSS_ENABLE.getValue(),
				"RSS", 60);
//		rssEnable.setEditor(checkBoxRSSEditor);
		configs.add(rssEnable);

		return new ColumnModel(configs);
	}

	@Override
	public void createStore() {
		store = new ListStore<User>();
	}

}