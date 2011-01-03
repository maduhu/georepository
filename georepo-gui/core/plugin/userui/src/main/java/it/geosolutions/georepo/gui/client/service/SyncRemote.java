package it.geosolutions.georepo.gui.client.service;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * 
 */
public interface SyncRemote extends RemoteService {

    public static class Util {
        private static SyncRemoteAsync instance;

        public static SyncRemoteAsync getInstance() {
            if (instance == null) {
                instance = (SyncRemoteAsync) GWT.create(SyncRemote.class);
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint(GWT.getModuleBaseURL() + "SyncRemote");
            }
            return instance;
        }
    }

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
    public void runDistribution(long watchId, int delayInSeconds);

    /**
     * Delete the a the indicated set of features on the indicated set of remote
     * nodes
     * 
     * @param featureIds
     *            List<String> of feature ids associated with imagery to delete
     * @param nodeIds
     *            List<Long> of node identifiers where imagery should be deleted
     */
    public void deleteImagery(List<String> featureIds, List<Long> nodeIds);
}
