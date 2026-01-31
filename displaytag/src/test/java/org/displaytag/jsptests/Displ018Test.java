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
 * Tests for DISPL-18 - Setting own comparator for column sorting.
 */
class Displ018Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-018.jsp";
    }

    /**
     * Check sorted columns.
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
        Assertions.assertEquals(4, tables.length, "Wrong number of tables.");

        for (int j = 0; j < tables.length; j++) {
            final WebTable table = tables[j];
            Assertions.assertEquals(2, table.getColumnCount(), "Wrong number of columns in table." + j);
            Assertions.assertEquals(5, table.getRowCount(), "Wrong number of rows in table." + j);

            for (int u = 1; u < 5; u++) {
                Assertions.assertEquals(Integer.toString(u), table.getCellAsText(u, 1), "Wrong value in table cell.");
            }
        }

    }

}
