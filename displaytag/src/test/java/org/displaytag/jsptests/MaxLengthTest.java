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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests for maxlength attribute.
 */
class MaxLengthTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "maxlength.jsp";
    }

    /**
     * Test that title is escaped correctly.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    // TODO JWL 2/26/2023 Disabled test as it fails with tomcat 8+
    @Override
    @Disabled
    @Test
    public void doTest() throws Exception {

        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));

        final WebResponse response = this.runner.getResponse(request);

        final WebTable[] tables = response.getTables();
        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");
        Assertions.assertEquals(4, tables[0].getColumnCount(), "Wrong number of columns.");

        Assertions.assertEquals("123\"567890\"123", tables[0].getTableCell(1, 0).getTitle().trim(), "Broken title.");

        Assertions.assertEquals("123\"567890...", tables[0].getCellAsText(1, 0), "Wrong content in column 1");
        Assertions.assertEquals("Lorem ipsum dolor...", tables[0].getCellAsText(1, 1), "Wrong content in column 2");
        Assertions.assertEquals("", tables[0].getCellAsText(1, 2), "Wrong content in column 3");
        Assertions.assertEquals("", tables[0].getCellAsText(1, 3), "Wrong content in column 4");

    }
}
