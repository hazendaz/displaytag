/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
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
