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
package org.displaytag.jsptests;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for DISPL-224 - Adding the "scope" attribute to table header cells for web accessibility.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
class Displ224Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-224.jsp";
    }

    /**
     * Check the content of the title attribute.
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
        Assertions.assertEquals(1, tables.length, "Wrong number of tables in result.");

        Assertions.assertEquals(2, tables[0].getRowCount(), "Wrong number of rows in result.");
        Assertions.assertEquals("col", tables[0].getTableCell(0, 0).getAttribute("scope"));
        Assertions.assertEquals(StringUtils.EMPTY, tables[0].getTableCell(0, 1).getAttribute("scope"));
        Assertions.assertEquals(StringUtils.EMPTY, tables[0].getTableCell(1, 0).getAttribute("scope"));
        Assertions.assertEquals("row", tables[0].getTableCell(1, 1).getAttribute("scope"));

    }

}
