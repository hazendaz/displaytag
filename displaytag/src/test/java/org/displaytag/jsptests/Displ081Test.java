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
 * Tests for DISPL-81 - using ColumnDecorator with tag body.
 */
class Displ081Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-081.jsp";
    }

    /**
     * Check that column body is decorated.
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
        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");

        Assertions.assertEquals(2, tables[0].getColumnCount(), "Wrong number of columns.");
        Assertions.assertEquals(2, tables[0].getRowCount(), "Wrong number of rows.");

        Assertions.assertEquals("decorated: ant", tables[0].getCellAsText(1, 0), "Wrong text in column 1");
        Assertions.assertEquals("decorated: body", tables[0].getCellAsText(1, 1), "Wrong text in column 2");
    }

}
