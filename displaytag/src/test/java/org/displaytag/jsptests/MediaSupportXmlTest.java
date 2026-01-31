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

import org.apache.commons.lang3.Strings;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownValue;
import org.displaytag.util.ParamEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for "media" attribute support.
 */
class MediaSupportXmlTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "media.jsp";
    }

    /**
     * Test as Xml.
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
        request.setParameter(mediaParameter, "" + MediaTypeEnum.XML.getCode());

        final WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug("RESPONSE: " + response.getText());
        }

        // we are really testing an xml output?
        Assertions.assertEquals("text/xml", response.getContentType(), "Expected a different content type.");

        final String output = response.getText();

        Assertions.assertTrue(Strings.CS.contains(output, KnownValue.BEE),
                "Expected value [" + KnownValue.BEE + "] missing");
        Assertions.assertTrue(Strings.CS.contains(output, KnownValue.CAMEL),
                "Expected value [" + KnownValue.CAMEL + "] missing");
        Assertions.assertTrue(!Strings.CS.contains(output, KnownValue.ANT),
                "Unexpected value [" + KnownValue.ANT + "] found");

    }

}
