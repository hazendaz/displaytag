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
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Verify that the TableProperties will show values from the proper locale.
 *
 * @author rapruitt
 *
 * @version $Revision$ ($Author$)
 */
class I18nPropertiesTest extends DisplaytagCase {

    /**
     * No results for an en locale.
     */
    private static final String MSG_DEFAULT = "There are no results.";

    /**
     * No results for an it locale.
     */
    private static final String MSG_IT = "Non ci sono risultati.";

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "i18nProperties.jsp";
    }

    /**
     * Check that the "no results" property is loaded from the correct locale file.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {

        WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        request.setHeaderField("Accept-Language", "en-us,en;q=0.5");

        WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug("RESPONSE: " + response.getText());
        }

        Assertions.assertTrue(response.getText().indexOf(I18nPropertiesTest.MSG_DEFAULT) > -1, "Expected message\""
                + I18nPropertiesTest.MSG_DEFAULT + "\" has not been found in response with locale en");
        Assertions.assertEquals(-1, response.getText().indexOf(I18nPropertiesTest.MSG_IT),
                "Unexpected message\"" + I18nPropertiesTest.MSG_IT + "\" has been found in response with locale en");

        // Now, with an Italian locale.
        request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        request.setHeaderField("Accept-Language", "it-it,it;q=0.5");

        response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug("RESPONSE: " + response.getText());
        }

        Assertions.assertTrue(response.getText().indexOf(I18nPropertiesTest.MSG_IT) > -1,
                "Expected message\"" + I18nPropertiesTest.MSG_IT + "\" has not been found in response with locale it");
        Assertions.assertEquals(-1, response.getText().indexOf(I18nPropertiesTest.MSG_DEFAULT), "Unexpected message\""
                + I18nPropertiesTest.MSG_DEFAULT + "\" has been found in response with locale it");
    }
}
