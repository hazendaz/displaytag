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
import java.util.Collections;

/**
 * Just a utility class for testing out the table and column tags. This List fills itself with objects and sorts them as
 * though it where pulling data from a report. This list is used to show the various report oriented examples (such as
 * grouping, callbacks, and data exports).
 */
public class ReportList extends ArrayList<ReportableListObject> {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Creats a TestList that is filled with 20 ReportableListObject suitable for testing.
     */
    public ReportList() {
        super();

        for (int j = 0; j < 20; j++) {
            add(new ReportableListObject());
        }

        Collections.sort(this);
    }

    /**
     * Creates a TestList that is filled with [size] ReportableListObject suitable for testing.
     *
     * @param size
     *            int
     */
    public ReportList(int size) {
        super();

        for (int j = 0; j < size; j++) {
            add(new ReportableListObject());
        }

        Collections.sort(this);
    }
}
