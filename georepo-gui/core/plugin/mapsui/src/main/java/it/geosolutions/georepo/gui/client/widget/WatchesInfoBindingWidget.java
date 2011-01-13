/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.WatchesInfoBindingWidget,v. 0.1 3-gen-2011 16.52.55 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 16.52.55 $
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

import it.geosolutions.georepo.gui.client.ContentType;
import it.geosolutions.georepo.gui.client.GeoRepoWidgetsData;
import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.DistribContentType;
import it.geosolutions.georepo.gui.client.DistribUpdateInterval;
import it.geosolutions.georepo.gui.client.Resources;
import it.geosolutions.georepo.gui.client.RetrievalType;
import it.geosolutions.georepo.gui.client.SendType;
import it.geosolutions.georepo.gui.client.UpdateInterval;
import it.geosolutions.georepo.gui.client.ContentType.ContentTypeEnum;
import it.geosolutions.georepo.gui.client.DistribContentType.DistribContentTypeEnum;
import it.geosolutions.georepo.gui.client.DistribUpdateInterval.DistribUpdateIntervalEnum;
import it.geosolutions.georepo.gui.client.RetrievalType.RetrievalTypeEnum;
import it.geosolutions.georepo.gui.client.SendType.SendTypeEnum;
import it.geosolutions.georepo.gui.client.UpdateInterval.UpdateIntervalEnum;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.Authorization;
import it.geosolutions.georepo.gui.client.model.Watch;
import it.geosolutions.georepo.gui.client.model.WatchKeyValue;
import it.geosolutions.georepo.gui.client.model.WatchMail;
import it.geosolutions.georepo.gui.client.model.WatchNode;
import it.geosolutions.georepo.gui.client.widget.binding.GeoRepoWatchFormBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.StoreSorter;
import com.extjs.gxt.ui.client.widget.CheckBoxListView;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

// TODO: Auto-generated Javadoc
/**
 * The Class WatchesInfoBindingWidget.
 */
public class WatchesInfoBindingWidget extends GeoRepoFormBindingWidget<Watch> {

    // //////////////////////////////////
    // ALL
    // //////////////////////////////////

    /** The form data. */
    private FormData formData;

    /** The trigger. */
    private Button trigger;

    /** The clear. */
    private Button clear;

    /** The watch model. */
    private Watch watchModel;

    /** The selected. */
    private boolean selected;

    // //////////////////////////////////
    //
    // NOTIFICATION
    //
    // //////////////////////////////////

    /** The title. */
    private TextField<String> title;

    /** The email text. */
    private TextField<String> emailText;

    /** The begin date. */
    private DateField beginDate;

    /** The expiration. */
    private DateField expiration;

    // private CheckBox customizedAttrib;

    /** The notification. */
    private Radio notification;

    /** The distribution. */
    private Radio distribution;

    /** The radio group. */
    private RadioGroup radioGroup;

    /** The radio table. */
    private FlexTable radioTable;

    /** The notification field set. */
    private FieldSet notificationFieldSet;

    /** The mail set. */
    private FieldSet mailSet;

    /** The store types. */
    private ListStore<SendType> storeTypes;

    /** The store retrieval. */
    private ListStore<RetrievalType> storeRetrieval;

    /** The store content. */
    private ListStore<ContentType> storeContent;

    /** The store times. */
    private ListStore<UpdateInterval> storeTimes;

    /** The combo types. */
    private ComboBox<SendType> comboTypes;

    /** The combo retrieval. */
    private ComboBox<RetrievalType> comboRetrieval;

    /** The combo content. */
    private ComboBox<ContentType> comboContent;

    /** The combo times. */
    private ComboBox<UpdateInterval> comboTimes;

    /** The store. */
    private ListStore<WatchMail> store;

    /** The mails list. */
    private ListView<WatchMail> mailsList;

    // private Button customAttrib;
    /** The message text. */
    private TextArea messageText;

    /** The delete mail. */
    private Button deleteMail;

    /** The add mail. */
    private Button addMail;

    /** The modify mail. */
    private Button modifyMail;

    // //////////////////////////////////
    //
    // DISTRIBUTIONS
    //
    // //////////////////////////////////

    /** The distribution field set. */
    private FieldSet distributionFieldSet;

    /** The dist store content. */
    private ListStore<DistribContentType> distStoreContent;

    /** The dist combo content. */
    private ComboBox<DistribContentType> distComboContent;

    /** The dist store. */
    private ListStore<WatchNode> distStore;

    /** The node list. */
    private CheckBoxListView<WatchNode> nodeList;

    /** The dist store times. */
    private ListStore<DistribUpdateInterval> distStoreTimes;

    /** The dist combo times. */
    private ComboBox<DistribUpdateInterval> distComboTimes;

    /**
     * Instantiates a new watches info binding widget.
     */
    public WatchesInfoBindingWidget() {
        this.init();
    }

    /**
     * Inits the.
     */
    private void init() {
        formData = new FormData("-20");
        formPanel = createFormPanel();
        formBinding = new GeoRepoWatchFormBinding(formPanel, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHBindingWidget# createFormPanel()
     */
    @Override
    public FormPanel createFormPanel() {
        FormPanel fp = new FormPanel();
        fp.setFrame(true);
        fp.setHeaderVisible(false);

        FieldSet fieldSet = new FieldSet();
        fieldSet.setHeading(I18nProvider.getMessages().watchGeneralFieldSet());
        fieldSet.setCheckboxToggle(false);
        fieldSet.setCollapsible(false);

        FormLayout layout = new FormLayout();
        fieldSet.setLayout(layout);

        title = new TextField<String>();
        title.setId(WatchKeyValue.WATCH_TITLE.getValue());
        title.setName(WatchKeyValue.WATCH_TITLE.getValue());
        title.setFieldLabel(I18nProvider.getMessages().watchTitleField());
        title.setWidth(150);

        fieldSet.add(title, formData);

        beginDate = new DateField();
        beginDate.setId(WatchKeyValue.WATCH_BEGIN_DATE.getValue());
        beginDate.setName(WatchKeyValue.WATCH_BEGIN_DATE.getValue());
        beginDate.setFieldLabel(I18nProvider.getMessages().watchEffectiveDate());
        beginDate.setWidth(150);

        fieldSet.add(beginDate, formData);

        expiration = new DateField();
        expiration.setId(WatchKeyValue.WATCH_EXPIRATION.getValue());
        expiration.setName(WatchKeyValue.WATCH_EXPIRATION.getValue());
        expiration.setFieldLabel(I18nProvider.getMessages().watchExpirationDate());
        expiration.setWidth(150);

        fieldSet.add(expiration, formData);

        fp.add(fieldSet);

        // //////////////////////////
        // Actions Radio Buttons
        // //////////////////////////

        // FlexTable radioTable = new FlexTable();
        radioTable = new FlexTable();

        radioTable.setCellSpacing(8);
        radioTable.setCellPadding(12);

        radioTable.setHTML(1, 0, "<div style='font-size: 12px; width: 90px'>Action :</span>");

        notification = new Radio();
        notification.setBoxLabel(I18nProvider.getMessages().watchNotificationRadio());
        notification.setValue(true);

        distribution = new Radio();
        distribution.setBoxLabel(I18nProvider.getMessages().watchDistributionRadio());

        radioGroup = new RadioGroup();
        radioGroup.setId("watchRadioGroup");
        radioGroup.setName("watchRadioGroup");
        // radioGroup.setFieldLabel(I18nProvider.getMessages().watchActionField());
        radioGroup.addListener(Events.Change, new Listener<BaseEvent>() {
            public void handleEvent(BaseEvent be) {
                if (radioGroup.getValue().getBoxLabel().equalsIgnoreCase("Distribution")) {
                    notificationFieldSet.hide();
                    distributionFieldSet.show();
                } else if (radioGroup.getValue().getBoxLabel().equalsIgnoreCase("Notification")) {
                    distributionFieldSet.hide();
                    notificationFieldSet.show();
                }
            }
        });
        radioGroup.add(notification);
        radioGroup.add(distribution);

        radioTable.setWidget(1, 1, radioGroup);

        fp.add(radioTable);

        // ///////////////////////////////////
        // Managing Notifications
        // ///////////////////////////////////

        notificationFieldSet = new FieldSet();
        notificationFieldSet.setHeading(I18nProvider.getMessages().watchNotificationFieldSet());
        notificationFieldSet.setCheckboxToggle(false);
        notificationFieldSet.setCollapsible(false);

        FormLayout notificationLayout = new FormLayout();
        notificationFieldSet.setLayout(notificationLayout);

        this.storeTypes = new ListStore<SendType>();
        this.storeTypes.add(GeoRepoWidgetsData.getSendTypes());

        comboTypes = new ComboBox<SendType>();
        comboTypes.setFieldLabel(I18nProvider.getMessages().watchTypeCombo());
        comboTypes.setEmptyText(I18nProvider.getMessages().watchTypeComboEmptyText());
        comboTypes.setId(SendTypeEnum.TYPE.getValue());
        comboTypes.setName(SendTypeEnum.TYPE.getValue());
        comboTypes.setDisplayField(SendTypeEnum.TYPE.getValue());
        comboTypes.setEditable(false);
        comboTypes.setAllowBlank(false);
        comboTypes.setForceSelection(true);
        comboTypes.setStore(storeTypes);
        comboTypes.setTypeAhead(true);
        comboTypes.setTriggerAction(TriggerAction.ALL);
        comboTypes.addListener(Events.SelectionChange, new Listener<BaseEvent>() {

            /*
             * (non-Javadoc)
             * 
             * @see com.extjs.gxt.ui.client.event.Listener#handleEvent(com.extjs.
             * gxt.ui.client.event.BaseEvent)
             */
            public void handleEvent(BaseEvent be) {
                String type = comboTypes.getValue().getType();

                if (type.equalsIgnoreCase("Email") || type.equalsIgnoreCase("EMailAndRSS"))
                    mailSet.enable();
                else
                    mailSet.disable();
            }

        });

        notificationFieldSet.add(comboTypes, formData);

        this.storeRetrieval = new ListStore<RetrievalType>();
        this.storeRetrieval.add(GeoRepoWidgetsData.getRetrievalType());

        comboRetrieval = new ComboBox<RetrievalType>();
        comboRetrieval.setFieldLabel(I18nProvider.getMessages().watchRetrievalCombo());
        comboRetrieval.setEmptyText(I18nProvider.getMessages().watchRetrievalComboemptyText());
        comboRetrieval.setId(RetrievalTypeEnum.TYPE.getValue());
        comboRetrieval.setName(RetrievalTypeEnum.TYPE.getValue());
        comboRetrieval.setDisplayField(RetrievalTypeEnum.TYPE.getValue());
        comboRetrieval.setEditable(false);
        comboRetrieval.setAllowBlank(false);
        comboRetrieval.setForceSelection(true);
        comboRetrieval.setStore(storeRetrieval);
        comboRetrieval.setTypeAhead(true);
        comboRetrieval.setTriggerAction(TriggerAction.ALL);

        notificationFieldSet.add(comboRetrieval, formData);

        // ///////////////////////////////////
        // Managing the Mail Addresses
        // ///////////////////////////////////

        mailSet = new FieldSet();
        mailSet.setHeading(I18nProvider.getMessages().watchMailsFieldSet());
        mailSet.setCheckboxToggle(false);
        mailSet.setCollapsible(false);

        mailSet.setLayout(new FormLayout());

        store = new ListStore<WatchMail>();
        store.setStoreSorter(new StoreSorter<WatchMail>());

        FlexTable tableListView = new FlexTable();
        tableListView.setCellPadding(12);

        mailsList = new ListView<WatchMail>();
        mailsList.setDisplayProperty("mail");
        mailsList.setHeight(90);
        mailsList.setWidth(250);
        mailsList.setStore(store);
        mailsList.addListener(Events.DoubleClick, new Listener<BaseEvent>() {

            /*
             * (non-Javadoc)
             * 
             * @see com.extjs.gxt.ui.client.event.Listener#handleEvent(com.extjs.
             * gxt.ui.client.event.BaseEvent)
             */
            public void handleEvent(BaseEvent be) {
                String mailValue = mailsList.getSelectionModel().getSelectedItem().getMail();
                emailText.setValue(mailValue);
                addMail.disable();
                modifyMail.enable();
            }

        });
        mailsList.addListener(Events.Select, new Listener<BaseEvent>() {

            /*
             * (non-Javadoc)
             * 
             * @see com.extjs.gxt.ui.client.event.Listener#handleEvent(com.extjs.
             * gxt.ui.client.event.BaseEvent)
             */
            public void handleEvent(BaseEvent be) {
                deleteMail.enable();
            }

        });

        deleteMail = new Button("", new SelectionListener<ButtonEvent>() {

            /*
             * (non-Javadoc)
             * 
             * @see com.extjs.gxt.ui.client.event.SelectionListener#componentSelected
             * (com.extjs.gxt.ui.client.event.ComponentEvent)
             */
            @Override
            public void componentSelected(ButtonEvent ce) {
                WatchMail model = mailsList.getSelectionModel().getSelectedItem();
                mailsList.getStore().remove(model);
                mailsList.refresh();
                deleteMail.disable();

                emailText.clear();
                addMail.enable();
                modifyMail.disable();
            }

        });

        deleteMail.setToolTip(I18nProvider.getMessages().watchDeleteMailButtonTooltip());
        deleteMail.setWidth(20);
        deleteMail.setIconStyle("x-georepo-delete");
        deleteMail.disable();

        tableListView.setWidget(1, 0, mailsList);
        tableListView.setWidget(1, 1, deleteMail);

        mailSet.disable();
        mailSet.add(tableListView);

        FlexTable tableMailAdd = new FlexTable();

        tableMailAdd.setCellSpacing(8);
        tableMailAdd.setCellPadding(12);

        emailText = new TextField<String>();
        emailText.setValidator(new Validator() {

            /*
             * (non-Javadoc)
             * 
             * @see com.extjs.gxt.ui.client.widget.form.Validator#validate(com.extjs
             * .gxt.ui.client.widget.form.Field, java.lang.String)
             */
            public String validate(Field<?> field, String value) {
                if (((String) field.getValue()).matches(".+@.+\\.[a-z]+"))
                    return null;
                return "Email not valid";
            }

        });
        emailText.setWidth(150);

        addMail = new Button("", new SelectionListener<ButtonEvent>() {

            /*
             * (non-Javadoc)
             * 
             * @see com.extjs.gxt.ui.client.event.SelectionListener#componentSelected
             * (com.extjs.gxt.ui.client.event.ComponentEvent)
             */
            @Override
            public void componentSelected(ButtonEvent ce) {
                String value = emailText.getValue();
                if (value.matches(".+@.+\\.[a-z]+")) {
                    mailsList.getStore().add(new WatchMail(emailText.getValue()));
                    mailsList.refresh();

                    emailText.clear();
                    addMail.enable();
                    modifyMail.disable();
                }
            }
        });

        addMail.setToolTip(I18nProvider.getMessages().watchAddMailButtonTooltip());
        addMail.setWidth(20);
        addMail.setIconStyle("x-georepo-add");

        modifyMail = new Button("", new SelectionListener<ButtonEvent>() {

            /*
             * (non-Javadoc)
             * 
             * @see com.extjs.gxt.ui.client.event.SelectionListener#componentSelected
             * (com.extjs.gxt.ui.client.event.ComponentEvent)
             */
            @Override
            public void componentSelected(ButtonEvent ce) {
                mailsList.getSelectionModel().getSelectedItem().set("mail", emailText.getValue());
                mailsList.refresh();

                emailText.clear();
                addMail.enable();
                modifyMail.disable();
            }

        });

        modifyMail.setToolTip(I18nProvider.getMessages().watchEditMailButtonTooltip());
        modifyMail.setWidth(20);
        modifyMail.setIconStyle("x-georepo-edit");
        modifyMail.disable();

        tableMailAdd.setWidget(1, 0, emailText);
        tableMailAdd.setWidget(1, 1, addMail);
        tableMailAdd.setWidget(1, 2, modifyMail);

        mailSet.add(tableMailAdd);

        notificationFieldSet.add(mailSet, formData);

        this.storeContent = new ListStore<ContentType>();
        this.storeContent.add(GeoRepoWidgetsData.getContentTypes());

        comboContent = new ComboBox<ContentType>();
        comboContent.setFieldLabel(I18nProvider.getMessages().watchContentCombo());
        comboContent.setEmptyText(I18nProvider.getMessages().watchContentComboEmptyText());
        comboContent.setId(ContentTypeEnum.TYPE.getValue());
        comboContent.setName(ContentTypeEnum.TYPE.getValue());
        comboContent.setDisplayField(ContentTypeEnum.TYPE.getValue());
        comboContent.setEditable(false);
        comboContent.setAllowBlank(false);
        comboContent.setForceSelection(true);
        comboContent.setStore(storeContent);
        comboContent.setTypeAhead(true);
        comboContent.setTriggerAction(TriggerAction.ALL);

        notificationFieldSet.add(comboContent, formData);

        messageText = new TextArea();
        messageText.setFieldLabel("Message Text");
        messageText.setWidth(200);
        messageText.setPreventScrollbars(true);

        notificationFieldSet.add(messageText, formData);

        // FlexTable customize = new FlexTable();
        //
        // customize.setCellSpacing(0);
        // customize.setCellPadding(0);
        //
        // customAttrib = new Button("", new SelectionListener<ButtonEvent>() {
        //
        // /* (non-Javadoc)
        // * @see
        // com.extjs.gxt.ui.client.event.SelectionListener#componentSelected(com.extjs.gxt.ui.client.event.ComponentEvent)
        // */
        // @Override
        // public void componentSelected(ButtonEvent ce) {
        //
        // }
        //
        // });
        //
        // customAttrib.setWidth(20);
        // customAttrib.disable();
        //
        // customize.setWidget(1, 1, customizedAttrib);
        // customize.setWidget(1, 2, customAttrib);
        //
        // notificationFieldSet.add(customize);

        this.storeTimes = new ListStore<UpdateInterval>();
        this.storeTimes.add(GeoRepoWidgetsData.getTimes());

        comboTimes = new ComboBox<UpdateInterval>();
        comboTimes.setFieldLabel(I18nProvider.getMessages().watchTimeCombo());
        comboTimes.setEmptyText(I18nProvider.getMessages().watchTimeComboEmptyText());
        comboTimes.setId(UpdateIntervalEnum.TIME.getValue());
        comboTimes.setName(UpdateIntervalEnum.TIME.getValue());
        comboTimes.setDisplayField(UpdateIntervalEnum.TIME.getValue());
        comboTimes.setEditable(false);
        comboTimes.setAllowBlank(false);
        comboTimes.setForceSelection(true);
        comboTimes.setStore(storeTimes);
        comboTimes.setTypeAhead(true);
        comboTimes.setTriggerAction(TriggerAction.ALL);

        notificationFieldSet.add(comboTimes, formData);

        fp.add(notificationFieldSet);

        // ///////////////////////////////////
        // Managing Distribution
        // ///////////////////////////////////

        distributionFieldSet = new FieldSet();
        distributionFieldSet.setHeading(I18nProvider.getMessages().watchDistributionFieldSet());
        distributionFieldSet.setCheckboxToggle(false);
        distributionFieldSet.setCollapsible(false);
        distributionFieldSet.hide();

        FormLayout distributionLayout = new FormLayout();
        distributionFieldSet.setLayout(distributionLayout);

        distStoreContent = new ListStore<DistribContentType>();
        distStoreContent.add(GeoRepoWidgetsData.getDistribContentTypes());

        distComboContent = new ComboBox<DistribContentType>();
        distComboContent.setFieldLabel(I18nProvider.getMessages().watchContentCombo());
        distComboContent.setEmptyText(I18nProvider.getMessages().watchContentComboEmptyText());
        distComboContent.setId(DistribContentTypeEnum.TYPE.getValue());
        distComboContent.setName(DistribContentTypeEnum.TYPE.getValue());
        distComboContent.setDisplayField(DistribContentTypeEnum.TYPE.getValue());
        distComboContent.setEditable(false);
        distComboContent.setAllowBlank(false);
        distComboContent.setForceSelection(true);
        distComboContent.setStore(distStoreContent);
        distComboContent.setTypeAhead(true);
        distComboContent.setTriggerAction(TriggerAction.ALL);

        distributionFieldSet.add(distComboContent, formData);

        distStore = new ListStore<WatchNode>();
        distStore.setStoreSorter(new StoreSorter<WatchNode>());
        distStore.add(getWatchNodes());

        FieldSet nodeFieldSet = new FieldSet();
        nodeFieldSet.setHeading("Available Nodes");
        nodeFieldSet.setCheckboxToggle(false);
        nodeFieldSet.setCollapsible(false);

        FormLayout nodeFieldSetLayout = new FormLayout();
        nodeFieldSet.setLayout(nodeFieldSetLayout);

        nodeList = new CheckBoxListView<WatchNode>();
        nodeList.setDisplayProperty("name");
        nodeList.setHeight(90);
        nodeList.setWidth(250);
        nodeList.setStore(distStore);

        nodeFieldSet.add(nodeList);
        distributionFieldSet.add(nodeFieldSet, formData);

        distStoreTimes = new ListStore<DistribUpdateInterval>();
        distStoreTimes.add(GeoRepoWidgetsData.getDistribTimes());

        distComboTimes = new ComboBox<DistribUpdateInterval>();
        distComboTimes.setFieldLabel(I18nProvider.getMessages().watchTimeCombo());
        distComboTimes.setEmptyText(I18nProvider.getMessages().watchTimeComboEmptyText());
        distComboTimes.setId(DistribUpdateIntervalEnum.TIME.getValue());
        distComboTimes.setName(DistribUpdateIntervalEnum.TIME.getValue());
        distComboTimes.setDisplayField(DistribUpdateIntervalEnum.TIME.getValue());
        distComboTimes.setEditable(false);
        distComboTimes.setAllowBlank(false);
        distComboTimes.setForceSelection(true);
        distComboTimes.setStore(distStoreTimes);
        distComboTimes.setTypeAhead(true);
        distComboTimes.setTriggerAction(TriggerAction.ALL);

        distributionFieldSet.add(distComboTimes, formData);

        fp.add(distributionFieldSet);

        // //////////////////////////
        // Toolbar buttons
        // //////////////////////////

        FlexTable table = new FlexTable();
        table.getElement().getStyle().setProperty("marginLeft", "60px");

        table.setCellSpacing(8);
        table.setCellPadding(4);

        trigger = new Button(I18nProvider.getMessages().watchTriggerButton(),
                new SelectionListener<ButtonEvent>() {

                    /*
                     * (non-Javadoc)
                     * 
                     * @see com.extjs.gxt.ui.client.event.SelectionListener#componentSelected
                     * (com.extjs.gxt.ui.client.event.ComponentEvent)
                     */
                    @Override
                    public void componentSelected(ButtonEvent ce) {
                        Dispatcher.forwardEvent(GeoRepoEvents.RUN_WATCH, watchModel);
                    }
                });

        trigger.setIcon(Resources.ICONS.trigger());
        trigger.setWidth(100);
        trigger.disable();

        table.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_CENTER);

        table.setWidget(1, 1, trigger);

        clear = new Button(I18nProvider.getMessages().watchCleanButton(),
                new SelectionListener<ButtonEvent>() {

                    /*
                     * (non-Javadoc)
                     * 
                     * @see com.extjs.gxt.ui.client.event.SelectionListener#componentSelected
                     * (com.extjs.gxt.ui.client.event.ComponentEvent)
                     */
                    @Override
                    public void componentSelected(ButtonEvent ce) {
                        Dispatcher.forwardEvent(GeoRepoEvents.UNBINDING_WATCH_WIDGET);
                    }
                });

        clear.setIcon(Resources.ICONS.cleanDgWatchMenu());
        clear.setWidth(100);
        clear.disable();

        table.getCellFormatter().setHorizontalAlignment(1, 2, HasHorizontalAlignment.ALIGN_CENTER);

        table.setWidget(1, 2, clear);

        fp.add(table);

        return fp;
    }

    /**
     * Enable buttons.
     */
    public void enableButtons() {
        this.clear.enable();
        this.trigger.enable();
    }

    /**
     * Disable buttons.
     */
    public void disableButtons() {
        this.clear.disable();
        this.trigger.disable();

        this.mailSet.disable();
    }

    /**
     * Gets the mails list.
     * 
     * @return the mails list
     */
    public ListView<WatchMail> getMailsList() {
        return mailsList;
    }

    /**
     * Gets the node list.
     * 
     * @return the node list
     */
    public CheckBoxListView<WatchNode> getNodeList() {
        return nodeList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHBindingWidget#unBindModel ()
     */
    @Override
    public void unBindModel() {

        ListView<WatchMail> listMail = getMailsList();
        if (listMail != null) {
            if (listMail.getStore().getCount() > 0) {
                listMail.getStore().removeAll();
                listMail.refresh();
            }
        }

        CheckBoxListView<WatchNode> listNode = getNodeList();
        if (listNode != null) {
            if (listNode.getChecked().size() > 0)
                listNode.refresh();
        }

        if (watchModel != null) {

            title.reset();
            emailText.reset();
            beginDate.reset();
            expiration.reset();

            comboTypes.removeAllListeners();
            comboTypes.reset();

            comboRetrieval.reset();
            comboContent.reset();
            messageText.reset();
            comboTimes.reset();
            distComboContent.reset();
            distComboTimes.reset();

            setSelected(false);
            this.radioGroup.enable();
            disableButtons();

            watchModel = null;

            comboTypes.addListener(Events.SelectionChange, new Listener<BaseEvent>() {

                /*
                 * (non-Javadoc)
                 * 
                 * @see com.extjs.gxt.ui.client.event.Listener#handleEvent(com.extjs
                 * .gxt.ui.client.event.BaseEvent)
                 */
                public void handleEvent(BaseEvent be) {
                    String type = comboTypes.getValue().getType();

                    if (type.equalsIgnoreCase("Email") || type.equalsIgnoreCase("EMailAndRSS"))
                        mailSet.enable();
                    else
                        mailSet.disable();
                }

            });

            Dispatcher.forwardEvent(GeoRepoEvents.UNBIND_FILTER_WIDGET);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHFormBindingWidget#bindModel
     * (com.extjs.gxt.ui.client.data.BaseModel)
     */
    @Override
    public void bindModel(Watch watch) {

        watchModel = new Watch();
        watchModel.setActionId(watch.getActionId());
        watchModel.setFilter(watch.getFilter());
        watchModel.setId(watch.getId());
        watchModel.setMember(watch.getMember());
        watchModel.setAoiId(watch.getAoiId());

        title.setValue(watch.getTitle());
        watchModel.setTitle(watch.getTitle());

        if (watch.getBeginDate() != null) {
            beginDate.setValue(watch.getBeginDate());
            watchModel.setBeginDate(watch.getBeginDate());
        }

        if (watch.getExpirationDate() != null) {
            expiration.setValue(watch.getExpirationDate());
            watchModel.setExpirationDate(watch.getExpirationDate());
        }

        if (watch.isNotification()) {

            comboTypes.setValue(new SendType(watch.getSendType()));
            watchModel.setSendType(watch.getSendType());

            comboRetrieval.setValue(new RetrievalType(watch.getRetrievalType()));
            watchModel.setRetrievalType(watch.getRetrievalType());

            comboContent.setValue(new ContentType(watch.getContentType()));
            watchModel.setContentType(watch.getContentType());

            messageText.setValue(watch.getMailContent());
            watchModel.setMailContent(watch.getMailContent());

            comboTimes.setValue(new UpdateInterval(watch.getUpInteval()));
            watchModel.setUpInteval(watch.getUpInteval());

            this.radioGroup.setValue(notification);
            this.notificationFieldSet.show();
            this.distributionFieldSet.hide();
            watchModel.setNotification(true);

        } else {

            distComboContent.setValue(new DistribContentType(watch.getDistContentType()));
            watchModel.setDistContentType(watch.getDistContentType());

            distComboTimes.setValue(new DistribUpdateInterval(watch.getDistUpInterval()));
            watchModel.setDistUpInterval(watch.getDistUpInterval());

            this.radioGroup.setValue(distribution);
            this.notificationFieldSet.hide();
            this.distributionFieldSet.show();
            watchModel.setNotification(false);
        }

        if (watch.isNotification()) {
            if (watch.getSendType().equalsIgnoreCase("Email")
                    || watch.getSendType().equalsIgnoreCase("EMailAndRSS")) {

                List<WatchMail> mails = new ArrayList<WatchMail>();
                List<String> watchMailList = watch.getWatchMail();

                if (watchMailList != null) {
                    for (int y = 0; y < watchMailList.size(); y++) {
                        WatchMail mail = new WatchMail(watchMailList.get(y));
                        mails.add(mail);
                    }

                    getMailsList().getStore().add(mails);
                    getMailsList().refresh();
                }
            }

        } else {
            Map<Long, String> mapNodes = watch.getNodeMap();

            ListStore<WatchNode> store = getNodeList().getStore();

            for (int k = 0; k < store.getCount(); k++) {
                WatchNode node = store.getAt(k);

                if (mapNodes.containsKey(node.getId()))
                    getNodeList().setChecked(node, true);
            }
        }

        setSelected(true);
        this.radioGroup.disable();
        enableButtons();

        if (watch.getFilter() != null)
            Dispatcher.forwardEvent(GeoRepoEvents.EXIST_DEFAULT_FILTER, watch.getFilter());
        else {
            Dispatcher.forwardEvent(GeoRepoEvents.UPDATE_TITLE);
        }
    }

    /**
     * Checks if is selected.
     * 
     * @return true, if is selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selected.
     * 
     * @param selected
     *            the new selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Check validation.
     * 
     * @return true, if successful
     */
    public boolean checkValidation() {

        if (radioGroup.getValue().getBoxLabel().equalsIgnoreCase("Notification")) {
            if (title.getValue() != null && comboTypes.getValue() != null
                    && comboContent.getValue() != null && comboRetrieval.getValue() != null
                    && comboTimes.getValue() != null)
                return true;
            else
                return false;
        } else {
            if (distComboContent.getValue() != null && distComboTimes.getValue() != null)
                return true;
            else
                return false;
        }
    }

    /**
     * Gets the model data.
     * 
     * @return the model data
     */
    public Watch getModelData() {
        Watch watch = new Watch();

        if (watchModel != null && isSelected()) {
            watch.setId(watchModel.getId());
            watch.setActionId(watchModel.getActionId());
        }

        watch.setTitle(title.getValue());

        if (beginDate.getValue() != null)
            watch.setBeginDate(beginDate.getValue());

        if (expiration.getValue() != null)
            watch.setExpirationDate(expiration.getValue());

        if (radioGroup.getValue().getBoxLabel().equalsIgnoreCase("Distribution")) {
            watch.setNotification(false);

            watch.setDistContentType(distComboContent.getValue().getType());
            watch.setDistUpInterval(distComboTimes.getValue().getTime());

            List<WatchNode> nodes = nodeList.getChecked();

            if (nodes != null) {
                Map<Long, String> watchNodesMap = new HashMap<Long, String>();

                for (int y = 0; y < nodes.size(); y++) {
                    Long id = new Long(nodes.get(y).getId());
                    String name = new String(nodes.get(y).getName());
                    watchNodesMap.put(id, name);
                }

                watch.setNodeMap(watchNodesMap);
            }

        } else if (radioGroup.getValue().getBoxLabel().equalsIgnoreCase("Notification")) {
            watch.setNotification(true);

            watch.setSendType(comboTypes.getValue().getType());

            if (comboTypes.getValue().getType().equalsIgnoreCase("Email")
                    || comboTypes.getValue().getType().equalsIgnoreCase("EMailAndRSS")) {
                List<WatchMail> mails = getMailsList().getStore().getModels();

                if (mails != null) {
                    List<String> watchMailList = new ArrayList<String>();
                    for (int y = 0; y < mails.size(); y++) {
                        String mail = new String(mails.get(y).getMail());
                        watchMailList.add(mail);
                    }
                    watch.setWatchMail(watchMailList);
                }
            }

            watch.setContentType(comboContent.getValue().getType());
            watch.setMailContent(messageText.getValue());
            watch.setRetrievalType(comboRetrieval.getValue().getType());
            watch.setUpInteval(comboTimes.getValue().getTime());
        }

        return watch;
    }

    /**
     * Gets the watch nodes.
     * 
     * @return the watch nodes
     */
    private static List<WatchNode> getWatchNodes() {
        List<WatchNode> nodes = new ArrayList<WatchNode>();

        // TODO: Hook up to server API
        nodes.add(new WatchNode("REMOTE_INTEGRATION", Long.valueOf(15000)));

        return nodes;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.geosolutions.georepo.gui.client.widget.DGWATCHFormBindingWidget#injectSecurity(java.util
     * .List)
     */
    @Override
    public void injectSecurity(List<Authorization> auths) {
        if (!auths.contains(Authorization.DISTRIBUTION)) {
            this.distributionFieldSet.disable();
            this.distribution.disable();

            // if notification is enabled, select it
            if (this.notification.isEnabled()) {
                this.radioGroup.setValue(notification);
            }
        }

        if (!auths.contains(Authorization.NOTIFICATION)) {
            this.mailSet.disable();
            this.notification.disable();

            // if distribution is enabled, select it
            if (this.distribution.isEnabled()) {
                this.radioGroup.setValue(distribution);
            }
        }

        // if 1 of the 2 radio buttons is disabled, there's no need to show the
        // radio buttons
        // at all - the user will never be able to use the control, the type of
        // watch being entered is clearly
        // denoted on the FieldSet, and it sames on screen real estate. Plus,
        // there seemed to be some
        // sort of UI bug in EXT GWT where the the Distribution radio button
        // would sometimes disappear if it was
        // disabled - hiding and re-showing the panel would make it appear
        // again, so it definitely is some
        // sort of UI framework bug. Hiding the radio buttons all together works
        // around this issue as well.
        if (!auths.contains(Authorization.DISTRIBUTION)
                || !auths.contains(Authorization.NOTIFICATION)) {
            // this.radioGroup.setVisible(false);
            this.radioTable.setVisible(false);
        }

    }

}
