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
package org.displaytag.pagination;

import java.util.List;

import org.displaytag.model.Row;
import org.displaytag.properties.SortOrderEnum;

/**
 * Interface describing an externally sorted and paginated list.
 *
 * @author JBN
 */
public interface PaginatedList {

    /**
     * Returns the current partial list.
     *
     * @return the current partial list
     */
    List<Row> getList();

    /**
     * Returns the page number of the partial list (starts from 1).
     *
     * @return the page number of the partial list
     */
    int getPageNumber();

    /**
     * Returns the number of objects per page. Unless this page is the last one the partial list should thus have a size
     * equal to the result of this method
     *
     * @return the number of objects per page
     */
    int getObjectsPerPage();

    /**
     * Returns the size of the full list.
     *
     * @return the size of the full list
     */
    int getFullListSize();

    /**
     * Returns the sort criterion used to externally sort the full list.
     *
     * @return the sort criterion used to externally sort the full list
     */
    String getSortCriterion();

    /**
     * Returns the sort direction used to externally sort the full list.
     *
     * @return the sort direction used to externally sort the full list
     */
    SortOrderEnum getSortDirection();

    /**
     * Returns an ID for the search used to get the list. It may be null. Such an ID can be necessary if the full list
     * is cached, in a way or another (in the session, in the business tier, or anywhere else), to be able to retrieve
     * the full list from the cache
     *
     * @return the search ID
     */
    String getSearchId();
}