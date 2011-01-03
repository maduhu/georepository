/*
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
import it.geosolutions.georepo.gui.client.model.ClientShortWatch;
import it.geosolutions.georepo.gui.client.model.Watch;
import it.geosolutions.georepo.gui.client.service.WatchServiceRemote;
import it.geosolutions.georepo.gui.client.service.WatchServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.WatchesInfoBindingWidget;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Tobia di Pisa
 * 
 */
public class WatchesController extends Controller {

    private final WatchServiceRemoteAsync watchRemote = WatchServiceRemote.Util.getInstance();

    private WatchesView watchesView;

    /**
	 * 
	 */
    public WatchesController() {
        registerEventTypes(DGWATCHEvents.INIT_AOIS_UI_MODULE, DGWATCHEvents.ATTACH_WATCHES_WIDGET, DGWATCHEvents.BINDING_WATCH_WIDGET, DGWATCHEvents.UNBINDING_WATCH_WIDGET, DGWATCHEvents.SAVE_WATCH, DGWATCHEvents.UPDATE_WATCH, DGWATCHEvents.TRIGGER_WATCH, DGWATCHEvents.LOGIN_SUCCESS, DGWATCHEvents.DELETE_WATCH);
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
     * @see
     * com.extjs.gxt.ui.client.mvc.Controller#handleEvent(com.extjs.gxt.ui.client
     * .mvc.AppEvent)
     */
    @Override
    public void handleEvent(AppEvent event) {

        if (event.getType() == DGWATCHEvents.BINDING_WATCH_WIDGET) {
            onWatchBind(event);
        }

        if (event.getType() == DGWATCHEvents.UNBINDING_WATCH_WIDGET) {
            onWatchUnBind();
        }

        if (event.getType() == DGWATCHEvents.SAVE_WATCH) {
            onSaveWatch(event);
        }

        if (event.getType() == DGWATCHEvents.UPDATE_WATCH) {
            onUpdateWatch(event);
        }

        if (event.getType() == DGWATCHEvents.TRIGGER_WATCH) {
            onTriggerWatch(event);
        }

        if (event.getType() == DGWATCHEvents.DELETE_WATCH) {
            onDeleteWatch(event);
        }

        forwardToView(watchesView, event);
    }

    /**
     * @param event
     */
    private void onDeleteWatch(AppEvent event) {
        final ClientShortWatch shortWatch = (ClientShortWatch) event.getData();
        this.watchRemote.deleteWatch(shortWatch.getId(), new AsyncCallback<Object>() {

            public void onFailure(Throwable caught) {
                Dispatcher.forwardEvent(DGWATCHEvents.SEND_ERROR_MESSAGE, new String[] {
                    "Watch Service",
                    "There was an error binding the WATCH " + shortWatch.getTitle() });
            }

            public void onSuccess(Object result) {
                Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE, new String[] {
                    "Watch Service",
                    "The Watch " + shortWatch.getTitle() + " has been deleted" });

                WatchesInfoBindingWidget watchWidget = watchesView.getWmWidget().getWatchesInfo();

                if (watchWidget.isSelected()) {
                    Watch watch = watchWidget.getModelData();

                    if (watch.getId() == shortWatch.getId())
                        Dispatcher.forwardEvent(DGWATCHEvents.UNBINDING_WATCH_WIDGET);
                }

                Dispatcher.forwardEvent(DGWATCHEvents.REFRESH_WATCH_GRID);
            }
        });
    }

    /**
     * @param event
     */
    private void onTriggerWatch(AppEvent event) {
        final ClientShortWatch shortWatch = (ClientShortWatch) event.getData();
        this.watchRemote.getWatchDetails(shortWatch.getId(), new AsyncCallback<Watch>() {

            public void onFailure(Throwable caught) {
                Dispatcher.forwardEvent(DGWATCHEvents.SEND_ERROR_MESSAGE, new String[] {
                    "Watch Service",
                    "There was an error binding the WATCH " + shortWatch.getTitle() });
            }

            public void onSuccess(Watch result) {
                Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE, new String[] {
                    "Watch Service",
                    "The Watch has been retrieved" });

                Dispatcher.forwardEvent(DGWATCHEvents.RUN_WATCH, result);

            }
        });
    }

    /**
     * @param event
     */
    private void onUpdateWatch(AppEvent event) {
        final Watch watch = (Watch) event.getData();

        this.watchRemote.updateWatch(watch, new AsyncCallback<Watch>() {

            public void onFailure(Throwable caught) {
                Dispatcher.forwardEvent(DGWATCHEvents.SEND_ERROR_MESSAGE, new String[] {
                    "Watch Service",
                    "There was an error updating the WATCH " + watch.getTitle() });
            }

            public void onSuccess(Watch result) {
                Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE, new String[] {
                    "Watch Service",
                    "The watch has been successfully updated." });

                bindWatch(result);
            }
        });
    }

    /**
     * @param event
     */
    private void onSaveWatch(AppEvent event) {
        final Watch watch = (Watch) event.getData();
        this.watchRemote.saveWatch(watch, new AsyncCallback<Watch>() {

            public void onFailure(Throwable caught) {
                Dispatcher.forwardEvent(DGWATCHEvents.SEND_ERROR_MESSAGE, new String[] {
                    "Watch Service",
                    "There was an error saving the WATCH " + watch.getTitle() });
            }

            public void onSuccess(Watch result) {
                Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE, new String[] {
                    "Watch retrieved",
                    "The Watch has been saved" + watch.getTitle() });

                bindWatch(result);
            }
        });
    }

    /**
	 * 
	 */
    private void onWatchUnBind() {
        WatchesInfoBindingWidget wibw = (watchesView.getWmWidget().getWatchesInfo());
        wibw.unBindModel();
    }

    /**
     * @param event
     */
    private void onWatchBind(AppEvent event) {
        final ClientShortWatch shortWatch = (ClientShortWatch) event.getData();
        this.watchRemote.getWatchDetails(shortWatch.getId(), new AsyncCallback<Watch>() {

            public void onFailure(Throwable caught) {
                Dispatcher.forwardEvent(DGWATCHEvents.SEND_ERROR_MESSAGE, new String[] {
                    "Watch Service",
                    "There was an error binding the WATCH " + shortWatch.getTitle() });
            }

            public void onSuccess(Watch result) {
                Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE, new String[] {
                    "Watch retrieved",
                    "The Watch has been retrieved" });
                bindWatch(result);

                Dispatcher.forwardEvent(DGWATCHEvents.LOAD_WATCH_AOI, result);

            }
        });
    }

    /**
     * @param watch
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
