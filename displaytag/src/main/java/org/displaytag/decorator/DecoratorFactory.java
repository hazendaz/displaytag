/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.decorator;

import jakarta.servlet.jsp.PageContext;

import org.displaytag.exception.DecoratorInstantiationException;
import org.displaytag.render.TableTotaler;

/**
 * Factory for TableDecorator or ColumnDecorator object.
 */
public interface DecoratorFactory {

    /**
     * <p>
     * Given a table decorator name, returns a <code>org.displaytag.decorator.TableDecorator</code> instance. The method
     * used to lookup decorator (direct instantiation, load from a pre-istantiated list or from the page context) may
     * vary between different implementations.
     * </p>
     *
     * @param pageContext
     *            the page context
     * @param decoratorName
     *            String full decorator class name
     *
     * @return instance of TableDecorator
     *
     * @throws DecoratorInstantiationException
     *             if unable to load specified TableDecorator
     */
    TableDecorator loadTableDecorator(PageContext pageContext, String decoratorName)
            throws DecoratorInstantiationException;

    /**
     * <p>
     * Given a column decorator name, returns a <code>org.displaytag.decorator.DisplaytagColumnDecorator</code>
     * instance. The method used to lookup decorator (direct instantiation, load from a pre-istantiated list or from the
     * page context) may vary between different implementations.
     * </p>
     *
     * @param pageContext
     *            the page context
     * @param decoratorName
     *            String full decorator class name
     *
     * @return instance of DisplaytagColumnDecorator
     *
     * @throws DecoratorInstantiationException
     *             if unable to load ColumnDecorator
     */
    DisplaytagColumnDecorator loadColumnDecorator(PageContext pageContext, String decoratorName)
            throws DecoratorInstantiationException;

    /**
     * Load table totaler.
     *
     * @param pageContext
     *            ctxt
     * @param decoratorName
     *            full class name
     *
     * @return the table totaler
     *
     * @throws DecoratorInstantiationException
     *             the decorator instantiation exception
     */
    TableTotaler loadTableTotaler(PageContext pageContext, String decoratorName) throws DecoratorInstantiationException;

}
