/*
 * Copyright (C) 2002-2022 Fabrizio Giustina, the Displaytag team
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

import org.apache.commons.lang3.StringUtils;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

/**
 * Tests for different kind of "data sources".
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
class DataSourceMapTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "map.jsp";
    }

    /**
     * Test with a Map[].
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
            this.log.debug("RESPONSE: " + response.getText());
        }

        final WebTable[] tables = response.getTables();

        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");

        Assertions.assertEquals(3, tables[0].getColumnCount(), "Bad number of generated columns.");

        Assertions.assertEquals(
                StringUtils.capitalize(KnownValue.ANT), tables[0].getCellAsText(0, 0),
                "Bad value in column header.");
        Assertions.assertEquals(
                StringUtils.capitalize(KnownValue.BEE), tables[0].getCellAsText(0, 1),
                "Bad value in column header.");
        Assertions.assertEquals(
                StringUtils.capitalize(KnownValue.CAMEL), tables[0].getCellAsText(0, 2),
                "Bad value in column header.");

        Assertions.assertEquals(KnownValue.ANT, tables[0].getCellAsText(1, 0), "Bad value in column content.");
        Assertions.assertEquals(KnownValue.BEE, tables[0].getCellAsText(1, 1), "Bad value in column content.");
        Assertions.assertEquals(KnownValue.CAMEL, tables[0].getCellAsText(1, 2), "Bad value in column content.");
    }

}