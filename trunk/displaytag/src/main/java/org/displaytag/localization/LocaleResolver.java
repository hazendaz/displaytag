package org.displaytag.localization;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;


/**
 * Handle the selection of the user locale. By default <code>request.getLocale()</code> is used, but specific
 * implementations can use different way to determine the locale, for example looking at session variables or cookies.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public interface LocaleResolver
{

    /**
     * Returns the Locale choosen for the given request.
     * @param request HttpServletRequest
     * @return a valid Locale (<code>null</code> should never be returned)
     */
    Locale resolveLocale(HttpServletRequest request);
}
