/*
 * Copyright (C) 2002-2024 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag.model;

import java.util.Comparator;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.exception.RuntimeLookupException;
import org.displaytag.util.LookupUtil;

/**
 * Comparator for rows.
 */
public class RowSorter implements Comparator<Object> {

    /**
     * name of the property in bean.
     */
    private final String property;

    /**
     * table decorator.
     */
    private final TableDecorator decorator;

    /** sort order ascending?. */
    private final boolean ascending;

    /**
     * Index of the sorted column.
     */
    private final int columnIndex;

    /**
     * Comparator used for comparisons.
     */
    private final Comparator<Object> comparator;

    /**
     * Initialize a new RowSorter.
     *
     * @param sortedColumnIndex
     *            index of the sorted column
     * @param beanProperty
     *            name of the property. If pProperty is null column index is used to get a static cell value from the
     *            row object
     * @param tableDecorator
     *            TableDecorator instance
     * @param ascendingOrder
     *            boolean ascending order?
     * @param compar
     *            the comparator to use
     */
    public RowSorter(final int sortedColumnIndex, final String beanProperty, final TableDecorator tableDecorator,
            final boolean ascendingOrder, final Comparator<Object> compar) {
        this.columnIndex = sortedColumnIndex;
        this.property = beanProperty;
        this.decorator = tableDecorator;
        this.ascending = ascendingOrder;
        this.comparator = compar;
        if (compar == null) {
            throw new IllegalArgumentException(
                    "A null comparator has been passed to RowSorter. A comparator instance is required");
        }
    }

    /**
     * Compares two objects by first fetching a property from each object and then comparing that value. If there are
     * any errors produced while trying to compare these objects then a RunTimeException will be thrown as any error
     * found here will most likely be a programming error that needs to be quickly addressed (like trying to compare
     * objects that are not comparable, or trying to read a property from a bean that is invalid, etc...)
     *
     * @param object1
     *            Object
     * @param object2
     *            Object
     *
     * @return int
     *
     * @see java.util.Comparator#compare(Object, Object)
     */
    @Override
    public final int compare(final Object object1, final Object object2) {

        Object obj1 = null;
        Object obj2 = null;

        // if property is null compare using two static cell objects
        if (this.property == null) {
            if (object1 instanceof Row) {
                obj1 = ((Row) object1).getCellList().get(this.columnIndex);
            }
            if (object2 instanceof Row) {
                obj2 = ((Row) object2).getCellList().get(this.columnIndex);
            }

            return this.checkNullsAndCompare(obj1, obj2);
        }

        if (object1 instanceof Row) {
            obj1 = ((Row) object1).getObject();
        }
        if (object2 instanceof Row) {
            obj2 = ((Row) object2).getObject();
        }

        try {
            Object result1;
            Object result2;

            // If they have supplied a decorator, then make sure and use it for the sorting as well
            if (this.decorator != null && this.decorator.hasGetterFor(this.property)) {
                // set the row before sending to the decorator
                this.decorator.initRow(obj1, 0, 0);

                result1 = LookupUtil.getBeanProperty(this.decorator, this.property);

                // set the row before sending to the decorator
                this.decorator.initRow(obj2, 0, 0);

                result2 = LookupUtil.getBeanProperty(this.decorator, this.property);
            } else {
                result1 = LookupUtil.getBeanProperty(obj1, this.property);
                result2 = LookupUtil.getBeanProperty(obj2, this.property);
            }

            return this.checkNullsAndCompare(result1, result2);
        } catch (final ObjectLookupException e) {
            throw new RuntimeLookupException(this.getClass(), this.property, e);
        }
    }

    /**
     * Compares two given objects, and handles the case where nulls are present.
     *
     * @param object1
     *            first object to compare
     * @param object2
     *            second object to compare
     *
     * @return int result
     */
    private int checkNullsAndCompare(final Object object1, final Object object2) {
        int returnValue;
        if (object1 == null && object2 != null) {
            returnValue = -1;
        } else if (object1 != null && object2 == null) {
            returnValue = 1;
        } else if (object1 == null && object2 == null) {
            // both null
            returnValue = 0;
        } else {
            returnValue = this.comparator.compare(object1, object2);
        }
        final int ascendingInt = this.ascending ? 1 : -1;
        return ascendingInt * returnValue;
    }

    /**
     * Is this Comparator the same as another one?.
     *
     * @param object
     *            Object
     *
     * @return boolean
     *
     * @see java.util.Comparator#equals(Object)
     */
    @Override
    public final boolean equals(final Object object) {
        if (object instanceof RowSorter) {
            return new EqualsBuilder().append(this.property, ((RowSorter) object).property)
                    .append(this.columnIndex, ((RowSorter) object).columnIndex).isEquals();
        }

        return false;
    }

    /**
     * Hash code.
     *
     * @return the int
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        return new HashCodeBuilder(31, 33).append(this.property).append(this.columnIndex).toHashCode();
    }

}
