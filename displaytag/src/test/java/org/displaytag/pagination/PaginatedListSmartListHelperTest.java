/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.pagination;

import java.util.List;

import org.displaytag.model.Row;
import org.displaytag.properties.SortOrderEnum;
import org.displaytag.properties.TableProperties;
import org.displaytag.util.DefaultHref;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PaginatedListSmartListHelper}.
 */
class PaginatedListSmartListHelperTest {

    @Test
    void testIndexesAndListAccess() {
        final ExposedHelper helper = new ExposedHelper(
                new TestPaginatedList(2, 3, 7, List.of(new Row("a", 0), new Row("b", 1), new Row("c", 2))),
                TableProperties.getInstance(null));

        Assertions.assertEquals(3, helper.getFirstIndexForCurrentPage());
        Assertions.assertEquals(5, helper.lastIndexCurrent());
        Assertions.assertEquals(6, helper.lastIndexFor(10));
        Assertions.assertNotNull(helper.getListForCurrentPage());
        Assertions.assertNull(helper.listFor(1));
        Assertions.assertEquals(3, helper.listFor(2).size());
    }

    @Test
    void testPageNavigationAndSearchSummaryBranches() {
        final TableProperties properties = TableProperties.getInstance(null);

        final ExposedHelper zero = new ExposedHelper(new TestPaginatedList(1, 10, 0, List.of()), properties);
        Assertions.assertNotNull(zero.getPageNavigationBar(new DefaultHref("http://localhost/x"), "p"));
        Assertions.assertNotNull(zero.getSearchResultsSummary());

        final ExposedHelper one = new ExposedHelper(new TestPaginatedList(1, 10, 1, List.of(new Row("a", 0))),
                properties);
        Assertions.assertNotNull(one.getSearchResultsSummary());

        final ExposedHelper all = new ExposedHelper(
                new TestPaginatedList(1, 10, 3, List.of(new Row("a", 0), new Row("b", 1), new Row("c", 2))),
                properties);
        Assertions.assertNotNull(all.getSearchResultsSummary());

        final ExposedHelper some = new ExposedHelper(
                new TestPaginatedList(2, 2, 5, List.of(new Row("a", 2), new Row("b", 3))), properties);
        Assertions.assertNotNull(some.getPageNavigationBar(new DefaultHref("http://localhost/x"), "p"));
        Assertions.assertNotNull(some.getSearchResultsSummary());
        Assertions.assertTrue(some.toString().contains("paginatedList"));
    }

    private static final class ExposedHelper extends PaginatedListSmartListHelper {
        private ExposedHelper(final PaginatedList<Row> paginatedList, final TableProperties tableProperties) {
            super(paginatedList, tableProperties);
        }

        private int lastIndexCurrent() {
            return this.getLastIndexForCurrentPage();
        }

        private int lastIndexFor(final int pageNumber) {
            return this.getLastIndexForPage(pageNumber);
        }

        private List<Row> listFor(final int pageNumber) {
            return this.getListForPage(pageNumber);
        }
    }

    private static final class TestPaginatedList implements PaginatedList<Row> {
        private final int pageNumber;
        private final int perPage;
        private final int fullSize;
        private final List<Row> list;

        private TestPaginatedList(final int pageNumber, final int perPage, final int fullSize, final List<Row> list) {
            this.pageNumber = pageNumber;
            this.perPage = perPage;
            this.fullSize = fullSize;
            this.list = list;
        }

        @Override
        public List<Row> getList() {
            return this.list;
        }

        @Override
        public int getPageNumber() {
            return this.pageNumber;
        }

        @Override
        public int getObjectsPerPage() {
            return this.perPage;
        }

        @Override
        public int getFullListSize() {
            return this.fullSize;
        }

        @Override
        public String getSortCriterion() {
            return "name";
        }

        @Override
        public SortOrderEnum getSortDirection() {
            return SortOrderEnum.ASCENDING;
        }

        @Override
        public String getSearchId() {
            return "search";
        }
    }
}
