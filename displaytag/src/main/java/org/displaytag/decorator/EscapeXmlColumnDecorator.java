/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.decorator;

import jakarta.servlet.jsp.PageContext;

import org.apache.commons.text.StringEscapeUtils;
import org.displaytag.properties.MediaTypeEnum;

/**
 * This takes the string that is passed in, and escapes html tags and entities. Only operates on "html" or "xml" media.
 */
public class EscapeXmlColumnDecorator implements DisplaytagColumnDecorator {

    /**
     * Instance used for the "escapeXml" tag attribute.
     */
    public static final DisplaytagColumnDecorator INSTANCE = new EscapeXmlColumnDecorator();

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

        if (columnValue == null || !media.equals(MediaTypeEnum.HTML) && !media.equals(MediaTypeEnum.XML)) {
            return columnValue;
        }

        return StringEscapeUtils.escapeXml10(columnValue.toString());
    }

}
