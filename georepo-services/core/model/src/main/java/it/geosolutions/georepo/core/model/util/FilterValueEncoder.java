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

import it.geosolutions.georepo.core.model.enums.ValueType;
import java.util.List;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * Creates an encoded composite value.
 * <P>
 * In order to create a filter value, you will add() as many values as you like,
 * and then you'll retrieve the string to set into ServiceFilter.setValue using
 * {@link #encodeValue() encodevalue()}.
 *
 * <P>See also {@link FilterValueDecoder}.
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class FilterValueEncoder {

    Element e = new Element("value");

    public void add(boolean b) {
        e.addContent(new Element(ValueType.BOOL.name()).setText(Boolean.toString(b)));
    }

    public void add(int i) {
        e.addContent(new Element(ValueType.INT.name()).setText(Integer.toString(i)));
    }

    public void add(String s) {
        e.addContent(new Element(ValueType.STRING.name()).setText(s));
    }

    public void add(List<String> l) {
        Element list = new Element(ValueType.STRINGLIST.name());
        e.addContent(list);
        for (String string : l) {
            list.addContent(new Element(ValueType.STRING.name()).setText(string));
        }
    }

    public String encodeValue() {
        XMLOutputter outputter = new XMLOutputter(Format.getRawFormat());
        return outputter.outputString(e);
    }

}
