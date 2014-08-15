/**
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag.model;

import java.text.Collator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.displaytag.util.HtmlAttributeMap;


/**
 * <p>
 * Represents a table cell.
 * </p>
 * <p>
 * A cell is used only when the content is placed as content of the column tag and need to be evaluated during
 * iteration.
 * </p>
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Cell implements Comparable<Cell>, Cloneable
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
     * Per row html attributes (style, class).
     */
    private HtmlAttributeMap attributes;

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
     * Compare the Cell value to another Cell.
     * @param obj Object to compare this cell to
     * @param collator Collator to use for the comparison of the other object is a Cell holding a String
     * @return int
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo(Object obj, Collator collator)
    {
        if (this.staticValue == null)
        {
            return -1;
        }
        if (obj instanceof Cell)
        {
            Object otherStatic = ((Cell) obj).getStaticValue();
            if (otherStatic == null)
            {
                return 1;
            }
            if (collator != null && this.staticValue instanceof String && otherStatic instanceof String)
            {
                String a = (String) this.staticValue;
                String b = (String) otherStatic;
                return collator.compare(a, b);
            }
            else
            {
                return ((Comparable<Object>) this.staticValue).compareTo(otherStatic);
            }
        }
        return ((Comparable<Object>) this.staticValue).compareTo(obj);
    }

    /**
     * Compare the Cell value to another Cell.
     * @param obj Object to compare this cell to
     * @return int
     * @see java.lang.Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(Cell obj)
    {
        return compareTo(obj, null);
    }

    /**
     * Simple toString wich output the static value.
     * @return String represantation of the cell
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE) //
            .append("staticValue", this.staticValue).toString(); //$NON-NLS-1$
    }

    public void setPerRowAttributes(HtmlAttributeMap perRowValues)
    {
        this.attributes = perRowValues;
    }

    public HtmlAttributeMap getPerRowAttributes()
    {
        return this.attributes;
    }

}