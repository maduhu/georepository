package it.geosolutions.georepo.gui.client.service;

import it.geosolutions.georepo.gui.client.model.ClientShortWatch;
import it.geosolutions.georepo.gui.client.model.ExecutedClientShortWatch;
import it.geosolutions.georepo.gui.client.model.Watch;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Tobia Di Pisa
 *
 */
public interface WatchServiceRemoteAsync {


	/**
	 * @param config
	 * @param searchText
	 */
	public void getMemberWatches(PagingLoadConfig config, String searchText, 
			AsyncCallback<PagingLoadResult<ClientShortWatch>> callback);
	
	/**
	 * @param watch
	 * @param callback
	 */
	public void saveWatch(Watch watch, AsyncCallback<Watch> callback);	

	/**
	 * @param watch
	 * @param callback
	 */
	public void updateWatch(Watch watch, AsyncCallback<Watch> callback);
	
	/**
	 * @param id
	 * @param callback
	 */
	public void getWatchDetails(long id, AsyncCallback<Watch> callback);
	
	/**
	 * @param config
	 * @param callback
	 */
	public void getExecutedWatches(PagingLoadConfig config, AsyncCallback<PagingLoadResult<ExecutedClientShortWatch>> callback);
	
	/**
	 * @param id
	 * @param callback
	 */
	public void deleteWatch(long id, AsyncCallback<Object> callback);
	
}
