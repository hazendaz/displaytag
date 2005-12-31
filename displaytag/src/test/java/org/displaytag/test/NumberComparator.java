package org.displaytag.test;

import java.util.Comparator;

import org.apache.commons.beanutils.ConvertUtils;


/**
 * Sorts 2 numbers, converted from objects using beanutils Converters.
 * @author rapruitt
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class NumberComparator implements Comparator
{

    /**
     * @see Comparator#compare(Object, Object)
     */
    public int compare(Object obj1, Object obj2)
    {
        double dbl1 = 0;
        if (obj1 instanceof Number)
        {
            dbl1 = ((Number) obj1).doubleValue();
        }
        else if (obj1 != null)
        {
            dbl1 = ((Number) ConvertUtils.convert(obj1.toString(), Number.class)).doubleValue();
        }

        double dbl2 = 0;
        if (obj2 instanceof Number)
        {
            dbl2 = ((Number) obj2).doubleValue();
        }
        else if (obj1 != null)
        {
            dbl2 = ((Number) ConvertUtils.convert(obj2.toString(), Number.class)).doubleValue();
        }

        return new Double(dbl1).compareTo(new Double(dbl2));
    }
}
