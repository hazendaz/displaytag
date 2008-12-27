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

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;


/**
 * <p>
 * Class to help pagination when dealing with lists.
 * </p>
 * <p>
 * The class will attempt to use <code>java.util.List.subList(int,int)</code> to index into the list for the
 * appropriate page.
 * </p>
 * <p>
 * If the list does not contain enough elements to support the sub list, then the first <code>pageSize</code> elements
 * will be returned.
 * </p>
 * @author <a href="mailto:kevin.a.conaway@gmail.com">Kevin Conaway</a>
 */
public class PaginationHelper
{

    private final int pageNumber;

    private final int pageSize;

    public PaginationHelper(int pageNumber, int pageSize)
    {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public Iterator getIterator(Object data)
    {
        if (data instanceof List)
        {
            return getIterator((List) data);
        }

        return IteratorUtils.getIterator(data);
    }

    public Iterator getIterator(List data)
    {
        int start = getStart(data.size());
        int end = getEnd(data.size(), start);
        return data.subList(start, end).iterator();
    }

    protected int getStart(int listSize)
    {
        int start = (pageNumber - 1) * pageSize;

        if (start >= listSize)
        {
            start = 0;
        }

        return start;
    }

    protected int getEnd(int listSize, int start)
    {

        int end = pageNumber * pageSize;

        if (end > listSize)
        {
            end = listSize;
            if ((end - start) > pageSize)
            {
                end = pageSize;
            }
        }

        return end;
    }
}
