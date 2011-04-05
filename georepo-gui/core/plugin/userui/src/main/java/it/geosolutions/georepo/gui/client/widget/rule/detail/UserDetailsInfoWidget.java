/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.rule.detail.UserDetailsInfoWidget,v. 0.1 5-apr-2011 16.30.38 created by tdipisa <tobia.dipisa at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 5-apr-2011 16.30.38 $
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
package it.geosolutions.georepo.gui.client.widget.rule.detail;

import it.geosolutions.georepo.gui.client.model.GSUser;
import it.geosolutions.georepo.gui.client.model.data.UserLimitsInfo;
import it.geosolutions.georepo.gui.client.service.GsUsersManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.GeoRepoFormBindingWidget;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

/**
 * The Class UserDetailsInfoWidget.
 */
public class UserDetailsInfoWidget extends GeoRepoFormBindingWidget<UserLimitsInfo> {

    /** The user. */
    private GSUser user;

    /** The user service. */
    private GsUsersManagerServiceRemoteAsync usersService;

    /** The user details widget. */
    private UserDetailsWidget userDetailsWidget;

    /** The allowed area. */
    private TextArea allowedArea;

    /**
     * Instantiates a new rule details info widget.
     * 
     * @param model
     *            the model
     * @param workspacesService
     *            the workspaces service
     * @param ruleDetailsWidget
     *            the rule details widget
     */
    public UserDetailsInfoWidget(GSUser model, GsUsersManagerServiceRemoteAsync usersService,
            UserDetailsWidget userDetailsWidget) {

        super();
        this.user = model;
        this.usersService = usersService;
        this.userDetailsWidget = userDetailsWidget;
        formPanel = createFormPanel();
    }

    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.client.widget.GeoRepoFormBindingWidget#createFormPanel()
     */
    @Override
    public FormPanel createFormPanel() {
        FormPanel fp = new FormPanel();
        fp.setFrame(true);
        fp.setHeaderVisible(false);
        fp.setHeight(400);
        fp.setWidth(650);

        FieldSet fieldSet = new FieldSet();
        fieldSet.setHeading("User Limits");
        fieldSet.setCheckboxToggle(false);
        fieldSet.setCollapsible(false);

        FormLayout layout = new FormLayout();
        fieldSet.setLayout(layout);

        allowedArea = new TextArea();
        allowedArea.setFieldLabel("Allowed Area");
        allowedArea.setWidth(400);
        allowedArea.setPreventScrollbars(true);
        allowedArea.addListener(Events.Change, new Listener<FieldEvent>() {

            public void handleEvent(FieldEvent be) {
                userDetailsWidget.enableSaveButton();
            }

        }); 

        fieldSet.add(allowedArea);

        fp.add(fieldSet);

        return fp;
    }

    /**
     * Gets the model data.
     * 
     * @return the model data
     */
    public UserLimitsInfo getModelData() {
        UserLimitsInfo userInfo = new UserLimitsInfo();

        userInfo.setAllowedArea(allowedArea.getValue());   
        userInfo.setUserId(user.getId());

        return userInfo;
    }

    /**
     * Bind model data.
     * 
     * @param layerDetailsInfo
     *            the layer details info
     */
    public void bindModelData(UserLimitsInfo userInfo){
        this.bindModel(userInfo);

        String area = userInfo.getAllowedArea();
        if(area != null){
            allowedArea.setValue(area);
        }else{
            allowedArea.setValue("");
        }   	
    }

}
