/*
 * $Header: it.geosolutions.georepo.gui.client.widget.UpdateUserWidget,v. 0.1 02/ago/2010 15.01.30 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 02/ago/2010 15.01.30 $
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


import it.geosolutions.georepo.gui.client.DGWATCHData;
import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.SendType;
import it.geosolutions.georepo.gui.client.UpdateInterval;
import it.geosolutions.georepo.gui.client.SendType.SendTypeEnum;
import it.geosolutions.georepo.gui.client.UpdateInterval.UpdateIntervalEnum;
import it.geosolutions.georepo.gui.client.model.BeanKeyValue;
import it.geosolutions.georepo.gui.client.model.User;
import it.geosolutions.georepo.gui.client.widget.DGWATCHUpdateWidget;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

/**
 * @author giuseppe
 * 
 */
public class UpdateUserWidget extends DGWATCHUpdateWidget<User> {

	private enum UpdateUserKey {
		USER_NAME_ID("USER_NAME_UPDATE"), EMAIL_UPDATE("EMAIL_UPDATE");

		private String value;

		UpdateUserKey(String value) {
			this.value = value;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}
	}

	private TextField<String> userName;
	private TextField<String> email;
	private CheckBox reducedContent;
	private ComboBox<SendType> comboTypes;
	private ComboBox<UpdateInterval> comboTimes;

	private ListStore<SendType> storeTypes;
	private ListStore<UpdateInterval> storeTimes;

	/**
	 * 
	 */
	@Override
    public void bind(User user) {
		super.bind(user);
		// checkComponentsModifiability();
	}

	// private void checkComponentsModifiability() {
	// if (object.isEnabled()) {
	// this.email.setEnabled(false);
	// } else {
	// this.email.setAllowBlank(false);
	//
	// }
	//
	// }

	public void execute() {
		this.saveStatus.setBusy("Operation in progress");
		Dispatcher.forwardEvent(DGWATCHEvents.UPDATE_USER, this.object);
	}

	@Override
	public void addComponentToForm() {
		fieldSet = new FieldSet();
		fieldSet.setHeading("User Information");
		FormLayout layout = new FormLayout();
		layout.setLabelWidth(150);
		fieldSet.setLayout(layout);

		userName = new TextField<String>();
		userName.setEnabled(false);
		userName.setId(UpdateUserKey.USER_NAME_ID.getValue());
		userName.setName(BeanKeyValue.USER_NAME.getValue());
		userName.setFieldLabel("User Name");
		fieldSet.add(userName);

		email = new TextField<String>();
		email.setFieldLabel("Email");
		email.setId(UpdateUserKey.EMAIL_UPDATE.getValue());
		email.setName(BeanKeyValue.EMAIL.getValue());
		email.setEnabled(false);

		// email.setValidator(new Validator() {
		//
		// public String validate(Field<?> field, String value) {
		// if (((String) field.getValue()).matches(".+@.+\\.[a-z]+"))
		// return null;
		// return "Email not valid";
		// }
		// });

		fieldSet.add(email);

		reducedContent = new CheckBox();
		reducedContent.setId(BeanKeyValue.REDUCED_CONTENT_UPDATE.getValue());
		reducedContent.setName(BeanKeyValue.REDUCED_CONTENT.getValue());
		reducedContent.setFieldLabel("Hide Attributions");
		reducedContent.setWidth(150);
		reducedContent.setEnabled(true);

		fieldSet.add(reducedContent);

		this.initCombo();

		this.formPanel.add(fieldSet);

	}

	private void initCombo() {

		this.storeTypes = new ListStore<SendType>();
		this.storeTypes.add(DGWATCHData.getSendTypes());

		comboTypes = new ComboBox<SendType>();
		comboTypes.setFieldLabel("Type");
		comboTypes.setId(SendTypeEnum.TYPE.getValue());
		comboTypes.setName(SendTypeEnum.TYPE.getValue());
		comboTypes.setEmptyText("Select Type ...");
		comboTypes.setDisplayField(SendTypeEnum.TYPE.getValue());
		comboTypes.setEditable(false);
		comboTypes.setAllowBlank(false);
		comboTypes.setForceSelection(true);
		comboTypes.setStore(storeTypes);
		comboTypes.setTypeAhead(true);
		comboTypes.setTriggerAction(TriggerAction.ALL);

		fieldSet.add(comboTypes);

		this.storeTimes = new ListStore<UpdateInterval>();
		this.storeTimes.add(DGWATCHData.getTimes());

		comboTimes = new ComboBox<UpdateInterval>();
		comboTimes.setFieldLabel("Interval");
		comboTimes.setId(UpdateIntervalEnum.TIME.getValue());
		comboTimes.setName(UpdateIntervalEnum.TIME.getValue());
		comboTimes.setEmptyText("Select interval ...");
		comboTimes.setDisplayField(UpdateIntervalEnum.TIME.getValue());
		comboTimes.setEditable(false);
		comboTimes.setAllowBlank(false);
		comboTimes.setForceSelection(true);
		comboTimes.setStore(storeTimes);
		comboTimes.setTypeAhead(true);
		comboTimes.setTriggerAction(TriggerAction.ALL);
		fieldSet.add(comboTimes);

	}

	@Override
	public void initSize() {
		setHeading("Update User");
		setSize(470, 250);

	}

	@Override
	public void initSizeFormPanel() {
		formPanel.setHeaderVisible(false);
		formPanel.setSize(470, 250);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void cancel() {
		super.close();
		this.formBinding.unbind();
	}
}