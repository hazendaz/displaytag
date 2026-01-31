/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.test;

import java.util.Comparator;

import org.apache.commons.beanutils2.ConvertUtils;

/**
 * Sorts 2 numbers, converted from objects using beanutils Converters.
 */
public class NumberComparator implements Comparator<Object> {

    /**
     * Compare.
     *
     * @param obj1
     *            the obj 1
     * @param obj2
     *            the obj 2
     *
     * @return the int
     *
     * @see Comparator#compare(Object, Object)
     */
    @Override
    public int compare(final Object obj1, final Object obj2) {
        double dbl1 = 0;
        if (obj1 instanceof Number) {
            dbl1 = ((Number) obj1).doubleValue();
        } else if (obj1 != null) {
            dbl1 = ((Number) ConvertUtils.convert(obj1.toString(), Number.class)).doubleValue();
        }

        double dbl2 = 0;
        if (obj2 instanceof Number) {
            dbl2 = ((Number) obj2).doubleValue();
        } else if (obj1 != null) {
            dbl2 = ((Number) ConvertUtils.convert(obj2.toString(), Number.class)).doubleValue();
        }

        return Double.valueOf(dbl1).compareTo(Double.valueOf(dbl2));
    }
}
