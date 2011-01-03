/*
 * $Header: it.geosolutions.georepo.gui.client.configuration.MenuClientTool,v. 0.1 30/set/2010 15.47.58 created by giuseppe $
 * $Revision: 0.1 $
 * $Date: 30/set/2010 15.47.58 $
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
package it.geosolutions.georepo.gui.client.configuration;

import it.geosolutions.georepo.gui.client.model.Authorization;
import com.extjs.gxt.ui.client.widget.form.ComboBox;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a dropdown control on the toolbar.
 *
 * @author gmurray
 *
 */
public class DropdownClientTool extends GenericClientTool {

    private static final long serialVersionUID = 8377394857738349837L;

    private String label;
	private boolean enabled = true;
    private String defaultValue;
    private boolean allowBlank = false;
    private List<DropdownOption> dropdownOptions = new ArrayList<DropdownOption>();

    /**
     * Return the label text that should be displayed with the dropdown.
     *
     * @return the text of the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label text that should be displayed with the dropdown.
     *
     * @param label the text of the label
     */
    public void setLabel(String label) {
        this.label = label;
    }

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

    /**
     * Setter for the dropdown options "model" for this DropdownClientTool.
     *
     * @param options the model for the dropdown data
     */
    public void setDropdownOptions(List<DropdownOption> options) {
        this.dropdownOptions = options;
    }

    /**
     * Returns the full set of DropdownOptions for this dropdown.
     *
     * @return List of DropdownOption objects
     */
    public List<DropdownOption> getDropdownOptions() {
        return this.dropdownOptions;
    }

    /**
     * Adds a dropdown option to the list of dropdown options for this dropdown.
     *
     * @param d the new DropdownOption
     */
    public void addOption(DropdownOption d) {
        this.dropdownOptions.add(d);
    }

    /**
     * Gets data value that should be used as the default selection when the dropdown is first rendered.
     *
     * @return the default data value for the dropdown
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Gets data value that should be used as the default selection when the dropdown is first rendered.
     *
     * @param defaultValue the default value to use
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Determines whether the user is allowed to choose a blank value for the dropdown.
     *
     * @return
     */
    public boolean isAllowBlank() {
        return allowBlank;
    }

    /**
     * Sets whether the user is allowed to choose a blank value for the dropdown.
     *
     * @param allowBlank true if the user is allowed to have "no selection", false otherwise
     */
    public void setAllowBlank(boolean allowBlank) {
        this.allowBlank = allowBlank;
    }

    /**
     * Performs a lookup to get the correct display value for the passed in data value.  This method is
     * provided in order to perform ad-hoc i18n lookups.  This is because at instantiation time (i.e., when 
     * Spring instantiates the bean representing this DropdownClientTool) I18nProvider is not available, and when
     * referenced/called in the init() method or constructor actually causes the GWT application to not start
     * up due to some sort of JavaScript problem in the base *.nocache.js file. By using this method, we defer
     * the i18n lookup until render time, when we know that the I18nProvider will be available.  It's a little
     * clunky, but it's the only way that I could figure to i18n these types of values.
     *
     * @param dataValue the data value to look up
     * @return the i18n display value for the passed in data value, or null if there is no corresponding i18n value
     */
    public String getDropdownOptionDisplayValue(String dataValue) {
        return null;
    }

    /**
     * Provides the ability to filter values in the dropdown based on passed-in authorizations.  The changes
     * to the ComboBox's list store are made in place in the ComboBox.
     *
     * @param combo the ComboBox that is backed by this DropdownClientTool
     * @param auths the authorizations that should be applied to the data in the dropdown
     */
    public void injectSecurity(ComboBox combo, List<Authorization> auths) {
        // default implementation - do nothing
    }
}