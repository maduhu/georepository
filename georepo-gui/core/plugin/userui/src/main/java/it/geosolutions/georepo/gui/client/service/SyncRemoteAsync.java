package it.geosolutions.georepo.gui.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 */
public interface SyncRemoteAsync {

    /**
     * Execute the distribution watch with specified id. Wait the designated
     * interval on the server before executing
     * 
     * @param watchId
     *            long id of distribution watch
     * @param delayInSeconds
     *            int number of seconds to delay before executing distribution
     *            watch
     */
    public void runDistribution(long watchId, int delayInSeconds, AsyncCallback<Void> callback);

    /**
     * Delete the a the indicated set of features on the indicated set of remote
     * nodes
     * 
     * @param featureIds
     *            List<String> of feature ids associated with imagery to delete
     * @param nodeIds
     *            List<Long> of node identifiers where imagery should be deleted
     */
    public void deleteImagery(List<String> featureIds, List<Long> nodeIds, AsyncCallback<Void> callback);
}
