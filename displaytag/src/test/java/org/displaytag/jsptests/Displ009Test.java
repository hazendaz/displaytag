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
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for DISPL-9 - Send user back to Page 1 on Desc/Asc.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
class Displ009Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-009.jsp";
    }

    /**
     * Verifies that the generated page contains a table with the expected number of columns.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        WebResponse response;

        // step 0
        response = this.runner.getResponse(request);

        // link[0] is a column header
        // link[1] is paging link

        // 1. User clicks column 3 header to sort ascending. User returned to page 1.
        response = response.getLinks()[0].click();
        Assertions.assertNotNull(response.getElementWithID("PAGEONE"), "Not in page one as expected");

        // 2. User clicks column 3 header to sort descending. User returned to page 1.
        response = response.getLinks()[0].click();
        Assertions.assertNotNull(response.getElementWithID("PAGEONE"), "Not in page one as expected");

        // 3. User navigates to page other than page 1.
        response = response.getLinks()[1].click();
        Assertions.assertNotNull(response.getElementWithID("OTHERPAGE"), "Not in page two as expected");

        // 4. User clicks column 3 header to sort ascending. User NOT returned to page 1, rather, user stays on current
        // page number.
        response = response.getLinks()[0].click();
        Assertions.assertNotNull(response.getElementWithID("PAGEONE"), "Not in page one as expected");
    }

}
