/*
 *  Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 * 
 *  GPLv3 + Classpath exception
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.geosolutions.georepo.core.model.util;

import it.geosolutions.georepo.core.model.ServiceFilter;
import it.geosolutions.georepo.core.model.enums.ValueType;
import java.util.Collections;
import java.util.List;

/**
 * Deals with the ServiceFilter.value field.
 * <P>
 * Filter value may hold different types of content, so we'll store it as an
 * encoded string.<BR>
 *
 * <P>See also {@link FilterValueEncoder}.
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class FilterValueDecoder {
    private String value;

    public FilterValueDecoder(String value) {
        this.value = value;
    }

    public FilterValueDecoder(ServiceFilter filter) {
        this(filter.getValue());
    }

    public int getValueCount() {
        // TODO
        return -1;
    }

    public ValueType getType(int index) {
        // TODO
        return ValueType.BOOL;
    }

    public boolean getBoolean(int index) {
        // TODO
        return false;
    }

    public int getInt(int index) {
        // TODO
        return Integer.MIN_VALUE;
    }

    public String getString(int index) {
        // TODO
        return "-";
    }

    public List<String> getStringList(int index) {
        // TODO
        return Collections.EMPTY_LIST;
    }

    /**
     * @return true if the value can be properly parsed
     */
    public boolean check() {
        // TODO
        return true;
    }

}
