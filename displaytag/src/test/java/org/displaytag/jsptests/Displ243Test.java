/*
 * Copyright (C) 2002-2024 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
 * Test for DISPL-243 - Default column sort breaks sorting after a few sorts of the column.
 */
class Displ243Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-243.jsp";
    }

    /**
     * CHeck sort order after some clicks.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        final ParamEncoder encoder = new ParamEncoder("table");
        final String orderParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER);

        WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");

        WebLink[] links = response.getLinks();
        Assertions.assertEquals(1, links.length, "Wrong number of links.");

        Assertions.assertEquals(Integer.toString(SortOrderEnum.DESCENDING.getCode()),
                links[0].getParameterValues(orderParameter)[0], "wrong sorting order");

        // a few clicks...
        for (int j = 0; j < 10; j++) {
            final String expectedSortOrder = j % 2 == 0 ? SortOrderEnum.ASCENDING.getName()
                    : SortOrderEnum.DESCENDING.getName();

            response = links[0].click();

            if (this.log.isDebugEnabled()) {
                this.log.debug(response.getText());
            }

            tables = response.getTables();
            Assertions.assertEquals(1, tables.length, "Wrong number of tables.");

            links = response.getLinks();
            Assertions.assertEquals(1, links.length, "Wrong number of links.");

            Assertions.assertEquals(expectedSortOrder,
                    SortOrderEnum.fromCode(Integer.parseInt(links[0].getParameterValues(orderParameter)[0])).getName(),
                    "Wrong sorting order for iteration " + j);
        }

    }

}
