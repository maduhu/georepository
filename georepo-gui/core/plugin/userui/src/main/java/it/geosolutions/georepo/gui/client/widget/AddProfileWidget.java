/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.AddProfileWidget,v. 0.1 25-feb-2011 16.31.40 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-feb-2011 16.31.40 $
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
import it.geosolutions.georepo.gui.client.model.Profile;
import it.geosolutions.georepo.gui.client.service.GsUsersManagerRemoteServiceAsync;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerRemoteServiceAsync;


// TODO: Auto-generated Javadoc
/**
 * The Class AddProfileWidget.
 */
public class AddProfileWidget extends GeoRepoFormWidget
{

    /** The submit event. */
    private EventType submitEvent;

    /** The close on submit. */
    private boolean closeOnSubmit;

    /** The profile. */
    protected Profile profile = new Profile();

    /** The gs manager service remote. */
    private GsUsersManagerRemoteServiceAsync gsManagerServiceRemote;

    /** The profiles manager service remote. */
    private ProfilesManagerRemoteServiceAsync profilesManagerServiceRemote;

    /** The profile name. */
    private TextField<String> profileName;

    /**
     * Instantiates a new adds the profile widget.
     *
     * @param submitEvent
     *            the submit event
     * @param closeOnSubmit
     *            the close on submit
     */
    public AddProfileWidget(EventType submitEvent, boolean closeOnSubmit)
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
        this.profile.setId(-1);
        this.profile.setName(profileName.getValue());
        this.profile.setDateCreation(new Date());
        this.profile.setEnabled(true);

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
        fieldSet.setHeading("Profile Information");

        FormLayout layout = new FormLayout();
        layout.setLabelWidth(80);
        fieldSet.setLayout(layout);

        profileName = new TextField<String>();
        profileName.setAllowBlank(false);
        profileName.setFieldLabel("Profile name");
        fieldSet.add(profileName);

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
        this.profileName.reset();
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
        setHeading( /* TODO: I18nProvider.getMessages().addAoiDialogTitle() */"Create new Profile");
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
        Dispatcher.forwardEvent(getSubmitEvent(), this.profile);
    }

    /**
     * Gets the profile.
     *
     * @return the profile
     */
    public Profile getProfile()
    {
        return profile;
    }

    /**
     * Sets the gs user service.
     *
     * @param gsManagerServiceRemote
     *            the new gs user service
     */
    public void setGsUserService(GsUsersManagerRemoteServiceAsync gsManagerServiceRemote)
    {
        this.gsManagerServiceRemote = gsManagerServiceRemote;
    }

    /**
     * Sets the profile service.
     *
     * @param profilesManagerServiceRemote
     *            the new profile service
     */
    public void setProfileService(ProfilesManagerRemoteServiceAsync profilesManagerServiceRemote)
    {
        this.profilesManagerServiceRemote = profilesManagerServiceRemote;
    }

}
