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

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;


/**
 * <p>
 * Class to help pagination when dealing with lists.
 * </p>
 * <p>
 * The class will attempt to use <code>java.util.List.subList(int,int)</code> to index into the list for the appropriate
 * page.
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

    public Iterator<Object> getIterator(Object data)
    {
        if (data instanceof List)
        {
            return getIterator((List<Object>) data);
        }

        return IteratorUtils.getIterator(data);
    }

    public Iterator<Object> getIterator(List<Object> data)
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
