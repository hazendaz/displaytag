/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.localization;

import jakarta.servlet.jsp.PageContext;

import java.util.Locale;

/**
 * Handle the selection of the user locale. By default <code>request.getLocale()</code> is used, but specific
 * implementations can use different way to determine the locale, for example looking at session variables or cookies.
 */
public interface LocaleResolver {

    /**
     * Returns the Locale choosen for the given request.
     *
     * @param pageContext
     *            PageContext
     *
     * @return a valid Locale (<code>null</code> should never be returned)
     */
    Locale resolveLocale(PageContext pageContext);
}
