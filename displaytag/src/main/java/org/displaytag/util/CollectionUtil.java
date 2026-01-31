/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.displaytag.model.Row;

/**
 * Utility methods for collection handling.
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
        final Iterator<Row> iterator = (Iterator<Row>) IteratorUtils.getIterator(iterableObject);
        return CollectionUtil.getSubList(iterator, startIndex, numberOfItems);
    }
}
