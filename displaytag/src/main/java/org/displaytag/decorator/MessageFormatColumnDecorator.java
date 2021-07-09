/*
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
package org.displaytag.decorator;

import java.text.MessageFormat;
import java.util.Locale;

import javax.servlet.jsp.PageContext;

import org.displaytag.Messages;
import org.displaytag.properties.MediaTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A decorator that simply formats input Objects using a <code>java.text.messageFormat</code>. By design, this
 * implementations handle MessageFormat errors by returning the unformatted value and logging the exception.
 *
 * @author Fabrizio Giustina
 *
 * @version $Id$
 */
public class MessageFormatColumnDecorator implements DisplaytagColumnDecorator {

    /**
     * Logger.
     */
    private static Logger log = LoggerFactory.getLogger(MessageFormatColumnDecorator.class);

    /**
     * Pre-compiled messageFormat.
     */
    private final MessageFormat format;

    /**
     * Pattern is held for alternative (eg excel) formatters.
     */
    private final String pattern;

    /**
     * Instantiates a new MessageFormatColumnDecorator with a given pattern and locale.
     *
     * @param pattern
     *            see <code>java.text.messageFormat</code>
     * @param locale
     *            current locale
     *
     * @see java.text.MessageFormat
     */
    public MessageFormatColumnDecorator(final String pattern, final Locale locale) {
        this.pattern = pattern;
        this.format = new MessageFormat(pattern, locale);
    }

    /**
     * Decorate.
     *
     * @param columnValue
     *            the column value
     * @param pageContext
     *            the page context
     * @param media
     *            the media
     *
     * @return the object
     *
     * @see org.displaytag.decorator.DisplaytagColumnDecorator#decorate(Object, PageContext, MediaTypeEnum)
     */
    @Override
    public Object decorate(final Object columnValue, final PageContext pageContext, final MediaTypeEnum media) {
        try {
            return this.format.format(new Object[] { columnValue });
        } catch (final IllegalArgumentException e) {
            MessageFormatColumnDecorator.log.error(Messages.getString("MessageFormatColumnDecorator.invalidArgument", //$NON-NLS-1$
                    new Object[] { this.format.toPattern(),
                            columnValue != null ? columnValue.getClass().getName() : "null" })); //$NON-NLS-1$

            return columnValue;
        }
    }

    /**
     * get the format.
     *
     * @return the format
     */
    public String getPattern() {
        return this.pattern;
    }

}
