package org.displaytag.pagination;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
/**
 * <p>Object representing a page</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class NumberedPage
{

    /**
     * page number
     */
    private int number;

    /**
     * is the page selected?
     */
    private boolean selected;

    /**
     * Creates a new page with the specified number
     * @param pageNumber page number
     * @param isSelected is the page selected?
     */
    public NumberedPage(int pageNumber, boolean isSelected)
    {
        this.number = pageNumber;
        this.selected = isSelected;
    }

    /**
     * return the page number
     * @return the page number
     */
    public int getNumber()
    {
        return number;
    }

    /**
     * is the page selected?
     * @return true if the page is slected
     */
    public boolean getSelected()
    {
        return selected;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
            .append("selected", this.selected)
            .append("number", this.number)
            .toString();
    }
}