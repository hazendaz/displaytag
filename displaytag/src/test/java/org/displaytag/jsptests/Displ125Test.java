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

import java.io.IOException;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

/**
 * Test for DISPL-125 - Preserve The Current Page And Sort Across Session.
 *
 * @author Fabrizio Giustina
 *
 * @version $Id: $
 */
public class Displ125Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-125.jsp";
    }

    /**
     * Preserve The Current Page And Sort Across Session.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        final ParamEncoder encoder = new ParamEncoder("table");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "3");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT), "0");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER), "1");

        WebResponse response = this.runner.getResponse(request);

        this.checkResponse(response);

        // repeating the same request without parameters must return the same result (using session)
        request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        response = this.runner.getResponse(request);
        this.checkResponse(response);
    }

    /**
     * Check response.
     *
     * @param response
     *            the response
     *
     * @throws SAXException
     *             the SAX exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void checkResponse(final WebResponse response) throws SAXException, IOException {

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final WebTable[] tables = response.getTables();
        Assert.assertEquals("Wrong number of tables.", 1, tables.length);
        Assert.assertEquals("Wrong number of rows.", 2, tables[0].getRowCount());
        Assert.assertEquals("Column content missing?", "ant", tables[0].getCellAsText(1, 0));

        final HTMLElement pagination = response.getElementWithID("pagination");
        Assert.assertNotNull("Paging banner not found.", pagination);
        Assert.assertEquals("Pagination links are not as expected.", "1, 2, [3]", pagination.getText());

        Assert.assertEquals("Column 1 should be marked as sorted.", "sortable sorted order2",
                tables[0].getTableCell(0, 0).getClassName());
    }

}