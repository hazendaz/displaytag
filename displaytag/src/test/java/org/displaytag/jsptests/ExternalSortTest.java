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

import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Basic tests for pagination.
 */
class ExternalSortTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "externalSort.jsp";
    }

    /**
     * Verifies that the generated page contains the pagination links with the inupt parameter. Tests #917200 ("{}" in
     * parameters).
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {

        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        final ParamEncoder p1 = new ParamEncoder("table");
        final ParamEncoder p2 = new ParamEncoder("table2");

        request.setParameter(p2.encodeParameterName(TableTagParameters.PARAMETER_SORT), "number");
        request.setParameter(p2.encodeParameterName(TableTagParameters.PARAMETER_SORTUSINGNAME), "1");

        final WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug("RESPONSE: " + response.getText());
        }

        final WebLink[] links = response.getLinks();

        Assertions.assertEquals("0",
                links[0].getParameterValues(p1.encodeParameterName(TableTagParameters.PARAMETER_SORT))[0]);
        Assertions.assertEquals("2",
                links[0].getParameterValues(p1.encodeParameterName(TableTagParameters.PARAMETER_ORDER))[0]);

        Assertions.assertEquals("buzz",
                links[1].getParameterValues(p1.encodeParameterName(TableTagParameters.PARAMETER_SORT))[0]);
        Assertions.assertEquals("2",
                links[1].getParameterValues(p1.encodeParameterName(TableTagParameters.PARAMETER_ORDER))[0]);

        // test that the column with sortName buzz was set as sorted and now has a link to sort desc
        Assertions.assertEquals("number",
                links[2].getParameterValues(p2.encodeParameterName(TableTagParameters.PARAMETER_SORT))[0]);
        Assertions.assertEquals("1",
                links[2].getParameterValues(p2.encodeParameterName(TableTagParameters.PARAMETER_ORDER))[0]);

        // now ensure that our data has not been sorted at all since we are doing it 'externally'
        final WebTable[] tables = response.getTables();
        Assertions.assertEquals("1", tables[1].getCellAsText(1, 0));
        Assertions.assertEquals("4", tables[1].getCellAsText(2, 0));
        Assertions.assertEquals("2", tables[1].getCellAsText(3, 0));
        Assertions.assertEquals("3", tables[1].getCellAsText(4, 0));
    }
}
