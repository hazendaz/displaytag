/*
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
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

import org.displaytag.localization.I18nResourceProvider;
import org.displaytag.localization.I18nWebworkAdapter;
import org.displaytag.localization.LocaleResolver;
import org.junit.Test;

import com.opensymphony.webwork.dispatcher.ServletDispatcher;

/**
 * I18n test with WebWork adapter.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class TitleKeyWebWorkTest extends AbstractTitleKeyTest {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return super.getJspName() + ".webwork";
    }

    /**
     * Gets the expected suffix.
     *
     * @return the expected suffix
     *
     * @see org.displaytag.properties.AbstractTitleKeyTest#getExpectedSuffix()
     */
    @Override
    protected String getExpectedSuffix() {
        return " webwork";
    }

    /**
     * Gets the i 18 n resource provider.
     *
     * @return the i 18 n resource provider
     *
     * @see org.displaytag.properties.AbstractTitleKeyTest#getI18nResourceProvider()
     */
    @Override
    protected I18nResourceProvider getI18nResourceProvider() {
        return new I18nWebworkAdapter();
    }

    /**
     * Gets the resolver.
     *
     * @return the resolver
     *
     * @see org.displaytag.properties.AbstractTitleKeyTest#getResolver()
     */
    @Override
    protected LocaleResolver getResolver() {
        return new I18nWebworkAdapter();
    }

    /**
     * Do test.
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.test.DisplaytagCase#doTest()
     */
    @Override
    @Test
    public void doTest() throws Exception {
        this.runner.registerServlet("*.webwork", ServletDispatcher.class.getName());
        super.doTest();
    }

}
