package it.geosolutions.georepo.gui.client.model;

/**
 * Authorizations for various portions of the GUI.
 *
 * @author gmurray
 */
public enum Authorization {

    /**
     * Authorization to log into the application
      */
    LOGIN,
    /**
     * Authorization to invoke remote calls
      */
    REMOTE,

    /**
     * Authorization to distribute content/metadata to remote nodes
     * @deprecated
     */
    DISTRIBUTION,

    /**
     * Authorization to administer GeoConstraints for members
     * @deprecated
     */
    GEOCONSTRAINT,


    /**
     * Authorization to distribute members to remote nodes
     * @deprecated
     */
    MEMBER_DISTRIBUTION,

    /**
     * Authorization to configure notifications of changes to an AOI
     * @deprecated
     */
    NOTIFICATION,
}