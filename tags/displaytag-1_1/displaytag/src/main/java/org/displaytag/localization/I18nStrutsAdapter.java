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
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.Globals;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;


/**
 * Struts implementation of a resource provider and locale resolver. Uses Struts
 * <code>RequestUtils.getUserLocale()</code> and <code>TagUtils.message()</code> for the lookup.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class I18nStrutsAdapter implements I18nResourceProvider, LocaleResolver
{

    /**
     * prefix/suffix for missing entries.
     */
    public static final String UNDEFINED_KEY = "???"; //$NON-NLS-1$

    /**
     * @see LocaleResolver#resolveLocale(HttpServletRequest)
     */
    public Locale resolveLocale(HttpServletRequest request)
    {
        Locale userLocale = null;
        HttpSession session = request.getSession(false);

        // Only check session if sessions are enabled
        if (session != null)
        {
            userLocale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
        }

        if (userLocale == null)
        {
            // Returns Locale based on Accept-Language header or the server default
            userLocale = request.getLocale();
        }

        return userLocale;
    }

    /**
     * @see I18nResourceProvider#getResource(String, String, Tag, PageContext)
     */
    public String getResource(String resourceKey, String defaultValue, Tag tag, PageContext pageContext)
    {

        // if titleKey isn't defined either, use property
        String key = (resourceKey != null) ? resourceKey : defaultValue;

        // retrieve MessageResources. Don't use TagUtils to mantain Struts 1.1 compatibility
        MessageResources resources = (MessageResources) pageContext.getAttribute(
            Globals.MESSAGES_KEY,
            PageContext.REQUEST_SCOPE);

        if (resources == null)
        {
            ModuleConfig moduleConfig = (ModuleConfig) pageContext.getRequest().getAttribute(Globals.MODULE_KEY);

            if (moduleConfig == null)
            {
                moduleConfig = (ModuleConfig) pageContext.getServletContext().getAttribute(Globals.MODULE_KEY);
                pageContext.getRequest().setAttribute(Globals.MODULE_KEY, moduleConfig);
            }

            resources = (MessageResources) pageContext.getAttribute(
                Globals.MESSAGES_KEY + moduleConfig.getPrefix(),
                PageContext.APPLICATION_SCOPE);
        }

        if (resources == null)
        {
            resources = (MessageResources) pageContext
                .getAttribute(Globals.MESSAGES_KEY, PageContext.APPLICATION_SCOPE);
        }

        String title = null;
        if (resources != null)
        {
            Locale userLocale = resolveLocale((HttpServletRequest) pageContext.getRequest());
            title = resources.getMessage(userLocale, key);
        }

        // if user explicitely added a titleKey we guess this is an error
        if (title == null && resourceKey != null)
        {
            title = UNDEFINED_KEY + resourceKey + UNDEFINED_KEY;
        }

        return title;
    }

}
