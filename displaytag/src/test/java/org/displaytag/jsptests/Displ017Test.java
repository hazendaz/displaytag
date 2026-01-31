/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.jsptests;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

/**
 * Tests for basic displaytag functionalities.
 */
class Displ017Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-017.jsp";
    }

    /**
     * Verifies that the generated page contains a table with the expected number of columns.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {

        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));

        WebResponse response = this.runner.getResponse(request);

        for (int j = 0; j < 4; j++) {
            final WebLink[] links = response.getLinks();
            response = links[j].click();

            if (this.log.isDebugEnabled()) {
                this.log.debug("After clicking on " + j + ":\n" + response.getText());
            }
            this.checkOnlyOneSorted(response, j);
        }

    }

    /**
     * Check that only the expected column is sorted.
     *
     * @param response
     *            WebResponse
     * @param sortedColumn
     *            expected sorted column number
     *
     * @throws SAXException
     *             in processing WebTables
     */
    private void checkOnlyOneSorted(final WebResponse response, final int sortedColumn) throws SAXException {
        final WebTable[] tables = response.getTables();

        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");
        Assertions.assertEquals(4, tables[0].getColumnCount(), "Wrong number of columns in result.");

        for (int j = 0; j < 4; j++) {
            final TableCell cell = tables[0].getTableCell(0, j);
            final boolean containsSorted = cell.getAttribute("class").indexOf("sorted") > -1;
            if (j == sortedColumn) {
                Assertions.assertTrue(containsSorted, "Column " + j + " is not sorted as expected");
            } else {
                Assertions.assertFalse(containsSorted,
                        "Column " + j + " is sorted, but only " + sortedColumn + " should be");
            }

        }

    }

}
