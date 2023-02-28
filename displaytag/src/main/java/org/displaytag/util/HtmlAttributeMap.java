/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Extends Map providing only a different toString() method which can be used in printing attributes inside an html tag.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class HtmlAttributeMap extends HashMap<String, Object> {

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Attribute value delimiter.
     */
    private static final char DELIMITER = '"';

    /**
     * character between name and value.
     */
    private static final char EQUALS = '=';

    /**
     * space before any attribute.
     */
    private static final char SPACE = ' ';

    /**
     * toString method: returns attributes in the format: attributename="attributevalue" attr2="attrValue2" ...
     *
     * @return String representation of the HtmlAttributeMap
     */
    @Override
    public String toString() {
        // fast exit when no attribute are present
        if (this.size() == 0) {
            return TagConstants.EMPTY_STRING;
        }

        // buffer estimated in number of attributes * 30
        final StringBuilder buffer = new StringBuilder(this.size() * 30);

        // get the entrySet
        final Set<java.util.Map.Entry<String, Object>> entrySet = this.entrySet();

        final Iterator<java.util.Map.Entry<String, Object>> iterator = entrySet.iterator();

        // iterates on attributes
        while (iterator.hasNext()) {
            final Map.Entry<String, Object> entry = iterator.next();

            // append a new attribute
            buffer.append(HtmlAttributeMap.SPACE).append(entry.getKey()).append(HtmlAttributeMap.EQUALS)
                    .append(HtmlAttributeMap.DELIMITER).append(entry.getValue()).append(HtmlAttributeMap.DELIMITER);
        }

        // return
        return buffer.toString();
    }
}
