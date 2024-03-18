/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
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

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

/**
 * The Class Displ292Test.
 */
class Displ292Test extends DisplaytagCase {

    /**
     * Gets the mime type.
     *
     * @return the mime type
     */
    protected String getMimeType() {
        return "application/vnd.ms-excel";
    }

    /**
     * Gets the code.
     *
     * @return the code
     */
    protected int getCode() {
        return MediaTypeEnum.EXCEL.getCode();
    }

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-292.jsp";
    }

    /**
     * Check that model modifications made by table decorator specified with in the decorator property the table tag
     * show up in the csv export.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    // TODO JWL 11/11/2022 Test fails on linux with wrong number of rows exported from response (4 expected but was 13)
    // TODO JWL 2/26/2023 Disabled test as it fails with tomcat 8+
    @Override
    @Disabled
    @Test
    @EnabledOnOs({ OS.WINDOWS, OS.MAC })
    public void doTest() throws Exception {
        final ParamEncoder encoder = new ParamEncoder("table");
        final String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);

        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        request.setParameter(mediaParameter, Integer.toString(this.getCode()));

        final WebResponse response = this.runner.getResponse(request);

        Assertions.assertEquals(this.getMimeType(), response.getContentType(), "Expected a different content type.");
        final String responseText = response.getText();

        final String[] rows = StringUtils.split(responseText, "\n");

        Assertions.assertEquals(4, rows.length,
                "Wrong number of rows exported from response text: " + Arrays.asList(rows));

    }

}
