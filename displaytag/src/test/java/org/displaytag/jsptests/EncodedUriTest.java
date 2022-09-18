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

import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

/**
 * Tests for encoded uri.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
class EncodedUriTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "requesturi.jsp";
    }

    /**
     * Test link generated using requestUri.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {

        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        request.setParameter("city", "MünchenXX");
        request.setHeaderField("Content-Type", "text/html; charset=UTF-8");

        // just check that everything is ok before reaching displaytag
        Assertions.assertEquals("MünchenXX", request.getParameter("city"));
        final WebResponse response = this.runner.getResponse(request);

        Assertions.assertEquals(StandardCharsets.UTF_8.name(), response.getCharacterSet());

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final WebTable[] tables = response.getTables();
        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");

        final WebLink[] links = response.getLinks();
        Assertions.assertEquals(4, links.length, "Wrong number of links in result.");

        final String expected = "M%C3%BCnchen";

        final String actual = StringUtils.substringBetween(links[0].getURLString(), "city=", "XX");

        Assertions.assertEquals(expected, actual);
    }
}