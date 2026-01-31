/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.properties;

import org.displaytag.localization.I18nResourceProvider;
import org.displaytag.localization.I18nSpringAdapter;
import org.displaytag.localization.LocaleResolver;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * I18n test with Spring adapter.
 */
class TitleKeySpringTest extends AbstractTitleKeyTest {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return super.getJspName() + ".spring";
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
        return " spring";
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
        return new I18nSpringAdapter();
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
        return new I18nSpringAdapter();
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
        this.runner.registerServlet("*.spring", DispatcherServlet.class.getName());
        super.doTest();
    }

}
