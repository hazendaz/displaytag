package org.displaytag.model;

import java.util.ArrayList;
import java.util.List;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.TagConstants;

/**
 * @author fgiust
 * @version $Revision$ ($Author$)
 */

public class Row
{

    /**
     * Field this.rowObject
     */
    private Object rowObject;

    /**
     * Field mStaticCells
     */
    private List staticCells;

    /**
     * Field mRowNumber
     */
    private int rowNumber;

    /**
     * Field mTableModel
     */
    private TableModel tableModel;

    /**
     *
     * @param number the new value for this.rowNumber
     */
    public void setRowNumber(int number)
    {
        this.rowNumber = number;
    }

    /**
     *
     * @return true if isOddRow is set to true.
     */
    public boolean isOddRow()
    {
        return this.rowNumber % 2 == 0;
    }

    /**
     * Method getRowNumber
     * @return int
     */
    public int getRowNumber()
    {
        return this.rowNumber;
    }

    /**
     * Constructor for Row
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
     * Method addCell
     * @param cell Cell
     */
    public void addCell(Cell cell)
    {
        this.staticCells.add(cell);
    }

    /**
     * Method getCellList
     * @return List
     */
    public List getCellList()
    {
        return this.staticCells;
    }

    /**
     * Method getObject
     * @return Object
     */
    public Object getObject()
    {
        return this.rowObject;
    }

    /**
     * Method toString
     * @return String
     */
    public String toString()
    {
        return this.rowObject.toString();
    }

    /**
     * Method getColumnIterator
     * @param columns List
     * @return ColumnIterator
     */
    public ColumnIterator getColumnIterator(List columns)
    {
        return new ColumnIterator(columns, this);
    }

    /**
     * Method setParentTable
     * @param table TableModel
     */
    protected void setParentTable(TableModel table)
    {
        this.tableModel = table;
    }

    /**
     * Method getParentTable
     * @return TableModel
     */
    protected TableModel getParentTable()
    {
        return this.tableModel;
    }

    /**
     * writes the open &lt;tr&gt; tag
     * @return String &lt;tr&gt; tag
     */
    public String getOpenTag()
    {
        return TagConstants.TAG_OPEN
            + TagConstants.TAGNAME_ROW
            + (isOddRow()
                ? " " + TagConstants.ATTRIBUTE_CLASS + "=\"" + TableTagParameters.CSS_ODDROW + "\""
                : " " + TagConstants.ATTRIBUTE_CLASS + "=\"" + TableTagParameters.CSS_EVENROW + "\"")
            + TagConstants.TAG_CLOSE;
    }

    /**
     * writes the &lt;/tr&gt; tag
     * @return String &lt;/tr&gt; tag
     */
    public String getCloseTag()
    {
        return TagConstants.TAG_TR_CLOSE;
    }

}