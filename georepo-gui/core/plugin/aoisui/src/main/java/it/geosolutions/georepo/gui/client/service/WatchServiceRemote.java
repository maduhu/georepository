package it.geosolutions.georepo.gui.client.service;

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.ClientShortWatch;
import it.geosolutions.georepo.gui.client.model.ExecutedClientShortWatch;
import it.geosolutions.georepo.gui.client.model.Watch;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * @author Tobia di Pisa
 *
 */
public interface WatchServiceRemote extends RemoteService {

	public static class Util {
		private static WatchServiceRemoteAsync instance;

		public static WatchServiceRemoteAsync getInstance() {
			if (instance == null) {
				instance = (WatchServiceRemoteAsync) GWT
				.create(WatchServiceRemote.class);
				ServiceDefTarget target = (ServiceDefTarget) instance;
				target.setServiceEntryPoint(GWT.getModuleBaseURL()
						+ "WatchServiceRemote");
			}
			return instance;
		}
	}

	/**
	 * @param config
	 * @param searchText
	 * @param callback
	 */
	public PagingLoadResult<ClientShortWatch> getMemberWatches(PagingLoadConfig config, 
			String searchText) throws ApplicationException;
	

	/**
	 * @param watch
	 * @return Watch
	 * @throws ApplicationException
	 */
	public Watch saveWatch(Watch watch) throws ApplicationException;

	/**
	 * @param watch
	 * @return
	 * @throws ApplicationException
	 */
	public Watch updateWatch(Watch watch) throws ApplicationException;
	
	/**
	 * @param id
	 * @return Watch
	 * @throws ApplicationException
	 */
	public Watch getWatchDetails(long id) throws ApplicationException;
	
	/**
	 * @param config
	 * @return PagingLoadResult<ExecutedClientShortWatch>
	 * @throws ApplicationException
	 */
	public PagingLoadResult<ExecutedClientShortWatch> getExecutedWatches(PagingLoadConfig config) throws ApplicationException;
	
	/**
	 * @param id
	 * @throws ApplicationException
	 */
	public void deleteWatch(long id) throws ApplicationException;
}
