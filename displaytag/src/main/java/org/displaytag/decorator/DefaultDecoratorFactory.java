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

import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.exception.DecoratorInstantiationException;
import org.displaytag.render.TableTotaler;
import org.displaytag.util.ReflectHelper;

/**
 * Factory for TableDecorator or ColumnDecorator object.
 *
 * @author Fabrizio Giustina
 *
 * @version $Id$
 */
public class DefaultDecoratorFactory implements DecoratorFactory {

    /**
     * <p>
     * If the user has specified a decorator, then this method takes care of creating the decorator (and checking to
     * make sure it is a subclass of the TableDecorator object). If there are any problems loading the decorator then
     * this will throw a DecoratorInstantiationException which will get propagated up to the page.
     * </p>
     * <p>
     * Two different methods for loading a decorator are handled by this factory:
     * </p>
     * <ul>
     * <li>First of all, an object with key <code>decoratorName</code> is searched in the
     * page/request/session/scope</li>
     * <li>If not found, assume <code>decoratorName</code> is the class name of the decorator and load it using
     * reflection</li>
     * </ul>
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
    @Override
    public TableDecorator loadTableDecorator(final PageContext pageContext, final String decoratorName)
            throws DecoratorInstantiationException {
        return (TableDecorator) this.lookup(pageContext, decoratorName);
    }

    /**
     * Lookup.
     *
     * @param pageContext
     *            the page context
     * @param decoratorName
     *            the decorator name
     *
     * @return the object
     *
     * @throws DecoratorInstantiationException
     *             the decorator instantiation exception
     */
    private Object lookup(final PageContext pageContext, final String decoratorName)
            throws DecoratorInstantiationException {
        if (StringUtils.isBlank(decoratorName)) {
            return null;
        }

        // first check: is decoratorName an object in page/request/session/application scope?
        Object decorator = pageContext.findAttribute(decoratorName);

        // second check: if a decorator was not found assume decoratorName is the class name and load it using
        // reflection
        if (decorator == null) {
            try {
                decorator = ReflectHelper.classForName(decoratorName).getDeclaredConstructor().newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                throw new DecoratorInstantiationException(DefaultDecoratorFactory.class, decoratorName, e);
            }
        }
        return decorator;
    }

    /**
     * <p>
     * If the user has specified a table totaler, then this method takes care of creating the totaler. If there are any
     * problems loading the decorator then this will throw a DecoratorInstantiationException which will get propagated
     * up to the page.
     * </p>
     * <p>
     * Two different methods for loading a decorator are handled by this factory:
     * </p>
     * <ul>
     * <li>First of all, an object with key <code>decoratorName</code> is searched in the
     * page/request/session/scope</li>
     * <li>If not found, assume <code>decoratorName</code> is the class name of the decorator and load it using
     * reflection</li>
     * </ul>
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
    @Override
    public TableTotaler loadTableTotaler(final PageContext pageContext, final String decoratorName)
            throws DecoratorInstantiationException {
        return (TableTotaler) this.lookup(pageContext, decoratorName);
    }

    /**
     * <p>
     * If the user has specified a column decorator, then this method takes care of creating the decorator (and checking
     * to make sure it is a subclass of the DisplaytagColumnDecorator object). If there are any problems loading the
     * decorator then this will throw a DecoratorInstantiationException which will get propagated up to the page.
     * </p>
     * <p>
     * Two different methods for loading a decorator are handled by this factory:
     * </p>
     * <ul>
     * <li>First of all, an object with key <code>decoratorName</code> is searched in the
     * page/request/session/scope</li>
     * <li>If not found, assume <code>decoratorName</code> is the class name of the decorator and load it using
     * reflection</li>
     * </ul>
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
    @Override
    public DisplaytagColumnDecorator loadColumnDecorator(final PageContext pageContext, final String decoratorName)
            throws DecoratorInstantiationException {
        if (StringUtils.isBlank(decoratorName)) {
            return null;
        }

        // first check: is decoratorName an object in page/request/session/application scope?
        Object decorator = pageContext.findAttribute(decoratorName);

        // second check: if a decorator was not found assume decoratorName is the class name and load it using
        // reflection
        if (decorator == null) {
            try {
                decorator = ReflectHelper.classForName(decoratorName).getDeclaredConstructor().newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                throw new DecoratorInstantiationException(DefaultDecoratorFactory.class, decoratorName, e);
            }
        }

        if (decorator instanceof DisplaytagColumnDecorator) {
            return (DisplaytagColumnDecorator) decorator;
        }
        throw new DecoratorInstantiationException(DefaultDecoratorFactory.class, decoratorName,
                new ClassCastException(decorator.getClass().getName()));
    }

}
