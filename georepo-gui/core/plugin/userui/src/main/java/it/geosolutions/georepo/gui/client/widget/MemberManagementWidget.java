/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.MemberManagementWidget,v. 0.1 3-gen-2011 17.06.55 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 17.06.55 $
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

import it.geosolutions.georepo.gui.client.i18n.I18nProvider;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.WidgetListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class MemberManagementWidget.
 */
public class MemberManagementWidget extends ContentPanel {

    /** The SEARC h_ button. */
    public static int SEARCH_BUTTON = 0x01;

    /** The GE t_ watche s_ button. */
    public static int GET_WATCHES_BUTTON = 0x02;

    /** The member info. */
    private MemberInfoBindingWidget memberInfo;

    /**
     * Instantiates a new member management widget.
     */
    public MemberManagementWidget() {
        setId("memberManagementWidget");
        setHeading(I18nProvider.getMessages().memberManagementLabel());
        setLayout(new FitLayout());

        setLayoutOnChange(true);

        memberInfo = new MemberInfoBindingWidget();

        add(memberInfo.getFormPanel());

        addWidgetListener(new WidgetListener() {
            @Override
            public void widgetResized(ComponentEvent ce) {
                memberInfo.getFormPanel().setHeight(getHeight());
            }
        });

        setScrollMode(Scroll.AUTOY);
    }

    /**
     * Instantiates a new member management widget.
     * 
     * @param showButtons
     *            the show buttons
     */
    public MemberManagementWidget(int showButtons) {

        this();

        if ((showButtons & SEARCH_BUTTON) == 0) {
            this.memberInfo.hideSearchButton();
        }

        if ((showButtons & GET_WATCHES_BUTTON) == 0) {
            this.memberInfo.hideGetWatchesButton();
        }

    }

    /**
     * Gets the member info.
     * 
     * @return the member info
     */
    public MemberInfoBindingWidget getMemberInfo() {
        return memberInfo;
    }

    /**
     * Enable buttons.
     */
    public void enableButtons() {
        this.memberInfo.enableButtons();
    }

    /**
     * Disable buttons.
     */
    public void disableButtons() {
        this.memberInfo.disableButtons();
    }
}
