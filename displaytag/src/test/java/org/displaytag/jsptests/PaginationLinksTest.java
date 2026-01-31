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

import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.URLAssertions;
import org.displaytag.util.ParamEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Testcase for #944056.
 */
class PaginationLinksTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "pagination-links.jsp";
    }

    /**
     * Checks generated pagination links.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {

        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName())
                + "?initiator=AVINASH&wfid=&approvedTDate=&initiatedFDate=&status=default"
                + "&initiatedTDate=04/28/2004&approvedFDate=&method=search&approver=");

        final WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug("RESPONSE: " + response.getText());
        }

        final WebLink[] links = response.getLinks();

        Assertions.assertEquals(36, links.length, "Wrong number of pagination links");

        String lastLink = links[links.length - 1].getURLString();

        // remove prefix
        lastLink = lastLink.substring(lastLink.indexOf(this.getJspName()));

        final String encodedParam = new ParamEncoder("table2").encodeParameterName(TableTagParameters.PARAMETER_PAGE);

        final String expected = "pagination-links.jsp?initiator=AVINASH&wfid=&approvedTDate=&initiatedFDate=&status=default"
                + "&initiatedTDate=04%2F28%2F2004&approvedFDate=&method=search&approver=&" + encodedParam + "=12";

        URLAssertions.assertEquals(expected, lastLink);

    }
}
