/*
 * $ Header: it.geosolutions.georepo.gui.client.controller.ServicesController,v. 0.1 3-gen-2011 17.06.54 created by afabiani <alessio.fabiani at geo-solutions.it> $
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
package it.geosolutions.georepo.gui.client.controller;

import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.model.Watch;
import it.geosolutions.georepo.gui.client.service.QuartzRemote;
import it.geosolutions.georepo.gui.client.service.QuartzRemoteAsync;
import it.geosolutions.georepo.gui.client.service.SyncRemote;
import it.geosolutions.georepo.gui.client.service.SyncRemoteAsync;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class ServicesController.
 */
public class ServicesController extends Controller {

    /** The quartz service. */
    private final QuartzRemoteAsync quartzService = QuartzRemote.Util.getInstance();

    /** The sync service. */
    private final SyncRemoteAsync syncService = SyncRemote.Util.getInstance();

    /**
     * Instantiates a new services controller.
     */
    public ServicesController() {
        registerEventTypes(GeoRepoEvents.INIT_USER_UI_MODULE, GeoRepoEvents.QUARTZ_TRIGGER,
                GeoRepoEvents.RUN_WATCH);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.Controller#handleEvent(com.extjs.gxt.ui.client
     * .mvc.AppEvent)
     */
    @Override
    public void handleEvent(AppEvent event) {

        if (event.getType() == GeoRepoEvents.QUARTZ_TRIGGER)
            onQuartzTrigger(event);

        if (event.getType() == GeoRepoEvents.RUN_WATCH)
            onRunWatch(event);
    }

    /**
     * On run watch.
     * 
     * @param event
     *            the event
     */
    private void onRunWatch(AppEvent event) {
        final Watch watch = (Watch) event.getData();

        if (watch.isNotification()) {
            this.quartzService.runWatch(watch.getId(), new AsyncCallback<Object>() {

                public void onFailure(Throwable caught) {
                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE, new String[] {
                            "Quartz Service",
                            "There was an error in launching the trigger with" + " timer "
                                    + watch.getUpInteval() + " h on Quartz Service." });
                }

                public void onSuccess(Object result) {
                    Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                            "Quartz Service",
                            "Success in launching the trigger" + " with timer "
                                    + watch.getUpInteval() + " h on Quartz Service." });
                }
            });
        } else {
            // Distribution
            Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                    "Distribution Watch", "Distribution Watch Triggered" });
            int delayInSeconds = 10; // TODO: Get delay from UI
            this.syncService.runDistribution(watch.getId(), delayInSeconds,
                    new AsyncCallback<Void>() {

                        public void onFailure(Throwable caught) {
                            Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE, new String[] {
                                    "Sync Service",
                                    "Failure executing distribution: ["
                                            + String.valueOf(watch.getTitle()) + "]" });
                        }

                        public void onSuccess(Void result) {
                            Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                                    "Sync Service",
                                    "Successful Distribution: [" + String.valueOf(watch.getTitle())
                                            + "]" });
                        }
                    });
        }

    }

    /**
     * On quartz trigger.
     * 
     * @param event
     *            the event
     */
    private void onQuartzTrigger(AppEvent event) {

        final Integer interval = (Integer) event.getData();
        this.quartzService.runTrigger(interval.intValue(), new AsyncCallback<Object>() {

            public void onFailure(Throwable caught) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_ERROR_MESSAGE, new String[] {
                        "Quartz Service",
                        "There was an error in launching the trigger with" + " timer "
                                + interval.intValue() + " h on Quartz Service." });
            }

            public void onSuccess(Object result) {
                Dispatcher.forwardEvent(GeoRepoEvents.SEND_INFO_MESSAGE, new String[] {
                        "Quartz Service",
                        "Success in launching the trigger" + " with timer " + interval.intValue()
                                + " h on Quartz Service." });
            }
        });
    }
}
