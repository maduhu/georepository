/*
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

/**
 * @author Tobia Di Pisa
 *
 */
public class MemberManagementWidget  extends ContentPanel {

    public static int SEARCH_BUTTON = 0x01;
    public static int GET_WATCHES_BUTTON = 0x02;

	private MemberInfoBindingWidget memberInfo;

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
	 * @return the userInfo
	 */
	public MemberInfoBindingWidget getMemberInfo() {
		return memberInfo;
	}

	/**
	 * Enable Both Delete and Update User Buttons
	 */
	public void enableButtons() {
		this.memberInfo.enableButtons();
	}

	/**
	 * Disable Both Delete and Update User Buttons
	 */
	public void disableButtons() {
		this.memberInfo.disableButtons();
	}
}
