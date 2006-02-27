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
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.exception.ObjectLookupException;


/**
 * Utility class with methods for object and properties retrieving.
 * @author Fabrizio Giustina
 * @version $Id$
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
            String objectName = StringUtils.substringBefore(beanAndPropertyName, ".");
            String beanProperty = StringUtils.substringAfter(beanAndPropertyName, ".");
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

        Validate.notNull(bean, "No bean specified");
        Validate.notNull(name, "No name specified");

        if (log.isDebugEnabled())
        {
            log.debug("getProperty [" + name + "] on bean " + bean);
        }

        try
        {
            return getProperty(bean, name);
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

    /**
     * Return the value of the (possibly nested) property of the specified name, for the specified bean, with no type
     * conversions.
     * @param bean Bean whose property is to be extracted
     * @param name Possibly nested name of the property to be extracted
     * @return Object
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws BeanPropertyLookupException in caso di errori nella lettura di proprietà del bean
     */
    public static Object getProperty(Object bean, String name) throws IllegalAccessException,
        InvocationTargetException, NoSuchMethodException
    {
        if (log.isDebugEnabled())
        {
            log.debug("getProperty [" + name + "] on bean " + bean);
        }

        Validate.notNull(bean, "No bean specified");
        Validate.notNull(name, "No name specified");

        Object evalBean = bean;
        String evalName = name;

        if (evalBean == null)
        {
            throw new IllegalArgumentException("No bean specified");
        }
        if (evalName == null)
        {
            throw new IllegalArgumentException("No name specified");
        }

        int indexOfINDEXEDDELIM = -1;
        int indexOfMAPPEDDELIM = -1;
        int indexOfMAPPEDDELIM2 = -1;
        int indexOfNESTEDDELIM = -1;
        while (true)
        {

            indexOfNESTEDDELIM = evalName.indexOf(PropertyUtils.NESTED_DELIM);
            indexOfMAPPEDDELIM = evalName.indexOf(PropertyUtils.MAPPED_DELIM);
            indexOfMAPPEDDELIM2 = evalName.indexOf(PropertyUtils.MAPPED_DELIM2);
            if (indexOfMAPPEDDELIM2 >= 0
                && indexOfMAPPEDDELIM >= 0
                && (indexOfNESTEDDELIM < 0 || indexOfNESTEDDELIM > indexOfMAPPEDDELIM))
            {
                indexOfNESTEDDELIM = evalName.indexOf(PropertyUtils.NESTED_DELIM, indexOfMAPPEDDELIM2);
            }
            else
            {
                indexOfNESTEDDELIM = evalName.indexOf(PropertyUtils.NESTED_DELIM);
            }
            if (indexOfNESTEDDELIM < 0)
            {
                break;
            }
            String next = evalName.substring(0, indexOfNESTEDDELIM);
            indexOfINDEXEDDELIM = next.indexOf(PropertyUtils.INDEXED_DELIM);
            indexOfMAPPEDDELIM = next.indexOf(PropertyUtils.MAPPED_DELIM);
            if (evalBean instanceof Map)
            {
                evalBean = ((Map) evalBean).get(next);
            }
            else if (indexOfMAPPEDDELIM >= 0)
            {

                evalBean = PropertyUtils.getMappedProperty(evalBean, next);

            }
            else if (indexOfINDEXEDDELIM >= 0)
            {
                evalBean = getIndexedProperty(evalBean, next);
            }
            else
            {
                evalBean = PropertyUtils.getSimpleProperty(evalBean, next);
            }

            if (evalBean == null)
            {
                log.debug("Null property value for '" + evalName.substring(0, indexOfNESTEDDELIM) + "'");
                return null;
            }
            evalName = evalName.substring(indexOfNESTEDDELIM + 1);

        }

        indexOfINDEXEDDELIM = evalName.indexOf(PropertyUtils.INDEXED_DELIM);
        indexOfMAPPEDDELIM = evalName.indexOf(PropertyUtils.MAPPED_DELIM);

        if (evalBean == null)
        {
            log.debug("Null property value for '" + evalName.substring(0, indexOfNESTEDDELIM) + "'");
            return null;
        }
        else if (evalBean instanceof Map)
        {
            evalBean = ((Map) evalBean).get(evalName);
        }
        else if (indexOfMAPPEDDELIM >= 0)
        {
            evalBean = PropertyUtils.getMappedProperty(evalBean, evalName);
        }
        else if (indexOfINDEXEDDELIM >= 0)
        {
            evalBean = getIndexedProperty(evalBean, evalName);
        }
        else
        {
            evalBean = PropertyUtils.getSimpleProperty(evalBean, evalName);
        }

        return evalBean;

    }

    /**
     * Return the value of the specified indexed property of the specified bean, with no type conversions. The
     * zero-relative index of the required value must be included (in square brackets) as a suffix to the property name,
     * or <code>IllegalArgumentException</code> will be thrown. In addition to supporting the JavaBeans specification,
     * this method has been extended to support <code>List</code> objects as well.
     * @param bean Bean whose property is to be extracted
     * @param name <code>propertyname[index]</code> of the property value to be extracted
     * @return Object
     * @exception IllegalAccessException if the caller does not have access to the property accessor method
     * @exception InvocationTargetException if the property accessor method throws an exception
     * @exception NoSuchMethodException if an accessor method for this propety cannot be found
     */
    public static Object getIndexedProperty(Object bean, String name) throws IllegalAccessException,
        InvocationTargetException, NoSuchMethodException
    {

        Validate.notNull(bean, "No bean specified");
        Validate.notNull(name, "No name specified");

        String evalName = name;

        // Identify the index of the requested individual property
        int delim = evalName.indexOf(PropertyUtils.INDEXED_DELIM);
        int delim2 = evalName.indexOf(PropertyUtils.INDEXED_DELIM2);
        if ((delim < 0) || (delim2 <= delim))
        {
            throw new IllegalArgumentException("Invalid indexed property '" + evalName + "'");
        }
        int index = -1;
        try
        {
            String subscript = evalName.substring(delim + 1, delim2);
            index = Integer.parseInt(subscript);
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException("Invalid indexed property '" + evalName + "'");
        }
        evalName = evalName.substring(0, delim);

        if (log.isDebugEnabled())
        {
            log.debug("getIndexedProperty property name={" + evalName + "} with index " + index);

        }

        // addd support for lists and arrays
        if (StringUtils.isEmpty(evalName))
        {
            if (bean instanceof List)
            {
                return ((List) bean).get(index);
            }
            else if (bean.getClass().isArray())
            {
                return ((Object[]) bean)[index];
            }
        }
        // Request the specified indexed property value
        return (PropertyUtils.getIndexedProperty(bean, evalName, index));

    }

}