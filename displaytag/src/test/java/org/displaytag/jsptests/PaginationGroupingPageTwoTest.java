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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

/**
 * The Class PaginationGroupingPageTwoTest.
 *
 * @author rwest
 *
 * @version $Revision: 1159 $ ($Author: rwest $)
 */
class PaginationGroupingPageTwoTest extends DisplaytagCase {

    /**
     * Do test.
     *
     * @throws Exception
     *             the exception
     */
    @Override
    public void doTest() throws Exception {
        // Not used
    }

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     */
    @Override
    public String getJspName() {
        // Not used
        return null;
    }

    /**
     * Use offset to get page two.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void useOffsetToGetPageTwo() throws Exception {
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl("pagination-grouping-page2.jsp"));
        final WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final WebTable[] tables = response.getTables();

        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");
        Assertions.assertEquals(3, tables[0].getColumnCount(), "Bad number of generated columns.");
        Assertions.assertEquals("8.0", tables[0].getCellAsText(6, 1), "Bad sub-total for group 1");
        Assertions.assertEquals("10.0", tables[0].getCellAsText(9, 1), "Bad grand total");
    }

    /**
     * Navigate to page two.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void navigateToPageTwo() throws Exception {
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl("pagination-grouping.jsp"));
        request.setParameter("d-148916-p", "2");
        final WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final WebTable[] tables = response.getTables();

        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");
        Assertions.assertEquals(3, tables[0].getColumnCount(), "Bad number of generated columns.");
        Assertions.assertEquals("18.0", tables[0].getCellAsText(6, 1), "Bad sub-total for group 1");
        Assertions.assertEquals("10.0", tables[0].getCellAsText(9, 1), "Bad grand total");
    }
}
