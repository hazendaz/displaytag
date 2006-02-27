/**
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://displaytag.sourceforge.net/license.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
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
