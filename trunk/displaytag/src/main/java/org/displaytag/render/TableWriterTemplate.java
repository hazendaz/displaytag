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
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.model.Column;
import org.displaytag.model.ColumnIterator;
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
 * @version $Revision$ ($Author$)
 */
public abstract class TableWriterTemplate
{

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(TableWriterTemplate.class);

    /**
     * Holds the previous row columns values.
     */
    private Map previousRow;

    /**
     * Holds the next row columns values.
     */
    private Map nextRow;

    /**
     * Table unique id.
     */
    private String id;

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

            // variables to hold the previous row columns values.
            this.previousRow = new Hashtable(10);

            // variables to hold next row column values.
            this.nextRow = new Hashtable(10);

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

    /*
     * writeTable callback methods
     */

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
        while (rowIterator.hasNext())
        {
            Row row = rowIterator.next();
            if (log.isDebugEnabled())
            {
                log.debug("[" + this.id + "] rowIterator.next()=" + row);
            }
            // decorate row start
            if (model.getTableDecorator() != null)
            {
                writeDecoratedRowStart(model);
            }

            // open row
            writeRowOpener(row);

            // iterator on columns
            if (log.isDebugEnabled())
            {
                log.debug("[" + this.id + "] creating ColumnIterator on " + model.getHeaderCellList());
            }
            ColumnIterator columnIterator = row.getColumnIterator(model.getHeaderCellList());

            while (columnIterator.hasNext())
            {
                Column column = columnIterator.nextColumn();

                // open column
                writeColumnOpener(column);
                // get the value to be displayed for the column
                String value = column.getChoppedAndLinkedValue();

                // check if column is grouped
                if (column.getGroup() != -1)
                {
                    value = this.groupColumns(value, column.getGroup());
                }

                // render column value
                writeColumnValue(value, column);
                // close column
                writeColumnCloser(column);
            }

            // no columns?
            if (model.isEmpty())
            {
                if (log.isDebugEnabled())
                {
                    log.debug("[" + this.id + "] table has no columns");
                }
                // render empty row
                writeRowWithNoColumns(row.getObject().toString());
            }

            // close row
            writeRowCloser(row);

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
    protected abstract void writeColumnValue(String value, Column column) throws Exception;

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
     * Given a column value and grouping index, this method groups the column and returns the appropriate string back to
     * the caller. (Background: This method refactors TableTagData.writeTableBody method. See above.)
     * @param value String
     * @param group int
     * @return String
     */
    private String groupColumns(String value, int group)
    {
        if ((group == 1) && this.nextRow.size() > 0)
        {
            // we are at the begining of the next row so copy the contents from nextRow to the previousRow.
            this.previousRow.clear();
            this.previousRow.putAll(this.nextRow);
            this.nextRow.clear();
        }

        if (!this.nextRow.containsKey(new Integer(group)))
        {
            // Key not found in the nextRow so adding this key now...
            // remember all the old values.
            this.nextRow.put(new Integer(group), value);
        }

        // Start comparing the value we received, along with the grouping index.
        // if no matching value is found in the previous row then return the value.
        // if a matching value is found then this value should not get printed out
        // so return an empty String
        if (this.previousRow.containsKey(new Integer(group)))
        {
            for (int j = 1; j <= group; j++)
            {

                if (!((String) this.previousRow.get(new Integer(j))).equals((this.nextRow.get(new Integer(j)))))
                {
                    // no match found so return this value back to the caller.
                    return value;
                }
            }
        }

        // This is used, for when there is no data in the previous row,
        // It gets used only the first time.
        if (this.previousRow.size() == 0)
        {
            return value;
        }

        // There is corresponding value in the previous row
        // this value doesn't need to be printed, return an empty String
        return TagConstants.EMPTY_STRING;
    }
}
