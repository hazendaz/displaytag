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

import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for optimized iterations (don't evaluate unneeded body of columns).
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class OptimizedIteration2Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "optimizediteration2.jsp";
    }

    /**
     * Verifies that the generated page contains the pagination links with the inupt parameter. Tests #917200 ("{}" in
     * parameters).
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception
    {

        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));
        ParamEncoder encoder = new ParamEncoder("table");

        // page 1, not sorted
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "1");
        checkNumberOfIterations(this.runner.getResponse(request), 1);

        // page 2, not sorted
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "2");
        checkNumberOfIterations(this.runner.getResponse(request), 1);

        // page 1, sorted (only current page)
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT), "1");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "1");
        checkNumberOfIterations(this.runner.getResponse(request), 1);

        // page 2, sorted (only current page)
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT), "1");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "2");
        checkNumberOfIterations(this.runner.getResponse(request), 1);

        // page 1, export single page
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "2");
        request.setParameter(
            encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE),
            Integer.toString(MediaTypeEnum.CSV.getCode()));
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "1");

        WebResponse response = this.runner.getResponse(request);
        String csvExport = response.getText();
        if (this.log.isDebugEnabled())
        {
            this.log.debug(response.getText());
        }

        Assert.assertEquals("Wrong csv export", "ant,1\n", csvExport);

    }

    /**
     * @param response WebResponse
     * @param iterations expected number of iterations
     * @throws Exception any axception thrown during test.
     */
    private void checkNumberOfIterations(WebResponse response, int iterations) throws Exception
    {
        if (this.log.isDebugEnabled())
        {
            this.log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        Assert.assertEquals("Expected 1 table in result.", 1, tables.length);
        Assert.assertEquals("Expected 2 rows in table.", 2, tables[0].getRowCount());

        Assert.assertEquals(
            "Wrong number of iterations. Evaluated column bodies number is different from expected",
            Integer.toString(iterations),
            response.getElementWithID("iterations").getText());
    }
}