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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.displaytag.util.ShortToStringStyle;


/**
 * <p>
 * Represents a table cell.
 * </p>
 * <p>
 * A cell is used only when the content is placed as content of the column tag and need to be evaluated during
 * iteration. If the content is set using the <code>value</code> attribute in the column tag no cell is created and
 * EMPTY_CELL is used as placeholder.
 * </p>
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Cell implements Comparable
{

    /**
     * empty cell object. Use as placeholder for empty cell to avoid useless object creation.
     */
    public static final Cell EMPTY_CELL = new Cell();

    /**
     * content of the cell.
     */
    private Object staticValue;

    /**
     * Creates a new empty cell. This should never be done, use EMPTY_CELL instead.
     */
    private Cell()
    {
        super();
    }

    /**
     * Creates a cell with a static value.
     * @param value Object value of the Cell object
     */
    public Cell(Object value)
    {
        this.staticValue = value;
    }

    /**
     * get the static value for the cell.
     * @return the Object value of this.staticValue.
     */
    public Object getStaticValue()
    {
        return this.staticValue;
    }

    /**
     * set the static value of the cell.
     * @param value - the new value for this.staticValue
     */
    public void setStaticValue(Object value)
    {
        this.staticValue = value;
    }

    /**
     * Compare the Cell value to another Cell.
     * @param obj Object to compare this cell to
     * @return int
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo(Object obj)
    {

        if (this.staticValue == null)
        {
            return -1;
        }

        if (obj instanceof Cell)
        {
            return ((Comparable) this.staticValue).compareTo(((Cell) obj).getStaticValue());
        }

        return ((Comparable) this.staticValue).compareTo(obj);

    }

    /**
     * Simple toString wich output the static value.
     * @return String represantation of the cell
     */
    public String toString()
    {
        return new ToStringBuilder(this, ShortToStringStyle.SHORT_STYLE) //
            .append("staticValue", this.staticValue).toString(); //$NON-NLS-1$
    }

}