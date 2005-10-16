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
package org.displaytag.util;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.exception.ObjectLookupException;


/**
 * Utility class with methods for object and properties retrieving.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public final class LookupUtil
{

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(LookupUtil.class);

    /**
     * don't instantiate a LookupUtil.
     */
    private LookupUtil()
    {
        // unused
    }

    /**
     * Read an object from the pagecontext with the specified scope and eventually lookup a property in it.
     * @param pageContext PageContext
     * @param beanAndPropertyName String expression with bean name and attributes
     * @param scope One of the following values:
     * <ul>
     * <li>PageContext.PAGE_SCOPE</li>
     * <li>PageContext.REQUEST_SCOPE</li>
     * <li>PageContext.SESSION_SCOPE</li>
     * <li>PageContext.APPLICATION_SCOPE</li>
     * </ul>
     * @return Object
     * @throws ObjectLookupException for errors while retrieving a property in the bean
     */
    public static Object getBeanValue(PageContext pageContext, String beanAndPropertyName, int scope)
        throws ObjectLookupException
    {

        if (beanAndPropertyName.indexOf('.') != -1)
        {
            // complex: property from a bean
            String objectName = beanAndPropertyName.substring(0, beanAndPropertyName.indexOf('.'));
            String beanProperty = beanAndPropertyName.substring(beanAndPropertyName.indexOf('.') + 1);
            Object beanObject;

            if (log.isDebugEnabled())
            {
                log.debug("getBeanValue - bean: {" + objectName + "}, property: {" + beanProperty + "}");
            }

            // get the bean
            beanObject = pageContext.getAttribute(objectName, scope);

            // if null return
            if (beanObject == null)
            {
                return null;
            }

            // go get the property
            return getBeanProperty(beanObject, beanProperty);

        }

        // simple, only the javabean
        if (log.isDebugEnabled())
        {
            log.debug("getBeanValue - bean: {" + beanAndPropertyName + "}");
        }

        return pageContext.getAttribute(beanAndPropertyName, scope);
    }

    /**
     * <p>
     * Returns the value of a property in the given bean.
     * </p>
     * <p>
     * Handle <code>NestedNullException</code> returning nulls and other exceptions returning
     * <code>ObjectLookupException</code>.
     * </p>
     * @param bean javabean
     * @param name name of the property to read from the javabean
     * @return Object
     * @throws ObjectLookupException for errors while retrieving a property in the bean
     */
    public static Object getBeanProperty(Object bean, String name) throws ObjectLookupException
    {
        if (bean == null)
        {
            throw new IllegalArgumentException("Bean cannot be null");
        }
        if (name == null)
        {
            throw new IllegalArgumentException("Property name cannot be null");
        }

        if (log.isDebugEnabled())
        {
            log.debug("getProperty [" + name + "] on bean " + bean);
        }

        try
        {
            return PropertyUtils.getProperty(bean, name);
        }
        catch (IllegalAccessException e)
        {
            throw new ObjectLookupException(LookupUtil.class, bean, name, e);
        }
        catch (InvocationTargetException e)
        {
            throw new ObjectLookupException(LookupUtil.class, bean, name, e);
        }
        catch (NoSuchMethodException e)
        {
            throw new ObjectLookupException(LookupUtil.class, bean, name, e);
        }
        catch (NestedNullException nne)
        {
            // don't throw exceptions for nulls
            return null;
        }
        catch (IllegalArgumentException e)
        {
            // don't throw exceptions for nulls; the bean and name have already been checked; this is being thrown when
            // the bean property value is itself null.
            log
                .debug(
                    "Caught IllegalArgumentException from beanutils while looking up " + name + " in bean " + bean,
                    e);
            return null;
        }
    }

}