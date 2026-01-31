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
import com.meterware.httpunit.WebTable;

import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for DISPL-085 - Dynamic Column Creation.
 */
class Displ085Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-085.jsp";
    }

    /**
     * A simple way for creating columns on the fly using jstl.
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

        Assertions.assertEquals(2, tables[0].getColumnCount(), "Wrong number of columns.");

        Assertions.assertEquals("ant title", tables[0].getCellAsText(0, 0), "Wrong title.");
        Assertions.assertEquals("bee title", tables[0].getCellAsText(0, 1), "Wrong title.");

        Assertions.assertEquals("ant", tables[0].getCellAsText(1, 0), "Wrong content.");
        Assertions.assertEquals("bee", tables[0].getCellAsText(1, 1), "Wrong content.");

        // only one sortable column
        Assertions.assertEquals(1, response.getLinks().length, "Wrong number of links.");

    }

}
