package org.displaytag.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Utility class which hides getter javabeans methods and return only setters.</p>
 * <p>Needed by Tag classes which have to declare setter attribute and have getters
 * with different return type. The javabean introspectory machine can't see these
 * methods (it looks for setters with a parameter of the same type returned by the getter).
 * Useful to let Tags use the "class" attribute, normally hidden by the getClass() method
 * in java.lang.Object.</p>
 * <p>Tag who wish to use this BeanInfo need to define a new class with the same name of the main
 * tag class + "BeanInfo" suffix wich extends BeanInfoUtil</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class BeanInfoUtil extends SimpleBeanInfo
{

    /**
     * logger
     */
    private static Log mLog = LogFactory.getLog(BeanInfoUtil.class);

    /**
     * create and returns an array of PropertyDescriptor for all the setXXX methods. Hide all the read-only properties
     * @return PropertyDescriptor[] with all the <strong>writable</strong> properties
     * @see java.beans.BeanInfo#getPropertyDescriptors()
     */
    public final PropertyDescriptor[] getPropertyDescriptors()
    {

        ArrayList lPdArray = new ArrayList();

        // get the full class name
        String lClassName = getClass().getName();

        // remove "BeanInfo" to get the bean class
        lClassName = lClassName.substring(0, lClassName.indexOf("BeanInfo"));

        Class lTagClass = null;

        try
        {
            // get the tag class
            lTagClass = Class.forName(lClassName);
        }
        catch (ClassNotFoundException ex1)
        {
            mLog.error("class not found: " + lClassName);
        }

        // get the method array
        Method[] lMethods = lTagClass.getMethods();

        String lMethodName;
        int lNumberOfMethods = lMethods.length;

        for (int lCounter = 0; lCounter < lNumberOfMethods; lCounter++)
        {
            Method lMeth = lMethods[lCounter];

            // look for setters only
            if ((lMeth.getParameterTypes().length == 1)
                && (lMethodName = lMeth.getName()).indexOf("set") == 0
                && (lMethodName.length() > 3)
                && Character.isUpperCase(lMethodName.charAt(3)))
            {

                String lAttributeName = Character.toLowerCase(lMethodName.charAt(3)) + lMethodName.substring(4);

                try
                {
                    // add setters only, hide getters
                    lPdArray.add(new PropertyDescriptor(lAttributeName, null, lMeth));
                }
                catch (IntrospectionException ex)
                {
                    // ignore
                    continue;
                }

            }
        }

        PropertyDescriptor[] lPd = new PropertyDescriptor[lPdArray.size()];

        Iterator lIterator = lPdArray.iterator();

        int lPid = 0;

        while (lIterator.hasNext())
        {
            lPd[lPid] = (PropertyDescriptor) (lIterator.next());
            lPid++;
        }

        return lPd;

    }
}
