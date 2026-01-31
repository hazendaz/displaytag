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

import org.apache.commons.lang3.StringUtils;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * The Class TotalsTest.
 */
class TotalsTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "totals.jsp";
    }

    /**
     * Check sorted columns.
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

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final WebTable[] tables = response.getTables();

        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");

        Assertions.assertEquals(3, tables[0].getColumnCount(), "Bad number of generated columns.");
        // The footer will PRECEDE the body.
        Assertions.assertTrue(StringUtils.isBlank(tables[0].getCellAsText(1, 0)),
                "Totals should not be calculated / present if the column is not so marked.  Value is: "
                        + tables[0].getCellAsText(1, 0));
        Assertions.assertEquals("" + KnownTypes.LONG_VALUE.doubleValue() * 2, tables[0].getCellAsText(1, 1),
                "Bad value in footer cell total.");
        Assertions.assertEquals("" + KnownTypes.LONG_VALUE.doubleValue() * 2, tables[0].getCellAsText(1, 2),
                "Bad value in footer cell total.");
    }
}
