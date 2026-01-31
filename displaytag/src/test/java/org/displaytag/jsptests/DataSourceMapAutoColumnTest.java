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

import org.apache.commons.lang3.StringUtils;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for different kind of "data sources".
 */
class DataSourceMapAutoColumnTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "map_autocolumn.jsp";
    }

    /**
     * Test with a Map[] with automatically discoverd column.
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
            this.log.debug("RESPONSE: " + response.getText());
        }

        final WebTable[] tables = response.getTables();

        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");

        Assertions.assertEquals(3, tables[0].getColumnCount(), "Bad number of generated columns.");

        Assertions.assertEquals(StringUtils.capitalize(KnownValue.ANT), tables[0].getCellAsText(0, 0),
                "Bad value in column header.");
        Assertions.assertEquals(StringUtils.capitalize(KnownValue.BEE), tables[0].getCellAsText(0, 1),
                "Bad value in column header.");
        Assertions.assertEquals("camel title", tables[0].getCellAsText(0, 2), "Bad value in column header."); // localized
                                                                                                              // text

        Assertions.assertEquals(KnownValue.ANT, tables[0].getCellAsText(1, 0), "Bad value in column content.");
        Assertions.assertEquals(KnownValue.BEE, tables[0].getCellAsText(1, 1), "Bad value in column content.");
        Assertions.assertEquals(KnownValue.CAMEL, tables[0].getCellAsText(1, 2), "Bad value in column content.");
    }
}
