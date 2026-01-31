/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.properties;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for "titlekey" column attribute.
 */
class TitleKeyJstlFmtBundleTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "titlekeyfmtbundle.jsp";
    }

    /**
     * Test that headers are correctly removed.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        // test keep
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));

        final WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final WebTable[] tables = response.getTables();
        Assertions.assertEquals(1, tables.length, "Expected one table");

        Assertions.assertEquals("foo bundle", tables[0].getCellAsText(0, 0), "Header from resource is not valid.");
        Assertions.assertEquals("baz bundle", tables[0].getCellAsText(0, 1), "Header from resource is not valid.");
        Assertions.assertEquals("camel bundle", tables[0].getCellAsText(0, 2), "Header from resource is not valid.");
        Assertions.assertEquals("???missing???", tables[0].getCellAsText(0, 3),
                "Missing resource should generate the ???missing??? header.");

    }
}
