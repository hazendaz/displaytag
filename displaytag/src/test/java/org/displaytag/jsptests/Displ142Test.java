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

import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for DISPL-142: Export of nested tables does not work.
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
