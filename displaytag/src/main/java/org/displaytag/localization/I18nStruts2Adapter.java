/**
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
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

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.views.jsp.TagUtils;
import org.displaytag.Messages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import java.util.Iterator;
import java.util.Locale;


/**
 * Resolve i18n resources in Struts 2.
 *
 * @author <a:href="mailto:snowwolf@wabunoh-tech.com">mailto:snowwolf@wabunoh-tech.com</>
 * @version $Revision: 1.1 $
 * @since Jan 4, 2008 2:37:29 PM
 */
public class I18nStruts2Adapter implements LocaleResolver, I18nResourceProvider
{

    /**
     * prefix/suffix for missing entries.
     */
    public static final String UNDEFINED_KEY = "???"; //$NON-NLS-1$

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(I18nWebworkAdapter.class);

    /**
     * @see LocaleResolver#resolveLocale(PageContext)
     */
    @Override
    public Locale resolveLocale(PageContext pageContext)
    {

        Locale result = null;
        ValueStack stack = ActionContext.getContext().getValueStack();

        Iterator<Object> iterator = stack.getRoot().iterator();
        while (iterator.hasNext())
        {
            Object o = iterator.next();

            if (o instanceof LocaleProvider)
            {
                LocaleProvider lp = (LocaleProvider) o;
                result = lp.getLocale();

                break;
            }
        }

        if (result == null)
        {
            log.debug("Missing LocalProvider actions, init locale to default");
            result = Locale.getDefault();
        }

        return result;
    }

    /**
     * @see I18nResourceProvider#getResource(String, String, Tag, javax.servlet.jsp.PageContext)
     */
    @Override
    public String getResource(String resourceKey, String defaultValue, Tag tag, PageContext pageContext)
    {

        // if resourceKey isn't defined either, use defaultValue
        String key = (resourceKey != null) ? resourceKey : defaultValue;

        String message = null;
        ValueStack stack = TagUtils.getStack(pageContext);
        Iterator<Object> iterator = stack.getRoot().iterator();

        while (iterator.hasNext())
        {
            Object o = iterator.next();

            if (o instanceof TextProvider)
            {
                TextProvider tp = (TextProvider) o;
                message = tp.getText(key, null, (String) null);

                break;
            }
        }

        // if user explicitely added a titleKey we guess this is an error
        if (message == null && resourceKey != null)
        {
            log.debug(Messages.getString("Localization.missingkey", resourceKey)); //$NON-NLS-1$
            message = UNDEFINED_KEY + resourceKey + UNDEFINED_KEY;
        }

        return message;
    }

}