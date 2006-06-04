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

import java.text.Collator;
import java.util.Comparator;


/**
 * Default comparator. Was previously part of RowSorter.
 * @author fguist
 * @author rapruitt
 * @version $Revision$ ($Author$)
 */
public class DefaultComparator implements Comparator
{

    /**
     * Use this collator.
     */
    private Collator collator;

    /**
     * Instantiate a default comparator with no collator specified.
     */
    public DefaultComparator()
    {
        this(Collator.getInstance());
    }

    /**
     * Instantiate a default comparator with a specified collator.
     * @param collatorToUse collator instance
     */
    public DefaultComparator(Collator collatorToUse)
    {
        this.collator = collatorToUse;
        collator.setStrength(Collator.PRIMARY); // ignore case and accents
    }

    /**
     * Compares two given objects. Not comparable objects are compared using their string representation. String
     * comparisons are done using a Collator.
     * @param object1 first parameter
     * @param object2 second parameter
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
