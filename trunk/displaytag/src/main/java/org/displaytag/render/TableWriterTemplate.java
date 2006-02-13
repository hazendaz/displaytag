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
package org.displaytag.render;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.model.Column;
import org.displaytag.model.ColumnIterator;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.RowIterator;
import org.displaytag.model.TableModel;
import org.displaytag.properties.TableProperties;
import org.displaytag.util.TagConstants;


/**
 * A template that encapsulates and drives the construction of a table based on a given table model and configuration.
 * This class is meant to be extended by classes that build tables sharing the same structure, sorting, and grouping,
 * but that write them in different formats and to various destinations. Subclasses must provide the format- and
 * destination-specific implementations of the abstract methods this class calls to build a table. (Background: This
 * class came about because our users wanted to export tables to Excel and PDF just as they were presented in HTML. It
 * originates with the TableTagData.writeHTMLData method, factoring its logic so that it can be re-used by classes that
 * write the tables as PDF, Excel, RTF and other formats. TableTagData.writeHTMLData now calls an HTML extension of this
 * class to write tables in HTML format to a JSP page.)
 * @author Jorge L. Barroso
 * @version $Id$
 */
public abstract class TableWriterTemplate
{

    public static final short GROUP_START = -2;

    public static final short GROUP_END = 5;

    public static final short GROUP_START_AND_END = 3;

    public static final short GROUP_NO_CHANGE = 0;

    protected static final int NO_RESET_GROUP = 42000;

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(TableWriterTemplate.class);

    /**
     * Table unique id.
     */
    private String id;

    int lowestEndedGroup;
    int lowestStartedGroup;

    /**
     * Given a table model, this method creates a table, sorting and grouping it per its configuration, while delegating
     * where and how it writes the table to subclass objects. (Background: This method refactors
     * TableTagData.writeHTMLData method. See above.)
     * @param model The table model used to build the table.
     * @param id This table's page id.
     * @throws JspException if any exception thrown while constructing the tablei, it is caught and rethrown as a
     * JspException. Extension classes may throw all sorts of exceptions, depending on their respective formats and
     * destinations.
     */
    public void writeTable(TableModel model, String id) throws JspException
    {
        try
        {
            // table id used for logging
            this.id = id;

            TableProperties properties = model.getProperties();

            if (log.isDebugEnabled())
            {
                log.debug("[" + this.id + "] writeTable called for table [" + this.id + "]");
            }

            // Handle empty table
            boolean noItems = model.getRowListPage().size() == 0;
            if (noItems && !properties.getEmptyListShowTable())
            {
                writeEmptyListMessage(properties.getEmptyListMessage());
                return;
            }

            // Put the page stuff there if it needs to be there...
            if (properties.getAddPagingBannerTop())
            {
                // search result and navigation bar
                writeTopBanner(model);
            }

            // open table
            writeTableOpener(model);

            // render caption
            if (model.getCaption() != null)
            {
                writeCaption(model);
            }

            // render headers
            if (model.getProperties().getShowHeader())
            {
                writeTableHeader(model);
            }

            // render footer prior to body
            if (model.getFooter() != null)
            {
                writePreBodyFooter(model);
            }

            // open table body
            writeTableBodyOpener(model);

            // render table body
            writeTableBody(model);

            // close table body
            writeTableBodyCloser(model);

            // render footer after body
            if (model.getFooter() != null)
            {
                writePostBodyFooter(model);
            }

            // close table
            writeTableCloser(model);

            if (model.getTableDecorator() != null)
            {
                writeDecoratedTableFinish(model);
            }

            writeBottomBanner(model);

            if (log.isDebugEnabled())
            {
                log.debug("[" + this.id + "] writeTable end");
            }
        }
        catch (Exception e)
        {
            throw new JspException(e);
        }
    }

    /**
     * Called by writeTable to write a message explaining that the table model contains no data.
     * @param emptyListMessage A message explaining that the table model contains no data.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writeEmptyListMessage(String emptyListMessage) throws Exception;

    /**
     * Called by writeTable to write a summary of the search result this table reports and the table's pagination
     * interface.
     * @param model The table model for which the banner is written.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writeTopBanner(TableModel model) throws Exception;

    /**
     * Called by writeTable to write the start of the table structure.
     * @param model The table model for which the content is written.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writeTableOpener(TableModel model) throws Exception;

    /**
     * Called by writeTable to write the table's caption.
     * @param model The table model for which the content is written.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writeCaption(TableModel model) throws Exception;

    /**
     * Called by writeTable to write the table's header columns.
     * @param model The table model for which the content is written.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writeTableHeader(TableModel model) throws Exception;

    /**
     * Called by writeTable to write table footer before table body.
     * @param model The table model for which the content is written.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writePreBodyFooter(TableModel model) throws Exception;

    /**
     * Called by writeTable to write the start of the table's body.
     * @param model The table model for which the content is written.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writeTableBodyOpener(TableModel model) throws Exception;

    // protected abstract void writeTableBody(TableModel model);

    /**
     * Called by writeTable to write the end of the table's body.
     * @param model The table model for which the content is written.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writeTableBodyCloser(TableModel model) throws Exception;

    /**
     * Called by writeTable to write table footer after table body.
     * @param model The table model for which the content is written.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writePostBodyFooter(TableModel model) throws Exception;

    /**
     * Called by writeTable to write the end of the table's structure.
     * @param model The table model for which the content is written.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writeTableCloser(TableModel model) throws Exception;

    /**
     * Called by writeTable to decorate the table.
     * @param model The table model for which the content is written.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writeDecoratedTableFinish(TableModel model) throws Exception;

    /**
     * Called by writeTable to write the table's footer.
     * @param model The table model for which the content is written.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writeBottomBanner(TableModel model) throws Exception;

    /**
     * Given a table model, writes the table body content, sorting and grouping it per its configuration, while
     * delegating where and how it writes to subclass objects. (Background: This method refactors
     * TableTagData.writeTableBody method. See above.)
     * @param model The table model used to build the table body.
     * @throws Exception if an error is encountered while writing the table body.
     */
    private void writeTableBody(TableModel model) throws Exception
    {
        // Ok, start bouncing through our list (only the visible part)
        RowIterator rowIterator = model.getRowIterator(false);

        // iterator on rows
        TableDecorator tableDecorator = model.getTableDecorator();
        Row previousRow = null;
        Row currentRow = null;
        Row nextRow = null;
        Map previousRowValues = new HashMap(10);
        Map currentRowValues = new HashMap(10);
        Map nextRowValues = new HashMap(10);

        while (nextRow != null || rowIterator.hasNext())
        {
            // The first pass
            if (currentRow == null)
            {
                currentRow = rowIterator.next();
            }
            else
            {
                previousRow = currentRow;
                currentRow = nextRow;
            }

            if (previousRow != null)
            {
                previousRowValues.putAll(currentRowValues);
            }
            if (!nextRowValues.isEmpty())
            {
                currentRowValues.putAll(nextRowValues);
            }
            // handle the first pass
            else
            {
                ColumnIterator columnIterator = currentRow.getColumnIterator(model.getHeaderCellList());
                // iterator on columns
                if (log.isDebugEnabled())
                {
                    log.debug(" creating ColumnIterator on " + model.getHeaderCellList());
                }
                while (columnIterator.hasNext())
                {
                    Column column = columnIterator.nextColumn();

                    // Get the value to be displayed for the column
                    column.initialize();
                    CellStruct struct = new CellStruct(column, column.getChoppedAndLinkedValue());
                    currentRowValues.put(new Integer(column.getHeaderCell().getColumnNumber()), struct);
                }
            }

            nextRowValues.clear();
            // Populate the next row values
            nextRow = rowIterator.hasNext() ? rowIterator.next() : null;
            if (nextRow != null)
            {
                ColumnIterator columnIterator = nextRow.getColumnIterator(model.getHeaderCellList());
                // iterator on columns
                if (log.isDebugEnabled())
                {
                    log.debug(" creating ColumnIterator on " + model.getHeaderCellList());
                }
                while (columnIterator.hasNext())
                {
                    Column column = columnIterator.nextColumn();
                    column.initialize();
                    // Get the value to be displayed for the column
                    CellStruct struct = new CellStruct(column, column.getChoppedAndLinkedValue());
                    nextRowValues.put(new Integer(column.getHeaderCell().getColumnNumber()), struct);
                }
            }
            // now we are going to create the current row; reset the decorator to the current row
            if (tableDecorator != null)
            {
                tableDecorator.initRow(currentRow.getObject(), currentRow.getRowNumber(), currentRow.getRowNumber()
                    + rowIterator.getPageOffset());
            }

            Iterator headerCellsIter = model.getHeaderCellList().iterator();
            ArrayList structsForRow = new ArrayList(model.getHeaderCellList().size());
            lowestEndedGroup = NO_RESET_GROUP;
            lowestStartedGroup = NO_RESET_GROUP;
            while (headerCellsIter.hasNext())
            {
                HeaderCell header = (HeaderCell) headerCellsIter.next();

                // Get the value to be displayed for the column
                CellStruct struct = (CellStruct) currentRowValues.get(new Integer(header.getColumnNumber()));
                struct.decoratedValue = struct.bodyValue;
                // Check and see if there is a grouping transition. If there is, then notify the decorator
                if (header.getGroup() != -1)
                {
                    CellStruct prior = (CellStruct) previousRowValues.get(new Integer(header.getColumnNumber()));
                    CellStruct next = (CellStruct) nextRowValues.get(new Integer(header.getColumnNumber()));
                    // Why npe?
                    String priorBodyValue = prior != null ? prior.bodyValue : null;
                    String nextBodyValue = next != null ? next.bodyValue : null;
                    short groupingValue = groupColumns(struct.bodyValue,
                            priorBodyValue, nextBodyValue, header.getGroup());

                    if (tableDecorator != null)
                    {
                        switch (groupingValue)
                        {
                            case GROUP_START :
                                tableDecorator.startOfGroup(struct.bodyValue, header.getGroup());
                                break;
                            case GROUP_END :
                                tableDecorator.endOfGroup(struct.bodyValue, header.getGroup());
                                break;
                            case GROUP_START_AND_END :
                                tableDecorator.startOfGroup(struct.bodyValue, header.getGroup());
                                tableDecorator.endOfGroup(struct.bodyValue, header.getGroup());
                                break;
                            default :
                                break;
                        }
                    }
                    if (tableDecorator != null)
                    {
                        struct.decoratedValue = tableDecorator.displayGroupedValue(struct.bodyValue,
                                groupingValue, header.getColumnNumber());
                    }
                    else if (groupingValue == GROUP_END || groupingValue == GROUP_NO_CHANGE)
                    {
                        struct.decoratedValue = TagConstants.EMPTY_STRING;
                    }
                }
                structsForRow.add(struct);
            }

            if (tableDecorator != null)
            {
                writeDecoratedRowStart(model);
            }
            // open row
            writeRowOpener(currentRow);

            for (Iterator iterator = structsForRow.iterator(); iterator.hasNext();)
            {
                CellStruct struct = (CellStruct) iterator.next();
                writeColumnOpener(struct.column);
                writeColumnValue(struct.decoratedValue, struct.column);
                writeColumnCloser(struct.column);
            }

            if (model.isEmpty())
            {
                if (log.isDebugEnabled())
                {
                    log.debug("[" + this.id + "] table has no columns");
                }
                // render empty row
                writeRowWithNoColumns(currentRow.getObject().toString());
            }

            // close row
            writeRowCloser(currentRow);
            // decorate row finish
            if (model.getTableDecorator() != null)
            {
                writeDecoratedRowFinish(model);
            }
        }

        // render empty list message
        if (model.getRowListPage().size() == 0)
        {
            writeEmptyListRowMessage(MessageFormat.format(
                model.getProperties().getEmptyListRowMessage(),
                new Object[]{new Integer(model.getNumberOfColumns())}));
        }
    }

    /*
     * writeTableBody callback methods
     */

    /**
     * Called by writeTableBody to write to decorate the table.
     * @param model The table model for which the content is written.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writeDecoratedRowStart(TableModel model) throws Exception;

    /**
     * Called by writeTableBody to write the start of the row structure.
     * @param row The table row for which the content is written.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writeRowOpener(Row row) throws Exception;

    /**
     * Called by writeTableBody to write the start of the column structure.
     * @param column The table column for which the content is written.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writeColumnOpener(Column column) throws Exception;

    /**
     * Called by writeTableBody to write a column's value.
     * @param value The column value.
     * @param column The table column for which the content is written.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writeColumnValue(Object value, Column column) throws Exception;

    /**
     * Called by writeTableBody to write the end of the column structure.
     * @param column The table column for which the content is written.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writeColumnCloser(Column column) throws Exception;

    /**
     * Called by writeTableBody to write a row that has no columns.
     * @param value The row value.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writeRowWithNoColumns(String value) throws Exception;

    /**
     * Called by writeTableBody to write the end of the row structure.
     * @param row The table row for which the content is written.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writeRowCloser(Row row) throws Exception;

    /**
     * Called by writeTableBody to decorate the table.
     * @param model The table model for which the content is written.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writeDecoratedRowFinish(TableModel model) throws Exception;

    /**
     * Called by writeTableBody to write a message explaining that the row contains no data.
     * @param message The message explaining that the row contains no data.
     * @throws Exception if it encounters an error while writing.
     */
    protected abstract void writeEmptyListRowMessage(String message) throws Exception;

    /**
     * This takes a column value and grouping index as the argument. It then groups the column and returns the
     * appropriate string back to the caller.
     * @param value String current cell value
     * @return String
     */
    protected short groupColumns(String value, String previous, String next, int currentGroup)
    {

        short groupingKey = GROUP_NO_CHANGE;
        if (lowestEndedGroup < currentGroup)
        {
            // if a lower group has ended, cascade so that all subgroups end as well
            groupingKey += GROUP_END;
        }
        else if (next == null || !ObjectUtils.equals(value, next))
        {
            // at the end of the list
            groupingKey += GROUP_END;
            lowestEndedGroup = currentGroup;
        }

        if (lowestStartedGroup < currentGroup)
        {
            // if a lower group has started, cascade so that all subgroups restart as well
            groupingKey += GROUP_START;
        }
        else if (previous == null || !ObjectUtils.equals(value, previous))
        {
            // At the start of the list
            groupingKey += GROUP_START;
            lowestStartedGroup = currentGroup;
        }
        return groupingKey;
    }

    static class CellStruct
    {

        Column column;

        String bodyValue;

        String decoratedValue;

        public CellStruct(Column theColumn, String bodyValueParam)
        {
            this.column = theColumn;
            this.bodyValue = bodyValueParam;
        }
    }
}
