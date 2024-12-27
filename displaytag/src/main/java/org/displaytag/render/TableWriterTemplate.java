/*
 * Copyright (C) 2002-2024 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.render;

import jakarta.servlet.jsp.JspException;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.model.Column;
import org.displaytag.model.ColumnIterator;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.RowIterator;
import org.displaytag.model.TableModel;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.properties.TableProperties;
import org.displaytag.util.TagConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A template that encapsulates and drives the construction of a table based on a given table model and configuration.
 * This class is meant to be extended by classes that build tables sharing the same structure, sorting, and grouping,
 * but that write them in different formats and to various destinations. Subclasses must provide the format- and
 * destination-specific implementations of the abstract methods this class calls to build a table. (Background: This
 * class came about because our users wanted to export tables to Excel and PDF just as they were presented in HTML. It
 * originates with the TableTagData.writeHTMLData method, factoring its logic so that it can be re-used by classes that
 * write the tables as PDF, Excel, RTF and other formats. TableTagData.writeHTMLData now calls an HTML extension of this
 * class to write tables in HTML format to a JSP page.)
 */
public abstract class TableWriterTemplate {

    /** The Constant GROUP_START. */
    public static final short GROUP_START = -2;

    /** The Constant GROUP_END. */
    public static final short GROUP_END = 5;

    /** The Constant GROUP_START_AND_END. */
    public static final short GROUP_START_AND_END = 3;

    /** The Constant GROUP_NO_CHANGE. */
    public static final short GROUP_NO_CHANGE = 0;

    /** The Constant NO_RESET_GROUP. */
    protected static final int NO_RESET_GROUP = 42000;

    /**
     * logger.
     */
    private static Logger log = LoggerFactory.getLogger(TableWriterTemplate.class);

    /**
     * Table unique id.
     */
    private String id;

    /** The lowest ended group. */
    int lowestEndedGroup;

    /** The lowest started group. */
    int lowestStartedGroup;

    /**
     * Given a table model, this method creates a table, sorting and grouping it per its configuration, while delegating
     * where and how it writes the table to subclass objects. (Background: This method refactors
     * TableTagData.writeHTMLData method. See above.)
     *
     * @param model
     *            The table model used to build the table.
     * @param id
     *            This table's page id.
     *
     * @throws JspException
     *             if any exception thrown while constructing the tablei, it is caught and rethrown as a JspException.
     *             Extension classes may throw all sorts of exceptions, depending on their respective formats and
     *             destinations.
     */
    public void writeTable(final TableModel model, final String id) throws JspException {
        try {
            // table id used for logging
            this.id = id;

            final TableProperties properties = model.getProperties();

            if (TableWriterTemplate.log.isDebugEnabled()) {
                TableWriterTemplate.log.debug("[" + this.id + "] writeTable called for table [" + this.id + "]");
            }

            // Handle empty table
            final boolean noItems = model.getRowListPage().isEmpty();
            if (noItems && !properties.getEmptyListShowTable()) {
                this.writeEmptyListMessage(properties.getEmptyListMessage());
                return;
            }

            // search result, navigation bar and export links.
            this.writeTopBanner(model);

            // open table
            this.writeTableOpener(model);

            // render caption
            if (model.getCaption() != null) {
                this.writeCaption(model);
            }

            // render headers
            if (model.getProperties().getShowHeader()) {
                this.writeTableHeader(model);
            }

            // render footer prior to body
            if (model.getFooter() != null) {
                this.writePreBodyFooter(model);
            }

            // open table body
            this.writeTableBodyOpener(model);

            // render table body
            this.writeTableBody(model);

            // close table body
            this.writeTableBodyCloser(model);

            // render footer after body
            if (model.getFooter() != null) {
                this.writePostBodyFooter(model);
            }

            // close table
            this.writeTableCloser(model);

            if (model.getTableDecorator() != null) {
                this.writeDecoratedTableFinish(model);
            }

            this.writeBottomBanner(model);

            if (TableWriterTemplate.log.isDebugEnabled()) {
                TableWriterTemplate.log.debug("[" + this.id + "] writeTable end");
            }
        } catch (final Exception e) {
            throw new JspException(e);
        }
    }

    /**
     * Called by writeTable to write a message explaining that the table model contains no data.
     *
     * @param emptyListMessage
     *            A message explaining that the table model contains no data.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writeEmptyListMessage(String emptyListMessage) throws Exception;

    /**
     * Called by writeTable to write a summary of the search result this table reports and the table's pagination
     * interface.
     *
     * @param model
     *            The table model for which the banner is written.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writeTopBanner(TableModel model) throws Exception;

    /**
     * Called by writeTable to write the start of the table structure.
     *
     * @param model
     *            The table model for which the content is written.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writeTableOpener(TableModel model) throws Exception;

    /**
     * Called by writeTable to write the table's caption.
     *
     * @param model
     *            The table model for which the content is written.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writeCaption(TableModel model) throws Exception;

    /**
     * Called by writeTable to write the table's header columns.
     *
     * @param model
     *            The table model for which the content is written.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writeTableHeader(TableModel model) throws Exception;

    /**
     * Called by writeTable to write table footer before table body.
     *
     * @param model
     *            The table model for which the content is written.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writePreBodyFooter(TableModel model) throws Exception;

    /**
     * Called by writeTable to write the start of the table's body.
     *
     * @param model
     *            The table model for which the content is written.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writeTableBodyOpener(TableModel model) throws Exception;

    // protected abstract void writeTableBody(TableModel model);

    /**
     * Called by writeTable to write the end of the table's body.
     *
     * @param model
     *            The table model for which the content is written.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writeTableBodyCloser(TableModel model) throws Exception;

    /**
     * Called by writeTable to write table footer after table body.
     *
     * @param model
     *            The table model for which the content is written.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writePostBodyFooter(TableModel model) throws Exception;

    /**
     * Called by writeTable to write the end of the table's structure.
     *
     * @param model
     *            The table model for which the content is written.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writeTableCloser(TableModel model) throws Exception;

    /**
     * Called by writeTable to decorate the table.
     *
     * @param model
     *            The table model for which the content is written.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writeDecoratedTableFinish(TableModel model) throws Exception;

    /**
     * Called by writeTable to write the table's footer.
     *
     * @param model
     *            The table model for which the content is written.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writeBottomBanner(TableModel model) throws Exception;

    /**
     * Given a table model, writes the table body content, sorting and grouping it per its configuration, while
     * delegating where and how it writes to subclass objects. (Background: This method refactors
     * TableTagData.writeTableBody method. See above.)
     *
     * @param model
     *            The table model used to build the table body.
     *
     * @throws Exception
     *             if an error is encountered while writing the table body.
     */
    protected void writeTableBody(final TableModel model) throws Exception {
        // Ok, start bouncing through our list (only the visible part)
        boolean fullList = false;
        if (!MediaTypeEnum.HTML.equals(model.getMedia()) && model.getProperties().getExportFullList()) {
            fullList = true;
        }
        final RowIterator rowIterator = model.getRowIterator(fullList);
        TableTotaler totalsTableDecorator = model.getTotaler();
        if (totalsTableDecorator == null) {
            totalsTableDecorator = TableTotaler.NULL;
        }

        // iterator on rows
        final TableDecorator tableDecorator = model.getTableDecorator();
        Row previousRow = null;
        Row currentRow = null;
        Row nextRow = null;
        final Map<Integer, CellStruct> previousRowValues = new HashMap<>(10);
        final Map<Integer, CellStruct> currentRowValues = new HashMap<>(10);
        final Map<Integer, CellStruct> nextRowValues = new HashMap<>(10);

        while (nextRow != null || rowIterator.hasNext()) {
            // The first pass
            if (currentRow == null) {
                currentRow = rowIterator.next();
            } else {
                previousRow = currentRow;
                currentRow = nextRow;
            }

            if (previousRow != null) {
                previousRowValues.putAll(currentRowValues);
            }
            if (!nextRowValues.isEmpty()) {
                currentRowValues.putAll(nextRowValues);
            }
            // handle the first pass
            else {
                final ColumnIterator columnIterator = currentRow.getColumnIterator(model.getHeaderCellList());
                // iterator on columns
                if (TableWriterTemplate.log.isDebugEnabled()) {
                    TableWriterTemplate.log.debug(" creating ColumnIterator on " + model.getHeaderCellList());
                }
                while (columnIterator.hasNext()) {
                    final Column column = columnIterator.nextColumn();

                    // Get the value to be displayed for the column
                    column.initialize();

                    @SuppressWarnings("deprecation")
                    final String cellvalue = MediaTypeEnum.HTML.equals(model.getMedia())
                            ? column.getChoppedAndLinkedValue()
                            : ObjectUtils.toString(column.getValue(true));

                    final CellStruct struct = new CellStruct(column, cellvalue);
                    currentRowValues.put(Integer.valueOf(column.getHeaderCell().getColumnNumber()), struct);
                }
            }

            nextRowValues.clear();
            // Populate the next row values
            nextRow = rowIterator.hasNext() ? rowIterator.next() : null;
            if (nextRow != null) {
                final ColumnIterator columnIterator = nextRow.getColumnIterator(model.getHeaderCellList());
                // iterator on columns
                if (TableWriterTemplate.log.isDebugEnabled()) {
                    TableWriterTemplate.log.debug(" creating ColumnIterator on " + model.getHeaderCellList());
                }
                while (columnIterator.hasNext()) {
                    final Column column = columnIterator.nextColumn();
                    column.initialize();
                    // Get the value to be displayed for the column

                    @SuppressWarnings("deprecation")
                    final String cellvalue = MediaTypeEnum.HTML.equals(model.getMedia())
                            ? column.getChoppedAndLinkedValue()
                            : ObjectUtils.toString(column.getValue(true));

                    final CellStruct struct = new CellStruct(column, cellvalue);
                    nextRowValues.put(Integer.valueOf(column.getHeaderCell().getColumnNumber()), struct);
                }
            }
            // now we are going to create the current row; reset the decorator to the current row
            if (tableDecorator != null) {
                tableDecorator.initRow(currentRow.getObject(), currentRow.getRowNumber(),
                        currentRow.getRowNumber() + rowIterator.getPageOffset());
            }
            if (totalsTableDecorator != null) {
                totalsTableDecorator.initRow(currentRow.getRowNumber(),
                        currentRow.getRowNumber() + rowIterator.getPageOffset());
            }

            final ArrayList<CellStruct> structsForRow = new ArrayList<>(model.getHeaderCellList().size());
            this.lowestEndedGroup = TableWriterTemplate.NO_RESET_GROUP;
            this.lowestStartedGroup = TableWriterTemplate.NO_RESET_GROUP;

            for (final HeaderCell header : model.getHeaderCellList()) {

                // Get the value to be displayed for the column
                final CellStruct struct = currentRowValues.get(Integer.valueOf(header.getColumnNumber()));
                struct.decoratedValue = struct.bodyValue;
                // Check and see if there is a grouping transition. If there is, then notify the decorator
                if (header.getGroup() != -1) {
                    final CellStruct prior = previousRowValues.get(Integer.valueOf(header.getColumnNumber()));
                    final CellStruct next = nextRowValues.get(Integer.valueOf(header.getColumnNumber()));
                    // Why npe?
                    final String priorBodyValue = prior != null ? prior.bodyValue : null;
                    final String nextBodyValue = next != null ? next.bodyValue : null;
                    final short groupingValue = this.groupColumns(struct.bodyValue, priorBodyValue, nextBodyValue,
                            header.getGroup());

                    if (tableDecorator != null || totalsTableDecorator != null) {
                        switch (groupingValue) {
                            case GROUP_START:
                                totalsTableDecorator.startGroup(struct.bodyValue, header.getGroup());
                                if (tableDecorator != null) {
                                    tableDecorator.startOfGroup(struct.bodyValue, header.getGroup());
                                }
                                break;
                            case GROUP_END:
                                totalsTableDecorator.stopGroup(struct.bodyValue, header.getGroup());
                                if (tableDecorator != null) {
                                    tableDecorator.endOfGroup(struct.bodyValue, header.getGroup());
                                }
                                break;
                            case GROUP_START_AND_END:
                                totalsTableDecorator.startGroup(struct.bodyValue, header.getGroup());
                                if (tableDecorator != null) {
                                    tableDecorator.startOfGroup(struct.bodyValue, header.getGroup());
                                }
                                totalsTableDecorator.stopGroup(struct.bodyValue, header.getGroup());
                                if (tableDecorator != null) {
                                    tableDecorator.endOfGroup(struct.bodyValue, header.getGroup());
                                }

                                break;
                            default:
                                break;
                        }
                    }
                    if (tableDecorator != null) {
                        struct.decoratedValue = tableDecorator.displayGroupedValue(struct.bodyValue, groupingValue,
                                header.getColumnNumber());
                    } else if (groupingValue == TableWriterTemplate.GROUP_END
                            || groupingValue == TableWriterTemplate.GROUP_NO_CHANGE) {
                        struct.decoratedValue = TagConstants.EMPTY_STRING;
                    }
                }
                structsForRow.add(struct);
            }

            if (totalsTableDecorator != null) {
                this.writeSubgroupStart(model);
            }
            if (tableDecorator != null) {
                this.writeDecoratedRowStart(model);
            }
            // open row
            this.writeRowOpener(currentRow);

            for (final CellStruct struct : structsForRow) {
                this.writeColumnOpener(struct.column);
                this.writeColumnValue(struct.decoratedValue, struct.column);
                this.writeColumnCloser(struct.column);
            }

            if (model.isEmpty()) {
                if (TableWriterTemplate.log.isDebugEnabled()) {
                    TableWriterTemplate.log.debug("[" + this.id + "] table has no columns");
                }
                // render empty row
                this.writeRowWithNoColumns(currentRow.getObject().toString());
            }

            // close row
            this.writeRowCloser(currentRow);
            // decorate row finish
            if (model.getTableDecorator() != null) {
                this.writeDecoratedRowFinish(model);
            }
            if (model.getTotaler() != null) {
                this.writeSubgroupStop(model);
            }
        }
        // how is this really going to work?
        // the totaler is notified whenever we start or stop a group, and the totaler tracks the current state of the
        // the totals; the totaler writes nothing
        // when the row is finished, it is the responsibility of the decorator or exporter to ask for the totaler total
        // and write it when the row is finished,

        // render empty list message
        if (model.getRowListPage().isEmpty()) {
            this.writeEmptyListRowMessage(
                    new MessageFormat(model.getProperties().getEmptyListRowMessage(), model.getProperties().getLocale())
                            .format(new Object[] { Integer.valueOf(model.getNumberOfColumns()) }));
        }
    }

    /*
     * writeTableBody callback methods
     */

    /**
     * Called by writeTableBody to write to decorate the table.
     *
     * @param model
     *            The table model for which the content is written.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writeDecoratedRowStart(TableModel model) throws Exception;

    /**
     * Write subgroup start.
     *
     * @param model
     *            the model
     *
     * @throws Exception
     *             the exception
     */
    protected void writeSubgroupStart(final TableModel model) throws Exception {
    }

    /**
     * Write subgroup stop.
     *
     * @param model
     *            the model
     *
     * @throws Exception
     *             the exception
     */
    protected void writeSubgroupStop(final TableModel model) throws Exception {
    }

    /**
     * Called by writeTableBody to write the start of the row structure.
     *
     * @param row
     *            The table row for which the content is written.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writeRowOpener(Row row) throws Exception;

    /**
     * Called by writeTableBody to write the start of the column structure.
     *
     * @param column
     *            The table column for which the content is written.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writeColumnOpener(Column column) throws Exception;

    /**
     * Called by writeTableBody to write a column's value.
     *
     * @param value
     *            The column value.
     * @param column
     *            The table column for which the content is written.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writeColumnValue(Object value, Column column) throws Exception;

    /**
     * Called by writeTableBody to write the end of the column structure.
     *
     * @param column
     *            The table column for which the content is written.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writeColumnCloser(Column column) throws Exception;

    /**
     * Called by writeTableBody to write a row that has no columns.
     *
     * @param value
     *            The row value.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writeRowWithNoColumns(String value) throws Exception;

    /**
     * Called by writeTableBody to write the end of the row structure.
     *
     * @param row
     *            The table row for which the content is written.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writeRowCloser(Row row) throws Exception;

    /**
     * Called by writeTableBody to decorate the table.
     *
     * @param model
     *            The table model for which the content is written.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writeDecoratedRowFinish(TableModel model) throws Exception;

    /**
     * Called by writeTableBody to write a message explaining that the row contains no data.
     *
     * @param message
     *            The message explaining that the row contains no data.
     *
     * @throws Exception
     *             if it encounters an error while writing.
     */
    protected abstract void writeEmptyListRowMessage(String message) throws Exception;

    /**
     * This takes a column value and grouping index as the argument. It then groups the column and returns the
     * appropriate string back to the caller.
     *
     * @param value
     *            String current cell value
     * @param previous
     *            the previous
     * @param next
     *            the next
     * @param currentGroup
     *            the current group
     *
     * @return String
     */
    @SuppressWarnings("deprecation")
    protected short groupColumns(final String value, final String previous, final String next, final int currentGroup) {

        short groupingKey = TableWriterTemplate.GROUP_NO_CHANGE;
        if (this.lowestEndedGroup < currentGroup) {
            // if a lower group has ended, cascade so that all subgroups end as well
            groupingKey += TableWriterTemplate.GROUP_END;
        } else if (next == null || !ObjectUtils.equals(value, next)) {
            // at the end of the list
            groupingKey += TableWriterTemplate.GROUP_END;
            this.lowestEndedGroup = currentGroup;
        }

        if (this.lowestStartedGroup < currentGroup) {
            // if a lower group has started, cascade so that all subgroups restart as well
            groupingKey += TableWriterTemplate.GROUP_START;
        } else if (previous == null || !ObjectUtils.equals(value, previous)) {
            // At the start of the list
            groupingKey += TableWriterTemplate.GROUP_START;
            this.lowestStartedGroup = currentGroup;
        }
        return groupingKey;
    }

    /**
     * The Class CellStruct.
     */
    static class CellStruct {

        /** The column. */
        Column column;

        /** The body value. */
        String bodyValue;

        /** The decorated value. */
        String decoratedValue;

        /**
         * Instantiates a new cell struct.
         *
         * @param theColumn
         *            the the column
         * @param bodyValueParam
         *            the body value param
         */
        public CellStruct(final Column theColumn, final String bodyValueParam) {
            this.column = theColumn;
            this.bodyValue = bodyValueParam;
        }
    }
}
