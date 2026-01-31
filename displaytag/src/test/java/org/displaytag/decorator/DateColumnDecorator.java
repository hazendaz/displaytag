/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.decorator;

import java.util.Locale;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang3.time.FastDateFormat;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

/**
 * A test column decorator for dates.
 */
public class DateColumnDecorator implements DisplaytagColumnDecorator {

    /**
     * date formatter.
     */
    FastDateFormat dateFormat = FastDateFormat.getInstance("EEEE", Locale.ENGLISH);

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
     * @throws DecoratorException
     *             the decorator exception
     *
     * @see org.displaytag.decorator.DisplaytagColumnDecorator#decorate(java.lang.Object, javax.servlet.jsp.PageContext,
     *      org.displaytag.properties.MediaTypeEnum)
     */
    @Override
    public Object decorate(final Object columnValue, final PageContext pageContext, final MediaTypeEnum media)
            throws DecoratorException {
        return this.dateFormat.format(columnValue);
    }

}
