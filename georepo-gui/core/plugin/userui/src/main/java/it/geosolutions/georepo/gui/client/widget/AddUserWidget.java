/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.AddUserWidget,v. 0.1 3-gen-2011 17.06.54 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 17.06.54 $
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

import it.geosolutions.georepo.gui.client.GeoRepoWidgetsData;
import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.SendType;
import it.geosolutions.georepo.gui.client.UpdateInterval;
import it.geosolutions.georepo.gui.client.SendType.SendTypeEnum;
import it.geosolutions.georepo.gui.client.UpdateInterval.UpdateIntervalEnum;
import it.geosolutions.georepo.gui.client.form.GeoRepoFormWidget;
import it.geosolutions.georepo.gui.client.model.RegUser;
import it.geosolutions.georepo.gui.client.model.User;
import it.geosolutions.georepo.gui.client.model.RegUser.RegUserKeyValue;

import java.util.List;

import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class AddUserWidget.
 */
public class AddUserWidget extends GeoRepoFormWidget {

    // private TextField<String> userName;
    /** The combo user names. */
    private ComboBox<RegUser> comboUserNames;

    /** The email. */
    private TextField<String> email;

    /** The reduced content. */
    private CheckBox reducedContent;

    /** The combo types. */
    private ComboBox<SendType> comboTypes;

    /** The combo times. */
    private ComboBox<UpdateInterval> comboTimes;

    /** The user to save. */
    private User userToSave = new User();

    /** The store types. */
    private ListStore<SendType> storeTypes;

    /** The store times. */
    private ListStore<UpdateInterval> storeTimes;

    /** The store reg user. */
    private ListStore<RegUser> storeRegUser;

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.form.IFormexecute()
     */
    public void execute() {
        this.saveStatus.setBusy("Operation in progress");
        this.injectValue();
        Dispatcher.forwardEvent(GeoRepoEvents.SAVE_USER, this.userToSave);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.form.DGWATCHFormWidgetaddComponentToForm ()
     */
    @Override
    public void addComponentToForm() {
        fieldSet = new FieldSet();
        fieldSet.setHeading("User Information");
        FormLayout layout = new FormLayout();
        layout.setLabelWidth(150);
        fieldSet.setLayout(layout);

        // userName = new TextField<String>();
        // userName.setAllowBlank(false);
        // userName.setFieldLabel("User Name");
        // fieldSet.add(userName);

        this.storeRegUser = new ListStore<RegUser>();

        comboUserNames = new ComboBox<RegUser>();
        comboUserNames.setWidth(300);
        comboUserNames.setFieldLabel("User Name");
        comboUserNames.setEmptyText("Select User Name ..");
        comboUserNames.setTemplate(getTemplate());
        comboUserNames.setDisplayField(RegUserKeyValue.CHOISE.getValue());
        comboUserNames.setEditable(false);
        comboUserNames.setAllowBlank(false);
        comboUserNames.setForceSelection(true);
        comboUserNames.setStore(storeRegUser);

        comboUserNames.setTypeAhead(true);
        comboUserNames.setTriggerAction(TriggerAction.ALL);

        this.fieldSet.add(comboUserNames);

        email = new TextField<String>();
        email.setAllowBlank(false);
        email.setFieldLabel("Email");

        email.setValidator(new Validator() {

            public String validate(Field<?> field, String value) {
                if (((String) field.getValue()).matches(".+@.+\\.[a-z]+"))
                    return null;
                return "Email not valid";
            }
        });

        this.fieldSet.add(email);

        reducedContent = new CheckBox();
        reducedContent.setFieldLabel("Hide Attributions");
        reducedContent.setWidth(150);
        reducedContent.setEnabled(true);

        this.fieldSet.add(reducedContent);

        this.initCombo();

        this.formPanel.add(fieldSet);

    }

    /**
     * Inits the combo.
     */
    private void initCombo() {
        this.storeTypes = new ListStore<SendType>();
        this.storeTypes.add(GeoRepoWidgetsData.getSendTypes());

        comboTypes = new ComboBox<SendType>();
        comboTypes.setFieldLabel("Type");
        comboTypes.setEmptyText("Select Type ...");
        comboTypes.setDisplayField(SendTypeEnum.TYPE.getValue());
        comboTypes.setEditable(false);
        comboTypes.setAllowBlank(false);
        comboTypes.setForceSelection(true);
        comboTypes.setStore(storeTypes);
        comboTypes.setTypeAhead(true);
        comboTypes.setTriggerAction(TriggerAction.ALL);

        this.fieldSet.add(comboTypes);

        this.storeTimes = new ListStore<UpdateInterval>();
        this.storeTimes.add(GeoRepoWidgetsData.getTimes());

        comboTimes = new ComboBox<UpdateInterval>();
        comboTimes.setFieldLabel("Interval");
        comboTimes.setEmptyText("Select interval ...");
        comboTimes.setDisplayField(UpdateIntervalEnum.TIME.getValue());
        comboTimes.setEditable(false);
        comboTimes.setAllowBlank(false);
        comboTimes.setForceSelection(true);
        comboTimes.setStore(storeTimes);
        comboTimes.setTypeAhead(true);
        comboTimes.setTriggerAction(TriggerAction.ALL);

        this.fieldSet.add(comboTimes);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.form.DGWATCHFormWidgetinitSize()
     */
    @Override
    public void initSize() {
        setHeading("Add User");
        setSize(450, 280);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.form.DGWATCHFormWidgetinitSizeFormPanel ()
     */
    @Override
    public void initSizeFormPanel() {
        formPanel.setHeaderVisible(false);
        formPanel.setSize(450, 250);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.form.DGWATCHFormWidgetcancel()
     */
    @SuppressWarnings("deprecation")
    @Override
    public void cancel() {
        super.close();
        this.reset();
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.form.DGWATCHFormWidget#reset()
     */
    @Override
    public void reset() {
        // this.userName.reset();
        this.comboUserNames.reset();
        this.storeRegUser.removeAll();
        this.email.reset();
        this.comboTypes.reset();
        this.comboTimes.reset();
        this.reducedContent.reset();
        this.saveStatus.clearStatus("");
    }

    /**
     * Inject value.
     */
    private void injectValue() {
        userToSave.setUserName(this.comboUserNames.getValue().getUserName());
        userToSave.setConnectId(this.comboUserNames.getValue().getConnectId());
        userToSave.setEmailAddress(email.getValue());
        userToSave.setReducedContent(reducedContent.getValue());
        userToSave.setSendType(comboTypes.getValue().getType());
        userToSave.setUpInteval(comboTimes.getValue().getTime());
    }

    /**
     * Fill store reg user.
     * 
     * @param regUsers
     *            the reg users
     */
    public void fillStoreRegUser(List<RegUser> regUsers) {
        this.storeRegUser.add(regUsers);
    }

    /**
     * Gets the template.
     * 
     * @return the template
     */
    private native String getTemplate() /*-{ 
                                        return  [ 
                                        '<tpl for=".">', 
                                        '<div class="x-combo-list-item" qtip="{choise}" qtitle="Choise">{choise}</div>', 
                                        '</tpl>' 
                                        ].join(""); 
                                        }-*/;
}
