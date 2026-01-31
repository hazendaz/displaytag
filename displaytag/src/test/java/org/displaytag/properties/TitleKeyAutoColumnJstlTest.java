/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.properties;

import org.displaytag.localization.I18nJstlAdapter;
import org.displaytag.localization.I18nResourceProvider;
import org.displaytag.localization.LocaleResolver;

/**
 * I18n test with JSTL adapter.
 */
public class TitleKeyAutoColumnJstlTest extends AbstractTitleKeyAutoColumnTest {

    /**
     * Gets the expected suffix.
     *
     * @return the expected suffix
     *
     * @see org.displaytag.properties.AbstractTitleKeyTest#getExpectedSuffix()
     */
    @Override
    protected String getExpectedSuffix() {
        return "";
    }

    /**
     * Gets the i 18 n resource provider.
     *
     * @return the i 18 n resource provider
     *
     * @see org.displaytag.properties.AbstractTitleKeyAutoColumnTest#getI18nResourceProvider()
     */
    @Override
    protected I18nResourceProvider getI18nResourceProvider() {
        return new I18nJstlAdapter();
    }

    /**
     * Gets the resolver.
     *
     * @return the resolver
     *
     * @see org.displaytag.properties.AbstractTitleKeyAutoColumnTest#getResolver()
     */
    @Override
    protected LocaleResolver getResolver() {
        return new I18nJstlAdapter();
    }

}
