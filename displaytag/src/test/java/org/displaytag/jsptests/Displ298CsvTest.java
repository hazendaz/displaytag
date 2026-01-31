/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.jsptests;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

import org.displaytag.decorator.ModelDecorator;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The Class Displ298CsvTest.
 */
class Displ298CsvTest extends DisplaytagCase {

    /**
     * Gets the mime type.
     *
     * @return the mime type
     */
    protected String getMimeType() {
        return "text/csv";
    }

    /**
     * Gets the code.
     *
     * @return the code
     */
    protected int getCode() {
        return MediaTypeEnum.CSV.getCode();
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
        return "DISPL-298.jsp";
    }

    /**
     * Check that model modifications made by table decorator specified with in the decorator property the table tag
     * show up in the csv export.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        final ParamEncoder encoder = new ParamEncoder("table");
        final String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);

        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        request.setParameter(mediaParameter, Integer.toString(this.getCode()));

        final WebResponse response = this.runner.getResponse(request);

        Assertions.assertEquals(this.getMimeType(), response.getContentType(), "Expected a different content type.");
        final String responseText = response.getText();
        final boolean expectedTextPresent = responseText != null
                && responseText.indexOf(ModelDecorator.DECORATED_VALUE) > -1;
        Assertions.assertTrue(expectedTextPresent, "Missing content.");
    }

}
