/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Test data provider. A list of 4 unordered NumberedItems.
 */
public class ShuffledNumberedList {

    /**
     * Returns an iterator on a list made of 4 unordered NumberedItems objects.
     *
     * @return iterator on a list made of 4 unordered NumberedItems objects
     */
    public Iterator<NumberedItem> iterator() {
        final List<NumberedItem> list = new ArrayList<>();
        list.add(new NumberedItem(1));
        list.add(new NumberedItem(4));
        list.add(new NumberedItem(2));
        list.add(new NumberedItem(3));
        return list.iterator();
    }
}
