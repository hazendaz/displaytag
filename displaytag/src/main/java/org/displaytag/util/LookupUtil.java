package org.displaytag.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.exception.ObjectLookupException;

/**
 * <p>Utility class with methods for object & properties retrieving</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public final class LookupUtil
{

    /**
     * logger
     */
    private static Log mLog = LogFactory.getLog(LookupUtil.class);

    /**
     * don't instantiate a LookupUtil
     */
    private LookupUtil()
    {
    }

    /**
     * Read an object from the pagecontext with the specified scope and eventually lookup a property in it
     * @param pPageContext PageContext
     * @param pBeanAndPropertyName String expression with bean name and attributes
     * @param pScope One of the following values:
     * <ul>
     *   <li>PageContext.PAGE_SCOPE</li>
     *   <li>PageContext.REQUEST_SCOPE</li>
     *   <li>PageContext.SESSION_SCOPE</li>
     *   <li>PageContext.APPLICATION_SCOPE</li>
     * </ul>
     * @return Object
     * @throws ObjectLookupException for errors while retrieving a property in the bean
     */
    public static Object getBeanValue(PageContext pPageContext, String pBeanAndPropertyName, int pScope)
        throws ObjectLookupException
    {

        if (pBeanAndPropertyName.indexOf(".") != -1)
        {
            // complex: property from a bean

            String lObjectName = pBeanAndPropertyName.substring(0, pBeanAndPropertyName.indexOf("."));
            String lBeanProperty = pBeanAndPropertyName.substring(pBeanAndPropertyName.indexOf(".") + 1);
            Object lBeanObject;

            if (mLog.isDebugEnabled())
            {
                mLog.debug("getBeanValue - bean: {" + lObjectName + "}, property: {" + lBeanProperty + "}");
            }

            // get the bean
            lBeanObject = pPageContext.getAttribute(lObjectName, pScope);

            // if null return
            if (lBeanObject == null)
            {
                return null;
            }

            // go get the property
            return getBeanProperty(lBeanObject, lBeanProperty);

        }
        else
        {
            // simple, only the javabean

            if (mLog.isDebugEnabled())
            {
                mLog.debug("getBeanValue - bean: {" + pBeanAndPropertyName + "}");
            }

            return pPageContext.getAttribute(pBeanAndPropertyName, pScope);
        }
    }

    /**
     * <p>Returns the value of a property in the given bean</p>
     * <p>This method is a modificated version from commons-beanutils PropertyUtils.getProperty(). It allows
     * intermediate nulls in expression without throwing exception (es. it doesn't throw an exception for the property
     * <code>object.date.time</code> if <code>date</code> is null)</p>
     * @param pBean javabean
     * @param pName name of the property to read from the javabean
     * @return Object
     * @throws ObjectLookupException for errors while retrieving a property in the bean
     */
    public static Object getBeanProperty(Object pBean, String pName) throws ObjectLookupException
    {

        if (mLog.isDebugEnabled())
        {
            mLog.debug("getProperty [" + pName + "] on bean " + pBean);
        }

        try
        {
            if (pBean == null)
            {
                throw new IllegalArgumentException("No bean specified");
            }
            if (pName == null)
            {
                throw new IllegalArgumentException("No name specified");
            }

            int lIndexOfINDEXEDDELIM = -1;
            int lIndexOfMAPPEDDELIM = -1;
            int lIndexOfMAPPEDDELIM2 = -1;
            int lIndexOfNESTEDDELIM = -1;
            while (true)
            {

                lIndexOfNESTEDDELIM = pName.indexOf(PropertyUtils.NESTED_DELIM);
                lIndexOfMAPPEDDELIM = pName.indexOf(PropertyUtils.MAPPED_DELIM);
                lIndexOfMAPPEDDELIM2 = pName.indexOf(PropertyUtils.MAPPED_DELIM2);
                if (lIndexOfMAPPEDDELIM2 >= 0
                    && lIndexOfMAPPEDDELIM >= 0
                    && (lIndexOfNESTEDDELIM < 0 || lIndexOfNESTEDDELIM > lIndexOfMAPPEDDELIM))
                {
                    lIndexOfNESTEDDELIM = pName.indexOf(PropertyUtils.NESTED_DELIM, lIndexOfMAPPEDDELIM2);
                }
                else
                {
                    lIndexOfNESTEDDELIM = pName.indexOf(PropertyUtils.NESTED_DELIM);
                }
                if (lIndexOfNESTEDDELIM < 0)
                {
                    break;
                }
                String lNext = pName.substring(0, lIndexOfNESTEDDELIM);
                lIndexOfINDEXEDDELIM = lNext.indexOf(PropertyUtils.INDEXED_DELIM);
                lIndexOfMAPPEDDELIM = lNext.indexOf(PropertyUtils.MAPPED_DELIM);
                if (pBean instanceof Map)
                {
                    pBean = ((Map) pBean).get(lNext);
                }
                else if (lIndexOfMAPPEDDELIM >= 0)
                {

                    pBean = PropertyUtils.getMappedProperty(pBean, lNext);

                }
                else if (lIndexOfINDEXEDDELIM >= 0)
                {
                    pBean = PropertyUtils.getIndexedProperty(pBean, lNext);
                }
                else
                {
                    pBean = PropertyUtils.getSimpleProperty(pBean, lNext);
                }

                if (pBean == null)
                {
                    mLog.debug("Null property value for '" + pName.substring(0, lIndexOfNESTEDDELIM) + "'");
                    return null;
                }
                pName = pName.substring(lIndexOfNESTEDDELIM + 1);

            }

            lIndexOfINDEXEDDELIM = pName.indexOf(PropertyUtils.INDEXED_DELIM);
            lIndexOfMAPPEDDELIM = pName.indexOf(PropertyUtils.MAPPED_DELIM);

            if (pBean == null)
            {
                mLog.debug("Null property value for '" + pName.substring(0, lIndexOfNESTEDDELIM) + "'");
                return null;
            }
            else if (pBean instanceof Map)
            {
                pBean = ((Map) pBean).get(pName);
            }
            else if (lIndexOfMAPPEDDELIM >= 0)
            {
                pBean = PropertyUtils.getMappedProperty(pBean, pName);
            }
            else if (lIndexOfINDEXEDDELIM >= 0)
            {
                pBean = PropertyUtils.getIndexedProperty(pBean, pName);
            }
            else
            {
                pBean = PropertyUtils.getSimpleProperty(pBean, pName);
            }
        }
        catch (IllegalAccessException e)
        {
            throw new ObjectLookupException(LookupUtil.class, pBean, pName, e);
        }

        catch (InvocationTargetException e)
        {
            throw new ObjectLookupException(LookupUtil.class, pBean, pName, e);
        }
        catch (NoSuchMethodException e)
        {
            throw new ObjectLookupException(LookupUtil.class, pBean, pName, e);
        }

        return pBean;

    }

}