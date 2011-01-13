/*
 * $ Header: it.geosolutions.georepo.gui.client.mvc.AOIView,v. 0.1 3-gen-2011 16.52.35 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.georepo.gui.client.AdministrationMode;
import it.geosolutions.georepo.gui.client.GeoRepoEvents;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.AOI;
import it.geosolutions.georepo.gui.client.model.GeoConstraint;
import it.geosolutions.georepo.gui.client.model.User;
import it.geosolutions.georepo.gui.client.widget.AOIBindingWidget;
import it.geosolutions.georepo.gui.client.widget.AOISWidget;
import it.geosolutions.georepo.gui.client.widget.AddAOIFromShpWidget;
import it.geosolutions.georepo.gui.client.widget.AddAOIWidget;
import it.geosolutions.georepo.gui.client.widget.AddGenericAOIWidget;
import it.geosolutions.georepo.gui.client.widget.AddGeoConstraintFromShpWidget;
import it.geosolutions.georepo.gui.client.widget.AddGeoConstraintWidget;
import it.geosolutions.georepo.gui.client.widget.GeoConstraintWidget;
import it.geosolutions.georepo.gui.client.widget.UploadWatchAreaWidget;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;

// TODO: Auto-generated Javadoc
/**
 * The Class AOIView.
 */
public class AOIView extends View {

    /** The aois. */
    private AOISWidget aois;

    /** The geo constraint. */
    private GeoConstraintWidget geoConstraint;

    /** The upload widget. */
    private UploadWatchAreaWidget uploadWidget;

    /** The add aoi. */
    private AddGenericAOIWidget addAOI;

    /** The add geo constraint. */
    private AddGenericAOIWidget addGeoConstraint;

    /** The add aoi from shp. */
    private AddGenericAOIWidget addAOIFromShp;

    /** The add geo constraint from shp. */
    private AddGenericAOIWidget addGeoConstraintFromShp;

    /** The drawing in progress for widget. */
    private AddGenericAOIWidget drawingInProgressForWidget;

    /** The current admin mode. */
    private AdministrationMode currentAdminMode = AdministrationMode.NOTIFICATION_DISTRIBUTION;

    /**
     * Instantiates a new aOI view.
     * 
     * @param controller
     *            the controller
     */
    public AOIView(Controller controller) {
        super(controller);
        this.aois = new AOISWidget(I18nProvider.getMessages().aoiManagementLabel());
        this.geoConstraint = new GeoConstraintWidget(I18nProvider.getMessages()
                .geoConstraintManagementLabel());
        this.uploadWidget = new UploadWatchAreaWidget();
        this.addAOI = new AddAOIWidget(GeoRepoEvents.SAVE_AOI, false);
        this.addGeoConstraint = new AddGeoConstraintWidget(
                GeoRepoEvents.BIND_SELECTED_GEOCONSTRAINT, true);
        this.addAOIFromShp = new AddAOIFromShpWidget(GeoRepoEvents.SAVE_AOI_FROM_SHP, false);
        this.addGeoConstraintFromShp = new AddGeoConstraintFromShpWidget(
                GeoRepoEvents.BIND_SELECTED_GEOCONSTRAINT, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.mvc.View#handleEvent(com.extjs.gxt.ui.client. mvc.AppEvent)
     */
    @Override
    protected void handleEvent(AppEvent event) {
        if (event.getType() == GeoRepoEvents.SHOW_UPLOAD_WIDGET) {
            onShowUploadWidget();
        }

        if (event.getType() == GeoRepoEvents.ATTACH_AOI_WIDGET) {
            onAttachAOIWidget(event);
        }

        if (event.getType() == GeoRepoEvents.ATTACH_GEOCONSTRAINT_AOI_WIDGET) {
            onAttachGeoConstraintAOIWidget(event);
        }

        if (event.getType() == GeoRepoEvents.AOI_MANAGEMENT_BIND) {
            onAOIBind(event);
        }

        if (event.getType() == GeoRepoEvents.AOI_MANAGEMENT_UNBIND) {
            onAOIUnBind();
        }

        if (event.getType() == GeoRepoEvents.SHOW_ADD_AOI) {
            onShowAddAOI();
        }

        if (event.getType() == GeoRepoEvents.SHOW_ADD_GEOCONSTRAINT) {
            onShowAddGeoConstraint();
        }

        if (event.getType() == GeoRepoEvents.INJECT_WKT) {
            onInjectWKT(event);
        }

        if (event.getType() == GeoRepoEvents.INJECT_WKT_FROM_SHP) {
            onInjectWKTFromShp(event);
        }

        if (event.getType() == GeoRepoEvents.CHECK_AOI_OWNER) {
            onCheckAoiOwner(event);
        }

        if (event.getType() == GeoRepoEvents.CHECK_AOI_STATUS) {
            onCheckAoiStatus();
        }

        if (event.getType() == GeoRepoEvents.DELETE_CONTENT) {
            onDeleteContent();
        }

        if (event.getType() == GeoRepoEvents.BIND_SELECTED_GEOCONSTRAINT) {
            onBindSelectedGeoConstraint(event);
        }

        if (event.getType() == GeoRepoEvents.ENABLE_DRAW_BUTTON) {
            onEnableDrawButton(event);
        }

        if (event.getType() == GeoRepoEvents.ADMIN_MODE_CHANGE) {
            this.currentAdminMode = event.getData();
        }
    }

    /**
     * On check aoi status.
     */
    private void onCheckAoiStatus() {
        AOIBindingWidget aoiBindingWidget = (AOIBindingWidget) this.aois.getAoiBinding();

        if ((aoiBindingWidget.getModel() != null) && !(aoiBindingWidget.getModel().isShared())) {
            aoiBindingWidget.unBindModel();
            Dispatcher.forwardEvent(GeoRepoEvents.ERASE_AOI_FEATURES);
        }
    }

    /**
     * On check aoi owner.
     * 
     * @param event
     *            the event
     */
    private void onCheckAoiOwner(AppEvent event) {
        User userDeleted = (User) event.getData();
        User ownerAOI = ((AOIBindingWidget) this.getAois().getAoiBinding()).getModel().getOwner();
        if ((ownerAOI != null) && (ownerAOI.equals(userDeleted))) {
            ((AOIBindingWidget) this.getAois().getAoiBinding()).unBindModel();
        }
    }

    /**
     * On inject wkt from shp.
     * 
     * @param event
     *            the event
     */
    private void onInjectWKTFromShp(AppEvent event) {
        switch (this.currentAdminMode) {
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

    /**
     * On inject wkt.
     * 
     * @param event
     *            the event
     */
    private void onInjectWKT(AppEvent event) {

        // this.addAOI.getWktArea().setValue((String) event.getData());
        // this.addAOI.show(false);
        this.drawingInProgressForWidget.getWktArea().setValue((String) event.getData());
        this.drawingInProgressForWidget.show(false);

        // no need to keep around the reference anymore
        this.drawingInProgressForWidget = null;
    }

    /**
     * On show add aoi.
     */
    private void onShowAddAOI() {
        this.addAOI.show(true);
    }

    /**
     * On show add geo constraint.
     */
    private void onShowAddGeoConstraint() {
        this.addGeoConstraint.show(true);
    }

    /**
     * On aoi un bind.
     */
    private void onAOIUnBind() {
        AOIBindingWidget aoiBindingWidget = (AOIBindingWidget) this.aois.getAoiBinding();
        aoiBindingWidget.unBindModel();
        aoiBindingWidget.setSelected(false);

        this.aois.setHeading(I18nProvider.getMessages().aoiManagementLabel());
    }

    /**
     * On aoi bind.
     * 
     * @param event
     *            the event
     */
    private void onAOIBind(AppEvent event) {
        AOI aoi = (AOI) event.getData();
        AOIBindingWidget aoiBindingWidget = (AOIBindingWidget) this.aois.getAoiBinding();
        aoiBindingWidget.getFormBinding().bind(aoi);

        aois.setHeading(I18nProvider.getMessages().aoiManagementLabel() + " (" + aoi.getTitle()
                + ")");

        aoiBindingWidget.setSelected(true);
    }

    /**
     * On attach aoi widget.
     * 
     * @param event
     *            the event
     */
    private void onAttachAOIWidget(AppEvent event) {
        ContentPanel east = (ContentPanel) event.getData();
        // this.aois.setHeading(I18nProvider.getMessages().aoiManagementLabel());
        east.add(this.aois);
        east.layout();
    }

    /**
     * On attach geo constraint aoi widget.
     * 
     * @param event
     *            the event
     */
    private void onAttachGeoConstraintAOIWidget(AppEvent event) {
        ContentPanel east = (ContentPanel) event.getData();
        // this.aois.setHeading(I18nProvider.getMessages().geoConstraintManagementLabel());
        east.add(this.geoConstraint);
        east.layout();
    }

    /**
     * On delete content.
     */
    private void onDeleteContent() {
        MessageBox.info("Delete Content", "Implement me!!!!", null);
    }

    /**
     * On bind selected geo constraint.
     * 
     * @param event
     *            the event
     */
    private void onBindSelectedGeoConstraint(AppEvent event) {
        GeoConstraint gc = event.getData();
        this.geoConstraint.getGeoConstraintBinding().getFormBinding().bind(gc);
        if (gc != null) {
            gc.notifyState();
        }
    }

    /**
     * On show upload widget.
     */
    private void onShowUploadWidget() {
        switch (this.currentAdminMode) {
        case NOTIFICATION_DISTRIBUTION:
            this.uploadWidget.setHeading(I18nProvider.getMessages().uploadAoiShapeDialogTitle());
            break;
        case GEOCONSTRAINTS:
            this.uploadWidget.setHeading(I18nProvider.getMessages()
                    .uploadGeoConstraintShapeDialogTitle());
            break;
        default:
            break;
        }
        this.uploadWidget.show();
    }

    /**
     * On enable draw button.
     * 
     * @param event
     *            the event
     */
    private void onEnableDrawButton(AppEvent event) {
        this.drawingInProgressForWidget = event.getData();
    }

    /**
     * Gets the aois.
     * 
     * @return the aois
     */
    public AOISWidget getAois() {
        return aois;
    }

    /**
     * Gets the geo constraint.
     * 
     * @return the geo constraint
     */
    public GeoConstraintWidget getGeoConstraint() {
        return this.geoConstraint;
    }

    /**
     * Gets the adds the aoi.
     * 
     * @return the adds the aoi
     */
    public AddGenericAOIWidget getAddAOI() {
        return this.addAOI;
    }

    /**
     * Gets the adds the geo constraint.
     * 
     * @return the adds the geo constraint
     */
    public AddGenericAOIWidget getAddGeoConstraint() {
        return this.addGeoConstraint;
    }

    /**
     * Gets the adds the aoi from shp.
     * 
     * @return the adds the aoi from shp
     */
    public AddGenericAOIWidget getAddAOIFromShp() {
        return addAOIFromShp;
    }

}
