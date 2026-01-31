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

import org.apache.commons.lang3.Strings;
import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for DISPL-105: https hrefs in Table get generated as http.
 */
class Displ105Test extends DisplaytagCase {

    /**
     * Generated link should be https.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        final String httpsUrl = Strings.CS.replace(this.getJspUrl("DISPL-105.jsp"), "http://", "https://");
        final WebRequest request = new GetMethodWebRequest(httpsUrl);

        final WebResponse response = this.runner.getResponse(request);

        final WebLink[] links = response.getLinks();
        Assertions.assertEquals(1, links.length, "Wrong number of generated links.");

        Assertions.assertTrue(links[0].getURLString().startsWith("https://"),
                "Generated link doesn't start with https: " + links[0].getURLString());

    }

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     */
    @Override
    public String getJspName() {
        // Not used
        return null;
    }

}
