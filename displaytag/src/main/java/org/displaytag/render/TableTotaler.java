/*
 * Copyright (C) 2002-2022 Fabrizio Giustina, the Displaytag team
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.displaytag.exception.DecoratorException;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.model.Column;
import org.displaytag.model.ColumnIterator;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.TableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class just keeps a running grouped total. It does not output anything; it is the responsibility of the exporter
 * or of the decorator to actually output the results.
 *
 * @author rapruitt Date: May 21, 2010 Time: 9:17:43 PM
 */
public class TableTotaler {

    /** The logger. */
    protected Logger logger = LoggerFactory.getLogger(TableTotaler.class);

    /** The Constant NULL. */
    public static final TableTotaler NULL = new TableTotaler();

    /** The first row for each group. */
    protected Map<Integer, Integer> firstRowForEachGroup = new HashMap<>();

    /** The how many groups. */
    protected int howManyGroups = 0;

    /** The current row number. */
    protected Integer currentRowNumber = 0;

    /** The table model. */
    protected TableModel tableModel;

    /** The opened columns. */
    List<Integer> openedColumns = new ArrayList<>(); // in excel, i need to know which ones are currently open; in xml,
                                                     // just what has just opened

    /** The grouping values by column. */
    TreeMap<Integer, String> groupingValuesByColumn = new TreeMap<>(); // in excel, i need to know which ones are
                                                                       // currently open; in xml, just what has just
                                                                       // opened

    /** The closed columns. */
    List<Integer> closedColumns = new ArrayList<>();
    /**
     * Magic constant to indicate that we want the whole list, not just a subgroup.
     */
    public static final Integer WHOLE_TABLE = 0;

    /**
     * Inits the.
     *
     * @param model
     *            the model
     */
    public void init(final TableModel model) {
        this.tableModel = model;
        this.firstRowForEachGroup = new HashMap<>();
        for (final HeaderCell c : model.getHeaderCellList()) {
            if (c.getGroup() > 0) {
                this.firstRowForEachGroup.put(c.getGroup(), 0);
                this.howManyGroups++;
            }
        }

    }

    /**
     * Inits the row.
     *
     * @param currentViewIndex
     *            the current view index
     * @param currentListIndex
     *            the current list index
     */
    public void initRow(final int currentViewIndex, final int currentListIndex) {
        this.openedColumns.clear();
        this.closedColumns.clear();
        this.currentRowNumber = currentListIndex;
    }

    /**
     * As column.
     *
     * @param groupNumber
     *            the group number
     *
     * @return the int
     */
    public int asColumn(final int groupNumber) {
        return groupNumber;
    }

    /**
     * As group.
     *
     * @param columnNumber
     *            the column number
     *
     * @return the int
     */
    public int asGroup(final int columnNumber) {
        return columnNumber;
    }

    /**
     * Start group.
     *
     * @param groupingValue
     *            the grouping value
     * @param groupNumber
     *            the group number
     */
    public void startGroup(final String groupingValue, final int groupNumber) {
        this.openedColumns.add(this.asColumn(groupNumber));
        this.groupingValuesByColumn.put(this.asColumn(groupNumber), groupingValue);
        this.firstRowForEachGroup.put(groupNumber, this.currentRowNumber);
    }

    /**
     * Gets the opened columns.
     *
     * @return the opened columns
     */
    public List<Integer> getOpenedColumns() {
        return new ArrayList<>(this.openedColumns);
    }

    /**
     * Gets the closed columns.
     *
     * @return the closed columns
     */
    public List<Integer> getClosedColumns() {
        return this.closedColumns;
    }

    /**
     * Stop group.
     *
     * @param value
     *            the value
     * @param groupNumber
     *            the group number
     */
    public void stopGroup(final String value, final int groupNumber) {
        this.closedColumns.add(this.asColumn(groupNumber));
    }

    /**
     * Override locally to perform your own math.
     *
     * @param column
     *            the column
     * @param total
     *            the total
     * @param value
     *            the value
     *
     * @return the object
     */
    public Object add(final Column column, final Object total, final Object value) {
        if (value == null) {
            return total;
        }
        if (value instanceof Number) {
            Number oldTotal = (double) 0;
            if (total != null) {
                oldTotal = (Number) total;
            }
            return oldTotal.doubleValue() + ((Number) value).doubleValue();
        } else {
            throw new UnsupportedOperationException(
                    "Cannot add a value of " + value + " in column " + column.getHeaderCell().getTitle());
        }
    }

    /**
     * Override locally to format it yourself.
     *
     * @param cell
     *            the current cell
     * @param total
     *            the current value
     *
     * @return the string
     */
    public String formatTotal(final HeaderCell cell, Object total) {
        if (total == null) {
            total = "";
        }
        return total instanceof String ? (String) total : total.toString();
    }

    /**
     * Gets the total for list.
     *
     * @param window
     *            the window
     * @param columnNumber
     *            the column number
     *
     * @return the total for list
     */
    protected Object getTotalForList(final List<Row> window, final int columnNumber) {
        Object total = null;
        for (final Row row : window) {
            final ColumnIterator columnIterator = row.getColumnIterator(this.tableModel.getHeaderCellList());
            while (columnIterator.hasNext()) {
                final Column column = columnIterator.nextColumn();
                if (column.getHeaderCell().getColumnNumber() == columnNumber) {
                    Object value = null;
                    try {
                        value = column.getValue(false);
                    } catch (ObjectLookupException | DecoratorException e) {
                        this.logger.error("", e);
                    }
                    if (value != null && !"".equals(value)) {
                        total = this.add(column, total, value);
                    }
                }
            }
        }
        return total;
    }

    /**
     * Gets the total for column.
     *
     * @param columnNumber
     *            the column number
     * @param groupNumber
     *            the group number
     *
     * @return the total for column
     */
    public Object getTotalForColumn(final int columnNumber, final int groupNumber) {
        final List<Row> fullList = this.tableModel.getRowListFull();
        Integer startRow = this.firstRowForEachGroup.get(groupNumber);
        final Integer stopRow = this.currentRowNumber + 1;
        if (groupNumber == TableTotaler.WHOLE_TABLE) { // asking for a total for the entire table
            startRow = 0;
        }
        final List<Row> window = fullList.subList(startRow, stopRow);
        return this.getTotalForList(window, columnNumber);
    }

    /**
     * Gets the grouping value.
     *
     * @param columnNumber
     *            the column number
     *
     * @return the grouping value
     */
    public String getGroupingValue(final Integer columnNumber) {
        return this.groupingValuesByColumn.get(columnNumber);
    }

    /**
     * Reset.
     */
    public void reset() {
        this.closedColumns.clear();
        this.openedColumns.clear();
        this.groupingValuesByColumn.clear();
        this.currentRowNumber = 0;
        this.howManyGroups = 0;
        this.firstRowForEachGroup.clear();
    }
}
