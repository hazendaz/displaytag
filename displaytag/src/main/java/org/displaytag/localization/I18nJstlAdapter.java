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
     * logger.
     */
    private static Log log = LogFactory.getLog(I18nJstlAdapter.class);

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
        Tag bundleTag = TagSupport.findAncestorWithClass(tag, BundleSupport.class);
        ResourceBundle bundle = null;
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
        else
        {
            // check for the localizationContext in applicationScope, set in web.xml
            LocalizationContext localization = BundleSupport.getLocalizationContext(pageContext);

            if (localization != null)
            {
                bundle = localization.getResourceBundle();
            }
        }

        if (bundle != null && key != null)
        {
            try
            {
                title = bundle.getString(key);
            }
            catch (MissingResourceException e)
            {
                log.debug("Missing resource for key [" + key + "]");

                // if user explicitely added a titleKey we guess this is an error
                if (resourceKey != null)
                {
                    title = "??" + resourceKey + "??";
                }
            }
        }

        return title;
    }
}
