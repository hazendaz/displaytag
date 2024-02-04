/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
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

import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The Class PaginationGroupingTest.
 *
 * @author rwest
 *
 * @version $Revision: 1159 $ ($Author: rwest $)
 */
class PaginationGroupingTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     */
    @Override
    public String getJspName() {
        return "pagination-grouping.jsp";
    }

    /**
     * Do test.
     *
     * @throws Exception
     *             the exception
     */
    @Override
    @Test
    public void doTest() throws Exception {
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        final WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final WebTable[] tables = response.getTables();

        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");
        Assertions.assertEquals(3, tables[0].getColumnCount(), "Bad number of generated columns.");
        Assertions.assertEquals("4.0", tables[0].getCellAsText(4, 1), "Bad sub-total for group 1");
        Assertions.assertEquals("6.0", tables[0].getCellAsText(9, 1), "Bad sub-total for group 2");
        Assertions.assertEquals("10.0", tables[0].getCellAsText(10, 1), "Bad grand total");
    }
}
