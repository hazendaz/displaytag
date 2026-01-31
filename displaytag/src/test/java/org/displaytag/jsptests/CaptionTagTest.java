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

import org.apache.commons.lang3.Strings;
import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for caption tag.
 */
class CaptionTagTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "caption.jsp";
    }

    /**
     * Verifies that the generated page contains a table with a caption and checks all the caption html attributes.
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
            this.log.debug("RESPONSE: " + response.getText());
        }

        final WebTable[] tables = response.getTables();

        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");

        final String output = response.getText();

        Assertions.assertTrue(Strings.CS.contains(output, "<caption"), "Caption tag missing");

        Assertions.assertTrue(Strings.CS.contains(output, "class=\"theclass\""));
        Assertions.assertTrue(Strings.CS.contains(output, "dir=\"thedir\""));
        Assertions.assertTrue(Strings.CS.contains(output, "id=\"theid\""));
        Assertions.assertTrue(Strings.CS.contains(output, "lang=\"thelang\""));
        Assertions.assertTrue(Strings.CS.contains(output, "style=\"thestyle\""));
        Assertions.assertTrue(Strings.CS.contains(output, "title=\"thetitle\""));

    }
}
