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

import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.URLAssert;
import org.displaytag.util.ParamEncoder;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Testcase for #944056.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class PaginationLinksTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "pagination-links.jsp";
    }

    /**
     * Checks generated pagination links.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception
    {

        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName())
            + "?initiator=AVINASH&wfid=&approvedTDate=&initiatedFDate=&status=default"
            + "&initiatedTDate=04/28/2004&approvedFDate=&method=search&approver=");

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug("RESPONSE: " + response.getText());
        }

        WebLink[] links = response.getLinks();

        Assert.assertEquals("Wrong number of pagination links", 36, links.length);

        String lastLink = links[links.length - 1].getURLString();

        // remove prefix
        lastLink = lastLink.substring(lastLink.indexOf(getJspName()), lastLink.length());

        String encodedParam = new ParamEncoder("table2").encodeParameterName(TableTagParameters.PARAMETER_PAGE);

        String expected = "pagination-links.jsp?initiator=AVINASH&wfid=&approvedTDate=&initiatedFDate=&status=default"
            + "&initiatedTDate=04%2F28%2F2004&approvedFDate=&method=search&approver=&"
            + encodedParam
            + "=12";

        URLAssert.assertEquals(expected, lastLink);

    }
}