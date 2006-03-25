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
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.MultipleHtmlAttribute;
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
        Map rowAttributes = new HtmlAttributeMap();

        MultipleHtmlAttribute cssAttribute = new MultipleHtmlAttribute(this.tableModel.getProperties().getCssRow(
            this.rowNumber));

        if (this.tableModel.getTableDecorator() != null)
        {
            try
            {
                String addStyle = this.tableModel.getTableDecorator().addRowClass();

                if (StringUtils.isNotBlank(addStyle))
                {
                    cssAttribute.addAttributeValue(addStyle);
                }

                String id = this.tableModel.getTableDecorator().addRowId();
                if (StringUtils.isNotBlank(id))
                {
                    rowAttributes.put(TagConstants.ATTRIBUTE_ID, id);
                }
            }
            catch (NoSuchMethodError e)
            {
                // this catch is here to allow decorators compiled with displaytag 1.0 work with 1.1
                // since the addRowClass() and addRowId() are new in displaytag 1.1 earlier decorators could throw
                // a NoSuchMethodError... be nice with them till a next major release
            }
        }

        if (!cssAttribute.isEmpty())
        {
            rowAttributes.put(TagConstants.ATTRIBUTE_CLASS, cssAttribute);
        }

        StringBuffer tag = new StringBuffer();
        tag.append(TagConstants.TAG_OPEN);
        tag.append(TagConstants.TAGNAME_ROW);

        tag.append(rowAttributes.toString());

        tag.append(TagConstants.TAG_CLOSE);

        return tag.toString();
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE) //
            .append("rowNumber", this.rowNumber) //$NON-NLS-1$
            .append("rowObject", this.rowObject) //$NON-NLS-1$
            .toString();
    }
}