package it.geosolutions.georepo.api.dto;

/**
 * @author etj
 */
public enum Authority {
    /**
     * Authorization to log into the application
     */
    LOGIN

    /**
     * Authorization to perform remote calls
     */
    , REMOTE

    ;
}
