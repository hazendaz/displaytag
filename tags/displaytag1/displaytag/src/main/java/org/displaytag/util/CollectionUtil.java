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
    private static List getSubList(Iterator iterator, int startIndex, int numberOfItems)
    {

        List croppedList = new ArrayList(numberOfItems);

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
    public static List getListFromObject(Object iterableObject, int startIndex, int numberOfItems)
    {
        if (iterableObject instanceof List)
        {
            // easier, use sublist
            List list = ((List) iterableObject);

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
        Iterator iterator = IteratorUtils.getIterator(iterableObject);
        return getSubList(iterator, startIndex, numberOfItems);
    }
}