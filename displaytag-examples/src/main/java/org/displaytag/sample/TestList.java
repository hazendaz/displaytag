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
package org.displaytag.sample;

import java.util.ArrayList;
import java.util.Random;

/**
 * Just a utility class for testing out the table and column tags. When this class is created, it loads itself with a
 * number of ListObjects that are shown throughout the various example pages that exercise the table object. If created
 * via the default constructor, this loads itself with 60 ListObjects.
 */
public class TestList extends ArrayList<ListObject> {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Creats a TestList that is filled with 60 ListObjects suitable for testing.
     */
    public TestList() {
        super();

        for (int j = 0; j < 60; j++) {
            add(new ListObject());
        }
    }

    /**
     * Creates a TestList that is filled with [size] ListObjects suitable for testing.
     *
     * @param size
     *            int size of the list
     * @param duplicates
     *            boolean put duplicates in the list
     */
    public TestList(int size, boolean duplicates) {
        if (duplicates) {
            // generate a random number between 1 and 3 and duplicate that many number of times.
            for (int j = 0; j < size; j++) {

                ListObject object1 = new ListObject();
                ListObject object2 = new ListObject();
                ListObject object3 = new ListObject();

                int random = new Random().nextInt(3);
                for (int k = 0; k <= random; k++) {
                    add(object1);
                }

                object1.setId(object2.getId());

                random = new Random().nextInt(3);
                for (int k = 0; k <= random; k++) {
                    add(object1);
                    add(object2);
                }

                object1.setEmail(object3.getEmail());

                random = new java.util.Random().nextInt(3);
                for (int k = 0; k <= random; k++) {
                    add(object1);
                }
            }
        } else {
            for (int j = 0; j < size; j++) {
                add(new ListObject());
            }
        }
    }

    /**
     * Returns a ListObject using get(index) from the Array.
     *
     * @param index
     *            int index of the List object into the array
     *
     * @return ListObject
     */
    public ListObject getItem(int index) {
        return super.get(index);
    }

}
