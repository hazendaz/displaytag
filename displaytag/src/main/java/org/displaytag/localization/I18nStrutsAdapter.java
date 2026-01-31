/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.localization;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.Tag;

import java.util.Locale;

import org.apache.struts.Globals;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;

/**
 * Struts implementation of a resource provider and locale resolver. Uses Struts
 * <code>RequestUtils.getUserLocale()</code> and <code>TagUtils.message()</code> for the lookup.
 */
public class I18nStrutsAdapter implements I18nResourceProvider, LocaleResolver {

    /**
     * prefix/suffix for missing entries.
     */
    public static final String UNDEFINED_KEY = "???"; //$NON-NLS-1$

    /**
     * @see LocaleResolver#resolveLocale(PageContext)
     */
    @Override
    public Locale resolveLocale(PageContext pageContext) {
        HttpServletRequest request = ((HttpServletRequest) pageContext.getRequest());
        Locale userLocale = null;
        HttpSession session = request.getSession(false);

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
     * @see I18nResourceProvider#getResource(String, String, Tag, PageContext)
     */
    @Override
    public String getResource(String resourceKey, String defaultValue, Tag tag, PageContext pageContext) {

        // if titleKey isn't defined either, use property
        String key = (resourceKey != null) ? resourceKey : defaultValue;

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
            Locale userLocale = resolveLocale(pageContext);
            title = resources.getMessage(userLocale, key);
        }

        // if user explicitely added a titleKey we guess this is an error
        if (title == null && resourceKey != null) {
            title = UNDEFINED_KEY + resourceKey + UNDEFINED_KEY;
        }

        return title;
    }

}
