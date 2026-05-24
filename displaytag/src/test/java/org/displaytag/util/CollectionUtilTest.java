/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.util;

import java.lang.reflect.Constructor;
import java.util.List;

import org.displaytag.model.Row;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CollectionUtil}.
 */
class CollectionUtilTest {

    @Test
    void testGetListFromListInputWithExplicitSize() {
        final List<Row> rows = List.of(new Row("r0", 0), new Row("r1", 1), new Row("r2", 2), new Row("r3", 3));

        final List<Row> cropped = CollectionUtil.getListFromObject(rows, 1, 2);

        Assertions.assertEquals(2, cropped.size());
        Assertions.assertSame(rows.get(1), cropped.get(0));
        Assertions.assertSame(rows.get(2), cropped.get(1));
    }

    @Test
    void testGetListFromListInputWithZeroLimitReturnsRemainingItems() {
        final List<Row> rows = List.of(new Row("r0", 0), new Row("r1", 1), new Row("r2", 2), new Row("r3", 3));

        final List<Row> cropped = CollectionUtil.getListFromObject(rows, 2, 0);

        Assertions.assertEquals(2, cropped.size());
        Assertions.assertSame(rows.get(2), cropped.get(0));
        Assertions.assertSame(rows.get(3), cropped.get(1));
    }

    @Test
    void testGetListFromListInputWithStartBeyondSizeReturnsEmpty() {
        final List<Row> rows = List.of(new Row("r0", 0), new Row("r1", 1));

        final List<Row> cropped = CollectionUtil.getListFromObject(rows, 5, 2);

        Assertions.assertTrue(cropped.isEmpty());
    }

    @Test
    void testGetListFromArrayInputUsesIteratorPath() {
        final Row[] rows = new Row[] { new Row("r0", 0), new Row("r1", 1), new Row("r2", 2), new Row("r3", 3) };

        final List<Row> cropped = CollectionUtil.getListFromObject(rows, 1, 2);

        Assertions.assertEquals(2, cropped.size());
        Assertions.assertSame(rows[1], cropped.get(0));
        Assertions.assertSame(rows[2], cropped.get(1));
    }

    @Test
    void testPrivateConstructorIsInvokableForCoverage() throws Exception {
        final Constructor<CollectionUtil> constructor = CollectionUtil.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        final CollectionUtil instance = constructor.newInstance();

        Assertions.assertNotNull(instance);
    }
}
