package org.displaytag.model;

import java.util.Iterator;
import java.util.List;

/**
 * <p>Iterator on columns</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class ColumnIterator
{

    /**
     * current row
     */
    private Row mParentRow;

    /**
     * Internal iterator on header cells
     */
    private Iterator mHeaderIterator;

    /**
     * Internal iterator on cells
     */
    private Iterator mCellIterator;

    /**
     * Create a new ColumnIterator given a list of column and a row
     * @param pColumns List containinf column objects
     * @param pParentRow current Row
     */
    public ColumnIterator(List pColumns, Row pParentRow)
    {
        mHeaderIterator = pColumns.iterator();
        mCellIterator = pParentRow.getCellList().iterator();
        mParentRow = pParentRow;
    }

    /**
     * Are there more columns?
     * @return boolean <code>true</code> if there are more columns
     */
    public boolean hasNext()
    {
        return mHeaderIterator.hasNext();
    }

    /**
     * Returns the next column
     * @return Column next column
     */
    public Column nextColumn()
    {
        // create a new column using the next value in the header and cell iterators and returns it
        return new Column((HeaderCell) mHeaderIterator.next(), (Cell) mCellIterator.next(), mParentRow);
    }

}