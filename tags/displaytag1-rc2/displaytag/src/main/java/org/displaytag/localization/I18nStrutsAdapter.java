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
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.RequestUtils;


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
     * logger.
     */
    private static Log log = LogFactory.getLog(I18nStrutsAdapter.class);

    /**
     * @see LocaleResolver#resolveLocale(HttpServletRequest)
     */
    public Locale resolveLocale(HttpServletRequest request)
    {
        return RequestUtils.getUserLocale(request, Globals.LOCALE_KEY);
    }

    /**
     * @see I18nResourceProvider#getResource(String, String, Tag, PageContext)
     */
    public String getResource(String resourceKey, String defaultValue, Tag tag, PageContext pageContext)
    {

        // if titleKey isn't defined either, use property
        String key = (resourceKey != null) ? resourceKey : defaultValue;

        String title = null;
        try
        {
            title = TagUtils.getInstance().message(pageContext, null, Globals.LOCALE_KEY, key);
        }
        catch (JspException e)
        {
            log.debug("Error during lookup for title key [" + key + "]");
        }

        // if user explicitely added a titleKey we guess this is an error
        if (title == null && resourceKey != null)
        {
            title = UNDEFINED_KEY + resourceKey + UNDEFINED_KEY;
        }

        return title;
    }
}
