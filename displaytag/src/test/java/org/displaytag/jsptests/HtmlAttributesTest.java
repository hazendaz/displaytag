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
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;


/**
 * Tests for standard html attributes of table and column tags.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class HtmlAttributesTest extends DisplaytagCase
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
        return "htmlattributes.jsp";
    }

    /**
     * Check content and ids in generated tables.
     *
     * @throws Exception any axception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception
    {
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));
        WebResponse response;

        response = this.runner.getResponse(request);

        WebTable[] tables = response.getTables();
        Assert.assertEquals("Wrong number of tables.", 1, tables.length);
        WebTable table = tables[0];

        Assert.assertEquals("invalid id", "idX", table.getID());

        Assert.assertEquals("invalid attribute value", "cellspacingX", table.getAttribute("cellspacing"));
        Assert.assertEquals("invalid attribute value", "cellpaddingX", table.getAttribute("cellpadding"));
        Assert.assertEquals("invalid attribute value", "frameX", table.getAttribute("frame"));
        Assert.assertEquals("invalid attribute value", "rulesX", table.getAttribute("rules"));
        Assert.assertEquals("invalid attribute value", "styleX", table.getAttribute("style"));
        Assert.assertEquals("invalid attribute value", "summaryX", table.getAttribute("summary"));
        Assert.assertEquals("invalid attribute value", "classX table", table.getAttribute("class"));

        TableCell header = table.getTableCell(0, 0);
        Assert.assertEquals("invalid attribute value", "classH", header.getAttribute("class"));
        Assert.assertEquals("invalid attribute value", "styleH", header.getAttribute("style"));

        TableCell cell = table.getTableCell(1, 0);
        Assert.assertEquals("invalid attribute value", "styleX", cell.getAttribute("style"));
        Assert.assertEquals("invalid attribute value", "classX", cell.getAttribute("class"));
    }
}