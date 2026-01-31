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

import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Testcase for "excludedParams" table attribute.
 */
class ExcludedParamsStarTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "excludedparams-star.jsp";
    }

    /**
     * Checks generated pagination links.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {

        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        request.setParameter("foo", "foovalue");
        request.setParameter("bar", "barvalue");
        request.setParameter("zoo", "zoovalue");

        final WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug("RESPONSE: " + response.getText());
        }

        final WebLink[] links = response.getLinks();

        for (final WebLink link : links) {
            final String linkUrl = link.getURLString();
            Assertions.assertEquals(-1, linkUrl.indexOf("foo"), "Link contains the excluded parameter foo.");
            Assertions.assertEquals(-1, linkUrl.indexOf("bar"), "Link contains the excluded parameter bar.");
            Assertions.assertEquals(-1, linkUrl.indexOf("zoo"), "Link contains the excluded parameter zoo.");
        }

    }
}
