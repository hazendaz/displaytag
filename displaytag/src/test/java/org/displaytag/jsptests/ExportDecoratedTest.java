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
import org.displaytag.decorator.DateColumnDecorator;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownTypes;
import org.displaytag.util.ParamEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for column decorators.
 */
class ExportDecoratedTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "columndecorator.jsp";
    }

    /**
     * Export should not be decorated.
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
            this.log.debug(response.getText());
        }

        Assertions.assertEquals("text/xml", response.getContentType(), "Expected a different content type.");
        Assertions.assertFalse(
                Strings.CS.contains(response.getText(),
                        (String) new DateColumnDecorator().decorate(KnownTypes.TIME_VALUE, null, null)),
                "Export should not be decorated");
        Assertions.assertTrue(Strings.CS.contains(response.getText(), KnownTypes.TIME_VALUE.toString()),
                "Export should not be decorated");
    }
}
