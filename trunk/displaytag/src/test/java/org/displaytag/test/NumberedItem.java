package org.displaytag.test;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * Simple test objects which wraps an int value.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class NumberedItem
{

    /**
     * Wrapped int.
     */
    private int number;

    /**
     * Instantiates a new numbered item.
     * @param num integer that will be returned by getNumber()
     */
    public NumberedItem(int num)
    {
        this.number = num;
    }

    /**
     * Getter for the wrapped int.
     * @return Returns the number.
     */
    public int getNumber()
    {
        return this.number;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("number", this.number).toString();
    }
}