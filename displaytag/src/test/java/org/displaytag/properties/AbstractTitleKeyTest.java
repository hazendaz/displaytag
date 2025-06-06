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
package org.displaytag.properties;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import org.displaytag.localization.I18nResourceProvider;
import org.displaytag.localization.LocaleResolver;
import org.displaytag.test.DisplaytagCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for "titlekey" column attribute.
 */
public abstract class AbstractTitleKeyTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "titlekey.jsp";
    }

    /**
     * Returns the LocaleResolver instance to be used in this test.
     *
     * @return LocaleResolver
     */
    protected abstract LocaleResolver getResolver();

    /**
     * Returns the I18nResourceProvider instance to be used in this test.
     *
     * @return I18nResourceProvider
     */
    protected abstract I18nResourceProvider getI18nResourceProvider();

    /**
     * Returns the suffix expected in the specific resource bundle.
     *
     * @return expected suffix
     */
    protected abstract String getExpectedSuffix();

    /**
     * Test that headers are correctly removed.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        // test keep
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));

        TableProperties.setLocaleResolver(this.getResolver());
        TableProperties.setResourceProvider(this.getI18nResourceProvider());

        WebResponse response;
        try {
            response = this.runner.getResponse(request);
        } finally {
            // reset
            TableProperties.setLocaleResolver(null);
            TableProperties.setResourceProvider(null);
        }

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final WebTable[] tables = response.getTables();
        Assertions.assertEquals(1, tables.length, "Expected one table");

        Assertions.assertEquals("foo title" + this.getExpectedSuffix(), tables[0].getCellAsText(0, 0),
                "Header from resource is not valid.");

        Assertions.assertEquals("baz title" + this.getExpectedSuffix(), tables[0].getCellAsText(0, 1),
                "Header from resource is not valid.");

        Assertions.assertEquals("camel title" + this.getExpectedSuffix(), tables[0].getCellAsText(0, 2),
                "Header from resource is not valid.");

        Assertions.assertEquals("???missing???", tables[0].getCellAsText(0, 3),
                "Missing resource should generate the ???missing??? header.");

    }
}
