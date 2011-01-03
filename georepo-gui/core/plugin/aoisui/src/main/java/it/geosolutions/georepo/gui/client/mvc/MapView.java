/*
 * $Header: it.geosolutions.georepo.gui.client.pages.MapView,v. 0.1 08/lug/2010 12.45.08 created by frank $
 * $Revision: 0.1 $
 * $Date: 08/lug/2010 12.45.08 $
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

import it.geosolutions.georepo.gui.client.AdministrationMode;
import org.gwtopenmaps.openlayers.client.LonLat;

import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.DGWATCHUtils;
import it.geosolutions.georepo.gui.client.widget.ButtonBar;
import it.geosolutions.georepo.gui.client.widget.map.MapLayoutWidget;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.ContentPanel;

/**
 * @author frank
 * 
 */
public class MapView extends View {

	private MapLayoutWidget mapLayout;

	private ButtonBar buttonBar;

	public MapView(Controller controller) {
		super(controller);

		this.mapLayout = new MapLayoutWidget();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.extjs.gxt.ui.client.mvc.View#handleEvent(com.extjs.gxt.ui.client.
	 * mvc.AppEvent)
	 */
	@Override
	protected void handleEvent(AppEvent event) {
		if (event.getType() == DGWATCHEvents.ATTACH_MAP_WIDGET) {
			this.mapLayout.onAddToCenterPanel((ContentPanel) event.getData());
        }

		if (event.getType() == DGWATCHEvents.UPDATE_MAP_SIZE) {
			this.mapLayout.updateMapSize();
        }

		if (event.getType() == DGWATCHEvents.ATTACH_TOOLBAR) {
			onAttachToolbar(event);
        }

		if (event.getType() == DGWATCHEvents.ACTIVATE_DRAW_FEATURES) {
			onActivateDrawFeature();
        }

		if (event.getType() == DGWATCHEvents.DEACTIVATE_DRAW_FEATURES) {
			onDeactivateDrawFeature();
        }

		if (event.getType() == DGWATCHEvents.ERASE_AOI_FEATURES) {
			onEraseAOIFeatures();
        }

		if (event.getType() == DGWATCHEvents.ENABLE_DRAW_BUTTON) {
			onEnableDrawButton();
        }

		if (event.getType() == DGWATCHEvents.DISABLE_DRAW_BUTTON) {
			onDisableDrawButton();
        }

		if (event.getType() == DGWATCHEvents.DRAW_AOI_ON_MAP) {
			onDrawAoiOnMap(event);
        }

        if (event.getType() == DGWATCHEvents.ZOOM_TO_CENTER) {
            onZoomToCenter();
        }

        if (event.getType() == DGWATCHEvents.ADMIN_MODE_CHANGE) {
            onAdminModeChange(event);
        }
        if (event.getType() == DGWATCHEvents.LOGIN_SUCCESS) {
            this.buttonBar.fireEvent(event.getType(), event);
        }
    }

	private void onZoomToCenter() {
		LonLat center = this.mapLayout.getMap().getCenter();
		this.mapLayout.getMap().setCenter(center, 3);
	}

	private void onDrawAoiOnMap(AppEvent event) {
		this.mapLayout.drawAoiOnMap((String) event.getData());
		Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE, new String[] {
				"AOI Service", "Zoom to selected AOI." });
	}

	private void onDisableDrawButton() {
		this.buttonBar.changeButtonState("drawFeature", false);
		this.mapLayout.deactivateDrawFeature();
	}

	private void onEnableDrawButton() {
		this.buttonBar.changeButtonState("drawFeature", true);
		this.mapLayout.activateDrawFeature();
	}

	private void onEraseAOIFeatures() {
		this.mapLayout.eraseFeatures();
	}

	private void onActivateDrawFeature() {
		this.mapLayout.activateDrawFeature();

	}

	private void onDeactivateDrawFeature() {
		this.mapLayout.deactivateDrawFeature();

	}

    private void onAdminModeChange(AppEvent event) {
        AdministrationMode adminMode = event.getData();
        this.buttonBar.changeButtonState("deleteContent", adminMode == AdministrationMode.NOTIFICATION_DISTRIBUTION);
    }

	private void onAttachToolbar(AppEvent event) {
		mapLayout.setTools(DGWATCHUtils.getInstance().getGlobalConfiguration()
				.getToolbarItemManager().getClientTools());

		this.buttonBar = new ButtonBar(mapLayout);

		ContentPanel north = (ContentPanel) event.getData();
		north.add(buttonBar.getToolBar());

		north.layout();
	}
}
