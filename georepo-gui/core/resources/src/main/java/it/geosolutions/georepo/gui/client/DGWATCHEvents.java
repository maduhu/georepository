/*
 * $Header: it.geosolutions.georepo.gui.client.DGWATCHEvents,v. 0.1 08/lug/2010 10.44.54 created by frank $
 * $Revision: 0.1 $
 * $Date: 08/lug/2010 10.44.54 $
 *
 * ====================================================================
 *
 * Copyright (C) 2010 GeoSolutions S.A.S.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. 
 *
 * ====================================================================
 *
 * This software consists of voluntary contributions made by developers
 * of GeoSolutions.  For more information on GeoSolutions, please see
 * <http://www.geo-solutions.it/>.
 *
 */
package it.geosolutions.georepo.gui.client;

import com.extjs.gxt.ui.client.event.EventType;

/**
 * @author Tobia Di Pisa
 * 
 */
public class DGWATCHEvents {

	public static final EventType INIT = new EventType();

	public static final EventType INIT_RESOURCES_MODULE = new EventType();

	public static final EventType SEND_ALERT_MESSAGE = new EventType();

	public static final EventType SEND_ERROR_MESSAGE = new EventType();

	public static final EventType SEND_INFO_MESSAGE = new EventType();

	public static final EventType INIT_AOIS_UI_MODULE = new EventType();

	public static final EventType INIT_USER_UI_MODULE = new EventType();

    /**
     * This event triggers the display of the login widget (the username/password fields).
     */
	public static final EventType INIT_DGWATCH_WIDGET = new EventType();

    public static final EventType LOGIN = new EventType();

    public static final EventType LOGIN_SUCCESS = new EventType();

	public static final EventType INIT_DGWATCH_MAIN_UI = new EventType();

	public static final EventType ATTACH_MAP_WIDGET = new EventType();

	public static final EventType UPDATE_MAP_SIZE = new EventType();

	public static final EventType ATTACH_USER_WIDGET = new EventType();

    public static final EventType ATTACH_AOI_WIDGET = new EventType();

	public static final EventType ATTACH_AOI_FILTER = new EventType();

	public static final EventType ATTACH_AOI_TAB_WIDGET = new EventType();

	public static final EventType SHOW_UPLOAD_WIDGET = new EventType();

	public static final EventType AOI_MANAGEMENT_BIND = new EventType();

	public static final EventType AOI_MANAGEMENT_UNBIND = new EventType();

	public static final EventType SHARE_AOI_ENABLE = new EventType();

	public static final EventType SHARE_AOI_DISABLE = new EventType();

	public static final EventType SHOW_CHOOSER_USER_WIDGET = new EventType();

	public static final EventType UNSHARE_AOI = new EventType();

	public static final EventType SHARE_AOI = new EventType();

	public static final EventType ATTACH_TOOLBAR = new EventType();

	public static final EventType ACTIVATE_DRAW_FEATURES = new EventType();

	public static final EventType DEACTIVATE_DRAW_FEATURES = new EventType();

	public static final EventType SHOW_ADD_USER_WIDGET = new EventType();

	public static final EventType SAVE_USER = new EventType();

	public static final EventType SHOW_SEARCH_USER_WIDGET = new EventType();

	public static final EventType BIND_SELECTED_USER = new EventType();

	public static final EventType DELETE_USER = new EventType();

	public static final EventType SHOW_UPDATE_USER_WIDGET = new EventType();

	public static final EventType UPDATE_USER = new EventType();

	public static final EventType SHOW_ADD_AOI = new EventType();

    public static final EventType SHOW_ADD_GEOCONSTRAINT = new EventType();

	public static final EventType ENABLE_DRAW_BUTTON = new EventType();

	public static final EventType DISABLE_DRAW_BUTTON = new EventType();

	public static final EventType ERASE_AOI_FEATURES = new EventType();

	public static final EventType INJECT_WKT = new EventType();

	public static final EventType INJECT_WKT_FROM_SHP = new EventType();

    public static final EventType SAVE_AOI = new EventType();

    public static final EventType SAVE_GEOCONSTRAINT = new EventType();

	public static final EventType SAVE_AOI_FROM_SHP = new EventType();

    public static final EventType SEARCH_AOI = new EventType();

    public static final EventType SEARCH_GEOCONSTRAINT = new EventType();

    public static final EventType BIND_SELECTED_AOI = new EventType();

    public static final EventType BIND_SELECTED_GEOCONSTRAINT = new EventType();

	public static final EventType DELETE_AOI = new EventType();

	public static final EventType UPDATE_AOI = new EventType();

    public static final EventType DELETE_GEOCONSTRAINT = new EventType();

	public static final EventType NOTIFY_UNSHARE_ERROR = new EventType();

	public static final EventType NOTIFY_UNSHARE_SUCCESS = new EventType();

	public static final EventType SEARCH_USER_AOI = new EventType();

	public static final EventType RESET_AOI_GRID = new EventType();

	public static final EventType DRAW_AOI_ON_MAP = new EventType();

    public static final EventType AOI_SELECTED = new EventType();

    public static final EventType GEOCONSTRAINT_SELECTED = new EventType();

    public static final EventType GEOCONSTRAINT_DELETED = new EventType();

	public static final EventType USER_SELECTED = new EventType();

	public static final EventType EXIST_DEFAULT_FILTER = new EventType();

	public static final EventType SET_USER_PREF = new EventType();

	public static final EventType UNBIND_FILTER_WIDGET = new EventType();

	public static final EventType CHECK_AOI_OWNER = new EventType();

	public static final EventType UNBIND_USER_WIDGET = new EventType();

	public static final EventType LOGOUT = new EventType();

	public static final EventType CHECK_RELATED_USERS_COUNT = new EventType();

	public static final EventType SEARCH_USER_GEORSS = new EventType();

	public static final EventType RESET_RSS_GRID = new EventType();

	public static final EventType CHECK_AOI_STATUS = new EventType();

	public static final EventType SESSION_EXPIRED = new EventType();

	public static final EventType ZOOM_TO_CENTER = new EventType();

    public static final EventType QUARTZ_TRIGGER = new EventType();

    public static final EventType DELETE_CONTENT = new EventType();

    public static final EventType ADMIN_MODE_CHANGE = new EventType();
    
    public static final EventType ATTACH_MEMBER_WIDGET = new EventType();

    public static final EventType ATTACH_GEOCONSTRAINT_MEMBER_WIDGET = new EventType();

    public static final EventType SHOW_SEARCH_MEMBER_WIDGET = new EventType();
    
    public static final EventType BIND_SELECTED_MEMBER = new EventType();
    
    public static final EventType UNBIND_SELECTED_MEMBER = new EventType();
    
    public static final EventType ATTACH_WATCHES_WIDGET = new EventType();
    
    public static final EventType BINDING_WATCH_WIDGET = new EventType();
    
    public static final EventType UNBINDING_WATCH_WIDGET = new EventType();
    
    public static final EventType SEARCH_MEMBER_WATCHES = new EventType();
    
    public static final EventType SAVE_WATCH = new EventType();
    
    public static final EventType UPDATE_WATCH = new EventType();
    
    public static final EventType LOAD_WATCH_AOI = new EventType();
    
    public static final EventType RESET_WATCH_GRID = new EventType();
    
    public static final EventType RUN_WATCH = new EventType();
    
    public static final EventType TRIGGER_WATCH = new EventType();
    
    public static final EventType UPDATE_TITLE = new EventType();
    
    public static final EventType DELETE_WATCH = new EventType();
    
    public static final EventType REFRESH_WATCH_GRID = new EventType();
    

    /**
     * The SAVE event is meant to be a "generic" save message that lets all listeners know that they
     * should save their current data (whatever that means for a particular listener).
     */
    public static final EventType SAVE = new EventType();

    public static final EventType ATTACH_GEOCONSTRAINT_AOI_WIDGET = new EventType();

    public static final EventType ATTACH_NODE_SELECTION_WIDGET = new EventType();

    public static final EventType RELOAD_GEOCONSTRAINTS = new EventType();

    public static final EventType BIND_SOURCE_DISTRIBUTION_NODES = new EventType();

    public static final EventType BIND_MEMBER_DISTRIBUTION_NODES = new EventType();

    public static final EventType MEMBER_GEOCONSTRAINT_UNBOUND = new EventType();

    public static final EventType SAVE_GEOCONSTRAINT_FROM_SHP = new EventType();

}
