package org.displaytag.decorator;

import java.beans.PropertyDescriptor;
import java.util.HashMap;

import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * <p>This class provides some basic functionality for all objects which serve
 * as decorators for the objects in the List being displayed.<p>
 * <p>Decorator should never be subclassed directly. Use TableDecorator instead</p>
 * @author mraible
 * @version $Revision$ ($Author$)
 **/
public abstract class Decorator
{

    /**
     * page context
     */
    private PageContext pageContext;

    /**
     * property info cache
     * contains classname#propertyname Strings as keys and Booleans as values
     */
    private static HashMap propertyMap = new HashMap();

    /**
     * decorated object. Usually a List
     */
    private Object decoratedObject;

    /**
     * Initialize the TableTecorator instance
     * @param context PageContext
     * @param decorated decorated object (usually a list)
     */
    public void init(PageContext context, Object decorated)
    {
        pageContext = context;
        decoratedObject = decorated;
    }

    /**
     * returns the page context
     * @return PageContext
     */
    public PageContext getPageContext()
    {
        return pageContext;
    }

    /**
     * returns the decorated object
     * @return Object
     */
    public Object getDecoratedObject()
    {
        return decoratedObject;
    }

    /**
     * Method called at the end of evaluation
     */
    public void finish()
    {
        pageContext = null;
        decoratedObject = null;
    }

    /**
     * look for a getter for the given property using introspection
     * @param propertyName name of the property to check
     * @return boolean true if the decorator has a getter for the given property
     */
    public boolean searchGetterFor(String propertyName)
    {
        PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(getClass());

        // iterate on property descriptors
        for (int j = 0; j < descriptors.length; j++)
        {
            if (propertyName.equals(descriptors[j].getName()))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Check if a getter exists for a given property. Uses cached info if property has already been requested.
     * This method only check for a simple property, if pPropertyName contains multiple tokens only the first part is
     * evaluated
     * @param propertyName name of the property to check
     * @return boolean true if the decorator has a getter for the given property
     */
    public boolean hasGetterFor(String propertyName)
    {
        String simpleProperty = propertyName;

        // get the simple (not nested) bean property
        if ((simpleProperty != null) && (simpleProperty.indexOf(".") > 0))
        {
            simpleProperty = simpleProperty.substring(0, simpleProperty.indexOf("."));
        }

        Boolean cachedResult;

        if ((cachedResult = (Boolean) propertyMap.get(getClass().getName() + "#" + simpleProperty)) != null)
        {
            return cachedResult.booleanValue();
        }

        // not already cached... check
        boolean hasGetter = searchGetterFor(simpleProperty);

        // save in cache
        propertyMap.put(getClass().getName() + "#" + simpleProperty, new Boolean(hasGetter));

        // and return
        return hasGetter;

    }

}
