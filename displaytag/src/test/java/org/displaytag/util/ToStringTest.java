/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.util;

import java.util.ArrayList;

import org.apache.commons.lang3.ClassUtils;
import org.displaytag.model.Cell;
import org.displaytag.model.Column;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.TableModel;
import org.displaytag.pagination.NumberedPage;
import org.displaytag.pagination.Pagination;
import org.displaytag.pagination.SmartListHelper;
import org.displaytag.properties.TableProperties;
import org.displaytag.tags.ColumnTag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Check that toString() methods are constructed appropriately, uses the correct style and that there aren't stupid NPE
 * bugs in them.
 */
class ToStringTest {

    /**
     * ToString methods should be costructed using toStringBuilder and the <code>ShortToStringStyle.SHORT_STYLE</code>
     * style.
     *
     * @param object
     *            test instance
     */
    private void checkToString(final Object object) {
        final String toString = object.toString();
        Assertions.assertTrue(toString.startsWith(ClassUtils.getShortClassName(object, null)));
    }

    /**
     * ToString() test.
     */
    @Test
    void testSmartListHelper() {
        this.checkToString(new SmartListHelper(new ArrayList<>(), 100, 10, TableProperties.getInstance(null), false));
    }

    /**
     * ToString() test.
     */
    @Test
    void testNumberedPage() {
        this.checkToString(new NumberedPage(1, false));
    }

    /**
     * ToString() test.
     */
    @Test
    void testPagination() {
        this.checkToString(new Pagination(null, null, null));
    }

    /**
     * ToString() test.
     */
    @Test
    void testCell() {
        this.checkToString(new Cell(null));
    }

    /**
     * ToString() test.
     */
    @Test
    void testHeaderCell() {
        this.checkToString(new HeaderCell());
    }

    /**
     * ToString() test.
     */
    @Test
    void testColumn() {
        this.checkToString(new Column(new HeaderCell(), null, null));
    }

    /**
     * ToString() test.
     */
    @Test
    void testRow() {
        this.checkToString(new Row(null, 0));
    }

    /**
     * ToString() test.
     */
    @Test
    void testTableModel() {
        this.checkToString(new TableModel(null, null, null));
    }

    /**
     * ToString() test.
     */
    @Test
    void testColumnTag() {
        this.checkToString(new ColumnTag());
    }

}
