package org.displaytag.model;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>Represents a table cell</p>
 * <p>A cell is used only when the content is placed as content of the column tag and need to be evaluated during
 * iteration. If the content is set using the <code>value</code> attribute in the column tag no cell is created and
 * EMPTY_CELL is used as placeholder.</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class Cell implements Comparable
{

    /**
     * empty cell object. Use as placeholder for empty cell to avoid useless object creation
     */
    public static final Cell EMPTY_CELL = new Cell();

    /**
     * content of the cell
     */
    private Object mStaticValue;

    /**
     * Creates a new empty cell. This should never be done, use EMPTY_CELL instead
     */
    private Cell()
    {
    }

    /**
     * Creates a cell with a static value
     * @param pStaticValue Object value of the Cell object
     */
    public Cell(Object pStaticValue)
    {
        mStaticValue = pStaticValue;
    }

    /**
     * get the static value for the cell
     * @return the Object value of mStaticValue.
     */
    public Object getStaticValue()
    {
        return mStaticValue;
    }

    /**
     * set the static value of the cell
     * @param pStaticValue - the new value for mStaticValue
     */
    public void setStaticValue(Object pStaticValue)
    {
        mStaticValue = pStaticValue;
    }

    /**
     * Compare the Cell value to another Cell
     * @param pObj Object to compare this cell to
     * @return int
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo(Object pObj)
    {

        if (mStaticValue == null)
        {
            return -1;
        }

        if (pObj instanceof Cell)
        {
            return ((Comparable) mStaticValue).compareTo(((Cell) pObj).getStaticValue());
        }
        else
        {
            return ((Comparable) mStaticValue).compareTo(pObj);
        }

    }

    /**
     * Simple toString wich output the static value
     * @return String represantation of the cell
     */
    public String toString()
    {
        return new ToStringBuilder(this).append("staticValue", mStaticValue).toString();
    }

}