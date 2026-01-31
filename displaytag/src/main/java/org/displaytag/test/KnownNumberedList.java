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
 * Test data provider. A list of 5 NumberedItems.
 */
public class KnownNumberedList {

    /**
     * Returns an iterator on a list made of 5 NumberedItems objects.
     *
     * @return iterator on a list made of 5 NumberedItems objects
     */
    public Iterator<NumberedItem> iterator() {
        final List<NumberedItem> list = new ArrayList<>();
        for (int j = 0; j < 5; j++) {
            list.add(new NumberedItem(j));
        }
        return list.iterator();
    }
}
