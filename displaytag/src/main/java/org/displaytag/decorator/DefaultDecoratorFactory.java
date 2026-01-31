/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.decorator;

import jakarta.servlet.jsp.PageContext;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.exception.DecoratorInstantiationException;
import org.displaytag.render.TableTotaler;
import org.displaytag.util.ReflectHelper;

/**
 * Factory for TableDecorator or ColumnDecorator object.
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
