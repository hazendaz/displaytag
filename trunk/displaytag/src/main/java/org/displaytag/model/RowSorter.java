package org.displaytag.model;

import java.util.Comparator;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.decorator.Decorator;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.util.LookupUtil;

/**
 * <p>Comparator for rows</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class RowSorter implements Comparator
{

    /**
     * logger
     */
    private static Log log = LogFactory.getLog(RowSorter.class);

    /**
     * name of the property in bean
     */
    private String property;

    /**
     * table decorator
     */
    private Decorator tableDecorator;

    /**
     * sort order ascending?
     */
    private boolean ascending;

    /**
     * index of the sorted column
     */
    private int columnIndex;

    /**
     * initialize a new RowSorter
     * @param pColumnIndex index of the sorted column
     * @param pProperty name of the property. If pProperty is null column index is used to get a static cell value from
     * the row object
     * @param pDecorator TableDecorator
     * @param pAscending boolean ascending order?
     */
    public RowSorter(int pColumnIndex, String pProperty, TableDecorator pDecorator, boolean pAscending)
    {
        this.columnIndex = pColumnIndex;
        this.property = pProperty;
        this.tableDecorator = pDecorator;
        this.ascending = pAscending;
    }

    /**
     * Compares two objects by first fetching a property from each object and then comparing that value.  If there are
     * any errors produced while trying to compare these objects then a RunTimeException will be thrown as any error
     * found here will most likely be a programming error that needs to be quickly addressed (like trying to compare
     * objects that are not comparable, or trying to read a property from a bean that is invalid, etc...)
     *
     * @param pObject1 Object
     * @param pObject2 Object
     * @return int
     * @see java.util.Comparator#compare(Object, Object)
     */
    public final int compare(Object pObject1, Object pObject2)
    {

        Object lObj1 = null;
        Object lObj2 = null;

        // if property is null compare using two static cell objects
        if (property == null)
        {
            if (pObject1 instanceof Row)
            {
                lObj1 = ((Row) pObject1).getCellList().get(columnIndex);
            }
            if (pObject2 instanceof Row)
            {
                lObj2 = ((Row) pObject2).getCellList().get(columnIndex);
            }

            return checkNullsAndCompare(lObj1, lObj2);

        }
        else
        {
            if (pObject1 instanceof Row)
            {
                lObj1 = ((Row) pObject1).getObject();
            }
            if (pObject2 instanceof Row)
            {
                lObj2 = ((Row) pObject2).getObject();
            }

            try
            {
                Object lResult1 = null;
                Object lResult2 = null;

                // If they have supplied a decorator, then make sure and use it for the sorting as well
                if (tableDecorator != null && tableDecorator.hasGetterFor(property))
                {
                    // set the row before sending to the decorator
                     ((TableDecorator) tableDecorator).initRow(lObj1, 0, 0);

                    lResult1 = LookupUtil.getBeanProperty(tableDecorator, property);

                    // set the row before sending to the decorator
                     ((TableDecorator) tableDecorator).initRow(lObj2, 0, 0);

                    lResult2 = LookupUtil.getBeanProperty(tableDecorator, property);
                }
                else
                {
                    lResult1 = LookupUtil.getBeanProperty(lObj1, property);
                    lResult2 = LookupUtil.getBeanProperty(lObj2, property);
                }

                return checkNullsAndCompare(lResult1, lResult2);
            }
            catch (ObjectLookupException e)
            {
                /** @todo error handling need to be improved, can't throw an exception here */
                log.error(
                    "ObjectLookupException thrown while trying to fetch property \"" + property + "\" during sort",
                    e);
                throw new RuntimeException(
                    "ObjectLookupException thrown while trying to fetch property \"" + property + "\" during sort");
            }
        }
    }

    /**
     * <p>compare two given objects according to the pAscending flag</p>
     * <p>Null values and not comparable objects are handled. Not comparable objects are compared using their string
     * representation</p>
     * @param pObject1 first object to compare
     * @param pObject2 second object to compare
     * @return int result
     */
    private int checkNullsAndCompare(Object pObject1, Object pObject2)
    {
        int lAscending = ascending ? 1 : -1;

        if (pObject1 instanceof Comparable && pObject2 instanceof Comparable)
        {
            return lAscending * ((Comparable) pObject1).compareTo(pObject2);
        }
        else if (pObject1 == null && pObject2 == null)
        {
            return 0;
        }
        else if (pObject1 == null && pObject2 != null)
        {
            return 1;
        }
        else if (pObject1 != null && pObject2 == null)
        {
            return -1;
        }
        else
        {
            // if object are not null and don't implement comparable, compare using string values
            return pObject1.toString().compareTo(pObject2.toString());
        }
    }

    /**
     * Is this Comparator the same as another one?
     * @param pObject Object
     * @return boolean
     * @see java.util.Comparator#equals(Object)
     */
    public final boolean equals(Object pObject)
    {
        if (pObject instanceof RowSorter)
        {
            return new EqualsBuilder()
                .append(property, ((RowSorter) pObject).property)
                .append(columnIndex, ((RowSorter) pObject).columnIndex)
                .isEquals();
        }

        return false;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public final int hashCode()
    {
        return new HashCodeBuilder(31, 33).append(property).append(columnIndex).toHashCode();
    }

}
