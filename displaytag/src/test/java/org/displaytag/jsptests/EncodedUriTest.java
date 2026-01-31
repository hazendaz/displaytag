/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.jsptests;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for encoded uri.
 */
class EncodedUriTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "requesturi.jsp";
    }

    /**
     * Test link generated using requestUri.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {

        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        request.setParameter("city", "MünchenXX");
        request.setHeaderField("Content-Type", "text/html; charset=UTF-8");

        // just check that everything is ok before reaching displaytag
        Assertions.assertEquals("MünchenXX", request.getParameter("city"));
        final WebResponse response = this.runner.getResponse(request);

        Assertions.assertEquals(StandardCharsets.UTF_8.name(), response.getCharacterSet());

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final WebTable[] tables = response.getTables();
        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");

        final WebLink[] links = response.getLinks();
        Assertions.assertEquals(4, links.length, "Wrong number of links in result.");

        final String expected = "M%C3%BCnchen";

        final String actual = StringUtils.substringBetween(links[0].getURLString(), "city=", "XX");

        Assertions.assertEquals(expected, actual);
    }
}
