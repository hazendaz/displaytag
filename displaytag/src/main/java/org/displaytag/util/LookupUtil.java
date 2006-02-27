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
import java.util.Map;

import javax.servlet.jsp.PageContext;

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

        if (beanAndPropertyName.indexOf(".") != -1)
        {
            // complex: property from a bean

            String objectName = beanAndPropertyName.substring(0, beanAndPropertyName.indexOf("."));
            String beanProperty = beanAndPropertyName.substring(beanAndPropertyName.indexOf(".") + 1);
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
     * This method is a modificated version from commons-beanutils PropertyUtils.getProperty(). It allows intermediate
     * nulls in expression without throwing exception (es. it doesn't throw an exception for the property
     * <code>object.date.time</code> if <code>date</code> is null)
     * </p>
     * @param bean javabean
     * @param name name of the property to read from the javabean
     * @return Object
     * @throws ObjectLookupException for errors while retrieving a property in the bean
     */
    public static Object getBeanProperty(Object bean, String name) throws ObjectLookupException
    {

        if (log.isDebugEnabled())
        {
            log.debug("getProperty [" + name + "] on bean " + bean);
        }

        if (bean == null)
        {
            throw new IllegalArgumentException("No bean specified");
        }
        if (name == null)
        {
            throw new IllegalArgumentException("No name specified");
        }

        Object evalBean = bean;
        String evalName = name;

        try
        {

            int indexOfINDEXEDDELIM;
            int indexOfMAPPEDDELIM;
            int indexOfMAPPEDDELIM2;
            int indexOfNESTEDDELIM;
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
                    evalBean = PropertyUtils.getIndexedProperty(evalBean, next);
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

            if (evalBean instanceof Map)
            {
                evalBean = ((Map) evalBean).get(evalName);
            }
            else if (indexOfMAPPEDDELIM >= 0)
            {
                evalBean = PropertyUtils.getMappedProperty(evalBean, evalName);
            }
            else if (indexOfINDEXEDDELIM >= 0)
            {
                evalBean = PropertyUtils.getIndexedProperty(evalBean, evalName);
            }
            else
            {
                evalBean = PropertyUtils.getSimpleProperty(evalBean, evalName);
            }
        }
        catch (IllegalAccessException e)
        {
            throw new ObjectLookupException(LookupUtil.class, evalBean, evalName, e);
        }

        catch (InvocationTargetException e)
        {
            throw new ObjectLookupException(LookupUtil.class, evalBean, evalName, e);
        }
        catch (NoSuchMethodException e)
        {
            throw new ObjectLookupException(LookupUtil.class, evalBean, evalName, e);
        }

        return evalBean;

    }

}