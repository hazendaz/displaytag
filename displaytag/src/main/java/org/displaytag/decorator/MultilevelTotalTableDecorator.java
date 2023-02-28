/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.decorator;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.exception.DecoratorException;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.model.Column;
import org.displaytag.model.ColumnIterator;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.TableModel;
import org.displaytag.util.TagConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A TableDecorator that, in conjunction with totaled and grouped columns, produces multi level subtotals on arbitrary
 * String groupings. Use it directly, subclass it, or use it as an example to better meet your local needs.
 *
 * @author rapruitt
 * @author Fabrizio Giustina
 */
public class MultilevelTotalTableDecorator extends TableDecorator {

    /**
     * If there are no columns that are totaled, we should not issue a totals row.
     */
    private final boolean containsTotaledColumns = true;

    /**
     * No current reset group.
     */
    private static final int NO_RESET_GROUP = 4200;

    /**
     * Maps the groups to their current totals.
     */
    private final Map<Integer, GroupTotals> groupNumberToGroupTotal = new HashMap<>();

    /**
     * The deepest reset group. Resets on an outer group will force any deeper groups to reset as well.
     */
    private int deepestResetGroup = MultilevelTotalTableDecorator.NO_RESET_GROUP;

    /**
     * Controls when the subgroup is ended.
     */
    protected int innermostGroup;

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(MultilevelTotalTableDecorator.class);

    /**
     * CSS class applied to grand total totals.
     */
    protected String grandTotalSum = "grandtotal-sum";

    /**
     * CSS class applied to grand total cells where the column is not totaled.
     */
    protected String grandTotalNoSum = "grandtotal-nosum";

    /**
     * CSS class applied to grand total lablels.
     */
    protected String grandTotalLabel = "grandtotal-label";

    /**
     * Grandtotal description.
     */
    protected String grandTotalDescription = "Grand Total";

    /**
     * CSS class appplied to subtotal headers.
     */
    private String subtotalHeaderClass = "subtotal-header";

    /**
     * CSS class applied to subtotal labels.
     */
    private String subtotalLabelClass = "subtotal-label";

    /**
     * Message format for subtotal descriptions.
     */
    private MessageFormat subtotalDesc = new MessageFormat("{0} Total");

    /**
     * CSS class applied to subtotal totals.
     */
    private String subtotalValueClass = "subtotal-sum";

    /**
     * Holds the header rows and their content for a particular group.
     */
    private final List<String> headerRows = new ArrayList<>(5);

    /**
     * Inits the.
     *
     * @param context
     *            the context
     * @param decorated
     *            the decorated
     * @param model
     *            the model
     */
    @Override
    public void init(final PageContext context, final Object decorated, final TableModel model) {
        super.init(context, decorated, model);
        final List<HeaderCell> headerCells = model.getHeaderCellList();
        // go through each column, looking for grouped columns; add them to the group number map
        for (final HeaderCell headerCell : headerCells) {
            if (headerCell.getGroup() > 0) {
                final GroupTotals groupTotals = new GroupTotals(headerCell.getColumnNumber());
                groupTotals.setStartRow(this.tableModel.getPageOffset());
                this.groupNumberToGroupTotal.put(Integer.valueOf(headerCell.getGroup()), groupTotals);
                if (headerCell.getGroup() > this.innermostGroup) {
                    this.innermostGroup = headerCell.getGroup();
                }
            }
        }
    }

    /**
     * Gets the grand total description.
     *
     * @return the grand total description
     */
    public String getGrandTotalDescription() {
        return this.grandTotalDescription;
    }

    /**
     * Sets the grand total description.
     *
     * @param grandTotalDescription
     *            the new grand total description
     */
    public void setGrandTotalDescription(final String grandTotalDescription) {
        this.grandTotalDescription = grandTotalDescription;
    }

    /**
     * The pattern to use to generate the subtotal labels. The grouping value of the cell will be the first arg. The
     * default value is "{0} Total".
     *
     * @param pattern
     *            the pattern
     * @param locale
     *            the locale
     */
    public void setSubtotalLabel(final String pattern, final Locale locale) {
        this.subtotalDesc = new MessageFormat(pattern, locale);
    }

    /**
     * Gets the grand total label.
     *
     * @return the grand total label
     */
    public String getGrandTotalLabel() {
        return this.grandTotalLabel;
    }

    /**
     * Gets the grand total sum.
     *
     * @return the grand total sum
     */
    public String getGrandTotalSum() {
        return this.grandTotalSum;
    }

    /**
     * Gets the grand total no sum.
     *
     * @return the grand total no sum
     */
    public String getGrandTotalNoSum() {
        return this.grandTotalNoSum;
    }

    /**
     * Sets the grand total no sum.
     *
     * @param grandTotalNoSum
     *            the new grand total no sum
     */
    public void setGrandTotalNoSum(final String grandTotalNoSum) {
        this.grandTotalNoSum = grandTotalNoSum;
    }

    /**
     * Sets the grand total sum.
     *
     * @param grandTotalSum
     *            the new grand total sum
     */
    public void setGrandTotalSum(final String grandTotalSum) {
        this.grandTotalSum = grandTotalSum;
    }

    /**
     * Sets the grand total label.
     *
     * @param grandTotalLabel
     *            the new grand total label
     */
    public void setGrandTotalLabel(final String grandTotalLabel) {
        this.grandTotalLabel = grandTotalLabel;
    }

    /**
     * Gets the subtotal value class.
     *
     * @return the subtotal value class
     */
    public String getSubtotalValueClass() {
        return this.subtotalValueClass;
    }

    /**
     * Sets the subtotal value class.
     *
     * @param subtotalValueClass
     *            the new subtotal value class
     */
    public void setSubtotalValueClass(final String subtotalValueClass) {
        this.subtotalValueClass = subtotalValueClass;
    }

    /**
     * Gets the subtotal label class.
     *
     * @return the subtotal label class
     */
    public String getSubtotalLabelClass() {
        return this.subtotalLabelClass;
    }

    /**
     * Sets the subtotal label class.
     *
     * @param subtotalLabelClass
     *            the new subtotal label class
     */
    public void setSubtotalLabelClass(final String subtotalLabelClass) {
        this.subtotalLabelClass = subtotalLabelClass;
    }

    /**
     * Gets the subtotal header class.
     *
     * @return the subtotal header class
     */
    public String getSubtotalHeaderClass() {
        return this.subtotalHeaderClass;
    }

    /**
     * Sets the subtotal header class.
     *
     * @param subtotalHeaderClass
     *            the new subtotal header class
     */
    public void setSubtotalHeaderClass(final String subtotalHeaderClass) {
        this.subtotalHeaderClass = subtotalHeaderClass;
    }

    /**
     * Start of group.
     *
     * @param value
     *            the value
     * @param group
     *            the group
     */
    @Override
    public void startOfGroup(final String value, final int group) {
        if (this.containsTotaledColumns) {
            final StringBuilder tr = new StringBuilder();
            tr.append("<tr>");
            final GroupTotals groupTotals = this.groupNumberToGroupTotal.get(Integer.valueOf(group));
            final int myColumnNumber = groupTotals.columnNumber;
            for (int i = 0; i < myColumnNumber; i++) {
                tr.append("<td></td>\n");
            }
            tr.append("<td class=\"").append(this.getSubtotalHeaderClass()).append(" group-").append(group)
                    .append("\" >");
            tr.append(value).append("</td>\n");
            final List<HeaderCell> headerCells = this.tableModel.getHeaderCellList();
            for (int i = myColumnNumber; i < headerCells.size() - 1; i++) {
                tr.append("<td></td>\n");
            }
            tr.append("</tr>\n");
            this.headerRows.add(tr.toString());
        }
    }

    /**
     * Display grouped value.
     *
     * @param value
     *            the value
     * @param groupingStatus
     *            the grouping status
     * @param columnNumber
     *            the column number
     *
     * @return the string
     */
    @Override
    public String displayGroupedValue(final String value, final short groupingStatus, final int columnNumber) {
        return "";
    }

    /**
     * Start row.
     *
     * @return the string
     */
    @Override
    public String startRow() {
        final StringBuilder sb = new StringBuilder();
        for (final String string : this.headerRows) {
            sb.append(string);
        }
        return sb.toString();
    }

    /**
     * End of group.
     *
     * @param value
     *            the value
     * @param groupNumber
     *            the group number
     */
    @Override
    public void endOfGroup(final String value, final int groupNumber) {
        if (this.deepestResetGroup > groupNumber) {
            this.deepestResetGroup = groupNumber;
        }
    }

    /**
     * Finish row.
     *
     * @return the string
     */
    @Override
    public String finishRow() {
        String returnValue = "";
        if (this.containsTotaledColumns) {
            if (this.innermostGroup > 0 && this.deepestResetGroup != MultilevelTotalTableDecorator.NO_RESET_GROUP) {
                final StringBuilder out = new StringBuilder();
                // Starting with the deepest group, print the current total and reset. Do not reset unaffected groups.
                for (int i = this.innermostGroup; i >= this.deepestResetGroup; i--) {
                    final Integer groupNumber = Integer.valueOf(i);

                    final GroupTotals totals = this.groupNumberToGroupTotal.get(groupNumber);
                    if (totals == null) {
                        this.logger.warn("There is a gap in the defined groups - no group defined for {}", groupNumber);
                        continue;
                    }
                    totals.printTotals(this.getListIndex(), out);
                    this.finishGroup(totals.getColumnNumber(), out);
                    totals.setStartRow(this.getListIndex() + 1);
                }
                returnValue = out.toString();
            } else {
                returnValue = null;
            }
            this.deepestResetGroup = MultilevelTotalTableDecorator.NO_RESET_GROUP;
            this.headerRows.clear();
            if (this.isLastRow()) {
                returnValue = StringUtils.defaultString(returnValue);
                returnValue += this.totalAllRows();
            }
        }
        return returnValue;
    }

    /**
     * Finish group.
     *
     * @param columnNumber
     *            the column number
     * @param out
     *            the out
     */
    protected void finishGroup(final int columnNumber, final StringBuilder out) {
        // Not Implemented
    }

    /**
     * Issue a grand total row at the bottom.
     *
     * @return the suitable string
     */
    protected String totalAllRows() {
        if (!this.containsTotaledColumns) {
            return "";
        }
        final List<HeaderCell> headerCells = this.tableModel.getHeaderCellList();
        final StringBuilder output = new StringBuilder();
        final int currentRow = this.getListIndex();
        output.append(TagConstants.TAG_OPEN + TagConstants.TAGNAME_ROW + " class=\"grandtotal-row\""
                + TagConstants.TAG_CLOSE);
        boolean first = true;
        for (final HeaderCell headerCell : headerCells) {
            if (first) {
                output.append(this.getTotalsTdOpen(headerCell, this.getGrandTotalLabel()));
                output.append(this.getGrandTotalDescription());
                first = false;
            } else if (headerCell.isTotaled()) {
                // a total if the column should be totaled
                final Object total = this.getTotalForColumn(headerCell.getColumnNumber(),
                        this.tableModel.getPageOffset(), currentRow);
                output.append(this.getTotalsTdOpen(headerCell, this.getGrandTotalSum()));
                output.append(this.formatTotal(headerCell, total));
            } else {
                // blank, if it is not a totals column
                output.append(this.getTotalsTdOpen(headerCell, this.getGrandTotalNoSum()));
            }
            output.append(TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_COLUMN + TagConstants.TAG_CLOSE);
        }
        output.append("\n</tr>\n");

        return output.toString();
    }

    /**
     * Gets the cell value.
     *
     * @param columnNumber
     *            the column number
     * @param rowNumber
     *            the row number
     *
     * @return the cell value
     */
    protected String getCellValue(final int columnNumber, final int rowNumber) {
        final List<Row> fullList = this.tableModel.getRowListFull();
        final Row row = fullList.get(rowNumber);
        final ColumnIterator columnIterator = row.getColumnIterator(this.tableModel.getHeaderCellList());
        while (columnIterator.hasNext()) {
            final Column column = columnIterator.nextColumn();
            if (column.getHeaderCell().getColumnNumber() == columnNumber) {
                try {
                    column.initialize();
                    return column.getChoppedAndLinkedValue();
                } catch (ObjectLookupException | DecoratorException e) {
                    this.logger.error("Error: {}", e.getMessage(), e);
                    throw new RuntimeException("Error: " + e.getMessage(), e);
                }
            }
        }
        throw new RuntimeException("Unable to find column " + columnNumber + " in the list of columns");
    }

    /**
     * Gets the total for column.
     *
     * @param columnNumber
     *            the column number
     * @param startRow
     *            the start row
     * @param stopRow
     *            the stop row
     *
     * @return the total for column
     */
    protected Object getTotalForColumn(final int columnNumber, final int startRow, final int stopRow) {
        final List<Row> fullList = this.tableModel.getRowListFull();
        final List<Row> window = fullList.subList(startRow, stopRow + 1);
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
                    if (value != null && !TagConstants.EMPTY_STRING.equals(value)) {
                        total = this.add(column, total, value);
                    }
                }
            }
        }
        return total;
    }

    /**
     * Adds the.
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
    protected Object add(final Column column, final Object total, final Object value) {
        if (value == null) {
            return total;
        }
        if (value instanceof Number) {
            Number oldTotal = Double.valueOf(0);
            if (total != null) {
                oldTotal = (Number) total;
            }
            return Double.valueOf(oldTotal.doubleValue() + ((Number) value).doubleValue());
        } else {
            throw new UnsupportedOperationException(
                    "Cannot add a value of " + value + " in column " + column.getHeaderCell().getTitle());
        }
    }

    /**
     * Gets the totals td open.
     *
     * @param header
     *            the header
     * @param totalClass
     *            the total class
     *
     * @return the totals td open
     */
    public String getTotalsTdOpen(final HeaderCell header, final String totalClass) {

        final Object cssClassObj = header.getHtmlAttributes().get("class");
        final String cssClass = cssClassObj != null ? cssClassObj.toString() : StringUtils.EMPTY;

        final StringBuilder buffer = new StringBuilder();
        buffer.append(TagConstants.TAG_OPEN);
        buffer.append(TagConstants.TAGNAME_COLUMN);
        if (cssClass != null || totalClass != null) {
            buffer.append(" class=\"");

            if (cssClass != null) {
                buffer.append(cssClass);
                if (totalClass != null) {
                    buffer.append(" ");
                }
            }
            if (totalClass != null) {
                buffer.append(totalClass);
            }
            buffer.append("\"");
        }
        buffer.append(TagConstants.TAG_CLOSE);
        return buffer.toString();
    }

    /**
     * Gets the totals row open.
     *
     * @return the totals row open
     */
    public String getTotalsRowOpen() {
        return TagConstants.TAG_OPEN + TagConstants.TAGNAME_ROW + " class=\"subtotal\"" + TagConstants.TAG_CLOSE;
    }

    /**
     * Gets the total row label.
     *
     * @param groupingValue
     *            the grouping value
     *
     * @return the total row label
     */
    public String getTotalRowLabel(final String groupingValue) {
        return this.subtotalDesc.format(new Object[] { groupingValue });
    }

    /**
     * Format total.
     *
     * @param header
     *            the header
     * @param total
     *            the total
     *
     * @return the string
     */
    public String formatTotal(final HeaderCell header, final Object total) {
        Object displayValue = total;
        if (header.getColumnDecorators().length > 0) {
            for (int i = 0; i < header.getColumnDecorators().length; i++) {
                final DisplaytagColumnDecorator decorator = header.getColumnDecorators()[i];
                try {
                    displayValue = decorator.decorate(total, this.getPageContext(), this.tableModel.getMedia());
                } catch (final DecoratorException e) {
                    this.logger.warn(e.getMessage(), e);
                    // ignore, use undecorated value for totals
                }
            }
        }
        return displayValue != null ? displayValue.toString() : "";
    }

    /**
     * The Class GroupTotals.
     */
    class GroupTotals {

        /**
         * The label class.
         */
        protected String totalLabelClass = MultilevelTotalTableDecorator.this.getSubtotalLabelClass();

        /** The row opener. */
        protected String totalsRowOpen = MultilevelTotalTableDecorator.this.getTotalsRowOpen();

        /**
         * The value class.
         */
        protected String totalValueClass = MultilevelTotalTableDecorator.this.getSubtotalValueClass();

        /** The column number. */
        private final int columnNumber;

        /** The first row of current set. */
        private int firstRowOfCurrentSet;

        /**
         * Instantiates a new group totals.
         *
         * @param headerCellColumn
         *            the header cell column
         */
        public GroupTotals(final int headerCellColumn) {
            this.columnNumber = headerCellColumn;
            this.firstRowOfCurrentSet = 0;
        }

        /**
         * Prints the totals.
         *
         * @param currentRow
         *            the current row
         * @param out
         *            the out
         */
        public void printTotals(final int currentRow, final StringBuilder out) {

            // For each column, output:
            final List<HeaderCell> headerCells = MultilevelTotalTableDecorator.this.tableModel.getHeaderCellList();
            if (this.firstRowOfCurrentSet < currentRow) // If there is more than one row, show a total
            {
                out.append(this.totalsRowOpen);
                for (final HeaderCell headerCell : headerCells) {
                    if (this.columnNumber == headerCell.getColumnNumber()) {
                        // a totals label if it is the column for the current group
                        final String currentLabel = MultilevelTotalTableDecorator.this.getCellValue(this.columnNumber,
                                this.firstRowOfCurrentSet);
                        out.append(MultilevelTotalTableDecorator.this.getTotalsTdOpen(headerCell,
                                this.getTotalLabelClass() + " group-" + (this.columnNumber + 1)));
                        out.append(MultilevelTotalTableDecorator.this.getTotalRowLabel(currentLabel));
                    } else if (headerCell.isTotaled()) {
                        // a total if the column should be totaled
                        final Object total = MultilevelTotalTableDecorator.this
                                .getTotalForColumn(headerCell.getColumnNumber(), this.firstRowOfCurrentSet, currentRow);
                        out.append(MultilevelTotalTableDecorator.this.getTotalsTdOpen(headerCell,
                                this.getTotalValueClass() + " group-" + (this.columnNumber + 1)));
                        out.append(MultilevelTotalTableDecorator.this.formatTotal(headerCell, total));
                    } else {
                        // blank, if it is not a totals column
                        final StringBuilder style = new StringBuilder("group-" + (this.columnNumber + 1));
                        if (headerCell.getColumnNumber() < MultilevelTotalTableDecorator.this.innermostGroup) {
                            style.append(" ").append(this.getTotalLabelClass()).append(" ");
                        }
                        out.append(MultilevelTotalTableDecorator.this.getTotalsTdOpen(headerCell, style.toString()));
                    }
                    out.append(TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_COLUMN + TagConstants.TAG_CLOSE);
                }
                out.append("\n</tr>\n");
            }
        }

        /**
         * Gets the column number.
         *
         * @return the column number
         */
        public int getColumnNumber() {
            return this.columnNumber;
        }

        /**
         * Sets the start row.
         *
         * @param i
         *            the new start row
         */
        public void setStartRow(final int i) {
            this.firstRowOfCurrentSet = i;
        }

        /**
         * Gets the total label class.
         *
         * @return the total label class
         */
        public String getTotalLabelClass() {
            return this.totalLabelClass;
        }

        /**
         * Sets the totals row open.
         *
         * @param totalsRowOpen
         *            the new totals row open
         */
        public void setTotalsRowOpen(final String totalsRowOpen) {
            this.totalsRowOpen = totalsRowOpen;
        }

        /**
         * Sets the total label class.
         *
         * @param totalLabelClass
         *            the new total label class
         */
        public void setTotalLabelClass(final String totalLabelClass) {
            this.totalLabelClass = totalLabelClass;
        }

        /**
         * Gets the total value class.
         *
         * @return the total value class
         */
        public String getTotalValueClass() {
            return this.totalValueClass;
        }

        /**
         * Sets the total value class.
         *
         * @param totalValueClass
         *            the new total value class
         */
        public void setTotalValueClass(final String totalValueClass) {
            this.totalValueClass = totalValueClass;
        }
    }
}
