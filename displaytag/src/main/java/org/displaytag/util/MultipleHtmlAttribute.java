/*
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
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

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * Object used to contain html multiple attribute value (for the "class" attribute).
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
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