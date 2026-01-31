/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.util;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * Object used to contain html multiple attribute value (for the "class" attribute).
 */
public class MultipleHtmlAttribute implements Cloneable {

    /**
     * Sets containing splitted attribute values.
     */
    private final Set<String> attributeSet;

    /**
     * Constructor for MultipleHtmlAttribute.
     *
     * @param attributeValue
     *            String
     */
    public MultipleHtmlAttribute(final String attributeValue) {
        this.attributeSet = new LinkedHashSet<>();
        this.addAllAttributesFromArray(StringUtils.split(attributeValue));
    }

    /**
     * Adds attributes from an array.
     *
     * @param attributes
     *            Object[] Array containing attributes
     */
    private void addAllAttributesFromArray(final String[] attributes) {
        if (attributes == null) {
            return;
        }

        // number of attributes to add
        final int length = attributes.length;

        // add all the splitted attributes
        for (int j = 0; j < length; j++) {

            // don't add if empty
            if (!StringUtils.isEmpty(attributes[j])) {
                this.attributeSet.add(attributes[j]);
            }

        }
    }

    /**
     * Returns the list of attributes separated by a space.
     *
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();

        final Iterator<String> iterator = this.attributeSet.iterator();

        while (iterator.hasNext()) {
            // apend next value
            buffer.append(iterator.next());
            if (iterator.hasNext()) {
                // append a space if there are more
                buffer.append(' ');
            }
        }

        return buffer.toString();
    }

    /**
     * Adds a value to the attribute.
     *
     * @param attributeValue
     *            value to add to the attribute
     */
    public void addAttributeValue(final String attributeValue) {
        // don't add if empty
        if (!StringUtils.isEmpty(attributeValue)) {
            this.attributeSet.add(attributeValue);
        }

    }

    /**
     * Return true if this MultipleHtmlValue doesn't store any value.
     *
     * @return <code>true</code> if this MultipleHtmlValue doesn't store any value
     */
    public boolean isEmpty() {
        return this.attributeSet.isEmpty();
    }

    /**
     * Clone.
     *
     * @return the object
     *
     * @see java.lang.Object#clone()
     */
    @Override
    protected Object clone() {
        MultipleHtmlAttribute clone;

        try {
            clone = (MultipleHtmlAttribute) super.clone();
        } catch (final CloneNotSupportedException e) {
            // should never happen
            throw new RuntimeException(e);
        }

        // copy attributes
        clone.addAllAttributesFromArray(this.attributeSet.toArray(new String[this.attributeSet.size()]));

        return clone;
    }

}
