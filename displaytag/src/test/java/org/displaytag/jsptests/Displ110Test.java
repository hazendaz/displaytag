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
 * Test for DISPL-110 - Ability to highlight selected table row.
 */
class Displ110Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-110.jsp";
    }

    /**
     * Do test.
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

        Assertions.assertEquals(2, tables[0].getColumnCount(), "Wrong number of columns.");
        Assertions.assertEquals(3, tables[0].getRowCount(), "Wrong number of rows."); // 2 plus header

        final TableRow[] rows = tables[0].getRows();

        Assertions.assertEquals("idcamel0", rows[1].getID(), "Wrong id for row 1");
        Assertions.assertEquals("idcamel1", rows[2].getID(), "Wrong id for row 2");

        Assertions.assertEquals("odd classcamel0", rows[1].getClassName(), "Wrong class for row 1");
        Assertions.assertEquals("even classcamel1", rows[2].getClassName(), "Wrong class for row 2");

    }
}
