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
import it.geosolutions.georepo.gui.client.model.GSInstance;
import it.geosolutions.georepo.gui.client.service.GsUsersManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.InstancesManagerServiceRemoteAsync;

import java.util.Date;

import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class AddGsInstanceWidget.
 */
public class AddInstanceWidget extends GeoRepoFormWidget {

    /** The submit event. */
    private EventType submitEvent;

    /** The close on submit. */
    private boolean closeOnSubmit;

    /** The instance. */
    protected GSInstance instance = new GSInstance();

    private InstancesManagerServiceRemoteAsync instancesManagerServiceRemote;

    private TextField<String> instanceName;
    
    private TextField<String> instanceDescription;
    
    private TextField<String> instanceBaseURL;

    private TextField<String> instanceUsername;
    
    private TextField<String> instancePassword;
    /**
     * Instantiates a new adds the gs instance widget.
     * 
     * @param submitEvent
     *            the submit event
     * @param closeOnSubmit
     *            the close on submit
     */
    public AddInstanceWidget(EventType submitEvent, boolean closeOnSubmit) {
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
        this.instance.setId(-1);
        this.instance.setName(instanceName.getValue());
        this.instance.setDateCreation(new Date());
        this.instance.setDescription(instanceDescription.getValue());
        this.instance.setBaseURL(instanceBaseURL.getValue());
        this.instance.setUsername(instanceUsername.getValue());
        this.instance.setPassword(instancePassword.getValue());
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
        fieldSet.setHeading("Instance Information");
        FormLayout layout = new FormLayout();
        layout.setLabelWidth(80);
        fieldSet.setLayout(layout);

        instanceName = new TextField<String>();
        instanceName.setAllowBlank(false);
        instanceName.setFieldLabel("name");
        fieldSet.add(instanceName);

        instanceDescription = new TextField<String>();
        instanceDescription.setAllowBlank(false);
        instanceDescription.setFieldLabel("description");
        fieldSet.add(instanceDescription);

        instanceBaseURL = new TextField<String>();
        instanceBaseURL.setAllowBlank(false);
        instanceBaseURL.setFieldLabel("base url");
        fieldSet.add(instanceBaseURL);
        
        instanceUsername = new TextField<String>();
        instanceUsername.setAllowBlank(false);
        instanceUsername.setFieldLabel("username");
        fieldSet.add(instanceUsername);
        
        instancePassword = new TextField<String>();
        instancePassword.setAllowBlank(false);
        instancePassword.setFieldLabel("password");
        fieldSet.add(instancePassword);
        
        this.formPanel.add(fieldSet);
        
        addOtherComponents();
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
        this.instanceName.reset();
        this.instanceDescription.reset();
        this.instanceBaseURL.reset();
        this.instanceUsername.reset();
        this.instancePassword.reset();
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
        setHeading(/* TODO: I18nProvider.getMessages().addAoiDialogTitle() */"Create new Instance");
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
        Dispatcher.forwardEvent(getSubmitEvent(), this.instance);
    }

    /**
     * Gets the instance.
     * 
     * @return the instance
     */
    public GSInstance getInstance() {
        return instance;
    }

    /*public void setGsUserService(GsUsersManagerServiceRemoteAsync gsManagerServiceRemote) {
        this.gsManagerServiceRemote = gsManagerServiceRemote;
    }*/

    public void setInstanceService(InstancesManagerServiceRemoteAsync instancesManagerServiceRemote) {
        this.instancesManagerServiceRemote = instancesManagerServiceRemote;
    }
    
}