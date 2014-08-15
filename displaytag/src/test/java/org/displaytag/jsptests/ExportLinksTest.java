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

import java.util.HashSet;
import java.util.Set;

import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests for "media" attribute support.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ExportLinksTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "media.jsp";
    }

    /**
     * Test that headers are correctly removed.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception
    {
        // test keep
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));
        WebResponse response = runner.getResponse(request);

        WebLink[] links = response.getLinks();

        Assert.assertEquals("Wrong number of export links. ", 4, links.length);

        Set<String> linkTexts = new HashSet<String>();
        for (int j = 0; j < links.length; j++)
        {
            String url = links[j].getURLString();
            log.debug(url);
            if (linkTexts.contains(url))
            {
                Assert.fail("Found duplicated link in export banner: " + url);
            }
            linkTexts.add(url);
        }

    }

}