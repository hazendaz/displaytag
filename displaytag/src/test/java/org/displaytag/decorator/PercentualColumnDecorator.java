/*
 * Copyright (C) 2002-2024 Fabrizio Giustina, the Displaytag team
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
