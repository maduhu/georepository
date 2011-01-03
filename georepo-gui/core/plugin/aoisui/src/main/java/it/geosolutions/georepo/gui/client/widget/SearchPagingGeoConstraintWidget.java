package it.geosolutions.georepo.gui.client.widget;

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.model.AOI;
import it.geosolutions.georepo.gui.client.model.AOI.AOIKeyValue;
import it.geosolutions.georepo.gui.client.model.GeoConstraint;
import it.geosolutions.georepo.gui.client.service.AOIServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.service.MembersRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.SearchStatus.EnumSearchStatus;
import com.extjs.gxt.ui.client.data.*;
import com.extjs.gxt.ui.client.event.LoadListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.event.WindowListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.ArrayList;
import java.util.List;

public class SearchPagingGeoConstraintWidget extends DGWATCHSearchWidget<GeoConstraint> {

	private MembersRemoteAsync service;

	public SearchPagingGeoConstraintWidget(MembersRemoteAsync service) {
		super();
		this.service = service;
	}

	@Override
	public void setWindowProperties() {
		setHeading("Search for Registered GeoConstraint");
		super.setSize(420, 490);

		super.addWindowListener(new WindowListener() {

			@Override
            public void windowShow(WindowEvent we) {
				searchText = "";
				loader.load(0, 25);
			}

		});
	}

	@Override
	public void createStore() {
		toolBar = new PagingToolBar(25);

		this.proxy = new RpcProxy<PagingLoadResult<GeoConstraint>>() {

			@Override
			protected void load(Object loadConfig,
								AsyncCallback<PagingLoadResult<GeoConstraint>> callback) {
                GeoConstraint searchCriteria = new GeoConstraint();
                searchCriteria.setName(searchText);

//GEOREPO REFACT
//				service.getGeoConstraints((PagingLoadConfig) loadConfig, searchCriteria,
//								callback);
			}
		};

		loader = new BasePagingLoader<PagingLoadResult<ModelData>>(this.proxy);
		loader.setRemoteSort(false);

		store = new ListStore<GeoConstraint>(loader);

		this.toolBar.bind(loader);

		setUpLoadListener();
	}

	@Override
	public void setGridProperties() {
		grid.setAutoExpandColumn(GeoConstraint.GeoConstraintKeyValue.NAME.getValue());

		grid.setWidth(350);
		grid.setHeight(270);
	}

	@Override
	public ColumnModel prepareColumnModel() {
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		ColumnConfig id = new ColumnConfig();
		id.setId(GeoConstraint.GeoConstraintKeyValue.ID.getValue());
		id.setHeader("Id");
		id.setWidth(120);
		configs.add(id);

		ColumnConfig titleColumn = new ColumnConfig();
		titleColumn.setId(GeoConstraint.GeoConstraintKeyValue.NAME.getValue());
		titleColumn.setHeader("Title");
		titleColumn.setWidth(80);
		configs.add(titleColumn);

		return new ColumnModel(configs);
	}

	@Override
	public void select() {
		searchStatus.setBusy("Get GeoConstraint Details....");
		Dispatcher.forwardEvent(DGWATCHEvents.BIND_SELECTED_GEOCONSTRAINT, grid.getSelectionModel().getSelectedItem());
	}

	private void setUpLoadListener() {
		loader.addLoadListener(new LoadListener() {

			@Override
            public void loaderBeforeLoad(LoadEvent le) {
				searchStatus.setBusy("Connection to the Server");
				if (select.isEnabled())
					select.disable();
			}

			@Override
            public void loaderLoad(LoadEvent le) {
				setSearchStatus(EnumSearchStatus.STATUS_SEARCH,
								EnumSearchStatus.STATUS_MESSAGE_SEARCH);
				//				toolBar.enable();
			}

			@Override
            public void loaderLoadException(LoadEvent le) {
				clearGridElements();
				try {
					throw le.exception;
				} catch (ApplicationException e) {
					setSearchStatus(EnumSearchStatus.STATUS_NO_SEARCH,
									EnumSearchStatus.STATUS_MESSAGE_NOT_SEARCH);
				} catch (Throwable e) {
					setSearchStatus(EnumSearchStatus.STATUS_SEARCH_ERROR,
									EnumSearchStatus.STATUS_MESSAGE_SEARCH_ERROR);
				}
			}
		});
	}
}