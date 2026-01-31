/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.decorator;

import javax.servlet.jsp.PageContext;

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
