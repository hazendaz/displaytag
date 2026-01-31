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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for DISPL-224 - Adding the "scope" attribute to table header cells for web accessibility.
 */
class Displ224Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-224.jsp";
    }

    /**
     * Check the content of the title attribute.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {

        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        final WebResponse response = this.runner.getResponse(request);

        final WebTable[] tables = response.getTables();
        Assertions.assertEquals(1, tables.length, "Wrong number of tables in result.");

        Assertions.assertEquals(2, tables[0].getRowCount(), "Wrong number of rows in result.");
        Assertions.assertEquals("col", tables[0].getTableCell(0, 0).getAttribute("scope"));
        Assertions.assertEquals(StringUtils.EMPTY, tables[0].getTableCell(0, 1).getAttribute("scope"));
        Assertions.assertEquals(StringUtils.EMPTY, tables[0].getTableCell(1, 0).getAttribute("scope"));
        Assertions.assertEquals("row", tables[0].getTableCell(1, 1).getAttribute("scope"));

    }

}
