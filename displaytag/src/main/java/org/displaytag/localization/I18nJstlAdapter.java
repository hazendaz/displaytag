/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
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
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.tag.common.fmt.BundleSupport;
import org.displaytag.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSTL implementation of a resource provider and locale resolver. It will make the <code>titleKey</code> attribute of
 * column tag works the same as fmt:message's <code>key property</code>. This tag must be the descendant of a
 * <code>fmt:bundle</code> tag in order to use the titleKey. This is just a shortcut, which makes
 *
 * <pre>
 * &lt;display:column titleKey="bar"/&gt;
 * </pre>
 *
 * behave the same as
 *
 * <pre>
 * &lt;c:set var="foo"&gt;
 *   &lt;fmt:message key="bar"/&gt;
 * &lt;/c:set&gt;
 * &lt;display:column title="${foo}"/&gt;
 * </pre>
 *
 * If you don't define either <code>titleKey</code> or <code>titleKey</code> property on your column, first the tag will
 * attempt to look up the <code>property</code> property in your ResourceBundle. Failing that, it will fall back to the
 * parent class's behavior of just using the property name.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class I18nJstlAdapter implements I18nResourceProvider, LocaleResolver {

    /**
     * prefix/suffix for missing entries.
     */
    public static final String UNDEFINED_KEY = "???"; //$NON-NLS-1$

    /**
     * logger.
     */
    private static Logger log = LoggerFactory.getLogger(I18nJstlAdapter.class);

    /**
     * Instantiates a new I18nJstlAdapter. Throw a NoClassDefFound error if BundleSupport is not available.
     */
    public I18nJstlAdapter() {
        // this will check if BundleSupport is available
        // if a NoClassDefFound error is thrown, the I18nJstlAdapter will not be used
        BundleSupport.class.hashCode();
    }

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
        Locale locale = (Locale) Config.find(pageContext, Config.FMT_LOCALE);
        if (locale == null) {
            locale = pageContext.getRequest().getLocale();
        }
        return locale;
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
        String key = resourceKey != null ? resourceKey : defaultValue;
        String title = null;
        ResourceBundle bundle = null;

        // jakarta jstl implementation, there is no other way to get the bundle from the parent fmt:bundle tag
        final Tag bundleTag = TagSupport.findAncestorWithClass(tag, BundleSupport.class);
        if (bundleTag != null) {
            final BundleSupport parent = (BundleSupport) bundleTag;
            if (key != null) {
                final String prefix = parent.getPrefix();
                if (prefix != null) {
                    key = prefix + key;
                }
            }
            bundle = parent.getLocalizationContext().getResourceBundle();
        }

        // resin jstl implementation, more versatile (we don't need to look up resin classes)
        if (bundle == null) {
            final Object cauchoBundle = pageContext.getAttribute("caucho.bundle"); //$NON-NLS-1$
            if (cauchoBundle instanceof LocalizationContext) {
                bundle = ((LocalizationContext) cauchoBundle).getResourceBundle();

                // handle prefix just like resin does
                final String prefix = (String) pageContext.getAttribute("caucho.bundle.prefix"); //$NON-NLS-1$
                if (prefix != null) {
                    key = prefix + key;
                }
            }
        }

        // standard jstl localizationContest
        if (bundle == null) {
            // check for the localizationContext in applicationScope, set in web.xml
            final LocalizationContext localization = BundleSupport.getLocalizationContext(pageContext);

            if (localization != null) {
                bundle = localization.getResourceBundle();
            }
        }

        if (bundle != null) {
            try {
                title = bundle.getString(key);
            } catch (final MissingResourceException e) {
                I18nJstlAdapter.log.debug(Messages.getString("Localization.missingkey", key)); //$NON-NLS-1$

                // if user explicitly added a titleKey we guess this is an error
                if (resourceKey != null) {
                    title = I18nJstlAdapter.UNDEFINED_KEY + resourceKey + I18nJstlAdapter.UNDEFINED_KEY;
                }
            }
        }

        return title;
    }
}
