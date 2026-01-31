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
 * The Class PaginationGroupingPageTwoTest.
 */
class PaginationGroupingPageTwoTest extends DisplaytagCase {

    /**
     * Do test.
     *
     * @throws Exception
     *             the exception
     */
    @Override
    public void doTest() throws Exception {
        // Not used
    }

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     */
    @Override
    public String getJspName() {
        // Not used
        return null;
    }

    /**
     * Use offset to get page two.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void useOffsetToGetPageTwo() throws Exception {
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl("pagination-grouping-page2.jsp"));
        final WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final WebTable[] tables = response.getTables();

        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");
        Assertions.assertEquals(3, tables[0].getColumnCount(), "Bad number of generated columns.");
        Assertions.assertEquals("8.0", tables[0].getCellAsText(6, 1), "Bad sub-total for group 1");
        Assertions.assertEquals("10.0", tables[0].getCellAsText(9, 1), "Bad grand total");
    }

    /**
     * Navigate to page two.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void navigateToPageTwo() throws Exception {
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl("pagination-grouping.jsp"));
        request.setParameter("d-148916-p", "2");
        final WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final WebTable[] tables = response.getTables();

        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");
        Assertions.assertEquals(3, tables[0].getColumnCount(), "Bad number of generated columns.");
        Assertions.assertEquals("18.0", tables[0].getCellAsText(6, 1), "Bad sub-total for group 1");
        Assertions.assertEquals("10.0", tables[0].getCellAsText(9, 1), "Bad grand total");
    }
}
