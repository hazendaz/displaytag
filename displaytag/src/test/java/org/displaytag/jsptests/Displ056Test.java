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

import org.apache.commons.lang3.Strings;
import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for DISPL-56 - unable to dinamically generate multiple tables on the same page with indipendent sorting
 * (different id).
 */
class Displ056Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-056.jsp";
    }

    /**
     * Try to sort generated tables.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        WebResponse response;

        response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        Assertions.assertEquals(3, tables.length, "Wrong number of tables in result.");

        for (int j = 0; j < tables.length; j++) {
            Assertions.assertEquals("row" + j, tables[j].getID(), "invalid id");
        }

        WebLink[] links = response.getLinks();
        Assertions.assertEquals(3, links.length, "Wrong number of links in result.");

        // click to sort the first table
        response = links[0].click();

        // get the links
        links = response.getLinks();
        Assertions.assertEquals(3, links.length, "Wrong number of links in result.");

        // and click again to sort in reversed order
        response = links[0].click();

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        tables = response.getTables();
        Assertions.assertEquals(3, tables.length, "Wrong number of tables in result.");

        // first is sorted, other aren't
        Assertions.assertTrue(Strings.CS.contains(tables[0].getTableCell(0, 0).getClassName(), "sorted"),
                "First table should be sorted. Wrong class attribute.");
        Assertions.assertEquals("sortable", tables[1].getTableCell(0, 0).getClassName(),
                "Second table should not be sorted. Wrong class attribute.");
        Assertions.assertEquals("sortable", tables[2].getTableCell(0, 0).getClassName(),
                "Third table should not be sorted. Wrong class attribute.");

        // and just to be sure also check values: sorted table
        for (int j = 1; j < tables[0].getRowCount(); j++) {
            Assertions.assertEquals(Integer.toString(4 - j), tables[0].getCellAsText(j, 0),
                    "Unexpected value in table cell");
        }

        // unsorted tables:
        for (int j = 1; j < tables[1].getRowCount(); j++) {
            Assertions.assertEquals(Integer.toString(j), tables[1].getCellAsText(j, 0),
                    "Unexpected value in table cell");
            Assertions.assertEquals(Integer.toString(j), tables[2].getCellAsText(j, 0),
                    "Unexpected value in table cell");
        }
    }
}
