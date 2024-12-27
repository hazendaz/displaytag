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

import javax.servlet.jsp.PageContext;

import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

/**
 * Interface for simple column decorators.
 * <p>
 * A column decorator is called after the object has been retrieved and it can "transform" the object before the
 * rendering.
 * <p>
 * The <code>DisplaytagColumnDecorator</code> interface has been introduced in displaytag 1.1 and replaces the previous
 * <code>ColumnDecorator</code> interface, adding the pageContext and media parameters, and changing the return type to
 * object to allow decorator chaining.
 *
 * @since 1.1
 */
public interface DisplaytagColumnDecorator {

    /**
     * Called after the object has been retrieved from the bean contained in the list. The decorate method is
     * responsible for transforming the object into a string to render in the page.
     *
     * @param columnValue
     *            Object to decorate
     * @param pageContext
     *            jsp page context
     * @param media
     *            current media (html, pdf, excel...)
     *
     * @return Object decorated object
     *
     * @throws DecoratorException
     *             wrapper exception for any exception thrown during decoration
     */
    Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException;

}
