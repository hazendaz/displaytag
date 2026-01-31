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

import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for DISPL-26 - More params for paging.banner.*_items_found.
 */
class Displ026Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-026.jsp";
    }

    /**
     * Check addictional parameters in paging.banner.*.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        WebResponse response;

        response = this.runner.getResponse(request);
        Assertions.assertEquals("1|3", response.getElementWithID("numbers").getText(),
                "Parameters {5} and {6} are not correctly evaluated in paging.banner.first.");
        Assertions.assertEquals("1|3", response.getElementWithID("label").getText(),
                "Parameters {4} and {5} are not correctly evaluated in paging.banner.some_items_found.");
    }

}
