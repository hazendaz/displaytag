package org.displaytag.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    private static Log log = LogFactory.getLog(BeanInfoUtil.class);

    /**
     * create and returns an array of PropertyDescriptor for all the setXXX methods. Hide all the read-only properties
     * @return PropertyDescriptor[] with all the <strong>writable</strong> properties
     * @see java.beans.BeanInfo#getPropertyDescriptors()
     */
    public final PropertyDescriptor[] getPropertyDescriptors()
    {

        List pdArray = new ArrayList();

        // get the full class name
        String className = getClass().getName();

        // remove "BeanInfo" to get the bean class
        className = className.substring(0, className.indexOf("BeanInfo"));

        Class tagClass = null;

        try
        {
            // get the tag class
            tagClass = Class.forName(className);
        }
        catch (ClassNotFoundException ex1)
        {
            log.error("class not found: " + className);
        }

        // get the method array
        Method[] methods = tagClass.getMethods();

        String methodName;
        int numberOfMethods = methods.length;

        for (int j = 0; j < numberOfMethods; j++)
        {
            Method meth = methods[j];

            // look for setters only
            if ((meth.getParameterTypes().length == 1)
                && (methodName = meth.getName()).indexOf("set") == 0
                && (methodName.length() > 3)
                && Character.isUpperCase(methodName.charAt(3)))
            {

                String attributeName = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);

                try
                {
                    // add setters only, hide getters
                    pdArray.add(new PropertyDescriptor(attributeName, null, meth));
                }
                catch (IntrospectionException ex)
                {
                    // ignore
                    continue;
                }

            }
        }

        PropertyDescriptor[] pd = new PropertyDescriptor[pdArray.size()];

        Iterator iterator = pdArray.iterator();

        int pdCount = 0;

        while (iterator.hasNext())
        {
            pd[pdCount] = (PropertyDescriptor) (iterator.next());
            pdCount++;
        }

        return pd;

    }
}
