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

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.BooleanUtils;


/**
 * <p>
 * This class provides some basic functionality for all objects which serve as decorators for the objects in the List
 * being displayed.
 * <p>
 * <p>
 * Decorator should never be subclassed directly. Use TableDecorator instead
 * </p>
 * @author mraible
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
abstract class Decorator
{

    /**
     * Char used to separate class name and property in the cache key.
     */
    private static final char CLASS_PROPERTY_SEPARATOR = '#';

    /**
     * property info cache contains classname#propertyname Strings as keys and Booleans as values.
     */
    private static Map propertyMap = new HashMap();

    /**
     * page context.
     */
    private PageContext pageContext;

    /**
     * decorated object. Usually a List
     */
    private Object decoratedObject;

    /**
     * Initialize the TableTecorator instance.
     * @param context PageContext
     * @param decorated decorated object (usually a list)
     */
    public void init(PageContext context, Object decorated)
    {
        this.pageContext = context;
        this.decoratedObject = decorated;
    }

    /**
     * returns the page context.
     * @return PageContext
     */
    public PageContext getPageContext()
    {
        return this.pageContext;
    }

    /**
     * returns the decorated object.
     * @return Object
     */
    public Object getDecoratedObject()
    {
        return this.decoratedObject;
    }

    /**
     * Called at the end of evaluation to clean up instance variable. A subclass of Decorator can override this method
     * but should always call super.finish() before return
     */
    public void finish()
    {
        this.pageContext = null;
        this.decoratedObject = null;
    }

    /**
     * Check if a getter exists for a given property. Uses cached info if property has already been requested. This
     * method only check for a simple property, if pPropertyName contains multiple tokens only the first part is
     * evaluated
     * @param propertyName name of the property to check
     * @return boolean true if the decorator has a getter for the given property
     */
    public boolean hasGetterFor(String propertyName)
    {
        String simpleProperty = propertyName;

        // get the simple (not nested) bean property
        int indexOfDot = simpleProperty.indexOf('.');
        if (indexOfDot > 0)
        {
            simpleProperty = simpleProperty.substring(0, indexOfDot);
        }

        Boolean cachedResult = (Boolean) propertyMap.get(getClass().getName()
            + CLASS_PROPERTY_SEPARATOR
            + simpleProperty);

        if (cachedResult != null)
        {
            return cachedResult.booleanValue();
        }

        // not already cached... check
        boolean hasGetter = searchGetterFor(propertyName);

        // save in cache
        propertyMap.put(getClass().getName() + CLASS_PROPERTY_SEPARATOR + simpleProperty, BooleanUtils
            .toBooleanObject(hasGetter));

        // and return
        return hasGetter;

    }

    /**
     * Looks for a getter for the given property using introspection.
     * @param propertyName name of the property to check
     * @return boolean true if the decorator has a getter for the given property
     */
    public boolean searchGetterFor(String propertyName)
    {

        Class type = null;

        try
        {
            // using getPropertyType instead of isReadable since isReadable doesn't support mapped properties.
            // Note that this method usually returns null if a property is not found and doesn't throw any exception
            // also for non existent properties
            type = PropertyUtils.getPropertyType(this, propertyName);
        }
        catch (IllegalAccessException e)
        {
            // ignore
        }
        catch (InvocationTargetException e)
        {
            // ignore
        }
        catch (NoSuchMethodException e)
        {
            // ignore
        }

        return type != null;

    }

}