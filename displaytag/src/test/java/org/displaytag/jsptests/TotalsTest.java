/**
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

import org.apache.commons.lang3.StringUtils;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownTypes;
import org.junit.Assert;
import org.junit.Test;


/**
 * The Class TotalsTest.
 *
 * @author rapruitt
 * @version $Revision$ ($Author$)
 */
public class TotalsTest extends DisplaytagCase
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
        return "totals.jsp";
    }

    /**
     * Check sorted columns.
     *
     * @throws Exception any axception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception
    {
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));
        WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled())
        {
            this.log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();

        Assert.assertEquals("Wrong number of tables.", 1, tables.length);

        Assert.assertEquals("Bad number of generated columns.", 3, tables[0].getColumnCount());
        // The footer will PRECEDE the body.
        Assert.assertTrue(
            "Totals should not be calculated / present if the column is not so marked.",
            StringUtils.isBlank(tables[0].getCellAsText(1, 0)));
        Assert.assertEquals(
            "Bad value in footer cell total.",
            "" + KnownTypes.LONG_VALUE.doubleValue() * 2,
            tables[0].getCellAsText(1, 1));
        Assert.assertEquals(
            "Bad value in footer cell total.",
            "" + KnownTypes.LONG_VALUE.doubleValue() * 2,
            tables[0].getCellAsText(1, 2));
    }
}
