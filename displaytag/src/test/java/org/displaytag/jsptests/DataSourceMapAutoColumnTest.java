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

import org.apache.commons.lang3.StringUtils;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownValue;
import org.junit.Assert;
import org.junit.Test;

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
public class DataSourceMapAutoColumnTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "map_autocolumn.jsp";
    }

    /**
     * Test with a Map[] with automatically discoverd column.
     *
     * @throws Exception
     *             any axception thrown during test.
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

        Assert.assertEquals("Wrong number of tables.", 1, tables.length);

        Assert.assertEquals("Bad number of generated columns.", 3, tables[0].getColumnCount());

        Assert.assertEquals("Bad value in column header.", //
                StringUtils.capitalize(KnownValue.ANT), tables[0].getCellAsText(0, 0));
        Assert.assertEquals("Bad value in column header.", //
                StringUtils.capitalize(KnownValue.BEE), tables[0].getCellAsText(0, 1));
        Assert.assertEquals("Bad value in column header.", //
                "camel title", tables[0].getCellAsText(0, 2)); // localized text

        Assert.assertEquals("Bad value in column content.", KnownValue.ANT, tables[0].getCellAsText(1, 0));
        Assert.assertEquals("Bad value in column content.", KnownValue.BEE, tables[0].getCellAsText(1, 1));
        Assert.assertEquals("Bad value in column content.", KnownValue.CAMEL, tables[0].getCellAsText(1, 2));
    }
}