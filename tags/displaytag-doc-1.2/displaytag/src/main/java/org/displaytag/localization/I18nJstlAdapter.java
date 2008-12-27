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
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.taglibs.standard.tag.common.fmt.BundleSupport;
import org.displaytag.Messages;


/**
 * JSTL implementation of a resource provider and locale resolver. It will make the <code>titleKey</code> attribute of
 * column tag works the same as fmt:message's <code>key property</code>. This tag must be the descendant of a
 * <code>fmt:bundle</code> tag in order to use the titleKey. This is just a shortcut, which makes
 * 
 * <pre>
 * &lt;display:column titleKey="bar"/>
 * </pre>
 * 
 * behave the same as
 * 
 * <pre>
 * &lt;c:set var="foo">
 *   &lt;fmt:message key="bar"/>
 * &lt;/c:set>
 * &lt;display:column title="${foo}"/>
 * </pre>
 * 
 * If you don't define either <code>titleKey</code> or <code>titleKey</code> property on your column, first the tag
 * will attempt to look up the <code>property</code> property in your ResourceBundle. Failing that, it will fall back
 * to the parent class's behavior of just using the property name.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class I18nJstlAdapter implements I18nResourceProvider, LocaleResolver
{

    /**
     * prefix/suffix for missing entries.
     */
    public static final String UNDEFINED_KEY = "???"; //$NON-NLS-1$

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(I18nJstlAdapter.class);

    /**
     * Instantiates a new I18nJstlAdapter. Throw a NoClassDefFound error if BundleSupport is not available.
     */
    public I18nJstlAdapter()
    {
        // this will check if BundleSupport is available
        // if a NoClassDefFound error is thrown, the I18nJstlAdapter will not be used
        BundleSupport.class.hashCode();
    }

    /**
     * @see LocaleResolver#resolveLocale(HttpServletRequest)
     */
    public Locale resolveLocale(HttpServletRequest request)
    {
        Locale locale = (Locale) Config.get(request.getSession(), Config.FMT_LOCALE);
        if (locale == null)
        {
            locale = request.getLocale();
        }
        return locale;
    }

    /**
     * @see I18nResourceProvider#getResource(String, String, Tag, PageContext)
     */
    public String getResource(String resourceKey, String defaultValue, Tag tag, PageContext pageContext)
    {

        // if titleKey isn't defined either, use property
        String key = (resourceKey != null) ? resourceKey : defaultValue;
        String title = null;
        ResourceBundle bundle = null;

        // jakarta jstl implementation, there is no other way to get the bundle from the parent fmt:bundle tag
        Tag bundleTag = TagSupport.findAncestorWithClass(tag, BundleSupport.class);
        if (bundleTag != null)
        {
            BundleSupport parent = (BundleSupport) bundleTag;
            if (key != null)
            {
                String prefix = parent.getPrefix();
                if (prefix != null)
                {
                    key = prefix + key;
                }
            }
            bundle = parent.getLocalizationContext().getResourceBundle();
        }

        // resin jstl implementation, more versatile (we don't need to look up resin classes)
        if (bundle == null)
        {
            Object cauchoBundle = pageContext.getAttribute("caucho.bundle"); //$NON-NLS-1$
            if (cauchoBundle != null && cauchoBundle instanceof LocalizationContext)
            {
                bundle = ((LocalizationContext) cauchoBundle).getResourceBundle();

                // handle prefix just like resin does
                String prefix = (String) pageContext.getAttribute("caucho.bundle.prefix"); //$NON-NLS-1$
                if (prefix != null)
                {
                    key = prefix + key;
                }
            }
        }

        // standard jstl localizationContest
        if (bundle == null)
        {
            // check for the localizationContext in applicationScope, set in web.xml
            LocalizationContext localization = BundleSupport.getLocalizationContext(pageContext);

            if (localization != null)
            {
                bundle = localization.getResourceBundle();
            }
        }

        if (bundle != null)
        {
            try
            {
                title = bundle.getString(key);
            }
            catch (MissingResourceException e)
            {
                log.debug(Messages.getString("Localization.missingkey", key)); //$NON-NLS-1$

                // if user explicitely added a titleKey we guess this is an error
                if (resourceKey != null)
                {
                    title = UNDEFINED_KEY + resourceKey + UNDEFINED_KEY;
                }
            }
        }

        return title;
    }
}
