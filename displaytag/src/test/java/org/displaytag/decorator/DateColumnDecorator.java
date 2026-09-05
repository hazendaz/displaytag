/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.decorator;

import jakarta.servlet.jsp.PageContext;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

/**
 * A test column decorator for dates.
 */
public class DateColumnDecorator implements DisplaytagColumnDecorator {

    /**
     * date formatter.
     */
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH);

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
     * @see org.displaytag.decorator.DisplaytagColumnDecorator#decorate(java.lang.Object,
     *      jakarta.servlet.jsp.PageContext, org.displaytag.properties.MediaTypeEnum)
     */
    @Override
    public Object decorate(final Object columnValue, final PageContext pageContext, final MediaTypeEnum media)
            throws DecoratorException {
        if (columnValue == null) {
            return null;
        }

        if (columnValue instanceof LocalDate) {
            return dateFormatter.format((LocalDate) columnValue);
        } else if (columnValue instanceof Date) {
            LocalDate date = ((Date) columnValue).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return dateFormatter.format(date);
        } else if (columnValue instanceof Calendar) {
            LocalDate date = ((Calendar) columnValue).toInstant()
                    .atZone(((Calendar) columnValue).getTimeZone().toZoneId()).toLocalDate();
            return dateFormatter.format(date);
        }

        return this.dateFormatter.format(TemporalAccessor.class.cast(columnValue));
    }

}
