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
     * Field mRowObject
     */
    private Object mRowObject;

    /**
     * Field mStaticCells
     */
    private List mStaticCells;
    /**
     * Field mRowNumber
     */
    private int mRowNumber;

    /**
     * Field mTableModel
     */
    private TableModel mTableModel;

    /**
     *
     * @param pRowNumber - the new value for mRowNumber
     */
    public void setRowNumber(int pRowNumber)
    {
        mRowNumber = pRowNumber;
    }

    /**
     *
     * @return true if isOddRow is set to true.
     */
    public boolean isOddRow()
    {
        return mRowNumber % 2 == 0;
    }

    /**
     * Method getRowNumber
     * @return int
     */
    public int getRowNumber()
    {
        return mRowNumber;
    }

    /**
     * Constructor for Row
     * @param pRowObject Object
     * @param pRowNumber int
     */
    public Row(Object pRowObject, int pRowNumber)
    {
        mRowObject = pRowObject;
        mRowNumber = pRowNumber;
        mStaticCells = new ArrayList();
    }

    /**
     * Method addCell
     * @param pCell Cell
     */
    public void addCell(Cell pCell)
    {
        // mLog.debug("adding cell " + pCell + " to collection " + mStaticCells);
        mStaticCells.add(pCell);
    }

    /**
     * Method getCellList
     * @return List
     */
    public List getCellList()
    {
        return mStaticCells;
    }

    /**
     * Method getObject
     * @return Object
     */
    public Object getObject()
    {
        return mRowObject;
    }

    /**
     * Method toString
     * @return String
     */
    public String toString()
    {
        return mRowObject.toString();
    }

    /**
     * Method getColumnIterator
     * @param pColumns List
     * @return ColumnIterator
     */
    public ColumnIterator getColumnIterator(List pColumns)
    {
        return new ColumnIterator(pColumns, this);
    }

    /**
     * Method setParentTable
     * @param pTableModel TableModel
     */
    protected void setParentTable(TableModel pTableModel)
    {
        mTableModel = pTableModel;
    }

    /**
     * Method getParentTable
     * @return TableModel
     */
    protected TableModel getParentTable()
    {
        return mTableModel;
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