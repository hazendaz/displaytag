/*
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
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

import org.displaytag.exception.DecoratorInstantiationException;
import org.displaytag.render.TableTotaler;


/**
 * Factory for TableDecorator or ColumnDecorator object.
 * @author Fabrizio Giustina
 * @version $Id$
 */
public interface DecoratorFactory
{

    /**
     * <p>
     * Given a table decorator name, returns a <code>org.displaytag.decorator.TableDecorator</code> instance. The
     * method used to lookup decorator (direct instantiation, load from a pre-istantiated list or from the page context)
     * may vary between different implementations.
     * </p>
     *
     * @param pageContext the page context
     * @param decoratorName String full decorator class name
     * @return instance of TableDecorator
     * @throws DecoratorInstantiationException if unable to load specified TableDecorator
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
     * @param pageContext the page context
     * @param decoratorName String full decorator class name
     * @return instance of DisplaytagColumnDecorator
     * @throws DecoratorInstantiationException if unable to load ColumnDecorator
     */
    DisplaytagColumnDecorator loadColumnDecorator(PageContext pageContext, String decoratorName)
        throws DecoratorInstantiationException;

    /**
     * Load table totaler.
     *
     * @param pageContext ctxt
     * @param decoratorName  full class name
     * @return the table totaler
     * @throws DecoratorInstantiationException the decorator instantiation exception
     */
     TableTotaler loadTableTotaler(PageContext pageContext, String decoratorName)
        throws DecoratorInstantiationException;

}