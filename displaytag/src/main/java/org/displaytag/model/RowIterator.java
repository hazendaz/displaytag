package org.displaytag.model;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.decorator.TableDecorator;

/**
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class RowIterator
{

    /**
     * logger
     */
    private static Log mLog = LogFactory.getLog(RowIterator.class);

    /**
     * List contaning CellHeader objects
     */
    private List mColumns;

    /**
     * internal iterator for Rows
     */
    private Iterator mIterator;

    /**
     * row number counter
     */
    private int mCount;

    /**
     * reference to the table TableDecorator
     */
    private TableDecorator mTableDecorator;

    /**
     * Constructor for RowIterator
     * @param pRowList List containing Row objects
     * @param pColumns List containing CellHeader objects
     * @param pTableDecorator TableDecorator
     */
    protected RowIterator(List pRowList, List pColumns, TableDecorator pTableDecorator)
    {
        mIterator = pRowList.iterator();
        mColumns = pColumns;
        mCount = 0;
        mTableDecorator = pTableDecorator;
    }

    /**
     * Method hasNext
     * @return boolean true if a new row
     */
    public boolean hasNext()
    {
        return mIterator.hasNext();
    }

    /**
     * return the next row object
     * @return Row
     */
    public Row next()
    {

        int lRowNumber = mCount++;

        if (mLog.isDebugEnabled())
        {
            mLog.debug("RowIterator.next() row number=" + lRowNumber);
        }

        Object lObject = mIterator.next();

        Row lRow = (Row) lObject;

        lRow.setRowNumber(lRowNumber);

        if (mTableDecorator != null)
        {
            mTableDecorator.initRow(lRow.getObject(), lRowNumber, lRowNumber);
        }

        return lRow;

    }

}