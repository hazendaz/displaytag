/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.jsptests;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import java.io.IOException;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

/**
 * Test for DISPL-125 - Preserve The Current Page And Sort Across Session.
 */
class Displ125Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-125.jsp";
    }

    /**
     * Preserve The Current Page And Sort Across Session.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        final ParamEncoder encoder = new ParamEncoder("table");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "3");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT), "0");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER), "1");

        WebResponse response = this.runner.getResponse(request);

        this.checkResponse(response);

        // repeating the same request without parameters must return the same result (using session)
        request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        response = this.runner.getResponse(request);
        this.checkResponse(response);
    }

    /**
     * Check response.
     *
     * @param response
     *            the response
     *
     * @throws SAXException
     *             the SAX exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void checkResponse(final WebResponse response) throws SAXException, IOException {

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final WebTable[] tables = response.getTables();
        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");
        Assertions.assertEquals(2, tables[0].getRowCount(), "Wrong number of rows.");
        Assertions.assertEquals("ant", tables[0].getCellAsText(1, 0), "Column content missing?");

        final HTMLElement pagination = response.getElementWithID("pagination");
        Assertions.assertNotNull(pagination, "Paging banner not found.");
        Assertions.assertEquals("1, 2, [3]", pagination.getText(), "Pagination links are not as expected.");

        Assertions.assertEquals("sortable sorted order2", tables[0].getTableCell(0, 0).getClassName(),
                "Column 1 should be marked as sorted.");
    }

}
