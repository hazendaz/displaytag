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

import static org.junit.Assert.assertEquals;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import org.displaytag.test.DisplaytagCase;
import org.junit.Test;


/**
 * The Class PaginationGroupingPageTwoTest.
 *
 * @author rwest
 * @version $Revision: 1159 $ ($Author: rwest $)
 */
public class PaginationGroupingPageTwoTest extends DisplaytagCase
{

    @Override
    public void doTest() throws Exception
    {
        // Not used
    }

    @Override
    public String getJspName() {
        // Not used
        return null;
    }

    /**
     * Use offset to get page two.
     *
     * @throws Exception the exception
     */
    @Test
    public void useOffsetToGetPageTwo() throws Exception
    {
        WebRequest request = new GetMethodWebRequest(getJspUrl("pagination-grouping-page2.jsp"));
        WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled())
        {
            this.log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();

        assertEquals("Wrong number of tables.", 1, tables.length);
        assertEquals("Bad number of generated columns.", 3, tables[0].getColumnCount());
        assertEquals("Bad sub-total for group 1", "8.0", tables[0].getCellAsText(6, 1));
        assertEquals("Bad grand total", "10.0", tables[0].getCellAsText(9, 1));
    }

    /**
     * Navigate to page two.
     *
     * @throws Exception the exception
     */
    @Test
    public void navigateToPageTwo() throws Exception
    {
        WebRequest request = new GetMethodWebRequest(getJspUrl("pagination-grouping.jsp"));
        request.setParameter("d-148916-p", "2");
        WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled())
        {
            this.log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();

        assertEquals("Wrong number of tables.", 1, tables.length);
        assertEquals("Bad number of generated columns.", 3, tables[0].getColumnCount());
        assertEquals("Bad sub-total for group 1", "8.0", tables[0].getCellAsText(6, 1));
        assertEquals("Bad grand total", "10.0", tables[0].getCellAsText(9, 1));
    }
}
