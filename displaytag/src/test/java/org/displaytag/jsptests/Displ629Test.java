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

import java.io.IOException;
import java.net.MalformedURLException;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;


/**
 * Tests for DISPL-629 - display:column w/o property works correctly only on first page.
 * @author Fabrizio Giustina
 * @version $Revision: 1081 $ ($Author: fgiust $)
 */
public class Displ629Test extends DisplaytagCase
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
        return "DISPL-629.jsp";
    }

    @Override
    @Test
    public void doTest() throws Exception
    {
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));

        ParamEncoder encoder = new ParamEncoder("table");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "5");
        request.setParameter("pagesize", "1");

        checkLastColumn(request);

        request = new GetMethodWebRequest(getJspUrl(getJspName()));

        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "10");
        request.setParameter("pagesize", "2");

        checkLastColumn(request);

    }

    /**
     * Check last column.
     *
     * @param request the request
     * @throws MalformedURLException the malformed URL exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SAXException the SAX exception
     */
    private void checkLastColumn(WebRequest request) throws MalformedURLException, IOException, SAXException
    {
        WebResponse response = this.runner.getResponse(request);

        WebTable[] tables = response.getTables();
        Assert.assertEquals(1, tables.length);

        Assert.assertEquals("Wrong column content", "D", tables[0].getCellAsText(tables[0].getRowCount() - 1, 0));
        Assert.assertEquals("Wrong column content", "foo", tables[0].getCellAsText(tables[0].getRowCount() - 1, 1));
    }

}