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
 * The Class PaginationGroupingTest.
 */
class PaginationGroupingTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     */
    @Override
    public String getJspName() {
        return "pagination-grouping.jsp";
    }

    /**
     * Do test.
     *
     * @throws Exception
     *             the exception
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
        Assertions.assertEquals(3, tables[0].getColumnCount(), "Bad number of generated columns.");
        Assertions.assertEquals("4.0", tables[0].getCellAsText(4, 1), "Bad sub-total for group 1");
        Assertions.assertEquals("6.0", tables[0].getCellAsText(9, 1), "Bad sub-total for group 2");
        Assertions.assertEquals("10.0", tables[0].getCellAsText(10, 1), "Bad grand total");
    }
}
