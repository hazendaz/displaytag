/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.decorator;

import jakarta.servlet.jsp.PageContext;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

/**
 * A column decorator which returns 100/value with padding of spaces.
 */
public class PercentualColumnDecorator implements DisplaytagColumnDecorator {

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

        int intValue = ((Number) columnValue).intValue();
        if (intValue == 0) {
            intValue = 1;
        }
        return StringUtils.leftPad(Integer.toString(100 / intValue), 3);
    }

}
