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
package org.displaytag.model;

import java.util.Comparator;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.exception.RuntimeLookupException;
import org.displaytag.util.LookupUtil;


/**
 * Comparator for rows.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class RowSorter implements Comparator
{

    /**
     * name of the property in bean.
     */
    private String property;

    /**
     * table decorator.
     */
    private TableDecorator decorator;

    /**
     * sort order ascending?
     */
    private boolean ascending;

    /**
     * Index of the sorted column.
     */
    private int columnIndex;

    /**
     * Initialize a new RowSorter.
     * @param sortedColumnIndex index of the sorted column
     * @param beanProperty name of the property. If pProperty is null column index is used to get a static cell value
     * from the row object
     * @param tableDecorator TableDecorator instance
     * @param ascendingOrder boolean ascending order?
     */
    public RowSorter(int sortedColumnIndex, String beanProperty, TableDecorator tableDecorator, boolean ascendingOrder)
    {
        this.columnIndex = sortedColumnIndex;
        this.property = beanProperty;
        this.decorator = tableDecorator;
        this.ascending = ascendingOrder;
    }

    /**
     * Compares two objects by first fetching a property from each object and then comparing that value. If there are
     * any errors produced while trying to compare these objects then a RunTimeException will be thrown as any error
     * found here will most likely be a programming error that needs to be quickly addressed (like trying to compare
     * objects that are not comparable, or trying to read a property from a bean that is invalid, etc...)
     * @param object1 Object
     * @param object2 Object
     * @return int
     * @see java.util.Comparator#compare(Object, Object)
     */
    public final int compare(Object object1, Object object2)
    {

        Object obj1 = null;
        Object obj2 = null;

        // if property is null compare using two static cell objects
        if (this.property == null)
        {
            if (object1 instanceof Row)
            {
                obj1 = ((Row) object1).getCellList().get(this.columnIndex);
            }
            if (object2 instanceof Row)
            {
                obj2 = ((Row) object2).getCellList().get(this.columnIndex);
            }

            return checkNullsAndCompare(obj1, obj2);
        }

        if (object1 instanceof Row)
        {
            obj1 = ((Row) object1).getObject();
        }
        if (object2 instanceof Row)
        {
            obj2 = ((Row) object2).getObject();
        }

        try
        {
            Object result1 = null;
            Object result2 = null;

            // If they have supplied a decorator, then make sure and use it for the sorting as well
            if (this.decorator != null && this.decorator.hasGetterFor(this.property))
            {
                // set the row before sending to the decorator
                this.decorator.initRow(obj1, 0, 0);

                result1 = LookupUtil.getBeanProperty(this.decorator, this.property);

                // set the row before sending to the decorator
                this.decorator.initRow(obj2, 0, 0);

                result2 = LookupUtil.getBeanProperty(this.decorator, this.property);
            }
            else
            {
                result1 = LookupUtil.getBeanProperty(obj1, this.property);
                result2 = LookupUtil.getBeanProperty(obj2, this.property);
            }

            return checkNullsAndCompare(result1, result2);
        }
        catch (ObjectLookupException e)
        {
            throw new RuntimeLookupException(getClass(), this.property, e);
        }
    }

    /**
     * Compares two given objects handlig nulls and not comparable objects are handled. Not comparable objects are
     * compared using their string representation.
     * @param object1 first object to compare
     * @param object2 second object to compare
     * @return int result
     */
    private int checkNullsAndCompare(Object object1, Object object2)
    {
        int returnValue = 0;

        if (object1 instanceof Comparable && object2 instanceof Comparable)
        {
            returnValue = ((Comparable) object1).compareTo(object2);
        }
        else if (object1 != null && object2 != null)
        {
            // if object are not null and don't implement comparable, compare using string values
            returnValue = object1.toString().compareTo(object2.toString());
        }
        else if (object1 == null && object2 != null)
        {
            returnValue = 1;
        }
        else if (object1 != null && object2 == null)
        {
            returnValue = -1;
        }
        else
        {
            // both null
            returnValue = 0;
        }

        int ascendingInt = this.ascending ? 1 : -1;
        return ascendingInt * returnValue;
    }

    /**
     * Is this Comparator the same as another one?
     * @param object Object
     * @return boolean
     * @see java.util.Comparator#equals(Object)
     */
    public final boolean equals(Object object)
    {
        if (object instanceof RowSorter)
        {
            return new EqualsBuilder().append(this.property, ((RowSorter) object).property).append(
                this.columnIndex,
                ((RowSorter) object).columnIndex).isEquals();
        }

        return false;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public final int hashCode()
    {
        return new HashCodeBuilder(31, 33).append(this.property).append(this.columnIndex).toHashCode();
    }

}
