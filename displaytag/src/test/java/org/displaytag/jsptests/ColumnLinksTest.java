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

import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for dynamic links.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ColumnLinksTest extends DisplaytagCase
{

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "columnlinks.jsp";
    }

    /**
     * Test link generated using column attributes.
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

        WebLink[] links = response.getLinks();
        Assert.assertEquals("Wrong number of links in result.", 6, links.length);

        Assert.assertEquals("Text in first link is wrong.", "/context/dynlink?param=ant", links[0].getURLString());
        Assert.assertEquals("Text in second link is wrong.", "/context/dynlink?param=ant", links[1].getURLString());
        Assert.assertEquals("Text in third link is wrong.", "dynlink?param=ant", links[2].getURLString());
        Assert.assertEquals(
            "Text in fourth link is wrong.",
            "http://something/dynlink?param=ant",
            links[3].getURLString());
        Assert.assertEquals("Text in fifth link is wrong.", "http://something/dynlink", links[4].getURLString());
        Assert.assertEquals("Text in sixth link is wrong.", "/context/dynlink", links[5].getURLString());
    }

}