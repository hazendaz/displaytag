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

import java.util.ArrayList;
import java.util.List;

import org.displaytag.properties.SortOrderEnum;
import org.displaytag.test.NumberedItem;


/**
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class SimplePaginatedList implements PaginatedList
{

    /**
     * wrapped list
     */
    private List fullList = new ArrayList();

    /**
     * Number of objects per page.
     */
    private int objectsPerPage;

    /**
     * Current page (starting from 1)
     */
    private int currentPage;

    /**
     * Instantiates a new paginated list.
     */
    public SimplePaginatedList(int objectsPerPage, int currentPage)
    {
        for (int j = 1; j < 11; j++)
        {
            fullList.add(new NumberedItem(j));
        }
        this.objectsPerPage = objectsPerPage;
        this.currentPage = currentPage;
    }

    /**
     * @see org.displaytag.pagination.PaginatedList#getList()
     */
    public List getList()
    {
        int startOffset = objectsPerPage * (currentPage - 1);
        List sublist = fullList.subList(startOffset, Math.min(fullList.size(), startOffset + objectsPerPage));
        return sublist;
    }

    /**
     * @see org.displaytag.pagination.PaginatedList#getPageNumber()
     */
    public int getPageNumber()
    {
        return currentPage;
    }

    /**
     * @see org.displaytag.pagination.PaginatedList#getObjectsPerPage()
     */
    public int getObjectsPerPage()
    {
        return objectsPerPage;
    }

    /**
     * @see org.displaytag.pagination.PaginatedList#getFullListSize()
     */
    public int getFullListSize()
    {
        return fullList.size();
    }

    /**
     * @see org.displaytag.pagination.PaginatedList#getSortCriterion()
     */
    public String getSortCriterion()
    {
        return "number";
    }

    /**
     * @see org.displaytag.pagination.PaginatedList#getSortDirection()
     */
    public SortOrderEnum getSortDirection()
    {
        return SortOrderEnum.DESCENDING;
    }

    /**
     * @see org.displaytag.pagination.PaginatedList#getSearchId()
     */
    public String getSearchId()
    {
        return Integer.toHexString(objectsPerPage * 10000 + currentPage);
    }

}
