package it.geosolutions.georepo.gui.client.widget;

import it.geosolutions.georepo.gui.client.model.AOI;
import it.geosolutions.georepo.gui.client.model.GeoConstraint;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

/**
 * @author gmurray
 *
 */
public class GeoConstraintWidget extends ContentPanel {

	private GeoConstraintBindingWidget geoConstraintBinding;


    public GeoConstraintWidget(String paneHeading) {
        this.setId("geoConstraintWidget");
        setHeading(paneHeading);

        setLayout(new FitLayout());

        this.geoConstraintBinding = new GeoConstraintBindingWidget();

        add(this.geoConstraintBinding.getFormPanel());

        setScrollMode(Scroll.AUTOY);
    }

	/**
	 * @return the aoiBinding
	 */
	public DGWATCHBindingWidget<GeoConstraint> getGeoConstraintBinding() {
		return geoConstraintBinding;
	}

    public GeoConstraintBindingWidget getGeoConstraintInfo() {
        return geoConstraintBinding;
    }

}