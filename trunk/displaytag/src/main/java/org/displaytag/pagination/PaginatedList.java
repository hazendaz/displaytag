/**
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://displaytag.sourceforge.net/license.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.displaytag.pagination;

import java.util.List;

import org.displaytag.properties.SortOrderEnum;


/**
 * Interface describing an externally sorted and paginated list.
 * @author JBN
 */
public interface PaginatedList
{

    /**
     * Returns the current partial list
     * @return the current partial list
     */
    List getList();

    /**
     * Returns the page number of the partial list (starts from 1)
     * @return the page number of the partial list
     */
    int getPageNumber();

    /**
     * Returns the number of objects per page. Unless this page is the last one the partial list should thus have a size
     * equal to the result of this method
     * @return the number of objects per page
     */
    int getObjectsPerPage();

    /**
     * Returns the size of the full list
     * @return the size of the full list
     */
    int getFullListSize();

    /**
     * Returns the sort criterion used to externally sort the full list
     * @return the sort criterion used to externally sort the full list
     */
    String getSortCriterion();

    /**
     * Returns the sort direction used to externally sort the full list
     * @return the sort direction used to externally sort the full list
     */
    SortOrderEnum getSortDirection();

    /**
     * Returns an ID for the search used to get the list. It may be null. Such an ID can be necessary if the full list
     * is cached, in a way or another (in the session, in the business tier, or anywhere else), to be able to retrieve
     * the full list from the cache
     * @return the search ID
     */
    String getSearchId();
}