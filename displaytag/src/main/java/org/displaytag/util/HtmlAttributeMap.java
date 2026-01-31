/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Extends Map providing only a different toString() method which can be used in printing attributes inside an html tag.
 */
public class HtmlAttributeMap extends HashMap<String, Object> {

    /**
     * Serial ID.
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
