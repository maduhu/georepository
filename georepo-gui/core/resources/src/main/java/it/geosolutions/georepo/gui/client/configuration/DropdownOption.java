package it.geosolutions.georepo.gui.client.configuration;

import java.io.Serializable;

/**
 * Represents a dropdown option for the DropdownClientTool.  Essentially, this is the model for the DropdownClientTool.
 */
public class DropdownOption implements Serializable {

    public static final String LABEL_KEY = "label";
    public static final String VALUE_KEY = "value";

    private String label;
    private String value;

    public DropdownOption() {}

    public DropdownOption(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
