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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.displaytag.util.ShortToStringStyle;
import org.displaytag.util.TagConstants;


/**
 * Holds informations for a table row.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Row
{

    /**
     * Object holding values for the current row.
     */
    private Object rowObject;

    /**
     * List of cell objects.
     */
    private List staticCells;

    /**
     * Row number.
     */
    private int rowNumber;

    /**
     * TableModel which the row belongs to.
     */
    private TableModel tableModel;

    /**
     * Constructor for Row.
     * @param object Object
     * @param number int
     */
    public Row(Object object, int number)
    {
        this.rowObject = object;
        this.rowNumber = number;
        this.staticCells = new ArrayList();
    }

    /**
     * Setter for the row number.
     * @param number row number
     */
    public void setRowNumber(int number)
    {
        this.rowNumber = number;
    }

    /**
     * @return true if the current row number is odd
     */
    public boolean isOddRow()
    {
        return this.rowNumber % 2 == 0;
    }

    /**
     * Getter for the row number.
     * @return row number
     */
    public int getRowNumber()
    {
        return this.rowNumber;
    }

    /**
     * Adds a cell to the row.
     * @param cell Cell
     */
    public void addCell(Cell cell)
    {
        this.staticCells.add(cell);
    }

    /**
     * getter for the list of Cell object.
     * @return List containing Cell objects
     */
    public List getCellList()
    {
        return this.staticCells;
    }

    /**
     * getter for the object holding values for the current row.
     * @return Object object holding values for the current row
     */
    public Object getObject()
    {
        return this.rowObject;
    }

    /**
     * Iterates on columns.
     * @param columns List
     * @return ColumnIterator
     */
    public ColumnIterator getColumnIterator(List columns)
    {
        return new ColumnIterator(columns, this);
    }

    /**
     * Setter for the table model the row belongs to.
     * @param table TableModel
     */
    protected void setParentTable(TableModel table)
    {
        this.tableModel = table;
    }

    /**
     * Getter for the table model the row belongs to.
     * @return TableModel
     */
    protected TableModel getParentTable()
    {
        return this.tableModel;
    }

    /**
     * Writes the open &lt;tr> tag.
     * @return String &lt;tr> tag with the appropriate css class attribute
     */
    public String getOpenTag()
    {
        String css = this.tableModel.getProperties().getCssRow(this.rowNumber);

        if (StringUtils.isNotBlank(css))
        {
            return TagConstants.TAG_OPEN + TagConstants.TAGNAME_ROW + " " //$NON-NLS-1$
                + TagConstants.ATTRIBUTE_CLASS + "=\"" //$NON-NLS-1$
                + css + "\"" //$NON-NLS-1$
                + TagConstants.TAG_CLOSE;
        }

        return TagConstants.TAG_OPEN + TagConstants.TAGNAME_ROW + TagConstants.TAG_CLOSE;

    }

    /**
     * writes the &lt;/tr> tag.
     * @return String &lt;/tr> tag
     */
    public String getCloseTag()
    {
        return TagConstants.TAG_TR_CLOSE;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this, ShortToStringStyle.SHORT_STYLE) //
            .append("rowNumber", this.rowNumber) //$NON-NLS-1$
            .append("rowObject", this.rowObject) //$NON-NLS-1$
            .toString();
    }
}