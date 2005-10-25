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

import org.displaytag.exception.DecoratorException;
import org.displaytag.exception.DecoratorInstantiationException;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.util.ReflectHelper;


/**
 * Factory for TableDecorator or ColumnDecorator object.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public final class DecoratorFactory
{

    /**
     * Constructor for DecoratorFactory.
     */
    private DecoratorFactory()
    {
        // unused
    }

    /**
     * If the user has specified a decorator, then this method takes care of creating the decorator (and checking to
     * make sure it is a subclass of the TableDecorator object). If there are any problems loading the decorator then
     * this will throw a DecoratorInstantiationException which will get propagated up to the page.
     * @param decoratorName String full decorator class name
     * @return instance of TableDecorator
     * @throws DecoratorInstantiationException if unable to load specified TableDecorator
     */
    public static TableDecorator loadTableDecorator(String decoratorName) throws DecoratorInstantiationException
    {
        if (decoratorName == null || decoratorName.length() == 0)
        {
            return null;
        }

        try
        {
            Class decoratorClass = ReflectHelper.classForName(decoratorName);
            return (TableDecorator) decoratorClass.newInstance();
        }
        catch (ClassNotFoundException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, decoratorName, e);
        }
        catch (InstantiationException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, decoratorName, e);
        }
        catch (IllegalAccessException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, decoratorName, e);
        }
        catch (ClassCastException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, decoratorName, e);
        }
    }

    /**
     * If the user has specified a column decorator, then this method takes care of creating the decorator (and checking
     * to make sure it is a subclass of the ColumnDecorator object). If there are any problems loading the decorator
     * then this will throw a DecoratorInstantiationException which will get propagated up to the page.
     * @param columnDecoratorName String full decorator class name
     * @return instance of DisplaytagColumnDecorator
     * @throws DecoratorInstantiationException if unable to load ColumnDecorator
     */
    public static DisplaytagColumnDecorator loadColumnDecorator(String columnDecoratorName)
        throws DecoratorInstantiationException
    {
        if (columnDecoratorName == null || columnDecoratorName.length() == 0)
        {
            return null;
        }

        try
        {
            Class decoratorClass = ReflectHelper.classForName(columnDecoratorName);

            Object decorator = decoratorClass.newInstance();

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
                    DecoratorFactory.class,
                    columnDecoratorName,
                    new ClassCastException(decorator.getClass().getName()));
            }
        }

        catch (ClassNotFoundException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, columnDecoratorName, e);
        }
        catch (InstantiationException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, columnDecoratorName, e);
        }
        catch (IllegalAccessException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, columnDecoratorName, e);
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
         * @see org.displaytag.decorator.DisplaytagColumnDecorator#decorate(java.lang.Object,
         * javax.servlet.jsp.PageContext, org.displaytag.properties.MediaTypeEnum)
         */
        public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media)
            throws DecoratorException
        {
            return decorator.decorate(columnValue);
        }

    }
}