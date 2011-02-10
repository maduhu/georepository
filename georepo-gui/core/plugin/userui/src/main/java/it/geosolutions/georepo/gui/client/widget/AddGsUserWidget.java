/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.AddGsUserWidget,v. 0.1 10-feb-2011 12.01.49 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 10-feb-2011 12.01.49 $
 *
 * ====================================================================
 *
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 * http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
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

import it.geosolutions.georepo.gui.client.form.GeoRepoFormWidget;
import it.geosolutions.georepo.gui.client.model.BeanKeyValue;
import it.geosolutions.georepo.gui.client.model.GSUser;
import it.geosolutions.georepo.gui.client.model.Profile;
import it.geosolutions.georepo.gui.client.service.GsUsersManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemoteAsync;

import java.util.Date;

import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class AddGsUserWidget.
 */
public class AddGsUserWidget extends GeoRepoFormWidget {

    /** The submit event. */
    private EventType submitEvent;

    /** The close on submit. */
    private boolean closeOnSubmit;

    /** The user. */
    protected GSUser user = new GSUser();

    private TextField<String> userName;

    private TextField<String> password;

    private TextField<String> fullName;

    private TextField<String> eMail;

    private ComboBox<Profile> profilesComboBox;

    private GsUsersManagerServiceRemoteAsync gsManagerServiceRemote;

    private ProfilesManagerServiceRemoteAsync profilesManagerServiceRemote;

    /**
     * Instantiates a new adds the gs user widget.
     * 
     * @param submitEvent
     *            the submit event
     * @param closeOnSubmit
     *            the close on submit
     */
    public AddGsUserWidget(EventType submitEvent, boolean closeOnSubmit) {
        this.submitEvent = submitEvent;
        this.closeOnSubmit = closeOnSubmit;
    }

    /**
     * Gets the submit event.
     * 
     * @return the submit event
     */
    protected EventType getSubmitEvent() {
        return this.submitEvent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.form.IForm#execute()
     */
    public void execute() {
        this.saveStatus.setBusy("Operation in progress");
        this.user.setId(-1);
        this.user.setName(userName.getValue());
        this.user.setPassword(password.getValue());
        this.user.setFullName(fullName.getValue());
        this.user.setEmailAddress(eMail.getValue());
        this.user.setDateCreation(new Date());
        this.user.setEnabled(true);
        this.user.setProfile(profilesComboBox.getValue());

        if (this.closeOnSubmit) {
            cancel();
        }

        this.injectEvent();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.digitalglobe.dgwatch.gui.client.form.DGWATCHFormWidget#addComponentToForm ()
     */
    @Override
    public void addComponentToForm() {
        fieldSet = new FieldSet();
        fieldSet.setHeading("User Information");
        FormLayout layout = new FormLayout();
        layout.setLabelWidth(80);
        fieldSet.setLayout(layout);

        userName = new TextField<String>();
        userName.setAllowBlank(false);
        userName.setFieldLabel("User name");
        fieldSet.add(userName);

        password = new TextField<String>();
        password.setPassword(true);
        password.setAllowBlank(false);
        password.setFieldLabel("Password");
        fieldSet.add(password);

        fullName = new TextField<String>();
        fullName.setAllowBlank(false);
        fullName.setFieldLabel("Full name");
        fieldSet.add(fullName);

        eMail = new TextField<String>();
        eMail.setAllowBlank(false);
        eMail.setFieldLabel("e-mail");
        fieldSet.add(eMail);

        createProfilesComboBox();
        
        this.formPanel.add(fieldSet);

        addOtherComponents();
    }

    /**
     * 
     */
    private void createProfilesComboBox() {
        profilesComboBox = new ComboBox<Profile>();
        profilesComboBox.setFieldLabel("Profile");
        profilesComboBox.setEmptyText("(No profile available)");
        profilesComboBox.setDisplayField(BeanKeyValue.NAME.getValue());
        profilesComboBox.setEditable(false);
        profilesComboBox.setStore(getAvailableProfiles());
        profilesComboBox.setTypeAhead(true);
        profilesComboBox.setTriggerAction(TriggerAction.ALL);
        profilesComboBox.setAllowBlank(false);
        profilesComboBox.setLazyRender(false);
        // profilesComboBox.setWidth(150);
        
        profilesComboBox.addListener(Events.TriggerClick, new Listener<FieldEvent>() {

            public void handleEvent(FieldEvent be) {
                profilesComboBox.getStore().getLoader().load();
            }
        });
        
        fieldSet.add(profilesComboBox);
    }

    /**
     * TODO: Call Profile Service here!!
     * 
     * @return
     */
    private ListStore<Profile> getAvailableProfiles() {
        ListStore<Profile> availableProfiles = new ListStore<Profile>();
        RpcProxy<PagingLoadResult<Profile>> profileProxy = new RpcProxy<PagingLoadResult<Profile>>() {

            @Override
            protected void load(Object loadConfig, AsyncCallback<PagingLoadResult<Profile>> callback) {
                profilesManagerServiceRemote.getProfiles((PagingLoadConfig) loadConfig, false,
                        callback);
            }

        };
        BasePagingLoader<PagingLoadResult<ModelData>> profilesLoader = new BasePagingLoader<PagingLoadResult<ModelData>>(
                profileProxy);
        profilesLoader.setRemoteSort(false);
        availableProfiles = new ListStore<Profile>(profilesLoader);

        return availableProfiles;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.digitalglobe.dgwatch.gui.client.form.DGWATCHFormWidget#cancel()
     */
    @SuppressWarnings("deprecation")
    @Override
    public void cancel() {
        resetComponents();
        super.close();

    }

    /**
     * Reset components.
     */
    public void resetComponents() {
        this.userName.reset();
        this.password.reset();
        this.fullName.reset();
        this.eMail.reset();
        this.profilesComboBox.reset();
        this.profilesComboBox.getStore().getLoader().load();
        this.saveStatus.clearStatus("");
        // Dispatcher.forwardEvent(DGWATCHEvents.DISABLE_DRAW_BUTTON);
        // Dispatcher.forwardEvent(DGWATCHEvents.ERASE_AOI_FEATURES);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.digitalglobe.dgwatch.gui.client.widget.AddGenericAOIWidget# addOtherComponents()
     */
    public void addOtherComponents() {
        // wktArea = new TextArea();
        // wktArea.setFieldLabel(I18nProvider.getMessages().wktAbbreviation());
        // wktArea.setAllowBlank(false);
        // fieldSet.add(wktArea);
        //
        // draw = new Button(I18nProvider.getMessages().drawAoiButton(),
        // new SelectionListener<ButtonEvent>() {
        //
        // @Override
        // public void componentSelected(ButtonEvent ce) {
        // hide();
        // Dispatcher
        // .forwardEvent(DGWATCHEvents.ENABLE_DRAW_BUTTON, AddAOIWidget.this);
        // }
        // });
        //
        // draw.setIcon(Resources.ICONS.drawFeature());
        //
        // this.formPanel.addButton(draw);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.digitalglobe.dgwatch.gui.client.form.DGWATCHFormWidget#initSize()
     */
    @Override
    public void initSize() {
        setHeading(/* TODO: I18nProvider.getMessages().addAoiDialogTitle() */"Create new User");
        setSize(420, 300);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.digitalglobe.dgwatch.gui.client.form.DGWATCHFormWidget#initSizeFormPanel ()
     */
    @Override
    public void initSizeFormPanel() {
        formPanel.setHeaderVisible(false);
        formPanel.setSize(450, 350);
    }

    @Override
    public void injectEvent() {
        Dispatcher.forwardEvent(getSubmitEvent(), this.user);
    }

    /**
     * Gets the user.
     * 
     * @return the user
     */
    public GSUser getUser() {
        return user;
    }

    public void setGsUserService(GsUsersManagerServiceRemoteAsync gsManagerServiceRemote) {
        this.gsManagerServiceRemote = gsManagerServiceRemote;
    }

    public void setProfileService(ProfilesManagerServiceRemoteAsync profilesManagerServiceRemote) {
        this.profilesManagerServiceRemote = profilesManagerServiceRemote;
    }
    
}
