/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
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

import org.displaytag.exception.DecoratorInstantiationException;
import org.displaytag.render.TableTotaler;

/**
 * A factory for creating NoDecorationDecorator objects.
 */
public class NoDecorationDecoratorFactory implements DecoratorFactory {

    /**
     * Load column decorator.
     *
     * @param pageContext
     *            the page context
     * @param decoratorName
     *            the decorator name
     *
     * @return the displaytag column decorator
     *
     * @throws DecoratorInstantiationException
     *             the decorator instantiation exception
     */
    @Override
    public DisplaytagColumnDecorator loadColumnDecorator(final PageContext pageContext, final String decoratorName)
            throws DecoratorInstantiationException {
        return new NoDecorationColumnDecorator();
    }

    /**
     * Load table decorator.
     *
     * @param pageContext
     *            the page context
     * @param decoratorName
     *            the decorator name
     *
     * @return the table decorator
     *
     * @throws DecoratorInstantiationException
     *             the decorator instantiation exception
     */
    @Override
    public TableDecorator loadTableDecorator(final PageContext pageContext, final String decoratorName)
            throws DecoratorInstantiationException {
        return new NoDecorationDecorator();
    }

    /**
     * Load table totaler.
     *
     * @param pageContext
     *            the page context
     * @param decoratorName
     *            the decorator name
     *
     * @return the table totaler
     *
     * @throws DecoratorInstantiationException
     *             the decorator instantiation exception
     */
    @Override
    public TableTotaler loadTableTotaler(final PageContext pageContext, final String decoratorName)
            throws DecoratorInstantiationException {
        return null;
    }
}
