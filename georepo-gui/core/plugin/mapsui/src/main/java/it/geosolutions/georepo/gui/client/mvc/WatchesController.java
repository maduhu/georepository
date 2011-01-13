/*
 * $ Header: it.geosolutions.georepo.gui.client.mvc.WatchesController,v. 0.1 3-gen-2011 16.52.35 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.model.ClientShortWatch;
import it.geosolutions.georepo.gui.client.model.Watch;
import it.geosolutions.georepo.gui.client.service.WatchServiceRemote;
import it.geosolutions.georepo.gui.client.service.WatchServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.WatchesInfoBindingWidget;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class WatchesController.
 */
public class WatchesController extends Controller {

    /** The watch remote. */
    private final WatchServiceRemoteAsync watchRemote = WatchServiceRemote.Util.getInstance();

    /** The watches view. */
    private WatchesView watchesView;

    /**
     * Instantiates a new watches controller.
     */
    public WatchesController() {
        registerEventTypes(GeoRepoEvents.INIT_MAPS_UI_MODULE, GeoRepoEvents.ATTACH_WATCHES_WIDGET,
                GeoRepoEvents.BINDING_WATCH_WIDGET, GeoRepoEvents.UNBINDING_WATCH_WIDGET,
                GeoRepoEvents.SAVE_WATCH, GeoRepoEvents.UPDATE_WATCH, GeoRepoEvents.TRIGGER_WATCH,
                GeoRepoEvents.LOGIN_SUCCESS, GeoRepoEvents.DELETE_WATCH);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.Controller#initialize()
     */
    @Override
    protected void initialize() {
        this.watchesView = new WatchesView(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.Controller#handleEvent(com.extjs.gxt.ui.client
     * .mvc.AppEvent)
     */
    @Override
    public void handleEvent(AppEvent event) {

        if (event.getType() == GeoRepoEvents.BINDING_WATCH_WIDGET) {
            onWatchBind(event);
        }

        if (event.getType() == GeoRepoEvents.UNBINDING_WATCH_WIDGET) {
            onWatchUnBind();
        }

        if (event.getType() == GeoRepoEvents.SAVE_WATCH) {
            onSaveWatch(event);
        }

        if (event.getType() == GeoRepoEvents.UPDATE_WATCH) {
            onUpdateWatch(event);
        }

        if (event.getType() == GeoRepoEvents.TRIGGER_WATCH) {
            onTriggerWatch(event);
        }

        if (event.getType() == GeoRepoEvents.DELETE_WATCH) {
            onDeleteWatch(event);
        }

        forwardToView(watchesView, event);
    }

    /**
     * On delete watch.
     * 
     * @param event
     *            the event
     */
    private void onDeleteWatch(AppEvent event) {
        final ClientShortWatch shortWatch = (ClientShortWatch) event.getData();
        this.watchRemote.deleteWatch(shortWatch.getId(), new AsyncCallback<Object>() {

            public void onFailure(Throwable caught) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE, new String[] {
                        "Watch Service",
                        "There was an error binding the WATCH " + shortWatch.getTitle() });
            }

            public void onSuccess(Object result) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE,
                        new String[] { "Watch Service",
                                "The Watch " + shortWatch.getTitle() + " has been deleted" });

                WatchesInfoBindingWidget watchWidget = watchesView.getWmWidget().getWatchesInfo();

                if (watchWidget.isSelected()) {
                    Watch watch = watchWidget.getModelData();

                    if (watch.getId() == shortWatch.getId())
                        Dispatcher.forwardEvent(GeoRepoEvents.UNBINDING_WATCH_WIDGET);
                }

                Dispatcher.forwardEvent(GeoRepoEvents.REFRESH_WATCH_GRID);
            }
        });
    }

    /**
     * On trigger watch.
     * 
     * @param event
     *            the event
     */
    private void onTriggerWatch(AppEvent event) {
        final ClientShortWatch shortWatch = (ClientShortWatch) event.getData();
        this.watchRemote.getWatchDetails(shortWatch.getId(), new AsyncCallback<Watch>() {

            public void onFailure(Throwable caught) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE, new String[] {
                        "Watch Service",
                        "There was an error binding the WATCH " + shortWatch.getTitle() });
            }

            public void onSuccess(Watch result) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                        "Watch Service", "The Watch has been retrieved" });

                Dispatcher.forwardEvent(GeoRepoEvents.RUN_WATCH, result);

            }
        });
    }

    /**
     * On update watch.
     * 
     * @param event
     *            the event
     */
    private void onUpdateWatch(AppEvent event) {
        final Watch watch = (Watch) event.getData();

        this.watchRemote.updateWatch(watch, new AsyncCallback<Watch>() {

            public void onFailure(Throwable caught) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE, new String[] {
                        "Watch Service",
                        "There was an error updating the WATCH " + watch.getTitle() });
            }

            public void onSuccess(Watch result) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                        "Watch Service", "The watch has been successfully updated." });

                bindWatch(result);
            }
        });
    }

    /**
     * On save watch.
     * 
     * @param event
     *            the event
     */
    private void onSaveWatch(AppEvent event) {
        final Watch watch = (Watch) event.getData();
        this.watchRemote.saveWatch(watch, new AsyncCallback<Watch>() {

            public void onFailure(Throwable caught) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE,
                        new String[] { "Watch Service",
                                "There was an error saving the WATCH " + watch.getTitle() });
            }

            public void onSuccess(Watch result) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                        "Watch retrieved", "The Watch has been saved" + watch.getTitle() });

                bindWatch(result);
            }
        });
    }

    /**
     * On watch un bind.
     */
    private void onWatchUnBind() {
        WatchesInfoBindingWidget wibw = (watchesView.getWmWidget().getWatchesInfo());
        wibw.unBindModel();
    }

    /**
     * On watch bind.
     * 
     * @param event
     *            the event
     */
    private void onWatchBind(AppEvent event) {
        final ClientShortWatch shortWatch = (ClientShortWatch) event.getData();
        this.watchRemote.getWatchDetails(shortWatch.getId(), new AsyncCallback<Watch>() {

            public void onFailure(Throwable caught) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE, new String[] {
                        "Watch Service",
                        "There was an error binding the WATCH " + shortWatch.getTitle() });
            }

            public void onSuccess(Watch result) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                        "Watch retrieved", "The Watch has been retrieved" });
                bindWatch(result);

                Dispatcher.forwardEvent(GeoRepoEvents.LOAD_WATCH_AOI, result);

            }
        });
    }

    /**
     * Bind watch.
     * 
     * @param watch
     *            the watch
     */
    private void bindWatch(Watch watch) {
        WatchesInfoBindingWidget wibw = (watchesView.getWmWidget().getWatchesInfo());

        // ////////////////////////
        // UNBIND data
        // ////////////////////////

        wibw.unBindModel();

        // ///////////////////////
        // BIND data
        // ///////////////////////

        wibw.bindModel(watch);

    }

}
