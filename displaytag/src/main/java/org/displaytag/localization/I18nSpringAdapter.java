package org.displaytag.localization;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.support.RequestContextUtils;


/**
 * Spring implementation of a resource provider and locale resolver. Since Displaytag locale resolution is modelled on
 * the Spring one, it simply forward <code>resolveLocale</code> calls to the Spring-configured LocaleResolver.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class I18nSpringAdapter implements LocaleResolver, I18nResourceProvider
{

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(I18nSpringAdapter.class);

    /**
     * @see LocaleResolver#resolveLocale(HttpServletRequest)
     */
    public Locale resolveLocale(HttpServletRequest request)
    {
        return RequestContextUtils.getLocale(request);
    }

    /**
     * @see I18nResourceProvider#getResource(String, String, Tag, PageContext)
     */
    public String getResource(String resourceKey, String defaultValue, Tag tag, PageContext pageContext)
    {
        MessageSource messageSource = RequestContextUtils.getWebApplicationContext(pageContext.getRequest());
        if (messageSource == null)
        {
            return null;
            // @todo log a warn? thrown ax exception?
        }

        String message = null;
        if (resourceKey != null)
        {
            message = messageSource.getMessage(resourceKey, null, null, RequestContextUtils
                .getLocale((HttpServletRequest) pageContext.getRequest()));
            log.debug("Missing resource for key [" + resourceKey + "]");

            // if user explicitely added a resourceKey we guess this is an error
            if (resourceKey != null)
            {
                message = "??" + resourceKey + "??";
            }

        }
        else
        {
            message = messageSource.getMessage(defaultValue, null, null, RequestContextUtils
                .getLocale((HttpServletRequest) pageContext.getRequest()));
        }

        return message;
    }
}
