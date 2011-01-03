/*
 * $Header: it.geosolutions.georepo.gui.client.mvc.AOIView,v. 0.1 20/lug/2010 10.40.03 created by frank $
 * $Revision: 0.1 $
 * $Date: 20/lug/2010 10.40.03 $
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
import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.AOI;
import it.geosolutions.georepo.gui.client.model.GeoConstraint;
import it.geosolutions.georepo.gui.client.model.User;
import it.geosolutions.georepo.gui.client.widget.*;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;

/**
 * @author frank
 * 
 */
public class AOIView extends View {

	private AOISWidget aois;
    private GeoConstraintWidget geoConstraint;
    private UploadWatchAreaWidget uploadWidget;
    private AddGenericAOIWidget addAOI;
    private AddGenericAOIWidget addGeoConstraint;
    private AddGenericAOIWidget addAOIFromShp;
    private AddGenericAOIWidget addGeoConstraintFromShp;

    private AddGenericAOIWidget drawingInProgressForWidget;

    private AdministrationMode currentAdminMode = AdministrationMode.NOTIFICATION_DISTRIBUTION;

	public AOIView(Controller controller) {
		super(controller);
        this.aois = new AOISWidget(I18nProvider.getMessages().aoiManagementLabel());
        this.geoConstraint = new GeoConstraintWidget(I18nProvider.getMessages().geoConstraintManagementLabel());
		this.uploadWidget = new UploadWatchAreaWidget();
        this.addAOI = new AddAOIWidget(DGWATCHEvents.SAVE_AOI, false);
        this.addGeoConstraint = new AddGeoConstraintWidget(DGWATCHEvents.BIND_SELECTED_GEOCONSTRAINT, true);
        this.addAOIFromShp = new AddAOIFromShpWidget(DGWATCHEvents.SAVE_AOI_FROM_SHP, false);
        this.addGeoConstraintFromShp = new AddGeoConstraintFromShpWidget(DGWATCHEvents.BIND_SELECTED_GEOCONSTRAINT, true);
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
        if (event.getType() == DGWATCHEvents.SHOW_UPLOAD_WIDGET) {
            onShowUploadWidget();
        }

        if (event.getType() == DGWATCHEvents.ATTACH_AOI_WIDGET) {
            onAttachAOIWidget(event);
        }

        if (event.getType() == DGWATCHEvents.ATTACH_GEOCONSTRAINT_AOI_WIDGET) {
            onAttachGeoConstraintAOIWidget(event);
        }

        if (event.getType() == DGWATCHEvents.AOI_MANAGEMENT_BIND) {
            onAOIBind(event);
        }

        if (event.getType() == DGWATCHEvents.AOI_MANAGEMENT_UNBIND) {
            onAOIUnBind();
        }

        if (event.getType() == DGWATCHEvents.SHOW_ADD_AOI) {
            onShowAddAOI();
        }

        if (event.getType() == DGWATCHEvents.SHOW_ADD_GEOCONSTRAINT) {
            onShowAddGeoConstraint();
        }

        if (event.getType() == DGWATCHEvents.INJECT_WKT) {
            onInjectWKT(event);
        }

        if (event.getType() == DGWATCHEvents.INJECT_WKT_FROM_SHP) {
            onInjectWKTFromShp(event);
        }

        if (event.getType() == DGWATCHEvents.CHECK_AOI_OWNER) {
            onCheckAoiOwner(event);
        }

        if (event.getType() == DGWATCHEvents.CHECK_AOI_STATUS) {
            onCheckAoiStatus();
        }

        if (event.getType() == DGWATCHEvents.DELETE_CONTENT) {
            onDeleteContent();
        }

        if (event.getType() == DGWATCHEvents.BIND_SELECTED_GEOCONSTRAINT) {
            onBindSelectedGeoConstraint(event);
        }

        if (event.getType() == DGWATCHEvents.ENABLE_DRAW_BUTTON) {
            onEnableDrawButton(event);
        }

        if (event.getType() == DGWATCHEvents.ADMIN_MODE_CHANGE) {
            this.currentAdminMode = event.getData();
        }
	}

	private void onCheckAoiStatus() {
		AOIBindingWidget aoiBindingWidget = (AOIBindingWidget) this.aois
				.getAoiBinding();

		if ((aoiBindingWidget.getModel() != null)
				&& !(aoiBindingWidget.getModel().isShared())) {
			aoiBindingWidget.unBindModel();
			Dispatcher.forwardEvent(DGWATCHEvents.ERASE_AOI_FEATURES);
		}
	}

	private void onCheckAoiOwner(AppEvent event) {
		User userDeleted = (User) event.getData();
		User ownerAOI = ((AOIBindingWidget) this.getAois().getAoiBinding())
				.getModel().getOwner();
		if ((ownerAOI != null) && (ownerAOI.equals(userDeleted))) {
			((AOIBindingWidget) this.getAois().getAoiBinding()).unBindModel();
		}
	}

	private void onInjectWKTFromShp(AppEvent event) {
        switch(this.currentAdminMode) {
            case NOTIFICATION_DISTRIBUTION:
        		this.addAOIFromShp.getAoi().setWkt((String) event.getData());
                this.addAOIFromShp.show(false);
                break;
            case GEOCONSTRAINTS:
                this.addGeoConstraintFromShp.getAoi().setWkt((String) event.getData());
                this.addGeoConstraintFromShp.show(false);
                break;
            default:
                break;
        }
	}

	private void onInjectWKT(AppEvent event) {

//		this.addAOI.getWktArea().setValue((String) event.getData());
//		this.addAOI.show(false);
		this.drawingInProgressForWidget.getWktArea().setValue((String) event.getData());
		this.drawingInProgressForWidget.show(false);

        // no need to keep around the reference anymore
        this.drawingInProgressForWidget = null;
	}

    private void onShowAddAOI() {
        this.addAOI.show(true);
    }

    private void onShowAddGeoConstraint() {
        this.addGeoConstraint.show(true);
    }

	private void onAOIUnBind() {
		AOIBindingWidget aoiBindingWidget = (AOIBindingWidget) this.aois
				.getAoiBinding();
		aoiBindingWidget.unBindModel();
		aoiBindingWidget.setSelected(false);
		
		this.aois.setHeading(I18nProvider.getMessages().aoiManagementLabel());
	}

	private void onAOIBind(AppEvent event) {
		AOI aoi = (AOI) event.getData();
		AOIBindingWidget aoiBindingWidget = (AOIBindingWidget) this.aois
				.getAoiBinding();
		aoiBindingWidget.getFormBinding().bind(aoi);
		
		aois.setHeading(I18nProvider.getMessages().aoiManagementLabel() +
				" (" + aoi.getTitle() + ")");
		
		aoiBindingWidget.setSelected(true);
	}

    private void onAttachAOIWidget(AppEvent event) {
        ContentPanel east = (ContentPanel) event.getData();
        //this.aois.setHeading(I18nProvider.getMessages().aoiManagementLabel());
        east.add(this.aois);
        east.layout();
    }

    private void onAttachGeoConstraintAOIWidget(AppEvent event) {
        ContentPanel east = (ContentPanel) event.getData();
        //this.aois.setHeading(I18nProvider.getMessages().geoConstraintManagementLabel());
        east.add(this.geoConstraint);
        east.layout();
    }

    private void onDeleteContent() {
        MessageBox.info("Delete Content", "Implement me!!!!", null);
    }

    private void onBindSelectedGeoConstraint(AppEvent event) {
        GeoConstraint gc = event.getData();
        this.geoConstraint.getGeoConstraintBinding().getFormBinding().bind(gc);
        if (gc != null) {
            gc.notifyState();
        }
    }

	private void onShowUploadWidget() {
        switch (this.currentAdminMode) {
            case NOTIFICATION_DISTRIBUTION:
                this.uploadWidget.setHeading(I18nProvider.getMessages().uploadAoiShapeDialogTitle());
                break;
            case GEOCONSTRAINTS:
                this.uploadWidget.setHeading(I18nProvider.getMessages().uploadGeoConstraintShapeDialogTitle());
                break;
            default:
                break;
        }
		this.uploadWidget.show();
	}

    private void onEnableDrawButton(AppEvent event) {
        this.drawingInProgressForWidget = event.getData();
    }

	/**
	 * @return the aois
	 */
    public AOISWidget getAois() {
        return aois;
    }

    public GeoConstraintWidget getGeoConstraint() {
        return this.geoConstraint;
    }

    /**
     * @return the addAOI
     */
    public AddGenericAOIWidget getAddAOI() {
        return this.addAOI;
    }

    /**
     * @return the addAOI
     */
    public AddGenericAOIWidget getAddGeoConstraint() {
        return this.addGeoConstraint;
    }

	/**
	 * @return the addAOIFromShp
	 */
	public AddGenericAOIWidget getAddAOIFromShp() {
		return addAOIFromShp;
	}

}
