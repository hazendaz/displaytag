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

import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

/**
 * Tests for DISPL-142: Export of nested tables does not work.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
class Displ142Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-142.jsp";
    }

    /**
     * Nested tables should be ignored.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {

        ParamEncoder encoder = new ParamEncoder("table");
        String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);

        WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        request.setParameter(mediaParameter, Integer.toString(MediaTypeEnum.CSV.getCode()));

        WebResponse response = this.runner.getResponse(request);

        Assertions.assertEquals("text/csv", response.getContentType(), "Expected a different content type.");

        // second column should be empty
        Assertions.assertEquals("ant,,camel\nant,,camel\n", response.getText(), "Wrong content.");

        // now export a nested table
        encoder = new ParamEncoder("nested");
        mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);
        request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        request.setParameter(mediaParameter, Integer.toString(MediaTypeEnum.CSV.getCode()));
        // this test needs the export filter
        request.setParameter(TableTagParameters.PARAMETER_EXPORTING, "1");
        response = this.runner.getResponse(request);

        Assertions.assertEquals("text/csv", response.getContentType(), "Expected a different content type.");

        // second column should be empty
        Assertions.assertEquals("bee\nbee\n", response.getText(), "Wrong content.");
    }

}
