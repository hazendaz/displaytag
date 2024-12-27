/*
 * Copyright (C) 2002-2024 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.model;

import java.text.Collator;
import java.util.Comparator;

/**
 * Default comparator. Was previously part of RowSorter.
 */
public class DefaultComparator implements Comparator<Object> {

    /**
     * Use this collator.
     */
    private final Collator collator;

    /**
     * Instantiate a default comparator with no collator specified.
     */
    public DefaultComparator() {
        this(Collator.getInstance());
    }

    /**
     * Instantiate a default comparator with a specified collator.
     *
     * @param collatorToUse
     *            collator instance
     */
    public DefaultComparator(final Collator collatorToUse) {
        this.collator = collatorToUse;
        this.collator.setStrength(Collator.PRIMARY); // ignore case and accents
    }

    /**
     * Compares two given objects. Not comparable objects are compared using their string representation. String
     * comparisons are done using a Collator.
     *
     * @param object1
     *            first parameter
     * @param object2
     *            second parameter
     *
     * @return the value
     */
    @Override
    public int compare(final Object object1, final Object object2) {
        int returnValue;
        if (object1 instanceof String && object2 instanceof String) {
            returnValue = this.collator.compare(object1, object2);
        } else if (object1 instanceof Cell) {
            return ((Cell) object1).compareTo(object2, this.collator);
        } else if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable<Object>) object1).compareTo(object2);
        } else {
            // if object are not null and don't implement comparable, compare using string values
            returnValue = this.collator.compare(object1.toString(), object2.toString());
        }
        return returnValue;
    }
}
