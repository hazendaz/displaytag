/**
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
    private List<Object> fullList = new ArrayList<Object>();

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
    @Override
    public List<Object> getList()
    {
        int startOffset = objectsPerPage * (currentPage - 1);
        List<Object> sublist = fullList.subList(startOffset, Math.min(fullList.size(), startOffset + objectsPerPage));
        return sublist;
    }

    /**
     * @see org.displaytag.pagination.PaginatedList#getPageNumber()
     */
    @Override
    public int getPageNumber()
    {
        return currentPage;
    }

    /**
     * @see org.displaytag.pagination.PaginatedList#getObjectsPerPage()
     */
    @Override
    public int getObjectsPerPage()
    {
        return objectsPerPage;
    }

    /**
     * @see org.displaytag.pagination.PaginatedList#getFullListSize()
     */
    @Override
    public int getFullListSize()
    {
        return fullList.size();
    }

    /**
     * @see org.displaytag.pagination.PaginatedList#getSortCriterion()
     */
    @Override
    public String getSortCriterion()
    {
        return "number";
    }

    /**
     * @see org.displaytag.pagination.PaginatedList#getSortDirection()
     */
    @Override
    public SortOrderEnum getSortDirection()
    {
        return SortOrderEnum.DESCENDING;
    }

    /**
     * @see org.displaytag.pagination.PaginatedList#getSearchId()
     */
    @Override
    public String getSearchId()
    {
        return Integer.toHexString(objectsPerPage * 10000 + currentPage);
    }

}
