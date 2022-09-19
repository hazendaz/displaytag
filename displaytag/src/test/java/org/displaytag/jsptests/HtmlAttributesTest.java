/*
 * Copyright (C) 2002-2022 Fabrizio Giustina, the Displaytag team
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

/**
 * Tests for standard html attributes of table and column tags.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
class HtmlAttributesTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "htmlattributes.jsp";
    }

    /**
     * Check content and ids in generated tables.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        WebResponse response;

        response = this.runner.getResponse(request);

        final WebTable[] tables = response.getTables();
        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");
        final WebTable table = tables[0];

        Assertions.assertEquals("idX", table.getID(), "invalid id");

        Assertions.assertEquals("cellspacingX", table.getAttribute("cellspacing"), "invalid attribute value");
        Assertions.assertEquals("cellpaddingX", table.getAttribute("cellpadding"), "invalid attribute value");
        Assertions.assertEquals("frameX", table.getAttribute("frame"), "invalid attribute value");
        Assertions.assertEquals("rulesX", table.getAttribute("rules"), "invalid attribute value");
        Assertions.assertEquals("styleX", table.getAttribute("style"), "invalid attribute value");
        Assertions.assertEquals("summaryX", table.getAttribute("summary"), "invalid attribute value");
        Assertions.assertEquals("classX table", table.getAttribute("class"), "invalid attribute value");

        final TableCell header = table.getTableCell(0, 0);
        Assertions.assertEquals("classH", header.getAttribute("class"), "invalid attribute value");
        Assertions.assertEquals("styleH", header.getAttribute("style"), "invalid attribute value");

        final TableCell cell = table.getTableCell(1, 0);
        Assertions.assertEquals("styleX", cell.getAttribute("style"), "invalid attribute value");
        Assertions.assertEquals("classX", cell.getAttribute("class"), "invalid attribute value");
    }
}