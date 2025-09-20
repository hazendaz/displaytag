/*
 * Copyright (C) 2002-2025 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.pagination;

import java.util.ArrayList;
import java.util.List;

import org.displaytag.model.Row;
import org.displaytag.properties.SortOrderEnum;
import org.displaytag.test.NumberedItem;

/**
 * The Class SimplePaginatedList.
 */
public class SimplePaginatedList implements PaginatedList<Row> {

    /** wrapped list. */
    private final List<Row> fullList = new ArrayList<>();

    /**
     * Number of objects per page.
     */
    private final int objectsPerPage;

    /** Current page (starting from 1). */
    private final int currentPage;

    /**
     * Instantiates a new paginated list.
     *
     * @param objectsPerPage
     *            the objects per page
     * @param currentPage
     *            the current page
     */
    public SimplePaginatedList(final int objectsPerPage, final int currentPage) {
        for (int j = 1; j < 11; j++) {
            this.fullList.add(new NumberedItem(j));
        }
        this.objectsPerPage = objectsPerPage;
        this.currentPage = currentPage;
    }

    /**
     * Gets the list.
     *
     * @return the list
     *
     * @see org.displaytag.pagination.PaginatedList#getList()
     */
    @Override
    public List<Row> getList() {
        final int startOffset = this.objectsPerPage * (this.currentPage - 1);
        return this.fullList.subList(startOffset, Math.min(this.fullList.size(), startOffset + this.objectsPerPage));
    }

    /**
     * Gets the page number.
     *
     * @return the page number
     *
     * @see org.displaytag.pagination.PaginatedList#getPageNumber()
     */
    @Override
    public int getPageNumber() {
        return this.currentPage;
    }

    /**
     * Gets the objects per page.
     *
     * @return the objects per page
     *
     * @see org.displaytag.pagination.PaginatedList#getObjectsPerPage()
     */
    @Override
    public int getObjectsPerPage() {
        return this.objectsPerPage;
    }

    /**
     * Gets the full list size.
     *
     * @return the full list size
     *
     * @see org.displaytag.pagination.PaginatedList#getFullListSize()
     */
    @Override
    public int getFullListSize() {
        return this.fullList.size();
    }

    /**
     * Gets the sort criterion.
     *
     * @return the sort criterion
     *
     * @see org.displaytag.pagination.PaginatedList#getSortCriterion()
     */
    @Override
    public String getSortCriterion() {
        return "number";
    }

    /**
     * Gets the sort direction.
     *
     * @return the sort direction
     *
     * @see org.displaytag.pagination.PaginatedList#getSortDirection()
     */
    @Override
    public SortOrderEnum getSortDirection() {
        return SortOrderEnum.DESCENDING;
    }

    /**
     * Gets the search id.
     *
     * @return the search id
     *
     * @see org.displaytag.pagination.PaginatedList#getSearchId()
     */
    @Override
    public String getSearchId() {
        return Integer.toHexString(this.objectsPerPage * 10000 + this.currentPage);
    }

}
