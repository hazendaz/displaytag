package org.displaytag.conversion;


/**
 * The PropertyConvertor specifies the translation between a column value and a number.  It is used for creating
 * totals.
 * @author rapruitt
 * @version $Revision$ ($Author$)
 */
public interface PropertyConvertor
{

    /**
     * Convert this parameter to a Number.
     * @param value the value to convert
     * @return a Number the converted value     
     */
    Number asNumber(Object value);

}
