/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.AOIUserPrefContainer,v. 0.1 3-gen-2011 17.06.54 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.service.LoginRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.SearchStatus.EnumSearchStatus;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.event.WindowListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;

// TODO: Auto-generated Javadoc
/**
 * The Class AOIUserPrefContainer.
 */
public class AOIUserPrefContainer extends Window {

    /** The vp. */
    private VerticalPanel vp;

    /** The form panel. */
    private FormPanel formPanel;

    /** The aoi user pref. */
    private AOIUserPrefWidget aoiUserPref;

    /** The search status. */
    private SearchStatus searchStatus;

    /** The cancel. */
    private Button cancel;

    /**
     * Instantiates a new aOI user pref container.
     * 
     * @param service
     *            the service
     */
    public AOIUserPrefContainer(LoginRemoteAsync service) {
        this.aoiUserPref = new AOIUserPrefWidget(service, this);
        initWindow();
        initVerticalPanel();
        initFormPanel();
        add(vp);
    }

    /**
     * Inits the window.
     */
    private void initWindow() {
        setHeading("Search for Registered Member");
        super.setSize(450, 420);

        super.addWindowListener(new WindowListener() {

            @Override
            public void windowShow(WindowEvent we) {
                aoiUserPref.getLoader().load(0, 25);
            }

        });

        setModal(true);
        setResizable(false);
        setLayout(new FlowLayout());
        setPlain(true);
        setMaximizable(false);

        addWindowListener(new WindowListener() {

            @Override
            public void windowHide(WindowEvent we) {
                reset();
                deleteAOI();
            }

        });
    }

    /**
     * Inits the vertical panel.
     */
    private void initVerticalPanel() {
        vp = new VerticalPanel();
        vp.setSpacing(10);
    }

    /**
     * Inits the form panel.
     */
    private void initFormPanel() {
        formPanel = new FormPanel();
        formPanel.setHeaderVisible(false);
        formPanel.setFrame(true);
        formPanel.setLayout(new FlowLayout());

        formPanel.add(this.aoiUserPref.getGrid());

        this.searchStatus = new SearchStatus();
        searchStatus.setAutoWidth(true);

        formPanel.getButtonBar().add(this.searchStatus);

        formPanel.getButtonBar().add(new LabelToolItem("    "));

        formPanel.setButtonAlign(HorizontalAlignment.RIGHT);

        cancel = new Button("Cancel", new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                cancel();
            }
        });

        cancel.setIconStyle("x-georepo-cancel");

        formPanel.addButton(cancel);

        formPanel.setBottomComponent(this.aoiUserPref.getToolBar());

        vp.add(formPanel);
    }

    /**
     * Delete aoi.
     */
    private void deleteAOI() {
        MessageBox.confirm("Delete AOI", "Are you sure to delete AOI "
                + aoiUserPref.getAoi().getTitle() + " ?", new Listener<MessageBoxEvent>() {

            public void handleEvent(MessageBoxEvent be) {
                Button btn = be.getButtonClicked();
                if (btn.getText().equalsIgnoreCase("YES"))
                    Dispatcher.forwardEvent(GeoRepoEvents.DELETE_AOI, aoiUserPref.getAoi());
            }
        });

    }

    /**
     * Sets the busy.
     * 
     * @param message
     *            the new busy
     */
    public void setBusy(String message) {
        this.searchStatus.setBusy(message);
    }

    /**
     * Cancel.
     */
    @SuppressWarnings("deprecation")
    public void cancel() {
        super.close();
        reset();
    }

    /**
     * Reset.
     */
    private void reset() {
        this.aoiUserPref.clearGridElements();
        this.searchStatus.clearStatus("");
    }

    /**
     * Sets the search status.
     * 
     * @param status
     *            the status
     * @param message
     *            the message
     */
    public void setSearchStatus(EnumSearchStatus status, EnumSearchStatus message) {
        this.searchStatus.setIconStyle(status.getValue());
        this.searchStatus.setText(message.getValue());
    }

    /**
     * Gets the aoi user pref.
     * 
     * @return the aoi user pref
     */
    public AOIUserPrefWidget getAoiUserPref() {
        return aoiUserPref;
    }

}
