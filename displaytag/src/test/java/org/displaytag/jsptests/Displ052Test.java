/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.jsptests;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for DISPL-052 - Support for checkboxes.
 */
class Displ052Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-052.jsp";
    }

    /**
     * Preserve The Current Page And Sort Across Session.
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
        Assertions.assertEquals(3, tables[0].getRowCount(), "Wrong number of rows.");
        Assertions.assertEquals("ant", tables[0].getCellAsText(1, 2), "Column content missing?");
        Assertions.assertEquals("INPUT", tables[0].getTableCell(1, 0).getElementsWithName("_chk")[0].getTagName(),
                "Checkbox missing?");
        Assertions.assertEquals("10", tables[0].getTableCell(1, 0).getElementsWithName("_chk")[0].getAttribute("value"),
                "Checkbox value missing?");

        final WebLink[] links = response.getLinks();
        Assertions.assertEquals("javascript:displaytagform(\'displ\',[{f:\'d-148916-p\',v:\'2\'}])",
                links[0].getAttribute("href"), "Wrong link generated");

    }

}
