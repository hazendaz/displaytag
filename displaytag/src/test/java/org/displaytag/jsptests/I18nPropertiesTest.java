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

import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Verify that the TableProperties will show values from the proper locale.
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
