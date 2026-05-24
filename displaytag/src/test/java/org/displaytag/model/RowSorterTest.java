/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.model;

import java.util.Comparator;

import org.displaytag.exception.RuntimeLookupException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link RowSorter}.
 */
class RowSorterTest {

    private static final Comparator<Object> OBJECT_COMPARATOR = (left, right) -> ((Comparable<Object>) left)
            .compareTo(right);

    @Test
    void testConstructorRequiresComparator() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RowSorter(0, "name", null, true, null));
    }

    @Test
    void testCompareStaticCellsAscendingAndDescending() {
        final Row left = new Row("left", 0);
        left.addCell(new Cell("a"));
        final Row right = new Row("right", 1);
        right.addCell(new Cell("b"));

        final RowSorter asc = new RowSorter(0, null, null, true, OBJECT_COMPARATOR);
        final RowSorter desc = new RowSorter(0, null, null, false, OBJECT_COMPARATOR);

        Assertions.assertTrue(asc.compare(left, right) < 0);
        Assertions.assertTrue(desc.compare(left, right) > 0);
    }

    @Test
    void testCompareByBeanPropertyAndNullHandling() {
        final Row left = new Row(new Bean("alice"), 0);
        final Row right = new Row(new Bean("bob"), 1);
        final Row nullValue = new Row(new Bean(null), 2);

        final RowSorter sorter = new RowSorter(0, "name", null, true, OBJECT_COMPARATOR);

        Assertions.assertTrue(sorter.compare(left, right) < 0);
        Assertions.assertTrue(sorter.compare(right, left) > 0);
        Assertions.assertTrue(sorter.compare(nullValue, left) < 0);
        Assertions.assertEquals(0, sorter.compare(nullValue, new Row(new Bean(null), 3)));
    }

    @Test
    void testCompareInvalidPropertyThrowsRuntimeLookupException() {
        final Row left = new Row(new Bean("alice"), 0);
        final Row right = new Row(new Bean("bob"), 1);
        final RowSorter sorter = new RowSorter(0, "missingProperty", null, true, OBJECT_COMPARATOR);

        Assertions.assertThrows(RuntimeLookupException.class, () -> sorter.compare(left, right));
    }

    @Test
    void testEqualsAndHashCode() {
        final RowSorter sorter1 = new RowSorter(1, "name", null, true, OBJECT_COMPARATOR);
        final RowSorter sorter2 = new RowSorter(1, "name", null, false, OBJECT_COMPARATOR);
        final RowSorter sorter3 = new RowSorter(2, "name", null, true, OBJECT_COMPARATOR);

        Assertions.assertEquals(sorter1, sorter2);
        Assertions.assertEquals(sorter1.hashCode(), sorter2.hashCode());
        Assertions.assertNotEquals(sorter1, sorter3);
        Assertions.assertNotEquals(sorter1, "x");
    }

    public static class Bean {
        private final String name;

        Bean(final String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
