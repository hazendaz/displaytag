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

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for DISPL-249 - Link generated for results navigation is breaking national character taken from FormBean.
 */
class Displ249Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-249.jsp";
    }

    /**
     * Link generated for results navigation is breaking national character taken from FormBean.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName())); // use post
        final String paramValue = "aàeèiìoòuù";

        request.setParameter("testparam", paramValue);

        request.setHeaderField("Content-Type", "text/html; charset=UTF-8");

        final WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final WebTable[] tables = response.getTables();
        Assertions.assertEquals(1, tables.length, "Wrong number of tables");

        final WebLink[] links = response.getLinks();
        Assertions.assertEquals(3, links.length, "Wrong number of links"); // sorting + paging

        final String url = URLDecoder.decode(links[0].getURLString(), StandardCharsets.UTF_8);

        String actual = StringUtils.substringAfter(url, "testparam=");
        if (Strings.CS.contains(actual, "&")) {
            actual = StringUtils.substringBefore(actual, "&");
        }

        Assertions.assertEquals(paramValue, actual);
    }

}
