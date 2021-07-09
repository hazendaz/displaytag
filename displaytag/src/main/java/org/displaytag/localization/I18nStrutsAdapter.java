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
package org.displaytag.localization;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.Globals;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;

/**
 * Struts implementation of a resource provider and locale resolver. Uses Struts
 * <code>RequestUtils.getUserLocale()</code> and <code>TagUtils.message()</code> for the lookup.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class I18nStrutsAdapter implements I18nResourceProvider, LocaleResolver {

    /**
     * prefix/suffix for missing entries.
     */
    public static final String UNDEFINED_KEY = "???"; //$NON-NLS-1$

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
        final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        Locale userLocale = null;
        final HttpSession session = request.getSession(false);

        // Only check session if sessions are enabled
        if (session != null) {
            userLocale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
        }

        if (userLocale == null) {
            // Returns Locale based on Accept-Language header or the server default
            userLocale = request.getLocale();
        }

        return userLocale;
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

        // if titleKey isn't defined either, use property
        final String key = resourceKey != null ? resourceKey : defaultValue;

        // retrieve MessageResources. Don't use TagUtils to mantain Struts 1.1 compatibility
        MessageResources resources = (MessageResources) pageContext.getAttribute(Globals.MESSAGES_KEY,
                PageContext.REQUEST_SCOPE);

        if (resources == null) {
            ModuleConfig moduleConfig = (ModuleConfig) pageContext.getRequest().getAttribute(Globals.MODULE_KEY);

            if (moduleConfig == null) {
                moduleConfig = (ModuleConfig) pageContext.getServletContext().getAttribute(Globals.MODULE_KEY);
                pageContext.getRequest().setAttribute(Globals.MODULE_KEY, moduleConfig);
            }

            resources = (MessageResources) pageContext.getAttribute(Globals.MESSAGES_KEY + moduleConfig.getPrefix(),
                    PageContext.APPLICATION_SCOPE);
        }

        if (resources == null) {
            resources = (MessageResources) pageContext.getAttribute(Globals.MESSAGES_KEY,
                    PageContext.APPLICATION_SCOPE);
        }

        String title = null;
        if (resources != null) {
            final Locale userLocale = this.resolveLocale(pageContext);
            title = resources.getMessage(userLocale, key);
        }

        // if user explicitly added a titleKey we guess this is an error
        if (title == null && resourceKey != null) {
            title = I18nStrutsAdapter.UNDEFINED_KEY + resourceKey + I18nStrutsAdapter.UNDEFINED_KEY;
        }

        return title;
    }

}
