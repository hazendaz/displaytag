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
package org.displaytag.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;


/**
 * Utility methods for collection handling.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public final class CollectionUtil
{

    /**
     * Don't instantiate a CollectionUtil.
     */
    private CollectionUtil()
    {
        // unused
    }

    /**
     * Create a list of objects taken from the given iterator and crop the resulting list according to the startIndex
     * and numberOfItems parameters.
     * @param iterator Iterator
     * @param startIndex int starting index
     * @param numberOfItems int number of items to keep in the list
     * @return List with values taken from the given object, cropped according to startIndex and numberOfItems
     * parameters
     */
    private static List<Object> getSubList(Iterator< ? > iterator, int startIndex, int numberOfItems)
    {

        List<Object> croppedList = new ArrayList<Object>(numberOfItems);

        int skippedRecordCount = 0;
        int copiedRecordCount = 0;
        while (iterator.hasNext())
        {

            Object object = iterator.next();

            if (++skippedRecordCount <= startIndex)
            {
                continue;
            }

            croppedList.add(object);

            if ((numberOfItems != 0) && (++copiedRecordCount >= numberOfItems))
            {
                break;
            }
        }

        return croppedList;

    }

    /**
     * create an iterator on a given object (Collection, Enumeration, array, single Object) and crop the resulting list
     * according to the startIndex and numberOfItems parameters.
     * @param iterableObject Collection, Enumeration or array to crop
     * @param startIndex int starting index
     * @param numberOfItems int number of items to keep in the list
     * @return List with values taken from the given object, cropped according the startIndex and numberOfItems
     * parameters
     */
    public static List<Object> getListFromObject(Object iterableObject, int startIndex, int numberOfItems)
    {
        if (iterableObject instanceof List)
        {
            // easier, use sublist
            List<Object> list = ((List<Object>) iterableObject);

            // check for partial lists
            int lastRecordExclusive = numberOfItems <= 0 ? list.size() : startIndex + numberOfItems;
            if (lastRecordExclusive > list.size())
            {
                lastRecordExclusive = list.size();
            }

            if (startIndex < list.size())
            {
                return list.subList(startIndex, lastRecordExclusive);
            }
        }

        // use an iterator
        Iterator< ? > iterator = IteratorUtils.getIterator(iterableObject);
        return getSubList(iterator, startIndex, numberOfItems);
    }
}