/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.pagination;

import java.util.List;

import org.displaytag.properties.SortOrderEnum;

/**
 * Interface describing an externally sorted and paginated list.
 */
public interface PaginatedList<T> {

    /**
     * Returns the current partial list.
     *
     * @return the current partial list
     */
    List<T> getList();

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
