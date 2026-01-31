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
 * Test for DISPL-234 - HTML title not added with chopped value (column tag - maxLength attribute).
 */
class Displ234Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-234.jsp";
    }

    /**
     * Title should be added to td.
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
        Assertions.assertEquals(1, tables.length, "Wrong number of tables in result.");
        Assertions.assertEquals(2, tables[0].getRowCount(), "Wrong number of rows in result.");

        Assertions.assertEquals("123456789012", tables[0].getTableCell(1, 0).getAttribute("title"),
                "Wrong or missing title for cropped text.");
        Assertions.assertEquals("12345678901234", tables[0].getTableCell(1, 1).getAttribute("title"),
                "Wrong or missing title for cropped text.");
        Assertions.assertEquals("12345678901234567", tables[0].getTableCell(1, 2).getAttribute("title"),
                "Wrong or missing title for cropped text.");
        Assertions.assertEquals("", tables[0].getTableCell(1, 3).getAttribute("title"), "Title should not be added.");

    }

}
