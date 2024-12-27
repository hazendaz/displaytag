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
package org.displaytag.localization;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.displaytag.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Spring implementation of a resource provider and locale resolver. Since Displaytag locale resolution is modelled on
 * the Spring one, it simply forward <code>resolveLocale</code> calls to the Spring-configured LocaleResolver.
 */
public class I18nSpringAdapter implements LocaleResolver, I18nResourceProvider {

    /**
     * prefix/suffix for missing entries.
     */
    public static final String UNDEFINED_KEY = "???"; //$NON-NLS-1$

    /**
     * logger.
     */
    private static Logger log = LoggerFactory.getLogger(I18nSpringAdapter.class);

    /**
     * Resolve locale.
     *
     * @param pageContext
     *            the page context
     *
     * @return the locale
     *
     * @see LocaleResolver#resolveLocale(PageContext)
     */
    @Override
    public Locale resolveLocale(final PageContext pageContext) {
        return RequestContextUtils.getLocale((HttpServletRequest) pageContext.getRequest());
    }

    /**
     * Gets the resource.
     *
     * @param resourceKey
     *            the resource key
     * @param defaultValue
     *            the default value
     * @param tag
     *            the tag
     * @param pageContext
     *            the page context
     *
     * @return the resource
     *
     * @see I18nResourceProvider#getResource(String, String, Tag, PageContext)
     */
    @Override
    public String getResource(final String resourceKey, final String defaultValue, final Tag tag,
            final PageContext pageContext) {
        final MessageSource messageSource = RequestContextUtils
                .findWebApplicationContext((HttpServletRequest) pageContext.getRequest());
        if (messageSource == null) {
            I18nSpringAdapter.log.warn("messageSource not found");
            return null;
        }

        // if resourceKey isn't defined either, use defaultValue
        final String key = resourceKey != null ? resourceKey : defaultValue;

        String message;

        message = messageSource.getMessage(key, null, null,
                RequestContextUtils.getLocale((HttpServletRequest) pageContext.getRequest()));

        // if user explicitly added a titleKey we guess this is an error
        if (message == null && resourceKey != null) {
            I18nSpringAdapter.log.debug(Messages.getString("Localization.missingkey", resourceKey)); //$NON-NLS-1$
            message = I18nSpringAdapter.UNDEFINED_KEY + resourceKey + I18nSpringAdapter.UNDEFINED_KEY;
        }

        return message;

    }
}
