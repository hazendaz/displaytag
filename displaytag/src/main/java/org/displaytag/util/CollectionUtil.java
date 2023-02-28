/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
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
import org.displaytag.model.Row;

/**
 * Utility methods for collection handling.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public final class CollectionUtil {

    /**
     * Don't instantiate a CollectionUtil.
     */
    private CollectionUtil() {
        // unused
    }

    /**
     * Create a list of objects taken from the given iterator and crop the resulting list according to the startIndex
     * and numberOfItems parameters.
     *
     * @param iterator
     *            Iterator
     * @param startIndex
     *            int starting index
     * @param numberOfItems
     *            int number of items to keep in the list
     *
     * @return List with values taken from the given object, cropped according to startIndex and numberOfItems
     *         parameters
     */
    private static List<Row> getSubList(final Iterator<Row> iterator, final int startIndex, final int numberOfItems) {

        final List<Row> croppedList = new ArrayList<>(numberOfItems);

        int skippedRecordCount = 0;
        int copiedRecordCount = 0;
        while (iterator.hasNext()) {

            final Row object = iterator.next();

            skippedRecordCount++;
            if (skippedRecordCount <= startIndex) {
                continue;
            }

            croppedList.add(object);

            if (numberOfItems != 0 && ++copiedRecordCount >= numberOfItems) {
                break;
            }
        }

        return croppedList;

    }

    /**
     * create an iterator on a given object (Collection, Enumeration, array, single Object) and crop the resulting list
     * according to the startIndex and numberOfItems parameters.
     *
     * @param iterableObject
     *            Collection, Enumeration or array to crop
     * @param startIndex
     *            int starting index
     * @param numberOfItems
     *            int number of items to keep in the list
     *
     * @return List with values taken from the given object, cropped according the startIndex and numberOfItems
     *         parameters
     */
    public static List<Row> getListFromObject(final Object iterableObject, final int startIndex,
            final int numberOfItems) {
        if (iterableObject instanceof List) {
            // easier, use sublist
            final List<Row> list = (List<Row>) iterableObject;

            // check for partial lists
            int lastRecordExclusive = numberOfItems <= 0 ? list.size() : startIndex + numberOfItems;
            if (lastRecordExclusive > list.size()) {
                lastRecordExclusive = list.size();
            }

            if (startIndex < list.size()) {
                return list.subList(startIndex, lastRecordExclusive);
            }
        }

        // use an iterator
        final Iterator<Row> iterator = IteratorUtils.getIterator(iterableObject);
        return CollectionUtil.getSubList(iterator, startIndex, numberOfItems);
    }
}
