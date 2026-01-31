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

import org.displaytag.decorator.DateColumnDecorator;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests for DISPL-505 - Class cache usage causes make using different factory for different tables not work.
 */
class Displ505Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-505.jsp";
    }

    /**
     * Check additional parameters in urls.
     *
     * @throws Exception
     *             any Exception thrown during test.
     */
    // TODO JWL 2/26/2023 Disabled test as it fails with tomcat 8+
    @Override
    @Disabled
    @Test
    public void doTest() throws Exception {
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));

        final WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug("RESPONSE: " + response.getText());
        }

        final WebTable[] tables = response.getTables();

        Assertions.assertEquals(2, tables.length, "Wrong number of tables.");

        Assertions.assertEquals(new DateColumnDecorator().decorate(KnownTypes.TIME_VALUE, null, null),
                tables[0].getCellAsText(1, 0), "Expected decorated value not found.");

        Assertions.assertEquals(KnownTypes.TIME_VALUE.toString(), tables[1].getCellAsText(1, 0),
                "Expected decorated value not found.");
    }
}
