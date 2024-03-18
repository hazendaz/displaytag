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
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests for DISPL-1 - Autolink and maxlength problem.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
class Displ001Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-001.jsp";
    }

    /**
     * Check the content of the title attribute.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    // TODO JWL 2/26/2023 Disabled test as it fails with tomcat 8+
    @Override
    @Disabled
    @Test
    public void doTest() throws Exception {
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl("DISPL-001.jsp"));
        final WebResponse response = this.runner.getResponse(request);

        final WebTable[] tables = response.getTables();
        Assertions.assertEquals(1, tables.length, "Expected 1 table in result.");
        Assertions.assertEquals("averylongemail@mail.com", tables[0].getTableCell(1, 0).getTitle().trim(),
                "Wrong title in column");

        final WebLink[] links = tables[0].getTableCell(1, 0).getLinks();
        Assertions.assertEquals(1, links.length, "Expected link not found");
        Assertions.assertEquals("averylongemail@...", links[0].getText(), "Wrong text in link");
        Assertions.assertEquals("mailto:averylongemail@mail.com", links[0].getURLString(), "Wrong url in link");
    }

}
