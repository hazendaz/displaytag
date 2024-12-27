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

import java.io.IOException;
import java.net.MalformedURLException;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

/**
 * Tests for DISPL-629 - display:column w/o property works correctly only on first page.
 */
class Displ629Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-629.jsp";
    }

    /**
     * Do test.
     *
     * @throws Exception
     *             the exception
     */
    // TODO JWL 2/26/2023 Disabled test as it fails with tomcat 8+
    @Override
    @Disabled
    @Test
    public void doTest() throws Exception {
        WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));

        final ParamEncoder encoder = new ParamEncoder("table");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "5");
        request.setParameter("pagesize", "1");

        this.checkLastColumn(request);

        request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));

        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "10");
        request.setParameter("pagesize", "2");

        this.checkLastColumn(request);

    }

    /**
     * Check last column.
     *
     * @param request
     *            the request
     *
     * @throws MalformedURLException
     *             the malformed URL exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws SAXException
     *             the SAX exception
     */
    private void checkLastColumn(final WebRequest request) throws MalformedURLException, IOException, SAXException {
        final WebResponse response = this.runner.getResponse(request);

        final WebTable[] tables = response.getTables();
        Assertions.assertEquals(1, tables.length);

        Assertions.assertEquals("D", tables[0].getCellAsText(tables[0].getRowCount() - 1, 0), "Wrong column content");
        Assertions.assertEquals("foo", tables[0].getCellAsText(tables[0].getRowCount() - 1, 1), "Wrong column content");
    }

}
