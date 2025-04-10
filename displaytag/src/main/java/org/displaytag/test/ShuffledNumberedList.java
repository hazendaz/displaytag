/*
 * Copyright (C) 2002-2024 Fabrizio Giustina, the Displaytag team
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
