/*
 * $ Header: it.geosolutions.georepo.gui.client.mvc.FilterController,v. 0.1 3-gen-2011 16.52.35 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 16.52.35 $
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
package it.geosolutions.georepo.gui.client.mvc;

import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.Filter;
import it.geosolutions.georepo.gui.client.widget.observer.FilterWatcher;
import it.geosolutions.georepo.gui.client.widget.observer.ObserverFilterWidget;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

// TODO: Auto-generated Javadoc
/**
 * The Class FilterController.
 */
public class FilterController extends Controller {

    // private FilterServiceRemoteAsync filterService = FilterServiceRemote.Util
    // .getInstance();

    /** The filter view. */
    private FilterView filterView;

    /** The observer filter widget. */
    private ObserverFilterWidget observerFilterWidget = new ObserverFilterWidget();

    /**
     * Instantiates a new filter controller.
     */
    public FilterController() {
        registerEventTypes(
                DGWATCHEvents.INIT_AOIS_UI_MODULE,// DGWATCHEvents.INIT_DGWATCH_WIDGET,
                DGWATCHEvents.ATTACH_AOI_FILTER, // DGWATCHEvents.AOI_SELECTED,
                DGWATCHEvents.USER_SELECTED, DGWATCHEvents.EXIST_DEFAULT_FILTER,
                DGWATCHEvents.SET_USER_PREF, DGWATCHEvents.UNBIND_FILTER_WIDGET,
                DGWATCHEvents.UPDATE_TITLE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.Controller#initialize()
     */
    @Override
    protected void initialize() {
        this.filterView = new FilterView(this);
        this.observerFilterWidget.addObserver(new FilterWatcher());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.Controller#handleEvent(com.extjs.gxt.ui.client
     * .mvc.AppEvent)
     */
    @Override
    public void handleEvent(AppEvent event) {

        // if (event.getType() == DGWATCHEvents.AOI_SELECTED)
        // onSelectedAOI(event);

        // if (event.getType() == DGWATCHEvents.USER_SELECTED)
        // onSelectedUser(event);

        if (event.getType() == DGWATCHEvents.EXIST_DEFAULT_FILTER)
            onExistDefaultFilter(event);

        if (event.getType() == DGWATCHEvents.SET_USER_PREF)
            onSetUserPref(event);

        if (event.getType() == DGWATCHEvents.UPDATE_TITLE)
            onUpdateTitlePref();

        forwardToView(filterView, event);
    }

    /**
     * On update title pref.
     */
    private void onUpdateTitlePref() {
        filterView.getFilter().setHeading(
                I18nProvider.getMessages().aoiFilterLabel() + " (Attribute Filter disabled)");
    }

    // @SuppressWarnings("rawtypes")
    /**
     * On set user pref.
     * 
     * @param event
     *            the event
     */
    private void onSetUserPref(AppEvent event) {
        // final Filter filter = (Filter) event.getData();
        //
        // this.filterService.setUserPref(filter.getUserID(), filter.getAoiID(),
        // filter, new AsyncCallback() {
        //
        // public void onFailure(Throwable caught) {
        // Dispatcher
        // .forwardEvent(
        // DGWATCHEvents.SEND_ERROR_MESSAGE,
        // new String[] { "Filter Sercice",
        // "There was an error in setting User Pref for AOI." });
        // }
        //
        // public void onSuccess(Object result) {
        // Dispatcher
        // .forwardEvent(DGWATCHEvents.UNBIND_USER_WIDGET);
        // Dispatcher
        // .forwardEvent(DGWATCHEvents.UNBIND_FILTER_WIDGET);
        // Dispatcher.forwardEvent(DGWATCHEvents.RESET_AOI_GRID);
        // Dispatcher.forwardEvent(DGWATCHEvents.RESET_RSS_GRID);
        // Dispatcher.forwardEvent(DGWATCHEvents.CHECK_AOI_STATUS);
        //
        // Dispatcher.forwardEvent(
        // DGWATCHEvents.SEND_INFO_MESSAGE, new String[] {
        // "Filter Service",
        // "Set User Pref for AOI with ID : "
        // + filter.getAoiID() });
        // }
        // });

    }

    /**
     * On exist default filter.
     * 
     * @param event
     *            the event
     */
    private void onExistDefaultFilter(AppEvent event) {
        final Filter filter = (Filter) event.getData();
        bindModels(filter);
        // this.filterService.findFilterByUserAOI(observerFilterWidget
        // .getUserSelected().getId(), observerFilterWidget
        // .getAoiSelected().getId(), new AsyncCallback<Filter>() {
        //
        // public void onSuccess(Filter result) {
        // result.setUserID(observerFilterWidget.getUserSelected().getId());
        // result.setAoiID(observerFilterWidget.getAoiSelected().getId());
        // bindModels(result);
        // }
        //
        // public void onFailure(Throwable caught) {
        // Dispatcher
        // .forwardEvent(
        // DGWATCHEvents.SEND_ERROR_MESSAGE,
        // new String[] { "Filter Sercice",
        // "There was an error retrieving the data from the service" });
        // }
        // });

    }

    // private void onSelectedUser(AppEvent event) {
    // this.observerFilterWidget.setUserSelected((User) event.getData());
    // }

    // private void onSelectedAOI(AppEvent event) {
    // this.observerFilterWidget.setAoiSelected((AOI) event.getData());
    //
    // }

    /**
     * Bind models.
     * 
     * @param filter
     *            the filter
     */
    private void bindModels(Filter filter) {
        this.filterView.getFilter().getFilterBinding().unBindModel();
        this.filterView.getFilter().getFilterBinding().bindModel(filter);

        filterView.getFilter().setHeading(
                I18nProvider.getMessages().aoiFilterLabel() + " (Attribute Filter enabled)");

    }
}
