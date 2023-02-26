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

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
 * Tests for DISPL-107: Excel and Text exports use Windows Latin-1 encoding.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
class Displ107Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-107.jsp";
    }

    /**
     * Encoding should be utf8.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));

        WebResponse response = this.runner.getResponse(request);
        Assertions.assertEquals("UTF8", response.getCharacterSet(), "Wrong encoding");

        final ParamEncoder encoder = new ParamEncoder("table");
        final String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);
        request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        request.setParameter(mediaParameter, Integer.toString(MediaTypeEnum.CSV.getCode()));

        response = this.runner.getResponse(request);
        this.checkContent(response);

        // enabled filter
        request.setParameter(TableTagParameters.PARAMETER_EXPORTING, "1");
        response = this.runner.getResponse(request);
        this.checkContent(response);

    }

    /**
     * Actually check exported bytes.
     *
     * @param response
     *            WebResponse
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    private void checkContent(final WebResponse response) throws Exception {
        // we are really testing an xml output?
        Assertions.assertEquals("text/csv", response.getContentType(), "Expected a different content type.");
        Assertions.assertEquals("UTF8", response.getCharacterSet(), "Wrong encoding");

        final InputStream stream = response.getInputStream();
        final byte[] result = new byte[11];
        stream.read(result);

        final byte[] expected = "ant,àèì\n".getBytes(StandardCharsets.UTF_8);
        if (this.log.isDebugEnabled()) {
            this.log.debug("expected: [" + new String(expected, StandardCharsets.UTF_8) + "]");
            this.log.debug("result:   [" + new String(result, StandardCharsets.UTF_8) + "]");
        }
        Assertions.assertEquals(expected.length, result.length, "Wrong length");

        for (int j = 0; j < result.length; j++) {
            Assertions.assertEquals(expected[j], result[j],
                "Wrong byte at position " + j + ", output=" + new String(result, StandardCharsets.UTF_8));

        }
    }

}
