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
package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownValue;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HTMLElementPredicate;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Simple nested tables.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class NestedTest extends DisplaytagCase
{

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "nested.jsp";
    }

    /**
     * Test for content disposition and filename.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception
    {
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));

        WebResponse response = this.runner.getResponse(request);

        WebTable[] tables = response.getTables();

        // nested tables don't show up in the count
        Assert.assertEquals("Wrong number of first-level tables", 1, tables.length);
        Assert.assertEquals("Wrong number of columns in main table", 4, tables[0].getColumnCount());
        Assert.assertEquals("Wrong number of rows in main table", 4, tables[0].getRowCount());

        for (int j = 1; j < tables[0].getRowCount(); j++)
        {
            Assert.assertEquals(
                "Content in cell [" + j + ",0] in main table is wrong",
                Integer.toString(j),
                tables[0].getCellAsText(j, 0));
            Assert.assertEquals(
                "Content in cell [" + j + ",1] in main table is wrong",
                KnownValue.ANT,
                tables[0].getCellAsText(j, 1));
            Assert.assertEquals(
                "Content in cell [" + j + ",2] in main table is wrong",
                KnownValue.BEE,
                tables[0].getCellAsText(j, 2));

            WebTable nested = tables[0].getTableCell(j, 3).getFirstMatchingTable(new HTMLElementPredicate()
            {

                @Override
                public boolean matchesCriteria(Object htmlElement, Object criteria)
                {
                    return true;
                }
            }, null);

            Assert.assertNotNull("Nested table not found in cell [" + j + ",3]", nested);
            Assert.assertEquals("Wrong number of columns in nested table", 3, nested.getColumnCount());
            Assert.assertEquals("Wrong number of rows in nested table", 4, nested.getRowCount());

            for (int x = 1; x < nested.getRowCount(); x++)
            {
                Assert.assertEquals(
                    "Content in cell [" + x + ",0] in nested table is wrong",
                    Integer.toString(x),
                    nested.getCellAsText(x, 0));
                Assert.assertEquals(
                    "Content in cell [" + x + ",1] in nested table is wrong",
                    KnownValue.ANT,
                    nested.getCellAsText(x, 1));
                Assert.assertEquals(
                    "Content in cell [" + x + ",2] in nested table is wrong",
                    KnownValue.CAMEL,
                    nested.getCellAsText(x, 2));
            }

        }

    }

}