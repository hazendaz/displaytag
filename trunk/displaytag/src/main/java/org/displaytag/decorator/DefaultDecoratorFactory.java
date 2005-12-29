/**
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://displaytag.sourceforge.net/license.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.displaytag.decorator;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.displaytag.exception.DecoratorException;
import org.displaytag.exception.DecoratorInstantiationException;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.util.ReflectHelper;


/**
 * Factory for TableDecorator or ColumnDecorator object.
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class DefaultDecoratorFactory implements DecoratorFactory
{

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
     * <li>First of all, an object with key <code>decoratorName</code> is searched in the page/request/session/scope</li>
     * <li>If not found, assume <code>decoratorName</code> is the class name of the decorator and load it using
     * reflection</li>
     * </ul>
     * @param decoratorName String full decorator class name
     * @return instance of TableDecorator
     * @throws DecoratorInstantiationException if unable to load specified TableDecorator
     */
    public TableDecorator loadTableDecorator(PageContext pageContext, String decoratorName)
        throws DecoratorInstantiationException
    {
        if (StringUtils.isBlank(decoratorName))
        {
            return null;
        }

        // first check: is decoratorName an object in page/request/session/application scope?
        Object decorator = pageContext.findAttribute(decoratorName);

        // second check: if a decorator was not found assume decoratorName is the class name and load it using
        // reflection
        if (decorator == null)
        {
            try
            {
                decorator = ReflectHelper.classForName(decoratorName).newInstance();
            }
            catch (ClassNotFoundException e)
            {
                throw new DecoratorInstantiationException(DefaultDecoratorFactory.class, decoratorName, e);
            }
            catch (InstantiationException e)
            {
                throw new DecoratorInstantiationException(DefaultDecoratorFactory.class, decoratorName, e);
            }
            catch (IllegalAccessException e)
            {
                throw new DecoratorInstantiationException(DefaultDecoratorFactory.class, decoratorName, e);
            }
        }

        if (decorator instanceof TableDecorator)
        {
            return (TableDecorator) decorator;
        }
        else
        {
            throw new DecoratorInstantiationException(
                DefaultDecoratorFactory.class,
                decoratorName,
                new ClassCastException(decorator.getClass().getName()));
        }

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
     * <li>First of all, an object with key <code>decoratorName</code> is searched in the page/request/session/scope</li>
     * <li>If not found, assume <code>decoratorName</code> is the class name of the decorator and load it using
     * reflection</li>
     * </ul>
     * @param decoratorName String full decorator class name
     * @return instance of DisplaytagColumnDecorator
     * @throws DecoratorInstantiationException if unable to load ColumnDecorator
     */
    public DisplaytagColumnDecorator loadColumnDecorator(PageContext pageContext, String decoratorName)
        throws DecoratorInstantiationException
    {
        if (StringUtils.isBlank(decoratorName))
        {
            return null;
        }

        // first check: is decoratorName an object in page/request/session/application scope?
        Object decorator = pageContext.findAttribute(decoratorName);

        // second check: if a decorator was not found assume decoratorName is the class name and load it using
        // reflection
        if (decorator == null)
        {
            try
            {
                decorator = ReflectHelper.classForName(decoratorName).newInstance();
            }
            catch (ClassNotFoundException e)
            {
                throw new DecoratorInstantiationException(DefaultDecoratorFactory.class, decoratorName, e);
            }
            catch (InstantiationException e)
            {
                throw new DecoratorInstantiationException(DefaultDecoratorFactory.class, decoratorName, e);
            }
            catch (IllegalAccessException e)
            {
                throw new DecoratorInstantiationException(DefaultDecoratorFactory.class, decoratorName, e);
            }
        }

        if (decorator instanceof DisplaytagColumnDecorator)
        {
            return (DisplaytagColumnDecorator) decorator;
        }
        else if (decorator instanceof ColumnDecorator)
        {
            return new DeprecatedDecoratorWrapper((ColumnDecorator) decorator);
        }
        else
        {
            throw new DecoratorInstantiationException(
                DefaultDecoratorFactory.class,
                decoratorName,
                new ClassCastException(decorator.getClass().getName()));
        }
    }

    /**
     * Wrapper class for handling decorators implementing the deprecated ColumnDecorator interface as 1.1
     * <code>DisplaytagColumnDecorator</code>s.
     */
    private static class DeprecatedDecoratorWrapper implements DisplaytagColumnDecorator
    {

        /**
         * Wrapped 1.0 decorator.
         */
        private ColumnDecorator decorator;

        /**
         * Instantiates a new wrapper for old decorators.
         * @param decorator ColumnDecorator instance
         */
        public DeprecatedDecoratorWrapper(ColumnDecorator decorator)
        {
            this.decorator = decorator;
        }

        /**
         * @see org.displaytag.decorator.DisplaytagColumnDecorator#decorate(Object, PageContext, MediaTypeEnum)
         * @deprecated
         */
        public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media)
            throws DecoratorException
        {
            return decorator.decorate(columnValue);
        }

    }
}