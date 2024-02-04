/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
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
import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for DISPL-129 - Partial list support with valuelist pattern.
 *
 * @author Fabrizio Giustina
 *
 * @version $Id: $
 */
class Displ129Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-129.jsp";
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

        final ParamEncoder encoder = new ParamEncoder("table");
        final String pageParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE);
        request.setParameter(pageParameter, "2");

        final WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final WebTable[] tables = response.getTables();
        Assertions.assertEquals(1, tables.length, "Wrong number of tables in result.");
        Assertions.assertEquals(3, tables[0].getRowCount(), "Wrong number of rows in result.");

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        Assertions.assertEquals("Number", tables[0].getCellAsText(0, 0), "Wrong column header.");
        Assertions.assertEquals("3", tables[0].getCellAsText(1, 0), "Wrong column content.");
        Assertions.assertEquals("4", tables[0].getCellAsText(2, 0), "Wrong column content.");

        final TableCell headerCell = tables[0].getTableCell(0, 0);

        final String cssClass = headerCell.getClassName();
        this.assertEqualsIgnoreOrder("Wrong css attributes.", new String[] { "sortable", "sorted", "order2" },
                StringUtils.split(cssClass));

        final WebLink[] headerLinks = headerCell.getLinks();
        Assertions.assertEquals(1, headerLinks.length, "Sorting link not found.");
        final WebLink sortingLink = headerLinks[0];
        this.assertEqualsIgnoreOrder("Wrong parameters.", new String[] { "sort", "searchid", "dir", pageParameter },
                sortingLink.getParameterNames());

        final HTMLElement pagebanner = response.getElementWithID("pagebanner");
        Assertions.assertEquals("10|3|4", pagebanner.getText(), "Wrong page banner");
        final HTMLElement pagelinks = response.getElementWithID("pagelinks");
        Assertions.assertEquals("1|[2]|3|4|5", pagelinks.getText(), "Wrong page links");

    }

}
