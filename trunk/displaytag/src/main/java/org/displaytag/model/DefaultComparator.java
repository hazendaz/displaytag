package org.displaytag.model;

import java.util.Comparator;
import java.text.Collator;


/**
 * Default comparator.  Was previously part of RowSorter.
 * @author fguist
 * @author rapruitt
 * @version $Revision$ ($Author$)
 */
public class DefaultComparator implements Comparator
{
    /**
     * Use this collator.
     */
    private Collator collator = Collator.getInstance();

    /**
     * Get the collator.
     * @return the collator
     */
    public Collator getCollator()
    {
        return collator;
    }

    /**
     * Set the collator.
     * @param collatorToUse the value
     */
    public void setCollator(Collator collatorToUse)
    {
        this.collator = collatorToUse;
    }

    /**
     * Compares two given objects. Not comparable objects are compared using their string representation. String
     * comparisons are done using a Collator.
     * @param object1       first parameter
     * @param object2       second parameter
     *
     * @return the value
     */
    public int compare(Object object1, Object object2)
    {
        int returnValue;
        if (object1 instanceof String && object2 instanceof String)
        {
            returnValue = collator.compare(object1, object2);
        }
        else if (object1 instanceof Comparable && object2 instanceof Comparable)
        {
            returnValue = ((Comparable) object1).compareTo(object2);
        }
        else
        {
            // if object are not null and don't implement comparable, compare using string values
            returnValue = collator.compare(object1.toString(), object2.toString());
        }
        return returnValue;
    }
}
