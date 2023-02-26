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

import java.nio.charset.StandardCharsets;

import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

/**
 * Test for #968559.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
class ExportUTF8FilterTest extends ExportUTF8Test {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "utf8.jsp";
    }

    /**
     * Test response encoding.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        // test keep
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));

        // test remove
        final ParamEncoder encoder = new ParamEncoder("table");
        final String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);
        request.setParameter(mediaParameter, Integer.toString(MediaTypeEnum.XML.getCode()));

        // this will enable the filter!
        request.setParameter(TableTagParameters.PARAMETER_EXPORTING, "1");

        final WebResponse response = this.runner.getResponse(request);

        final String encoding = response.getCharacterSet();

        Assertions.assertEquals(StandardCharsets.UTF_8.name(), encoding, "Encoding is not UTF-8 as expected.");

    }
}
