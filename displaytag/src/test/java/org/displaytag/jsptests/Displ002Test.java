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
 * Tests for DISPL-2 - Ability to use java var in id attribute in tabletag.
 */
class Displ002Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-002.jsp";
    }

    /**
     * Check if tables are generated with variable id and content in column is filled appropriately.
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
        Assertions.assertEquals(4, tables.length, "Wrong number of tables in result.");

        for (int j = 0; j < tables.length; j++) {
            final WebTable table = tables[j];
            Assertions.assertEquals(2, table.getRowCount(), "Wrong number of rows in table " + (j + 1));
            Assertions.assertEquals("ant", table.getCellAsText(1, 0), "Wrong content in cell for table " + (j + 1));
            Assertions.assertEquals("bee", table.getCellAsText(1, 1), "Wrong content in cell for table " + (j + 1));
        }
    }
}
