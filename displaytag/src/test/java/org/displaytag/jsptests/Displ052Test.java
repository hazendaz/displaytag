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
import org.junit.jupiter.api.Test;

/**
 * Test for DISPL-052 - Support for checkboxes.
 *
 * @author Fabrizio Giustina
 *
 * @version $Id$
 */
class Displ052Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-052.jsp";
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
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));

        final WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final WebTable[] tables = response.getTables();
        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");
        Assertions.assertEquals(3, tables[0].getRowCount(), "Wrong number of rows.");
        Assertions.assertEquals("ant", tables[0].getCellAsText(1, 2), "Column content missing?");
        Assertions.assertEquals("INPUT", tables[0].getTableCell(1, 0).getElementsWithName("_chk")[0].getTagName(),
                "Checkbox missing?");
        Assertions.assertEquals("10", tables[0].getTableCell(1, 0).getElementsWithName("_chk")[0].getAttribute("value"),
                "Checkbox value missing?");

        final WebLink[] links = response.getLinks();
        Assertions.assertEquals("javascript:displaytagform(\'displ\',[{f:\'d-148916-p\',v:\'2\'}])",
                links[0].getAttribute("href"), "Wrong link generated");

    }

}
