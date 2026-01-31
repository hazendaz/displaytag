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

import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for DISPL-209 - getListIndex() does not return the real list index. Note: the result is different from the one
 * expected from the decription in the Jira report, but after the test it was clear that there is no usable way to get
 * the desired result.
 */
class Displ209Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-209.jsp";
    }

    /**
     * Check list index/view index values.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        final WebResponse response = this.runner.getResponse(request);

        final ParamEncoder encoder = new ParamEncoder("table");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "2");

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final WebTable[] tables = response.getTables();
        Assertions.assertEquals(1, tables.length, "Wrong number of tables in result.");
        Assertions.assertEquals(4, tables[0].getRowCount(), "Wrong number of rows in result.");

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        Assertions.assertEquals("a", tables[0].getCellAsText(1, 0), "Wrong value");
        Assertions.assertEquals("0", tables[0].getCellAsText(1, 1), "Wrong viewIndex");
        Assertions.assertEquals("0", tables[0].getCellAsText(1, 2), "Wrong listIndex");

        Assertions.assertEquals("b", tables[0].getCellAsText(2, 0), "Wrong value");
        Assertions.assertEquals("1", tables[0].getCellAsText(2, 1), "Wrong viewIndex");
        Assertions.assertEquals("1", tables[0].getCellAsText(2, 2), "Wrong listIndex");

        Assertions.assertEquals("c", tables[0].getCellAsText(3, 0), "Wrong value");
        Assertions.assertEquals("2", tables[0].getCellAsText(3, 1), "Wrong viewIndex");
        Assertions.assertEquals("2", tables[0].getCellAsText(3, 2), "Wrong listIndex");
    }

}
