/*
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
package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

/**
 * Simple nested tables.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
class NestedTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "nested.jsp";
    }

    /**
     * Test for content disposition and filename.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));

        final WebResponse response = this.runner.getResponse(request);

        final WebTable[] tables = response.getTables();

        // nested tables don't show up in the count
        Assertions.assertEquals(1, tables.length, "Wrong number of first-level tables");
        Assertions.assertEquals(4, tables[0].getColumnCount(), "Wrong number of columns in main table");
        Assertions.assertEquals(4, tables[0].getRowCount(), "Wrong number of rows in main table");

        for (int j = 1; j < tables[0].getRowCount(); j++) {
            Assertions.assertEquals(Integer.toString(j),
                    tables[0].getCellAsText(j, 0), "Content in cell [" + j + ",0] in main table is wrong");
            Assertions.assertEquals(KnownValue.ANT,
                    tables[0].getCellAsText(j, 1), "Content in cell [" + j + ",1] in main table is wrong");
            Assertions.assertEquals(KnownValue.BEE,
                    tables[0].getCellAsText(j, 2), "Content in cell [" + j + ",2] in main table is wrong");

            final WebTable nested = tables[0].getTableCell(j, 3).getFirstMatchingTable((htmlElement, criteria) -> true, null);

            Assertions.assertNotNull(nested, "Nested table not found in cell [" + j + ",3]");
            Assertions.assertEquals(3, nested.getColumnCount(), "Wrong number of columns in nested table");
            Assertions.assertEquals(4, nested.getRowCount(), "Wrong number of rows in nested table");

            for (int x = 1; x < nested.getRowCount(); x++) {
                Assertions.assertEquals(Integer.toString(x),
                        nested.getCellAsText(x, 0), "Content in cell [" + x + ",0] in nested table is wrong");
                Assertions.assertEquals(KnownValue.ANT,
                        nested.getCellAsText(x, 1), "Content in cell [" + x + ",1] in nested table is wrong");
                Assertions.assertEquals(KnownValue.CAMEL,
                        nested.getCellAsText(x, 2), "Content in cell [" + x + ",2] in nested table is wrong");
            }

        }

    }

}