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

import org.displaytag.properties.SortOrderEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for DISPL-208 - Column level default sort order.
 */
class Displ208Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-208.jsp";
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
        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");

        final WebLink[] links = response.getLinks();
        Assertions.assertEquals(3, links.length, "Wrong number of links.");

        final ParamEncoder encoder = new ParamEncoder("table");
        final String orderParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER);

        Assertions.assertEquals(Integer.toString(SortOrderEnum.DESCENDING.getCode()),
                links[0].getParameterValues(orderParameter)[0], "wrong sorting order");
        Assertions.assertEquals(Integer.toString(SortOrderEnum.ASCENDING.getCode()),
                links[1].getParameterValues(orderParameter)[0], "wrong sorting order");
        Assertions.assertEquals(Integer.toString(SortOrderEnum.ASCENDING.getCode()),
                links[2].getParameterValues(orderParameter)[0], "wrong sorting order");

    }

}
