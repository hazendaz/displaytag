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

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;


/**
 * Tests for DISPL-526 - cell value that is the same as the one vertically above is omitted.
 * @author Fabrizio Giustina
 * @version $Revision: 1081 $ ($Author: fgiust $)
 */
public class Displ526Test extends DisplaytagCase
{

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName()
    {
        return "DISPL-526.jsp";
    }

    /**
     * Check additional parameters in paging.banner.*.
     *
     * @throws Exception any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception
    {
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));

        WebResponse response;

        response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled())
        {
            this.log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        Assert.assertEquals(1, tables.length);

        // TODO 12/12/2016 JWL Review why we are having JVM inconsistencies.
        if (System.getProperty("java.version").startsWith("1.8.")) {
            Assert.assertEquals("Wrong column content", "1", tables[0].getCellAsText(2, 1));
        } else {
            Assert.assertEquals("Wrong column content", "5", tables[0].getCellAsText(2, 1));
        }

    }

}