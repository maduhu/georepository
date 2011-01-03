package it.geosolutions.georepo.gui.client.widget;

import it.geosolutions.georepo.gui.client.model.GeoConstraint;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.Dispatcher;

/**
 * @author gmurray
 *
 */
public class AddGeoConstraintFromShpWidget extends AddGenericAOIWidget {

    public AddGeoConstraintFromShpWidget(EventType submitEvent, boolean closeOnSubmit) {
        super(submitEvent, closeOnSubmit);
    }

	/*
	 * (non-Javadoc)
	 *
	 * @see it.geosolutions.georepo.gui.client.widget.AddGenericAOIWidget#
	 * addOtherComponents()
	 */
	@Override
	public void addOtherComponents() {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * it.geosolutions.georepo.gui.client.form.DGWATCHFormWidget#initSize()
	 */
	@Override
	public void initSize() {
		setHeading("Add GeoConstraint from SHAPE");
		setSize(420, 250);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * it.geosolutions.georepo.gui.client.form.DGWATCHFormWidget#initSizeFormPanel
	 * ()
	 */
	@Override
	public void initSizeFormPanel() {
		formPanel.setHeaderVisible(false);
		formPanel.setSize(450, 200);

	}

	@Override
    public void reset() {
		this.resetComponents();
	}

	@Override
    public void resetComponents() {
		this.aoiTitle.reset();
		this.saveStatus.clearStatus("");
	}

	@Override
	public void injectEvent() {
        GeoConstraint gc = new GeoConstraint();
        gc.setName(this.aoi.getTitle());
        gc.setWkt(this.aoi.getWkt());
        Dispatcher.forwardEvent(getSubmitEvent(), gc);
	}

}