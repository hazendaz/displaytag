/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.decorator;

import jakarta.servlet.jsp.PageContext;

import java.text.MessageFormat;
import java.util.Locale;

import org.displaytag.Messages;
import org.displaytag.properties.MediaTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A decorator that simply formats input Objects using a <code>java.text.messageFormat</code>. By design, this
 * implementations handle MessageFormat errors by returning the unformatted value and logging the exception.
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
