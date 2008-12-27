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
package org.displaytag.decorator;

import java.text.MessageFormat;
import java.util.Locale;

import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.Messages;
import org.displaytag.properties.MediaTypeEnum;


/**
 * A decorator that simply formats input Objects using a <code>java.text.messageFormat</code>. By design, this
 * implementations handle MessageFormat errors by returning the unformatted value and logging the exception.
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class MessageFormatColumnDecorator implements DisplaytagColumnDecorator
{

    /**
     * Logger.
     */
    private static Log log = LogFactory.getLog(MessageFormatColumnDecorator.class);

    /**
     * Pre-compiled messageFormat.
     */
    private MessageFormat format;

    /**
     * Instantiates a new MessageFormatColumnDecorator with a given pattern and locale.
     * @param pattern see <code>java.text.messageFormat</code>
     * @param locale current locale
     * @see java.text.messageFormat
     */
    public MessageFormatColumnDecorator(String pattern, Locale locale)
    {
        this.format = new MessageFormat(pattern, locale);
    }

    /**
     * @see org.displaytag.decorator.DisplaytagColumnDecorator#decorate(Object, PageContext, MediaTypeEnum)
     */
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media)
    {
        try
        {
            return this.format.format(new Object[]{columnValue});
        }
        catch (IllegalArgumentException e)
        {
            log.error(Messages.getString("MessageFormatColumnDecorator.invalidArgument", new Object[]{ //$NON-NLS-1$
                this.format.toPattern(), columnValue != null ? columnValue.getClass().getName() : "null"})); //$NON-NLS-1$

            return columnValue;
        }
    }
}
