package it.geosolutions.georepo.gui.client.widget;

import it.geosolutions.georepo.gui.client.model.GeoConstraint;
import it.geosolutions.georepo.gui.client.model.Member;
import it.geosolutions.georepo.gui.client.service.MembersRemoteAsync;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

/**
 * @author gmurray
 *
 */
public class GeoConstraintManagementWidget extends ContentPanel {

	private GeoConstraintPaginationGridWidget geoConstraintPageGridWidget;

	/**
	 *
	 */
	public GeoConstraintManagementWidget(MembersRemoteAsync service) {
        setId("geoConstraintManagementWidget");
		setHeaderVisible(false);
		setFrame(true);
		setHeight(170);
		setLayout(new FitLayout());

		this.geoConstraintPageGridWidget = new GeoConstraintPaginationGridWidget(service);
		add(this.geoConstraintPageGridWidget.getGrid());

		super.setMonitorWindowResize(true);

		setScrollMode(Scroll.NONE);

		setBottomComponent(this.geoConstraintPageGridWidget.getToolBar());
	}

	@Override
	protected void onWindowResize(int width, int height) {
		// TODO Auto-generated method stub
		super.setWidth(width - 5);
		super.layout();
	}

    public void loadGeoConstraints(Member member) {
        geoConstraintPageGridWidget.setConnectId(member.getConnectId());
        geoConstraintPageGridWidget.loadData();
    }

	/**
	 * @return the geoConstraintPageGridWidget
	 */
	public GeoConstraintPaginationGridWidget getGeoConstraintPageGridWidget() {
		return geoConstraintPageGridWidget;
	}

    public void removeGeoConstraint(GeoConstraint gc) {
        this.geoConstraintPageGridWidget.removeGeoConstraint(gc);
    }

    public void removeGeoConstraint(Integer gcId) {
        this.geoConstraintPageGridWidget.removeGeoConstraint(gcId);
    }

	public void updateGeoConstraintTitle(GeoConstraint gc) {
		this.geoConstraintPageGridWidget.updateGeoConstraintTitle(gc);
	}

	public void updateGeoConstraintStatus(GeoConstraint gc) {
		this.geoConstraintPageGridWidget.updateGeoConstraintStatus(gc);
	}

    public boolean contains(GeoConstraint gc) {
        return this.geoConstraintPageGridWidget.getStore().contains(gc);
    }

}