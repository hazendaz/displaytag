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
     * logger.
     */
    private static Log log = LogFactory.getLog(RowIterator.class);

    /**
     * internal iterator for Rows.
     */
    private Iterator iterator;

    /**
     * row number counter.
     */
    private int rowNumber;

    /**
     * reference to the table TableDecorator.
     */
    private TableDecorator decorator;

    /**
     * id inherited from the TableTag (needed only for logging).
     */
    private String id;

    /**
     * Constructor for RowIterator.
     * @param rowList List containing Row objects
     * @param columnList List containing CellHeader objects
     * @param tableDecorator TableDecorator
     */
    protected RowIterator(List rowList, List columnList, TableDecorator tableDecorator)
    {
        this.iterator = rowList.iterator();
        this.rowNumber = 0;
        this.decorator = tableDecorator;
    }

    /**
     * Setter for the tablemodel id.
     * @param tableId same id of table tag, needed for logging
     */
    public void setId(String tableId)
    {
        this.id = tableId;
    }

    /**
     * Check if a next row exist.
     * @return boolean true if a new row
     */
    public boolean hasNext()
    {
        return this.iterator.hasNext();
    }

    /**
     * Returns the next row object.
     * @return Row
     */
    public Row next()
    {

        int currentRowNumber = this.rowNumber++;

        if (log.isDebugEnabled())
        {
            log.debug("[" + this.id + "] rowIterator.next() row number=" + currentRowNumber);
        }

        Object object = this.iterator.next();

        Row row = (Row) object;

        row.setRowNumber(currentRowNumber);

        if (this.decorator != null)
        {
            this.decorator.initRow(row.getObject(), currentRowNumber, currentRowNumber);
        }

        return row;

    }

}