package it.geosolutions.georepo.gui.client;

/**
 * String utilities.  Unfortunately, this is a copy of a class in dg-common.  But, because of the way GWT
 * works, the existing class couldn't be utilized in the GUI (i.e., compiled to JavaScript by GWT), so we
 * had to add a parallel implementation.
 */
public class StringUtil {

    /**
     * Determine if the string is empty (blank or all whitespace) or null.
     */
	public static boolean isEmpty(String value){
        return (value == null) || (value.trim().length() == 0);
	}

}