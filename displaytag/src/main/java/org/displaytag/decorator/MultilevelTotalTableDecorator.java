/**
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
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
import java.util.Iterator;
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
 * @author rapruitt
 * @author Fabrizio Giustina
 */
public class MultilevelTotalTableDecorator extends TableDecorator
{

    /**
     * If there are no columns that are totaled, we should not issue a totals row.
     */
    private boolean containsTotaledColumns = true;

    /**
     * No current reset group.
     */
    private static final int NO_RESET_GROUP = 4200;

    /**
     * Maps the groups to their current totals.
     */
    private Map<Integer, GroupTotals> groupNumberToGroupTotal = new HashMap<Integer, GroupTotals>();

    /**
     * The deepest reset group. Resets on an outer group will force any deeper groups to reset as well.
     */
    private int deepestResetGroup = NO_RESET_GROUP;

    /**
     * Controls when the subgroup is ended.
     */
    protected int innermostGroup;

    /**
     * Logger.
     */
    private Logger logger = LoggerFactory.getLogger(MultilevelTotalTableDecorator.class);

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
    private List<StringBuffer> headerRows = new ArrayList<StringBuffer>(5);

    @Override
    public void init(PageContext context, Object decorated, TableModel model)
    {
        super.init(context, decorated, model);
        List<HeaderCell> headerCells = model.getHeaderCellList();
        // go through each column, looking for grouped columns; add them to the group number map
        for (Iterator<HeaderCell> iterator = headerCells.iterator(); iterator.hasNext();)
        {
            HeaderCell headerCell = iterator.next();
            // containsTotaledColumns = containsTotaledColumns || headerCell.isTotaled();
            if (headerCell.getGroup() > 0)
            {
                GroupTotals groupTotals = new GroupTotals(headerCell.getColumnNumber());
                groupTotals.setStartRow(this.tableModel.getPageOffset());
                this.groupNumberToGroupTotal.put(Integer.valueOf(headerCell.getGroup()), groupTotals);
                if (headerCell.getGroup() > this.innermostGroup)
                {
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
    public String getGrandTotalDescription()
    {
        return this.grandTotalDescription;
    }

    /**
     * Sets the grand total description.
     *
     * @param grandTotalDescription the new grand total description
     */
    public void setGrandTotalDescription(String grandTotalDescription)
    {
        this.grandTotalDescription = grandTotalDescription;
    }

    /**
     * The pattern to use to generate the subtotal labels. The grouping value of the cell will be the first arg. The
     * default value is "{0} Total".
     *
     * @param pattern the pattern
     * @param locale the locale
     */
    public void setSubtotalLabel(String pattern, Locale locale)
    {
        this.subtotalDesc = new MessageFormat(pattern, locale);
    }

    /**
     * Gets the grand total label.
     *
     * @return the grand total label
     */
    public String getGrandTotalLabel()
    {
        return this.grandTotalLabel;
    }

    /**
     * Gets the grand total sum.
     *
     * @return the grand total sum
     */
    public String getGrandTotalSum()
    {
        return this.grandTotalSum;
    }

    /**
     * Gets the grand total no sum.
     *
     * @return the grand total no sum
     */
    public String getGrandTotalNoSum()
    {
        return this.grandTotalNoSum;
    }

    /**
     * Sets the grand total no sum.
     *
     * @param grandTotalNoSum the new grand total no sum
     */
    public void setGrandTotalNoSum(String grandTotalNoSum)
    {
        this.grandTotalNoSum = grandTotalNoSum;
    }

    /**
     * Sets the grand total sum.
     *
     * @param grandTotalSum the new grand total sum
     */
    public void setGrandTotalSum(String grandTotalSum)
    {
        this.grandTotalSum = grandTotalSum;
    }

    /**
     * Sets the grand total label.
     *
     * @param grandTotalLabel the new grand total label
     */
    public void setGrandTotalLabel(String grandTotalLabel)
    {
        this.grandTotalLabel = grandTotalLabel;
    }

    /**
     * Gets the subtotal value class.
     *
     * @return the subtotal value class
     */
    public String getSubtotalValueClass()
    {
        return this.subtotalValueClass;
    }

    /**
     * Sets the subtotal value class.
     *
     * @param subtotalValueClass the new subtotal value class
     */
    public void setSubtotalValueClass(String subtotalValueClass)
    {
        this.subtotalValueClass = subtotalValueClass;
    }

    /**
     * Gets the subtotal label class.
     *
     * @return the subtotal label class
     */
    public String getSubtotalLabelClass()
    {
        return this.subtotalLabelClass;
    }

    /**
     * Sets the subtotal label class.
     *
     * @param subtotalLabelClass the new subtotal label class
     */
    public void setSubtotalLabelClass(String subtotalLabelClass)
    {
        this.subtotalLabelClass = subtotalLabelClass;
    }

    /**
     * Gets the subtotal header class.
     *
     * @return the subtotal header class
     */
    public String getSubtotalHeaderClass()
    {
        return this.subtotalHeaderClass;
    }

    /**
     * Sets the subtotal header class.
     *
     * @param subtotalHeaderClass the new subtotal header class
     */
    public void setSubtotalHeaderClass(String subtotalHeaderClass)
    {
        this.subtotalHeaderClass = subtotalHeaderClass;
    }

    @Override
    public void startOfGroup(String value, int group)
    {
        if (this.containsTotaledColumns)
        {
            StringBuffer tr = new StringBuffer();
            tr.append("<tr>");
            GroupTotals groupTotals = this.groupNumberToGroupTotal.get(Integer.valueOf(group));
            int myColumnNumber = groupTotals.columnNumber;
            for (int i = 0; i < myColumnNumber; i++)
            {
                tr.append("<td></td>\n");
            }
            tr.append("<td class=\"").append(getSubtotalHeaderClass()).append(" group-").append(group).append("\" >");
            tr.append(value).append("</td>\n");
            List<HeaderCell> headerCells = this.tableModel.getHeaderCellList();
            for (int i = myColumnNumber; i < headerCells.size() - 1; i++)
            {
                tr.append("<td></td>\n");
            }
            tr.append("</tr>\n");
            this.headerRows.add(tr);
        }
    }

    @Override
    public String displayGroupedValue(String value, short groupingStatus, int columnNumber)
    {
        // if (groupingStatus == TableWriterTemplate.GROUP_START_AND_END && columnNumber > 1)
        // {
        // return value;
        // }
        // else
        // {
        return "";
        // }
    }

    @Override
    public String startRow()
    {
        StringBuffer sb = new StringBuffer();
        for (Iterator<StringBuffer> iterator = this.headerRows.iterator(); iterator.hasNext();)
        {
            StringBuffer stringBuffer = iterator.next();
            sb.append(stringBuffer);
        }
        return sb.toString();
    }

    @Override
    public void endOfGroup(String value, int groupNumber)
    {
        if (this.deepestResetGroup > groupNumber)
        {
            this.deepestResetGroup = groupNumber;
        }
    }

    @Override
    public String finishRow()
    {
        String returnValue = "";
        if (this.containsTotaledColumns)
        {
            if (this.innermostGroup > 0 && this.deepestResetGroup != NO_RESET_GROUP)
            {
                StringBuffer out = new StringBuffer();
                // Starting with the deepest group, print the current total and reset. Do not reset unaffected groups.
                for (int i = this.innermostGroup; i >= this.deepestResetGroup; i--)
                {
                    Integer groupNumber = Integer.valueOf(i);

                    GroupTotals totals = this.groupNumberToGroupTotal.get(groupNumber);
                    if (totals == null)
                    {
                        this.logger.warn("There is a gap in the defined groups - no group defined for " + groupNumber);
                        continue;
                    }
                    totals.printTotals(getListIndex(), out);
                    finishGroup(totals.getColumnNumber(), out);
                    totals.setStartRow(getListIndex() + 1);
                }
                returnValue = out.toString();
            }
            else
            {
                returnValue = null;
            }
            this.deepestResetGroup = NO_RESET_GROUP;
            this.headerRows.clear();
            if (isLastRow())
            {
                returnValue = StringUtils.defaultString(returnValue);
                returnValue += totalAllRows();
            }
        }
        return returnValue;
    }

    /**
     * Finish group.
     *
     * @param columnNumber the column number
     * @param out the out
     */
    protected void finishGroup(int columnNumber, StringBuffer out)
    {
        // Not Implemented
    }

    /**
     * Issue a grand total row at the bottom.
     * @return the suitable string
     */
    protected String totalAllRows()
    {
        if (this.containsTotaledColumns)
        {
            List<HeaderCell> headerCells = this.tableModel.getHeaderCellList();
            StringBuffer output = new StringBuffer();
            int currentRow = getListIndex();
            output.append(TagConstants.TAG_OPEN
                + TagConstants.TAGNAME_ROW
                + " class=\"grandtotal-row\""
                + TagConstants.TAG_CLOSE);
            boolean first = true;
            for (Iterator<HeaderCell> iterator = headerCells.iterator(); iterator.hasNext();)
            {
                HeaderCell headerCell = iterator.next();
                if (first)
                {
                    output.append(getTotalsTdOpen(headerCell, getGrandTotalLabel()));
                    output.append(getGrandTotalDescription());
                    first = false;
                }
                else if (headerCell.isTotaled())
                {
                    // a total if the column should be totaled
                    Object total = getTotalForColumn(
                        headerCell.getColumnNumber(),
                        this.tableModel.getPageOffset(),
                        currentRow);
                    output.append(getTotalsTdOpen(headerCell, getGrandTotalSum()));
                    output.append(formatTotal(headerCell, total));
                }
                else
                {
                    // blank, if it is not a totals column
                    output.append(getTotalsTdOpen(headerCell, getGrandTotalNoSum()));
                }
                output.append(TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_COLUMN + TagConstants.TAG_CLOSE);
            }
            output.append("\n</tr>\n");

            return output.toString();
        }
        else
        {
            return "";
        }
    }

    /**
     * Gets the cell value.
     *
     * @param columnNumber the column number
     * @param rowNumber the row number
     * @return the cell value
     */
    protected String getCellValue(int columnNumber, int rowNumber)
    {
        List<Row> fullList = this.tableModel.getRowListFull();
        Row row = fullList.get(rowNumber);
        ColumnIterator columnIterator = row.getColumnIterator(this.tableModel.getHeaderCellList());
        while (columnIterator.hasNext())
        {
            Column column = columnIterator.nextColumn();
            if (column.getHeaderCell().getColumnNumber() == columnNumber)
            {
                try
                {
                    column.initialize();
                    return column.getChoppedAndLinkedValue();
                }
                catch (ObjectLookupException e)
                {
                    this.logger.error("Error: " + e.getMessage(), e);
                    throw new RuntimeException("Error: " + e.getMessage(), e);
                }
                catch (DecoratorException e)
                {
                    this.logger.error("Error: " + e.getMessage(), e);
                    throw new RuntimeException("Error: " + e.getMessage(), e);
                }
            }
        }
        throw new RuntimeException("Unable to find column " + columnNumber + " in the list of columns");
    }

    /**
     * Gets the total for column.
     *
     * @param columnNumber the column number
     * @param startRow the start row
     * @param stopRow the stop row
     * @return the total for column
     */
    protected Object getTotalForColumn(int columnNumber, int startRow, int stopRow)
    {
        List<Row> fullList = this.tableModel.getRowListFull();
        List<Row> window = fullList.subList(startRow, stopRow + 1);
        Object total = null;
        for (Iterator<Row> iterator = window.iterator(); iterator.hasNext();)
        {
            Row row = iterator.next();
            ColumnIterator columnIterator = row.getColumnIterator(this.tableModel.getHeaderCellList());
            while (columnIterator.hasNext())
            {
                Column column = columnIterator.nextColumn();
                if (column.getHeaderCell().getColumnNumber() == columnNumber)
                {
                    Object value = null;
                    try
                    {
                        value = column.getValue(false);
                    }
                    catch (ObjectLookupException e)
                    {
                        this.logger.error("", e);
                    }
                    catch (DecoratorException e)
                    {
                        this.logger.error("", e);
                    }
                    if (value != null && !TagConstants.EMPTY_STRING.equals(value))
                    {
                        total = add(column, total, value);
                    }
                }
            }
        }
        return total;
    }

    /**
     * Adds the.
     *
     * @param column the column
     * @param total the total
     * @param value the value
     * @return the object
     */
    protected Object add(Column column, Object total, Object value)
    {
        if (value == null)
        {
            return total;
        }
        else if (value instanceof Number)
        {
            Number oldTotal = Double.valueOf(0);
            if (total != null)
            {
                oldTotal = (Number) total;
            }
            return Double.valueOf(oldTotal.doubleValue() + ((Number) value).doubleValue());
        }
        else
        {
            throw new UnsupportedOperationException("Cannot add a value of "
                + value
                + " in column "
                + column.getHeaderCell().getTitle());
        }
    }

    /**
     * Gets the totals td open.
     *
     * @param header the header
     * @param totalClass the total class
     * @return the totals td open
     */
    public String getTotalsTdOpen(HeaderCell header, String totalClass)
    {

        Object cssClassObj = header.getHtmlAttributes().get("class");
        String cssClass = cssClassObj != null ? cssClassObj.toString() : StringUtils.EMPTY;

        StringBuffer buffer = new StringBuffer();
        buffer.append(TagConstants.TAG_OPEN);
        buffer.append(TagConstants.TAGNAME_COLUMN);
        if (cssClass != null || totalClass != null)
        {
            buffer.append(" class=\"");

            if (cssClass != null)
            {
                buffer.append(cssClass);
                if (totalClass != null)
                {
                    buffer.append(" ");
                }
            }
            if (totalClass != null)
            {
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
    public String getTotalsRowOpen()
    {
        return TagConstants.TAG_OPEN + TagConstants.TAGNAME_ROW + " class=\"subtotal\"" + TagConstants.TAG_CLOSE;
    }

    /**
     * Gets the total row label.
     *
     * @param groupingValue the grouping value
     * @return the total row label
     */
    public String getTotalRowLabel(String groupingValue)
    {
        return this.subtotalDesc.format(new Object[]{groupingValue});
    }

    /**
     * Format total.
     *
     * @param header the header
     * @param total the total
     * @return the string
     */
    public String formatTotal(HeaderCell header, Object total)
    {
        Object displayValue = total;
        if (header.getColumnDecorators().length > 0)
        {
            for (int i = 0; i < header.getColumnDecorators().length; i++)
            {
                DisplaytagColumnDecorator decorator = header.getColumnDecorators()[i];
                try
                {
                    displayValue = decorator.decorate(total, this.getPageContext(), this.tableModel.getMedia());
                }
                catch (DecoratorException e)
                {
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
    class GroupTotals
    {

        /**
         * The label class.
         */
        protected String totalLabelClass = getSubtotalLabelClass();

        /** The row opener. */
        protected String totalsRowOpen = getTotalsRowOpen();

        /**
         * The value class.
         */
        protected String totalValueClass = getSubtotalValueClass();

        /** The column number. */
        private int columnNumber;

        /** The first row of current set. */
        private int firstRowOfCurrentSet;

        /**
         * Instantiates a new group totals.
         *
         * @param headerCellColumn the header cell column
         */
        public GroupTotals(int headerCellColumn)
        {
            this.columnNumber = headerCellColumn;
            this.firstRowOfCurrentSet = 0;
        }

        /**
         * Prints the totals.
         *
         * @param currentRow the current row
         * @param out the out
         */
        public void printTotals(int currentRow, StringBuffer out)
        {

            // For each column, output:
            List<HeaderCell> headerCells = MultilevelTotalTableDecorator.this.tableModel.getHeaderCellList();
            if (this.firstRowOfCurrentSet < currentRow) // If there is more than one row, show a total
            {
                out.append(this.totalsRowOpen);
                for (Iterator<HeaderCell> iterator = headerCells.iterator(); iterator.hasNext();)
                {
                    HeaderCell headerCell = iterator.next();

                    if (this.columnNumber == headerCell.getColumnNumber())
                    {
                        // a totals label if it is the column for the current group
                        String currentLabel = getCellValue(this.columnNumber, this.firstRowOfCurrentSet);
                        out.append(getTotalsTdOpen(headerCell, getTotalLabelClass() + " group-" + (this.columnNumber + 1)));
                        out.append(getTotalRowLabel(currentLabel));
                    }
                    else if (headerCell.isTotaled())
                    {
                        // a total if the column should be totaled
                        Object total = getTotalForColumn(headerCell.getColumnNumber(), this.firstRowOfCurrentSet, currentRow);
                        out.append(getTotalsTdOpen(headerCell, getTotalValueClass() + " group-" + (this.columnNumber + 1)));
                        out.append(formatTotal(headerCell, total));
                    }
                    else
                    {
                        // blank, if it is not a totals column
                        String style = "group-" + (this.columnNumber + 1);
                        if (headerCell.getColumnNumber() < MultilevelTotalTableDecorator.this.innermostGroup)
                        {
                            style += " " + getTotalLabelClass() + " ";
                        }
                        out.append(getTotalsTdOpen(headerCell, style));
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
        public int getColumnNumber()
        {
            return this.columnNumber;
        }

        /**
         * Sets the start row.
         *
         * @param i the new start row
         */
        public void setStartRow(int i)
        {
            this.firstRowOfCurrentSet = i;
        }

        /**
         * Gets the total label class.
         *
         * @return the total label class
         */
        public String getTotalLabelClass()
        {
            return this.totalLabelClass;
        }

        /**
         * Sets the totals row open.
         *
         * @param totalsRowOpen the new totals row open
         */
        public void setTotalsRowOpen(String totalsRowOpen)
        {
            this.totalsRowOpen = totalsRowOpen;
        }

        /**
         * Sets the total label class.
         *
         * @param totalLabelClass the new total label class
         */
        public void setTotalLabelClass(String totalLabelClass)
        {
            this.totalLabelClass = totalLabelClass;
        }

        /**
         * Gets the total value class.
         *
         * @return the total value class
         */
        public String getTotalValueClass()
        {
            return this.totalValueClass;
        }

        /**
         * Sets the total value class.
         *
         * @param totalValueClass the new total value class
         */
        public void setTotalValueClass(String totalValueClass)
        {
            this.totalValueClass = totalValueClass;
        }
    }
}
