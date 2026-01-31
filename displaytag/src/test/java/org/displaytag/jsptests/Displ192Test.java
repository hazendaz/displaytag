/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.jsptests;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.TableRow;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for DISPL-192 - add row style to current row from TableDecorator.
 */
class Displ192Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-192.jsp";
    }

    /**
     * No exception when an invalid page is requested.
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
        Assertions.assertEquals(4, tables[0].getRowCount(), "Wrong number of rows in result.");

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final TableRow[] rows = tables[0].getRows();
        Assertions.assertEquals(4, rows.length, "Wrong number of rows in result.");

        Assertions.assertEquals("", rows[0].getClassName());
        Assertions.assertEquals("odd", rows[1].getClassName());
        Assertions.assertEquals("even", rows[2].getClassName());
        Assertions.assertEquals("odd highlighted", rows[3].getClassName());

        Assertions.assertEquals("", rows[0].getID());
        Assertions.assertEquals("rowid0", rows[1].getID());
        Assertions.assertEquals("rowid1", rows[2].getID());
        Assertions.assertEquals("rowid2", rows[3].getID());

    }

}
