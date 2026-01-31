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

import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for dynamic links.
 */
class ColumnLinksTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "columnlinks.jsp";
    }

    /**
     * Test link generated using column attributes.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));

        final WebResponse response = this.runner.getResponse(request);
        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final WebTable[] tables = response.getTables();
        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");

        final WebLink[] links = response.getLinks();
        Assertions.assertEquals(6, links.length, "Wrong number of links in result.");

        Assertions.assertEquals("/context/dynlink?param=ant", links[0].getURLString(), "Text in first link is wrong.");
        Assertions.assertEquals("/context/dynlink?param=ant", links[1].getURLString(), "Text in second link is wrong.");
        Assertions.assertEquals("dynlink?param=ant", links[2].getURLString(), "Text in third link is wrong.");
        Assertions.assertEquals("http://something/dynlink?param=ant", links[3].getURLString(),
                "Text in fourth link is wrong.");
        Assertions.assertEquals("http://something/dynlink", links[4].getURLString(), "Text in fifth link is wrong.");
        Assertions.assertEquals("/context/dynlink", links[5].getURLString(), "Text in sixth link is wrong.");
    }

}
