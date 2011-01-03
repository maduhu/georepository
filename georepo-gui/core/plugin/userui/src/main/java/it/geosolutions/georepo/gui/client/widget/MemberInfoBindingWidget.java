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

import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.Resources;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.Member;
import it.geosolutions.georepo.gui.client.model.MemberKeyValue;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

/**
 * @author Tobia Di Pisa
 *
 */
public class MemberInfoBindingWidget  extends DGWATCHBindingWidget<Member> {

	private FormData formData;

	private LabelField memberName;
	private LabelField memberUI;

	private Button searchMember;
	private Button showWatches;
	
	private boolean selected;

	/**
	 * 
	 */
	public MemberInfoBindingWidget() {
		this.init();
	}

	/**
	 * 
	 */
	private void init() {
		formData = new FormData("-20");
		formPanel = createFormPanel();
		formBinding = new FormBinding(formPanel, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.geosolutions.georepo.gui.client.widget.DGWATCHBindingWidget#
	 * createFormPanel()
	 */
	@Override
    public FormPanel createFormPanel() {
		FormPanel fp = new FormPanel();
		fp.setFrame(true);
		fp.setHeaderVisible(false);

		FieldSet fieldSet = new FieldSet();
		fieldSet.setHeading(I18nProvider.getMessages().memberInfo());
		fieldSet.setCheckboxToggle(false);
		fieldSet.setCollapsible(false);

		FormLayout layout = new FormLayout();
		fieldSet.setLayout(layout);

		memberName = new LabelField();
		memberName.setId(MemberKeyValue.MEMBER_NAME.getValue());
		memberName.setName(MemberKeyValue.MEMBER_NAME.getValue());
		memberName.setWidth(150);
		memberName.setFieldLabel(I18nProvider.getMessages().memberName());

		fieldSet.add(memberName, formData);

		memberUI = new LabelField();
		memberUI.setId(MemberKeyValue.MEMBER_UID.getValue());
		memberUI.setName(MemberKeyValue.MEMBER_UID.getValue());
		memberUI.setWidth(150);
		memberUI.setFieldLabel(I18nProvider.getMessages().memberUID());

		fieldSet.add(memberUI, formData);

		fp.add(fieldSet);

		FlexTable table = new FlexTable();
		table.getElement().getStyle().setProperty("marginLeft", "60px");

		table.setCellSpacing(8);
		table.setCellPadding(2);

		this.searchMember = new Button(I18nProvider.getMessages().searchMember(), new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				Dispatcher.forwardEvent(
						DGWATCHEvents.SEND_INFO_MESSAGE, new String[] {
								I18nProvider.getMessages().searchMember(),
								I18nProvider.getMessages().searchMemberPressed()
						});
				Dispatcher.forwardEvent(DGWATCHEvents.SHOW_SEARCH_MEMBER_WIDGET);
			}
		});

		this.searchMember.setIcon(Resources.ICONS.search());

		table.getCellFormatter().setHorizontalAlignment(1, 1,
				HasHorizontalAlignment.ALIGN_CENTER);

		table.setWidget(1, 1, this.searchMember);

		this.showWatches = new Button(I18nProvider.getMessages().getWatches(), new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				Dispatcher.forwardEvent(DGWATCHEvents.SEARCH_MEMBER_WATCHES, getModel());
			}
		});

		this.showWatches.disable();

		this.showWatches.setIcon(Resources.ICONS.getFeatures());

		table.getCellFormatter().setHorizontalAlignment(1, 2,
				HasHorizontalAlignment.ALIGN_CENTER);

		table.setWidget(1, 2, this.showWatches);
		
		fp.add(table);

		return fp;
	}

	/**
	 * Enable Both Delete User and Update User Buttons
	 */
	public void enableButtons() {
		this.showWatches.enable();
	}

	/**
	 * Disable Both Show Button
	 */
	public void disableButtons() {
		this.showWatches.disable();
	}

    public void showGetWatchesButton() {
        this.showWatches.show();
    }

    public void hideGetWatchesButton() {
        this.showWatches.hide();
    }

    public void showSearchButton() {
        this.searchMember.show();
    }

    public void hideSearchButton() {
        this.searchMember.hide();
    }

	/* (non-Javadoc)
	 * @see it.geosolutions.georepo.gui.client.widget.DGWATCHBindingWidget#unBindModel()
	 */
	@Override
    public void unBindModel() {
		super.unBindModel();
		disableButtons();
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
