/*
 * $Id$
 *
 * Todo
 *   - implementation
 *   - documentation (javadoc, examples, etc...)
 *   - junit test cases
 */

package org.apache.taglibs.display;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * This utility class is used to sort the list that the table is viewing
 * based on arbitrary properties of objects contained in that list.
 *
 * <p>The only assumption made is that the object returned by the property is
 * either a native type, or implements the Comparable interface.</p>
 *
 * <p>If the property does not implement Comparable, then this little sorter
 * object will just assume that all objects are the same, and quietly do
 * nothing (so you should check for Comparable objecttypes elsewhere as
 * this object won't complain).</p>
 *
 * @version $Revision$
 */
class BeanSorter extends Object implements Comparator {
    private String property;
    private Decorator dec;

    /**
     * BeanSorter is a decorator of sorts, you need to initialize it with
     * the property name of the object that is to be sorted (getXXX method
     * name).  This property should return a Comparable object.
     */
    protected BeanSorter(String property, Decorator dec) {
        this.property = property;
        this.dec = dec;
    }

    /**
     * Compares two objects by first fetching a property from each object
     * and then comparing that value.  If there are any errors produced while
     * trying to compare these objects then a RunTimeException will be
     * thrown as any error found here will most likely be a programming
     * error that needs to be quickly addressed (like trying to compare
     * objects that are not comparable, or trying to read a property from a
     * bean that is invalid, etc...)
     *
     * @throws RuntimeException if there are any problems making a comparison
     *    of the object properties.
     */
    public int compare(Object o1, Object o2)
            throws RuntimeException {
        if (property == null) {
            throw new NullPointerException("Null property provided which " +
                                           "prevents BeanSorter sort");
        }

        try {
            Object p1 = null;
            Object p2 = null;

            // If they have supplied a decorator, then make sure and use it for
            // the sorting as well... TODO - Major hack....

            if (this.dec != null) {
                try {
                    dec.initRow(o1, -1, -1);
                    p1 = PropertyUtils.getProperty(dec, property);

                    dec.initRow(o2, -1, -1);
                    p2 = PropertyUtils.getProperty(dec, property);
                }
                catch (Exception e) {
                    // If there were any problems, then assume that the decorator
                    // does not implement that method, and instead fall down to
                    // the original object...
                    p1 = PropertyUtils.getProperty(o1, property);
                    p2 = PropertyUtils.getProperty(o2, property);
                }
            }
            else {
                p1 = PropertyUtils.getProperty(o1, property);
                p2 = PropertyUtils.getProperty(o2, property);
            }

            if (p1 instanceof Comparable && p2 instanceof Comparable) {
                Comparable c1 = (Comparable) p1;
                Comparable c2 = (Comparable) p2;
                return c1.compareTo(c2);
            }
            else {
                throw new RuntimeException("Object returned by property \"" +
                                           property + "\" is not a Comparable object");
            }
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("IllegalAccessException thrown while " +
                                       "trying to fetch property \"" + property + "\" during sort");
        }
        catch (InvocationTargetException e) {
            throw new RuntimeException("InvocationTargetException thrown while " +
                                       "trying to fetch property \"" + property + "\" during sort");
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException("NoSuchMethodException thrown while " +
                                       "trying to fetch property \"" + property + "\" during sort");
        }
    }


    /**
     * Checks if this Comparator the same as another one.
     */
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj instanceof BeanSorter) {
            if (this.property != null) {
                return this.property.equals(((BeanSorter) obj).property);
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
}