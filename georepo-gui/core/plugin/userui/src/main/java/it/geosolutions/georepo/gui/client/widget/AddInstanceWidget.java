/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.AddInstanceWidget,v. 0.1 25-feb-2011 16.31.41 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-feb-2011 16.31.41 $
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

import java.util.Date;

import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

import it.geosolutions.georepo.gui.client.form.GeoRepoFormWidget;
import it.geosolutions.georepo.gui.client.model.GSInstance;
import it.geosolutions.georepo.gui.client.service.InstancesManagerRemoteServiceAsync;


// TODO: Auto-generated Javadoc
/**
 * The Class AddInstanceWidget.
 */
public class AddInstanceWidget extends GeoRepoFormWidget
{

    /** The submit event. */
    private EventType submitEvent;

    /** The close on submit. */
    private boolean closeOnSubmit;

    /** The instance. */
    protected GSInstance instance = new GSInstance();

    /** The instances manager service remote. */
    private InstancesManagerRemoteServiceAsync instancesManagerServiceRemote;

    /** The instance name. */
    private TextField<String> instanceName;

    /** The instance description. */
    private TextField<String> instanceDescription;

    /** The instance base url. */
    private TextField<String> instanceBaseURL;

    /** The instance username. */
    private TextField<String> instanceUsername;

    /** The instance password. */
    private TextField<String> instancePassword;

    /**
     * Instantiates a new adds the instance widget.
     *
     * @param submitEvent
     *            the submit event
     * @param closeOnSubmit
     *            the close on submit
     */
    public AddInstanceWidget(EventType submitEvent, boolean closeOnSubmit)
    {
        this.submitEvent = submitEvent;
        this.closeOnSubmit = closeOnSubmit;
    }

    /**
     * Gets the submit event.
     *
     * @return the submit event
     */
    protected EventType getSubmitEvent()
    {
        return this.submitEvent;
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.georepo.gui.client.form.IForm#execute()
     */
    public void execute()
    {
        this.saveStatus.setBusy("Operation in progress");
        this.instance.setId(-1);
        this.instance.setName(instanceName.getValue());
        this.instance.setDateCreation(new Date());
        this.instance.setDescription(instanceDescription.getValue());
        this.instance.setBaseURL(instanceBaseURL.getValue());
        this.instance.setUsername(instanceUsername.getValue());
        this.instance.setPassword(instancePassword.getValue());
        if (this.closeOnSubmit)
        {
            cancel();
        }

        this.injectEvent();
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.georepo.gui.client.form.GeoRepoFormWidget#addComponentToForm ()
     */
    @Override
    public void addComponentToForm()
    {
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
        instancePassword.setPassword(true);
        instancePassword.setFieldLabel("password");
        fieldSet.add(instancePassword);

        this.formPanel.add(fieldSet);

        addOtherComponents();
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.georepo.gui.client.form.GeoRepoFormWidget#cancel()
     */
    @SuppressWarnings("deprecation")
    @Override
    public void cancel()
    {
        resetComponents();
        super.close();

    }

    /**
     * Reset components.
     */
    public void resetComponents()
    {
        this.instanceName.reset();
        this.instanceDescription.reset();
        this.instanceBaseURL.reset();
        this.instanceUsername.reset();
        this.instancePassword.reset();
        this.saveStatus.clearStatus("");
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.georepo.gui.client.widget.AddGenericAOIWidget# addOtherComponents()
     */
    /**
     * Adds the other components.
     */
    public void addOtherComponents()
    {
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.georepo.gui.client.form.GeoRepoFormWidget#initSize()
     */
    @Override
    public void initSize()
    {
        setHeading( /* TODO: I18nProvider.getMessages().addAoiDialogTitle() */"Create new Instance");
        setSize(420, 300);
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.georepo.gui.client.form.GeoRepoFormWidget#initSizeFormPanel ()
     */
    @Override
    public void initSizeFormPanel()
    {
        formPanel.setHeaderVisible(false);
        formPanel.setSize(450, 350);
    }

    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.client.form.GeoRepoFormWidget#injectEvent()
     */
    @Override
    public void injectEvent()
    {
        Dispatcher.forwardEvent(getSubmitEvent(), this.instance);
    }

    /**
     * Gets the instance.
     *
     * @return the instance
     */
    public GSInstance getInstance()
    {
        return instance;
    }

    /*public void setGsUserService(GsUsersManagerServiceRemoteAsync gsManagerServiceRemote) {
        this.gsManagerServiceRemote = gsManagerServiceRemote;
    }*/

    /**
     * Sets the instance service.
     *
     * @param instancesManagerServiceRemote
     *            the new instance service
     */
    public void setInstanceService(InstancesManagerRemoteServiceAsync instancesManagerServiceRemote)
    {
        this.instancesManagerServiceRemote = instancesManagerServiceRemote;
    }

}
