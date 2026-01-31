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
 * Test for DISPL-237 - Problems using sorting, defaultsort.
 */
class Displ237Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-237.jsp";
    }

    /**
     * Trying to reproduce an IndexOutOfBoundsException...
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));

        WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final WebTable[] tables = response.getTables();
        Assertions.assertEquals(1, tables.length, "Wrong number of tables");

        WebLink[] links = response.getLinks();
        Assertions.assertEquals(6, links.length, "Wrong number of links"); // sorting + paging

        response = links[3].click(); // sort again on default sorted column
        response = links[4].click(); // sort on column 2

        response.getTables();
        Assertions.assertEquals(1, tables.length, "Wrong number of tables");

        links = response.getLinks();
        Assertions.assertEquals(6, links.length, "Wrong number of links"); // sorting + paging

        response = links[0].click(); // go to page 2

        response.getTables();
        Assertions.assertEquals(1, tables.length, "Wrong number of tables");

        links = response.getLinks();
        Assertions.assertEquals(6, links.length, "Wrong number of links"); // sorting + paging

        response = links[3].click(); // sort again on default sorted column
        response = links[4].click(); // sort on column 2

    }

}
