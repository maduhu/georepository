package it.geosolutions.georepo.gui.client;

import it.geosolutions.georepo.gui.client.configuration.DGWATCHGlobalConfiguration;
import it.geosolutions.georepo.gui.client.mvc.AppController;
import it.geosolutions.georepo.gui.client.service.ConfigurationRemote;
import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {

	private Dispatcher dispatcher;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		GXT.hideLoadingPanel("loading");
		
		dispatcher = Dispatcher.get();

		dispatcher.addController(new AppController());

		ConfigurationRemote.Util.getInstance().initServerConfiguration(
				new AsyncCallback<DGWATCHGlobalConfiguration>() {

					public void onSuccess(DGWATCHGlobalConfiguration result) {
						DGWATCHUtils.getInstance().setGlobalConfiguration(
								result);
						Dispatcher
								.forwardEvent(DGWATCHEvents.INIT_DGWATCH_WIDGET);

					}

					public void onFailure(Throwable caught) {
						Info.display("Configuration Service Error",
								caught.getMessage());

					}
				});

//		dispatcher.dispatch(DGWATCHEvents.INIT);

	}
}
