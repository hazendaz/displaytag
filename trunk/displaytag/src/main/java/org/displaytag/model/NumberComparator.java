package org.displaytag.model;

import org.displaytag.conversion.DefaultPropertyConvertor;
import org.apache.commons.beanutils.Converter;

import java.util.Comparator;

/**
 * Sorts 2 numbers, converted from objects using the Convertor.
 * @author rapruitt
 * @version $Revision$ ($Author$)

 */
public class NumberComparator implements Comparator
{
    /**
     * Used to convert from object.
     */
    private Converter convertor;

    /**
     * Use the DefaultPropertyConvertor.
     */
    public NumberComparator()
    {
        convertor = new DefaultPropertyConvertor();
    }

    /**
     * Use the specified Converter.
     * @param converter a Converter that can convert to Number
     */
    public NumberComparator(Converter converter)
    {
        this.convertor = converter;
    }

    /**
     * {@inheritDoc}
     */
    public int compare(Object obj1, Object obj2)
    {
        Double first = new Double(((Number) convertor.convert(Number.class, obj1)).doubleValue());
        Double second = new Double(((Number) convertor.convert(Number.class, obj2)).doubleValue());
        return first.compareTo(second);
    }
}
