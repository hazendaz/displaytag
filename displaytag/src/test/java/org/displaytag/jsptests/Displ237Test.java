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
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for DISPL-237 - Problems using sorting, defaultsort.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
class Displ237Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-237.jsp";
    }

    /**
     * Trying to reproduce an IndexOutOfBoundsException...
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));

        WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final WebTable[] tables = response.getTables();
        Assertions.assertEquals(1, tables.length, "Wrong number of tables");

        WebLink[] links = response.getLinks();
        Assertions.assertEquals(6, links.length, "Wrong number of links"); // sorting + paging

        response = links[3].click(); // sort again on default sorted column
        response = links[4].click(); // sort on column 2

        response.getTables();
        Assertions.assertEquals(1, tables.length, "Wrong number of tables");

        links = response.getLinks();
        Assertions.assertEquals(6, links.length, "Wrong number of links"); // sorting + paging

        response = links[0].click(); // go to page 2

        response.getTables();
        Assertions.assertEquals(1, tables.length, "Wrong number of tables");

        links = response.getLinks();
        Assertions.assertEquals(6, links.length, "Wrong number of links"); // sorting + paging

        response = links[3].click(); // sort again on default sorted column
        response = links[4].click(); // sort on column 2

    }

}
