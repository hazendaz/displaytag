/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.decorator;

import java.util.ArrayList;
import java.util.List;

import org.displaytag.model.Row;
import org.displaytag.model.RowIterator;
import org.displaytag.model.TableModel;
import org.displaytag.pagination.SmartListHelper;
import org.displaytag.properties.TableProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for TableDecorator with pagination. If you set up pagination and iterate through the entire page, you should
 * always be on the last row at the end. If you have grouped totals, the first group on a page other than the first
 * should start at the page offset, not at 0.
 */
class TableDecoratorPaginationTest {

    /**
     * Test single page.
     */
    @Test
    void testSinglePage() {
        final List<Integer> rawData = new ArrayList<>(10);
        final List<Row> data = new ArrayList<>(10);
        for (int i = 1; i <= 10; i++) {
            rawData.add(i);
            data.add(new Row(i, i));
        }

        final TableProperties props = TableProperties.getInstance(null);
        final SmartListHelper helper = new SmartListHelper(data, data.size(), 10, props, false);
        helper.setCurrentPage(1);
        final List<Row> fullList = helper.getListForCurrentPage();

        final TableModel model = new TableModel(props, "", null);
        model.setRowListPage(fullList);
        model.setPageOffset(helper.getFirstIndexForCurrentPage());

        final MultilevelTotalTableDecorator decorator = new MultilevelTotalTableDecorator();
        decorator.init(null, rawData, model);
        model.setTableDecorator(decorator);

        final RowIterator iterator = model.getRowIterator(false);
        while (iterator.hasNext()) {
            iterator.next();
        }

        Assertions.assertTrue(decorator.isLastRow());
    }

    /**
     * Test first page.
     */
    @Test
    void testFirstPage() {
        final List<Integer> rawData = new ArrayList<>(10);
        final List<Row> data = new ArrayList<>(10);
        for (int i = 1; i <= 10; i++) {
            rawData.add(i);
            data.add(new Row(i, i));
        }

        final TableProperties props = TableProperties.getInstance(null);
        final SmartListHelper helper = new SmartListHelper(data, data.size(), 5, props, false);
        helper.setCurrentPage(1);
        final List<Row> fullList = helper.getListForCurrentPage();

        final TableModel model = new TableModel(props, "", null);
        model.setRowListPage(fullList);
        model.setPageOffset(helper.getFirstIndexForCurrentPage());

        final MultilevelTotalTableDecorator decorator = new MultilevelTotalTableDecorator();
        decorator.init(null, rawData, model);
        model.setTableDecorator(decorator);

        final RowIterator iterator = model.getRowIterator(false);
        while (iterator.hasNext()) {
            iterator.next();
        }

        Assertions.assertTrue(decorator.isLastRow());
    }

    /**
     * Test second page.
     */
    @Test
    void testSecondPage() {
        final List<Integer> rawData = new ArrayList<>(10);
        final List<Row> data = new ArrayList<>(10);
        for (int i = 1; i <= 10; i++) {
            rawData.add(i);
            data.add(new Row(i, i));
        }

        final TableProperties props = TableProperties.getInstance(null);
        final SmartListHelper helper = new SmartListHelper(data, data.size(), 5, props, false);
        helper.setCurrentPage(2);
        final List<Row> fullList = helper.getListForCurrentPage();

        final TableModel model = new TableModel(props, "", null);
        model.setRowListPage(fullList);
        model.setPageOffset(helper.getFirstIndexForCurrentPage());

        final MultilevelTotalTableDecorator decorator = new MultilevelTotalTableDecorator();
        decorator.init(null, rawData, model);
        model.setTableDecorator(decorator);

        final RowIterator iterator = model.getRowIterator(false);
        while (iterator.hasNext()) {
            iterator.next();
        }

        Assertions.assertTrue(decorator.isLastRow());
    }
}
